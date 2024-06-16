package renderer;
import Scene.Scene;
import lighting.LightSource;
import primitives.*;

import java.util.List;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;

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

        return calcColor(ray.findClosestGeoPoint(intersections),ray);
    }
    /**
     * Get the color of an intersection point
     * @param intersection point of intersection and the geometry
     * @return Color of the intersection point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return this.scene.ambientLight.getIntensity().add(calcLocalEffects(intersection, ray));
    }

    /**
     * Calculate local effects (diffusive and specular) of the intersection point
     * @param intersection point of intersection and the geometry
     * @param ray the ray
     * @return the color of the intersection point
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector n=intersection.geometry.getNormal(intersection.point);
        Vector v=ray.getDir();
        double nv=alignZero(n.dotProduct(v));
        if (nv==0)
            return Color.BLACK;
        Material material=intersection.geometry.getMaterial();
        Color color=intersection.geometry.getEmission();
        for(LightSource lightSource : scene.lights) {
            Vector l=lightSource.getL(intersection.point);
            double nl=alignZero(n.dotProduct(l));
            if (nl*nv>0){ // sign(nl)==sign(nv)
                Color iL=lightSource.getIntensity(intersection.point); // intensity of the light
                color=color.add(calcDiffusive(material.kD, l, n, iL).add(calcSpecular(material.kS,l,n,v, material.nShininess, iL)));
            }
        }
        return color;
    }
    /**
     * Calculate the diffusive component of the light
     * @param kd the diffusive coefficient
     * @param l the vector from the light source to the intersection point
     * @param n the normal vector to the geometry at the intersection point
     * @param iL the intensity of the light
     * @return the color of the diffusive component
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color iL) {
        double lN = l.normalize().dotProduct(n.normalize());
        return iL.scale(kd.scale(Math.abs(lN)));
    }

    /**
     * Calculate the specular component of the light
     * @param ks the specular coefficient
     * @param l the vector from the light source to the intersection point
     * @param n the normal vector to the geometry at the intersection point
     * @param v the vector from the intersection point to the camera
     * @param nShininess the shininess coefficient
     * @param iL the intensity of the light
     * @return the color of the specular component
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, double nShininess, Color iL) {

        Vector r = l.subtract(n.scale(l.dotProduct(n)).scale(2)).normalize();
        double max = Math.max(0, -v.dotProduct(r));

        double maxNs = Math.pow(max, nShininess);
        Double3 ksMaxNs = ks.scale(maxNs);

        return iL.scale(ksMaxNs);
    }
}




