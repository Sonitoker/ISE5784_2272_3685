package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Tube class.
 */
class TubeTests {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test method for {@link geometries.Tube#getNormal(Point)(geometries.Tube)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        Tube tube= new Tube(1, new Ray(new Point(0,0,0), new Vector(0,0,1)));
        // TC01: ensure there are no exceptions
        assertDoesNotThrow(() -> tube.getNormal(new Point(1, 0, 2)), "Failed test: exception thrown");
        // generate the test result
        Vector result = tube.getNormal(new Point(1, 0, 2));
        // TC02: ensure |result| = 1
        assertEquals(1,
                result.length(),
                DELTA,
                "tube's normal is not a unit vector");
        //TC03: check it is the right normal
        assertEquals((new Vector(1, 0, 0)),
                result,
                "tube's normal at (1,1,1) is incorrect");


        // =============== Boundary Values Tests ==================
        //TC04: Test normal at a point where the direction of the ray is orthogonal to the vector from the point to the ray's origin

        // The point is (1, 0, 0), orthogonal to the ray's direction (0, 0, 1)
        assertDoesNotThrow(() -> tube.getNormal(new Point(1, 0, 0)), "Failed test: exception thrown");
        // generate the test result
        result = tube.getNormal(new Point(1, 0, 0));
        // TC02: ensure |result| = 1
        assertEquals(1,
                result.length(),
                DELTA,
                "tube's normal is not a unit vector");
        //TC03: check it is the right normal
        assertEquals((new Vector(1, 0, 0)),
                result,
                "tube's normal at (1,1,1) is incorrect");

    }
}