package Game;

import Utility.BoardType;
import Utility.Point2;
import Utility.Vector2;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;


public class GamePanel extends JPanel {

    private static final double GameVersion = 1.11;
    private static final String Update_date = "2018/06/20";
    private static final int gameWidth = 480;
    private static final int gameHeight = 640;
    private static final int TIMETICK_MS = 25;
    private static final double BALL_SIZE = 50;
    private static final Random RANDOMS = new Random();
    public static boolean isOver;
    public static boolean isReStart;
    public static boolean isPause;
    public static boolean isInstructing;
    public static boolean isAbouting;
    private static boolean isDead;
    private static boolean isFalling;
    private static boolean isSting;
    private static boolean isWin;
    private static boolean isRegenerate;
    private static int health;
    private static int feet;
    private static double timetickCount;
    private static int STING_BOARD_LENGTH;
    private static int NORMAL_BOARD_LENGTH;
    private static int REGENERATE_BOARD_LENGTH;
    private static int LEFT_BOARD_LENGTH;
    private static int RIGHT_BOARD_LENGTH;
    private static int HighestScore = 0;
    private static int DeathCount = -1;
    private static int BOARD_LENGTH = 140;
    private static double FallingSpeed = 0.25;
    private static String Planet = "Earth";
    private final String BACKGROUND_STING = "####################################################################";
    private final String STING_BOARD = "#################";
    private final String NORMAL_BOARD = "=================";
    private final String REGENERATE_BOARD = "OOOOOOOOOOOOO";
    private final String LEFT_BOARD = "<<<<<<<<<<<<<<<<<<";
    private final String RIGHT_BOARD = ">>>>>>>>>>>>>>>>>>";
    private GameBall Ball;
    private GameBoard board;
    private List<GameBoard> boards;


    // ================================================================================

    /**
     * Initializes the panel and all the necessary value by calling the method Reset()
     */
    public GamePanel() {
        Reset();
        setLayout(null);
    }


    /**
     * A method to reset all the necessary value to its initial state
     */
    public void Reset() {

        this.Ball = new GameBall(new Point2(15.0, 10.0), new Vector2(0, 0), BALL_SIZE, MainFrame.BallColor);

        isPause = true;

        isDead = false;
        isFalling = false;
        isSting = false;
        isReStart = false;
        isOver = false;
        isInstructing = false;
        isAbouting = false;
        isRegenerate = false;

        board = new GameBoard();
        board.Makeboard();
        boards = board.getBoardList();

        health = 5;
        feet = 0;

        if (isWin == false) {
            DeathCount++;
        }

        isWin = false;
    }

    // ================================================================================

    /**
     * A method which include two while loop to update the GamePanel.
     * <p>
     * First Loop:
     * when the game is running, a <code>Thread.sleep()</code> method
     * to stall the program so it won't run to fast. if methods to check
     * the wining condition, restart command, highest score etc, then it
     * will call the manageSkin() method to check the gameball's state.
     * Second Loop:
     * when the game is paused, the program will run 20 times slower and
     * check if the game will restart. Making a recursive call to keep
     * the program running and checking itself.
     * <p>
     * Known bug:  If the program keep pausing for for than 1 hour, a StackOverflowError
     * will occur.
     */
    public void update() {

        while (isPause == false) {
            try {
                Thread.sleep(TIMETICK_MS);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isDead == true || isReStart == true) {
                Reset();
            }

            if (isOver == false) {
                if (health == 0) {
                    isDead = true;
                }
            }

            if (feet >= 30000) {
                isWin = true;
                isOver = true;
                isPause = true;
            }

            CheckHighestScore(feet);
            manageState(Ball);
            ManageSkin();

            repaint();

        }

        // ================================================================================

        while (isPause == true || isOver == true) {

            try {
                Thread.sleep(TIMETICK_MS * 20);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isReStart == true) {
                Reset();
            }

            repaint();

            update();
        }


    }


    // ================================================================================

    /**
     * A main method to manage and check the position of the game ball.
     * <p>
     * The ball will stay inside the game area.
     * <p>
     * If the ball hit the roof it will lose health then be pushed down one ball size.
     * <p>
     * If the ball not falling, the ball should be on the board and should
     * be moving up as the same speed as the board.
     * <p>
     * The ball's traveled distance is calculated as one tenth its total Y coordinates.
     * <p>
     * If the ball gets sting it will lose health every 30 time tick.
     * <p>
     * If the ball is regenerate it will restore health every 30 time tick.
     * <p>
     * isFalling will determined by the CheckBallState method.
     *
     * @param ball the game ball to check.
     */
    public void manageState(GameBall ball) {

        double X = ball.getPosition().getX();
        double Y = ball.getPosition().getY();

        isFalling = true;

        isFalling = CheckBoards(Ball, X, Y, isFalling);

        if (X < MainFrame.APP_WIDTH - gameWidth) {
            ball.getPosition().setX(MainFrame.APP_WIDTH - gameWidth);
        }

        if (X > gameWidth - BALL_SIZE - 7) {
            ball.getPosition().setX(gameWidth - BALL_SIZE - 7);
        }

        if (Y > gameHeight - 2 * BALL_SIZE) {
            health = 0;
        }

        if (isFalling == true) {
            Ball.move(FallingSpeed);
            feet += Ball.getPosition().getY() / 10;
        }

        if (isFalling == false) {
            ball.setVelocity(new Vector2(0, 0));
            ball.setPosition(new Point2(ball.getPosition().getX(), ball.getPosition().getY() - FallingSpeed * 10));
        }

        if (Y < 0) {
            isSting = true;
            isFalling = true;
            ball.setPosition(new Point2(X, Y + BALL_SIZE));
        }

        if (isSting == true) {

            if (timetickCount == 0) {
                health -= 1;
            }

            timetickCount++;

            isFalling = true;

            if (timetickCount > 30) {
                isSting = false;
                timetickCount = 0;
            }
        }

        if (isRegenerate == true) {

            if (timetickCount == 0) {
                health++;
            }

            timetickCount++;

            if (timetickCount > 30) {
                isRegenerate = false;
                timetickCount = 0;
            }
        }

    }

    // ================================================================================

    /**
     * A method to manage the behaver of boards and call the CheckOnBoard to check which board is on.
     * <p>
     * All the boards should move upwards.
     * <p>
     * If the board in the <list>boards</list> list moved out of the game area,
     * it will set the board to the bottom of the program then call the getNewBoard
     * method to get a new board to replace the old board in the <list>boards</list>
     * list to keep the number of boards in play constant.
     * <p>
     * Left board will drag the game ball to left, vice versa.
     * <p>
     * Regenerate board will set isRegenerate to true.
     * <p>
     * Sting board will set isSting to true.
     *
     * @param Ball      the game ball to check
     * @param X         the game ball's X position
     * @param Y         the game ball's Y position
     * @param isfalling whether the game ball is falling
     * @return the verdict to if the ball is falling or not
     */
    private boolean CheckBoards(GameBall Ball, double X, double Y, boolean isfalling) {

        for (int i = 0; i < boards.size(); i++) {

            GameBoard board = boards.get(i);

            board.move(FallingSpeed * 10, 270);

            if (board.getPosition().getY() < 0) {
                board.getPosition().setY(gameHeight);
                board = this.getNewBoard(board);
                boards.add(board);
                boards.remove(i);
            }

            if (CheckOnBoard(board, Ball) == true) {

                isfalling = false;

                if (board.getBoardType() == BoardType.Normal) {
                    BOARD_LENGTH = NORMAL_BOARD_LENGTH;
                }

                if (board.getBoardType() == BoardType.Left) {
                    BOARD_LENGTH = LEFT_BOARD_LENGTH;
                    Ball.getPosition().setX(X - FallingSpeed * 10);
                }

                if (board.getBoardType() == BoardType.Right) {
                    BOARD_LENGTH = RIGHT_BOARD_LENGTH;
                    Ball.getPosition().setX(X + FallingSpeed * 10);
                }

                if (board.getBoardType() == BoardType.Regenerate) {
                    BOARD_LENGTH = REGENERATE_BOARD_LENGTH;
                    if (health < 5) {
                        isRegenerate = true;
                    }
                }

                if (board.getBoardType() == BoardType.Sting) {
                    BOARD_LENGTH = STING_BOARD_LENGTH;
                    isSting = true;
                }
            }
        }

        return isfalling;
    }

// ================================================================================

    /**
     * A method to check if the ball is on board.
     * <p>
     * By comparing the board's coordinate with the game ball's.
     *
     * @param board the board to check
     * @param ball  the game ball to check
     * @return if the game ball is on board
     */
    public boolean CheckOnBoard(GameBoard board, GameBall ball) {

        double boardX = board.getPosition().getX();
        double boardY = board.getPosition().getY();

        double ballX = ball.getPosition().getX();
        double ballY = ball.getPosition().getY();

        int xDiff = (int) (boardX - ballX);
        int yDiff = (int) (boardY - ballY);

        if (xDiff < BALL_SIZE && xDiff > -BOARD_LENGTH && yDiff < BALL_SIZE && yDiff > BALL_SIZE / 2) {
            return true;
        }

        return false;


    }

    // ================================================================================

    /**
     * A method to return a random new board with random X coordinates.
     *
     * @param b the old board to replace
     * @return a new board
     */
    public GameBoard getNewBoard(GameBoard b) {

        int num = RANDOMS.nextInt(100);

        int newX = RANDOMS.nextInt(gameWidth - BOARD_LENGTH);

        if (20 > num) {

            return new GameBoard(new Point2(newX, b.getPosition().getY()), b.getColor(), BoardType.Sting);

        } else if (40 > num) {

            return new GameBoard(new Point2(newX, b.getPosition().getY()), b.getColor(), BoardType.Regenerate);

        } else if (60 > num) {

            return new GameBoard(new Point2(newX, b.getPosition().getY()), b.getColor(), BoardType.Left);

        } else if (80 > num) {

            return new GameBoard(new Point2(newX, b.getPosition().getY()), b.getColor(), BoardType.Right);

        } else {

            return new GameBoard(new Point2(newX, b.getPosition().getY()), b.getColor(), BoardType.Normal);
        }

    }

    // ================================================================================

    /*
     * A method to paint required component.
     *
     */
    public void paintComponent(Graphics g) {

        // Paint back ground.

        g.setColor(MainFrame.BackGroundColor);
        g.fillRect(MainFrame.APP_WIDTH - gameWidth, MainFrame.APP_HEIGHT - gameHeight, gameWidth, gameHeight);

        g.setColor(Color.BLACK);
        g.drawString(BACKGROUND_STING, 0, 10);

        // ================================================================================

        // Paint boards with specific color.

        for (GameBoard board : boards) {

            //Different "Planet" have different board color

            if (Planet.equals("Sun")) {
                board.setColor(Color.WHITE);
            }
            if (Planet.equals("Jupiter")) {
                board.setColor(Color.MAGENTA);
            }
            if (Planet.equals("Earth")) {
                board.setColor(Color.BLACK);
            }


            g.setColor(board.getColor());
            BoardType type = board.getBoardType();


            if (type.equals(BoardType.Normal)) {
                g.drawString(NORMAL_BOARD, (int) board.getPosition().getX(), (int) board.getPosition().getY());
                NORMAL_BOARD_LENGTH = g.getFontMetrics().stringWidth(NORMAL_BOARD);
            }

            if (type.equals(BoardType.Left)) {
                g.drawString(LEFT_BOARD, (int) board.getPosition().getX(), (int) board.getPosition().getY());
                LEFT_BOARD_LENGTH = g.getFontMetrics().stringWidth(LEFT_BOARD);
            }

            if (type.equals(BoardType.Right)) {
                g.drawString(RIGHT_BOARD, (int) board.getPosition().getX(), (int) board.getPosition().getY());
                RIGHT_BOARD_LENGTH = g.getFontMetrics().stringWidth(RIGHT_BOARD);
            }

            if (type.equals(BoardType.Regenerate)) {
                g.drawString(REGENERATE_BOARD, (int) board.getPosition().getX(), (int) board.getPosition().getY());
                REGENERATE_BOARD_LENGTH = g.getFontMetrics().stringWidth(REGENERATE_BOARD);
            }

            if (type.equals(BoardType.Sting)) {
                g.drawString(STING_BOARD, (int) board.getPosition().getX(), (int) board.getPosition().getY());
                STING_BOARD_LENGTH = g.getFontMetrics().stringWidth(STING_BOARD);
            }

        }

        // ================================================================================

        // Paint the traveled distance text with effects.

        if (feet >= 0 && feet < 10000) {
            g.setColor(Color.BLUE);
        }
        if (feet >= 10000 && feet <= 20000) {
            g.setColor(Color.MAGENTA);
        }
        if (feet > 20000 && feet <= 30000) {
            g.setColor(Color.CYAN);
        }
        g.setFont(new Font("Feet", Font.CENTER_BASELINE, 13));
        g.drawString(feet + "  Feet Below the " + Planet, getWidth() - g.getFontMetrics().stringWidth(feet + "  Feet Below the " + Planet) - 20, getHeight() - 20);

        // ================================================================================

        // Paint the health text with effects.

        if (health == 5) {
            g.setColor(Color.GREEN);
        }
        if (health >= 3 && health <= 4) {
            g.setColor(Color.ORANGE);
        }
        if (health >= 1 && health <= 2) {
            g.setColor(Color.RED);
        }
        g.setFont(new Font("Health", Font.BOLD, 13));
        g.drawString("Health: " + health, g.getFontMetrics().stringWidth("Health: " + health) + 20, getHeight() - 20);

        // ================================================================================

        // Paint the SpaceBar instructions when paused.

        if (isPause == true) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Pause", Font.BOLD, 15));
            g.drawString(("Press SpaceBar to Toggle Start / Pause"), (getWidth() - g.getFontMetrics().stringWidth("Press SpaceBar to Toggle Start / Pause")) / 2, getHeight() / 2);
        }

        // Make the ball hollow when get sting and beating when regenerating.

        if (isSting == true) {
            g.setColor(MainFrame.BallColor);
            g.drawOval((int) Ball.getPosition().getX(), (int) Ball.getPosition().getY(), (int) BALL_SIZE, (int) BALL_SIZE);
        } else if (isRegenerate == true) {
            g.setColor(MainFrame.BallColor);
            g.fillOval((int) (Ball.getPosition().getX() - BALL_SIZE / 10), (int) (Ball.getPosition().getY() - BALL_SIZE / 10), (int) (BALL_SIZE + BALL_SIZE / 5), (int) (BALL_SIZE + BALL_SIZE / 5));
        } else {
            g.setColor(MainFrame.BallColor);
            g.fillOval((int) Ball.getPosition().getX(), (int) Ball.getPosition().getY(), (int) BALL_SIZE, (int) BALL_SIZE);
        }

        // Paint end game text.

        if (isWin == true) {
            g.setColor(Color.BLACK);
            g.fillRect(MainFrame.APP_WIDTH - gameWidth, MainFrame.APP_HEIGHT - gameHeight, gameWidth, gameHeight);

            g.setFont(new Font("Win1", Font.BOLD, 20));
            g.setColor(Color.RED);
            g.drawString("CONGRATULATIONS", (getWidth() - g.getFontMetrics().stringWidth("CONGRATULATIONS")) / 2, getHeight() / 2);

            g.setFont(new Font("Win2", Font.ITALIC, 18));
            g.drawString("You Have Reached the Hadal Zone", (getWidth() - g.getFontMetrics().stringWidth("You Have Reached the Hadal Zone")) / 2, (getHeight() / 2) + 50);
        }

        // Repaint the background when clicked the instruction or about button.

        if (isInstructing == true || isAbouting == true) {

            g.setColor(MainFrame.BackGroundColor);
            g.fillRect(MainFrame.APP_WIDTH - gameWidth, MainFrame.APP_HEIGHT - gameHeight, gameWidth, gameHeight);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Pause", Font.BOLD, 15));
            g.drawString(("Press SpaceBar to Resume"), (getWidth() - g.getFontMetrics().stringWidth("Press SpaceBar to Resume")) / 2, getHeight() - 30);

            // Paint the instructions.

            if (isInstructing == true) {

                g.setColor(Color.BLACK);
                g.setFont(new Font("Instrucions", Font.PLAIN, 13));

                g.drawString(NORMAL_BOARD, (getWidth() - g.getFontMetrics().stringWidth(NORMAL_BOARD)) / 2, g.getFontMetrics().getHeight() + 20);
                g.drawString("This is a borad just like other boards", (getWidth() - g.getFontMetrics().stringWidth("This is a borad just like other boards")) / 2, g.getFontMetrics().getHeight() + 40);
                g.drawString("Not a thing will happen if you accidentally step on it", (getWidth() - g.getFontMetrics().stringWidth("Not a thing will happen if you accidentally step on it")) / 2, g.getFontMetrics().getHeight() + 55);

                g.drawString(LEFT_BOARD, (getWidth() - g.getFontMetrics().stringWidth(LEFT_BOARD)) / 2, g.getFontMetrics().getHeight() + 120);
                g.drawString("Here comes a borad just like other boards", (getWidth() - g.getFontMetrics().stringWidth("Here comes a borad just like other boards")) / 2, g.getFontMetrics().getHeight() + 140);
                g.drawString("Besides an irresistible force dragging you to the direction of its pattern", (getWidth() - g.getFontMetrics().stringWidth("Besides an irresistible force dragging you to the direction of its pattern")) / 2, g.getFontMetrics().getHeight() + 155);

                g.drawString(RIGHT_BOARD, (getWidth() - g.getFontMetrics().stringWidth(RIGHT_BOARD)) / 2, g.getFontMetrics().getHeight() + 220);
                g.drawString("And this borad is just like the previous board", (getWidth() - g.getFontMetrics().stringWidth("And this borad is just like the previous board")) / 2, g.getFontMetrics().getHeight() + 240);
                g.drawString("Apart from its distinctive '>' s", (getWidth() - g.getFontMetrics().stringWidth("Apart from its distinctive '>' s")) / 2, g.getFontMetrics().getHeight() + 255);

                g.drawString(STING_BOARD, (getWidth() - g.getFontMetrics().stringWidth(STING_BOARD)) / 2, g.getFontMetrics().getHeight() + 320);
                g.drawString("However the borad right here just like no other boards", (getWidth() - g.getFontMetrics().stringWidth("However the borad right here just like no other boards")) / 2, g.getFontMetrics().getHeight() + 340);
                g.drawString("Nobody have the faintest idea why it grows thorns", (getWidth() - g.getFontMetrics().stringWidth("Nobody have the faintest idea why it grows thorns")) / 2, g.getFontMetrics().getHeight() + 355);

                g.drawString(REGENERATE_BOARD, (getWidth() - g.getFontMetrics().stringWidth(REGENERATE_BOARD)) / 2, g.getFontMetrics().getHeight() + 420);
                g.drawString("Finally 'tis the board just like other boards should be", (getWidth() - g.getFontMetrics().stringWidth("Finally 'tis the board just like other boards should be")) / 2, g.getFontMetrics().getHeight() + 440);
                g.drawString("The Old Lady said it will make thee feel 'energetic' ", (getWidth() - g.getFontMetrics().stringWidth("The Old Lady said it will make thee feel 'energetic' ")) / 2, g.getFontMetrics().getHeight() + 455);

                g.setFont(new Font("Instrucion", Font.CENTER_BASELINE, 15));
                g.drawString("The Objective is to reach 30000 feet", (getWidth() - g.getFontMetrics().stringWidth("The Objective is to reach 30000 feet")) / 2, g.getFontMetrics().getHeight() + 500);
                ;
            }

            // Paint the About.

            if (isAbouting == true) {

                g.setColor(Color.BLACK);
                g.setFont(new Font("About", Font.CENTER_BASELINE, 17));
                g.drawString(MainFrame.APP_TITLE, (getWidth() - g.getFontMetrics().stringWidth(MainFrame.APP_TITLE)) / 3, getHeight() / 5);

                g.setColor(Color.BLACK);
                g.setFont(new Font("About", Font.ITALIC, 15));
                g.drawString("A Game by Shangru Li", (getWidth() - g.getFontMetrics().stringWidth("A Game by Shangru Li")) / 3 + 150, getHeight() / 5 + 20);

                g.setColor(Color.BLACK);
                g.setFont(new Font("About", Font.BOLD, 16));

                if (HighestScore == 30000) {
                    g.setColor(Color.RED);
                    g.drawString("YOU HAVE BEAT THE GAME", (getWidth() - g.getFontMetrics().stringWidth("YOU HAVE BEAT THE GAME")) / 2, getHeight() / 2);
                } else {
                    g.drawString("Deepest Distance Reached: " + HighestScore, (getWidth() - g.getFontMetrics().stringWidth("Deepest Distance Reached " + HighestScore)) / 2, getHeight() / 2);
                }

                g.drawString("Number of Deaths: " + DeathCount, (getWidth() - g.getFontMetrics().stringWidth("Number of Deaths: " + DeathCount)) / 2, getHeight() / 2 + 50);


                g.setFont(new Font("About", Font.ITALIC, 10));
                g.drawString("GameVerison: " + GameVersion, (getWidth() - g.getFontMetrics().stringWidth("GameVerison " + GameVersion)) - 5, getHeight() - 12);

                g.drawString("Updated: " + Update_date, (getWidth() - g.getFontMetrics().stringWidth("Updated: " + Update_date)) - 5, getHeight() - 2);
            }
        }
    }

    // ================================================================================

    /**
     * Two methods to make the ball move left/right with 40 times falling speed.
     */
    public void MoveBallLeft() {
        if (isPause == false) {
            Ball.getPosition().setX(Ball.getPosition().getX() - FallingSpeed * 40);
        }
    }

    public void MoveBallRight() {
        if (isPause == false) {
            Ball.getPosition().setX(Ball.getPosition().getX() + FallingSpeed * 40);
        }
    }

    // ================================================================================

    /**
     * Method to update the high score
     *
     * @param score the current score to check
     */
    public void CheckHighestScore(int score) {
        if (score > HighestScore) {
            if (score >= 30000) {
                score = 30000;
            }
            HighestScore = score;
        }
    }

    // ================================================================================

    /**
     * Method to change falling speed and background color.
     *
     * @param planet to determine what should change
     */
    public void ChangeDiffculy(String planet) {

        if (planet.equals("Earth")) {
            FallingSpeed = 0.25;
            Planet = planet;
            MainFrame.BackGroundColor = Color.WHITE;
        }
        if (planet.equals("Jupiter")) {
            FallingSpeed = 0.35;
            Planet = planet;
            MainFrame.BackGroundColor = Color.YELLOW;
        }

        if (planet.equals("Sun")) {
            FallingSpeed = 0.4;
            Planet = planet;
            MainFrame.BackGroundColor = Color.RED;
        }
    }

    // ================================================================================

    /**
     * Method to check highest score to unlock different game ball color
     */
    public void ManageSkin() {
        if (HighestScore >= 10000) {
            MainFrame.Green.setEnabled(true);
        }
        if (HighestScore >= 20000) {
            MainFrame.Blue.setEnabled(true);
        }
        if (HighestScore == 30000) {
            MainFrame.Pink.setEnabled(true);
        }
    }
}