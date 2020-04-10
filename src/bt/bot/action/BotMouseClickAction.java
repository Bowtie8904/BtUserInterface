package bt.bot.action;

import java.awt.event.InputEvent;

import bt.bot.BotActionExecutor;
import bt.utils.log.Logger;

/**
 * @author &#8904
 *
 */
public class BotMouseClickAction extends BotAction
{
    public static final int RELEASE = 1;
    public static final int PRESS = 2;

    public static final int LEFT = InputEvent.BUTTON1_DOWN_MASK;
    public static final int MIDDLE = InputEvent.BUTTON2_DOWN_MASK;
    public static final int RIGHT = InputEvent.BUTTON3_DOWN_MASK;

    private int button;
    private int pressRelease;

    public BotMouseClickAction(int button, int pressRelease)
    {
        this.button = button;
        this.pressRelease = pressRelease;
    }

    /**
     * @see bt.bot.action.BotAction#execute(bt.bot.BotActionExecutor)
     */
    @Override
    public void execute(BotActionExecutor executor)
    {
        if (this.pressRelease == PRESS)
        {
            executor.getRobot().mousePress(this.button);
            Logger.global().print("Pressed [" + buttonToString(this.button) + "]");
        }
        else if (this.pressRelease == RELEASE)
        {
            executor.getRobot().mouseRelease(this.button);
            Logger.global().print("Released [" + buttonToString(this.button) + "]");
        }
    }

    private String buttonToString(int button)
    {
        String literal = "";

        switch (button)
        {
            case LEFT:
                literal = "Mouse Left";
                break;
            case MIDDLE:
                literal = "Mouse Middle";
                break;
            case RIGHT:
                literal = "Mouse Right";
                break;
        }

        return literal;
    }

    @Override
    public String toString()
    {
        return this.getClass().getName() + " [button=" + (this.button == LEFT ? "left" : this.button == RIGHT ? "right" : "middle") +
               ", pressRelease=" + (this.pressRelease == RELEASE ? "release" : "press") + "]";
    }
}