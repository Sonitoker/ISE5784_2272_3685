package primitives;

/**
 * Class representing a material in a scene.
 */
public class Material {
    /**
     * Constructs a new Material object.
     */
    public Material() {
    }

    /**
     * The diffuse reflection coefficient and the specular reflection coefficient.
     */
    public Double3 kD = Double3.ZERO, kS = Double3.ZERO;
    /**
     * The shininess coefficient.
     */
    public int nShininess = 0;
    /**
     * transparency attenuation coefficient
     */
    public Double3 kT = Double3.ZERO;
    /**
     * reflection attenuation coefficient
     */
    public Double3 kR = Double3.ZERO;

    /**
     * setter for kD- the diffuse reflection coefficient.
     *
     * @param kD the diffuse reflection coefficient.
     * @return Material after setting kD
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Setter for kD- the diffuse reflection coefficient.
     *
     * @param kD the diffuse reflection coefficient.
     * @return Material after setting kD
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Setter for kS- the specular reflection coefficient.
     *
     * @param kS the specular reflection coefficient.
     * @return Material after setting kS
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for kS- the specular reflection coefficient.
     *
     * @param kS the specular reflection coefficient.
     * @return Material after setting kS
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Setter for nShininess- the shininess coefficient.
     *
     * @param nShininess the shininess coefficient.
     * @return Material after setting nShininess
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * Setter for kT- the transparency attenuation coefficient.
     *
     * @param kT the transparency attenuation coefficient.
     * @return Material after setting kT
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Setter for kT- the transparency attenuation coefficient.
     *
     * @param kT the transparency attenuation coefficient.
     * @return Material after setting kT
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Setter for kR- the reflection attenuation coefficient.
     *
     * @param kR the reflection attenuation coefficient.
     * @return Material after setting kR
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Setter for kR- the reflection attenuation coefficient.
     *
     * @param kR the reflection attenuation coefficient.
     * @return Material after setting kR
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }


}
