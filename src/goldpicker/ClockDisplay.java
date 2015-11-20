package goldpicker;
/**
 * The ClockDisplay class implements a digital clock display for a
 * timer-style clock. The clock shows minutes, seconds and milliseconds.
 * 
 * The clock display receives "ticks" (via the timeTick method)
 * every 10 milliseconds
 * 
 * @author Michael Kolling and David J. Barnes
 * !! UPDATED by Eddie Tindame - et222@kent.ac.uk !!
 * 
 * @version (09/04/2015)
 */
public final class ClockDisplay
{
    protected NumberDisplay millis;
    protected NumberDisplay minutes;
    protected NumberDisplay seconds;
    protected String displayString;
    /**
     * Constructor for ClockDisplay objects. This constructor 
     * creates a new clock set at 00:00:00.
     */
    public ClockDisplay() {
        minutes = new NumberDisplay(60);
        seconds = new NumberDisplay(60);
        millis = new NumberDisplay(100);
        updateDisplay();
    }
    /**
     * Constructor for ClockDisplay objects. This constructor
     * creates a new clock set at the time specified by the 
     * parameters.
     * @param minute    minutes.
     * @param second    seconds.
     * @param milli     10 milliseconds.
     */
    public ClockDisplay( int minute, int second, int milli ) {
        minutes = new NumberDisplay(60);
        seconds = new NumberDisplay(60);
        millis = new NumberDisplay(100);
        setTime( minute, second, milli );
    }
    /**
     * Increments milliseconds by 10. If it rolls
     * over (becomes 0) it increments seconds by 1.
     */
    public void timeTickMilli() {
        millis.increment();
        if(millis.getValue() == 0) 
            seconds.increment();
        updateDisplay();
    }
    /**
     * Decrements milliseconds by 10. If it rolls over
     * (becomes 0) it decrements seconds by 1.
     */
    public void reverseTickMilli() {
        if(millis.getValue() == 0) {   
           seconds.decrement();
           millis.setValue(99);
        } else {
            millis.decrement();
        }
        updateDisplay();
    }
    /**
     * Increments minutes by 1.
     */
    public void timeTickMinute() {
        minutes.increment();
        updateDisplay();
    }
    /**
     * Increments seconds by 1. If it rolls over
     * (becomes 0) it increments minutes by 1.
     */
    public void timeTickSecond() {
        seconds.increment();
        if(seconds.getValue() == 0) {   
            timeTickMinute();
        }
        updateDisplay();
    }
    /**
     * Decrements seconds by 1. If it rolls over
     * (becomes 0) it decrements minutes by 1.
     */
    public void reverseTickSecond() {
        seconds.decrement();
        if(seconds.getValue() == 0) {   
            minutes.decrement();
        }
        updateDisplay();
    }
    /**
     * Set the time of the display to the specified
     * minute, second and 10-milliseconds.
     * @param minute    minutes.
     * @param second    seconds.
     * @param milli     10 milliseconds.
     */
    public void setTime( int minute, int second, int milli ) {
        minutes.setValue(minute);
        seconds.setValue(second);
        millis.setValue(milli);
        updateDisplay();
    }
    /**
     * Return the current time of this display in the format MM:SS:mm.
     * @return 
     */
    public String getTime() {
        return displayString;
    }
    /**
     * Update the internal string that represents the display.
     */
    public void updateDisplay() {
        displayString = minutes.getDisplayValue() + ":" + 
                        seconds.getDisplayValue() + ":" +
                        millis.getDisplayValue();
    }
}

