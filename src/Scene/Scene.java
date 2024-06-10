package Scene;

import com.google.gson.Gson;
import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Scene class represents a scene in three-dimensional space.
 * A scene is a collection of geometries and a background color.
 */
public class Scene {
    public String name;
    public Color background=Color.BLACK;
    public AmbientLight ambientLight= AmbientLight.NONE;
    public Geometries geometries=new Geometries();

    /**
     * Constructs a scene with the given name.
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
    }

    //setters
    /**
     * Sets the background color of the scene.
     * @param background The background color of the scene.
     * @return The scene object.
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     * @param ambientLight The ambient light of the scene.
     * @return The scene object.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries of the scene.
     * @param geometries The geometries of the scene.
     * @return The scene object.
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }



    public static Scene readFromFile(String filename) {
    try{
        FileReader reader = new FileReader(filename);
        Gson gson = new Gson();
        return gson.fromJson(reader, Scene.class);
    } catch(IOException e){
        throw new RuntimeException(e);
    }

    }





//    Gson gson = new Gson();
//    String str = gson.toJson(scene);
//        try {
//        FileWriter writer = new FileWriter("scene.json");
//        gson.toJson(scene, writer);
//    } catch (IOException e) {
//        throw new RuntimeException(e);
//    }




}
