/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LightR;
import org.iot.raspberry.grovepi.devices.*;
import java.io.IOException;
import static java.lang.Math.*;
import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.iot.raspberry.grovepi.GrovePi;

/**
 *
 * @author Garratt
 */
public class Light {
    
    public static int lightLimit = 50;
    
    public static double run(GrovePi grovePi, Monitor monitor) throws IOException{
        
        GroveDigitalOut blueLed = grovePi.getDigitalOut(2);
        GroveLightSensor lightSensor = new GroveLightSensor(grovePi, 2);
        double lightLevel = lightSensor.get();
        boolean state = false, overLimit = false;
        double lux, alpha = 3.75;
        lux = lightLevel * alpha;

        System.out.println("Light: " + lux);   

        return lux;

    }
    
}
