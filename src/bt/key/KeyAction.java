package bt.key;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

/**
 * @author &#8904
 *
 */
public class KeyAction
{
    public static final int UNDEFINED_KEY_ACTION_CODE = Integer.MIN_VALUE;
    public static final int NO_MODIFIER = 0;
    public static final int SHIFT_MODIFIER = 1;
    public static final int ALT_MODIFIER = 2;
    public static final int CTRL_MODIFIER = 3;

    public static String modifierToString(int modifier)
    {
        String sMod = "Invalid modifier";

        switch (modifier)
        {
        case NO_MODIFIER:
            sMod = "No modifier";
            break;
        case SHIFT_MODIFIER:
            sMod = "Shift modifier";
            break;
        case ALT_MODIFIER:
            sMod = "Alt modifier";
            break;
        case CTRL_MODIFIER:
            sMod = "Ctrl modifier";
            break;
        }

        return sMod;
    }

    private Component component;
    private Consumer<KeyEvent> keyPressed;
    private Consumer<KeyEvent> keyReleased;
    private int keyCode;
    private int modifier = NO_MODIFIER;

    public enum Action
    {
        KEY_PRESSED,
        KEY_RELEASED
    }

    public KeyAction(Consumer<KeyEvent> keyPressed)
    {
        this.keyCode = UNDEFINED_KEY_ACTION_CODE;
        this.keyPressed = keyPressed;
        this.keyReleased = (e) ->
        {
        };
    }

    public KeyAction(Consumer<KeyEvent> keyPressed, Consumer<KeyEvent> keyReleased)
    {
        this.keyCode = UNDEFINED_KEY_ACTION_CODE;
        this.keyPressed = keyPressed;
        this.keyReleased = keyReleased;
    }

    public KeyAction(int keyCode, Consumer<KeyEvent> keyPressed)
    {
        this.keyCode = keyCode;
        this.keyPressed = keyPressed;
        this.keyReleased = (e) ->
        {
        };
    }

    public KeyAction(int keyCode, Consumer<KeyEvent> keyPressed, Consumer<KeyEvent> keyReleased)
    {
        this.keyCode = keyCode;
        this.keyPressed = keyPressed;
        this.keyReleased = keyReleased;
    }

    public KeyAction(int keyCode, int modifier, Consumer<KeyEvent> keyPressed)
    {
        this.modifier = modifier;
        this.keyCode = keyCode;
        this.keyPressed = keyPressed;
        this.keyReleased = (e) ->
        {
        };
    }

    public KeyAction(int keyCode, int modifier, Consumer<KeyEvent> keyPressed, Consumer<KeyEvent> keyReleased)
    {
        this.modifier = modifier;
        this.keyCode = keyCode;
        this.keyPressed = keyPressed;
        this.keyReleased = keyReleased;
    }

    public KeyAction(Component component, Consumer<KeyEvent> keyPressed)
    {
        this.component = component;
        this.keyCode = UNDEFINED_KEY_ACTION_CODE;
        this.keyPressed = keyPressed;
        this.keyReleased = (e) ->
        {
        };
    }

    public KeyAction(Component component, Consumer<KeyEvent> keyPressed, Consumer<KeyEvent> keyReleased)
    {
        this.component = component;
        this.keyCode = UNDEFINED_KEY_ACTION_CODE;
        this.keyPressed = keyPressed;
        this.keyReleased = keyReleased;
    }

    public KeyAction(Component component, int keyCode, Consumer<KeyEvent> keyPressed)
    {
        this.component = component;
        this.keyCode = keyCode;
        this.keyPressed = keyPressed;
        this.keyReleased = (e) ->
        {
        };
    }

    public KeyAction(Component component, int keyCode, Consumer<KeyEvent> keyPressed, Consumer<KeyEvent> keyReleased)
    {
        this.component = component;
        this.keyCode = keyCode;
        this.keyPressed = keyPressed;
        this.keyReleased = keyReleased;
    }

    public KeyAction(Component component, int keyCode, int modifier, Consumer<KeyEvent> keyPressed)
    {
        this.component = component;
        this.modifier = modifier;
        this.keyCode = keyCode;
        this.keyPressed = keyPressed;
        this.keyReleased = (e) ->
        {
        };
    }

    public KeyAction(Component component, int keyCode, int modifier, Consumer<KeyEvent> keyPressed,
            Consumer<KeyEvent> keyReleased)
    {
        this.component = component;
        this.modifier = modifier;
        this.keyCode = keyCode;
        this.keyPressed = keyPressed;
        this.keyReleased = keyReleased;
    }

    public void execute(Action actionType, KeyEvent e)
    {
        if (this.component != null)
        {
            boolean execute = false;
            Component comp = e.getComponent();

            if (comp == null)
            {
                return;
            }
            else if (!comp.equals(this.component))
            {
                while ((comp = comp.getParent()) != null)
                {
                    if (comp.equals(this.component))
                    {
                        execute = true;
                        break;
                    }
                }
            }
            else
            {
                execute = true;
            }

            if (!execute)
            {
                return;
            }
        }

        if (this.modifier == NO_MODIFIER
                || this.modifier == SHIFT_MODIFIER && e.isShiftDown()
                || this.modifier == ALT_MODIFIER && e.isAltDown()
                || this.modifier == CTRL_MODIFIER && e.isControlDown())
        {
            switch (actionType)
            {
            case KEY_PRESSED:
                this.keyPressed.accept(e);
                break;
            case KEY_RELEASED:
                this.keyReleased.accept(e);
                break;
            }
        }
    }

    public void setModifier(int modifier)
    {
        this.modifier = modifier;
    }

    public int getModifier()
    {
        return this.modifier;
    }

    public void setKeyPressedAction(Consumer<KeyEvent> keyPressed)
    {
        this.keyPressed = keyPressed;
    }

    public void setKeyReleasedAction(Consumer<KeyEvent> keyReleased)
    {
        this.keyReleased = keyReleased;
    }

    public Consumer<KeyEvent> getKeyPressedAction()
    {
        return this.keyPressed;
    }

    public Consumer<KeyEvent> getKeyReleasedAction()
    {
        return this.keyReleased;
    }

    public int getKeyCode()
    {
        return this.keyCode;
    }

    public Component getComponent()
    {
        return this.component;
    }

    @Override
    public boolean equals(Object o)
    {
        if (super.equals(o))
        {
            return true;
        }
        else if (o instanceof KeyAction)
        {
            return this.keyCode == ((KeyAction)o).getKeyCode()
                    && this.modifier == ((KeyAction)o).getModifier()
                    && (this.component == null || this.component.equals(((KeyAction)o).getComponent()));
        }

        return false;
    }

    @Override
    public String toString()
    {
        return "KeyCode: " + this.getKeyCode() + "\n"
                + "KeyLiteral: " + KeyEvent.getKeyText(this.getKeyCode()) + "\n"
                + "Modifier: " + KeyAction.modifierToString(this.getModifier());
    }
}