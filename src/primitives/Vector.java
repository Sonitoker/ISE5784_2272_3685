package primitives;

import javax.naming.OperationNotSupportedException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a vector in three-dimensional space.
 */
public class Vector extends Point {

    /**
     * Constructs a new Vector with the specified coordinates.
     *
     * @param x The x-coordinate of the vector.
     * @param y The y-coordinate of the vector.
     * @param z The z-coordinate of the vector.
     * @throws IllegalArgumentException if the vector is the zero vector.
     */
    public Vector(double x, double y, double z) {
        this(new Double3(x, y, z));
    }


    /**
     * Constructs a new Vector with the specified Double3 coordinates.
     *
     * @param xyz The Double3 containing the coordinates of the vector.
     * @throws IllegalArgumentException if the vector is the zero vector.
     */
    Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Cannot create zero vector");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;
        return xyz.equals(vector.xyz);
    }


    @Override
    public String toString() {
        return "Vector{" + xyz + '}';
    }

    /**
     * Computes the sum of this vector and another vector.
     *
     * @param vec The vector to add to this vector.
     * @return A new vector representing the sum of this vector and the specified vector.
     */
    public Vector add(Vector vec) {
        return new Vector(this.xyz.add(vec.xyz));
    }


    /**
     * Scales this vector by a scalar factor.
     *
     * @param rsh The scalar factor to scale this vector by.
     * @return A new vector representing this vector scaled by the specified factor.
     */
    public Vector scale(double rsh) {
        return new Vector(this.xyz.scale(rsh));
    }


    /**
     * Computes the dot product of this vector and another vector.
     *
     * @param vec The vector to compute the dot product with.
     * @return The dot product of this vector and the specified vector.
     */
    public double dotProduct(Vector vec) {
        return ((this.xyz.d1 * vec.xyz.d1) + (this.xyz.d2 * vec.xyz.d2) + (this.xyz.d3 * vec.xyz.d3));
    }

    /**
     * Computes the cross product of this vector and another vector.
     *
     * @param vec The vector to compute the cross product with.
     * @return A new vector representing the cross product of this vector and the specified vector.
     */
    public Vector crossProduct(Vector vec) {
        return new Vector(
                ((xyz.d2 * vec.xyz.d3) - (xyz.d3 * vec.xyz.d2)),
                ((xyz.d3 * vec.xyz.d1) - (xyz.d1 * vec.xyz.d3)),
                ((xyz.d1 * vec.xyz.d2) - (xyz.d2 * vec.xyz.d1)));
    }

    /**
     * Computes the square of the length of this vector.
     *
     * @return The square of the length of this vector.
     */
    public double lengthSquared() {
        return ((xyz.d1 * xyz.d1) + (xyz.d2 * xyz.d2) + (xyz.d3 * xyz.d3));
    }


    /**
     * Computes the length of this vector.
     *
     * @return The length of this vector.
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }


    /**
     * normalize the vector
     *
     * @return the normalized vector
     */
    public Vector normalize() {
        double length = alignZero(length());
        if (length == 0)
            throw new ArithmeticException("Cannot normalize Vector(0,0,0)");
        return new Vector(xyz.scale(1 / length));
    }


}

