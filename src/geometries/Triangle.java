package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Triangle class represents a triangle geometry in three-dimensional space.
 * A triangle is a polygon with three edges and three vertices.
 * This class extends the Polygon class.
 */
public class Triangle extends Polygon {
    public Triangle(Point... vertices) {
        super(vertices);
    }


    @Override
    public List<Point> findIntersections(Ray ray) {

        List<Point> p = plane.findIntersections(ray); // Find the intersection point with the plane
        if (p == null) {
            return null;
        }

        Point intersectionPoint = p.get(0);
        // Check if the intersection point is a vertex of the triangle
        if (intersectionPoint.equals(vertices.get(0)) ||
                intersectionPoint.equals(vertices.get(1)) ||
                intersectionPoint.equals(vertices.get(2))) {
            return null;
        }
        // Check if the intersection point is inside the triangle
        if (isPointInTriangle(intersectionPoint, vertices.get(0), vertices.get(1), vertices.get(2))) {
            return p;
        }

        return null;
    }


    /**
     * Checks if a point is inside a triangle using barycentric coordinates.
     *
     * @param p  The point to check.
     * @param p1 The first vertex of the triangle.
     * @param p2 The second vertex of the triangle.
     * @param p3 The third vertex of the triangle.
     * @return true if the point is inside the triangle, false otherwise.
     */
    private boolean isPointInTriangle(Point p, Point p1, Point p2, Point p3) {
        // Calculate vectors
        Vector v0 = p2.subtract(p1);
        Vector v1 = p3.subtract(p1);
        Vector v2 = p.subtract(p1);

        // Compute dot products
        double dot00 = v0.dotProduct(v0);
        double dot01 = v0.dotProduct(v1);
        double dot02 = v0.dotProduct(v2);
        double dot11 = v1.dotProduct(v1);
        double dot12 = v1.dotProduct(v2);

        // Compute barycentric coordinates
        double invDenom = 1 / (dot00 * dot11 - dot01 * dot01);
        double u = (dot11 * dot02 - dot01 * dot12) * invDenom;
        double v = (dot00 * dot12 - dot01 * dot02) * invDenom;
        double w = 1.0 - u - v;

        // Check if point is in triangle
        if (u > 0 && v > 0 && w > 0 && u + v + w <= 1) {
            return true;
        }
        return false;
    }

}
