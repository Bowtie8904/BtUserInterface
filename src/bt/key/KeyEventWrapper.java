package bt.key;

import java.awt.Button;
import java.awt.Component;
import java.awt.event.KeyEvent;

import lc.kra.system.keyboard.event.GlobalKeyEvent;

/**
 * @author &#8904
 *
 */
public class KeyEventWrapper extends KeyEvent
{
    private boolean isCtrlDown;
    private boolean isShiftDown;
    private boolean isAltDown;
    private int keyCode;
    private char keyChar;

    private KeyEventWrapper(
            Component source,
            int id,
            long when,
            int modifiers,
            int keyCode,
            char keyChar,
            int keyLocation)
    {
        super(source, id, when, modifiers, keyCode, keyChar, keyLocation);
    }

    public KeyEventWrapper(KeyEvent e)
    {
        super(new Button("DUMMY FILLER COMPONENT"), -1, -1, 0, -1, '0');
        this.isCtrlDown = e.isControlDown();
        this.isShiftDown = e.isShiftDown();
        this.isAltDown = e.isAltDown();
        this.keyCode = e.getKeyCode();
        this.keyChar = e.getKeyChar();
    }

    public KeyEventWrapper(GlobalKeyEvent e)
    {
        super(new Button("DUMMY FILLER COMPONENT"), -1, -1, 0, -1, '0');
        this.isCtrlDown = e.isControlPressed();
        this.isShiftDown = e.isShiftPressed();
        this.isAltDown = e.isMenuPressed();
        this.keyCode = e.getVirtualKeyCode();
        this.keyChar = e.getKeyChar();
    }

    @Override
    public boolean isControlDown()
    {
        return this.isCtrlDown;
    }

    @Override
    public boolean isShiftDown()
    {
        return this.isShiftDown;
    }

    @Override
    public boolean isAltDown()
    {
        return this.isAltDown;
    }

    @Override
    public int getKeyCode()
    {
        return this.keyCode;
    }

    @Override
    public char getKeyChar()
    {
        return this.keyChar;
    }
}