package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {

    @Test
    void testFindIntersection() {
        // ============ Equivalence Partitions Tests ==============
        //Tc01: some geometries are intersected
        Geometries geometries1 = new Geometries(new Sphere(1, new Point(1, 0, 0)),
               new Sphere(1, new Point(0, 1, 0)),
                new Sphere(1, new Point(3, 3, 3)),
                new Sphere(1, new Point(0, 0, 0)));
        Ray ray = new Ray(new Point(0.5, -1, 0), new Vector(0.5, 1, 0));
        List<Point> result = geometries1.findIntersections(ray);
        assertEquals(2, result.size(), "some geometries are intersected");



        // =============== Boundary Values Tests ==================
        // TC02: empty geometries list
        Geometries geometries = new Geometries();
        assertNull(geometries.findIntersections(null), "empty geometries list");

        // TC03: no geometry is intersected
        Plane plane = new Plane(new Point(1, 0, 0), new Point(2, 0, 0), new Point(1.5, 0, 1));
        Sphere sphere = new Sphere(1, new Point(1, 0, 1));
        Triangle triangle = new Triangle(new Point(0, 2, 0), new Point(2, 2, 0), new Point(1.5, 2, 2));
        Geometries geometries2 = new Geometries(plane, sphere, triangle);
        Ray rayNoIntersections = new Ray(new Point(1, -1, 1), new Vector(0, -1, 0));

        assertNull(geometries.findIntersections(rayNoIntersections), "The ray suppose not intersect the objects");



        // TC04: one geometry is intersected
        Ray rayOneObjectIntersect = new Ray(new Point(1.5, 1.5, 0.5), new Vector(0, 1, 0));
        assertEquals(1, geometries2.findIntersections(rayOneObjectIntersect).size(),
                "Suppose to be one intersection point (one object intersect)");


        // TC05: all geometries are intersected
        Ray rayAllObjectIntersect = new Ray(new Point(1, 2.5, 1), new Vector(0, -1, 0));
        assertEquals(4, geometries2.findIntersections(rayAllObjectIntersect).size(),
                "Suppose to be 4 intersection points");



    }

    }