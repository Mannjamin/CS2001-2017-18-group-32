package group.cs2001.lightr;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.*;


import static group.cs2001.lightr.R.*;

public class MainSound extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String MaxdB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main_sound);
        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, string.navigation_drawer_open, string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MaxdB = "0";
        getJSON("http://82.39.20.185/php/test.php");
    }

    private void getJSON(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onPostExecute(String JsonString) {
                super.onPostExecute(JsonString);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                LineGraphSeries<DataPoint> maxseries = new LineGraphSeries<>();
                try {
                    int maxSound = 0;
                    int minSound = 1000;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date maxDate = dateFormat.parse("1970-01-01 00:00:00");
                    Date minDate = dateFormat.parse("3000-01-01 00:00:00");
                    JSONArray jsonarray1 = new JSONArray(JsonString);
                    JSONArray jsonarray = jsonarray1.getJSONArray(0);
                    for(int i = 0; i < jsonarray.length(); i++)
                    {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String timestampString = jsonobject.getString("timestamp");
                        String soundString = jsonobject.getString("sound");
                        Date parsedDate = dateFormat.parse(timestampString);
                        int sound = Integer.parseInt(soundString);
                        DataPoint dp = new DataPoint(parsedDate, sound);
                        series.appendData(dp, true, 24);
                        updateCurrentdB(Double.toString(sound));

                        if(sound > maxSound)
                        {
                            maxSound = sound;
                        }

                        if(sound < minSound)
                        {
                            minSound = sound;
                        }

                        if(parsedDate.after(maxDate))
                        {
                            maxDate = parsedDate;
                        }

                        if(parsedDate.before(minDate))
                        {
                            minDate = parsedDate;
                        }
                    }
                    DataPoint StartingMaxdp = new DataPoint(minDate, Integer.parseInt(MaxdB));
                    DataPoint EndingMaxdp2 = new DataPoint(maxDate, Integer.parseInt(MaxdB));
                    maxseries.appendData(StartingMaxdp, true, 10);
                    maxseries.appendData(EndingMaxdp2, true, 10);
                    DataPoint[] dpArray = {StartingMaxdp, EndingMaxdp2};
                    maxseries.resetData(dpArray);
                    maxseries.setBackgroundColor(color.colorAccent);

                    dateFormat = new SimpleDateFormat("mm");

                    GraphView graph = findViewById(id.sound_graph);
                    graph.getGridLabelRenderer().setHorizontalAxisTitle("Time (hrs)");
                    graph.getGridLabelRenderer().setVerticalAxisTitle("Sound (dB)");

                    graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(MainSound.this, dateFormat));
                    graph.getGridLabelRenderer().setNumHorizontalLabels(10);

                    graph.getViewport().setScalable(true);
                    graph.getViewport().setScalableY(true);

                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(minSound);
                    graph.getViewport().setMaxY(maxSound);

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
        Intent intent = new Intent(MainSound.this, MainMenu.class);
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
            Intent intent = new Intent(MainSound.this, MainDevices.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent); } else if (id == R.id.nav_light)
        {
            Intent intent = new Intent(MainSound.this, MainLight.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent); } else if (id == R.id.nav_temp)
        {
            Intent intent = new Intent(MainSound.this, MainTemp.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent); } else if (id == R.id.nav_sound)
        {
            Intent intent = new Intent(MainSound.this, MainSound.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent); } else if (id == R.id.nav_settings)
        {
            Intent intent = new Intent(MainSound.this, MainSettings.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateCurrentdB(String toThis) {
        TextView textView = findViewById(id.textView5);
        textView.setText(toThis + " dB");
    }

    public void sendMessage(View view) {
        TextView theFact = findViewById(id.max_decibels);
        MaxdB = theFact.getText().toString();
        getJSON("http://82.39.20.185/php/test.php");
    }

}