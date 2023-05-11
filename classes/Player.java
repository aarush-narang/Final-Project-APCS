package classes;

public class Player
{

    private String username;
    private int    points;

    /**
     * Constructs a new player with the given username and points
     * 
     * @param u
     * @param p
     */
    public Player(String u, int p)
    {
        username = u;
        points = p;
    }


    public Player(String u)
    {
        username = u;
        points = 0;
    }


    /**
     * Changes and updates the player's points by p. Player's points will
     * increase by p if p is positive or decrease by p if p is negative
     * 
     * @param p
     *            - amount of points to change the player's current points
     */
    public void changePoints(int p)
    {
        points = points + p;
    }


    /**
     * Returns the number of points the player has
     * 
     * @return the number of points the player has
     */
    public int getPoints()
    {
        return points;
    }


    /**
     * Returns the players username
     * 
     * @return the username of the player
     */
    public String getUsername()
    {
        return username;
    }


    /**
     * compareTo method for the Player class that compares the points of the
     * players. This method is used by the TreeSet to sort the players by
     * points.
     * 
     * @param other
     *            the other player to compare to
     * @return 0 if the points are equal, 1 if the points of this player are, -1
     *         if the points of the other player are greater
     */
    public int compareTo(Player other)
    {
        if (points == other.points)
        {
            return 0;
        }
        else if (points > other.points)
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
}