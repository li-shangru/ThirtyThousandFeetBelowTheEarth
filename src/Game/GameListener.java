package Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GameListener implements KeyListener, ActionListener {

    private MainFrame Mainframe;
    private GamePanel gamePanel;

    // ================================================================================


    /**
     * Initializes the listener so that it has no MainFrame
     * and no GamePanel.
     */
    public GameListener() {
        Mainframe = null;
        gamePanel = null;
    }


    /**
     * Set the MainFrame and GamePanel for this Listener.
     *
     * @param mainFrame the MainFrame
     * @param gamepanel the GamePanel
     */
    public GameListener(MainFrame mainFrame, GamePanel gamepanel) {
        this.Mainframe = mainFrame;
        this.gamePanel = gamepanel;
    }


    // ================================================================================


    /**
     * Respond to the user pressing a button
     *
     * @param e the action event to respond to
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int source = e.getKeyCode();

        if (GamePanel.isOver == false) {

            if (source == KeyEvent.VK_LEFT) {
                gamePanel.MoveBallLeft();
            } else if (source == KeyEvent.VK_RIGHT) {
                gamePanel.MoveBallRight();
            } else if (source == KeyEvent.VK_SPACE) {
                GamePanel.isPause = !GamePanel.isPause;

                if (GamePanel.isInstructing == true || GamePanel.isAbouting == true) {

                    GamePanel.isInstructing = false;
                    GamePanel.isAbouting = false;
                    GamePanel.isPause = true;

                }

            }

        }

        if (source == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

    }

    // ================================================================================

    @Override
    public void keyReleased(KeyEvent e) {
    }

    // ================================================================================

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // ================================================================================


    /**
     * Respond to the user clicking a menu item in the MainFrame
     *
     * @param event the action event to respond to
     */
    @Override
    public void actionPerformed(ActionEvent event) {

        String command = event.getActionCommand();

        if (command.equals(MainFrame.NEW_GAME)) {
            GamePanel.isReStart = true;

        } else if (command.equals(MainFrame.EXIT)) {
            System.exit(0);
        } else if (command.equals(MainFrame.EARTH)) {
            Mainframe.Jupiter.setSelected(false);
            Mainframe.Sun.setSelected(false);
            Mainframe.Earth.setSelected(true);
            gamePanel.ChangeDiffculy("Earth");
        } else if (command.equals(MainFrame.JUPITER)) {
            Mainframe.Earth.setSelected(false);
            Mainframe.Sun.setSelected(false);
            Mainframe.Jupiter.setSelected(true);
            gamePanel.ChangeDiffculy("Jupiter");
        } else if (command.equals(MainFrame.SUN)) {
            Mainframe.Earth.setSelected(false);
            Mainframe.Jupiter.setSelected(false);
            Mainframe.Sun.setSelected(true);
            gamePanel.ChangeDiffculy("Sun");
        } else if (command.equals(MainFrame.BLACK)) {
            MainFrame.Blue.setSelected(false);
            MainFrame.Green.setSelected(false);
            MainFrame.Pink.setSelected(false);
            MainFrame.Black.setSelected(true);
            MainFrame.BallColor = Color.BLACK;

        } else if (command.equals(MainFrame.BLUE)) {
            MainFrame.Green.setSelected(false);
            MainFrame.Black.setSelected(false);
            MainFrame.Pink.setSelected(false);
            MainFrame.Blue.setSelected(true);
            MainFrame.BallColor = Color.BLUE;
        } else if (command.equals(MainFrame.GREEN)) {
            MainFrame.Blue.setSelected(false);
            MainFrame.Black.setSelected(false);
            MainFrame.Pink.setSelected(false);
            MainFrame.Green.setSelected(true);
            MainFrame.BallColor = Color.GREEN;
        } else if (command.equals(MainFrame.PINK)) {
            MainFrame.Blue.setSelected(false);
            MainFrame.Green.setSelected(false);
            MainFrame.Black.setSelected(false);
            MainFrame.Pink.setSelected(true);
            MainFrame.BallColor = Color.pink;
        } else if (command.equals(MainFrame.INSTRUCTIONS)) {
            GamePanel.isPause = true;
            GamePanel.isAbouting = false;
            GamePanel.isInstructing = !GamePanel.isInstructing;
        } else if (command.equals(MainFrame.ABOUT)) {
            GamePanel.isPause = true;
            GamePanel.isInstructing = false;
            GamePanel.isAbouting = !GamePanel.isAbouting;
        }

    }

}

