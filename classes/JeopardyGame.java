package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class JeopardyGame
{
    private TreeSet<Player>          players;
    private TreeMap<Integer, Card[]> questions;

    public JeopardyGame()
    {
        players = new TreeSet<Player>();
        questions = new TreeMap<Integer, Card[]>();
    }


    /**
     * Adds a player to the Jeopardy Game
     */
    public void addPlayer(Player player)
    {
        players.add(player);
    }


    /**
     * Returns an ArrayList of players from the TreeSet for convenient access
     * 
     * @return an ArrayList of players
     */
    public ArrayList<Player> getPlayers()
    {
        ArrayList<Player> p = new ArrayList<Player>();
        Iterator<Player> i = players.iterator();
        while (i.hasNext())
        {
            p.add(i.next());
        }
        return p;
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
        Card retVal = null;

        String filename = point + ".csv";
        String pathname = "assets/jeopardy-questions/main/" + filename;

        File file = new File(pathname);
        Scanner input = null;
        Scanner input2 = null;

        // instantiate the Scanner objects: one to count the number of lines and
        // the other to get the random question
        try
        {
            input = new Scanner(file);
            input2 = new Scanner(file);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("***Cannot open " + pathname + " ***");
            System.exit(1);
        }

        // count the number of lines in the file
        int numLines = 0;
        while (input.hasNext())
        {
            numLines++;
            input.nextLine();
        }

        // get a random question
        int randomQuestionLine = (int)(Math.random() * numLines);
        int count = 0;
        while (input2.hasNext())
        {
            if (count == randomQuestionLine)
            {
                String str = input2.nextLine();
                String[] arr = str.split("\",\"");

                String category = arr[0];
                String pointValue = arr[1].replace("$", "");
                String question = arr[2];
                String answer = arr[3];

                retVal = new Card(question, answer, Integer.parseInt(pointValue), category);

                break;
            }

            count++;
            input2.nextLine();
        }

        return retVal;
    }


    public static void main(String args[])
    {
        JeopardyGame game = new JeopardyGame();
        Card[] card = game.getFiveRandomCards(100);

        // print out the questions
        for (int i = 0; i < 5; i++)
        {
            System.out.println(card[i].getQuestion());
        }

    }
}
