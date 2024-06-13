package renderer;
import Scene.Scene;
import primitives.Color;
import primitives.Point;
import primitives.Ray;

import java.util.List;
import geometries.Intersectable.GeoPoint;

/**
 * SimpleRayTracer class represents a simple ray tracer.
 * A simple ray tracer is a ray tracer that does not perform any ray tracing.
 */
public class SimpleRayTracer extends RayTracerBase{
   /**
     * Constructs a SimpleRayTracer object with the given scene.
     * @param scene The scene to render.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Get color of the intersection of the ray with the scene
     * @param ray Ray to trace
     * @return Color of intersection
     */
    @Override
    public Color traceRay(Ray ray) {
        var intersections  = this.scene.geometries.findGeoIntersections(ray);

        if (intersections == null)
            return this.scene.background;

        return calcColor(ray.findClosestGeoPoint(intersections));
    }
    /**
     * Get the color of an intersection point
     * @param gp point of intersection and the geometry
     * @return Color of the intersection point
     */
    private Color calcColor(GeoPoint gp) {
        return this.scene.ambientLight.getIntensity().add(gp.geometry.getEmission());
    }
}




