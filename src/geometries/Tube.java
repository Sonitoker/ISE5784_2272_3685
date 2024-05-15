
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * The Tube class represents a tube geometry in three-dimensional space.
 * A tube is defined by its radius and axis (a ray representing its center line).
 * This class extends the RadialGeometry class.
 */
public class Tube extends RadialGeometry {

    /**
     * The axis of the tube, represented by a ray.
     */
    protected final Ray axis;

    /**
     * Constructs a new Tube object with the specified radius and axis.
     *
     * @param radius The radius of the tube.
     * @param axis   The axis of the tube.
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    /**
     * Calculates the normal vector to the tube at the specified point.
     *
     * @param p The point on the surface of the tube.
     * @return The normal vector to the tube at the specified point, which is null.
     */
    public Vector getNormal(Point p) {
        return null;
    }
}