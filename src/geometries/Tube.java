
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Tube class represents a tube geometry in three-dimensional space.
 * A tube is defined by its radius and axis (a ray representing its center line).
 * This class extends the RadialGeometry class.
 */
public class Tube extends RadialGeometry {

    /**
     * The axis of the tube, represented by a ray.
     */
    protected final Ray axis;

    /**
     * Constructs a new Tube object with the specified radius and axis.
     *
     * @param radius The radius of the tube.
     * @param axis   The axis of the tube.
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    /**
     * Calculates the normal vector to the tube at the specified point.
     *
     * @param p The point on the surface of the tube.
     * @return The normal vector to the tube at the specified point, which is null.
     */
    public Vector getNormal(Point p) {

            // Calculate the vector from the base point of the axis to the given point
            Vector vectorFromAxisStart = p.subtract(axis.getHead());

            // Project the above vector on the axis direction to find the projection point on the axis
            double t = axis.getDir().dotProduct(vectorFromAxisStart);
            Point o;
            if(isZero(t))
                o=axis.getHead();
            else
                o = axis.getPoint(t);

            // Calculate the normal vector by subtracting the projection point from the given point
            Vector normal = p.subtract(o).normalize();

            return normal;


    }
    /**
     * Finds the intersections of the specified ray with the tube.
     *
     * @param ray The ray with which the intersections are calculated.
     * @return A list of intersection points with the tube, or null if there are no intersections.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Vector axisHead = axis.getDir();
        Vector v = ray.getDir();
        Point p0 = ray.getHead();

        double A = 0;
        double B = 0;
        double C = 0;

        double vVa = alignZero(v.dotProduct(axisHead)); // v*va
        Vector vVaVa; // v*va*va
        Vector vMinusVVaVa; // v-(v*va)*va
        if (vVa == 0) {
            vMinusVVaVa = v;
        } else {
            vVaVa = axisHead.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) {
                return null;
            }
        }

        A = vMinusVVaVa.lengthSquared(); // A = (v-(v*va)*va)^2
        double t = Math.sqrt(radius * radius / A);

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(axis.getHead());
        } catch (IllegalArgumentException e1) {
            return vVa == 0 ? alignZero(t) <= 0 ? null : List.of(new GeoPoint(this,ray.getPoint(radius))) : alignZero(t) <= 0 ? null : List.of(new GeoPoint(this,ray.getPoint(t)));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(axisHead));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0) {
            dPMinusdPVaVa = deltaP;
        } else {
            dPVaVa = axisHead.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                return alignZero(t) <= 0 ? null : List.of(new GeoPoint(this,ray.getPoint(t)));
            }
        }

        B = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa)); // B = 2*(v-(v*va)*va)*(p0-(p0*va)*va)
        C = dPMinusdPVaVa.lengthSquared() - radius * radius; // C = (p0-(p0*va)*va)^2-R^2

        double discriminant = alignZero(B * B - 4 * A * C);
        if (discriminant <= 0) { // No intersections
            return null;
        }

        double doubleA = 2 * A;
        return buildIntersectionsList(ray, alignZero(-B / doubleA), Math.sqrt(discriminant) / doubleA);
    }



    /**
     * A helper function that builds a list of intersection points with the tube.
     *
     * @param ray     The ray with which the intersections are calculated.
     * @param tMiddle The middle value of the intersection points.
     * @param tOffset The offset value of the intersection points.
     * @return A list of intersection points with the tube.
     */
    private List<GeoPoint> buildIntersectionsList(Ray ray, double tMiddle, double tOffset) {
        List<GeoPoint> intersections = new LinkedList<>();

        double t1 = alignZero(tMiddle - tOffset);
        double t2 = alignZero(tMiddle + tOffset);

        if (t1 > 0) {
            intersections.add(new GeoPoint(this,ray.getPoint(t1)));
        }

        if (t2 > 0) {
            intersections.add(new GeoPoint(this,ray.getPoint(t2)));
        }

        return intersections.isEmpty() ? null : intersections;
    }



}