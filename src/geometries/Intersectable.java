package geometries;

import primitives.*;
import java.util.List;

/**
 * Interface for geometrical objects that can be intersected by a ray.
 */
public abstract class Intersectable {

    /**
     * Finds all intersection points between the given ray and the geometrical object.
     *
     * @param ray the ray to intersect with the geometrical object
     * @return a list of intersection points (of type {@link Point}), or an empty list if there are no intersections
     */
     public abstract List<Point> findIntersections(Ray ray);


    public static class GeoPoint {
        public Geometry geometry;
        public Point point;
    }

}

