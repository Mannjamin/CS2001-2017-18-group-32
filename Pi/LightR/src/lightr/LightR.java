/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    public static int waitTime = 60000;
    
    
    @Override
    public void run(GrovePi grovePi, Monitor monitor) throws Exception {
        
        double sound = 0;
        double light = 0;
        double temp = 0;
        double soundLimit = SQL.limits("soundLimit");
        double lightLimit = SQL.limits("lightLimit");
        double tempLimit = SQL.limits("tempLimit");
        

        while (monitor.isRunning()){
            
            int LEDConfig = 0;
            light = Light.run(grovePi, monitor);
            sound = SoundSensor.run(grovePi, monitor);
            temp = Temp.run(grovePi, monitor);
            
            System.out.println();
            
            if (sound > soundLimit){
                
                LEDConfig += 1;
                
                
            }
            if (light > lightLimit){
                    
                LEDConfig += 2;
                    
            }
            if (temp > tempLimit){
                        
                LEDConfig += 5;        
                        
            }
                    
            LED.run(monitor, grovePi, LEDConfig);
            
            
            SQL.run(temp, light, sound);

            Thread.sleep(waitTime);

       }
    
    }
    
    public static int getWait(){
        
        return waitTime;
        
    }
 
}
