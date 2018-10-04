package Utility;

import java.util.Objects;

public class Point2 {
    private double x;
    private double y;

    public Point2() {
        set(0.0D, 0.0D);
    }

    public Point2(double x, double y) {
        set(x, y);
    }

    public Point2(Point2 other) {
        this(other.x, other.y);
    }

    public double getX() {
        return this.x;
    }

    public void setX(double newX) {
        this.x = newX;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double newY) {
        this.y = newY;
    }

    public void set(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }

    public void moveX(double dx) {
        this.x += dx;
    }

    public void moveY(double dy) {
        this.y += dy;
    }

    public double distanceTo(Point2 other) {
        return Math.hypot(getX() - other.getX(), getY() - other.getY());
    }

    public boolean similarTo(Point2 other, double tol) {
        return distanceTo(other) < tol;
    }

    public String toString() {
        String s = String.format("(%s, %s)", new Object[]{Double.valueOf(getX()), Double.valueOf(getY())});
        return s;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Double.valueOf(this.x), Double.valueOf(this.y)});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Point2 other = (Point2) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        return true;
    }
}