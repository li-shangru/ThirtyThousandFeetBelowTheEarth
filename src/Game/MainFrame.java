package Game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class MainFrame extends JFrame {

    // ================================================================================
    public static final int APP_WIDTH = 480;
    public static final int APP_HEIGHT = 640;
    public static final String APP_TITLE = "Thirty Thousand Feet Below the Earth";
    public static final String NEW_GAME = "New Game";
    public static final String EXIT = "EXIT";
    public static final String INSTRUCTIONS = "Instructions";
    public static final String ABOUT = "About";
    public static final String EARTH = "Earth";
    public static final String SUN = "Sun";
    public static final String JUPITER = "Jupiter";
    public static final String BLACK = "Black";
    public static final String GREEN = "Green";
    public static final String BLUE = "Blue";
    public static final String PINK = "Pink";
    private static final int APP_TOP = 200;
    private static final int APP_LEFT = 200;
    public static Color BallColor = Color.BLACK;
    public static Color BackGroundColor = Color.white;
    public static JRadioButton Black = new JRadioButton(BLACK);
    public static JRadioButton Green = new JRadioButton(GREEN);
    public static JRadioButton Blue = new JRadioButton(BLUE);
    public static JRadioButton Pink = new JRadioButton(PINK);
    private final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
    public JRadioButton Earth = new JRadioButton(EARTH);
    public JRadioButton Jupiter = new JRadioButton(JUPITER);
    public JRadioButton Sun = new JRadioButton(SUN);
    private GamePanel gamePanel = new GamePanel();

    /**
     * Initialized the main frame then call the update() method in game panel.
     */
    public MainFrame() {
        super(APP_TITLE);
        setMinimumSize(new Dimension(APP_WIDTH, APP_HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        SetMenu();

        add(BorderLayout.CENTER, gamePanel);
        addKeyListener(new GameListener(this, gamePanel));

        pack();
        this.setLocation(new Point(APP_LEFT, APP_TOP));
        setVisible(true);

        gamePanel.update();
    }

    // ================================================================================

    /**
     * The main method to run the game.
     *
     * @param args
     */
    public static void main(String[] args) {
        new MainFrame();
    }

    // ================================================================================

    /**
     * A method to add various menu and buttons to the frame.
     */
    private void SetMenu() {

        JMenuBar menuBar = new JMenuBar();

        // ================================================================================

        JMenu GameMenu = new JMenu("Game");

        JMenuItem NewGame = new JMenuItem(NEW_GAME);
        JMenuItem ExitGame = new JMenuItem(EXIT);

        NewGame.setActionCommand(NEW_GAME);
        NewGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));
        NewGame.addActionListener(new GameListener(this, gamePanel));

        ExitGame.setActionCommand(EXIT);
        ExitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, SHORTCUT_MASK));
        ExitGame.addActionListener(new GameListener(this, gamePanel));

        GameMenu.add(NewGame);
        GameMenu.add(ExitGame);

        menuBar.add(GameMenu);

        // ================================================================================

        JMenu SettingsMenu = new JMenu("Settings");


        JMenu DifficultyMenu = new JMenu("Change Difficulty");

        Earth.setSelected(true);
        Earth.setActionCommand(EARTH);
        Earth.addActionListener(new GameListener(this, gamePanel));

        Jupiter.setActionCommand(JUPITER);
        Jupiter.addActionListener(new GameListener(this, gamePanel));

        Sun.setActionCommand(SUN);
        Sun.addActionListener(new GameListener(this, gamePanel));

        DifficultyMenu.add(Earth);
        DifficultyMenu.add(Jupiter);
        DifficultyMenu.add(Sun);


        JMenu SkinMenu = new JMenu("Change Skin");

        Black.setSelected(true);
        Black.setActionCommand(BLACK);
        Black.addActionListener(new GameListener(this, gamePanel));

        Green.setActionCommand(GREEN);
        Green.addActionListener(new GameListener(this, gamePanel));
        Green.setEnabled(false);

        Blue.setActionCommand(BLUE);
        Blue.addActionListener(new GameListener(this, gamePanel));
        Blue.setEnabled(false);

        Pink.setActionCommand(PINK);
        Pink.addActionListener(new GameListener(this, gamePanel));
        Pink.setEnabled(false);

        SkinMenu.add(Black);
        SkinMenu.add(Green);
        SkinMenu.add(Blue);
        SkinMenu.add(Pink);


        SettingsMenu.add(DifficultyMenu);
        SettingsMenu.add(SkinMenu);

        menuBar.add(SettingsMenu);

        // ================================================================================

        JMenu HelpMenu = new JMenu("Help");

        JMenuItem Instructions = new JMenuItem(INSTRUCTIONS);

        Instructions.setActionCommand(INSTRUCTIONS);
        Instructions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, SHORTCUT_MASK));
        Instructions.addActionListener(new GameListener(this, gamePanel));

        JMenuItem About = new JMenuItem(ABOUT);

        About.setActionCommand(ABOUT);
        About.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, SHORTCUT_MASK));
        About.addActionListener(new GameListener(this, gamePanel));

        HelpMenu.add(Instructions);
        HelpMenu.add(About);

        menuBar.add(HelpMenu);

        // ================================================================================

        setJMenuBar(menuBar);
    }
}
