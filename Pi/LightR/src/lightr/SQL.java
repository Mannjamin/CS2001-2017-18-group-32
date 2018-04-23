/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LightR;
import org.iot.raspberry.grovepi.devices.*;
import java.io.IOException;
import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.iot.raspberry.grovepi.GrovePi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger; 
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Garratt
 */
public class SQL {
    
    public static void run(double temp, double light, double sound) throws SQLException, ClassNotFoundException{
        
        Statement stmt;
        ResultSet rs;
        
        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://82.39.20.185:3306/lightr";

        Connection con = DriverManager.getConnection( url,"garratt","password");
        Statement select = con.createStatement();

        // Execute a quesry

       stmt = con.createStatement();
        
       
       stmt.executeUpdate( "INSERT INTO lightr(temperature, lighting, sound) VALUES("+temp+", "+light+", "+sound+")");
        
        
        
    }
    
    public static int limits(String sensor) throws ClassNotFoundException, SQLException{
        
        Statement stmt;
        ResultSet rs;
        
        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://82.39.20.185:3306/lightr";

        Connection con = DriverManager.getConnection( url,"garratt","password");
        Statement select = con.createStatement();

       rs = select.executeQuery("SELECT "+sensor+" FROM sensor_Limits WHERE ID = 1");
       int value = 0;
       while (rs.next()) { // process results one row at a time
        value = rs.getInt(1);
        
       }

       return value;
    }
    

}
