package Scene;



import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * The Scene class represents a scene in three-dimensional space.
 * A scene is a collection of geometries and a background color.
 */
public class Scene {
    /**
     * The name of the scene.
     */
    public String name;
    /**
     * The background color of the scene.
     */
    public Color background = Color.BLACK;
    /**
     * The ambient light of the scene.
     */
    public AmbientLight ambientLight = AmbientLight.NONE;
    /**
     * The geometries of the scene.
     */
    public Geometries geometries = new Geometries();
    /**
     * The lights of the scene.
     */
    public List<LightSource> lights = new LinkedList<>();
    /**
     * The BVH improvement flag
     */
    public boolean isBVH = false;

    /**
     * Constructs a scene with the given name.
     *
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
    }


    /**
     * Sets the background color of the scene.
     *
     * @param background The background color of the scene.
     * @return The scene object.
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }


    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight The ambient light of the scene.
     * @return The scene object.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries of the scene.
     *
     * @param geometries The geometries of the scene.
     * @return The scene object.
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the lights of the scene.
     *
     * @param lights The lights of the scene.
     * @return The scene object.
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}

