package goldpicker;
/**
 * Class ScoreKeeper
 * 
 * @author (Eddie Tindame - et222@kent.ac.uk) 
 * @version (09/04/2015)
 */
public class ScoreKeeper
{
    private double timePassed;
    private int scoreValue,
                combo,
                highCombo,
                highScore;
    /**
     * Constructor for objects of class ScoreKeeper.
     */
    public ScoreKeeper() {
        timePassed = 0;
        scoreValue = 0;
        combo      = 0;
        highCombo  = 0;
        highScore  = 0;
    }
    /**
     * Calculates the score based on the selected mode.
     * @param MODE  pass in game mode.
     * @return      
     */
    public int getScore( int MODE ) {
        if ( MODE == 0 ) {
            //CLASSIC
            if ( getRoundedTimePassed( timePassed ) == 0 )
                scoreValue = scoreValue + combo * 1000;
            else
                scoreValue = scoreValue + combo * getRoundedTimePassed( timePassed ) * 1000;
        }
        else if ( MODE == 1 ) {
            //TIMEATTACK
            if ( getRoundedTimePassed( timePassed ) == 0 )
                scoreValue = scoreValue + combo * 1000;
            else
                scoreValue = scoreValue + ( combo / getRoundedTimePassed( timePassed ) ) * 1000;
        }
        return scoreValue;
    }
    /**
     * Returns the time passed to the nearest second.
     */
    int getRoundedTimePassed( double TIMEPASSED ) {
    	return ((int)Math.floor( TIMEPASSED / 100 ));
    }
    /**
     * Returns string of score formatted with tailing 0's.
     * @param  MODE    pass in game mode.
     * @return 
     */
    public String getScoreDisplay( int MODE ) {
        if( scoreValue < 10 )
            return "00000000000" + getScore(MODE);
        else if( scoreValue < 100 && scoreValue > 9 )
            return "0000000000" + getScore(MODE);
        else if( scoreValue < 1000 && scoreValue > 99 )
            return "000000000" + getScore(MODE);
        else if( scoreValue < 10000 && scoreValue > 999 )
            return "00000000" + getScore(MODE);
        else if( scoreValue < 100000 && scoreValue > 9999 )
            return "0000000" + getScore(MODE);
        else if( scoreValue < 1000000 && scoreValue > 99999 )
            return "000000" + getScore(MODE);
        else if( scoreValue < 10000000 && scoreValue > 999999 )
            return "00000" + getScore(MODE);
        else if( scoreValue < 100000000 && scoreValue > 9999999 )
            return "0000" + getScore(MODE);
        else if( scoreValue < 1000000000 && scoreValue > 99999999 )
            return "000" + getScore(MODE);
        else if( scoreValue < (long)(1000000000*10) && scoreValue > 999999999 )
            return "00" + getScore(MODE);
        else if( scoreValue < (long)(1000000000*100) && scoreValue > 999999999.9*10 )
            return "0" + getScore(MODE);
        else if( scoreValue == 0 )
            return "000000000000";
        else
            return "" + getScore(MODE);
    }
    /**
     * Returns string of combo formatted with tailing 0's.
     * @param  COMBO    pass in combo.
     * @return 
     */
    public String getComboDisplay( int COMBO ) {
        if ( COMBO < 10 )
            return "00" + COMBO;
        else if ( COMBO < 100 && COMBO > 9 )
            return "0" + COMBO;
        else
            return "" + COMBO;
    }
    /**
     * Accessor for timePassed.
     * @return
     */
    public double getTimePassed() {
        return timePassed;
    }
    /**
     * Mutator for timePassed.
     */
    public void incrementTimePassed() {
        timePassed++;
    }
    /**
     * Accessor for scoreValue.
     * @return
     */
    public int getScoreValue() {
        return scoreValue;
    }
    /**
     * Accessor for combo.
     * @return
     */
    public int getCombo() {
        return combo;
    }
    /**
     * Mutator for combo.
     * @param  COMBO    pass in new combo.
     */
    public void setCombo( int COMBO ) {
        combo = COMBO;
    }
    /**
     * Mutator for combo.
     */
    public void incrementCombo() {
        combo++;
    }
    /**
     * Accessor for highCombo.
     * @return
     */
    public int getHighCombo() {
        return highCombo;
    }
    /**
     * Mutator for highCombo and highScore.
     */
    public void resetHighest() {
        highCombo = 0;
        highScore = 0;
    }
    /**
     * Accessor for highScore.
     * @return
     */
    public int getHighScore() {
        return highScore;
    }
    /**
     * Checks whether the current score and combo is
     * larger than the current highest.
     */
    public void checkHighest() {
        if ( combo > highCombo )
            highCombo = combo;
        if ( scoreValue > highScore )
            highScore = scoreValue;
    }
    /**
     * Mutator for timePassed, scoreValue and combo.
     */
    public void reset() {
        timePassed = 0;
        scoreValue = 0;
        combo      = 0;
    }
}
