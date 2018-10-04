package Game;

import Utility.Lab3Util;
import Utility.Point2;
import Utility.Vector2;

import java.awt.*;

public class GameBall {

    /**
     * Gravitational acceleration vector.
     */
    private static final Vector2 G = new Vector2(0.0, 9.81);
    /**
     * The current position of the ball.
     */
    private Point2 position;
    /**
     * The current velocity of the ball.
     */
    private Vector2 velocity;
    /**
     * The current diameter of the ball.
     */
    private double diameter;
    /**
     * The current color of the ball.
     */
    private Color BallColor;


    /**
     * Initialize the ball so that its position and velocity are
     * equal to the given position and velocity.
     *
     * @param position  the position of the ball
     * @param velocity  the velocity of the ball
     * @param diameter  the diameter of the ball
     * @param ballcolor the color of the ball
     */
    public GameBall(Point2 position, Vector2 velocity, Double diameter, Color ballcolor) {
        this.position = position;
        this.velocity = velocity;
        this.diameter = diameter;
        this.BallColor = ballcolor;
    }


    /**
     * Return the position of the ball.
     *
     * @return the position of the ball
     */
    public Point2 getPosition() {
        return this.position;
    }

    /**
     * Set the position of the ball to the given position.
     *
     * @param position the new position of the ball
     */
    public void setPosition(Point2 position) {
        this.position = position;
    }

    /**
     * Return the velocity of the ball.
     *
     * @return the velocity of the ball
     */
    public Vector2 getVelocity() {
        return this.velocity;
    }

    /**
     * Set the velocity of the ball to the given velocity.
     *
     * @param velocity the new velocity of the ball
     */
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    /**
     * Return the diameter of the ball.
     *
     * @return the diameter of the ball
     */
    public Double getDiameter() {
        return this.diameter;
    }

    /**
     * Set the diameter of the ball to the given diameter.
     *
     * @param diameter the new diameter of the ball
     */
    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }

    /**
     * Return the color of the ball.
     *
     * @return the color of the ball
     */
    public Color getColor() {
        return this.BallColor;
    }

    /**
     * Set the color of the ball to the given color.
     *
     * @param ballcolor the new color of the ball
     */
    public void setColor(Color ballcolor) {
        this.BallColor = ballcolor;
    }


    /**
     * Moves the ball from its current position using its current
     * velocity accounting for the force of gravity.
     *
     * @param dt the time period over which the ball has moved
     * @return the new position of the ball
     */
    public Point2 move(double dt) {
        if (dt < 0) {
            throw new IllegalArgumentException("time period cannot less than zero");
        } else {
            Vector2 dp1 = Lab3Util.multiply(dt, this.velocity);
            Vector2 dp2 = Lab3Util.multiply(0.5 * dt * dt, GameBall.G);
            Vector2 dp = Lab3Util.add(dp1, dp2);
            this.position = Lab3Util.add(this.position, dp);
            Vector2 dv = Lab3Util.multiply(dt, GameBall.G);
            this.velocity.add(dv);
            return this.position;
        }
    }

}