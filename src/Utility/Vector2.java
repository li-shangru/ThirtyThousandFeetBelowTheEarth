package Utility;

public final class Vector2 {
    private double x;
    private double y;

    public Vector2() {
        this(0.0D, 0.0D);
    }

    public Vector2(double x, double y) {
        set(x, y);
    }

    public Vector2(Vector2 other) {
        this(other.getX(), other.getY());
    }

    public static Vector2 dirVector(double theta) {
        double radians = Math.toRadians(theta);
        return new Vector2(Math.cos(radians), Math.sin(radians));
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 other) {
        setX(getX() + other.getX());
        setY(getY() + other.getY());
        return this;
    }

    public Vector2 subtract(Vector2 other) {
        setX(getX() - other.getX());
        setY(getY() - other.getY());
        return this;
    }

    public Vector2 multiply(double s) {
        setX(getX() * s);
        setY(getY() * s);
        return this;
    }

    public double mag() {
        return Math.hypot(getX(), getY());
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;

        long temp = Double.doubleToLongBits(this.x);
        result = 31 * result + (int) (temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.y);
        result = 31 * result + (int) (temp ^ temp >>> 32);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector2 other = (Vector2) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y))
            return false;
        return true;
    }

    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }

    public boolean similarTo(Vector2 other, double tol) {
        Vector2 delta = new Vector2(this).subtract(other);
        return delta.mag() < Math.abs(tol);
    }
}