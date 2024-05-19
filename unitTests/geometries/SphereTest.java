package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SphereTest {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a sphere
        Sphere sphere= new Sphere(1d, new Point(0,0,0));
        // TC02: ensure there are no exceptions
        assertDoesNotThrow(() -> sphere.getNormal(new Point(0, 0, 1)), "Failed test: exception thrown");
        // generate the test result
        Vector result = sphere.getNormal(new Point(0, 0, 1));
        // TC03: rensure |result| = 1
        assertEquals(1,
                result.length(),
                DELTA,
                "Sphere's normal is not a unit vector");
        //TC04: Additional checks for normal direction
        assertEquals(new Vector(0, 0, 1),
                result,
                "Sphere's normal at (0,0,1) is incorrect");

    }
}
