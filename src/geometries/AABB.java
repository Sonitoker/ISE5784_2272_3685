package geometries;

import primitives.Point;
import primitives.Ray;

/**
 * Axis Aligned Bounding Box class
 */
public class AABB {

    Point min;
    Point max;

    /**
     * constructor for AABB
     * @throws IllegalArgumentException if a min point coordinate is bigger than a max point coordinate
     * @param min the minimum point
     * @param max the maximum point
     */
    public AABB(Point min, Point max) {


        if(min.getX() > max.getX() || min.getY() > max.getY() || min.getZ() > max.getZ())
        {
            throw new IllegalArgumentException("min point must be smaller than max point");
        }

        this.min = min;
        this.max = max;
    }


    /**
     *  checks if a point is in the AABB
     *  @param p the point to check
     *  @return true if the point is in the AABB, false otherwise
     */
    public boolean PointInAABB(Point p)
    {
        return (p.getX() >= min.getX() && p.getX() <= max.getX() &&
                p.getY() >= min.getY() && p.getY() <= max.getY() &&
                p.getZ() >= min.getZ() && p.getZ() <= max.getZ());
    }

    /**
     * checks if a ray intersects the AABB
     * @param ray the ray to check
     * @return true if the ray intersects the AABB, false otherwise
     */
    public boolean intersects(Ray ray)
    {
        // Calculate the intersection distances for the X-axis
        double tmin = (min.getX() - ray.getHead().getX()) / ray.getDir().getX();
        double tmax = (max.getX() - ray.getHead().getX()) / ray.getDir().getX();

        // Ensure tmin is always smaller than tmax
        if (tmin > tmax) {
            double temp = tmin;
            tmin = tmax;
            tmax = temp;
        }

        // Calculate the intersection distances for the Y-axis
        double tymin = (min.getY() - ray.getHead().getY()) / ray.getDir().getY();
        double tymax = (max.getY() - ray.getHead().getY()) / ray.getDir().getY();

        // Ensure tymin is always smaller than tymax
        if (tymin > tymax) {
            double temp = tymin;
            tymin = tymax;
            tymax = temp;
        }

        // Check if the ray is intersecting the AABB
        if ((tmin > tymax) || (tymin > tmax))
            return false;

        // Check if the ray is intersecting the AABB
        if (tymin > tmin)
            tmin = tymin;

        // Check if the ray is intersecting the AABB
        if (tymax < tmax)
            tmax = tymax;

        // Calculate the intersection distances for the Z-axis
        double tzmin = (min.getZ() - ray.getHead().getZ()) / ray.getDir().getZ();
        double tzmax = (max.getZ() - ray.getHead().getZ()) / ray.getDir().getZ();

        // Ensure tzmin is always smaller than tzmax
        if (tzmin > tzmax) {
            double temp = tzmin;
            tzmin = tzmax;
            tzmax = temp;
        }

        // Check if the ray is intersecting the AABB
        if ((tmin > tzmax) || (tzmin > tmax))
            return false;

        return true;
    }

    /**
     * getter for min and max points
     * @return min and max points
     */
    public Point getMin() {
        return min;
    }

    /**
     * getter for max point
     * @return max point
     */
    public Point getMax() {
        return max;
    }

    /**
     * getter for the center point of the AABB
     * @return the center point of the AABB
     */
    public Point getCenterPoint()
    {
        return new Point((min.getX() + max.getX())/2, (min.getY() + max.getY())/2, (min.getZ() + max.getZ())/2);
    }

    /**
     * setter for min point
     * @param min the new min point
     */
    public void setMin(Point min) {
        this.min = min;
    }

    /**
     * setter for max point
     * @param max the new max point
     */
    public void setMax(Point max) {
        this.max = max;
    }
}