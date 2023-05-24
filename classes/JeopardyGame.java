package classes;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JFrame;

public class JeopardyGame
{
    public static final int          MAX_POINTS = 500;
    private TreeSet<Player>          players;
    private Queue<Player>            playerQueue;
    private TreeMap<Integer, Card[]> questions;

    public JeopardyGame()
    {
        players = new TreeSet<Player>();
        playerQueue = new LinkedList<Player>();
        questions = new TreeMap<Integer, Card[]>();

        for (int i = 100; i <= MAX_POINTS; i += 100)
        {
            questions.put(i, getFiveRandomCards(i));
        }
    }


    /**
     * Adds a player to the Jeopardy Game
     */
    public void addPlayer(Player player)
    {
        players.add(player);
        playerQueue.add(player);
    }


    /**
     * Returns an ArrayList of players from the TreeSet for convenient access
     * 
     * @return an ArrayList of players in descending order
     */
    public ArrayList<Player> getPlayers()
    {
        ArrayList<Player> p = new ArrayList<Player>();
        Iterator<Player> i = players.iterator();
        while (i.hasNext())
        {
            Player player = i.next();
            p.add(player);
        }

        return p;
    }


    /**
     * Gets the next player in the queue
     * 
     * @return the next player in the queue
     */
    public Player getNextPlayer()
    {
        Player player = playerQueue.poll();
        playerQueue.add(player);

        return player;
    }


    /**
     * Gets the current player
     * 
     * @return the current player
     */
    public Player getCurrentPlayer()
    {
        return playerQueue.peek();
    }


    /**
     * Gets the questions for the game
     * 
     * @return a TreeMap of questions
     */
    public TreeMap<Integer, Card[]> getQuestions()
    {
        return questions;
    }


    /**
     * Gets the questions for the game given the point value
     * 
     * @param points
     *            the point value for the questions
     * @return an array of questions
     */
    public Card[] getQuestions(int points)
    {
        return questions.get(points);
    }


    /**
     * Gets five random cards given the point value
     * 
     * @param point
     *            the point value for which you want the questions
     * @return returns an array of five random cards
     */
    private Card[] getFiveRandomCards(int point)
    {
        Card[] card = new Card[5];
        for (int i = 0; i < 5; i++)
        {
            card[i] = getSingularCard(point);
        }
        return card;
    }


    /**
     * Gets a singular card given a point value
     * 
     * @param point
     *            value for the question
     * @return the Card with the question
     */
    private Card getSingularCard(int point)
    {
        String filename = point + ".csv";
        String pathname = "assets/jeopardy-questions/main/" + filename;

        File file = new File(pathname);
        Scanner input = null;

        try
        {
            input = new Scanner(file);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("***Cannot open " + pathname + " ***");
            System.exit(1);
        }

        int numLines = 0;
        while (input.hasNext())
        {
            numLines++;
            input.nextLine();
        }

        int randomQuestionLine = (int)(Math.random() * numLines);
        int count = 0;

        try
        {
            input = new Scanner(file);
            while (input.hasNext())
            {
                if (count == randomQuestionLine)
                {
                    String str = input.nextLine();
                    String[] arr = str.substring(1, str.length() - 1).split("\",\"");

                    String category = arr[0];
                    String pointValue = arr[1].replace("$", "");
                    String question = arr[2];
                    String answer = arr[3];

                    input.close();

                    return new Card(question, answer, Integer.parseInt(pointValue), category);
                }

                count++;
                input.nextLine();
            }
        }
        catch (FileNotFoundException ex)
        {
            input.close();

            System.out.println("***Cannot open " + pathname + " ***");
            System.exit(1);
        }

        input.close();

        return null;
    }


    public static void main(String args[])
    {
        JeopardyGame game = new JeopardyGame();

        LoginWindow login = new LoginWindow(game);

        // Function to set size of JFrame.
        login.setSize(500, 200);
        login.setLocationRelativeTo(null); // center the frame

        // Function to set visibility of JFrame.
        login.setVisible(true);

        // Function to set default operation of JFrame.
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
