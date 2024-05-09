package primitives;

/**
 * Represents a point in three-dimensional space.
 */
public class Point {
    /** The coordinates of the point in three-dimensional space. */
    final protected  Double3 xyz;

    /** A constant representing the origin point (0, 0, 0). */
    public static final Point ZERO=new Point(0,0,0);

    /**
     * Constructs a new Point with the specified coordinates.
     *
     * @param d1 The x-coordinate of the point.
     * @param d2 The y-coordinate of the point.
     * @param d3 The z-coordinate of the point.
     */
    public Point(double d1, double d2, double d3) {
        this.xyz =new Double3(d1,d2,d3);
    }



    /**
     * Constructs a new Point with the specified Double3 coordinates.
     *
     * @param xyz The Double3 containing the coordinates of the point.
     * @throws IllegalArgumentException if xyz represents the zero vector.
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }



    @Override
    public boolean equals(Object obj){
        if(this==obj) return true;
        return obj instanceof Point other && this.xyz.equals(other.xyz);
    }


    @Override
    public String toString(){return ""+xyz;}


    /**
     * Computes the vector obtained by subtracting another point from this point.
     *
     * @param p The point to subtract from this point.
     * @return The vector representing the difference between this point and the specified point.
     */
    public Vector subtract(Point p) {
        return new Vector(this.xyz.subtract(p.xyz));
    }

    /**
     * Computes the point obtained by adding a vector to this point.
     *
     * @param vec The vector to add to this point.
     * @return The point resulting from adding the specified vector to this point.
     */
    public Point add(Vector vec) {
        return new Point(this.xyz.add(vec.xyz));
    }

    /**
     * Computes the square of the Euclidean distance between this point and another point.
     *
     * @param p The point to compute the distance to.
     * @return The square of the Euclidean distance between this point and the specified point.
     */
    public double distanceSquared(Point p) {
        Vector vec = subtract(p);
        return vec.xyz.d1 * vec.xyz.d1 + vec.xyz.d2 * vec.xyz.d2 + vec.xyz.d3 * vec.xyz.d3;
    }

    /**
     * Computes the Euclidean distance between this point and another point.
     *
     * @param p The point to compute the distance to.
     * @return The Euclidean distance between this point and the specified point.
     */
    public double distance(Point p) {
        return Math.sqrt(this.distanceSquared(p));
    }
}