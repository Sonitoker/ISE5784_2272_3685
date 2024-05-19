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
        Point p0 = axis.getHead();
        Vector dir = axis.getDir();

        // Vector from the base point to the given point
        if(p.equals(p0))
            return dir.scale(-1).normalize();

        Vector vectorFromP0 = p.subtract(p0);

        // Project the point onto the cylinder's axis
        double t = dir.dotProduct(vectorFromP0);

        // Check if the point is on the bottom base
        if (t <= 0) {
            return dir.scale(-1).normalize(); // normal is the opposite direction of the cylinder's axis direction
        }

        // Check if the point is on the top base
        if (t >= height) {
            return dir.normalize(); // normal is the direction of the cylinder's axis direction
        }

        // The point is on the lateral surface
        Point o = p0.add(dir.scale(t));
        return p.subtract(o).normalize();
    }
}