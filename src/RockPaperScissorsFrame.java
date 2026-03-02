import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.util.Random;

public class RockPaperScissorsFrame extends JFrame {

    /** Text area where all previous moves are displayed. */
    private JTextArea historyArea;

    // GUI Components
    private JButton rockBtn, paperBtn, scissorsBtn, quitBtn;
    private JTextField playerWinsField, computerWinsField, tiesField;
    private JTextArea resultsArea;

    // Game Stats
    private int playerWins = 0;
    private int computerWins = 0;
    private int ties = 0;
    private final Random random = new Random();

    /** List of moves. Using list */
    List<String> historyMoves = new ArrayList<>();

    private Font titleFont = new Font("Comic Sans MS", Font.BOLD, 42);
    private Font fortuneFont = new Font("Comic Sans MS", Font.PLAIN, 18);
    private Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 20);

    /** Stores the index of the previously displayed fortune. */
    private int lastIndex = -1;

    public RockPaperScissorsFrame() {
        super("Rock Paper Scissors");

        setLayout(new BorderLayout());

        add(createTopPanel(), BorderLayout.NORTH);

        add(createMiddlePanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
        add(createLeftPanel(), BorderLayout.WEST);
        // initializeFortunes(); going to replace this with List<String> historyMoves
        configureFrame();
    }

    private JScrollPane createResultsPanel() {
        resultsArea = new JTextArea(10, 40);
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // JScrollPane wraps the text area to provide scrollbars
        return new JScrollPane(resultsArea);
    }

    private ImageIcon loadAndScaleImage(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image scaledImage = originalIcon.getImage()
                .getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 6, 5, 5));
        panel.setBorder(new EtchedBorder());

        playerWinsField = new JTextField("0", 5);
        computerWinsField = new JTextField("0", 5);
        tiesField = new JTextField("0", 5);

        // Make fields read-only
        playerWinsField.setEditable(false);
        computerWinsField.setEditable(false);
        tiesField.setEditable(false);

        panel.add(new JLabel("Player Wins:", SwingConstants.RIGHT));
        panel.add(playerWinsField);
        panel.add(new JLabel("Comp Wins:", SwingConstants.RIGHT));
        panel.add(computerWinsField);
        panel.add(new JLabel("Ties:", SwingConstants.RIGHT));
        panel.add(tiesField);

        return panel;
    }

    private JPanel createTopPanel() {

        JPanel panel = new JPanel();

        ImageIcon scaledIcon = loadAndScaleImage("resources/fortune.jpg", 200, 200);

        JLabel label = new JLabel("Rock Paper Scissors", scaledIcon, JLabel.CENTER);

        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setFont(titleFont);

        panel.add(label);
        return panel;
    }

    private JPanel createMiddlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        return panel;
    }

    private Component createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        historyArea = new JTextArea(10, 40);
        historyArea.setEditable(false);
        //historyArea.setFont();

        JScrollPane scrollPane = new JScrollPane(historyArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private String getWinString(String winner, String loser) {
        if (winner.equals("Rock")) return "Rock breaks " + loser;
        if (winner.equals("Paper")) return "Paper covers " + loser;
        return "Scissors cuts " + loser;
    }

    private void playGame(String playerChoice) {
        String[] options = {"Rock", "Paper", "Scissors"};
        String computerChoice = options[random.nextInt(3)];
        String outcome = "";

        if (playerChoice.equals(computerChoice)) {
            outcome = "It's a tie! Both chose " + playerChoice;
            ties++;
        } else if ((playerChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                (playerChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                (playerChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            outcome = getWinString(playerChoice, computerChoice) + " (Player Wins)";
            playerWins++;
        } else {
            outcome = getWinString(computerChoice, playerChoice) + " (Computer Wins)";
            computerWins++;
        }

        // Update UI components
        resultsArea.append(outcome + "\n");
        playerWinsField.setText(String.valueOf(playerWins));
        computerWinsField.setText(String.valueOf(computerWins));
        tiesField.setText(String.valueOf(ties));
    }


    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(new EtchedBorder(), "Game Controls"));

        // Helper to create buttons quickly
        JButton rockBtn = new JButton("Rock");
        JButton paperBtn = new JButton("Paper");
        JButton scissorsBtn = new JButton("Scissors");
        JButton quitBtn = new JButton("Quit", new ImageIcon("src/images/quit.png"));

        // Action Listeners
        rockBtn.addActionListener(e -> playGame("Rock"));
        paperBtn.addActionListener(e -> playGame("Paper"));
        scissorsBtn.addActionListener(e -> playGame("Scissors"));
        quitBtn.addActionListener(e -> System.exit(0));

        panel.add(rockBtn);
        panel.add(paperBtn);
        panel.add(scissorsBtn);
        panel.add(quitBtn);
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
