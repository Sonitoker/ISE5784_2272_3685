package renderer;




import static Scene.Scene.readSceneFromFile;
import static java.awt.Color.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import geometries.*;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import primitives.*;
import Scene.Scene;

import java.io.FileReader;
import java.io.IOException;

/**
 * Test rendering a basic image
 * @author Dan
 */
public class RenderTests {
    /** Scene of the tests */
    private final Scene          scene  = new Scene("Test scene");
    /** Camera builder of the tests */
    private final Camera.Builder camera = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene))
            .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0,1,0))
            .setVpDistance(100)
            .setVpSize(500, 500);

    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     */
    @Test
    public void renderTwoColorTest() {
        scene.geometries.add(new Sphere(50d, new Point(0, 0, -100)),
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
                // left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                        new Point(-100, -100, -100)), // down
                // left
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
        scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), Double3.ONE))
                .setBackground(new Color(75, 127, 90));

        // right
        camera
                .setImageWriter(new ImageWriter("base render test", 1000, 1000))
                .build()
                .renderImage()
                .printGrid(100, new Color(YELLOW))
                .writeToImage();
    }

    // For stage 6 - please disregard in stage 5
    /**
     * Produce a scene with basic 3D model - including individual lights of the
     * bodies and render it into a png image with a grid
     */
    @Test
    public void renderMultiColorTest() {
        scene.geometries.add( // center
                new Sphere(50d, new Point(0, 0, -100)),
                // up left
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100))
                        .setEmission(new Color(GREEN)),
                // down left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100))
                        .setEmission(new Color(RED)),
                // down right
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))
                        .setEmission(new Color(BLUE)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2, 0.2, 0.2))); //

        camera
                .setImageWriter(new ImageWriter("color render test", 1000, 1000))
                .build()
                .renderImage()
                .printGrid(100, new Color(WHITE))
                .writeToImage();


        //scene.writeSceneToFile(scene,"scene.json");

    }

    /** Test for json based scene - for bonus */
    @Test
    public void basicRenderJson() {



            final Camera.Builder newCameraBuilder = Camera.getBuilder()
                    .setDirection(new Vector(1, 0, 0), new Vector(0, 0, 1));

            scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255).reduce(6), new Double3(0.15)));
            scene.lights.add(new SpotLight(new Vector(1, 0, 0), new Color(YELLOW), new Point(-300, 6, 10)));

            double angle = 0;
            double height = 0;

            scene.geometries.add(new Plane(new Point(-4, 4, 0), new Vector(0, 0, 1))
                    .setMaterial(new Material().setKd(0.8).setKs(0.6).setShininess(100).setKt(0.7).setKr(0.5)));

            java.awt.Color[] colors = {MAGENTA, CYAN, GREEN, ORANGE};

            for (int i = 25; i < 200; ++i) {
                int colorIndex = i % colors.length;

                scene.geometries
                        .add(new Sphere(0.5, new Point(i / 25.0 * Math.cos(angle), i / 25.0 * Math.sin(angle), height))
                                .setEmission(new Color(colors[colorIndex]).reduce(2.2))
                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(80).setKt(0.3)));

                angle += Math.PI / 15.0;
                height += 0.15;
            }

            java.awt.Color[] colors2 = {RED, YELLOW, CYAN, WHITE, GREEN, ORANGE};

            height = 10;
            for (int i = 25; i < 50; ++i) {
                int colorIndex = i % colors2.length;

                scene.geometries.add(new Sphere(0.3, new Point(i / 25.0 * Math.cos(angle), i * Math.sin(angle), height))
                        .setEmission(new Color(colors2[colorIndex]).reduce(2.2))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(80).setKt(0.3)));

                angle += Math.PI / 30.0;
                height += 0.5;
            }

            height = 10;
            for (int i = 25; i < 300; ++i) {

                scene.geometries.add(new Sphere(0.05, new Point(i / 25.0 * Math.cos(angle), i * Math.sin(angle), height))
                        .setEmission(new Color(GREEN))
                        .setMaterial(new Material().setKd(1).setKs(1d).setShininess(100).setKt(1)));

                angle += Math.PI / 60.0;
                height += 0.1 % 50;
            }

            scene.lights.add(new SpotLight(new Vector(1, 0, 0), new Color(255, 255, 255).reduce(2), new Point(-150, 0, 5)));
            scene.lights.add(new SpotLight(new Vector(1, 0, 0), new Color(RED).reduce(2), new Point(50, 0, 5)));

            scene.setBackground(new Color(WHITE).reduce(TRANSLUCENT));

            ImageWriter imageWriter = new ImageWriter("myNewShape", 500, 500);

            newCameraBuilder.setLocation(new Point(-330, 0, 5))
                    .setVpDistance(1000)
                    .setVpSize(200, 200)
                    .setRayTracer(new SimpleRayTracer(scene))
                    .setImageWriter(imageWriter)
                    .build()
                    .renderImage()
                    .writeToImage();



    }




}
