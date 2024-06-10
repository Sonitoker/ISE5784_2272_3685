
package geometries;

import Scene.Scene;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometric shape in 3D space.
 */
public abstract class Geometry extends  Intersectable {

    protected Color emission= new Color(java.awt.Color.BLACK);

    /**
     * Computes and returns the normal vector to the geometry at a given point.
     * @param p The point at which to compute the normal vector.
     * @return The normal vector to the geometry at the given point.
     */
   public abstract Vector getNormal(Point p);

   /**
    * Getter for the emission color of the geometry
    * @return The emission color of the geometry
    */
   public Color getEmission() {
       return emission;
   }

    /**
     * Setter for the emission color of the geometry
     * @param emission The emission color of the geometry
     * @return The geometry object
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
}