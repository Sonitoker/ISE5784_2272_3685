package primitives;

import geometries.Polygon;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Vector class.
 */
class VectorTests {


    private static final double DELTA = 0.0000001;

    /**
     * Test method for Vector constructor
     */

    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: constructing a vector
        assertDoesNotThrow(() -> new Vector(1, 2, 3),
                "Failed constructing a correct vector");

        // =============== Boundary Values Tests ==================
        // TC02: constructing a zero vector
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(0, 0, 0),
                "ERROR: zero vector does not throw an exception");
    }


    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {

        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 0, 1);
        Vector v3 = new Vector(1, 2, 4);
        Vector vOpposite1 = new Vector(-1, -2, -3);

        // ============ Equivalence Partitions Tests ==============
        //TC01:adding a vector to a vector
        assertEquals(v3,
                v2.add(v1),
                "ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC02: adding a vector to an opposite vector so the result is ZERO
        assertThrows(IllegalArgumentException.class,
                () -> v1.add(vOpposite1),
                "ERROR: Vector + -itself does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#subtract(Point)(primitives.Vector)}.
     */
    @Test
    void testSubtract() {

        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 0, 1);
        Vector v3 = new Vector(1, 2, 2);

        // ============ Equivalence Partitions Tests ==============
        //TC01:subtracting a vector to a vector
        assertEquals(v3,
                v1.subtract(v2),
                "ERROR: Vector - Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC02: subtracting a vector from itself
        assertThrows(IllegalArgumentException.class,
                () -> v1.subtract(v1),
                "ERROR: Vector - itself does not throw an exception");
    }


    /**
     * Test method for {@link primitives.Vector#scale(double)(primitives.Vector)}.
     */
    @Test
    void testScale() {
        Vector v1 = new Vector(1, 1, 1);
        // ============ Equivalence Partitions Tests ==============
        //TC01: dot product between 2 vectors
        assertEquals(new Vector(2, 2, 2),
                v1.scale(2),
                "ERROR: multiplying a vector by a double does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC02 testing for 0*v1 throw an exception
        assertThrows(IllegalArgumentException.class,
                () -> v1.scale(0),
                "ERROR: multiplying a vector by zero does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(2, 0, 0);
        Vector v3 = new Vector(0, 2, 0);
        // ============ Equivalence Partitions Tests ==============
        //TC01: dot product between 2 vectors
        assertEquals(2,
                v1.dotProduct(v2),
                DELTA,
                "ERROR: dotProduct() wrong value");

        // =============== Boundary Values Tests ==================
        //TC02: dot product between 2  orthogonal vectors
        assertEquals(0,
                v3.dotProduct(v2),
                DELTA,
                "ERROR: dotProduct() for orthogonal vectors is not zero");

    }


    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v123 = new Vector(0, 0, 1);
        Vector v03M2 = new Vector(0, 1, 0);
        Vector vr = v123.crossProduct(v03M2);
        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v123.length() * v03M2.length(), vr.length(), DELTA, "crossProduct() wrong result length");
        // TC02: Test cross-product result orthogonality to its operands
        assertEquals(0, vr.dotProduct(v123), "crossProduct() result is not orthogonal to 1st operand");
        assertEquals(0, vr.dotProduct(v03M2), "crossProduct() result is not orthogonal to 2nd operand");
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of parallel vectors
        Vector vM2M4M6 = new Vector(0, 0, 2);
        assertThrows(IllegalArgumentException.class, () -> v123.crossProduct(vM2M4M6), //
                "crossProduct() for parallel vectors does not throw an exception");

    }


    /**
     * Test method for {@link primitives.Vector#length()(primitives.Vector)}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:vector length
        Vector vec1 = new Vector(0, 0, 2);
        assertEquals(2,
                vec1.length(),
                DELTA,
                "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()(primitives.Vector)}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:vector length
        Vector vec1 = new Vector(0, 0, 2);
        assertEquals(4,
                vec1.lengthSquared(),
                DELTA,
                "ERROR: lengthSquared() wrong value");
    }


    /**
     * Test method for {@link primitives.Vector#normalize()(primitives.Vector)}.
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(0, 3, 4);
        Vector n = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(1d,
                n.lengthSquared(),
                DELTA,
                "ERROR: the normalized vector is not a unit vector");
        assertThrows(IllegalArgumentException.class,
                () -> v.crossProduct(n),
                "ERROR: the normalized vector is not parallel to the original one");
        assertEquals(new Vector(0, 0.6, 0.8),
                n,
                "wrong normalized vector");
    }

}