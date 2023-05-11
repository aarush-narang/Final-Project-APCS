package classes;

import java.util.ArrayList;
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
    public void addPlayer()
    {

    }


    /**
     * Returns an ArrayList of players from the TreeSet for convenient access
     * 
     * @return an ArrayList of players
     */
    public ArrayList<Player> getPlayers()
    {
        return null; // TODO Fix This!
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
        return null; // TODO Fix This!
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
        return null; // TODO Fix This!
    }


    /**
     */
    public static void main(String args[])
    {

    }
}
