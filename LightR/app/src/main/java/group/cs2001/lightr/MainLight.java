package group.cs2001.lightr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainLight extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ProgressDialog progressDialog;
    String MaxLumens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_light);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        MaxLumens = "0";
        getJSON("http://82.39.20.185/php/getLightingData.php");
    }

    private void getJSON(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                progressDialog= new ProgressDialog(MainLight.this);
                progressDialog.setMessage("Loading Data, Please Wait...");
                progressDialog.show();
                super.onPreExecute();
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onPostExecute(String JsonString) {
                super.onPostExecute(JsonString);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                LineGraphSeries<DataPoint> maxseries = new LineGraphSeries<>();
                try {
                    int maxLight = 0;
                    int minLight = 1000;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date maxDate = dateFormat.parse("1970-01-01 00:00:00");
                    Date minDate = dateFormat.parse("3000-01-01 00:00:00");
                    JSONArray jsonarray1 = new JSONArray(JsonString);
                    JSONArray jsonarray = jsonarray1.getJSONArray(0);
                    for(int i = 0; i < jsonarray.length(); i++)
                    {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String timestampString = jsonobject.getString("timestamp");
                        String lightString = jsonobject.getString("lighting");
                        Date parsedDate = dateFormat.parse(timestampString);
                        int light = Integer.parseInt(lightString);
                        DataPoint dp = new DataPoint(parsedDate, light);
                        series.appendData(dp, true, 24);
                        updateCurrentLumens(Double.toString(light));

                        if(light > maxLight){maxLight = light;}

                        if(light < minLight){minLight = light;}

                        if(parsedDate.after(maxDate)){maxDate = parsedDate;}

                        if(parsedDate.before(minDate)){minDate = parsedDate;}
                    }
                    DataPoint StartingMaxdp = new DataPoint(minDate, Integer.parseInt(MaxLumens));
                    DataPoint EndingMaxdp2 = new DataPoint(maxDate, Integer.parseInt(MaxLumens));
                    maxseries.appendData(StartingMaxdp, true, 10);
                    maxseries.appendData(EndingMaxdp2, true, 10);
                    DataPoint[] dpArray = {StartingMaxdp, EndingMaxdp2};
                    maxseries.resetData(dpArray);
                    maxseries.setThickness(10);
                    maxseries.setDrawBackground(true);

                    dateFormat = new SimpleDateFormat("mm");

                    GraphView graph = findViewById(R.id.light_graph);
                    graph.removeAllSeries();
                    graph.getGridLabelRenderer().setHorizontalAxisTitle("Time (hrs)");
                    graph.getGridLabelRenderer().setVerticalAxisTitle("Light (Lumens)");

                    graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(MainLight.this, dateFormat));
                    graph.getGridLabelRenderer().setNumHorizontalLabels(10);

                    graph.getViewport().setScalable(true);
                    graph.getViewport().setScalableY(true);

                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(minLight);
                    graph.getViewport().setMaxY(maxLight);

                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinX(minDate.getTime());
                    graph.getViewport().setMaxX(maxDate.getTime());

                    graph.addSeries(maxseries);
                    graph.addSeries(series);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;

                    while ((json = bufferedReader.readLine()) != null) {
                        System.out.println(json);
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    @Override
    public void onBackPressed() {
        //Log.d("CDA", "onBackPressed Called");
        Intent intent = new Intent(MainLight.this, MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_devices) {
            Intent intent = new Intent(MainLight.this, MainDevices.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_light) {
            Intent intent = new Intent(MainLight.this, MainLight.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_temp) {
            Intent intent = new Intent(MainLight.this, MainTemp.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_sound) {
            Intent intent = new Intent(MainLight.this, MainSound.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainLight.this, MainSettings.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateCurrentLumens(String toThis) {
        TextView textView = findViewById(R.id.textView5);
        textView.setText(toThis + " dB");
    }

    public void sendMessage(View view) {
        TextView theFact = findViewById(R.id.max_lumens);
        MaxLumens = theFact.getText().toString();
        getJSON("http://82.39.20.185/php/getLightingData.php");
    }
}