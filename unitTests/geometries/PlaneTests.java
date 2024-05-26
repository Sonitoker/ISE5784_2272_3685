package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Plane class.
 */
class PlaneTests {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test method for {@link geometries.Plane#getNormal(Point)(geometries.Plane)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // There is a simple single test here - using a plane
        Point[] pts =
                {new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0)};
        Plane plane= new Plane(new Point(1,0,0),new Point(0,1,0), new Point(0,0,1));
        // TC01: ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(new Point(0, 0, 1)), "");
        // TC02: Generate the test result
        Vector result = plane.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Plane's normal is not a unit vector");
        // TC03: ensure the result is orthogonal to all the edges
        for (int i = 0; i < 2; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1])),
                    DELTA,
                    "Planes's normal is not orthogonal to one of the edges");
        // TC04: Also check the opposite direction of the normal vector
        Vector oppositeResult = result.scale(-1);
        for (int i = 0; i < 2; ++i) {
            assertEquals(0d, oppositeResult.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1])),
                    DELTA,
                    "Plane's normal in the opposite direction is not orthogonal to one of the edges");
        }
    }

    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct plane
        assertDoesNotThrow(() -> new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)),
                "Failed constructing a correct plane");

        // =============== Boundary Values Tests ==================

        // TC02: First and second point are the same
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0)),
                "Constructed a plane with two identical points");

        // TC03: Points are collinear
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0, 0, 0), new Point(1, 1, 1), new Point(2, 2, 2)),
                "Constructed a plane with collinear points");
    }

    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);
    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        // ================ EP: The Ray must be neither orthogonal nor parallel to the plane ==================
        Plane plane = new Plane(new Point(1,0,1),
                new Point(0,1,1),
                new Point(1,1,1));
        //TC01: Ray intersects the plane
        assertEquals(List.of(new Point(1,0.5,1)),
                plane.findIntersections(new Ray(new Point(0,0.5,0),
                        new Vector(1,0,1))),
                "Ray does not intersects the plane");

        //TC02: Ray does not intersect the plane
        assertNull(plane.findIntersections(new Ray(new Point(1,0.5,2),
                        new Vector(1,2,5))),
                "Ray intersects the plane");


        // ====================== Boundary Values Tests =======================//
        // **** Group: Ray is parallel to the plane
        //TC10: The ray included in the plane
        assertNull(plane.findIntersections(new Ray(new Point(1,2,1), new Vector(1,0,0))),
                "Does not return null- when ray included in the plane");

        //TC11: The ray not included in the plane
        assertNull(plane.findIntersections(new Ray(new Point(1,2,2),
                        new Vector(1,0,0))),
                "Does not return null- when ray not included in the plane");

        // **** Group: Ray is orthogonal to the plane
        //TC12: before the plane
        assertEquals(List.of(new Point(1,1,1)),
                plane.findIntersections(new Ray(new Point(1,1,0),
                        new Vector(0,0,1))),
                "Ray is orthogonal to the plane, before the plane");

        //TC13: on the plane
        assertNull(plane.findIntersections(new Ray(new Point(1,2,1),
                        new Vector(0,0,1))),
                "Does not return null- when ray is orthogonal to the plane, on the plane");

        //TC14: after the plane
        assertNull(plane.findIntersections(new Ray(new Point(1,2,2),
                        new Vector(0,0,1))),
                "Does not return null- when ray is orthogonal to the plane, after the plane");

        // **** Group: Ray is neither orthogonal nor parallel to
        //TC15: Ray begins at the plane
        assertNull(plane.findIntersections(new Ray(new Point(2,4,1),
                        new Vector(2,3,5))),
                "Does not return null- when ray is neither orthogonal nor parallel to ray and begin at the plane");

        //TC16: Ray begins in the same point which appears as reference point in the plane
        assertNull(plane.findIntersections(new Ray(new Point(1,0,1), new Vector(2,3,5))),
                "Does not return null- when ray begins in the same point which appears as reference point in the plane");
    }
}


