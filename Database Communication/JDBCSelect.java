// To compile: javac JDBCSelect.java
// To run: java -cp .:mysqljdbc.jar JDBCSelect


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class JDBCSelect {

    public static void main(String args[]){
        System.out.println("Hello");
        try {
            Statement stmt;
            ResultSet rs;

            //Register the JDBC driver for MySQL

            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://IP:3306/lightr";

            Connection con = DriverManager.getConnection( url,"USERNAME","PASSWORD");
            Statement select = con.createStatement();

            // Execute a quesry

            rs = select.executeQuery("SELECT identification, timestamp, temperature, lighting, sound FROM lightr");

            System.out.println("Some results:");
            while (rs.next()) { // process results one row at a time
                int identification = rs.getInt(1);
                int timestamp = rs.getInt(2);
                int temperature = rs.getInt(3);
                int lighting = rs.getInt(4);
                int sound = rs.getInt(5);

                System.out.println("Identification: " + identification);
                System.out.println("Timestamp: " + timestamp);
                System.out.println("Temperature: " + temperature);
                System.out.println("Lighting: " + lighting);
                System.out.println("Sound: " + sound);
            }
        }
        catch (Exception e) {
            System.out.println(e);  // What is this doing?
        }
    }
}


