package lighting;

import primitives.Color;
import primitives.Double3;
/**
 * AmbientLight class represents a light source that lights all the scene equally.
 * The light intensity is constant and does not depend on the direction of the light.
 * The light intensity is multiplied by the material's ambient coefficient.
 */
public class AmbientLight {
    private final Color intensity; // The light color
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0);
    /**
     * Constructs an AmbientLight object with the given intensity factor.
     * @param ka The light intensity factor.
     */
    public AmbientLight(Color Ia, Double3 ka) {
          this.intensity = Ia.scale(ka);
    }

    /**
     * Constructs an AmbientLight object with the given intensity factor.
     * @param ka The light intensity factor.
     */
    public AmbientLight(Color Ia, double ka) {
        this.intensity = Ia.scale(ka);
    }

    /**
     * Returns the light intensity factor.
     * @return The light intensity factor.
     */
    public Color getIntensity() {
        return intensity;
    }
}
