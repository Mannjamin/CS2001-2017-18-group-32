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
public class SoundSensor {
    
    public static int soundLimit = 50;
    
    public static int run(GrovePi grovePi, Monitor monitor) throws IOException, InterruptedException, ClassNotFoundException, SQLException{
        
        Statement stmt;
        ResultSet rs;
        
        GroveSoundSensor soundSensor = new GroveSoundSensor(grovePi, 0);
        double soundLevel = soundSensor.get();
        int intSound = (int)Math.round(soundLevel);
        System.out.println("Sound: " + intSound);
        
        return intSound;
            
    }
    
}
    
    

