package bt.bot.action;

import bt.bot.BotActionExecutor;
import bt.utils.log.Logger;

/**
 * @author &#8904
 *
 */
public class BotMouseMoveAction extends BotAction
{
    private int x;
    private int y;

    public BotMouseMoveAction(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(BotActionExecutor executor)
    {
        executor.getRobot().mouseMove(this.x, this.y);
        Logger.global().print("Moved [" + this.x + " | " + this.y + "]");
    }
}