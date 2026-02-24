import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RockPaperScissorsFrame extends JFrame {

    /** Text area where all previous moves are displayed. */
    private JTextArea historyArea;

    /** List of moves. Using list */
    List<String> historyMoves = new ArrayList<>();

    /** Random number generator for selecting fortunes. */
    //private Random random = new Random();

    /** Stores the index of the previously displayed fortune. */
    private int lastIndex = -1;


    /**
     * Constructs the RockPaperScissorsFrame.
     *
     * Initializes layout, panels, fortunes, and frame configuration.
     */

    public RockPaperScissorsFrame() {
        super("Rock Paper Scissors");

        setLayout(new BorderLayout());

        add(createTopPanel(), BorderLayout.NORTH);
        add(createMiddlePanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        // initializeFortunes(); going to replace this with List<String> historyMoves
        configureFrame();
    }


    /**
     * Loads and scales an image from the given path.
     *
     * @param path  the file path of the image
     * @param width desired width
     * @param height desired height
     * @return a scaled ImageIcon
     */

    private ImageIcon loadAndScaleImage(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image scaledImage = originalIcon.getImage()
                .getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Creates the top panel containing the title and scores of CPU and Player.
     *
     * @return JPanel containing the formatted title label with icon
     */

    private JPanel createTopPanel() {

        JLabel titleLabel = customInteraction.createLabel(
                "Rock Paper Scissors",
                "Comic Sans",
                Font.BOLD,
                36,
                Color.BLUE,
                SwingConstants.LEFT,
                SwingConstants.CENTER
        );

        JLabel personScore = customInteraction.createLabel(
                "Player Score:",
                "Comic Sans",
                Font.BOLD,
                24,
                Color.BLUE,
                SwingConstants.CENTER,
                SwingConstants.CENTER
        );

        JLabel computerScore = customInteraction.createLabel(
                "CPU Score:",
                "Comic Sans",
                Font.BOLD,
                24,
                Color.RED,
                SwingConstants.CENTER,
                SwingConstants.CENTER
        );
        // Score panel (side by side)
        JPanel scorePanel = new JPanel(new GridLayout(1, 2, 10, 0)); // 10px horizontal gap
        scorePanel.setBackground(Color.WHITE);
        scorePanel.add(personScore);
        scorePanel.add(computerScore);

        // Top panel with BorderLayout: title left, scores right
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(scorePanel, BorderLayout.EAST);
        return topPanel;
    }

    /**
     * Creates the middle panel containing the scrollable text area
     * where historymoves are displayed.
     *
     * @return JPanel containing a JScrollPane with JTextArea
     */

    private JPanel createMiddlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        return panel;
    }

    /**
     * Creates the left panel containing the history list of moves.
     *
     * @return JPanel containing the formatted title label with icon
     */

    private Component createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        historyArea = new JTextArea(10, 40);
        historyArea.setEditable(false);
        //historyArea.setFont(fortuneFont);

        JScrollPane scrollPane = new JScrollPane(historyArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }


    /**
     * Creates the bottom panel containing the action buttons.
     *
     * @return JPanel containing "Read My Fortune" and "Quit" buttons
     */

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();

        JButton readButton = new JButton("Read My Fortune!");
        JButton quitButton = new JButton("Quit");

        //readButton.addActionListener(e -> displayFortune());
        quitButton.addActionListener(e -> System.exit(0));

        panel.add(readButton);
        panel.add(quitButton);

        return panel;
    }


    /**
     * Configures the main application window.
     *
     * Sets window size to 3/4 of the screen
     * Centers the window
     * Sets close operation
     * Makes the frame visible
     */

    private void configureFrame() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int width = (int)(screenSize.width * 0.75);
        int height = (int)(screenSize.height * 0.75);

        setSize(width, height);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Main entry point of the Fortune Teller application.
     *
     * Ensures the GUI is created on the Event Dispatch Thread (EDT)
     * using SwingUtilities.invokeLater.
     */

    public static void main(String[] args)
    {
        // The invokeLater method ensures the GUI is created on the
        // Event Dispatch Thread (EDT) to avoid thread interference.

        SwingUtilities.invokeLater(() -> { new RockPaperScissorsFrame();

        });
    }
}
