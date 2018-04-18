

package LightR;
import org.iot.raspberry.grovepi.devices.*;
import java.io.IOException;
import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.iot.raspberry.grovepi.GrovePi;
  
/**
 *
 * @author Garratt
 */
public class SoundSensor {
    
    public static boolean run(GrovePi grovePi, Monitor monitor) throws IOException, InterruptedException{
        
        
        GroveSoundSensor soundSensor = new GroveSoundSensor(grovePi, 0);
        GroveDigitalOut redLed = grovePi.getDigitalOut(3);
        GroveDigitalOut greenLed = grovePi.getDigitalOut(5);
        GroveDigitalOut blueLed = grovePi.getDigitalOut(2);
        GroveDigitalOut onLed = null;
        boolean state = false, overLimit = false;
        double soundLimit = 50.0;
        
        try {
            double soundLevel = soundSensor.get();
            System.out.println("Sound: " + soundLevel);
            GroveDigitalOut ledToTurn;
            if (soundLevel > soundLimit) {
                overLimit = true;
                //greenLed.set(!state);
                //Thread.sleep(100);
                //greenLed.set(state);
            } 
            } catch (IOException ex) {
                System.out.println("Error");
            }
    
            
        
        return overLimit;
    }
    
    
    
}
