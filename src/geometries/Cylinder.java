package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
        Point o = axis.getPoint(t);
        return p.subtract(o).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // Initialize intersections list
        List<Point> intersections = new LinkedList<>();

        // Find intersections with the infinite cylinder
        Tube tube = new Tube(radius, axis);
        List<Point> infiniteCylinderIntersections = tube.findIntersections(ray);
        if (infiniteCylinderIntersections != null) {
            intersections.addAll(infiniteCylinderIntersections);
        }

        // Remove intersections outside the cylinder height
        Iterator<Point> iterator = intersections.iterator();
        while (iterator.hasNext()) {
            Point intersection = iterator.next();
            double t = axis.getDir().dotProduct(intersection.subtract(axis.getPoint(0d)));
            if (t <= 0d || t >= height || alignZero(intersection.distance(ray.getPoint(0)) - maxDistance) > 0d) {
                iterator.remove();
            }
        }

        // Define planes for the bottom and top bases
        Plane bottomBase = new Plane(axis.getPoint(0d), axis.getDir());
        Plane topBase = new Plane(axis.getPoint(height), axis.getDir());

        // Return intersections if there are exactly 2 (so they are on the sides of the cylinder)
        if (intersections.size() == 2) {
            return List.of(new GeoPoint(this, intersections.get(0)), new GeoPoint(this, intersections.get(1)));
        }


        // Find intersections with the bottom base
        List<Point> bottomBaseIntersections = bottomBase.findIntersections(ray);
        if (bottomBaseIntersections != null && alignZero(bottomBaseIntersections.getFirst().distanceSquared(ray.getPoint(0)) - maxDistance) <= 0d) {
            Point intersection = bottomBaseIntersections.getFirst();
            if (axis.getPoint(0d).distanceSquared(intersection) <= radius * radius) {
                intersections.add(intersection);
            }
        }

        // Find intersections with the top base
        List<Point> topBaseIntersections = topBase.findIntersections(ray);
        if (topBaseIntersections != null && alignZero(topBaseIntersections.getFirst().distanceSquared(ray.getPoint(0)) - maxDistance) <= 0d) {
            Point intersection = topBaseIntersections.getFirst();
            if (axis.getPoint(height).distanceSquared(intersection) <= radius * radius) {
                intersections.add(intersection);
            }
        }

        // if the ray is tangent to the cylinder
        if (intersections.size() == 2 && axis.getPoint(0).distanceSquared(intersections.get(0)) == radius * radius &&
                axis.getPoint(height).distanceSquared(intersections.get(1)) == radius * radius) {
            Vector v = intersections.get(1).subtract(intersections.get(0));
            if (v.normalize().equals(axis.getDir()) || v.normalize().equals(axis.getDir().scale(-1d)))
                return null;
        }

        // Return null if no valid intersections found
        List<GeoPoint> geoPoints = new LinkedList<>();
        for (Point p : intersections) {
            geoPoints.add(new GeoPoint(this, p));
        }

        return geoPoints.isEmpty() ? null : geoPoints;
    }



}