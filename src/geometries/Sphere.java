package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Sphere class represents a sphere geometry in three-dimensional space.
 * A sphere is defined by its radius and center point.
 * This class extends the RadialGeometry class.
 */
public class Sphere extends RadialGeometry {

    private final Point center;

    /**
     * Constructs a Sphere object with the given radius and center point.
     * @param radius The radius of the sphere.
     * @param center The center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the normal vector to the sphere at the given point.
     * @param p The point on the surface of the sphere.
     * @return The normal vector to the sphere at the given point.
     */
    public Vector getNormal(Point p) {
        if (p.equals(center)){
            throw new IllegalArgumentException("point p equals center:  not valid ");
        }
        return  p.subtract(center).normalize();
    }
}
