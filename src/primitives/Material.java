package primitives;

public class Material {
    public Double3 kD= Double3.ZERO, kS= Double3.ZERO;
    public int nShininess=0;

    /**
     * setter for kD- the diffuse reflection coefficient.
     * @param kD
     * @return Material after setting kD
     */
    Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Setter for kD- the diffuse reflection coefficient.
     * @param kD
     * @return Material after setting kD
     */
    Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }
    /**
     * Setter for kS- the specular reflection coefficient.
     * @param kS
     * @return Material after setting kS
     */
    Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for kS- the specular reflection coefficient.
     * @param kS
     * @return Material after setting kS
     */
    Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }
    /**
     * Setter for nShininess- the shininess coefficient.
     * @param nShininess
     * @return Material after setting nShininess
     */
    Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
