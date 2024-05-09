package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{

    private double height;


    public Cylinder(double radius, Ray axis, double height) {
        super(radius,axis);
        this.height=height;
    }

    public Vector getNormal(Point p){
        return null;
    }




}
