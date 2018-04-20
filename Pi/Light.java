
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
    
    
    public static boolean run(GrovePi grovePi, Monitor monitor) throws IOException{
        
        GroveDigitalOut blueLed = grovePi.getDigitalOut(2);
        GroveLightSensor lightSensor = new GroveLightSensor(grovePi, 2);
        double lightLevel = lightSensor.get();
        boolean state = false, overLimit = false;
        double lux, alpha = 3.75, lightLimit = 200.0;
        lux = lightLevel * alpha;
        try{
            
          if (lux > lightLimit) {
                overLimit = true;
                //blueLed.set(!state);
                //Thread.sleep(100);
                //blueLed.set(state);
            } 
          
          System.out.println("Light: " + lux);   
          }

        catch (Exception e){
            
            System.out.println(e);
            
        }
        
        
        
        return overLimit;
        
        
        
    }
    
}
