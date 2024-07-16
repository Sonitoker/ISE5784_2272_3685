package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

public class Geometries extends Intersectable{



    public List<Intersectable> geometries= new LinkedList<Intersectable>();
    public Geometries(){}
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        Collections.addAll(this.geometries, geometries); //add all the geometries to the list
    }

    /**
     * constructor for Geometries class
     * @param geometries list of geometries
     */
    private Geometries(List<Intersectable> geometries)
    {
        this.geometries = geometries;
    }
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> result = null;
        for (Intersectable geometry : geometries) {
            if(geometry.findGeoIntersections(ray)!=null){
                List<GeoPoint> tempIntersections = geometry.findGeoIntersections(ray);
                if (tempIntersections != null) {
                    if (result == null) {
                        result = new LinkedList<>();
                    }
                    for(GeoPoint geoPoint :tempIntersections) {
                        if (alignZero(geoPoint.point.distance(ray.getHead()) - maxDistance) <= 0) {
                            result.add(geoPoint);
                        }
                    }
                }
            }
        }


        return result;
    }

    /**
     * finds the minimum coordinates of the geometries
     * @return the minimum coordinates
     */
    @Override
    public Point getMinCoords() {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        for (Intersectable geo : geometries) {
            Point geoMin = geo.getMinCoords();
            if (geoMin.getX() < minX) {
                minX = geoMin.getX();
            }
            if (geoMin.getY() < minY) {
                minY = geoMin.getY();
            }
            if (geoMin.getZ() < minZ) {
                minZ = geoMin.getZ();
            }
        }
        return new Point(minX, minY, minZ);
    }

    /**
     * finds the maximum coordinates of the geometries
     * @return the maximum coordinates
     */
    @Override
    public Point getMaxCoords()
    {
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        for (Intersectable geo : geometries) {
            Point geoMax = geo.getMaxCoords();
            if (geoMax.getX() > maxX) {
                maxX = geoMax.getX();
            }
            if (geoMax.getY() > maxY) {
                maxY = geoMax.getY();
            }
            if (geoMax.getZ() > maxZ) {
                maxZ = geoMax.getZ();
            }
        }
        return new Point(maxX, maxY, maxZ);
    }



    /**
     * get the size of the geometries
     * @return the size
     */
    public int size() {
        return geometries.size();
    }

    /**
     * finds the center point of the geometries
     * @return the center point
     */
    @Override
    public Point getCenterPoint() {
        Point min = getMinCoords();
        Point max = getMaxCoords();
        return new Point((min.getX() + max.getX()) / 2, (min.getY() + max.getY()) / 2, (min.getZ() + max.getZ()) / 2);
    }

    /**
     * splits the geometries into two lists
     * @param box the box to split by
     * @return the two lists
     */
    public Geometries splitAxisAligned(AABB box)
    {
        List<Intersectable> left = new LinkedList<>();
        List<Intersectable> right = new LinkedList<>();
        Point min = box.getMin();
        Point max = box.getMax();

        //sorts the geometries by the axis with the largest difference
        //if the difference is the same, it sorts by the x axis
        if(max.getX() - min.getX() > max.getY() - min.getY() && max.getX() - min.getX() > max.getZ() - min.getZ())
        {
            geometries = geometries.stream().sorted(Comparator.comparingDouble(a -> a.getCenterPoint().getX())).toList();
        }
        //if the difference in the y axis is the largest
        else if(max.getY() - min.getY() > max.getX() - min.getX() && max.getY() - min.getY() > max.getZ() - min.getZ())
        {
            geometries = geometries.stream().sorted(Comparator.comparingDouble(a -> a.getCenterPoint().getY())).toList();
        }
        //if the difference in the z axis is the largest
        else
        {
            geometries = geometries.stream().sorted(Comparator.comparingDouble(a -> a.getCenterPoint().getZ())).toList();
        }
        for (int i = 0; i < geometries.size()/2; i++) {
            left.add(geometries.get(i));
        }
        for(int i = geometries.size()/2; i < geometries.size(); i++) {
            right.add(geometries.get(i));
        }

        this.geometries = left;
        return new Geometries(right);
    }
}

