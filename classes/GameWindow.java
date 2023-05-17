package classes;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// class extends JFrame and implements actionlistener
public class GameWindow
    extends JFrame
    implements ActionListener
{
    JeopardyGame game;

    // Declaration of objects of CardLayout class.
    CardLayout   card;

    // Declaration of objects of JButton class.
    JButton      b1, b2;

    // Declaration of objects
    // of Container class.
    Container    c;

    JTextField   field;

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
        int rows = 5;
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
    }


    public void makeButton(Card card)
    {
        // Initialization of object "b" of JButton class.
        JButton b = new JButton(Integer.toString(card.getPoints())); // point
                                                                     // value

        // this Keyword refers to current object.
        // Adding JButton "b" on JFrame using ActionListener.
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                // Create a new window with the question text
                JFrame questionFrame = new JFrame("Question");
                JTextArea questionText = new JTextArea(card.getQuestion());
                questionText.setWrapStyleWord(true);
                questionText.setLineWrap(true);
                questionText.setEditable(false);
                questionFrame.add(questionText);
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

        // Store the card in the button
        b.putClientProperty("card", card);
    }


    public void actionPerformed(ActionEvent e)
    {
        // Get the source of the event
        JButton button = (JButton)e.getSource();

        // Get the card associated with the button
        Card card = (Card)button.getClientProperty("card");

        // Create a new window with the question text and answer field
        JFrame questionFrame = new JFrame("Question");
        JPanel questionPanel = new JPanel(new BorderLayout());
        JTextArea questionText = new JTextArea(card.getQuestion());
        questionText.setWrapStyleWord(true);
        questionText.setLineWrap(true);
        questionText.setEditable(false);
        questionPanel.add(questionText, BorderLayout.NORTH);
        JTextField answerField = new JTextField();
        answerField.setPreferredSize(new Dimension(200, 30));
        questionPanel.add(answerField, BorderLayout.CENTER);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                // Get the player's answer
                String answer = answerField.getText();

                // Check if the answer is correct
                if (answer.equals(card.getAnswer()))
                {
                    // Display a message indicating that the answer is correct
                    JOptionPane.showMessageDialog(questionFrame, "Correct!");
                }
                else
                {
                    // Display a message indicating that the answer is incorrect
                    JOptionPane.showMessageDialog(
                        questionFrame,
                        "Incorrect. The correct answer is: " + card.getAnswer());
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
}
