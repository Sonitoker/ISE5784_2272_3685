package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The DirectionalLight class represents a directional light source in three-dimensional space.
 * A directional light source is defined by its direction and intensity.
 */
public class DirectionalLight extends Light implements LightSource {

    /**
     * vector representing the direction of the light source
     */
    private Vector direction;

    /**
     * constructs a DirectionalLight object with the given direction and intensity.
     *
     * @param direction The direction of the light source.
     * @param intensity The intensity of the light source.
     */
    public DirectionalLight(Vector direction, Color intensity) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return this.intensity;
    }

    @Override
    public Vector getL(Point p) {
        return this.direction.normalize();
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }


}
