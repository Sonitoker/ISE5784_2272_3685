package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * The Sphere class represents a sphere geometry in three-dimensional space.
 * A sphere is defined by its radius and center point.
 * This class extends the RadialGeometry class.
 */
public class Sphere extends RadialGeometry {

    private final Point center;

    /**
     * Constructs a Sphere object with the given radius and center point.
     *
     * @param radius The radius of the sphere.
     * @param center The center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the normal vector to the sphere at the given point.
     *
     * @param p The point on the surface of the sphere.
     * @return The normal vector to the sphere at the given point.
     */
    public Vector getNormal(Point p) {
        if (p.equals(center)) {
            throw new IllegalArgumentException("point p equals center:  not valid ");
        }
        return p.subtract(center).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point P0 = ray.getHead(); // ray's starting point
        Point O = center; //the sphere's center point
        Vector V = ray.getDir(); // "the v vector" from the presentation
        if (O.equals(P0)) {
            return List.of(new GeoPoint(this,ray.getPoint(radius)));
        }
        Vector U = O.subtract(P0);
        double tm = V.dotProduct(U);
        double d = Math.sqrt(U.lengthSquared() - tm * tm);
        if (d >= radius) { //no intersections
            return null;
        }
        double th = Math.sqrt(radius * radius - d * d);
        double t1 = tm - th;
        double t2 = tm + th;
        if (t1 > 0 && t2 > 0) {
            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this,p1), new GeoPoint(this,p2));
        } else if (t1 > 0) {
            Point p1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this,p1));
        } else if (t2 > 0) {
            Point p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this,p2));
        }
        return null;

    }
}
