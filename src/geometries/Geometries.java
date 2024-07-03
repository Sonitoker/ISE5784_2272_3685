package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

public class Geometries extends Intersectable{


    List<Intersectable> geometries= new LinkedList<Intersectable>();
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
}
