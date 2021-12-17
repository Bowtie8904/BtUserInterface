package bt.bot.action;

import bt.bot.BotActionExecutor;
import bt.bot.BotKey;
import bt.log.Log;

/**
 * @author &#8904
 *
 */
public class BotKeyAction extends BotAction
{
    public static final int RELEASE = 1;
    public static final int PRESS = 2;

    private BotKey key;
    private int pressRelease;

    public BotKeyAction(BotKey key, int pressRelease)
    {
        this.key = key;
        this.pressRelease = pressRelease;
    }

    @Override
    public void execute(BotActionExecutor executor)
    {
        if (this.key != null && executor.getRobot() != null)
        {
            if (this.pressRelease == PRESS)
            {
                executor.getRobot().keyPress(this.key.getCode());
                Log.info("Pressed [" + this.key.getLiteral() + "]");
            }
            else if (this.pressRelease == RELEASE)
            {
                executor.getRobot().keyRelease(this.key.getCode());
                Log.info("Released [" + this.key.getLiteral() + "]");
            }
        }
    }

    @Override
    public String toString()
    {
        return this.getClass().getName() + " [key=" + this.key.toString() + ", pressRelease=" + (this.pressRelease == RELEASE ? "release" : "press") + "]";
    }
}