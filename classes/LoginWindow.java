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

// create CreateLoginForm class to create login form
// class extends JFrame to create a window where our component add
// class implements ActionListener to perform an action on button click
class LoginWindow
    extends JFrame
    implements ActionListener
{
    private JeopardyGame game;

    // initialize button, panel, label, and text field

    boolean              twoFieldsMatch; // checks if number of players equals
                                         // the number of names

    JButton              b1;
    JPanel               newPanel;
    JLabel               passLabel;
    final JTextField     textField1;
    // JButton leaderboard;

    // calling constructor
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


    // define abstract method actionPerformed() which will be called on button
    // click
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
