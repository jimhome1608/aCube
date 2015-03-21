package jimhome.acube;

import android.os.SystemClock;

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

    public void WalkThisWay() {
        int _delay = 0;
        boolean hitX = false;
        boolean hitY = false;
        boolean hitZ = false;
        while ( hitZ == false) {
            hitX = false;
            while (hitX == false) {
                hitX = Xplus();
                BlueToothWrite();
                SystemClock.sleep(_delay);
            }
            hitY = false;
            while (hitY == false) {
                hitY = Yplus();
                BlueToothWrite();
                SystemClock.sleep(_delay);
            }
            hitX = false;
            while (hitX == false) {
                hitX = Xminus();
                BlueToothWrite();
                SystemClock.sleep(_delay);
            }
            hitY = false;
            while (hitY == false) {
                hitY = Yminus();
                BlueToothWrite();
                SystemClock.sleep(_delay);
            }
            hitZ = Zplus();
            BlueToothWrite();
            SystemClock.sleep(_delay);
        }
        hitX = false;
        while (hitX == false) {
            hitX = Xplus();
            BlueToothWrite();
            SystemClock.sleep(_delay);
        }
        hitY = false;
        while (hitY == false) {
            hitY = Yplus();
            BlueToothWrite();
            SystemClock.sleep(_delay);
        }
        hitX = false;
        while (hitX == false) {
            hitX = Xminus();
            BlueToothWrite();
            SystemClock.sleep(_delay);
        }
        hitY = false;
        while (hitY == false) {
            hitY = Yminus();
            BlueToothWrite();
            SystemClock.sleep(_delay);
        }
    }

    public void WalkThisWayBasic() {
        busy = true;
        int x = 0;
        int y = 0;
        int z = 0;
        Led led;
        Led prevLed = null;
        int _delay = 0;

        MoveTo(0,0,0);
        for (z=0;z<=3;z++) {
            for (x = 0; x <= 3; x++) {
                Xplus();
                BlueToothWrite();
                SystemClock.sleep(_delay);
            }
            for (y = 1; y <= 3; y++) {
                Yplus();
                BlueToothWrite();
                SystemClock.sleep(_delay);
            }
            for (x = 2; x >= 0; x--) {
                Xminus();
                BlueToothWrite();
                SystemClock.sleep(_delay);
            }
            for (y = 2; y >= 0; y--) {
                Yminus();
                BlueToothWrite();
                SystemClock.sleep(_delay);
            }
            Zplus();
            BlueToothWrite();
            SystemClock.sleep(_delay);
        }
        busy = false;
    }


    public void Step() {
        int _delay = 200;
        if (direction == "zup" ) {
            Zplus();
            direction = "xup";
            return;
        }
        if (direction == "xup" ) {
            if (Xplus()==true)
                direction = "yup";
            return;
        }
        if (direction == "yup" ) {
            if (Yplus()==true)
                direction = "xdown";
            return;
        }
        if (direction == "xdown" ) {
            if (Xminus()==true)
                direction = "ydown";
            return;
        }
        if (direction == "ydown" ) {
            if (Yminus()==true)
                direction = "zup";
            return;
        }
        if (direction != "stop" ) {
            BlueToothWrite();
            SystemClock.sleep(_delay);
        }
        if (home3D.x ==0 && home3D.y == 0 && home3D.z == 3)
            direction = "stop";
    }
}
