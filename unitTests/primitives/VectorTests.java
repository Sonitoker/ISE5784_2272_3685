package primitives;

import geometries.Polygon;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class VectorTests {


    private static final double DELTA = 0.0000001;

    /**
     * Test method for Vector co
     * {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */

    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: constructing a vector
        assertDoesNotThrow(() -> new Vector(1, 2, 3), "Failed constructing a correct vector");

        // =============== Boundary Values Tests ==================
        // TC02: constructing a zero vector
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(0, 0, 0),
                "ERROR: zero vector does not throw an exception");
    }
    @Test
    void testAdd() {
    }

    @Test
    void testScale() {
    }

    @Test
    void testDotProduct() {
    }

    @Disabled
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v123 = new Vector(0, 0, 1);
        Vector v03M2 = new Vector(0,1,0);
        Vector vr = v123.crossProduct(v03M2);
        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v123.length() * v03M2.length(), vr.length(), DELTA, "crossProduct() wrong result length");
        // TC02: Test cross-product result orthogonality to its operands
        assertEquals(0, vr.dotProduct(v123), "crossProduct() result is not orthogonal to 1st operand");
        assertEquals(0, vr.dotProduct(v03M2), "crossProduct() result is not orthogonal to 2nd operand");
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of parallel vectors
        Vector vM2M4M6= new Vector(0,0,2);
        assertThrows(IllegalArgumentException.class, () -> v123.crossProduct(vM2M4M6), //
                "crossProduct() for parallel vectors does not throw an exception");

    }

    @Test
    void testLengthSquared() {
    }

    @Test
    void testLength() {
    }

    @Test
    void testNormalize() {
        Vector v = new Vector(0, 3, 4);
        Vector n = v.normalize();
// ============ Equivalence Partitions Tests ==============
// TC01: Simple test
        assertEquals(1d, n.lengthSquared(), 0.00001, "wrong normalized vector length");
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(n),"normalized vector is not in the same direction");
        assertEquals(new Vector(0, 0.6, 0.8), n, "wrong normalized vector");
    }

}