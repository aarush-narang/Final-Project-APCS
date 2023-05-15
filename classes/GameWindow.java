package classes;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

// class extends JFrame and implements actionlistener
public class GameWindow
    extends JFrame
    implements ActionListener
{

    // Declaration of objects of CardLayout class.
    CardLayout card;

    // Declaration of objects of JButton class.
    JButton    b1, b2, b3;

    // Declaration of objects
    // of Container class.
    Container  c;

    GameWindow()
    {

        // to get the content
        c = getContentPane();

        // Initialization of object "card"
        // of CardLayout class with 40
        // horizontal space and 30 vertical space .
        card = new CardLayout(40, 30);

        // set the layout
        c.setLayout(card);

        // Initialization of object "b1" of JButton class.
        b1 = new JButton("AP");

        // Initialization of object "b2" of JButton class.
        b2 = new JButton("Computer");

        // Initialization of object "b3" of JButton class.
        b3 = new JButton("Science");

        // this Keyword refers to current object.
        // Adding Jbutton "b1" on JFrame using ActionListener.
        b1.addActionListener(this);

        // Adding Jbutton "b2" on JFrame using ActionListener.
        b2.addActionListener(this);

        // Adding Jbutton "b3" on JFrame using ActionListener.
        b3.addActionListener(this);

        // Adding the JButton "b1"
        c.add("a", b1);

        // Adding the JButton "b2"
        c.add("b", b2);

        // Adding the JButton "b1"
        c.add("c", b3);
    }


    public void actionPerformed(ActionEvent e)
    {

        // call the next card
        card.next(c);
    }


    // Main Method
    public static void main(String[] args)
    {

        // Creating Object of CardLayout class.
        GameWindow cl = new GameWindow();

        // Function to set size of JFrame.
        cl.setSize(400, 400);

        // Function to set visibility of JFrame.
        cl.setVisible(true);

        // Function to set default operation of JFrame.
        cl.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
