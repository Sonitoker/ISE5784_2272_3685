package geometries;

/**
 * The RadialGeometry class is an abstract class representing geometries with radial properties.
 * It implements the Geometry interface.
 */
public abstract class RadialGeometry implements Geometry {

    /** The radius of the geometry. */
    protected double radius;

    /**
     * Constructs a RadialGeometry object with the given radius.
     * @param radius The radius of the geometry.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
