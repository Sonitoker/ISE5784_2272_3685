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

    @Override
    public boolean isIntersectBox(Ray ray, double maxDistance) {
        for(Intersectable g:geometries){
            if(g.isIntersectBox(ray, maxDistance))return true;
        }
        return  false ;
    }
    /**
     * Sets up bounding boxes for the geometries.
     * This method constructs bounding boxes for each geometry in the collection
     * of geometries. The bounding boxes are used for various geometric calculations
     * such as intersection tests and spatial optimizations.
     */
    public void setBoxes(){
        for(Intersectable g:geometries){
            g.constructBox();
        }
    }
    public void constructBox() {
        return;
    }


}

