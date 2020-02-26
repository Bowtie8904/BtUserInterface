package bt.bot.action;

import bt.bot.BotActionExecutor;

/**
 * @author &#8904
 *
 */
public class BotRepeatAction extends BotAction
{
    private int repeatFrom;

    public BotRepeatAction(int repeatFrom)
    {
        this.repeatFrom = repeatFrom;
    }

    @Override
    public void execute(BotActionExecutor executor)
    {
        // no actualy action
        // this is just used as a type to check for
    }

    public int from()
    {
        return this.repeatFrom;
    }
}