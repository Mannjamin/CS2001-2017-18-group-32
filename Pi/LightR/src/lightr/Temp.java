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


/**
 *
 * @author Garratt
 */
public class Temp {
    
    //public static int tempLimit = SQL.limits("tempLimit");;
    
    public static double run(GrovePi grovePi, Monitor monitor) throws IOException{
        
        GroveTemperatureAndHumiditySensor dht = new GroveTemperatureAndHumiditySensor(grovePi, 4, GroveTemperatureAndHumiditySensor.Type.DHT11);
        double temp = new Double(dht.get().toString());
        System.out.println("Temperature: " + temp);

        return temp;
        
    }
    
    
    
}
