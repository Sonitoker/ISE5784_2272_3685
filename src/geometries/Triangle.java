package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Triangle class represents a triangle geometry in three-dimensional space.
 * A triangle is a polygon with three edges and three vertices.
 * This class extends the Polygon class.
 */
public class Triangle extends Polygon{
    public Triangle(Point... vertices) {
        super(vertices);
    }



    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> p=plane.findIntersections(ray);
        if(p == null){
            return null;
        }
        Vector v1= vertices.get(0).subtract(ray.getHead());
        Vector v2= vertices.get(1).subtract(ray.getHead());
        Vector v3= vertices.get(2).subtract(ray.getHead());
        Vector n1= v1.crossProduct(v2).normalize();
        Vector n2= v2.crossProduct(v3).normalize();
        Vector n3= v3.crossProduct(v1).normalize();
        Vector v=ray.getDir();
        if(alignZero(v.dotProduct(n1))>0 && alignZero(v.dotProduct(n2))>0 && alignZero(v.dotProduct(n3))>0 ){
            return p;
        }
        if(alignZero(v.dotProduct(n1))<0 && alignZero(v.dotProduct(n2))<0 && alignZero(v.dotProduct(n3))<0 ){
            return p;
        }
        return null;
    }
}
