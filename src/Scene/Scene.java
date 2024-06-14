package Scene;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import geometries.Geometries;
import geometries.Intersectable;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;
import primitives.Point;
import primitives.Ray;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * The Scene class represents a scene in three-dimensional space.
 * A scene is a collection of geometries and a background color.
 */
public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();
    List<LightSource> lights= new LinkedList<>();
    /**
     * Constructs a scene with the given name.
     *
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
    }

    //setters

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

    //bonus 5
    /**
     * reeads a scene from a file in JSON format.
     * @param filename The name of the file to read the scene from.
     * @return The scene object.
     */
    static public Scene readSceneFromFile(String filename) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Intersectable.class, new InterfaceAdapter());
        Gson gson = gsonBuilder.create();
        try {
            return gson.fromJson(new FileReader(filename), Scene.class);
        } catch (IOException e) {
            throw new  RuntimeException(e);
        }
    }

    /**
     * Writes the scene to a file in JSON format.
     *
     * @param filename The name of the file to write the scene to.
     */
    public void writeSceneToFile(String filename) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Intersectable.class, new InterfaceAdapter());
        Gson gson = gsonBuilder.create();
        try {
            gson.toJson(this, new FileWriter(filename));
        } catch (IOException e) {
            throw new  RuntimeException(e);
        }
    }



}
