package Game;

import Utility.BoardType;
import Utility.Point2;
import Utility.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GameBoard {

    /**
     * The current position of the board.
     */
    private Point2 position;

    /**
     * The current color of the board.
     */
    private Color BoardColor;

    /**
     * The type of the board.
     * Types are specified in BoardType.
     */
    private BoardType boardType;


    /**
     * A list of the boards.
     */
    private List<GameBoard> boards;


    /**
     * Initializes this board to have zero board in the <List>boards</list>.
     */
    public GameBoard() {
        boards = new ArrayList<GameBoard>();
    }

    /**
     * Initialize the board so that its position, velocity
     * and type are equal to the given position, velocity
     * and type.
     *
     * @param position the position of the board
     * @param BoardColor the color of the ball
     * @param Type     the type of the board
     */
    public GameBoard(Point2 position, Color BoardColor, BoardType Type) {
        this.position = position;
        this.BoardColor = BoardColor;
        this.boardType = Type;
    }


    /**
     * Return the position of the board.
     *
     * @return the position of the board
     */
    public Point2 getPosition() {
        return this.position;
    }

    /**
     * Set the position of the board to the given position.
     *
     * @param position the new position of the board
     */
    public void setPosition(Point2 position) {
        this.position = position;
    }

    /**
     * Return the color of the board.
     *
     * @return the color of the board
     */
    public Color getColor() {
        return this.BoardColor;
    }

    /**
     * Set the color of the board to the given color.
     *
     * @param boardColor the new color of the board
     */
    public void setColor(Color boardColor) {
        this.BoardColor = boardColor;
    }

    /**
     * Return the type of the board.
     *
     * @return the type of the board
     */
    public BoardType getBoardType() {
        return this.boardType;
    }

    /**
     * Set the type of the board to the given type.
     *
     * @param Type the new type of the board
     */
    public void setBoardType(BoardType Type) {
        this.boardType = Type;
    }

    /**
     * Get a shallow copy of the list of boards.
     *
     * @return a shallow copy of the list of boards
     */
    public List<GameBoard> getBoardList() {
        return new ArrayList<GameBoard>(this.boards);
    }

    /**
     * Walks the turtle forward by a given distance in the direction the turtle is
     * currently facing. A line is drawn as the turtle moves to the new position
     * using the current pen color.
     *
     * @param distance the distance to move
     * @param angle    the angle in degrees that one should face
     * @throws IllegalArgumentException if distance is less than zero
     * @throws IllegalArgumentException if angle is not in the appropriate range
     */
    public Point2 move(double distance, double angle) {
        if (distance < 0) {
            throw new IllegalArgumentException("distance is less than zero");
        }
        if (angle < 0 || angle > 360) {
            throw new IllegalArgumentException("angle should be in range 0 to 360");
        } else {
            Vector2 dirVectorUp = Vector2.dirVector(angle);
            dirVectorUp.multiply(distance);
            position.moveX(dirVectorUp.getX());
            position.moveY(dirVectorUp.getY());
            return position;
        }
    }


    /**
     * Add different type of board to the <List>boards</list> with appropriate
     * initial position.
     */
    public void Makeboard() {

        Normalboard(new Point2(20, 300));
        Normalboard(new Point2(224, 391));
        Stingboard(new Point2(156, 209));
        Leftlboard(new Point2(88, 482));
        Rightboard(new Point2(292, 573));
        Regenerateboard(new Point2(360, 118));

    }


    /**
     * Create a new <BoardType>Normal</BoardType> board and add it to the <list>boards</list> list.
     *
     * @param position the position of the board
     */
    private void Normalboard(Point2 position) {
        GameBoard Normalboard = new GameBoard(position, BoardColor, BoardType.Normal);
        boards.add(Normalboard);
    }


    /**
     * Create a new <BoardType>Regenerate</BoardType> board and add it to the <list>boards</list> list.
     *
     * @param position the position of the board
     */
    private void Regenerateboard(Point2 position) {
        GameBoard Regenerateboard = new GameBoard(position, BoardColor, BoardType.Regenerate);
        boards.add(Regenerateboard);
    }


    /**
     * Create a new <BoardType>Left</BoardType> board and add it to the <list>boards</list> list.
     *
     * @param position the position of the board
     */
    private void Leftlboard(Point2 position) {
        GameBoard Leftlboard = new GameBoard(position, BoardColor, BoardType.Left);
        boards.add(Leftlboard);
    }


    /**
     * Create a new <BoardType>Right</BoardType> board and add it to the <list>boards</list> list.
     *
     * @param position the position of the board
     */
    private void Rightboard(Point2 position) {
        GameBoard Rightboard = new GameBoard(position, BoardColor, BoardType.Right);
        boards.add(Rightboard);
    }


    /**
     * Create a new <BoardType>Sting</BoardType> board and add it to the <list>boards</list> list.
     *
     * @param position the position of the board
     */
    private void Stingboard(Point2 position) {
        GameBoard Stingboard = new GameBoard(position, BoardColor, BoardType.Sting);
        boards.add(Stingboard);
    }


}