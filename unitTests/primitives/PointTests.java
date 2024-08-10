package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Point class
 */
public class PointTests {
    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    public PointTests() {
    }
    private static final double DELTA = 0.0000001;

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtruct() {
        // ============ Equivalence Partitions Tests ==============

        //TC01:subtracting vectors
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 0, 1);
        Vector v3 = new Vector(1, 2, 2);

        assertEquals(v3,
                v1.subtract(v2),
                "ERROR: (point2 - point1) does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC02 subtracting equal vectors
        assertThrows(IllegalArgumentException.class,
                () -> v1.subtract(v1),
                "ERROR: (point - itself) does not throw an exception");


        ;
    }

    /**
     * Test method for {@link primitives.Point#add(Vector)(primitives.Point)}.
     */
    @Test
    void testAdd() {

        Vector v1 = new Vector(1, 2, 3);
        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(1, 2, 4);
        Point p3 = new Point(-1, -2, -3);

        // ============ Equivalence Partitions Tests ==============
        //TC01:adding a vector to a point
        assertEquals(p2,
                p1.add(v1),
                "ERROR: (point + vector) = other point does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC02: adding a vector to a point so the result is ZERO
        assertEquals(Point.ZERO,
                p3.add(v1),
                "ERROR: (point + vector) = center of coordinates does not work correctly");


    }


    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void testDistance() {

        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        //TC01:distance between 2 points
        assertEquals(3,
                p1.distance(p2),
                DELTA,
                "ERROR: point distance to a different point is not correct");
        //TC02:distance between 2 points in the opposite way
        assertEquals(3,
                p2.distance(p1),
                DELTA,
                "ERROR: point distance to a different point is not correct");

        // =============== Boundary Values Tests ==================
        //TC03: distance between a point to itself
        assertEquals(0,
                p1.distance(p1),
                DELTA,
                "ERROR: point distance to itself is not zero");


    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {

        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        //TC01:squared distance between 2 points

        assertEquals(9,
                p1.distanceSquared(p2),
                DELTA,
                "ERROR: squared distance between points is wrong");

        //TC02:squared distance between 2 points in the opposite way
        assertEquals(9,
                p2.distanceSquared(p1),
                DELTA,
                "ERROR: squared distance between points is wrong");

        // =============== Boundary Values Tests ==================
        //TC03:squared distance between a point to itself
        assertEquals(0,
                p1.distanceSquared(p1),
                DELTA,
                "ERROR: point squared distance to itself is not zero");

    }


}




