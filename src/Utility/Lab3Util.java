package Utility;

public class Lab3Util

{

    public static Vector2 add(Vector2 a, Vector2 b) {
        Vector2 result = new Vector2(a);
        result.add(b);
        return result;
    }

    public static Vector2 subtract(Vector2 a, Vector2 b) {
        Vector2 result = new Vector2(a);
        result.subtract(b);
        return result;
    }

    public static Vector2 multiply(double s, Vector2 a) {
        Vector2 result = new Vector2(a);
        result.multiply(s);
        return result;
    }

    public static Point2 add(Point2 p, Vector2 v) {
        return new Point2(p.getX() + v.getX(), p.getY() + v.getY());
    }

}