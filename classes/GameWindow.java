package classes;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

    /**
     * Constructs a new game window
     * 
     * @param game
     *            - the game to be played
     */
    public GameWindow(JeopardyGame game)
    {
        this.game = game;

        // General Layout
        setTitle("Jeopardy");
        // setSize(MAXIMIZED_HORIZ, MAXIMIZED_VERT);

        this.setPreferredSize(new Dimension(300, 100));
        this.pack();
        this.setVisible(true);

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
                JPanel questionPanel = new JPanel(new BorderLayout());
                JTextArea questionText = new JTextArea(card.getQuestion());
                questionText.setWrapStyleWord(true);
                questionText.setLineWrap(true);
                questionText.setEditable(false);
                questionPanel.add(questionText, BorderLayout.NORTH);

                // Create the answer field and submit button
                JTextField answerField = new JTextField();
                answerField.setPreferredSize(new Dimension(200, 30));
                answerField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                questionPanel.add(answerField, BorderLayout.CENTER);
                JButton submitButton = new JButton("Submit");
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

                        // Close the question window
                        questionFrame.dispose();
                    }
                });
                questionPanel.add(submitButton, BorderLayout.SOUTH);
                questionFrame.add(questionPanel);
                questionFrame.setSize(600, 400);
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
        ArrayList<Player> p = game.getPlayers();

        JButton leaderboard = new JButton("Leaderboard");

        leaderboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JFrame qFrame = new JFrame("Leaderboard");
                JPanel qPanel = new JPanel(new BorderLayout());
                for (int i = 0; i < p.size(); i++)
                {
                    String str = (i + 1) + " " + p.get(i);
                    JTextArea qText = new JTextArea(str);
                    qText.setWrapStyleWord(true);
                    qText.setLineWrap(true);
                    qText.setEditable(false);
                    qPanel.add(qText, BorderLayout.NORTH);
                }

                qFrame.add(qPanel);
                qFrame.pack(); // Pack the components in the JFrame
                qFrame.setVisible(true); // Make the JFrame visible
            }
        });

        // Adding the leaderboard button to the game window
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;
        gbc.gridwidth = 5; // Set gridwidth to 5
        buttonPanel.add(leaderboard, gbc);
        c.add(buttonPanel);
    }


    public void actionPerformed(ActionEvent e)
    {

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
