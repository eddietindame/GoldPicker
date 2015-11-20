package goldpicker;
/**
 * The NumberDisplay class represents a digital number display that can hold
 * values from zero to a given limit. The limit can be specified when creating
 * the display. The values range from zero (inclusive) to limit-1. If used,
 * for example, for the seconds on a digital clock, the limit would be 60, 
 * resulting in display values from 0 to 59. When incremented, the display 
 * automatically rolls over to zero when reaching the limit.
 * 
 * @author Michael Kolling and David J. Barnes
 * !! UPDATED by Eddie Tindame - et222@kent.ac.uk !!
 * 
 * @version (09/04/2015)
 */
public class NumberDisplay
{
    private final int limit;
    private int value;
    /**
     * Constructor for objects of class Display.
     * @param rollOverLimit    value limit for number display.
     */
    public NumberDisplay( int rollOverLimit ) {
        limit = rollOverLimit;
        value = 0;
    }
    /**
     * Accessor for current value.
     * @return
     */
    public int getValue() {
        return value;
    }
    /**
     * Return the display value (that is, the current value as a two-digit
     * String. If the value is less than ten, it will be padded with a leading
     * zero).
     * @return
     */
    public String getDisplayValue() {
        if(value < 10)
            return "0" + value;
        else
            return "" + value;
    }
    /**
     * Set the value of the display to the new specified value. If the new
     * value is less than zero or over the limit, do nothing.
     * @param replacementValue  new digit value.
     */
    public void setValue( int replacementValue ) {
        if((replacementValue >= 0) && (replacementValue < limit))
            value = replacementValue;
    }
    /**
     * Increment the display value by one, rolling over to zero if the
     * limit is reached.
     */
    public void increment() {
        value = (value + 1) % limit;
    }
    /**
     * Decrement the display value by one.
     */
    public void decrement() {
        value = (value - 1) % limit;
    }
}