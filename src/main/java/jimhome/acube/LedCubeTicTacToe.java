package jimhome.acube;

/**
 * Created by jack2 on 21/03/2015.
 */
public class LedCubeTicTacToe extends LedCube {

    public Point3D winStart;
    public Point3D winEnd;
    public boolean PlayerWon = false;

    public LedCubeTicTacToe() {
        winStart = new Point3D(0, 0, 0);
        winEnd = new Point3D(0, 0, 0);
    }


    public int showWiningLine() {
        DrawLine(winStart,winEnd,"blue");
        return 0;
    }

    public boolean checkForWinInLine(Point3D p1, Point3D p2) {
        boolean _result = false;
        int countHowManySetInLine = 0;
        Led led;
        Point3D p = new Point3D(p1.x,p1.y,p1.z);
        led = getLed(p.x,p.y,p.z);
        if (led.turnedOnByUser)
            countHowManySetInLine++;
        if (p.x < p2.x) p.x++;
        if (p.x > p2.x) p.x--;
        if (p.y < p2.y) p.y++;
        if (p.y > p2.y) p.y--;
        if (p.z < p2.z) p.z++;
        if (p.z > p2.z) p.z--;
        led = getLed(p.x,p.y,p.z);
        if (led.turnedOnByUser)
            countHowManySetInLine++;
        if (p.x < p2.x) p.x++;
        if (p.x > p2.x) p.x--;
        if (p.y < p2.y) p.y++;
        if (p.y > p2.y) p.y--;
        if (p.z < p2.z) p.z++;
        if (p.z > p2.z) p.z--;
        led = getLed(p.x,p.y,p.z);
        if (led.turnedOnByUser)
            countHowManySetInLine++;
        if (p.x < p2.x) p.x++;
        if (p.x > p2.x) p.x--;
        if (p.y < p2.y) p.y++;
        if (p.y > p2.y) p.y--;
        if (p.z < p2.z) p.z++;
        if (p.z > p2.z) p.z--;
        led = getLed(p.x,p.y,p.z);
        if (led.turnedOnByUser)
            countHowManySetInLine++;
        if (countHowManySetInLine == 4)
            _result = true;
        return  _result;
    }

    public boolean checkForWin() {
        if (PlayerWon)
            return true;
        int x = 0;
        int y = 0;
        int z = 0;
        int countHowManySetInLine = 0;
        Led led;
        boolean _result = false;
        //check all X  lines
        for (z=0;z< 4;z++) {
            for (y = 0; y < 4; y++) {
                winStart.set(0,y,z);
                winEnd.set(3,y,z);
                if (checkForWinInLine(winStart,winEnd) ) {
                    PlayerWon = true;
                    return true;
                }
            }
        }
        //check all Y  lines
        for (z=0;z<4;z++) {
            for (x = 0; x < 4; x++) {
                winStart.set(x,0,z);
                winEnd.set(x,3,z);
                if (checkForWinInLine(winStart,winEnd) ) {
                    PlayerWon = true;
                    return true;
                }
            }
        }
        //check all z  lines
        for (y=0;y<4;y++) {
            for (x = 0; x < 4; x++) {
                winStart.set(x,y,0);
                winEnd.set(x,y,3);
                if (checkForWinInLine(winStart,winEnd) ) {
                    PlayerWon = true;
                    return true;
                }
            }
        }
        //check diagnols
        winStart.set(0,0,0);
        winEnd.set(3,3,3);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(0,3,0);
        winEnd.set(3,0,3);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(3,0,0);
        winEnd.set(0,3,3);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(3,3,0);
        winEnd.set(0,0,3);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }

        // side angles 2 on each side - 6 sides including top and bottom
       // Y = 0 FRONT FACE
        winStart.set(0,0,0);
        winEnd.set(3,0,3);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(3,0,0);
        winEnd.set(0,0,3);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }
        // Y = 3 BACK FACE
        winStart.set(0,3,0);
        winEnd.set(3,3,3);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(3,3,0);
        winEnd.set(0,3,3);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }

        //x= 0 left wall
        winStart.set(0,0,0);
        winEnd.set(0,3,3);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(0,3,0);
        winEnd.set(0,0,3);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }
        //x= 3 rigth  wall
        winStart.set(3,0,0);
        winEnd.set(3,3,3);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(3,3,0);
        winEnd.set(3,0,3);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }
        // top  z= 3
        winStart.set(0,0,3);
        winEnd.set(3,3,3);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(3,0,3);
        winEnd.set(0,3,3);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }
        // bottom  z= 0
        winStart.set(0,0,0);
        winEnd.set(3,3,0);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(3,0,0);
        winEnd.set(0,3,0);
        if (checkForWinInLine(winStart,winEnd) ) {
            PlayerWon = true;
            return true;
        }
        return _result;
    }
}
