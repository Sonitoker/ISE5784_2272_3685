package geometries;
import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{

    private Point center;

    public Sphere(double radius,Point center) {
        super(radius);
        this.center=center;
    }

    public Vector getNormal(Point p){
        return null;
    }
}
