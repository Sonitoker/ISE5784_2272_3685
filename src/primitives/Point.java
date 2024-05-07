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
        this.xyz = xyz;
    }


    @Override
    public boolean equals(Object obj){
        if(this==obj) return true;
        return obj instanceof Point other && xyz.equals(other.xyz);
    }

    @Override
    public String toString(){return ""+xyz;}

}
