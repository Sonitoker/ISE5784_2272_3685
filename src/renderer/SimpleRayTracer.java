
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
     * The maximum level of recursion for calculating the color of a point.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * the initial value of the color coefficient.
     */
    private static final Double3 INITIAL_K = Double3.ONE;
    /**
     * The minimum value of the color coefficient.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * Calculate partial shadow
     *
     * @param light    The light source
     * @param l        The ray from the light source
     * @param n        The normal to the intersection point
     * @param geoPoint The intersection point
     * @return The shadowing factor (Transparency factor)
     */
    private Double3 transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
        // Calculate the continuous ray from the intersection point
        Ray lightRay = new Ray(geoPoint.point, l.scale(-1), n);

        // Check if there is any intersection between the object
        // from the intersection point to the light source
        List<GeoPoint> intersections;
            intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(geoPoint.point));
        if (intersections == null) return Double3.ONE;

        // Initiate the transparency to 1 (the object is translucent)
        Double3 ktr = Double3.ONE;

        // For each intersection point with the geometries check if have an intersection
        // between the intersection point to the light source
        for (GeoPoint gp : intersections) {
                ktr = ktr.product(gp.geometry.getMaterial().kT);
            // If the intensity of the light ray is too small, the object is opaque
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
        }
        return ktr;
    }






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
        ray.setBVH(scene.isBVH);
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * Find the closest intersection point of the ray with the scene
     * @param ray the ray
     * @return the closest intersection point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections;
            intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null?  null: ray.findClosestGeoPoint(intersections);
    }

    /**
     * Get the color of an intersection point
     * @param intersection point of intersection and the geometry
     * @return Color of the intersection point
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = intersection.geometry.getEmission()
                .add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Get the color of an intersection point
     * @param gp point of intersection and the geometry
     * @param ray the ray
     * @return the color of the intersection point
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return  calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculate the global effects of the intersection point
     * @param gp the intersection point
     * @param ray the ray
     * @param level the level of recursion
     * @param k the color coefficient
     * @return the color of the intersection point
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {


        // Initiate the result color to BLACK (equal to (0,0,0))
        Color color = Color.BLACK;
        // The material type of the intersected geometry object
        Material material = gp.geometry.getMaterial();
        color=color.add(calcGlobalEffect(gp, ray, level, k,material.kR ,true))
                .add(calcGlobalEffect(gp, ray, level, k, material.kT, false));

        // Return the final color
        return color;
    }

    /**
     * Calculates the color effect for a given ray (either reflection or refraction) at a given geo-point.
     *
     * @param gp the geo-point where the effect is calculated
     * @param ray the original ray that intersects with the geo-point
     * @param level the recursion depth level
     * @param k the attenuation coefficient from previous recursions
     * @param kx the reflection (kR) or refraction (kT) coefficient of the material at the geo-point
     * @param isReflection true if calculating reflection effect, false if calculating refraction effect
     * @return the color effect due to reflection or refraction
     */
    private Color calcGlobalEffect(GeoPoint gp,Ray ray, int level, Double3 k, Double3 kx, boolean isReflection){

      if (kx.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;

    Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;

    Ray effectRay = isReflection ? constructReflectedRay(gp, ray) : constructRefractedRay(gp, ray);
    GeoPoint effectPoint = findClosestIntersection(effectRay);
        if (effectPoint == null)
            return this.scene.background;

        return calcColor(effectPoint, effectRay, level - 1, kkx).scale(kx);

}

    /**
     * Construct the refracted ray
     *@param gp The intersection point
     * @param ray The ray from the camera to the intersection point
     * @return the refracted ray
     */
    private Ray constructReflectedRay(GeoPoint gp, Ray ray) {
        Vector n=gp.geometry.getNormal(gp.point);
        Vector v=ray.getDir();
        // Calculate the reflection ray
        double nv = alignZero(n.dotProduct(v));
        // Calculate the reflection vector r= v - 2 * (v*n)*n
        Vector r = v.subtract(n.scale(nv).scale(2d)).normalize();
        // Create the reflection ray, and return it
        return new Ray(gp.point, r, n);
    }

    /**
     * Calculate the refracted ray
     *
     * @param gp The intersection point
     * @param ray The ray from the camera to the intersection point
     * @return The refracted ray (Ray)
     */
    private Ray constructRefractedRay(GeoPoint gp, Ray ray) {
        // Create the refracted ray and return it
        return new Ray(gp.point, ray.getDir(), gp.geometry.getNormal(gp.point));
    }

    /**
     * Calculate local effects (diffusive and specular) of the intersection point
     * @param intersection point of intersection and the geometry
     * @param ray the ray
     * @return the color of the intersection point
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) {
        Vector n=intersection.geometry.getNormal(intersection.point);
        Vector v=ray.getDir();
        double nv=alignZero(n.dotProduct(v));
        if (nv==0)
            return Color.BLACK;
        Material material=intersection.geometry.getMaterial();
        Color color=Color.BLACK;
        for(LightSource lightSource : scene.lights) {
            Vector l=lightSource.getL(intersection.point);
            double nl=alignZero(n.dotProduct(l));
            if (nl*nv>0){ // sign(nl)==sign(nv)
                Double3 ktr= transparency(lightSource, l, n, intersection);
                if(ktr.product(k).greaterThan(MIN_CALC_COLOR_K  )){
                    Color iL=lightSource.getIntensity(intersection.point).scale(ktr); // intensity of the light
                    color=color.add(calcDiffusive(material.kD, iL, nl)
                            .add(calcSpecular(material.kS,l,n,v, material.nShininess, iL)));
                }
            }
        }
        return color;
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

    /**
     * Calculate the diffusive component of the light
     * @param kD the diffusive coefficient
     * @param lightIntensity the intensity of the light
     * @param nl the dot product of the normal vector and the vector from the light source to the intersection point
     * @return the color of the diffusive component
     */
    private Color calcDiffusive(Double3 kD, Color lightIntensity, double nl) {    // Calculate the diffuse intensity color
        Color diffuse = lightIntensity.scale(kD.scale( Math.abs(nl)));
        return diffuse;
    }


}




