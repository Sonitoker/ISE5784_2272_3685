package geometries;

import primitives.*;
import java.util.List;

/**
 * Interface for geometrical objects that can be intersected by a ray.
 */
public abstract class Intersectable {

    /**
     * Finds all intersection points between the given ray and the geometrical object.
     * @param ray
     * @return
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }


     /**
      * A class representing a point and the geometry it intersects.
      */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;
        /**
         * Constructs a GeoPoint object with the given geometry and point.
         *
         * @param geometry The geometry the point intersects.
         * @param point The point that intersects the geometry.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;

        }

         @Override
         public boolean equals(Object o) {
             if (this == o) return true;
             if (!(o instanceof GeoPoint gPoint)) return false;
             return point.equals(gPoint.point) && geometry.equals(gPoint.geometry);
         }

         @Override
         public String toString() {
                return "GeoPoint{" +
                        "geometry=" + geometry +
                        ", point=" + point +
                        '}';
            }
    }

    /**
     * Finds all intersection points between the given ray and the geometrical object.
     *
     * @param ray the ray to intersect with the geometrical object
     * @return a list of intersection points (of type {@link GeoPoint}), or an empty list if there are no intersections
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);


}

