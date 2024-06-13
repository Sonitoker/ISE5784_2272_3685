package primitives;

import java.util.List;

import static primitives.Util.isZero;
import geometries.Intersectable.GeoPoint;

/**
 * Represents a ray in three-dimensional space, defined by a starting point (head) and a direction.
 */
public class Ray {
    final private  Point head;
    final private  Vector direction;

    /**
     * Constructs a new Ray with the specified starting point and direction.
     *
     * @param p   The starting point (head) of the ray.
     * @param vec The direction vector of the ray.
     */
    public Ray(Point p, Vector vec) {
        this.head = p;
        this.direction=vec.normalize();
    }

    /**
     * Returns the head point of the ray.
     *
     * @return the head point of the ray.
     */
    public Point getHead() {
        return head;
    }

    /**
     * Returns the direction of the ray.
     *
     * @return the direction of the ray.
     */
    public Vector getDir() {
        return direction;
    }

    @Override
    public boolean equals(Object obj){
        return (obj instanceof Ray other) && this.head.equals(other.head) && this.direction.equals(other.direction);
    }



    @Override
    public String toString() {
        return "Ray: Head = " + head + ", Direction = " + direction;
    }

    /**
     * Returns a point on the ray at a distance t from the head.
     *
     * @param t The distance from the head of the ray.
     * @return The point on the ray at the specified distance from the head.
     */
    public Point getPoint(double t){
        if(isZero(t))
           return head;
        return head.add(direction.scale(t));
    }

    /**
     * Finds the closest point to the head of the ray from a list of points.
     *
     * @param points The list of points to check.
     * @return The closest point to the head of the ray.
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }


    /**
     * Finds the closest point to the head of the ray from a list of GeoPoints.
     *
     * @param intersections The list of GeoPoints to check.
     * @return The closest point to the head of the ray.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
        GeoPoint closest = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (GeoPoint p : intersections) {
            double distance = head.distance(p.point);
            if (distance < minDistance) {
                minDistance = distance;
                closest = p;
            }
        }
        return closest;
    }


}

