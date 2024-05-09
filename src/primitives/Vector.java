package primitives;

public class Vector extends Point {
    //parameter constructor
    public Vector(double d1, double d2, double d3) {
        super(d1,d2,d3);
        if (d1 == 0 && d2 == 0 && d3 == 0) {
            throw new IllegalArgumentException("Zero vector is not allowed.");
        }
    }
    // parameter constructor
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Cannot create zero vector");
        }
    }
    @Override
    public boolean equals(Object obj){
       return super.equals(obj);
    }

    @Override
    public String toString(){return super.toString();}

    public Vector add(Vector vec){
        return new Vector(this.xyz.add(vec.xyz));
    }

    public Vector scale(double rsh){
        return new Vector(this.xyz.scale(rsh));
    }

    public double dotProduct(Vector vec){
        return ((this.xyz.d1 * vec.xyz.d1) + (this.xyz.d2 * vec.xyz.d2) + (this.xyz.d3 * vec.xyz.d3));
    }

    //לפי נוסחה ממבואנ להנדסת תוכנה
    public Vector crossProduct(Vector vec){
        return new Vector(((this.xyz.d2 * vec.xyz.d3)-(this.xyz.d3 * vec.xyz.d2)), ((this.xyz.d3 * vec.xyz.d1)-(this.xyz.d1 * vec.xyz.d3)) , ((this.xyz.d1 * vec.xyz.d2)-(this.xyz.d2 * vec.xyz.d1)));
    }

    public double lengthSquared(){
        return this.dotProduct(this);
    }

    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    public Vector normalize(){
        return new Vector(((this.xyz.d1)/(this.length())),((this.xyz.d2)/(this.length())), ((this.xyz.d3)/(this.length())));
    }

}

