package bt.bot.action;

import bt.bot.BotActionExecutor;
import bt.log.Log;

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
                Log.info("Waiting " + this.waitTime);
                Thread.sleep(this.waitTime);
            }
            catch (InterruptedException e)
            {}
        }
    }

    @Override
    public String toString()
    {
        return this.getClass().getName() + " [time=" + this.waitTime + "]";
    }
}