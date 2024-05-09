package geometries;

import primitives.Vector;
import primitives.Point;


public class Plane implements Geometry {

   private Point q;
   private Vector normal;



    public Plane(Point a, Point b, Point c) {

        this.q=a;
        Vector vec1=a.subtract(b);
        Vector vec2=a.subtract(c);
        this.normal=vec1.crossProduct(vec2).normalize();
    }

    public Plane(Point q, Vector normal) {
        this.q=q;
        this.normal=normal.normalize();
    }

    @Override
    public Vector getNormal(Point q) {
       return normal;
    }

    public Vector getNormal() {
        return normal;
    }


}
