package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{


    List<Intersectable> geometries= new LinkedList<Intersectable>();

    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    private void add(Intersectable... geometries) {
        Collections.addAll(this.geometries, geometries); //פונקציה קיימת שמכניסה לרשימה את הכל- במקם לולאה
    }

    @Override
    public List<Point> findIntersection(Ray ray) {
        return null;
    }
}
