package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Interface representing a light source in a scene.
 */
public interface LightSource {
    /**
     *  calculates the intensity of the light source at a given point.
     * @param p The point at which to get the intensity of the light source.
     * @return The intensity of the light source at the given point.
     */
    public Color getIntensity(Point p);
    /**
     * returns the vector from the light source to a given point.
     * @param p The point at which to get the vector from the light source.
     * @return The vector from the light source to the given point.
     */
    public Vector getL(Point p);

    /**
     * returns the distance from the light source to a given point.
     * @param point The point at which to get the distance from the light source.
     * @return The distance from the light source to the given point.
     */
    double getDistance(Point point);
}
