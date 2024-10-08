package renderer;

import Scene.Scene;
import org.junit.jupiter.api.*;
import primitives.*;
import geometries.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the Camera class.
 */
public class CameraIntegrationsTest {
    /**
     * The view plane is defined by the camera location and the direction vectors.
     */
    public CameraIntegrationsTest() {
    }
    /**
     * The view plane is defined by the camera location and the direction vectors.
     */
    private final Vector vUp = new Vector(0, -1, 0);
    /**
     * The view plane is defined by the camera location and the direction vectors.
     */
    private final Vector vTo = new Vector(0, 0, -1);

    /**
     * The camera builder for the tests.
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(vUp, vTo)
            .setVpDistance(1)
            .setVpSize(3, 3);


    /**
     * Test method for {@link Camera#constructRay(int, int, int, int)}.
     *
     * @param camera   The camera to test
     * @param geometry The geometry to test
     * @param expected The expected number of intersections
     */
    private void helpCountsIntersections(Camera camera, Geometry geometry, int expected) {
        int count = 0;
        List<Point> intersections;
        //running on the view plane and construct a ray from the camera to each point on the view plane
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                intersections = geometry.findIntersections(camera.constructRay(3, 3, j, i));
                count += intersections == null ? 0 : intersections.size();
            }
        }
        //check the number of intersections
        assertEquals(expected, count, "wrong number of intersections");
    }


    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)}
     * and {@link geometries.Sphere#findIntersections(Ray)}.
     */
    @Test
    public void CameraRaySphereIntegration() {

        Camera camera1 = cameraBuilder.setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0)).setVpDistance(1d).setVpSize(3d, 3d).setImageWriter(new ImageWriter("purpleOrange", 800, 500)).setRayTracer(new SimpleRayTracer(new Scene("hi"))).build();
        Camera camera2 = cameraBuilder.setLocation(new Point(0, 0, 0.5)).setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0)).setVpDistance(1d).setVpSize(3d, 3d).build();


        //TC01: Sphere r=1 (2 intersections)
        helpCountsIntersections(camera1, new Sphere(1d, new Point(0, 0, -3)), 2);


        //TC02: Sphere r=2.5 (18 intersections)
        helpCountsIntersections(camera2, new Sphere(2.5d, new Point(0, 0, -2.5)), 18);


        //TC03: Sphere r=2 (10 intersections)
        helpCountsIntersections(camera2, new Sphere(2d, new Point(0, 0, -2)), 10);


        //TC04: Sphere r=4 (9 intersections)
        helpCountsIntersections(camera2, new Sphere(4d, new Point(0, 0, 1)), 9);

        //TC05: Sphere r=0.5 (0 intersections)
        helpCountsIntersections(camera1, new Sphere(0.5d, new Point(0, 0, 1)), 0);

    }

    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)}
     * and {@link geometries.Triangle#findIntersections(Ray)}.
     */
    @Test
    public void CameraRayTriangleIntegration() {
        Camera camera1 = cameraBuilder.setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0)).setVpDistance(1d).setVpSize(3d, 3d).setImageWriter(new ImageWriter("purpleOrange", 800, 500)).setRayTracer(new SimpleRayTracer(new Scene("hi"))).build();


        //TC01: Small triangle (1 intersection)
        helpCountsIntersections(camera1, new Triangle(new Point(1, -1, -2), new Point(-1, -1, -2), new Point(0, 1, -2)), 1);


        //TC02: Large triangle (2 intersection)
        helpCountsIntersections(camera1, new Triangle(new Point(1, -1, -2), new Point(-1, -1, -2), new Point(0, 20, -2)), 2);


    }


    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)}
     * and {@link geometries.Plane#findIntersections(Ray)}.
     */

    @Test
    public void CameraRayPlaneIntegration() {
        Camera camera1 = cameraBuilder.setLocation(Point.ZERO).setDirection(new Vector(0, 0, 1), new Vector(0, -1, 0)).setVpDistance(1d).setVpSize(3d, 3d).setImageWriter(new ImageWriter("purpleOrange", 800, 500)).setRayTracer(new SimpleRayTracer(new Scene("hi"))).build();


        //TC01: The plane parallel to the View Plane (9 intersections)
        helpCountsIntersections(camera1, new Plane(new Point(0, 0, 5),
                new Vector(0, 0, 1)), 9);


        //TC02: Diagonal plane to the View Plane (9 intersections)
        helpCountsIntersections(camera1, new Plane(new Point(0, 0, 5),
                new Vector(0, -1, 2)), 9);


        ////TC03: Diagonal plane with an obtuse angle to the View Plane (6 intersections)
        helpCountsIntersections(camera1, new Plane(new Point(0, 0, 2),
                new Vector(1, 1, 1)), 6);


        // TC04:The plane behind the view plane (0 intersections)
        helpCountsIntersections(camera1, new Plane(new Point(0, 0, -4),
                new Vector(0, 0, 1)), 0);

    }
}
