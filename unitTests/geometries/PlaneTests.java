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
        Sphere sphere = new Sphere(p100, 1d);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310))
                .stream().sorted(Comparator.comparingDouble(p) -> p.distance(p01)))
 .toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
 ...
        // TC04: Ray starts after the sphere (0 points)
 ...
        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        // TC12: Ray starts at sphere and goes outside (0 points)
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        // TC14: Ray starts at sphere and goes inside (1 points)
        // TC15: Ray starts inside (1 points)
        // TC16: Ray starts at the center (1 points)
        // TC17: Ray starts at sphere and goes outside (0 points)
        // TC18: Ray starts after sphere (0 points)
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        // TC20: Ray starts at the tangent point
        // TC21: Ray starts after the tangent point
        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
    }
}


