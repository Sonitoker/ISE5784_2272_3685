package primitives;

public class Point {
    final protected  Double3 xyz;
    public static final Point ZERO=new Point(0,0,0);

    //constructor with three double numbers
    public Point(double d1, double d2, double d3) {
        this.xyz =new Double3(d1,d2,d3);
    }
    // parameter constructor
    public Point(Double3 xyz) {
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Cannot create zero vector");
        }
        this.xyz = xyz;
    }


    @Override
    public boolean equals(Object obj){
        if(this==obj) return true;
        return obj instanceof Point other && xyz.equals(other.xyz);
    }

    @Override
    public String toString(){return ""+xyz;}



public Vector subtract(Point p){
    return new Vector(this.xyz.subtract(p.xyz));
}

public Point add(Vector vec){
    return new Point(this.xyz.add(vec.xyz));
}

public double distanceSquared(Point p){
    Vector vec= subtract(this);
    return vec.xyz.d1*vec.xyz.d1 + vec.xyz.d2*vec.xyz.d2 + vec.xyz.d3*vec.xyz.d3;
}

public double distance(Point p) {
    return Math.sqrt(this.distanceSquared(p));
}

}