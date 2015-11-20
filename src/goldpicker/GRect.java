package goldpicker;

import java.awt.*;
/**
 * Class GRect - Golden rectangles.
 * 
 * @author (Eddie Tindame - et222@kent.ac.uk) 
 * @version (09/04/2015)
 */
public class GRect extends Canvas {
    private static final long serialVersionUID = -654771373726916989L;
    private int width,
                height,
                posx,
                posy;
    private boolean isGolden,
                    paint = false;
    private final int MIN = 1,
                      MAX = 9;
    /**
     * Constructor for objects of class GRect.
     * @param GOLD
     */
    public GRect( boolean GOLD ) {
        isGolden = GOLD;
        if( isGolden == true )
            makeGoldenRect();
        else if( isGolden == false )
            makeRandomRect();
    }
    /**
     * Paint method for applet. This will paint a rectangle (using the width
     * and height fields) if the applet is in paint mode or a blank space if not.
     * @param  g   the Graphics object for this applet.
     */
    @Override
    public void paint( Graphics g ) {
       if( paint == true ) {
            if( isGolden == false ) 
                g.setColor( Color.red );
            else if( isGolden == true )  // Change colour of golden
                g.setColor( Color.red ); // rectangle for easy dev/debug
            // Paints rectangle in the centre of the canvas
            posx = this.getWidth()/2 - width/2;
            posy = this.getHeight()/2 - height/2;
            g.fillRect( posx, posy, width, height );
            g.setColor( Color.black ); // Draw outline
            g.drawRect( posx, posy, width, height );
       } else { 
            g.setColor( Color.white );
            g.fillRect( 0, 0, 200, 200 );
       }
    }
    /**
     * Mutator for paint.
     * @param PAINT
     */
    public void setPaint( boolean PAINT ) {
        paint = PAINT;
    }
    /**
     * Accessor for paint.
     * @return 
     */
    public boolean getPaint() {
        return paint;
    }
    /**
     * Sets the width and height fields to that of a golden rectangle. The
     * height is random (between MIN and MAX) but the width is golden.
     */
    final void makeGoldenRect() {
        height = (int)Math.floor( Math.random() * ( MAX - MIN + 1 ) + 1 ) * 10;
        width  = (int)Math.round( height * ( (Math.sqrt(5)+1)/2 ) );
        System.out.println( "GRect width: " + width +  " height: " + height ); // Debug
    }
    /**
     * Sets the width and height fields to that of a random rectangle. Both 
     * width and height are random (between MIN and MAX). The long side will 
     * always be the base.
     */
    final void makeRandomRect() {
        height = (int)Math.floor( Math.random() * ( MAX - MIN + 1 ) + 1 ) * 10;
        width  = (int)Math.floor( Math.random() * ( MAX - MIN + 1 ) + 1 ) * 10;
        if( height > width ) { // Keeps long side as base
            int i = height;
            height = width;
            width = i;
        }
        System.out.println( "RRect width: " + width + " height: " + height ); // Debug
    }
    /**
     * Resets the width and height fields to new values (golden or random)
     * and repaints the canvases.
     */
    void reset( boolean GOLD ) {
        isGolden = GOLD;
        if( isGolden == true )
            makeGoldenRect();
        else if( isGolden == false )
            makeRandomRect();
        repaint();
    }
}
