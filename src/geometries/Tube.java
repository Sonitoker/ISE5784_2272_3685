
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

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

            // Calculate the vector from the base point of the axis to the given point
            Vector vectorFromAxisStart = p.subtract(axis.getHead());

            // Project the above vector on the axis direction to find the projection point on the axis
            double t = axis.getDir().dotProduct(vectorFromAxisStart);
            Point o;
            if(isZero(t))
                o=axis.getHead();
            else
                o = axis.getHead().add(axis.getDir().scale(t));

            // Calculate the normal vector by subtracting the projection point from the given point
            Vector normal = p.subtract(o).normalize();

            return normal;


    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}