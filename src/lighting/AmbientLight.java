package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * AmbientLight class represents a light source that lights all the scene equally.
 * The light intensity is constant and does not depend on the direction of the light.
 * The light intensity is multiplied by the material's ambient coefficient.
 */
public class AmbientLight extends Light {
    /**
     * A constant AmbientLight object with no intensity.
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * Constructs an AmbientLight object with the given intensity factor.
     *
     * @param ka The light intensity factor.
     * @param Ia The light intensity.
     */
    public AmbientLight(Color Ia, Double3 ka) {
        super(Ia.scale(ka));
    }

    /**
     * Constructs an AmbientLight object with the given intensity factor.
     *
     * @param ka The light intensity factor.
     * @param Ia The light intensity.
     */
    public AmbientLight(Color Ia, double ka) {
        super(Ia.scale(ka));
    }
}
