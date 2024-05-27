
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Plane class represents a plane geometry in 3D space.
 * It implements the Geometry interface.
 */
public class Plane implements Geometry {

    /**
     * A point on the plane.
     */
    private final Point q;

    /**
     * The normal vector to the plane.
     */
    private final Vector normal;

    /**
     * Constructs a Plane object from three points on the plane.
     *
     * @param a The first point on the plane.
     * @param b The second point on the plane.
     * @param c The third point on the plane.
     */
    public Plane(Point a, Point b, Point c) {
        q = a;

        Vector vec1 = a.subtract(b);
        Vector vec2 = a.subtract(c);

        normal = vec1.crossProduct(vec2).normalize();
    }

    /**
     * Constructs a Plane object from a point on the plane and its normal vector.
     *
     * @param q      A point on the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }


    @Override
    public Vector getNormal(Point q) {
        return normal;
    }


    public Point getQ() {
        return q;
    }

    /**
     * Returns the normal vector to the plane.
     *
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {

        if (q.equals(ray.getHead())) { // if the ray starts from the plane it doesn't cut the plane at all
            return null;
        }
        double nv = normal.dotProduct(ray.getDir());
        if (isZero(nv)) {
            return null;
        }
        double t = alignZero(normal.dotProduct(q.subtract(ray.getHead())) / nv);
        if (t > 0) {
            return List.of(ray.getPoint(t));
        } else {
            return null;

        }
    }
}
