package classes;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Represents a login window for the game, where the user can enter the names of
 * the players in the game. The window will close when the user clicks the
 * submit button and will open the game window.
 */
public class LoginWindow
    extends JFrame
    implements ActionListener
{
    private JeopardyGame     game;
    private JButton          b1;
    private JPanel           newPanel;
    private JLabel           passLabel;
    private final JTextField textField1;

    /**
     * Creates a new login window for the game.
     * 
     * @param game
     *            the game to create the login window for
     */
    LoginWindow(JeopardyGame game)
    {
        this.game = game;

        setTitle("Login Window"); // set title to the login form

        // create label for password
        passLabel = new JLabel();
        passLabel.setText("Names (Seperated by a comma)");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        // create text field to get username from the user
        textField1 = new JTextField(15); // set length of the text
        textField1.setFont(new Font("Arial", Font.PLAIN, 20));

        // create submit button
        b1 = new JButton("Submit"); // set label to button

        // create panel to put form elements
        newPanel = new JPanel(new GridLayout(3, 1));
        newPanel.add(passLabel); // set password label to panel
        newPanel.add(textField1); // set text field to panel
        newPanel.add(b1); // set button to panel

        // set border to panel
        add(newPanel, BorderLayout.CENTER);

        // perform action on button click
        b1.addActionListener(this); // add action listener to button
    }


    /**
     * Adds the players in the game based on the text entered in the text field.
     * Names are seperated by a comma.
     */
    private void addPlayersInTheGame() // pass action listener as a
                                       // parameter
    {
        // username from the
        // textField1
        String userNames = textField1.getText(); // get user entered

        String[] theNames = userNames.split(",");
        for (int i = 0; i < theNames.length; i++)
        {
            theNames[i] = theNames[i].strip();
        }

        Set<String> myNamesSet = new HashSet<String>(Arrays.asList(theNames));

        if (myNamesSet.size() != theNames.length)
        {
            // throw the error
            JOptionPane.showMessageDialog(this, "Please enter unique names");
            return;
        }
        if (theNames.length < 2)
        {
            JOptionPane.showMessageDialog(this, "Please enter at least two names");
            return;
        }

        for (String name : theNames)
        {
            if (name.length() > 0)
            {
                Player p = new Player(name.strip());
                game.addPlayer(p);
            }
        }

        // throw dialog errors when two names are the same
    }


    /**
     * Performs an action when the submit button is clicked. The action is to
     * add the players in the game based on the text entered in the text field.
     * The helper method addPlayersInTheGame() is called to add the players in
     * the game.
     * 
     * @param e
     *            the action event
     */
    public void actionPerformed(ActionEvent e)
    {
        addPlayersInTheGame();

        if (game.getPlayers().size() == 0)
        {
            return;
        }

        // close the login form
        dispose();

        GameWindow gameWindow = new GameWindow(game);
        gameWindow.setSize(600, 600);
        gameWindow.setLocationRelativeTo(null); // center the frame

        // Function to set visibility of JFrame.
        gameWindow.setVisible(true);

        // Function to set default operation of JFrame.
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
