package goldpicker;
/**
 * Class CommentDisplay
 * 
 * @author (Eddie Tindame - et222@kent.ac.uk) 
 * @version (09/04/2015)
 */
public class CommentDisplay
{    
    private String commentString;
    /**
     * Constructor for objects of class CommentDisplay.
     */
    public CommentDisplay() {
        commentString = "Click Start!";
    }
    /**
     * Accessor for commentString.
     * @return
     */
    public String getCommentString() {
        return commentString;
    }
    /**
     * Changes the comment depending on the passed parameters.
     * @param score    current score.
     * @param combo    current combo.
     * @param answer   0 = none, 1 = correct, 2 = incorrect.
     * @param running  is game running?.
     * @return
     */
    public String getCommentDisplay( int score, int combo, int answer, boolean running ) {
        if ( running == false ) {
            commentString = "Click Start!";
        } else if ( running == true ) {
            if ( answer == 0 )      // No Answer
                commentString = "Choose Golden!";
            else if ( answer == 1 ) // Answer Correct
                commentString = "Nice!";
            else if ( answer == 2 ) // Answer Incorrect - Combo broken
                commentString = "Unlucky...";
            if ( combo == 5 )
                commentString = "Nice Combo!";
            else if ( combo == 10 )
                commentString = "Doing Great!";
        }
        return commentString;
    }
}
