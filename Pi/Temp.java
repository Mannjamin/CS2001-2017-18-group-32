
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
    
    public static boolean run(GrovePi grovePi, Monitor monitor) throws IOException{
        
        GroveTemperatureAndHumiditySensor dht = new GroveTemperatureAndHumiditySensor(grovePi, 4, GroveTemperatureAndHumiditySensor.Type.DHT11);
        GroveDigitalOut redLed = grovePi.getDigitalOut(3);
        double tempLimit = 15.0;
        
        double temp = new Double(dht.get().toString());
        boolean state = false, overLimit = false;
        
        try{
            
          if (temp > tempLimit) {
                overLimit = true;
                //redLed.set(!state);
                //Thread.sleep(100);
                //redLed.set(state);
            } 
          
          System.out.println("Temperature: " + temp);
          }

        catch (Exception e){
            
            System.out.println(e);
            
        }

        return overLimit;
        
    }
    
    
    
}
