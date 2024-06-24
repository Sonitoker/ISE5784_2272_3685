package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
    /**
     * The position of the light source.
     */
    protected Point position;
    /**
     * The constant attenuation coefficient.
     */
    private double kC=1d, kL=0d, kQ=0d;


    /**
     * Constructs a PointLight object with the given intensity and position.
     * @param intensity The intensity of the light source.
     * @param position The position of the light source.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }
    /**
     * Setter for kC- the constant attenuation coefficient.
     * @param kC
     * @return
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Setter for kL- the linear attenuation coefficient.
     * @param kL
     * @return
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Setter for kQ- the quadratic attenuation coefficient.
     * @param kQ
     * @return
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        // The intensity of the color of the light
        // is proportional to squared distance
        double d = position.distance(p);
        double factor = kC + kL * d + kQ * d * d;
        if(Util.isZero(factor))
            return intensity.scale(Double.POSITIVE_INFINITY);

        return intensity.scale(1d/factor);
    }

    @Override
    public Vector getL(Point p) {
        // Return the normalized vector from the light source to the point
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }

}
