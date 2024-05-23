package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Sphere class.
 */
public class SphereTests {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)(geometries.Sphere)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // There is a simple single test here - using a sphere
        Sphere sphere = new Sphere(1d, new Point(0, 0, 0));
        // TC01: ensure there are no exceptions
        assertDoesNotThrow(() -> sphere.getNormal(new Point(0, 0, 1)), "Failed test: exception thrown");
        // TC02: generate the test result
        Vector result = sphere.getNormal(new Point(0, 0, 1));
        // TC03: Make sure |result| = 1
        assertEquals(1,
                result.length(),
                DELTA,
                "Sphere's normal is not a unit vector");
        //TC04: Additional checks for normal direction
        assertEquals(new Vector(0, 0, 1),
                result,
                "Sphere's normal at (0,0,1) is incorrect");

    }

    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, p100);
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
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p01)))
                .toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
        final var exp2 =List.of(new Point(1.5075,0.8075,0.3));
        final var result2 = sphere.findIntersections(new Ray(new Point(1.2, 0.5, 0.3), v110));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(exp, result2, "Wrong intersection point");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1,0.5,0), new Vector(-10,0,0))), "Ray's line out of sphere");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        final var exp3 =List.of(new Point(1,1,1));
        final var result3 = sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1,1,1)));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(exp, result3, "Wrong intersection point");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1,1,0), new Vector(-15,5,-1))), "Ray's line out of sphere");


        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        final var exp4 =List.of(new Point(2,0,0),new Point(0,0,0));
        final var result4 = sphere.findIntersections(new Ray(new Point(3,0,0), new Vector(-1,0,0)))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(new Point(2,0,0))))
                .toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");
        // TC14: Ray starts at sphere and goes inside (1 point)


        // TC15: Ray starts inside (1 point)

        // TC16: Ray starts at the center (1 point)

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
