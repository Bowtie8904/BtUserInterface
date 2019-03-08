package bt.gui.invis;

import java.awt.Color;

import javax.swing.JFrame;

/**
 * A simple JFrame which is undecorated and transparent.
 * 
 * @author &#8904
 */
public class InvisibleFrame extends JFrame
{
    /**
     * Creates a new instance.
     */
    public InvisibleFrame()
    {
        setUndecorated(true);
        setBackground(new Color(0, true));
    }

    /**
     * Stretches the frame to the size of the screen.
     */
    public void fullScreen()
    {
        setBounds(getGraphicsConfiguration().getBounds());
    }
}