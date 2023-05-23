package classes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
        implements ActionListener {
    private JeopardyGame game;

    // initialize button, panel, label, and text field

    boolean twoFieldsMatch; // checks if number of players equals
                            // the number of names

    JButton b1;
    JPanel newPanel;
    JLabel passLabel;
    final JTextField textField1;
    // JButton leaderboard;

    // calling constructor
    LoginWindow(JeopardyGame game) {
        this.game = game;

        setTitle("Login Window"); // set title to the login form

        // create label for password
        passLabel = new JLabel();
        passLabel.setText("Names (Seperated by a comma)");

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
        for (int i = 0; i < theNames.length; i++) {
            theNames[i] = theNames[i].strip();
        }

        ArrayList<String> names = new ArrayList<>(Arrays.asList(theNames));

        Set<String> myNamesSet = new HashSet<String>(names);

        if (myNamesSet.size() != names.size()) {
            // throw the error
            JOptionPane.showMessageDialog(this, "Please enter unique names");
            return;
        }

        for (String name : names) {
            Player p = new Player(name.strip());
            game.addPlayer(p);
        }

        // throw dialog errors when two names are the same
    }

    public void actionPerformed(ActionEvent e) {
        addPlayersInTheGame();

        if (game.getPlayers().size() == 0) {
            return;
        }

        // close the login form
        dispose();

        GameWindow gameWindow = new GameWindow(game);
        gameWindow.setSize(400, 400);

        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the JFrame
        int w = gameWindow.getSize().width;
        int h = gameWindow.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        // Move the JFrame to the center of the screen
        gameWindow.setLocation(x, y);

        // Function to set visibility of JFrame.
        gameWindow.setVisible(true);

        // Function to set default operation of JFrame.
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

// create the main class
class LoginWindowDemo {
    // main() method start
    public static void main(String arg[]) {
        JeopardyGame j = new JeopardyGame();

        try {
            // create instance of the CreateLoginForm
            LoginWindow form = new LoginWindow(j);
            form.setSize(300, 100); // set size of the frame
            form.setVisible(true); // make form visible to the user
        } catch (Exception e) {
            // handle exception
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
