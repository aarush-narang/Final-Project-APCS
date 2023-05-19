package classes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
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

    boolean twoFieldsMatch; // checks if number of players equals the number of names

    JButton              b1;
    JPanel               newPanel;
    JLabel               userLabel, passLabel;
    final JTextField     textField1, textField2;
    //JButton leaderboard;

    // calling constructor
    LoginWindow(JeopardyGame game)
    {
        this.game = game;

        // create label for username
        userLabel = new JLabel();
        userLabel.setText("Number of Players");      // set label value for
                                                     // textField1

        // create text field to get username from the user
        textField1 = new JTextField(15);    // set length of the text

        // create label for password
        passLabel = new JLabel();
        passLabel.setText("Names (Seperated by a comma in quotes -> \",\")");      // set label value for textField2

        // create text field to get password from the user
        textField2 = new JTextField(15);    // set length for the password

        // create submit button
        b1 = new JButton("Submit"); // set label to button

        // create panel to put form elements
        newPanel = new JPanel(new GridLayout(3, 1));
        newPanel.add(userLabel);    // set username label to panel
        newPanel.add(textField1);   // set text field to panel
        newPanel.add(passLabel);    // set password label to panel
        newPanel.add(textField2);   // set text field to panel
        newPanel.add(b1);           // set button to panel

        // set border to panel
        add(newPanel, BorderLayout.CENTER);

        // perform action on button click
        b1.addActionListener(this);     // add action listener to button
        setTitle("Login Window");         // set title to the login form

    }


    // define abstract method actionPerformed() which will be called on button
    // click
    public ArrayList<Player> playersInTheGame()    // pass action listener as a
                                                    // parameter
    {
        String numPlayers = textField1.getText();        // get user entered
                                                         // username from the
                                                         // textField1
        String userNames = textField2.getText();        // get user entered

        String[] theNames = userNames.split("\",\"");
        ArrayList<String> names = new ArrayList<>(Arrays.asList(theNames));

        for (String name : names)
        {
            Player p = new Player(name);
            game.addPlayer(p);
        }
        ArrayList<Player> players = game.getPlayers();
        return players;
    }
    
    


        // pasword from the
        // textField2

        // check whether the credentials are authentic or not
        // if (userValue.equals("test1@gmail.com") && passValue.equals("test"))
        // { // if authentic, navigate user to a new page

        // // create instance of the NewPage
        // NewPage page = new NewPage();

        // // make page visible to the user
        // page.setVisible(true);

        // // create a welcome label and set it to the new page
        // JLabel wel_label = new JLabel("Welcome: " + userValue);
        // page.getContentPane().add(wel_label);
        // }
        // else
        // {
        // // show error message
        // System.out.println("Please enter valid username and password");
        // }
    }
}




// create the main class
class LoginWindowDemo
{
    // main() method start
    public static void main(String arg[])
    {
        JeopardyGame j = new JeopardyGame();

        try
        {
            // create instance of the CreateLoginForm
            LoginWindow form = new LoginWindow(j);
            form.setSize(300, 100);  // set size of the frame
            form.setVisible(true);  // make form visible to the user
        }
        catch (Exception e)
        {
            // handle exception
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
