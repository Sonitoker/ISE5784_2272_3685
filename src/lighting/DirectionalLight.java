package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{

    /** vector representing the direction of the light source */
    private Vector direction;

    /**
     * constructs a DirectionalLight object with the given direction and intensity.
     * @param direction
     * @param intensity
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


}
