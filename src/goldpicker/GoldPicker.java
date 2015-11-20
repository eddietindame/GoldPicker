package goldpicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Class GoldPicker
 * 
 * @author (Eddie Tindame - et222@kent.ac.uk) 
 * @version (09/04/2015)
 * 
 * Keep applet window 350h x 700w
 */
public class GoldPicker extends JApplet implements Runnable
{
    private static final long serialVersionUID = -3997299317777943277L;

    private final Dimension SIZE     = new Dimension( 700,350 );
    private final Dimension CANVSIZE = new Dimension( 200,200 );
        
    private Thread thread;
    
    private boolean running = true,
                    canvasActive = false;
    
    private int mode = 0,
                difficulty = 1,
                goldHolder,
                answerCorrect = 0;
    
    private GRect canvas1,
                  canvas2,
                  canvas3;
                        
    private JPanel p1, p2, p3,
                   canvasPanel,
                   diffPanel;
                   
    private JLabel clockLabel,
                   scoreLabel,
                   commentLabel,
                   difficultyLabel,
                   titleLabel,
                   comboLabel;
    
    private JButton b1, b2;
    private JRadioButton r1, r2,
                         d1, d2, d3;
    private ButtonGroup modeRadioGroup,
                        diffRadioGroup;
    
    private ClockDisplay cd;
    private ScoreKeeper score;
    private CommentDisplay comment;
    /**
     * Called by the browser or applet viewer to inform this JApplet that it
     * has been loaded into the system. It is always called before the first 
     * time that the start method is called.
     */
    @Override
    public void init() {
        resize( SIZE );
        
        cd      = new ClockDisplay();
        score   = new ScoreKeeper();
        comment = new CommentDisplay();
        
        Container c = getContentPane();
        c.setLayout( new FlowLayout() );
        
        p1 = new JPanel();
        p1.setToolTipText( "Button Menu" );
        p2 = new JPanel();
        p1.setToolTipText( "Timer Panel" );
        p3 = new JPanel();
        canvasPanel = new JPanel();
        diffPanel = new JPanel();
        
        titleLabel   = new JLabel( "Gold Picker" );
        clockLabel   = new JLabel( "" + cd.getTime() );
        scoreLabel   = new JLabel( "Score: " + score.getScoreDisplay( mode ) );
        comboLabel   = new JLabel( "Combo: " + score.getComboDisplay( score.getCombo() ) );
        commentLabel = new JLabel( "" + comment.getCommentString() );
        difficultyLabel = new JLabel( "Difficulty (hover) : " );
        
        b1 = new JButton( "stop"  );
        b2 = new JButton( "start" );
        
        r1 = new JRadioButton( "Classic"    , true  );
        r2 = new JRadioButton( "Time Attack", false );
        d1 = new JRadioButton( "Easy"       , true  );
        d2 = new JRadioButton( "Medium"     , false );
        d3 = new JRadioButton( "Hard"       , false );
        
        r1.setToolTipText( "" );
        r2.setToolTipText( "" );
        d1.setToolTipText( " - CLASSIC -\n" +
                           "Time Limit: 20 (Easy), 15 (Medium), 10 (Hard)\n" +
                           "- TIME ATTACK -\n" +
                           "Time Allowed: 5 (Easy), 3 (Medium), 1 (Hard)" );
        d2.setToolTipText( " - CLASSIC -\n" +
                           "Time Limit: 20 (Easy), 15 (Medium), 10 (Hard)\n" +
                           "- TIME ATTACK -\n" +
                           "Time Allowed: 5 (Easy), 3 (Medium), 1 (Hard)" );
        d3.setToolTipText( " - CLASSIC -\n" +
                           "Time Limit: 20 (Easy), 15 (Medium), 10 (Hard)\n" +
                           "- TIME ATTACK -\n" +
                           "Time Allowed: 5 (Easy), 3 (Medium), 1 (Hard)" );
        diffPanel.setToolTipText( " - CLASSIC -\n" +
                                  "Time Limit: 20 (Easy), 15 (Medium), 10 (Hard)\n" +
                                  "- TIME ATTACK -\n" +
                                  "Time Allowed: 5 (Easy), 3 (Medium), 1 (Hard)" );
        titleLabel.setToolTipText( "Guess the golden rectangle!" );
        clockLabel.setToolTipText( "Timer" );
        scoreLabel.setToolTipText( "Your Score" );
        comboLabel.setToolTipText( "Increase your combo for a higher score!" );
        
        modeRadioGroup = new ButtonGroup();
        diffRadioGroup = new ButtonGroup();
              
        setGolden();
      
        // <LAYOUT>
        c.add(titleLabel);
        c.add(p1);
        c.add(p2);
        c.add(canvasPanel);
        c.add(diffPanel);
        c.add(p3);
        
        p1.add(b2);
        p1.add(clockLabel);
        p1.add(b1);
        canvasPanel.add(canvas1);
        canvasPanel.add(canvas2);
        canvasPanel.add(canvas3);
        c.add(p3);
        p2.add( commentLabel );
        p3.add(comboLabel);
        p3.add(r1);
        p3.add(r2);
        p3.add( scoreLabel );
        diffPanel.add( difficultyLabel );
        diffPanel.add( d1 );
        diffPanel.add( d2 );
        diffPanel.add( d3 );
        // </LAYOUT>
        
        modeRadioGroup.add(r1);
        modeRadioGroup.add(r2);
        diffRadioGroup.add(d1);
        diffRadioGroup.add(d2);
        diffRadioGroup.add(d3);
        
        JButtonHandler handler = new JButtonHandler();
        b1.addActionListener( handler );
        b2.addActionListener( handler );
        
        MouseHandler mouse = new MouseHandler();
        canvas1.addMouseListener( mouse );
        canvas2.addMouseListener( mouse );
        canvas3.addMouseListener( mouse );

        RadioButtonHandler rHandler = new RadioButtonHandler();
        r1.addItemListener( rHandler );
        r2.addItemListener( rHandler );
        d1.addItemListener( rHandler );
        d2.addItemListener( rHandler );
        d3.addItemListener( rHandler );
        
        printDebug( "end of init() method" );
    }
    /**
     * Called by the browser or applet viewer to inform this JApplet that it 
     * should start its execution. It is called after the init method and 
     * each time the JApplet is revisited in a Web page. 
     */
    @Override
    public void start() {  
        if ( running )
            return;
        printDebug( "start of start() method" );
        running = true;
        toggleDrawing( true );
        updateLabels();
        printDebug( "start() method before null check" );
        if ( thread == null ) { 
            thread = new Thread(this); 
            thread.start();
        } 
        printDebug( "end of start() method" );
    }
    /** 
     * Called by the browser or applet viewer to inform this JApplet that
     * it should stop its execution. It is called when the Web page that
     * contains this JApplet has been replaced by another page, and also
     * just before the JApplet is to be destroyed. 
     */
    @Override
    public void stop() {
        printDebug( "start of stop() method" );
        running = false;
        answerCorrect = 0;
        if ( mode == 0 )
            cd.setTime( 0,0,0 );
        else if ( mode == 1 )
            if ( difficulty == 1 ) //EASY
                cd.setTime( 0,5,0 );
            else if ( difficulty == 2 ) //MEDIUM
                cd.setTime( 0,3,0 );
            else if ( difficulty == 3 ) //HARD
                cd.setTime( 0,1,0 );
        score.reset();
        updateLabels();
        resetGolden();
        toggleDrawing( false );
        printDebug( "stop() method before null check" );
        if ( thread != null ) { 
            thread.interrupt();
            thread = null; 
        }
        printDebug( "end of stop() method" );
    }
    /** 
     * Run method is called when thread.start() is called. Contains a loop that
     * keeps the clock ticking and constantly checks for end of time limit.
     */
    @Override
    public void run() {
        printDebug( "start of run() method" );
        while(running) {
            try {
                if ( mode == 0 ) {
                    //CLASSIC
                    clockLabel.setText( cd.getTime() );
                    if ( difficulty == 1 )
                        isTimeOver( 0,20,0 );
                    else if ( difficulty == 2 )
                        isTimeOver( 0,15,0 );
                    else if ( difficulty == 3 )
                        isTimeOver( 0,10,0 );
                    Thread.sleep( 10 );
                    cd.timeTickMilli();
                    score.incrementTimePassed();
                } else if ( mode == 1 ) {
                    //TIMEATTACK
                    clockLabel.setText( cd.getTime() );
                    isTimeOver( 0,0,0 );
                    Thread.sleep( 10 );
                    cd.reverseTickMilli();
                    score.incrementTimePassed();
                }
            } catch( InterruptedException e ) {}
        }
    }
    /**
     * Randomly sets one of three canvases to contain a golden rectangle.
     * To be called once in the init method.
     */
    void setGolden() {
        goldHolder = (int)Math.floor( Math.random() * (3 - 1 + 1) + 1 );
        if ( goldHolder == 1 ) {
            canvas1 = new GRect(true);
            canvas1.setSize( CANVSIZE );
            canvas1.setBackground( Color.white );
            canvas2 = new GRect(false);
            canvas2.setSize( CANVSIZE );
            canvas2.setBackground( Color.white );
            canvas3 = new GRect(false);
            canvas3.setSize( CANVSIZE );
            canvas3.setBackground( Color.white );
        } else if ( goldHolder == 2 ) {
            canvas1 = new GRect(false);
            canvas1.setSize( CANVSIZE );
            canvas1.setBackground( Color.white );
            canvas2 = new GRect(true);
            canvas2.setSize( CANVSIZE );
            canvas2.setBackground( Color.white );
            canvas3 = new GRect(false);
            canvas3.setSize( CANVSIZE );
            canvas3.setBackground( Color.white );
        } else if ( goldHolder == 3 ) {
            canvas1 = new GRect(false);
            canvas1.setSize( CANVSIZE );
            canvas1.setBackground( Color.white );
            canvas2 = new GRect(false);
            canvas2.setSize( CANVSIZE );
            canvas2.setBackground( Color.white );
            canvas3 = new GRect(true);
            canvas3.setSize( CANVSIZE );
            canvas3.setBackground( Color.white );
        }
    }
    /**
     * Randomly resets one of three canvases to contain a golden rectangle. 
     */
    void resetGolden() {
        goldHolder = (int)Math.floor( Math.random() * (3 - 1 + 1) + 1 );
        if ( goldHolder == 1 ) {
            canvas1.reset(true);
            canvas2.reset(false);
            canvas3.reset(false);
        } else if ( goldHolder == 2 ) {
            canvas1.reset(false);
            canvas2.reset(true);
            canvas3.reset(false);
        } else if ( goldHolder == 3 ) {
            canvas1.reset(false);
            canvas2.reset(false);
            canvas3.reset(true);
        }
    }
    /**
     * Sets visibility of the canvases on or off. Essentially tells canvas
     * whether or not to paint a rectangle.
     */
    void toggleDrawing( boolean visibility ) {
        canvasActive  = visibility;
        canvas1.setPaint( visibility );
        canvas2.setPaint( visibility );
        canvas3.setPaint( visibility );
        canvas1.repaint();
        canvas2.repaint();
        canvas3.repaint();
    }
    /**
     * Updates label strings with latest information.
     */
    void updateLabels() {
        clockLabel.setText( cd.getTime() );
        comboLabel.setText( "Combo: " + score.getComboDisplay( score.getCombo() ) );
        scoreLabel.setText( "Score: " + score.getScoreDisplay( mode ) );
        commentLabel.setText( "" + comment.getCommentDisplay( score.getScoreValue(),
                                                              score.getCombo(),
                                                              answerCorrect,
                                                              running ) );
        printDebug( "end of updateLabels() method" );
    }
    /**
     * Changes game mode. Stops and resets the game if mode is changed.
     */
    void changeMode( String whichMode ) {
        stop();
        score.resetHighest();
        if ( null != whichMode ) switch (whichMode) {
            case "Classic":
                mode = 0;
                cd.setTime( 0,0,0 );
                updateLabels();
                break;
            case "TimeAttack":
                if ( difficulty == 1 ) //EASY
                    cd.setTime( 0,5,0 );
                else if ( difficulty == 2 ) //MEDIUM
                    cd.setTime( 0,3,0 );
                else if ( difficulty == 3 ) //HARD
                    cd.setTime( 0,1,0 );
                mode = 1;
                updateLabels();
                break;
        }
        printDebug( "end of changeMode() method" );
    }
    /**
     * Changes game difficulty. Stops and resets the game if difficulty is changed.
     */
    void changeDifficulty( int d ) {
        stop();
        score.resetHighest();
        if ( mode == 0 ) { //CLASSIC
            if ( d == 1 ) //EASY
                difficulty = 1;
            else if ( d == 2 ) //MEDIUM
                difficulty = 2;
            else if ( d == 3 ) //HARD
                difficulty = 3;
            cd.setTime( 0,0,0 );
            updateLabels();
        } else if ( mode == 1 ) { //TIMEATTACK
            if ( d == 1 ) {
                //EASY
                difficulty = 1;
                cd.setTime( 0,5,0 );
            } else if ( d == 2 ) {
                //MEDIUM
                difficulty = 2;
                cd.setTime( 0,3,0 );
            } else if ( d == 3 ) {
                //HARD
                difficulty = 3;
                cd.setTime( 0,1,0 );
            }
            updateLabels();
        }
        printDebug( "end of changeMode() method" );
    }
    /**
     * Checks if player has ran out of time. Time limit set by parameters.
     */
    void isTimeOver( int minute, int second, int milli ) {
        if ( cd.millis.getValue()  == milli  &&
             cd.seconds.getValue() == second &&
             cd.minutes.getValue() == minute ) {
                commentLabel.setText( "Time Over!" );
                gameOver();
        }     
    }
    /**
     * Shows alert with score information and stops game. To be called when
     * time is over or when wrong rectangle is clicked in time attack mode.
     */
    void gameOver() {
        printDebug( "start of gameOver() method" );
        JOptionPane.showMessageDialog( null, "         Game Over\nHighest Combo:   "
                                       + score.getComboDisplay( score.getHighCombo() )
                                       + "\nScore: " + score.getScoreDisplay(mode)
                                       + "\nHi-Score: " + score.getHighScore() );
        stop();
        printDebug( "end of gameOver() method" );
    }
    /**
     * Prints a list of major variables. This is called at critical points in
     * the program to help with debugging.
     */
    void printDebug( String debugComment ) {
        System.out.println( "*********************************" );
        System.out.println( "" );
        System.out.println( debugComment );
    	System.out.println( "" );
    	System.out.println( "Thread: " + thread );
    	System.out.println( "Running: " + running );
    	System.out.println( "Canvas Active: " + canvasActive );
    	if ( mode == 0 )	
            System.out.println( "Mode: Classic" );
    	else if ( mode == 1 )
            System.out.println( "Mode: Time Attack");
    	System.out.println( "Gold Holder: " + goldHolder );
    	System.out.println( "Answer Correct: " + answerCorrect );
    	System.out.println( "Time: " + cd.getTime() );
    	System.out.println( "Score: " + score.getScoreValue() );
    	System.out.println( "Combo: " + score.getCombo() );
    	System.out.println( "Highest Combo: " + score.getHighCombo() );
    	System.out.println( "Highest Score: " + score.getHighScore() );
    	System.out.println( "Time Passed: " + score.getTimePassed() );
    	System.out.println( "Rounded Time Passed: " + score.getRoundedTimePassed( score.getTimePassed() ) );
    	System.out.println( "Comment: " + comment.getCommentDisplay( score.getScoreValue(),
                                                                     score.getCombo(),
                                                                     answerCorrect,
                                                                     running ) );
    	System.out.println( "" );
    	System.out.println( "*********************************" );
    }
    /**
     * Class JButtonHandler. Handles the JButtons.
     */
    class JButtonHandler implements ActionListener {
        /**
         * Adds functionality to start and stop buttons.
         */
        @Override
        public void actionPerformed( ActionEvent e ) {
            if ( e.getSource() == b1 ) {
                stop();
            }
            if ( e.getSource() == b2 ) {
                running = false;
                start();
            }
        }
    }
    /**
     * Class JButtonHandler. Handles the JRadioButtons.
     */
    class RadioButtonHandler implements ItemListener {
        /**
         * Adds functionality to mode and difficulty buttons.
         */
        @Override
        public void itemStateChanged( ItemEvent e )
        {
            if ( e.getSource() == r1 ) //CLASSIC
                changeMode( "Classic" );
            else if ( e.getSource() == r2 ) //TIMEATTACK
                changeMode( "TimeAttack" );
            if ( e.getSource() == d1 ) //EASY
                changeDifficulty(1);
            else if ( e.getSource() == d2 ) //MEDIUM
                changeDifficulty(2);
            else if ( e.getSource() == d3 ) //HARD
                changeDifficulty(3);
        }
    }
    /**
     * Class JButtonHandler. Handles the mouse events.
     */
    class MouseHandler implements MouseListener {
        /**
         * Adds mouse click functionality to canvases.
         */
        @Override
        public void mouseClicked( MouseEvent e ) {
            if ( e.getSource() == canvas1 ) {
                System.out.println( "Canvas1 Clicked" ); // Debug
                goldCheck(1);
            }
            if ( e.getSource() == canvas2 ) {
                System.out.println( "Canvas2 Clicked" ); // Debug
                goldCheck(2);
            }
            if ( e.getSource() == canvas3 ) {
                System.out.println( "Canvas3 Clicked" ); // Debug
                goldCheck(3);
            }
            System.out.println( "" + score.getScore(mode) );
        }                     
        @Override
        public void mousePressed( MouseEvent e ) {}
        @Override
        public void mouseReleased( MouseEvent e ) {}
        /**
         * Turns background blue when mouse enters.
         */
        @Override
        public void mouseEntered( MouseEvent e ) {
            if ( e.getSource() == canvas1 ) {
                if ( canvas1.getPaint() == true )
                    canvas1.setBackground( Color.blue );
            }
            if ( e.getSource() == canvas2 ) {
                if ( canvas2.getPaint() == true )
                    canvas2.setBackground( Color.blue );
            }
            if ( e.getSource() == canvas3 ) {
                if ( canvas3.getPaint() == true )
                    canvas3.setBackground( Color.blue );
            }
        }
        /**
         * Turns background back to white when mouse exits.
         */
        @Override
        public void mouseExited( MouseEvent e ) {
            if ( e.getSource() == canvas1 ) {
                if ( canvas1.getPaint() == true )
                    canvas1.setBackground( Color.white );
            }
            if ( e.getSource() == canvas2 ) {
                if ( canvas2.getPaint() == true )
                    canvas2.setBackground( Color.white );
            }
            if ( e.getSource() == canvas3 ) {
                if ( canvas3.getPaint() == true )
                    canvas3.setBackground( Color.white );
            }
        }
        /**
         * Checks whether or not the user clicks the correct canvas.
         */
        void goldCheck( int i ) {
            if ( canvasActive == true ) {
                if ( goldHolder == i) { // CORRECT SELECTION
                    score.incrementCombo();
                    score.checkHighest();
                    resetGolden();
                    answerCorrect = 1;
                    if ( mode == 1 ) {
                        //TIMEATTACK
                        if ( difficulty == 1 ) //EASY
                            cd.setTime( 0,5,0 );
                        else if ( difficulty == 2 ) //MEDIUM
                            cd.setTime( 0,3,0 );
                        else if ( difficulty == 3 ) //HARD
                            cd.setTime( 0,1,0 );
                    }
                    updateLabels();
                } else if ( goldHolder != i ) { // WRONG SELECTION
                    answerCorrect = 2;
                    if ( mode == 0 ) {
                        //CLASSIC
                        score.setCombo(0);
                        resetGolden();
                        updateLabels();
                    } else if ( mode == 1 ) {
                        //TIMEATTACK
                        updateLabels();
                        if ( thread != null ) { 
                            running = false;
                            thread = null;
                        }
                        gameOver();
                    }
                }
            }
        }
    }
}
   

