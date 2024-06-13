package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
        Point o = p0.add(dir.scale(t));
        return p.subtract(o).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        // Initialize intersections list
        List<GeoPoint> intersections = new LinkedList<>();

        // Find intersections with the infinite cylinder
        Tube tube = new Tube(radius, axis);
        List<GeoPoint> infiniteCylinderIntersections = tube.findGeoIntersections(ray);
        if (infiniteCylinderIntersections != null) {
            intersections.addAll(infiniteCylinderIntersections);
        }

        // Remove intersections outside the cylinder height
        Iterator<GeoPoint> iterator = intersections.iterator();
        while (iterator.hasNext()) {
            GeoPoint intersection = iterator.next();
            double t = axis.getDir().dotProduct(intersection.point.subtract(axis.getPoint(0)));
            if (t <= 0 || t >= height) {
                iterator.remove();
            }
        }

        // Define planes for the bottom and top bases
        Plane bottomBase = new Plane(axis.getPoint(0), axis.getDir());
        Plane topBase = new Plane(axis.getPoint(height), axis.getDir());

        // Return intersections if there are exactly 2 (so they are on the sides of the cylinder)
        if(intersections.size() == 2)
            return intersections;

        // Find intersections with the bottom base
        List<Point> bottomBaseIntersections = bottomBase.findIntersections(ray);
        if (bottomBaseIntersections != null) {
            Point intersection = bottomBaseIntersections.getFirst();
            if (axis.getPoint(0).distanceSquared(intersection) <= radius * radius) {
                intersections.add(new GeoPoint(this,intersection));
            }
        }

        // Find intersections with the top base
        List<Point> topBaseIntersections = topBase.findIntersections(ray);
        if (topBaseIntersections != null) {
            Point intersection = topBaseIntersections.getFirst();
            if (axis.getPoint(height).distanceSquared(intersection) <= radius * radius) {
                intersections.add(new GeoPoint(this,intersection));
            }
        }

        // if the ray is tangent to the cylinder- it means no intersections
        if(intersections.size() == 2 && axis.getPoint(0).distanceSquared(intersections.get(0).point) == radius* radius &&
                axis.getPoint(height).distanceSquared(intersections.get(1).point) == radius * radius){
            Vector v = intersections.get(1).point.subtract(intersections.get(0).point);
            if(v.normalize().equals(axis.getDir()) || v.normalize().equals(axis.getDir().scale(-1)))
                return null;
        }

        // Return null if no valid intersections found
        return intersections.isEmpty() ? null : intersections;
    }




}