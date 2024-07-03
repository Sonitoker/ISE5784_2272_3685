
package renderer;

import static java.awt.Color.*;

import geometries.*;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;

import primitives.*;
import Scene.Scene;

public class Minip {
    private final Scene scene = new Scene("Test scene");
    Material birdM = new Material().setKd(0.2).setKs(0.6).setShininess(300);
    private final Camera.Builder camera = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene))
            .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(130)
            .setVpSize(280, 280);

    Material windowsMaterial = new Material().setKd(0.5).setKt(0.8).setKr(0.05);
    Material frameMaterial = new Material().setKd(0.5);
    private final Point pointMoon = new Point(70, 80, -120);
    private final Point pointStar1 = new Point(20, 40, -115);
    private final Point pointStar2 = new Point(-20, 30, -115);
    private final Point pointStar3 = new Point(-30, 90, -115);
    private final Point pointStar4 = new Point(50, 50, -115);
    private final Point pointStar5 = new Point(-40, 60, -115);
    private final Point pointStar6 = new Point(30, 100, -115);
    private final Point pointStar7 = new Point(80, 60, -115);
    private final Point pointStar8 = new Point(90, 70, -115);
    private final Point pointHomeWindows1 = new Point(42.5, -45, -82.5);
    private final Point pointHomeWindows2 = new Point(67.5, -45, -107.5);


    private final Geometry windows1 = new Polygon(new Point(10, 100, -70), new Point(90, 100, -70), new Point(90, -35, -70), new Point(10, -35, -70))//
            .setMaterial(windowsMaterial);
    private final Geometry windows2 = new Polygon(new Point(50, 100, -74), new Point(90, 100, -74), new Point(90, -35, -74), new Point(50, -35, -74))//
            .setMaterial(windowsMaterial);
    private final Geometry frame11 = new Polygon(new Point(8, 100, -70), new Point(10, 100, -70), new Point(10, -40, -70), new Point(8, -40, -70))//
            .setMaterial(frameMaterial).setEmission(new Color(20, 20, 20));
    private final Geometry frame12 = new Polygon(new Point(8, -35, -70), new Point(90, -35, -70), new Point(90, -40, -70), new Point(8, -40, -70))//
            .setMaterial(frameMaterial).setEmission(new Color(20, 20, 20));
    private final Geometry frame21 = new Polygon(new Point(48, 100, -74), new Point(50, 100, -74), new Point(50, -40, -74), new Point(48, -40, -74))//
            .setMaterial(frameMaterial).setEmission(new Color(20, 20, 20));
    private final Geometry frame22 = new Polygon(new Point(48, -35, -74), new Point(90, -35, -74), new Point(90, -40, -74), new Point(48, -40, -74))//
            .setMaterial(frameMaterial).setEmission(new Color(20, 20, 20));
    private final Geometry windowRail11 = new Polygon(new Point(-50, 100, -68), new Point(-49, 100, -68), new Point(-49, -40, -68), new Point(-50, -40, -68))//
            .setMaterial(frameMaterial).setEmission(new Color(100, 100, 100));
    private final Geometry windowRail12 = new Polygon(new Point(-50, -39, -68), new Point(90, -39, -68), new Point(90, -40, -68), new Point(-50, -40, -68))//
            .setMaterial(frameMaterial).setEmission(new Color(100, 100, 100));
    private final Geometry windowRail21 = new Polygon(new Point(-50, 100, -72), new Point(-49, 100, -72), new Point(-49, -40, -72), new Point(-50, -40, -72))//
            .setMaterial(frameMaterial).setEmission(new Color(100, 100, 100));
    private final Geometry windowRail22 = new Polygon(new Point(-50, -39, -72), new Point(90, -39, -72), new Point(90, -40, -72), new Point(-50, -40, -72))//
            .setMaterial(frameMaterial).setEmission(new Color(100, 100, 100));
    private final Geometry windowRail31 = new Polygon(new Point(-50, 100, -76), new Point(-49, 100, -76), new Point(-49, -40, -76), new Point(-50, -40, -76))//
            .setMaterial(frameMaterial).setEmission(new Color(100, 100, 100));
    private final Geometry windowRail32 = new Polygon(new Point(-50, -39, -76), new Point(90, -39, -76), new Point(90, -40, -76), new Point(-50, -40, -76))//
            .setMaterial(frameMaterial).setEmission(new Color(100, 100, 100));
    private final Geometry windowsill = new Polygon(new Point(-50, -40, -100), new Point(100, -40, -100), new Point(100, -40, -60), new Point(-50, -40, -60))//
            .setMaterial(new Material().setKd(0.5)).setEmission(new Color(GRAY));/*.setKr(0.7).setSpreadingR(0.3)*/
    private final Geometry lintel = new Polygon(new Point(-50, 100, -60), new Point(-50, 100, -100), new Point(-50, -40, -100), new Point(-50, -40, -60))//
            .setMaterial(new Material().setKd(0.5)).setEmission(new Color(GRAY));
    private final Geometry wall1 = new Polygon(new Point(-90, 100, -60), new Point(-50, 100, -60), new Point(-50, -40, -60), new Point(-90, -40, -60))//
            .setMaterial(new Material().setKd(0.5)).setEmission(new Color(22, 147, 147));
    private final Geometry wall2 = new Polygon(new Point(-90, -40, -60), new Point(90, -40, -60), new Point(90, -70, -60), new Point(-90, -70, -60))//
            .setMaterial(new Material().setKd(0.5)).setEmission(new Color(22, 147, 147));
    private final Geometry plane = new Plane(new Point(0, -70, 0), new Vector(0, 1, 0)).setMaterial(new Material().setKd(0.5)).setEmission(new Color(68, 42, 10));
    private final Geometry homeWall1 = new Polygon(new Point(-20, -30, -195), new Point(30, -30, -170), new Point(30, -70, -170), new Point(-20, -70, -195))//
            .setMaterial(new Material().setKd(0.1)).setEmission(new Color(GRAY));
    private final Geometry homeWall2 = new Polygon(new Point(30, -30, -170), new Point(80, -30, -220), new Point(80, -70, -220), new Point(30, -70, -170))//
            .setMaterial(new Material().setKd(0.1)).setEmission(new Color(GRAY));
    private final Geometry homeDoor = new Polygon(new Point(-0.5, -45, -185), new Point(9.5, -45, -180), new Point(9.5, -70, -180), new Point(-0.5, -70, -185))//
            .setMaterial(new Material().setKd(0.1)).setEmission(new Color(73, 46, 0));
    private final Geometry homeWindows1 = new Polygon(new Point(38, -40, -177.5), new Point(48, -40, -177.5), new Point(48, -50, -177.5), new Point(38, -50, -177.5))//
            .setMaterial(new Material().setKd(0.1).setKt(0.9)).setEmission(new Color(GRAY));
    private final Geometry homeWindows2 = new Polygon(new Point(62, -40, -200), new Point(72, -40, -210), new Point(72, -50, -210), new Point(62, -50, -200))//
            .setMaterial(new Material().setKd(0.1).setKt(0.9)).setEmission(new Color(GRAY));
    private final Geometry homeRoof1 = new Triangle(new Point(-20, -30, -195), new Point(30, -30, -170), new Point(5, 0, -182.5))//
            .setMaterial(new Material().setKd(0.1)).setEmission(new Color(RED));
    private final Geometry homeRoof2 = new Polygon(new Point(5, 0, -170), new Point(55, 0, -220), new Point(80, -30, -220), new Point(30, -30, -170))//
            .setMaterial(new Material().setKd(0.1)).setEmission(new Color(RED));
    private final Geometry sphereMoon1 = new Sphere(10d, pointMoon).setEmission(new Color(220, 220, 70)) //
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.65));
    private final Geometry sphereMoon2 = new Sphere(3.5d, pointMoon).setEmission(new Color(220, 220, 70)) //
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.75));
    private final Geometry sphereMoon3 = new Sphere(2d, pointMoon).setEmission(new Color(220, 220, 70)) //
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.85));
    private final Geometry sphereMoon4 = new Sphere(1d, pointMoon).setEmission(new Color(220, 220, 70)) //
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.95));
    private final Geometry sphereStar1 = new Sphere(1d, pointStar1).setEmission(new Color(220, 220, 70)) //
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.5));
    private final Geometry sphereStar2 = new Sphere(1d, pointStar2).setEmission(new Color(220, 220, 30)) //
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.5));
    private final Geometry sphereStar3 = new Sphere(1d, pointStar3).setEmission(new Color(220, 220, 30)) //
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.5));
    private final Geometry sphereStar4 = new Sphere(1d, pointStar4).setEmission(new Color(220, 220, 30)) //
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.5));
    private final Geometry sphereStar5 = new Sphere(1d, pointStar5).setEmission(new Color(220, 220, 30)) //
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.5));
    private final Geometry sphereStar6 = new Sphere(1d, pointStar6).setEmission(new Color(220, 220, 30)) //
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.5));
    private final Geometry sphereStar7 = new Sphere(1d, pointStar7).setEmission(new Color(220, 220, 30)) //
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.5));
    private final Geometry sphereStar8 = new Sphere(1d, pointStar8).setEmission(new Color(220, 220, 30)) //
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.5));

    /**
     * Produce a picture with all the effects
     */


    @Test
    public void theFinal() {
        scene.geometries.add(sphereMoon1, sphereMoon2, sphereMoon3, sphereMoon4, sphereStar1, sphereStar2, sphereStar3,
                sphereStar4, sphereStar5, sphereStar6, wall1, wall2, windows1, windows2, windowsill, lintel, frame11, frame12,
                frame21, frame22, homeWall1, homeWall2, homeRoof1, homeRoof2, plane, homeWindows1, homeWindows2, homeDoor,
                windowRail11, windowRail12, windowRail21, windowRail22, windowRail31, windowRail32
        );

        scene.lights.add(//
                new PointLight(new Color(100, 90, 40), pointMoon) //
                        .setKl(0.0004).setKq(0.0000006));
        scene.lights.add(//
                new PointLight(new Color(50, 40, 20), pointStar1) //
                        .setKl(0.000004).setKq(0.000000006));
        scene.lights.add(//
                new PointLight(new Color(50, 40, 20), pointStar2) //
                        .setKl(0.000004).setKq(0.000000006));
        scene.lights.add(//
                new PointLight(new Color(50, 40, 20), pointStar3) //
                        .setKl(0.000004).setKq(0.000000006));
        scene.lights.add(//
                new PointLight(new Color(50, 40, 20), pointStar4) //
                        .setKl(0.000004).setKq(0.000000006));
        scene.lights.add(//
                new PointLight(new Color(50, 40, 20), pointStar5) //
                        .setKl(0.000004).setKq(0.000000006));
        scene.lights.add(//
                new PointLight(new Color(50, 40, 20), pointStar6) //
                        .setKl(0.000004).setKq(0.000000006));
        scene.lights.add(//
                new PointLight(new Color(10, 8, 4), new Point(0, 0, 0)) //
                        .setKl(0.004).setKq(0.000006));
        scene.lights.add(//
                new SpotLight(homeWall2.getNormal(pointHomeWindows1), new Color(50, 40, 20), pointHomeWindows1) //
                        .setKl(0.004).setKq(0.000006));
        scene.lights.add(//
                new SpotLight(homeWall2.getNormal(pointHomeWindows2), new Color(50, 40, 20), pointHomeWindows2) //
                        .setKl(0.004).setKq(0.000006));


        camera
                .setImageWriter(new ImageWriter("MINIP test", 1000, 1000))
                .build()
                .renderImage()
                .writeToImage();

    }
}

////    @Test
////    public void theFinal1() {
////        scene.geometries.add(sphereMoon1, sphereMoon2, sphereMoon3, sphereMoon4, sphereStar1,sphereStar2,sphereStar3,
////                sphereStar4,sphereStar5,sphereStar6,wall1, wall2, windows1,windows2, windowsill, lintel, frame11,frame12,
////                frame21,frame22, homeWall1,homeWall2,homeRoof1,homeRoof2, plane,homeWindows1,homeWindows2, homeDoor,
////                windowRail11, windowRail12, windowRail21, windowRail22, windowRail31, windowRail32
////        );
////        scene.lights.add(//
////                new PointLight(new Color(100, 90, 40), pointMoon) //
////                        .setKl(0.0004).setKq(0.0000006));
////        scene.lights.add(//
////                new PointLight(new Color(50, 40, 20), pointStar1) //
////                        .setKl(0.000004).setKq(0.000000006));
////        scene.lights.add(//
////                new PointLight(new Color(50, 40, 20),pointStar2) //
////                        .setKl(0.000004).setKq(0.000000006));
////        scene.lights.add(//
////                new PointLight(new Color(50, 40, 20), pointStar3) //
////                        .setKl(0.000004).setKq(0.000000006));
////        scene.lights.add(//
////                new PointLight(new Color(50, 40, 20), pointStar4) //
////                        .setKl(0.000004).setKq(0.000000006));
////        scene.lights.add(//
////                new PointLight(new Color(50, 40, 20), pointStar5) //
////                        .setKl(0.000004).setKq(0.000000006));
////        scene.lights.add(//
////                new PointLight(new Color(50, 40, 20), pointStar6) //
////                        .setKl(0.000004).setKq(0.000000006));
////        scene.lights.add(//
////                new PointLight(new Color(10, 8, 4), new Point(0,0, 0)) //
////                        .setKl(0.004).setKq(0.000006));
////        scene.lights.add(//
////                new SpotLight(homeWall2.getNormal(pointHomeWindows1),new Color(50, 40, 20), pointHomeWindows1) //
////                        .setKl(0.004).setKq(0.000006));
////        scene.lights.add(//
////                new SpotLight( homeWall2.getNormal(pointHomeWindows2),new Color(50, 40, 20), pointHomeWindows2) //
////                        .setKl(0.004).setKq(0.000006));
////
////
////        camera
////                .setImageWriter(new ImageWriter("final test", 1000, 1000))
////                .build()
////                .renderImage()
////                .writeToImage();
////
////    }
//
//
//}
