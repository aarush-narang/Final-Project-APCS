package classes;

public class Card
{
    private int     points;
    private String  question;
    private String  answer;
    private Player  respondent;
    private boolean correct;
    private boolean isVisible;

    /**
     * Constructs a new card with the given point value, question, and answer.
     * The rest is initialized to false or null.
     * 
     * @param p
     *            - point value
     * @param q
     *            - question
     * @param a
     *            - answer
     */
    public Card(int p, String q, String a)
    {
        points = p;
        question = q;
        answer = a;
        correct = false;
        isVisible = false;
    }


    /**
     * Retunrs the number of points this card is worth
     * 
     * @return number of points
     */

    public int getPoints()
    {
        return points;
    }


    /**
     * Returns the question for the card
     * 
     * @return the question for the card
     */
    public String getQuestion()
    {
        return question;
    }


    /**
     * Returns the answer to the question of the card
     * 
     * @return the answer to the question of the card
     */
    public String getAnswer()
    {
        return answer;
    }


    /**
     * Returns the player who answered the card
     * 
     * @return the player who answered the card
     */
    public Player getRespondent()
    {
        return respondent;
    }


    /**
     * Returns if the card is visible or not
     * 
     * @return true if the card is visible, false if the card is not visible
     */
    public boolean getVisibility()
    {
        return isVisible;
    }


    /**
     * Sets the card's visibility to the visibility in the parameter
     * 
     * @param visible
     *            - the visibility to set the visibility of the card to
     */
    public void setVisibility(boolean visible)
    {
        isVisible = visible;
    }


    /**
     * Submits the player's answer to the card and determines if the submitted
     * answer matches with the card's answer
     * 
     * @param a
     *            - answer that the player is submtting
     * @param player
     *            - player that is submitting this answer
     * @return true if the submitted answer matches answer (the answer to the
     *         card), false if the submitted answer does not match answer(the
     *         answer to the card)
     */
    public boolean submitAnswer(String a, Player player)
    {
        if (a.equals(answer))
        {
            player.changePoints(points);
            correct = true;
            return true;
        }
        return false;
    }

}
