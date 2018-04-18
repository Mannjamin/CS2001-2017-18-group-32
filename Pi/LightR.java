
package LightR;
//package org.iot.raspberry.examples;
import org.iot.raspberry.grovepi.devices.*;
import java.io.IOException;
import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.iot.raspberry.grovepi.GrovePi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;  


/**
 *
 * @author Garratt
 */
public class LightR implements Example {
    
    /**
     *
     * @param grovePi
     * @param monitor
     * @throws Exception
     */
    public void run(GrovePi grovePi, Monitor monitor) throws Exception {
        
        //SQLConnect();
        Statement stmt;
        ResultSet rs;
        boolean state = false;
        GroveDigitalOut redLed = grovePi.getDigitalOut(3);
        GroveDigitalOut greenLed = grovePi.getDigitalOut(5);
        GroveDigitalOut blueLed = grovePi.getDigitalOut(2);
        boolean soundTest = false;
        boolean lightTest = false;
        boolean tempTest = false;
        
        
        while (monitor.isRunning()){
            
            int LEDConfig = 0;
            soundTest = SoundSensor.run(grovePi, monitor);
            lightTest = Light.run(grovePi, monitor);
            tempTest = temp.run(grovePi, monitor);
            
            System.out.println();
            
            if (soundTest = true){
                
                LEDConfig += 1;
                
                
            }
            if (lightTest = true){
                    
                LEDConfig += 2;
                    
            }
            if (tempTest = true){
                        
                LEDConfig += 5;        
                        
            }
                    
            switch (LEDConfig){
            
            case 1:
                greenLed.set(!state);
                Thread.sleep(100);
                greenLed.set(state);
                break;
                
            case 2: 
                blueLed.set(!state);
                Thread.sleep(100);
                blueLed.set(state);
                break;
                    
            case 3:
                greenLed.set(!state);
                blueLed.set(!state);
                Thread.sleep(100);
                greenLed.set(state);
                blueLed.set(state);
                break;
            
            case 5:
                redLed.set(!state);
                Thread.sleep(100);
                redLed.set(state);
                break;
                
            case 6:
                redLed.set(!state);
                greenLed.set(!state);
                Thread.sleep(100);
                redLed.set(state);
                greenLed.set(state);
                break;
                
            case 7:
                redLed.set(!state);
                blueLed.set(!state);
                Thread.sleep(100);
                redLed.set(state);
                blueLed.set(state);
                break;
                
            case 8:
                greenLed.set(!state);
                redLed.set(!state);
                blueLed.set(!state);
                Thread.sleep(100);
                greenLed.set(!state);
                redLed.set(state);
                blueLed.set(state);
                break;
            
        }        
            
            
            Thread.sleep(5000);
                    
            
            /*
        try{
            
        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://82.39.20.185:3306/LightR";

        Connection con = DriverManager.getConnection( url,"garratt","password");
        Statement select = con.createStatement();

        // Execute a quesry

       rs = select.executeQuery("SELECT Identification, Temperature FROM LightR");
       
       while (rs.next()) { // process results one row at a time
           
       int key = rs.getInt(1);
       int field = rs.getInt(2);

        System.out.println("key = " + key);
      
        System.out.println("Field = " + field);

       }
       System.out.println("YAY");
        }
       catch (Exception e) {
            System.out.println(e);  // What is this doing?
        }

                
            
            */
            
       
 
       }
    
    }
    
    public Connection SQLConnect(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            String url = "jdbc:mysql://172.31.84.82:3306/TABLE NAME";
            
            
            Connection con = DriverManager.getConnection( url,"root","root");
            return con;
           
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
        
        
         
    }
    
    
    
}
