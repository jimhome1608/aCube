package jimhome.acube;

import android.graphics.Point;


public class Point3D extends Point {
    public int z;

    public Point3D(int x, int y, int z) {
        super(x,y);
        this.z = z;
    }

    public void set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

}