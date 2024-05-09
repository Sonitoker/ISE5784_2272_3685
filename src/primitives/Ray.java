package primitives;

public class Ray {
    final private  Point head;
    final private  Vector direction;

    // parameter constructor
    public Ray(Point p, Vector vec) {
        this.head = p;
        this.direction=vec.normalize();
    }

    @Override
    public boolean equals(Object obj){
        return (obj instanceof Ray other) && this.head.equals(other.head) && this.direction.equals(other.direction);
    }

    @Override
    public String toString(){return super.toString();}

}

