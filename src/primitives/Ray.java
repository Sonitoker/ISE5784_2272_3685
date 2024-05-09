package primitives;

/**
 * Represents a ray in three-dimensional space, defined by a starting point (head) and a direction.
 */
public class Ray {
    final private  Point head;
    final private  Vector direction;

    /**
     * Constructs a new Ray with the specified starting point and direction.
     *
     * @param p   The starting point (head) of the ray.
     * @param vec The direction vector of the ray.
     */
    public Ray(Point p, Vector vec) {
        this.head = p;
        this.direction=vec.normalize();
    }


    /**
     * Checks if this Ray is equal to another object.
     *
     * @param obj The object to compare to.
     * @return True if the objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj){
        return (obj instanceof Ray other) && this.head.equals(other.head) && this.direction.equals(other.direction);
    }


    /**
     * Returns a string representation of this Ray.
     *
     * @return A string representation of this Ray.
     */
    @Override
    public String toString() {
        return "Ray: Head = " + head + ", Direction = " + direction;
    }

}
//heeyyy
