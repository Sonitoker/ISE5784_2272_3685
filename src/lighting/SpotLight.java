package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

public class SpotLight extends PointLight{
   /** vector representing the direction of the light source */
    private Vector direction;
    private double narrowBeam = 1d;

    /**
     * Constructs a SpotLight object with the given direction, intensity, and position.
     * @param direction The direction of the light source.
     * @param intensity The intensity of the light source.
     * @param position The position of the light source.
     */
    public SpotLight(Vector direction, Color intensity, Point position) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public SpotLight setKc(double kC) {
        super.setKc(kC) ;
        return this;
    }

    @Override
    public SpotLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }

    @Override
    public SpotLight setKq(double kQ) {
        super.setKq(kQ);
        return this;
    }

    /**
     * Setter for the narrow beam of the light source.
     * @param narrowBeam the narrow beam of the light
     * @return the light source
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
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
        return  narrowBeam!=1d
        ?  super.getIntensity(p).scale(Math.pow(Math.max(0d, projection), narrowBeam))
        : (super.getIntensity(p).scale(Math.max(0d, projection)));
    }

    @Override
    public double getDistance(Point point) {
        return super.getDistance(point);
    }

}
