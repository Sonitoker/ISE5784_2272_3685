package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


import java.util.*;
import java.util.stream.IntStream;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Camera class represents a camera in the 3D space.
 * The camera has a location, a direction, and a size of the view plane.
 * The camera can construct a ray through a pixel in the view plane.
 */
public class Camera implements Cloneable {


    private Point p0;
    private Vector vTo, vUp, vRight;
    private double width = 0d, height = 0d, distance = 0d;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private Blackboard blackboard;
    private boolean BVH = false;

    ///
    private int threadsCount = 0; // -2 auto, -1 range/stream, 0 no threads, 1+ number of threads
    private double printInterval = 0; // printing progress percentage interval



    /**
     * camera getter
     *
     * @return the width of the view plane
     */
    public double getWidth() {
        return width;
    }

    /**
     * camera getter
     *
     * @return the height of the view plane
     */
    public double getHeight() {
        return height;
    }

    /**
     * camera getter
     *
     * @return the distance from the camera to the view plane
     */
    public double getDistance() {
        return distance;
    }

    /**
     * camera constructor
     */
    private Camera() {
    }

    /**
     * Builder class for the camera
     *
     * @return the camera builder
     */
    public static Builder getBuilder() {
        return new Builder();
    }


    /**
     * Camera builder class
     * The builder constructs a camera with a location, a direction, and a size of the view plane.
     *
     */
    public static class Builder {
        /**
         * The camera to build
         */
        private final Camera camera = new Camera();

        ///
         private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores



        /**
         * Set the location of the camera
         *
         * @param p0 the location of the camera
         * @return the camera builder
         */
        public Builder setLocation(Point p0) {
            camera.p0 = p0;
            return this;
        }

        /**
         * Set the direction of the camera
         *
         * @param vTo the direction of the camera- the vector from the camera to the view plane
         * @param vUp the up direction of the camera- the vector from the camera to the top
         * @return the camera builder
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (!isZero(vTo.dotProduct(vUp))) {
                throw new IllegalArgumentException("the vectors must be orthogonal");
            }
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * Set the size of the view plane
         *
         * @param width  the width of the view plane
         * @param height the height of the view plane
         * @return the camera builder
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("width and height must be positive");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Set the distance between the camera and the view plane
         *
         * @param distance the distance between the camera and the view plane
         * @return the camera builder
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("distance must be positive");
            }
            camera.distance = distance;
            return this;
        }

        /**
         * Set the image writer
         *
         * @param imageWriter the image writer
         * @return the camera builder
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Set the ray tracer
         *
         * @param rayTracer the ray tracer
         * @return the camera builder
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Set the blackboard
         *
         * @param blackboard the blackboard
         * @return the camera builder
         */
        public Builder setBlackboard(Blackboard blackboard) {
            camera.blackboard = blackboard;
            return this;
        }

        /**
         * Set the BVH
         *
         * @param BVH the BVH
         * @return the camera builder
         */
        public Builder setBVH(boolean BVH) {
            camera.BVH = BVH;
            return this;
        }

        ///
        public Builder setMultithreading(int threads) {
            if (threads < -2) throw new IllegalArgumentException("Multithreading must be -2 or higher");
            if (threads >= -1) camera.threadsCount = threads;
            else { // == -2
                int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
                camera.threadsCount = cores <= 2 ? 1 : cores;
            }
            return this;
        }
        public Builder  setDebugPrint(double interval) {
            camera.printInterval = interval;
            return this;
        }




        /**
         * Build the camera
         *
         * @return the camera
         */
        public Camera build() {

            final String description = "Missing rendering data";
            final String className = "Camera";
            if (camera.p0 == null) {
                throw new MissingResourceException(description, className, "p0");
            }
            if (camera.vTo == null) {
                throw new MissingResourceException(description, className, "vTo");
            }
            if (camera.vUp == null) {
                throw new MissingResourceException(description, className, "vUp");
            }
            if (isZero(camera.width)) {
                throw new MissingResourceException(description, className, "width");
            }
            if (isZero(camera.height)) {
                throw new MissingResourceException(description, className, "height");
            }
            if (isZero(camera.distance)) {
                throw new MissingResourceException(description, className, "distance");
            }
            if(camera.imageWriter == null) {
                throw new MissingResourceException(description, className, "imageWriter");
            }
            if(camera.rayTracer == null) {
                throw new MissingResourceException(description, className, "rayTracer");
            }
            if (camera.blackboard == null) {
                camera.blackboard = new Blackboard(0);

            }

            if (camera.width < 0) {
                throw new IllegalArgumentException("width must be positive");
            }
            if (camera.height < 0) {
                throw new IllegalArgumentException("height must be positive");
            }
            if (camera.distance < 0) {
                throw new IllegalArgumentException("distance must be positive");
            }
            camera.vRight = (camera.vTo.crossProduct(camera.vUp)).normalize();

            if (!isZero(camera.vTo.dotProduct(camera.vRight)) || !isZero(camera.vTo.dotProduct(camera.vUp)) || !isZero(camera.vUp.dotProduct(camera.vRight))) {
                throw new IllegalArgumentException("The 3 vectors must be orthogonal");
            }
            if (alignZero(camera.vTo.length() )!= 1 || camera.vRight.length() != 1 || camera.vUp.length() != 1) {
                throw new IllegalArgumentException("The 3 vectors must be normalized");
            }

            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException exception) {
                throw new RuntimeException(exception);
            }

        }


    }

    /**
     * Construct a ray through a pixel in the view plane
     *
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j  the x index of the pixel
     * @param i  the y index of the pixel
     * @return the ray through the pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        double Ry = height / nY;
        double Rx = width / nX;
        Point pIJ = p0;
        double Yi = -(i - (nY - 1) / 2d) * Ry;
        double Xj = (j - (nX - 1) / 2d) * Rx;
        if (!isZero(Xj)) pIJ = pIJ.add(vRight.scale(Xj));
        if (!isZero(Yi)) pIJ = pIJ.add(vUp.scale(Yi));

        pIJ = pIJ.add(vTo.scale(distance)); //pIJ is the center of the pixel in the view plane
        return new Ray(p0, pIJ.subtract(p0).normalize()); //return the ray from the camera to the center of the pixel

    }

    /**
     * Construct rays through a pixel in the view plane
     *
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j  the x index of the pixel
     * @param i  the y index of the pixel
     * @return the list of rays through the pixel
     */
    public List<Ray> constructRays(int nX, int nY, int j, int i) {
        List<Ray> rays = new ArrayList<>();
        Random random = new Random();
        int gridSize = blackboard.getGridSize();
        double Ry = height / nY;
        double Rx = width / nX;
        double stepY = Ry / gridSize;
        double stepX = Rx / gridSize;

        for (int subI = 0; subI < gridSize; subI++) {
            for (int subJ = 0; subJ < gridSize; subJ++) {
                double jitterY = random.nextDouble()%gridSize; // Random offset in Y direction
                double jitterX = random.nextDouble()%gridSize; // Random offset in X direction

                double offsetI = (subI + jitterY) * stepY;
                double offsetJ = (subJ + jitterX) * stepX;
                double Yi = -(i - (nY - 1) / 2d) * Ry + offsetI;
                double Xj = (j - (nX - 1) / 2d) * Rx + offsetJ;
                Point pIJ = p0;
                if (!isZero(Xj)) pIJ = pIJ.add(vRight.scale(Xj));
                if (!isZero(Yi)) pIJ = pIJ.add(vUp.scale(Yi));
                pIJ = pIJ.add(vTo.scale(distance)); // pIJ is the center of the pixel in the view plane

                Ray ray = new Ray(p0, pIJ.subtract(p0).normalize());
                rays.add(ray);
            }
        }
        return rays;
    }



    /**
     * Render the image
     * @return the camera
     */
    public Camera renderImage() {

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        ///
        Pixel.initialize(nY, nX, printInterval);
        if (threadsCount == 0)
            for (int i = 0; i < nY; i++)
                for (int j = 0; j < nX; j++)
                    castRay(nX, nY, j, i);
        else if (threadsCount == -1) {
            IntStream.range(0, nY).parallel() //
                    .forEach(i -> IntStream.range(0, nX).parallel() //
                            .forEach(j -> castRay(nX, nY, j, i)));
        }

        return this;
    }

    /**
     * Cast a ray through a pixel in the view plane
     *
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j  the x index of the pixel
     * @param i  the y index of the pixel
     */
    private void castRay(int nX, int nY, int j, int i) {
        if(blackboard.isAntiAliasingEnabled()) {
            List<Ray> rays = constructRays(nX, nY, j, i);
            Color color = Color.BLACK;
            for (Ray ray : rays) {
                color = color.add(rayTracer.traceRay(ray));
            }
            color = color.scale(1d/rays.size());
            imageWriter.writePixel(j, i, color);
        } else {
            Ray ray = constructRay(nX, nY, j, i);
            Color color= rayTracer.traceRay(ray);
            imageWriter.writePixel(j, i, color);
        }
        ///
        Pixel.pixelDone();

    }


    /**
     * Print a grid on the view plane
     * @param interval
     * @param color
     * @return the camera
     */
        public Camera printGrid(int interval, Color color) {
            //=== running on the view plane===//
            for (int i = 0; i < imageWriter.getNx(); i++) {
                for (int j = 0; j < imageWriter.getNy(); j++) {
                    //=== create the net ===//
                    if (i % interval == 0 || j % interval == 0) {
                        imageWriter.writePixel(i, j, color);
                    }
                }
            }
            return this;
        }

        /**
         * Write the image to the file
         */
        public void writeToImage() {
            this.imageWriter.writeToImage();
        }



    }








