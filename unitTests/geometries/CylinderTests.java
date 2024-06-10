package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for the Cylinder class.
 */
class CylinderTests {


    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)(geometries.Cylinder)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============


        Cylinder cylinder = new Cylinder(1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 5);

        // Test normal to a point on the lateral surface

        // TC01: ensure there are no exceptions
        assertDoesNotThrow(() -> cylinder.getNormal(new Point(1, 0, 3)), "Failed test: exception thrown");
        // generate the test result
        Vector result = cylinder.getNormal(new Point(1, 0, 3));
        // TC02: ensure |result| = 1
        assertEquals(1,
                result.length(),
                DELTA,
                "Cylinder's normal is not a unit vector");
        //TC03: check it is the right normal
        assertEquals((new Vector(1, 0, 0)),
                result,
                "Cylinder's normal at (1,1,1) is incorrect");


        // Test normal on the bottom base of the cylinder

        // TC04: ensure there are no exceptions
        assertDoesNotThrow(() -> cylinder.getNormal(new Point(0.5, 0.5, 0)), "Failed test: exception thrown");
        // generate the test result when the point is on the
        result = cylinder.getNormal(new Point(0.5, 0.5, 0));
        // TC05: ensure |result| = 1
        assertEquals(1,
                result.length(),
                DELTA,
                "Cylinder's normal is not a unit vector");
        //TC06: check it is the right normal
        assertEquals(new Vector(0, 0, -1),
                result,
                "ERROR: Cylinder's normal wrong result for bottom base");

        // Test normal on the top base of the cylinder

        // TC07: ensure there are no exceptions
        assertDoesNotThrow(() -> cylinder.getNormal(new Point(0.5, 0.5, 5)), "Failed test: exception thrown");
        // generate the test result
        result = cylinder.getNormal(new Point(0.5, 0.5, 5));
        // TC08: ensure |result| = 1
        assertEquals(1,
                result.length(),
                DELTA,
                "Cylinder's normal is not a unit vector");
        //TC09: check it is the right normal
        assertEquals(new Vector(0, 0, 1),
                result,
                "ERROR: Cylinder's normal wrong result for top base");


        // =============== Boundary Values Tests ==================

        //Test normal at the center of the bottom base of the cylinder

        // TC10: ensure there are no exceptions
        assertDoesNotThrow(() -> cylinder.getNormal(new Point(0, 0, 0)), "Failed test: exception thrown");
        // generate the test result
        result = cylinder.getNormal(new Point(0, 0, 0));
        // TC11: ensure |result| = 1
        assertEquals(1,
                result.length(),
                DELTA,
                "Cylinder's normal is not a unit vector");
        //TC12: check it is the right normal
        assertEquals(new Vector(0, 0, -1),
                result,
                "ERROR: Cylinder's normal wrong result for bottom base");

        // Test normal at the center of the top base of the cylinder
        // TC13: ensure there are no exceptions
        assertDoesNotThrow(() -> cylinder.getNormal(new Point(0, 0, 5)), "Failed test: exception thrown");
        // generate the test result
        result = cylinder.getNormal(new Point(0, 0, 5));
        // TC14: ensure |result| = 1
        assertEquals(1,
                result.length(),
                DELTA,
                "Cylinder's normal is not a unit vector");
        //TC15: check it is the right normal
        assertEquals(new Vector(0, 0, 1),
                result,
                "ERROR: Cylinder's normal wrong result for top base");


    }




    /**
     * Test method for {@link geometries.Cylinder#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Cylinder cylinder2 = new Cylinder(2, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the cylinder (0 points)
        assertNull(cylinder2.findIntersections(new Ray(new Point(0, 0, 2), new Vector(0, 0, 1))),
                "Ray's line out of cylinder");

        // TC02: Ray starts before and crosses the cylinder (2 points)
        List<Point> result = cylinder2.findIntersections(new Ray(new Point(0, 0, -1), new Vector(0, 0, 1)));
        assertEquals(2, result.size(), "Wrong number of points");

        // TC03: Ray starts inside the cylinder (1 point)
        result = cylinder2.findIntersections(new Ray(new Point(0, 0, 1), new Vector(0, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");

        // TC04: Ray starts after the cylinder (0 points)
        assertNull(cylinder2.findIntersections(new Ray(new Point(0, 0, 3), new Vector(0, 0, 1))),
                "Ray's line out of cylinder");

        // TC05: Ray starts at the cylinder and goes outside (0 points)
        assertNull(cylinder2.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 0, -1))),
                "Ray's line out of cylinder");


// TC06: Ray starts at the cylinder and goes inside (1 point)
        result = cylinder2.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");

        // TC07: Ray intersects the cylinder's top surface (1 point)
        result = cylinder2.findIntersections(new Ray(new Point(0, 0, 3), new Vector(0, 0, -1)));
        assertEquals(2, result.size(), "Wrong number of points");

        // TC10: Ray starts at the cylinder's top surface and goes inside (1 point)
        result = cylinder2.findIntersections(new Ray(new Point(0, 0, 2), new Vector(0, 0, 1)));
        assertNull(result, "Wrong number of points");

        // TC11: Ray intersects the tube but not the cylinder (0 points)
        assertNull(cylinder2.findIntersections(new Ray(new Point(0, 0, 3), new Vector(0, 1, 0))),
                "Ray's line out of cylinder");

        // TC12: Ray tangent to the cylinder's top surface (0 points)
        assertNull(cylinder2.findIntersections(new Ray(new Point(0, 0, 2), new Vector(0, 1, 0))),
                "Ray's line out of cylinder");

        // TC13: Ray tangent to the cylinder's bottom surface (0 points)
        assertNull(cylinder2.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0))),
                "Ray's line out of cylinder");

        // TC14: Ray tangent to the cylinder's side surface (0 points)
        assertNull(cylinder2.findIntersections(new Ray(new Point(0, 2, -1), new Vector(0, 0, 1))),
                "Ray's line out of cylinder");
    }

}





