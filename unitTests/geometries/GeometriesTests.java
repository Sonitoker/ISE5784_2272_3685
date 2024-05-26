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
        // TC01: empty geometries list
        Geometries geometries = new Geometries();
        assertNull(geometries.findIntersections(null), "empty geometries list");

        // TC02: no geometry is intersected
        geometries.add(new Sphere(1, new Point(1, 0, 0)),
                new Sphere(1, new Point(0, 1, 0)),
                new Sphere(1, new Point(0, 0, 1)));
        assertNull(geometries.findIntersections(new Ray(new Point(-1, -1, -1), new Vector(1, 1, 1))),
                "no geometry is intersected");

        // TC03: one geometry is intersected
        Ray ray = new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0));
        List<Point> result = geometries.findIntersections(ray);
        assertEquals(1, result.size(), "one geometry is intersected");


        // TC04: all geometries are intersected
        geometries.add(new Sphere(1, new Point(0, 0, 0)));
        result = geometries.findIntersections(ray);
        assertEquals(2, result.size(), "all geometries are intersected");

        // =============== Boundary Values Tests ==================
        // TC11: empty geometries list
        geometries = new Geometries();
        assertNull(geometries.findIntersections(ray), "empty geometries list");

        // TC12: no geometry is intersected
        geometries.add(new Sphere(1, new Point(1, 0, 0)),
                new Sphere(1, new Point(0, 1, 0)),
                new Sphere(1, new Point(0, 0, 1)));
        assertNull(geometries.findIntersections(ray), "no geometry is intersected");

        // TC13: one geometry is intersected
        geometries.add(new Sphere(1, new Point(0, 0, 0)));

    }

    }