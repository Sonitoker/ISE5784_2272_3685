package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTests {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a plane
        Point[] pts =
                {new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0)};
        Plane plane= new Plane(new Point(1,0,0),new Point(0,1,0), new Point(0,0,1));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = plane.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 2; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1])),
                    DELTA,
                    "Planes's normal is not orthogonal to one of the edges");
    }

}


