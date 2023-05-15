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
     * Gets five random cards given the point value
     * 
     * @param point
     *            the point value for which you want the questions
     * @return returns an array of five random cards
     */
    public Card[] getFiveRandomCards(int point)
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
        String pathname = point + ".txt";
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

        while (input.hasNext())
        {
            String str = input.nextLine();

        }
    }


    /**
     */
    public static void main(String args[])
    {

    }
}
