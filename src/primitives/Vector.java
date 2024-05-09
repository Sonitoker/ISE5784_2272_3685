package primitives;

/**
 * Represents a vector in three-dimensional space.
 */
public class Vector extends Point {

    /**
     * Constructs a new Vector with the specified coordinates.
     *
     * @param d1 The x-coordinate of the vector.
     * @param d2 The y-coordinate of the vector.
     * @param d3 The z-coordinate of the vector.
     * @throws IllegalArgumentException if the vector is the zero vector.
     */
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
        if (d1 == 0 && d2 == 0 && d3 == 0) {
            throw new IllegalArgumentException("Zero vector is not allowed.");
        }
    }


    /**
     * Constructs a new Vector with the specified Double3 coordinates.
     *
     * @param xyz The Double3 containing the coordinates of the vector.
     * @throws IllegalArgumentException if the vector is the zero vector.
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Cannot create zero vector");
        }
    }


    /**
     * Checks if this Vector is equal to another object.
     *
     * @param obj The object to compare to.
     * @return True if the objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    /**
     * Returns a string representation of this Vector.
     *
     * @return A string representation of this Vector.
     */
    @Override
    public String toString() {
        return super.toString();
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
        return new Vector(((this.xyz.d2 * vec.xyz.d3) - (this.xyz.d3 * vec.xyz.d2)),
                ((this.xyz.d3 * vec.xyz.d1) - (this.xyz.d1 * vec.xyz.d3)),
                ((this.xyz.d1 * vec.xyz.d2) - (this.xyz.d2 * vec.xyz.d1)));
    }

    /**
     * Computes the square of the length of this vector.
     *
     * @return The square of the length of this vector.
     */
    public double lengthSquared() {
        return this.dotProduct(this);
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
     * Normalizes this vector (i.e., scales it to have unit length).
     *
     * @return A new vector representing this vector normalized to have unit length.
     */
    public Vector normalize() {
        return new Vector(((this.xyz.d1) / (this.length())),
                ((this.xyz.d2) / (this.length())),
                ((this.xyz.d3) / (this.length())));
    }

}

