package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

public class SpotLight extends PointLight{
   /** vector representing the direction of the light source */
    private Vector direction;

    /**
     * Constructs a SpotLight object with the given direction, intensity, and position.
     * @param direction The direction of the light source.
     * @param intensity The intensity of the light source.
     * @param position The position of the light source.
     */
    public SpotLight(Vector direction, Color intensity, Point position) {
        super(intensity, position);
        this.direction = direction;
    }
    /**
     * Setter for kC- the constant attenuation coefficient.
     * @param kC
     * @return
     */
    SpotLight setKc(double kC) {
        super.setKc(kC) ;
        return this;
    }

    /**
     * Setter for kL- the linear attenuation coefficient.
     * @param kL
     * @return
     */
    SpotLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }

    /**
     * Setter for kQ- the quadratic attenuation coefficient.
     * @param kQ
     * @return
     */
    SpotLight setKq(double kQ) {
        super.setKq(kQ);
        return this;
    }

    @Override
    public Color getIntensity(Point p) {

        double projection = direction.dotProduct(getL(p));
        // If the point is behind the light source
        if (Util.isZero(projection)) {
            return Color.BLACK;
        }
        // The intensity of the color of the light
        return (super.getIntensity(p).scale(Math.max(0, projection)));
    }
}
