package jimhome.acube;

/**
 * Created by jack2 on 3/03/2015.
 */
public class LedCubePatterns extends LedCube {

    void Swirl() {
        Led led;
        led = getLed(1,1,1);
        led.b = 255;
        led.changed = true;
    };
}
