package jimhome.acube;

import android.graphics.Color;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.Random;

public class LedCube {
    public String homeColor = "yellow";
    public String onColor = "blue";
    public Point3D home3D;
    public Point3D AnotherSpot;
    public ArrayList<Led> ledList = new ArrayList<Led>();
    public String direction = "xup";
    public boolean busy = false;

    public void RandomColors() {
        busy = true;
        Led led;
        for (int x=0;x<4;x++) {
            for (int y=0;y<4;y++) {
                for (int z=0; z<4; z++) {
                    led = getLed(x,y,z);
                    led.randomColor();

                }
            }
        }
        busy = false;
    }

    public void RandomLedsRandomColors() {
        busy = true;
        Led led;
        Random rn = new Random();
        for (int x=0;x<4;x++) {
            for (int y=0;y<4;y++) {
                for (int z=0; z<4; z++) {
                    led = getLed(rn.nextInt(4),rn.nextInt(4),rn.nextInt(4));
                    led.randomColor();
                    led.turnedOnByUser = false;
                    BlueToothWrite();
                   // SystemClock.sleep(10);
                }
            }
        }
        busy = false;
    }



    public LedCube() {
        home3D = new Point3D(0, 0, 0);
        AnotherSpot = new Point3D(0, 0, 0);
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int z = 0; z < 4; z++) {
                    Led led = new Led(x, y, z, 0, 0, 0);
                    ledList.add(led);
                }
                ;
            }
        }
        setAllChanged(false);
    }

    public void BlueToothWrite() {
        Led led;
        String result = "";
        for (int i = 0; i < 64; i++) {
            led = (Led) ledList.get(i);
            if (led.changed == false)
                continue;
            // Log.v("x", led.asCommand());
            result = result+led.asCommand();
        };
        setAllChanged(false);
        aBluetooth.write(result);
    }

    public String GetLedCommands() {
        Led led;
        String result = "";
        for (int i = 0; i < 64; i++) {
            led = (Led) ledList.get(i);
            if (led.changed == false)
                continue;
            // Log.v("x", led.asCommand());
            result = result+led.asCommand();
        };
        setAllChanged(false);
        return result;
    }

    public void setTurnedOnByUser() {
        Led led;
        led = getLed(home3D.x,home3D.y, home3D.z);
        led.turnOn(onColor);
        led.turnedOnByUser = true;
    }


    public void MoveTo (int x, int y, int z) {
        home3D.x = x;
        home3D.y = y;
        home3D.z = z;
    }

    public int LevelZ(int _z) {
        AllOff();
        Led led;
        for (int x = 0; x < 4; x++) {
            for (int y=0; y<4; y++) {
                led = getLed(x,y, home3D.z);
                led.turnOn(homeColor);
            }
        }
        return 0;
    }

    public int WallX(int _x) {
        AllOff();
        Led led;
        for (int z = 0; z < 4; z++) {
            for (int y=0; y<4; y++) {
                led = getLed(home3D.x,y, z);
                led.turnOn(homeColor);
            }
        }
        return 0;
    }

    public int WallY(int _y) {
        AllOff();
        Led led;
        for (int x = 0; x < 4; x++) {
            for (int z=0; z<4; z++) {
                led = getLed(x,home3D.y, z);
                led.turnOn(homeColor);
            }
        }
        return 0;
    }

    public int AllOff() {
        Led led;
        for (int i = 0; i < 64; i++) {
            led = (Led) ledList.get(i);
            if (led.hasColor()) {
                led.changed = true;
                led.turnedOnByUser = false;
                led.b = 0;
                led.g = 0;
                led.r = 0;
            };
        }
        //MoveTo(0,0,0);
        return 0;
    }

    public int AllOn(String colorString) {
        Led led;
        int color = Color.parseColor(colorString);
        for (int i = 0; i < 64; i++) {
            led = (Led) ledList.get(i);
            led.b = Color.blue(color);
            led.g = Color.green(color);
            led.r = Color.red(color);
        }
        //MoveTo(0,0,0);
        return 0;
    }

    public void LineTo(Point3D p) {
        DrawLine(home3D, p,homeColor);
        MoveTo(p.x,p.y,p.z);
    }

    public void LineTo(int x, int y, int z) {
        DrawLine(home3D, new Point3D(x,y,z),homeColor);
        MoveTo(x, y, z);
    }

    public boolean Xplus(){
        Led led;
        led = getLed(home3D.x,home3D.y,home3D.z);
        led.turnOff();
        home3D.x++;
        if (home3D.x > 3)
            home3D.x = 3;
        led = getLed(home3D.x,home3D.y,home3D.z);
        led.turnOn(homeColor);
        return (home3D.x == 3);

    }

    public boolean Xminus(){
        Led led;
        led = getLed(home3D.x,home3D.y,home3D.z);
        led.turnOff();
        home3D.x--;
        if (home3D.x < 0)
            home3D.x = 0;
        led = getLed(home3D.x,home3D.y,home3D.z);
        led.turnOn(homeColor);
        return (home3D.x == 0);
    }

    public boolean Zplus(){
        Led led;
        led = getLed(home3D.x,home3D.y,home3D.z);
        led.turnOff();
        home3D.z++;
        if (home3D.z > 3)
            home3D.z = 3;
        led = getLed(home3D.x,home3D.y,home3D.z);
        led.turnOn(homeColor);
        return (home3D.z == 3);
    }

    public boolean Zminus(){
        Led led;
        led = getLed(home3D.x,home3D.y,home3D.z);
        led.turnOff();
        home3D.z--;
        if (home3D.z < 0)
            home3D.z = 0;
        led = getLed(home3D.x,home3D.y,home3D.z);
        led.turnOn(homeColor);
        return (home3D.z == 0);
    }

    public boolean Yplus(){
        Led led;
        led = getLed(home3D.x,home3D.y,home3D.z);
        led.turnOff();
        home3D.y++;
        if (home3D.y > 3)
            home3D.y = 3;
        led = getLed(home3D.x,home3D.y,home3D.z);
        led.turnOn(homeColor);
        return (home3D.y == 3);
    }

    public boolean Yminus(){
        Led led;
        led = getLed(home3D.x,home3D.y,home3D.z);
        led.turnOff();
        home3D.y--;
        if (home3D.y < 0)
            home3D.y = 0;
        led = getLed(home3D.x,home3D.y,home3D.z);
        led.turnOn(homeColor);
        return (home3D.y == 0);
    }

    public  int DrawLine(Point3D p1, Point3D p2, String colorString ) {
        int color = Color.parseColor(colorString);
        Led led;
        Point3D p = new Point3D(p1.x,p1.y,p1.z);
        led = getLed(p.x,p.y,p.z);
        led.setColor(color);
        led.changed = true;
        if (p.x < p2.x) p.x++;
        if (p.x > p2.x) p.x--;
        if (p.y < p2.y) p.y++;
        if (p.y > p2.y) p.y--;
        if (p.z < p2.z) p.z++;
        if (p.z > p2.z) p.z--;
        led = getLed(p.x,p.y,p.z);
        led.setColor(color);
        led.changed = true;
        if (p.x < p2.x) p.x++;
        if (p.x > p2.x) p.x--;
        if (p.y < p2.y) p.y++;
        if (p.y > p2.y) p.y--;
        if (p.z < p2.z) p.z++;
        if (p.z > p2.z) p.z--;
        led = getLed(p.x,p.y,p.z);
        led.setColor(color);
        led.changed = true;
        if (p.x < p2.x) p.x++;
        if (p.x > p2.x) p.x--;
        if (p.y < p2.y) p.y++;
        if (p.y > p2.y) p.y--;
        if (p.z < p2.z) p.z++;
        if (p.z > p2.z) p.z--;
        led = getLed(p.x,p.y,p.z);
        led.setColor(color);
        led.changed = true;
        return 1;
    }

    public Led getLed(int _x, int _y, int _z) {
        Led result = null;
        for (int idx=0;idx<64;idx++) {
            result =  (Led) ledList.get(idx);
            if (result.x==_x && result.y ==_y && result.z==_z) {
                 result =  (Led) ledList.get(idx);
                 break;
                }
        }
        return result;
    }

    public int setAllChanged(boolean _changed) {
        Led led;
        for (int i = 0; i < 64; i++) {
            led = (Led) ledList.get(i);
            led.changed = _changed;
        }
        return 0;
    }

    public int invert() {
        Led led;
        for (int i = 0; i < 64; i++) {
            led = (Led) ledList.get(i);
            led.invert();
        }
        return 0;
    }



    public class Led {
        public int x = 0;
        public int y = 0;
        public int z = 0;
        public int r = 0;
        public int g = 0;
        public int b = 0;
        public boolean changed = false;
        public boolean turnedOnByUser = false;

        public void setColor(int color ) {
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
        }

        public Led(int _x, int _y, int _z, int _r, int _g, int _b){
            x = _x;
            y = _y;
            z = _z;
            r = _r;
            g = _g;
            b = _b;
        }

        public boolean hasColor()
        {
           boolean _result = false;

           if (r != 0) _result = true;
           if (g != 0) _result = true;
           if (b != 0) _result = true;
           return _result;
        }

        public void randomColor() {
            Random rn = new Random();
            r = rn.nextInt(255);
            g = rn.nextInt(255);
            b = rn.nextInt(255);
            changed = true;
        }

        public void invert()
        {
            if (hasColor())
                turnOff();
            else
                turnOn(homeColor);
            changed = true;
        }


        public void turnOff() {
            if (turnedOnByUser)
                return;
            if (hasColor()) {
                changed = true;
                r = 0;
                g = 0;
                b = 0;
            }
        }

        public void turnOn(String colorString) {
            if (turnedOnByUser)
                 return;
            int color = Color.parseColor(colorString);
            if (r != Color.red(color)) {
                changed = true;
                r = Color.red(color);
            };
            if (g != Color.green(color)) {
                changed = true;
                g = Color.green(color);
            };
            if (b != Color.blue(color)) {
                changed = true;
                b = Color.blue(color);
            };
        }

        public String asCommand() {
            String red =  Integer.toHexString(r).toUpperCase();
            if (red.length() < 2) red = '0'+red;
            String green = Integer.toHexString(g).toUpperCase();
            if (green.length() < 2) green = '0'+green;
            String blue = Integer.toHexString(b).toUpperCase();
            if (blue.length() < 2) blue = '0'+blue;
            return String.format("{%d%d%d%s%s%s}", x, y,z,red,green,blue);
        }
    }

    }
