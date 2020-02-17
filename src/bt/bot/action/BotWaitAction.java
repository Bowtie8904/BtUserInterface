package bt.bot.action;

import bt.bot.BotActionExecutor;
import bt.utils.log.Logger;

/**
 * @author &#8904
 *
 */
public class BotWaitAction extends BotAction
{
    private long waitTime;

    public BotWaitAction(long waitTime)
    {
        this.waitTime = waitTime;
    }

    @Override
    public void execute(BotActionExecutor executor)
    {
        if (this.waitTime > 0)
        {
            try
            {
                Logger.global().print("Waiting " + this.waitTime);
                Thread.sleep(this.waitTime);
            }
            catch (InterruptedException e)
            {}
        }
    }
}