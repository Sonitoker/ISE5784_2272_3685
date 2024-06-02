package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Camera implements Cloneable {

    private Point p0;
    private Vector vTo, vUp, vRight;
    private double width = 0d, height = 0d, distance = 0d;

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }

    private Camera() {
    }

    static public Builder getBuilder(){
        return new Builder();
    }

    public Ray constructRay(int nX, int nY, int j, int i){
        return null;
    }

    public static class Builder{
        private final Camera camera= new Camera();
        public Builder setP0(Point p0) {
            camera.p0 = p0;
            return this;
        }

        public Builder setDirection(Vector vTo, Vector vUp){

        }






    }










}
