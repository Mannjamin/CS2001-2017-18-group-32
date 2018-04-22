package group.cs2001.lightr;

import android.os.AsyncTask;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// The types specified here are the input data type, the progress type, and the result type
public class MyAsyncTask extends AsyncTask<String, Void, LineGraphSeries<DataPoint>> {

    protected void onPreExecute() {
    }

    protected LineGraphSeries<DataPoint> doInBackground(String... strings) {
        // Some long-running task like downloading an image.
        return GetSeries();
    }

    protected void onPostExecute(LineGraphSeries<DataPoint> result) {
        // This method is executed in the UIThread
        // with access to the result of the long running task
        GraphView graph = null;
        graph = graph.findViewById(R.id.graph);
        graph.addSeries(result);
    }

    private LineGraphSeries<DataPoint> GetSeries() {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        try {
            ResultSet rs;

            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://82.39.20.185:3306/lightr";

            Connection con = DriverManager.getConnection( url,"USERNAME","PASSWORD");
            Statement select = con.createStatement();

            // Execute a query

            rs = select.executeQuery("SELECT timestamp, sound FROM lightr");

            System.out.println("Some results:");
            while (rs.next()) { // process results one row at a time
                int timestamp = rs.getInt(2);
                int sound = rs.getInt(5);

                System.out.println(timestamp + ", " + sound);
                DataPoint datap = new DataPoint(timestamp, sound);

                series.appendData(datap, true, 24);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return series;
    }
}
