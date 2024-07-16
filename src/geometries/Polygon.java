package geometries;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends  Geometry {
    /**
     * List of polygon's vertices
     */
    protected final List<Point> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected final Plane plane;
    /**
     * The size of the polygon - the amount of the vertices in the polygon
     */
    private final int size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by
     *                 edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        size = vertices.length;

        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return; // no need for more tests for a Triangle

        Vector n = plane.getNormal();
        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with the normal. If all the rest consequent edges will generate the same sign
        // - the polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        //Finding an intersection with the plane of the Polygon
        List<GeoPoint> intersectionWithPlane =  this.plane.findGeoIntersections(ray, maxDistance);

        if(intersectionWithPlane == null)
            return null;

        List<Vector> vectors = new LinkedList<>();
        for (Point p : vertices) {
            vectors.add(p.subtract(ray.getHead()));
        }

        List<Vector> normals = new LinkedList<>();
        Vector v1, v2;
        for (int i = 0; i < vectors.size(); i++) {
            v1 = vectors.get(i);
            v2 = i != vectors.size() - 1 ? vectors.get(i + 1) : vectors.getFirst();

            normals.add(v1.crossProduct(v2).normalize());
        }

        List<Double> scalars = new LinkedList<>();
        for (Vector n : normals) {
            scalars.add(alignZero(ray.getDir().dotProduct(n)));
        }

        //If one of the scalar products is zero - no cutting
        if(scalars.contains(0))
            return null;

        //If all the scalar lines have the same sign - then the intersection with the plane cuts the triangle
        int sign = (scalars.getFirst() > 0) ? 1 : -1;
        for (Double s : scalars) {
            int currentSign = (s > 0) ? 1 : -1;
            if (currentSign != sign) {
                return null;
            }
        }
        return  List.of(new GeoPoint(this, intersectionWithPlane.getFirst().point));
    }
    /**
     * finds the minimum coordinates of the polygon
     * @return the minimum coordinates of the polygon
     */
    @Override
    public Point getMinCoords() {
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double minZ = Double.MAX_VALUE;
        for (Point p : vertices) {
            if (p.getX() < minX) minX = p.getX();
            if (p.getY() < minY) minY = p.getY();
            if (p.getZ() < minZ) minZ = p.getZ();
        }
        return new Point(minX, minY, minZ);
    }

    /**
     * finds the maximum coordinates of the polygon
     * @return the maximum coordinates of the polygon
     */
    public Point getMaxCoords() {
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        double maxZ = Double.MIN_VALUE;
        for (Point p : vertices) {
            if (p.getX() > maxX) maxX = p.getX();
            if (p.getY() > maxY) maxY = p.getY();
            if (p.getZ() > maxZ) maxZ = p.getZ();
        }
        return new Point(maxX, maxY, maxZ);
    }

    /**
     * finds the center point of the polygon
     * @return the center point of the polygon
     */
    public Point getCenterPoint() {
        double sumX = 0;
        double sumY = 0;
        double sumZ = 0;
        for (Point p : vertices) {
            sumX += p.getX();
            sumY += p.getY();
            sumZ += p.getZ();
        }
        return new Point(sumX / vertices.size(), sumY / vertices.size(), sumZ / vertices.size());
    }
}


