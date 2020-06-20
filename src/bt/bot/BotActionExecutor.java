package bt.bot;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.List;

import bt.bot.action.BotAction;
import bt.bot.action.BotRepeatAction;
import bt.log.Logger;

/**
 * @author &#8904
 *
 */
public class BotActionExecutor
{
    private Robot robot;
    private int maxRuns;

    public BotActionExecutor()
    {
        try
        {
            this.robot = new Robot();
        }
        catch (AWTException e)
        {
            Logger.global().print(e);
        }

        this.maxRuns = -1;
    }

    public int getMaxRuns()
    {
        return this.maxRuns;
    }

    public void setMaxRuns(int maxRuns)
    {
        this.maxRuns = maxRuns;
    }

    public Robot getRobot()
    {
        return this.robot;
    }

    public void execute(List<? extends BotAction> actions)
    {
        int index = 0;
        BotAction action;
        Robot robot = null;
        try
        {
            robot = new Robot();
        }
        catch (AWTException e1)
        {
            Logger.global().print(e1);
        }

        int runs = 0;

        while (index < actions.size())
        {
            action = actions.get(index);

            if (action instanceof BotRepeatAction)
            {
                runs ++ ;

                if (this.maxRuns > -1 && runs >= this.maxRuns)
                {
                    Logger.global().print("Exiting loop after " + runs + " runs.");
                    index ++ ;
                    runs = 0;
                    this.maxRuns = -1;
                }
                else
                {
                    index = ((BotRepeatAction)action).from();
                    Logger.global().print("Repeating from " + ((BotRepeatAction)action).from() + "  [Run: " + runs + "]");
                }
            }
            else
            {
                action.execute(this);
                index ++ ;
            }
        }

    }
}