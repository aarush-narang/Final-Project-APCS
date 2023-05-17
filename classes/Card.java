package classes;

public class Card
{
    private String  question;
    private String  answer;
    private int     points;
    private String  category;
    private Player  respondent;
    private boolean correct;
    private boolean isVisible;

    /**
     * Constructs a new card with the given point value, question, and answer.
     * The rest is initialized to false or null.
     * 
     * @param question
     *            - question
     * @param answer
     *            - answer
     * @param points
     *            - point value
     * @param category
     *            - category
     */
    public Card(String question, String answer, int points, String category)
    {
        this.points = points;
        this.question = question;
        this.answer = answer;
        this.category = category;

        correct = false;
        isVisible = false;
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
     * Retunrs the number of points this card is worth
     * 
     * @return number of points
     */

    public int getPoints()
    {
        return points;
    }


    /**
     * Returns the category of the card
     * 
     * @return the category of the card
     */
    public String getCategory()
    {
        return category;
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
     * Returns if the player answered the card correctly or not
     * 
     * @return true if the player answered the card correctly, false if the
     *         player did not answer the card correctly
     */
    public boolean getCorrect()
    {
        return correct;
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
        respondent = player;

        if (a.equals(answer))
        {
            player.changePoints(points);

            correct = true;
            return true;
        }

        correct = false;
        return false;
    }


    public String toString()
    {
        return question + ": " + answer + " ($" + points + ")";
    }

}
