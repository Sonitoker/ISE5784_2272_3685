package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Geometries class represents a collection of geometrical objects in three-dimensional space.
 * This class extends the
 * Intersectable class.
 */
public class Geometries extends Intersectable {
    /**
     * The list of geometries in the collection.
     */
    public List<Intersectable> geometries = new LinkedList<Intersectable>();

    /**
     * Constructs a new Geometries object.
     */
    public Geometries() {
    }

    /**
     * Constructs a new Geometries object with the given geometries.
     *
     * @param geometries The geometries to add to the collection.
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Adds the given geometries to the collection.
     *
     * @param geometries The geometries to add to the collection.
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(this.geometries, geometries); //add all the geometries to the list
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> result = null;
        for (Intersectable geometry : geometries) {
            if (geometry.findGeoIntersections(ray) != null) {
                List<GeoPoint> tempIntersections = geometry.findGeoIntersections(ray);
                if (tempIntersections != null) {
                    if (result == null) {
                        result = new LinkedList<>();
                    }
                    for (GeoPoint geoPoint : tempIntersections) {
                        if (alignZero(geoPoint.point.distance(ray.getHead()) - maxDistance) <= 0) {
                            result.add(geoPoint);
                        }
                    }
                }
            }
        }


        return result;
    }

    @Override
    public boolean isIntersectBox(Ray ray, double maxDistance) {
        for (Intersectable g : geometries) {
            if (g.isIntersectBox(ray, maxDistance)) return true;
        }
        return false;
    }

    /**
     * Sets up bounding boxes for the geometries.
     * This method constructs bounding boxes for each geometry in the collection
     * of geometries. The bounding boxes are used for various geometric calculations
     * such as intersection tests and spatial optimizations.
     */
    public void setBoxes() {
        for (Intersectable g : geometries) {
            g.constructBox();
        }
    }

    /**
     * Constructs the Axis-Aligned Bounding Box (AABB) for the geometrical object.
     */
    public void constructBox() {
        return;
    }


    /**
     * get the size of the geometries
     * @return the size
     */
    public int size() {
        return geometries.size();
    }


    /**
     * Splits the geometries collection into two collections along the given axis-aligned box.
     *
     * @param box The axis-aligned box to split the geometries along.
     * @return The geometries collection that intersects the given box.
     */
    public Geometries splitAxisAligned(Box box) {
        Geometries result = new Geometries();
        Ray ray = new Ray(new Point(box.minX, box.minY, box.minZ), new Vector(box.maxX, box.maxY, box.maxZ));
        for (Intersectable g : geometries) {
            if (g.isIntersectBox(ray, Double.POSITIVE_INFINITY)) {
                result.add(g);
            }
        }
        return result;
    }
}

