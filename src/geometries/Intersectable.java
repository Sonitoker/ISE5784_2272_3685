package geometries;

import primitives.*;

import java.util.List;

/**
 * Abstract class for geometrical objects that can be intersected by a ray.
 */
public abstract class Intersectable {

protected Box box;

    /**
     * Finds all intersection points between the given ray and the geometrical object.
     * @param ray the ray to intersect with the geometrical object
     * @return a list of intersection points, or null if there are no intersections
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);

        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }
    public abstract boolean isIntersectBox(Ray ray, double maxDistance);
    public abstract void constructBox();
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
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        if (ray.isBVH) {
            return (!isIntersectBox(ray, maxDistance)) ? null : findGeoIntersectionsHelper(ray, maxDistance);
        }
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    protected abstract List<GeoPoint>
    findGeoIntersectionsHelper(Ray ray, double maxDistance);

/* Represents an Axis-Aligned Bounding Box (AABB).
            */
    public static class Box {
        private final double minX;
        private final double minY;
        private final double minZ;
        private final double maxX;
        private final double maxY;
        private final double maxZ;

            /* Constructs a new Box object with the specified minimum and maximum coordinates.
             *
                     * @param minX The minimum x-coordinate of the box.
                * @param minY The minimum y-coordinate of the box.
                * @param minZ The minimum z-coordinate of the box.
                * @param maxX The maximum x-coordinate of the box.
                * @param maxY The maximum y-coordinate of the box.
                * @param maxZ The maximum z-coordinate of the box.
                */
        public Box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }

        /**
         * Checks if the box intersects with the given ray within the maximum distance.
         *
         * @param ray         The ray to check for intersection.
         * @param maxDistance The maximum distance for intersection.
         * @return true if the box intersects with the ray, false otherwise.
         */
        public boolean intersects(Ray ray, double maxDistance) {
            double dirX = ray.getDir().getX();
            double dirY = ray.getDir().getY();
            double dirZ = ray.getDir().getZ();
            double p0X = ray.getHead().getX();
            double p0Y = ray.getHead().getY();
            double p0Z = ray.getHead().getZ();
            double txmin = Util.alignZero(minX - p0X) / dirX;
            double txmax = Util.alignZero(maxX - p0X) / dirX;
            double tymin = Util.alignZero(minY - p0Y) / dirY;
            double tymax = Util.alignZero(maxY - p0Y) / dirY;
            double tzmin = Util.alignZero(minZ - p0Z) / dirZ;
            double tzmax = Util.alignZero(maxZ - p0Z) / dirZ;
            double tmin = Math.max(Math.max(Math.min(txmin, txmax), Math.min(tymin, tymax)), Math.min(tzmin, tzmax));
            double tmax = Math.min(Math.min(Math.max(txmin, txmax), Math.max(tymin, tymax)), Math.max(tzmin, tzmax));

            // if tmax < 0, ray (line) is intersecting AABB, but the whole AABB is behind us
            if (tmax < 0 || tmin > maxDistance) {
                return false;
            }
            // if tmin > tmax, ray doesn't intersect AABB
            return !(tmin > tmax);
        }

    }

}

