package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{


    List<Intersectable> geometries= new LinkedList<Intersectable>();
    public Geometries(){}
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        Collections.addAll(this.geometries, geometries); //פונקציה קיימת שמכניסה לרשימה את הכל- במקם לולאה
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
