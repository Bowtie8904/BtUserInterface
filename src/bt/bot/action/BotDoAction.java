package bt.bot.action;

import bt.bot.BotActionExecutor;

/**
 * @author &#8904
 *
 */
public class BotDoAction extends BotAction
{
    private int runs;

    public BotDoAction(int runs)
    {
        this.runs = runs;
    }

    @Override
    public void execute(BotActionExecutor executor)
    {
        executor.setMaxRuns(this.runs);
    }

    @Override
    public String toString()
    {
        return this.getClass().getName() + " [runs=" + this.runs + "]";
    }
}