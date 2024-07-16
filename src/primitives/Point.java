package primitives;

import java.util.Objects;

/**
 * Represents a point in three-dimensional space.
 */
public class Point {
    /**
     * The coordinates of the point in three-dimensional space.
     */
    final protected Double3 xyz;

    /**
     * A constant representing the origin point (0, 0, 0).
     */
    public static final Point ZERO = new Point(0, 0, 0);

    /**
     * Constructs a new Point with the specified coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param z The z-coordinate of the point.
     */
    public Point(double x, double y, double z) {
        //this.xyz =new Double3(x,y,z);
        this(new Double3(x, y, z));
    }


    /**
     * Constructs a new Point with the specified Double3 coordinates.
     *
     * @param xyz The Double3 containing the coordinates of the point.
     */
    Point(Double3 xyz) {
        this.xyz = xyz;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return xyz.equals(point.xyz);
    }

    @Override
    public int hashCode() {
        return xyz.hashCode();
    }

    @Override
    public String toString() {
        return "Point{" + xyz + '}';
    }

    /**
     * Computes the vector obtained by subtracting another point from this point.
     *
     * @param p The point to subtract from this point.
     * @return The vector representing the difference between this point and the specified point.
     */
    public Vector subtract(Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }

    /**
     * Computes the point obtained by adding a vector to this point.
     *
     * @param vec The vector to add to this point.
     * @return The point resulting from adding the specified vector to this point.
     */
    public Point add(Vector vec) {
        return new Point(xyz.add(vec.xyz));
    }

    /**
     * Computes the square of the Euclidean distance between this point and another point.
     *
     * @param p The point to compute the distance to.
     * @return The square of the Euclidean distance between this point and the specified point.
     */
    public double distanceSquared(Point p) {
        double xx = (xyz.d1 - p.xyz.d1) * (xyz.d1 - p.xyz.d1);
        double yy = (xyz.d2 - p.xyz.d2) * (xyz.d2 - p.xyz.d2);
        double zz = (xyz.d3 - p.xyz.d3) * (xyz.d3 - p.xyz.d3);

        return xx + yy + zz;
    }

    /**
     * Computes the Euclidean distance between this point and another point.
     *
     * @param p The point to compute the distance to.
     * @return The Euclidean distance between this point and the specified point.
     */
    public double distance(Point p) {
        return Math.sqrt(distanceSquared(p));
    }

    /**
     * Returns the x-coordinate of the point.
     *
     * @return The x-coordinate of the point.
     */
    public double getX() {
        return xyz.d1;
    }
    /**
     * Returns the y-coordinate of the point.
     *
     * @return The y-coordinate of the point.
     */
    public double getY() {
        return xyz.d2;
    }
    /**
     * Returns the z-coordinate of the point.
     *
     * @return The z-coordinate of the point.
     */
    public double getZ() {
        return xyz.d3;
    }

    /**
     * Returns the coordinate of the point at the specified index.
     *
     * @param index The index of the coordinate to return (0 for x, 1 for y, 2 for z).
     * @return The coordinate of the point at the specified index.
     */
    public double getCoord(int index) {
        switch (index) {
            case 0: return xyz.d1;
            case 1: return xyz.d2;
            case 2: return xyz.d3;
            default: throw new IllegalArgumentException("Index must be 0, 1, or 2");
        }
    }


}