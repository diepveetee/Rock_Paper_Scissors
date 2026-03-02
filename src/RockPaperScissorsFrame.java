import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RockPaperScissorsFrame extends JFrame {


    /** Text area where all previous moves are displayed. */
    private JTextArea historyArea;

    // Game stats
    private int playerWins = 0;
    private int computerWins = 0;
    private int ties = 0;

    // UI fields for stats
    private JTextField playerWinsField;
    private JTextField computerWinsField;
    private JTextField tiesField;

    private int countR = 0;
    private int countP = 0;
    private int countS = 0;
    private String lastPlayerMove = null;

    private Strategy leastUsed = new LeastUsed();
    private Strategy mostUsed = new MostUsed();
    private Strategy lastUsed = new LastUsed();
    private Strategy randomStrategy = new RandomStrategy();
    private Strategy cheat = new Cheat();

    /** Stores the index of the previously displayed fortune. */
    private int lastIndex = -1;

    public RockPaperScissorsFrame() {
        super("Rock Paper Scissors Game");

        setLayout(new BorderLayout());

        add(createTopPanel(), BorderLayout.NORTH);
        add(createMiddlePanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        configureFrame();
    }

    private ImageIcon createIcon(String filename, int width, int height) {
        try {
            // Look in the resources folder (filename should be like "rock.png")
            java.net.URL resource = getClass().getResource("/" + filename);

            if (resource == null) {
                System.err.println("Could not find file: " + filename);
                return null;
            }

            ImageIcon originalIcon = new ImageIcon(resource);

            // Scale the image to fit your buttons (e.g., 50x50)
            Image scaledImage = originalIcon.getImage()
                    .getScaledInstance(width, height, Image.SCALE_SMOOTH);

            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates the top panel containing the title and scores of CPU and Player.
     *
     * @return JPanel containing the formatted title label with icon
     */

    private JPanel createTopPanel() {

        JLabel titleLabel = new JLabel("Rock Paper Scissors Game");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(new Color(10, 50, 160));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);

        JLabel personScore = new JLabel("Player:");
        personScore.setFont(new Font("SansSerif", Font.BOLD, 18));
        personScore.setForeground(Color.DARK_GRAY);

        JLabel computerScore = new JLabel("Computer:");
        computerScore.setFont(new Font("SansSerif", Font.BOLD, 18));
        computerScore.setForeground(Color.DARK_GRAY);
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
        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(new EmptyBorder(10,10,10,10));

        // Left: history
        panel.add(createLeftPanel(), BorderLayout.CENTER);

        // Right: controls + stats
        JPanel right = new JPanel(new BorderLayout(10,10));
        right.add(createControlsPanel(), BorderLayout.CENTER);
        right.add(createStatsPanel(), BorderLayout.SOUTH);

        panel.add(right, BorderLayout.EAST);

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

    private JPanel createControlsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Play"));

        JPanel buttons = new JPanel(new GridLayout(2,2,10,10));

        // Attempt to load icons from resources /images
        ImageIcon rockIcon = createIcon("rock.png", 64, 64);
        ImageIcon paperIcon = createIcon("paper.png", 64, 64);
        ImageIcon scissorsIcon = createIcon("scissors.png", 64, 64);

        JButton rockButton = new JButton("Rock", rockIcon);
        JButton paperButton = new JButton("Paper", paperIcon);
        JButton scissorsButton = new JButton("Scissors", scissorsIcon);
        JButton quitButton = new JButton("Quit");

        MoveListener listener = new MoveListener();

        rockButton.addActionListener(listener);
        paperButton.addActionListener(listener);
        scissorsButton.addActionListener(listener);
        quitButton.addActionListener(e -> System.exit(0));

        rockButton.setActionCommand("R");
        paperButton.setActionCommand("P");
        scissorsButton.setActionCommand("S");



        buttons.add(rockButton);
        buttons.add(paperButton);
        buttons.add(scissorsButton);
        buttons.add(quitButton);

        panel.add(buttons);

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(3,2,6,6));
        panel.setBorder(BorderFactory.createTitledBorder("Stats"));

        JLabel pLabel = new JLabel("Player Wins:");
        playerWinsField = new JTextField("0");
        playerWinsField.setEditable(false);

        JLabel cLabel = new JLabel("Computer Wins:");
        computerWinsField = new JTextField("0");
        computerWinsField.setEditable(false);

        JLabel tLabel = new JLabel("Ties:");
        tiesField = new JTextField("0");
        tiesField.setEditable(false);

        panel.add(pLabel);
        panel.add(playerWinsField);
        panel.add(cLabel);
        panel.add(computerWinsField);
        panel.add(tLabel);
        panel.add(tiesField);

        return panel;
    }

    /**
     * Creates the bottom panel containing the action buttons.
     *
     * @return JPanel containing "Read My Fortune" and "Quit" buttons
     */

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();

        panel.setBorder(new EmptyBorder(6,6,6,6));
        panel.add(new JLabel("Made By: Van Diep"));

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

    //Strategies

    public class LeastUsed implements Strategy{
        public String getMove(String playerMove) {
            int min = Math.min(countR, Math.min(countP, countS));
            if (min == countR) return "P"; // beats R
            if (min == countP) return "S"; // beats P
            return "R";                    // beats S
        }
    }

    private class MostUsed implements Strategy {
        public String getMove(String playerMove) {
            int max = Math.max(countR, Math.max(countP, countS));

            if (max == countR) return "P"; // beats R
            if (max == countP) return "S"; // beats P
            return "R";                    // beats S
        }
    }

    private class LastUsed implements Strategy {
        public String getMove(String playerMove) {
            if (lastPlayerMove == null) return new RandomStrategy().getMove(playerMove);

            switch (lastPlayerMove) {
                case "R": return "P";
                case "P": return "S";
                default:  return "R";
            }
        }
    }

    private Strategy chooseStrategy() {
        double r = Math.random();

        if (r < 0.20) return leastUsed;      // 20%
        else if (r < 0.40) return mostUsed;  // 20%
        else if (r < 0.60) return lastUsed;  // 20%
        else if (r < 0.85) return randomStrategy;    // 25%
        else return cheat;                   // 15%
    }

    private class MoveListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            String playerMove = ae.getActionCommand(); // "R", "P", or "S"

            // update counts
            if (playerMove.equals("R")) countR++;
            else if (playerMove.equals("P")) countP++;
            else countS++;

            Strategy strat = chooseStrategy();
            String compMove = strat.getMove(playerMove);

            lastPlayerMove = playerMove;

            evaluateRound(playerMove, compMove, strat);
        }
    }

    private String toWord(String move) {
        switch (move) {
            case "R": return "Rock";
            case "P": return "Paper";
            case "S": return "Scissors";
        }
        return "";
    }

    private boolean winsAgainst(String p, String c) {
        return (p.equals("R") && c.equals("S")) ||
                (p.equals("P") && c.equals("R")) ||
                (p.equals("S") && c.equals("P"));
    }


    private void evaluateRound(String playerMove, String compMove, Strategy strat) {
        String strategyName = strat.getClass().getSimpleName();

        String pWord = toWord(playerMove);
        String cWord = toWord(compMove);

        String resultMessage;

        // Determine winner
        if (playerMove.equals(compMove)) {
            ties++;
            tiesField.setText(String.valueOf(ties));
            resultMessage = pWord + " equals " + cWord + ". (Tie! Computer used: " + strategyName + ")";
        }
        else if (winsAgainst(playerMove, compMove)) {
            playerWins++;
            playerWinsField.setText(String.valueOf(playerWins));
            resultMessage = pWord + " beats " + cWord + ". (Player wins! Computer used: " + strategyName + ")";
        }
        else {
            computerWins++;
            computerWinsField.setText(String.valueOf(computerWins));
            resultMessage = cWord + " beats " + pWord + ". (Computer wins! Computer used: " + strategyName + ")";
        }

        // Add to history
        historyArea.append(resultMessage + "\n");
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
