package bt.bot.action;

import bt.bot.BotActionExecutor;
import bt.bot.BotKey;
import bt.utils.log.Logger;

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
                Logger.global().print("Pressed [" + this.key.getLiteral() + "]");
            }
            else if (this.pressRelease == RELEASE)
            {
                executor.getRobot().keyRelease(this.key.getCode());
                Logger.global().print("Released [" + this.key.getLiteral() + "]");
            }

        }
    }
}