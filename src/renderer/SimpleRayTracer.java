
package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import Scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * A class that inherits from the RayTracerBase class and implements the method
 */
public class SimpleRayTracer extends RayTracerBase {

    private static final Double3 INITIAL_K = Double3.ONE;

    private static final int MAX_CALC_COLOR_LEVEL = 10;

    private static final double MIN_CALC_COLOR_K = 0.001;




    /**
     * Calculates the transparency factor for a point with respect to a light source.
     *
     * @param geopoint The point for which transparency is calculated.
     * @param light    The light source.
     * @param l        The vector from the light source to the point.
     * @param n        The normal vector at the point.
     * @return The transparency factor as a Double3 representing (r, g, b) values.
     */
    private Double3 transparency(GeoPoint geopoint, LightSource light, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n); //build ray with delta
        double lightDistance = light.getDistance(geopoint.point);

        var intersections = this.scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) {
            return Double3.ONE; //no intersections
        }
        Double3 ktr = Double3.ONE;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
                ktr = ktr.product(gp.geometry.getMaterial().kT); //the more transparency the less shadow
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
            }
        }
        return ktr;
    }

    /**
     * constructor
     *
     * @param scene A scene where the department is initialized
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Get the color of an intersection point
     *
     * @param point point of intersection
     * @param ray   for the ray
     * @return Color of the intersection point
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return calcColor(point, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(this.scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color at a given intersection point.
     *
     * @param geoPoint The intersection point.
     * @param ray      The ray that intersected with the geometry.
     * @param level    The recursion level.
     * @param k        The attenuation factor.
     * @return The color at the intersection point.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = geoPoint.geometry.getEmission()
                .add(calcLocalEffects(geoPoint, ray, k));

        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    @Override
    public Color traceRay(Ray ray) {
        var point = this.findClosestIntersection(ray);
        if (point == null) {
            return scene.background;
        }
        return calcColor(point, ray);
    }


    /**
     * Calculates the local effects (diffuse and specular) at a given intersection point.
     *
     * @param gp  The intersection point.
     * @param ray The ray that intersected with the geometry.
     * @param k   The attenuation factor.
     * @return The color contribution from local effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;

        Material material = gp.geometry.getMaterial();
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                Double3 ktr = transparency(gp, lightSource, l, n); //intensity of shadow
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, v)));
                }
            }
        }
        return color;
    }


    /**
     * Calculates the global effects (reflection and refraction) at a given intersection point.
     *
     * @param gp    The intersection point.
     * @param ray   The ray that intersected with the geometry.
     * @param level The recursion level.
     * @param k     The attenuation factor.
     * @return The color contribution from global effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Material material = gp.geometry.getMaterial();

        color = color.add(calcRayEffect(gp, ray, level, k, material.kR, true));
        color = color.add(calcRayEffect(gp, ray, level, k, material.kT, false));

        return color;
    }

    /**
     * Calculates the color effect for a given ray (either reflection or refraction) at a given geo-point.
     *
     * @param gp the geo-point where the effect is calculated
     * @param ray the original ray that intersects with the geo-point
     * @param level the recursion depth level
     * @param k the attenuation coefficient from previous recursions
     * @param kEffect the reflection (kR) or refraction (kT) coefficient of the material at the geo-point
     * @param isReflection true if calculating reflection effect, false if calculating refraction effect
     * @return the color effect due to reflection or refraction
     */
    private Color calcRayEffect(GeoPoint gp, Ray ray, int level, Double3 k, Double3 kEffect, boolean isReflection) {
        if (kEffect.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;

        Double3 kkEffect = k.product(kEffect);
        if (kkEffect.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;

        Ray effectRay = isReflection ? constructReflected(gp, ray) : constructRefracted(gp, ray);
        GeoPoint effectPoint = findClosestIntersection(effectRay);
        if (effectPoint == null)
            return this.scene.background;

        return calcColor(effectPoint, effectRay, level - 1, kkEffect).scale(kEffect);
    }



    /**
     * find the closest intersection to the starting point of the ray
     *
     * @param ray the ray that intersect with the geometries of the scene
     * @return the geoPoint that is point is the closest point to the starting point of the ray
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(ray);
        return intersections == null ? null : ray.findClosestGeoPoint(intersections);
    }

    /**
     * Constructs a reflected ray at a given intersection point.
     *
     * @param gp  The intersection point.
     * @param ray The incident ray.
     * @return The reflected ray.
     */
    private Ray constructReflected(GeoPoint gp, Ray ray) {
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(v.dotProduct(n));
        // r = v - 2 * (v * n) * n
        Vector r = v.subtract(n.scale(2d * nv)).normalize();
        return new Ray(gp.point, r, n); //use the constructor with the normal for moving the head
    }


    /**
     * Constructs a refracted ray at a given intersection point.
     *
     * @param gp  The intersection point.
     * @param ray The incident ray.
     * @return The refracted ray.
     */
    private Ray constructRefracted(GeoPoint gp, Ray ray) {
        return new Ray(gp.point, ray.getDir(), gp.geometry.getNormal(gp.point));
    }


    /**
     * Calculation of specular light component
     *
     * @param material Attenuation coefficient for specular light component
     * @param n        normal to point
     * @param l        direction vector from light to point
     * @param v        direction of ray shot to point
     * @return Color of specular light component
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, Vector v) {
        Vector r = l.subtract(n.scale(2 * l.dotProduct(n))).normalize();
        double minusVR = -v.dotProduct(r);
        return alignZero(minusVR) <= 0 ? Double3.ZERO
                : material.kS.scale(Math.pow(minusVR, material.nShininess));
    }

    /**
     * Calculation of diffusion light component
     *
     * @param material normal to point
     * @param nl       dot product between n-normal to point and l-direction vector from light to point
     * @return Color of diffusion light component
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

}




//package renderer;
//import Scene.Scene;
//import lighting.LightSource;
//import primitives.*;
//
//import java.util.List;
//import geometries.Intersectable.GeoPoint;
//
//import static primitives.Util.alignZero;
//
///**
// * SimpleRayTracer class represents a simple ray tracer.
// * A simple ray tracer is a ray tracer that does not perform any ray tracing.
// */
//public class SimpleRayTracer extends RayTracerBase{
//
//
//    /**
//     * The maximum level of recursion for calculating the color of a point.
//     */
//    private static final int MAX_CALC_COLOR_LEVEL = 10;
//    /**
//     * the initial value of the color coefficient.
//     */
//    private static final Double3 INITIAL_K = Double3.ONE;
//    /**
//     * The minimum value of the color coefficient.
//     */
//    private static final double MIN_CALC_COLOR_K = 0.001;
//
//    /**
//     * Calculate partial shadow
//     *
//     * @param light    The light source
//     * @param l        The ray from the light source
//     * @param n        The normal to the intersection point
//     * @param geoPoint The intersection point
//     * @return The shadowing factor (Transparency factor)
//     */
//    private Double3 transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
//        // Calculate the continuous ray from the intersection point
//        Ray lightRay = new Ray(geoPoint.point, l.scale(-1), n);
//
//        // Check if there is any intersection between the object
//        // from the intersection point to the light source
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
//        // If no intersection the object is translucent (attenuation factor equal to 1)
//        if (intersections == null) return Double3.ONE;
//
//        // Calculate the distance from the light source to the intersection point
//        double lightDistance = light.getDistance(geoPoint.point);
//        // Initiate the transparency to 1 (the object is translucent)
//        Double3 ktr = Double3.ONE;
//
//        // For each intersection point with the geometries check if have an intersection
//        // between the intersection point to the light source
//        for (GeoPoint gp : intersections) {
//            // if there is an intersection point between the intersection point and the light source
//            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0)
//                // The intensity of the light is attenuate by the transparency attenuation factor
//                ktr = ktr.product(gp.geometry.getMaterial().kT);
//            // If the intensity of the light ray is too small, the object is opaque
//            if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
//        }
//        return ktr;
//    }
//
//
//
//
//
//
//    /**
//     * Constructs a SimpleRayTracer object with the given scene.
//     * @param scene The scene to render.
//     */
//    public SimpleRayTracer(Scene scene) {
//        super(scene);
//    }
//
//    /**
//     * Get color of the intersection of the ray with the scene
//     * @param ray Ray to trace
//     * @return Color of intersection
//     */
//    @Override
//    public Color traceRay(Ray ray) {
//        GeoPoint closestPoint = findClosestIntersection(ray);
//        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
//    }
//
//    /**
//     * Find the closest intersection point of the ray with the scene
//     * @param ray the ray
//     * @return the closest intersection point
//     */
//    private GeoPoint findClosestIntersection(Ray ray) {
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
//        return intersections == null?  null: ray.findClosestGeoPoint(intersections);
//    }
//
//    /**
//     * Get the color of an intersection point
//     * @param intersection point of intersection and the geometry
//     * @return Color of the intersection point
//     */
//    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
//        Color color = intersection.geometry.getEmission()
//                .add(calcLocalEffects(intersection, ray, k));
//        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
//    }
//
//    /**
//     * Get the color of an intersection point
//     * @param gp point of intersection and the geometry
//     * @param ray the ray
//     * @return the color of the intersection point
//     */
//    private Color calcColor(GeoPoint gp, Ray ray) {
//        return  calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
//                .add(scene.ambientLight.getIntensity());
//    }
//
//    /**
//     * Calculate the global effects of the intersection point
//     * @param gp the intersection point
//     * @param ray the ray
//     * @param level the level of recursion
//     * @param k the color coefficient
//     * @return the color of the intersection point
//     */
//    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
//
//
//        // Initiate the result color to BLACK (equal to (0,0,0))
//        Color color = Color.BLACK;
//        // The material type of the intersected geometry object
//        Material material = gp.geometry.getMaterial();
//        color=color.add(calcGlobalEffect(gp, ray, level, k,material.kR ,true))
//                .add(calcGlobalEffect(gp, ray, level, k, material.kT, false));
//
//        // Return the final color
//        return color;
//    }
//
//    /**
//     * Calculates the color effect for a given ray (either reflection or refraction) at a given geo-point.
//     *
//     * @param gp the geo-point where the effect is calculated
//     * @param ray the original ray that intersects with the geo-point
//     * @param level the recursion depth level
//     * @param k the attenuation coefficient from previous recursions
//     * @param kx the reflection (kR) or refraction (kT) coefficient of the material at the geo-point
//     * @param isReflection true if calculating reflection effect, false if calculating refraction effect
//     * @return the color effect due to reflection or refraction
//     */
//    private Color calcGlobalEffect(GeoPoint gp,Ray ray, int level, Double3 k, Double3 kx, boolean isReflection){
//
//      if (kx.lowerThan(MIN_CALC_COLOR_K))
//            return Color.BLACK;
//
//    Double3 kkx = k.product(kx);
//        if (kkx.lowerThan(MIN_CALC_COLOR_K))
//            return Color.BLACK;
//
//    Ray effectRay = isReflection ? constructReflectedRay(gp, ray) : constructRefractedRay(gp, ray);
//    GeoPoint effectPoint = findClosestIntersection(effectRay);
//        if (effectPoint == null)
//            return this.scene.background;
//
//        return calcColor(effectPoint, effectRay, level - 1, kkx).scale(kx);
//
//}
//
//    /**
//     * Construct the refracted ray
//     *@param gp The intersection point
//     * @param ray The ray from the camera to the intersection point
//     * @return the refracted ray
//     */
//    private Ray constructReflectedRay(GeoPoint gp, Ray ray) {
//        Vector n=gp.geometry.getNormal(gp.point);
//        Vector v=ray.getDir();
//        // Calculate the reflection ray
//        double nv = alignZero(n.dotProduct(v));
//        // Calculate the reflection vector r= v - 2 * (v*n)*n
//        Vector r = v.subtract(n.scale(nv).scale(2d)).normalize();
//        // Create the reflection ray, and return it
//        return new Ray(gp.point, r, n);
//    }
//
//    /**
//     * Calculate the refracted ray
//     *
//     * @param gp The intersection point
//     * @param ray The ray from the camera to the intersection point
//     * @return The refracted ray (Ray)
//     */
//    private Ray constructRefractedRay(GeoPoint gp, Ray ray) {
//        // Create the refracted ray and return it
//        return new Ray(gp.point, ray.getDir(), gp.geometry.getNormal(gp.point));
//    }
//
//    /**
//     * Calculate local effects (diffusive and specular) of the intersection point
//     * @param intersection point of intersection and the geometry
//     * @param ray the ray
//     * @return the color of the intersection point
//     */
//    private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) {
//        Vector n=intersection.geometry.getNormal(intersection.point);
//        Vector v=ray.getDir();
//        double nv=alignZero(n.dotProduct(v));
//        if (nv==0)
//            return Color.BLACK;
//        Material material=intersection.geometry.getMaterial();
//        Color color=Color.BLACK;
//        for(LightSource lightSource : scene.lights) {
//            Vector l=lightSource.getL(intersection.point);
//            double nl=alignZero(n.dotProduct(l));
//            if (nl*nv>0){ // sign(nl)==sign(nv)
//                Double3 ktr= transparency(lightSource, l, n, intersection);
//                if(ktr.product(k).greaterThan(MIN_CALC_COLOR_K  )){
//                    Color iL=lightSource.getIntensity(intersection.point).scale(ktr); // intensity of the light
//                    color=color.add(calcDiffusive(material.kD, iL, nl)
//                            .add(calcSpecular(material.kS,l,n,v, material.nShininess, iL)));
//                }
//            }
//        }
//        return color;
//    }
//
//    /**
//     * Calculate the specular component of the light
//     * @param ks the specular coefficient
//     * @param l the vector from the light source to the intersection point
//     * @param n the normal vector to the geometry at the intersection point
//     * @param v the vector from the intersection point to the camera
//     * @param nShininess the shininess coefficient
//     * @param iL the intensity of the light
//     * @return the color of the specular component
//     */
//    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, double nShininess, Color iL) {
//
//        Vector r = l.subtract(n.scale(l.dotProduct(n)).scale(2)).normalize();
//        double max = Math.max(0, -v.dotProduct(r));
//        double maxNs = Math.pow(max, nShininess);
//        Double3 ksMaxNs = ks.scale(maxNs);
//        return iL.scale(ksMaxNs);
//    }
//
//    /**
//     * Calculate the diffusive component of the light
//     * @param kD the diffusive coefficient
//     * @param lightIntensity the intensity of the light
//     * @param nl the dot product of the normal vector and the vector from the light source to the intersection point
//     * @return the color of the diffusive component
//     */
//    private Color calcDiffusive(Double3 kD, Color lightIntensity, double nl) {    // Calculate the diffuse intensity color
//        Color diffuse = lightIntensity.scale(kD.scale( Math.abs(nl)));
//        return diffuse;
//    }
//
//
//}
//
//
//
//
