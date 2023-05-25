package classes;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * This class creates the game window for the Jeopardy game
 */
public class GameWindow
    extends JFrame
    implements ActionListener
{
    private JeopardyGame game;

    // Declaration of objects of CardLayout class.
    CardLayout           card;

    // Declaration of objects of JButton class.
    JButton              b1, b2;

    // Declaration of objects
    // of Container class.
    Container            c;

    JTextField           field;

    ArrayList<Player>    players;

    /**
     * Constructs a new game window
     * 
     * @param game
     *            - the game to be played
     */
    public GameWindow(JeopardyGame game)
    {
        this.game = game;
        updatePlayers();

        // General Layout
        setTitle("Jeopardy");

        c = getContentPane();

        // Initialize grid layout
        int rows = (JeopardyGame.MAX_POINTS / 100) + 1;
        int cols = 5;
        GridLayout grid = new GridLayout(rows, cols);
        grid.setHgap(5);
        grid.setVgap(5);
        c.setLayout(grid);

        // Create cards for each question
        TreeMap<Integer, Card[]> allCards = game.getQuestions();

        for (Card[] cards : allCards.values())
        {
            for (Card card : cards)
            {
                makeButton(card);
            }
        }

        makeLeaderbordButton();
    }


    public void makeButton(Card card)
    {
        // Initialization of object "b" of JButton class.
        JButton b = new JButton(Integer.toString(card.getPoints())); // point
                                                                     // value
        b.setBorder(new RoundedBorder(10));
        // Separate popup for each button that shows the question and answer
        // input
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                // Get the source of the event
                JButton button = (JButton)e.getSource();

                // Get the card associated with the button
                Card card = (Card)button.getClientProperty("card");
                if (!card.getVisibility())
                {
                    JOptionPane.showMessageDialog(
                        getContentPane(),
                        "This question has already been answered.");
                    return;
                }

                // Disable the button
                card.disableQuestion();

                // Create a new window with the question text and answer field
                JFrame questionFrame = new JFrame("Question");

                // Add a listener to the window to move to the next player when
                // the window is closed
                questionFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e)
                    {
                        button.setBackground(Color.RED);
                        button.setOpaque(true);
                        button.setBorderPainted(false);
                        button.setForeground(Color.WHITE);

                        game.getNextPlayer(); // Move to the next player

                    }
                });

                JPanel questionPanel = new JPanel(new BorderLayout());

                JTextArea questionText = new JTextArea(card.getQuestion());
                questionText.setWrapStyleWord(true);
                questionText.setLineWrap(true);
                questionText.setEditable(false);
                questionText.setFont(new Font("Arial", Font.PLAIN, 20));
                questionPanel.add(questionText, BorderLayout.NORTH);

                // Create the answer field and submit button
                JTextField answerField = new JTextField();
                answerField.setFont(new Font("Arial", Font.PLAIN, 16));
                questionPanel.add(answerField, BorderLayout.CENTER);

                JButton submitButton = new JButton("Submit");
                submitButton.setSize(600, 50);

                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        // Get the player's answer
                        String answer = answerField.getText();

                        if (answer.equals(""))
                        {
                            JOptionPane.showMessageDialog(questionFrame, "Please enter an answer.");
                            return;
                        }

                        boolean correct = card.submitAnswer(answer, game.getCurrentPlayer());

                        // Check if the answer is correct
                        if (correct)
                        {
                            // Display a message indicating that the answer is
                            // correct
                            JOptionPane.showMessageDialog(questionFrame, "Correct!");

                            // Set the color of the button to green
                            button.setBackground(Color.GREEN);
                            button.setOpaque(true);
                            button.setBorderPainted(false);
                            button.setForeground(Color.WHITE);
                        }
                        else
                        {
                            // Display a message indicating that the answer is
                            // incorrect
                            JOptionPane.showMessageDialog(
                                questionFrame,
                                "Incorrect. The correct answer is: " + card.getAnswer());

                            // Set the color of the button to red
                            button.setBackground(Color.RED);
                            button.setOpaque(true);
                            button.setBorderPainted(false);
                            button.setForeground(Color.WHITE);
                        }

                        game.getNextPlayer(); // Move to the next player

                        // Close the question window
                        questionFrame.dispose();
                    }
                });

                questionPanel.add(submitButton, BorderLayout.SOUTH);
                questionFrame.add(questionPanel);

                questionFrame.pack();
                questionFrame.setLocationRelativeTo(null);
                questionFrame.setSize(600, 200);
                questionFrame.setVisible(true);
            }
        });

        // Adding the button to the card layout
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buttonPanel.add(b, gbc);
        c.add(buttonPanel, Integer.toString(card.getPoints()));

        // Store the card in the button so we can access it later
        b.putClientProperty("card", card);
    }


    public void makeLeaderbordButton()
    {
        updatePlayers();

        JButton leaderboard = new JButton("Leaderboard");

        leaderboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                updatePlayers();

                JFrame qFrame = new JFrame("Leaderboard");

                qFrame.setPreferredSize(new Dimension(600, 300));
                qFrame.setLocationRelativeTo(null);

                JPanel qPanel = new JPanel(new BorderLayout());

                DefaultListModel<String> leaderboardModel = new DefaultListModel<String>();

                JPanel buttonPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;
                gbc.anchor = GridBagConstraints.NORTH;

                for (int i = 0; i < players.size(); i++)
                {
                    if (game.getCurrentPlayer().equals(players.get(i)))
                    {
                        leaderboardModel
                            .addElement((i + 1) + ". " + players.get(i) + " (current turn)");
                    }
                    else
                    {
                        leaderboardModel.addElement((i + 1) + ". " + players.get(i));
                    }

                    JButton plus = new JButton("+");
                    JButton minus = new JButton("-");

                    final int index = i; // Declare i as a final variable

                    plus.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e)
                        {
                            players.get(index).changePoints(100);

                            updatePlayers();

                            // Update the leaderboard model with the new player
                            // list
                            leaderboardModel.clear();
                            for (int i = 0; i < players.size(); i++)
                            {
                                if (game.getCurrentPlayer().equals(players.get(i)))
                                {
                                    leaderboardModel.addElement(
                                        (i + 1) + ". " + players.get(i) + " (current turn)");
                                }
                                else
                                {
                                    leaderboardModel.addElement((i + 1) + ". " + players.get(i));
                                }
                            }

                            // Update the leaderboard model with the new point
                            // values
                            for (int i = 0; i < players.size(); i++)
                            {
                                if (game.getCurrentPlayer().equals(players.get(i)))
                                {
                                    leaderboardModel.set(
                                        i,
                                        (i + 1) + ". " + players.get(i) + " (current turn)");
                                }
                                else
                                {
                                    leaderboardModel.set(i, (i + 1) + ". " + players.get(i));
                                }
                            }

                            updatePlayers();
                        }
                    });
                    minus.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e)
                        {
                            players.get(index).changePoints(-100);

                            updatePlayers();

                            // Update the leaderboard model with the new player
                            // list
                            leaderboardModel.clear();
                            for (int i = 0; i < players.size(); i++)
                            {
                                if (game.getCurrentPlayer().equals(players.get(i)))
                                {
                                    leaderboardModel.addElement(
                                        (i + 1) + ". " + players.get(i) + " (current turn)");
                                }
                                else
                                {
                                    leaderboardModel.addElement((i + 1) + ". " + players.get(i));
                                }
                            }

                            // Update the leaderboard model with the new point
                            // values
                            for (int i = 0; i < players.size(); i++)
                            {
                                if (game.getCurrentPlayer().equals(players.get(i)))
                                {
                                    leaderboardModel.set(
                                        i,
                                        (i + 1) + ". " + players.get(i) + " (current turn)");
                                }
                                else
                                {
                                    leaderboardModel.set(i, (i + 1) + ". " + players.get(i));
                                }
                            }

                            updatePlayers();
                        }
                    });

                    updatePlayers();

                    JPanel buttonSubPanel = new JPanel(new BorderLayout());

                    buttonSubPanel.add(minus, BorderLayout.WEST);
                    buttonSubPanel.add(plus, BorderLayout.EAST);

                    gbc.gridy = i; // Set the gridy to i
                    gbc.anchor = GridBagConstraints.NORTH; // Set the anchor to
                    // NORTH
                    buttonPanel.add(buttonSubPanel, gbc); // Add the sub-panel
                    // to the main button
                    // panel
                }

                updatePlayers();

                JScrollPane scrollPane = new JScrollPane(buttonPanel);
                JList<String> leaderboardList = new JList<>(leaderboardModel);
                JScrollPane scrollPane2 = new JScrollPane(leaderboardList);

                qPanel.add(scrollPane2, BorderLayout.WEST);
                qPanel.add(scrollPane, BorderLayout.CENTER);

                JSplitPane splitPane =
                    new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane2, scrollPane);
                splitPane.setResizeWeight(0.75);
                qPanel.add(splitPane, BorderLayout.CENTER);

                qFrame.add(qPanel);
                qFrame.pack();
                qFrame.setVisible(true);
            }
        });

        updatePlayers();

        // Adding the leaderboard button to the game window
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Set weightx to 1.0
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Set gridwidth to
        // REMAINDER
        gbc.anchor = GridBagConstraints.NORTH; // Set the anchor to NORTH
        gbc.gridy = players.size(); // Set the gridy to the last row
        buttonPanel.add(leaderboard, gbc);
        c.add(buttonPanel);
    }


    public void updatePlayers()
    {
        this.players = new ArrayList<>(game.getPlayers());
    }


    public void actionPerformed(ActionEvent e)
    {
        updatePlayers();
    }

    private static class RoundedBorder
        implements Border
    {

        private int radius;

        RoundedBorder(int radius)
        {
            this.radius = radius;
        }


        public Insets getBorderInsets(Component c)
        {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }


        public boolean isBorderOpaque()
        {
            return true;
        }


        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
        {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
