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
public class LED {
    
    public static void run(Monitor monitor, GrovePi grovePi, int LEDConfig) throws Exception{
        
        GroveDigitalOut redLed = grovePi.getDigitalOut(3);
        GroveDigitalOut greenLed = grovePi.getDigitalOut(5);
        GroveDigitalOut blueLed = grovePi.getDigitalOut(2);
        
        boolean state = false;
        int wait = (LightR.getWait() / 100);
        
       
        
        switch (LEDConfig){
            
            case 1:
                greenLed.set(!state);
                Thread.sleep(1000);
                greenLed.set(state);
                break;
                
            case 2: 
                blueLed.set(!state);
                Thread.sleep(1000);
                blueLed.set(state);
                break;
                    
            case 3:
                greenLed.set(!state);
                blueLed.set(!state);
                Thread.sleep(1000);
                greenLed.set(state);
                blueLed.set(state);
                break;
            
            case 5:
                redLed.set(!state);
                Thread.sleep(1000);
                redLed.set(state);
                break;
                
            case 6:
                redLed.set(!state);
                greenLed.set(!state);
                Thread.sleep(1000);
                redLed.set(state);
                greenLed.set(state);
                break;
                
            case 7:
                redLed.set(!state);
                blueLed.set(!state);
                Thread.sleep(1000);
                redLed.set(state);
                blueLed.set(state);
                break;
                
            case 8:
                greenLed.set(!state);
                redLed.set(!state);
                blueLed.set(!state);
                Thread.sleep(1000);
                greenLed.set(state);
                redLed.set(state);
                blueLed.set(state);
                break;
            
          
        
       }
        
    }
    
    
    
}
