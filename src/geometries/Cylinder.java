package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a cylinder in 3D space, extending from a given axis with a certain radius and height.
 * Inherits from the Tube class.
 */
public class Cylinder extends Tube{

    private double height; // The height of the cylinder

    /**
     * Constructs a cylinder with the given radius, axis, and height.
     * @param radius The radius of the cylinder.
     * @param axis The axis of the cylinder.
     * @param height The height of the cylinder.
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius,axis);
        this.height=height;
    }

    /**
     * Computes and returns the normal vector to the cylinder at a given point.
     * @param p The point at which to compute the normal vector.
     * @return The normal vector to the cylinder at the given point.
     */
    public Vector getNormal(Point p){
        return null;
    }
}