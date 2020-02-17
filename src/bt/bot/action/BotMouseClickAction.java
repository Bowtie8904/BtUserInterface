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
    public static final int LEFT = InputEvent.BUTTON1_DOWN_MASK;
    public static final int MIDDLE = InputEvent.BUTTON2_DOWN_MASK;
    public static final int RIGHT = InputEvent.BUTTON3_DOWN_MASK;

    private int button;

    public BotMouseClickAction(int button)
    {
        this.button = button;
    }

    /**
     * @see bt.bot.action.BotAction#execute(bt.bot.BotActionExecutor)
     */
    @Override
    public void execute(BotActionExecutor executor)
    {
        executor.getRobot().mousePress(this.button);
        executor.getRobot().mouseRelease(this.button);
        Logger.global().print("Clicked [" + buttonToString(this.button) + "]");
    }

    private String buttonToString(int button)
    {
        String literal = "";

        switch (button)
        {
            case LEFT:
                literal = "Left";
                break;
            case MIDDLE:
                literal = "Middle";
                break;
            case RIGHT:
                literal = "Right";
                break;
        }

        return literal;
    }
}