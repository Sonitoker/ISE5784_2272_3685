package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * The Triangle class represents a triangle geometry in three-dimensional space.
 * A triangle is a polygon with three edges and three vertices.
 * This class extends the Polygon class.
 */
public class Triangle extends Polygon{
    public Triangle(Point... vertices) {
        super(vertices);
    }



    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
