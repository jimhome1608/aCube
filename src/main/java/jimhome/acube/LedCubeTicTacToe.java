    package jimhome.acube;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

    /**
 * Created by jack2 on 21/03/2015.
 */
public class LedCubeTicTacToe extends LedCube {

    public Point3D winStart;
    public Point3D winEnd;
    public boolean needDefensiveMove = false;
    public boolean foundNextMove = false;
    public boolean canWin = false;
    public boolean PlayerWon = false;
    public boolean MachineWon = false;
    public Point3D winStartMachine;
    public Point3D winEndMachine;

    int BestLineSoFar = 0;

    public boolean GameOver() {
        if (PlayerWon)
          return true;
        if (MachineWon)
            return true;
        return false;
    }

    public LedCubeTicTacToe() {
        winStart = new Point3D(0, 0, 0);
        winEnd = new Point3D(0, 0, 0);
        winStartMachine = new Point3D(0, 0, 0);
        winEndMachine = new Point3D(0, 0, 0);
    }


    public int showWiningLine() {
        DrawLine(winStart,winEnd,"blue");
        return 0;
    }

    public int showMachineWiningLine() {
        DrawLine(winStartMachine,winEndMachine,"red");
        return 0;
    }

    public boolean findRandomMachineMove() {
        Led led;
        Random rn = new Random();
        for (int x=0;x<16;x++) {
            for (int y=0;y<16;y++) {
                for (int z=0; z<16; z++) {
                    led = getLed(rn.nextInt(4),rn.nextInt(4),rn.nextInt(4));
                    if (led.turnedOnByUser)
                        continue;
                    if (led.turnedOnByMacine)
                        continue;
                    nextMachineMove.set(led.x,led.y,led.z);
                    return true;
                }
            }
        }
        return false;
    }

    public int checkForWinInLine(Point3D p1, Point3D p2) {
        int countHowManySetInLine = 0;
        //Log.v("outStream" + Integer.toString(i), Character.toString((char) (Integer.parseInt(Byte.toString(income.getBytes()[i])))));
        ArrayList<Led>  leds = new ArrayList<Led>();
        Led led;
        Point3D p = new Point3D(p1.x,p1.y,p1.z);
        led = getLed(p.x,p.y,p.z);
        leds.add(led);
        if (led.turnedOnByUser)
            countHowManySetInLine++;
        if (p.x < p2.x) p.x++;
        if (p.x > p2.x) p.x--;
        if (p.y < p2.y) p.y++;
        if (p.y > p2.y) p.y--;
        if (p.z < p2.z) p.z++;
        if (p.z > p2.z) p.z--;
        led = getLed(p.x,p.y,p.z);
        leds.add(led);
        if (led.turnedOnByUser)
            countHowManySetInLine++;
        if (p.x < p2.x) p.x++;
        if (p.x > p2.x) p.x--;
        if (p.y < p2.y) p.y++;
        if (p.y > p2.y) p.y--;
        if (p.z < p2.z) p.z++;
        if (p.z > p2.z) p.z--;
        led = getLed(p.x,p.y,p.z);
        leds.add(led);
        if (led.turnedOnByUser)
            countHowManySetInLine++;
        if (p.x < p2.x) p.x++;
        if (p.x > p2.x) p.x--;
        if (p.y < p2.y) p.y++;
        if (p.y > p2.y) p.y--;
        if (p.z < p2.z) p.z++;
        if (p.z > p2.z) p.z--;
        led = getLed(p.x,p.y,p.z);
        leds.add(led);
        if (led.turnedOnByUser)
            countHowManySetInLine++;
        if (countHowManySetInLine == 3 ) {
            for (int i=0;i<4;i++) {
                led = (Led) leds.get(i);
                if (led.turnedOnByMacine || led.turnedOnByUser)
                    continue;
                nextMachineMove.set(led.x, led.y, led.z);
                needDefensiveMove = true;
            }
        }
        return  countHowManySetInLine;
    }

    public boolean checkForWin() {
        if (PlayerWon)
            return true;
        needDefensiveMove = false;
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
                countHowManySetInLine = checkForWinInLine(winStart,winEnd);
                if (countHowManySetInLine == 4 ) {
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
                countHowManySetInLine = checkForWinInLine(winStart,winEnd);
                if (countHowManySetInLine == 4 ) {
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
                countHowManySetInLine = checkForWinInLine(winStart,winEnd);
                if (countHowManySetInLine == 4 ) {
                    PlayerWon = true;
                    return true;
                }
            }
        }
        //check diagnols
        winStart.set(0,0,0);
        winEnd.set(3,3,3);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(0,3,0);
        winEnd.set(3,0,3);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(3,0,0);
        winEnd.set(0,3,3);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(3,3,0);
        winEnd.set(0,0,3);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }

        // side angles 2 on each side - 6 sides including top and bottom
       // Y = 0 FRONT FACE
        winStart.set(0,0,0);
        winEnd.set(3,0,3);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(3,0,0);
        winEnd.set(0,0,3);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }
        // Y = 3 BACK FACE
        winStart.set(0,3,0);
        winEnd.set(3,3,3);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(3,3,0);
        winEnd.set(0,3,3);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }

        //x= 0 left wall
        winStart.set(0,0,0);
        winEnd.set(0,3,3);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(0,3,0);
        winEnd.set(0,0,3);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }
        //x= 3 rigth  wall
        winStart.set(3,0,0);
        winEnd.set(3,3,3);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(3,3,0);
        winEnd.set(3,0,3);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }
        // top  z= 3
        winStart.set(0,0,3);
        winEnd.set(3,3,3);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(3,0,3);
        winEnd.set(0,3,3);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }
        // bottom  z= 0
        winStart.set(0,0,0);
        winEnd.set(3,3,0);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }
        winStart.set(3,0,0);
        winEnd.set(0,3,0);
        countHowManySetInLine = checkForWinInLine(winStart,winEnd);
        if (countHowManySetInLine == 4 ) {
            PlayerWon = true;
            return true;
        }
        return _result;
    }

        public boolean checkForMachineWin() {
            if (MachineWon)
                return true;
            int x = 0;
            int y = 0;
            int z = 0;
            int countHowManySetInLine = 0;
            Led led;
            boolean _result = false;
            BestLineSoFar = 0;
            foundNextMove = false;
            canWin = false;
            //check all X  lines
            for (z=0;z< 4;z++) {
                for (y = 0; y < 4; y++) {
                    winStartMachine.set(0,y,z);
                    winEndMachine.set(3,y,z);
                    countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
                    if (countHowManySetInLine == 4 ) {
                        MachineWon = true;
                        return true;
                    }
                }
            }
            //check all Y  lines
            for (z=0;z<4;z++) {
                for (x = 0; x < 4; x++) {
                    winStartMachine.set(x,0,z);
                    winEndMachine.set(x,3,z);
                    countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
                    if (countHowManySetInLine == 4 ) {
                        MachineWon = true;
                        return true;
                    }
                }
            }
            //check all z  lines
            for (y=0;y<4;y++) {
                for (x = 0; x < 4; x++) {
                    winStartMachine.set(x,y,0);
                    winEndMachine.set(x,y,3);
                    countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
                    if (countHowManySetInLine == 4 ) {
                        MachineWon = true;
                        return true;
                    }
                }
            }
            //check diagnols
            winStartMachine.set(0,0,0);
            winEndMachine.set(3,3,3);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }
            winStartMachine.set(0,3,0);
            winEndMachine.set(3,0,3);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }
            winStartMachine.set(3,0,0);
            winEndMachine.set(0,3,3);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }
            winStartMachine.set(3,3,0);
            winEndMachine.set(0,0,3);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }

            // side angles 2 on each side - 6 sides including top and bottom
            // Y = 0 FRONT FACE
            winStartMachine.set(0,0,0);
            winEndMachine.set(3,0,3);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }
            winStartMachine.set(3,0,0);
            winEndMachine.set(0,0,3);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }
            // Y = 3 BACK FACE
            winStartMachine.set(0,3,0);
            winEndMachine.set(3,3,3);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }
            winStartMachine.set(3,3,0);
            winEndMachine.set(0,3,3);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }

            //x= 0 left wall
            winStartMachine.set(0,0,0);
            winEndMachine.set(0,3,3);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }
            winStartMachine.set(0,3,0);
            winEndMachine.set(0,0,3);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }
            //x= 3 rigth  wall
            winStartMachine.set(3,0,0);
            winEndMachine.set(3,3,3);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }
            winStartMachine.set(3,3,0);
            winEndMachine.set(3,0,3);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }
            // top  z= 3
            winStartMachine.set(0,0,3);
            winEndMachine.set(3,3,3);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }
            winStartMachine.set(3,0,3);
            winEndMachine.set(0,3,3);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }
            // bottom  z= 0
            winStartMachine.set(0,0,0);
            winEndMachine.set(3,3,0);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }
            winStartMachine.set(3,0,0);
            winEndMachine.set(0,3,0);
            countHowManySetInLine = checkMachineWinInLine(winStartMachine,winEndMachine);
            if (countHowManySetInLine == 4 ) {
                MachineWon = true;
                return true;
            }
            return _result;
        }

        public int checkMachineWinInLine(Point3D p1, Point3D p2) {
            int countHowManySetInLine = 0;
            int countHowManUserSetInLine = 0;
            //Log.v("outStream" + Integer.toString(i), Character.toString((char) (Integer.parseInt(Byte.toString(income.getBytes()[i])))));
            ArrayList<Led>  leds = new ArrayList<Led>();
            Led led;
            Point3D p = new Point3D(p1.x,p1.y,p1.z);
            led = getLed(p.x,p.y,p.z);
            leds.add(led);
            if (led.turnedOnByMacine)
                countHowManySetInLine++;
            if (led.turnedOnByUser)
                countHowManUserSetInLine++;
            if (p.x < p2.x) p.x++;
            if (p.x > p2.x) p.x--;
            if (p.y < p2.y) p.y++;
            if (p.y > p2.y) p.y--;
            if (p.z < p2.z) p.z++;
            if (p.z > p2.z) p.z--;
            led = getLed(p.x,p.y,p.z);
            leds.add(led);
            if (led.turnedOnByMacine)
                countHowManySetInLine++;
            if (led.turnedOnByUser)
                countHowManUserSetInLine++;
            if (p.x < p2.x) p.x++;
            if (p.x > p2.x) p.x--;
            if (p.y < p2.y) p.y++;
            if (p.y > p2.y) p.y--;
            if (p.z < p2.z) p.z++;
            if (p.z > p2.z) p.z--;
            led = getLed(p.x,p.y,p.z);
            leds.add(led);
            if (led.turnedOnByMacine)
                countHowManySetInLine++;
            if (led.turnedOnByUser)
                countHowManUserSetInLine++;
            if (p.x < p2.x) p.x++;
            if (p.x > p2.x) p.x--;
            if (p.y < p2.y) p.y++;
            if (p.y > p2.y) p.y--;
            if (p.z < p2.z) p.z++;
            if (p.z > p2.z) p.z--;
            led = getLed(p.x,p.y,p.z);
            leds.add(led);
            if (led.turnedOnByMacine)
                countHowManySetInLine++;
            if (led.turnedOnByUser)
                countHowManUserSetInLine++;
            if (countHowManUserSetInLine == 0) {
                if (countHowManySetInLine > BestLineSoFar) {
                    BestLineSoFar = countHowManySetInLine;
                    for (int i=0;i<4;i++) {
                        led = (Led) leds.get(i);
                        if (led.turnedOnByMacine || led.turnedOnByUser)
                            continue;
                        nextMachineMove.set(led.x, led.y, led.z);
                        foundNextMove = true;
                        if (countHowManySetInLine == 3)
                            canWin = true;
                    }
                }
            }

            return  countHowManySetInLine;
        }


    }
