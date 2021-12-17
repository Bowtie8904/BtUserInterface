package bt.bot;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.List;

import bt.bot.action.BotAction;
import bt.bot.action.BotRepeatAction;
import bt.log.Log;

/**
 * @author &#8904
 *
 */
public class BotActionExecutor
{
    private Robot robot;
    private int maxRuns;
    private volatile boolean running;

    public BotActionExecutor()
    {
        try
        {
            this.robot = new Robot();
        }
        catch (AWTException e)
        {
            Log.error("Failed to create robot", e);
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

    public void stop()
    {
        this.running = false;
    }

    public void execute(List<? extends BotAction> actions)
    {
        this.running = true;
        int index = 0;
        int runs = 0;
        BotAction action;

        while (this.running && index < actions.size())
        {
            action = actions.get(index);

            if (action instanceof BotRepeatAction)
            {
                runs ++ ;

                if (this.maxRuns > -1 && runs >= this.maxRuns)
                {
                    Log.info("Exiting loop after " + runs + " runs.");
                    index ++ ;
                    runs = 0;
                    this.maxRuns = -1;
                }
                else
                {
                    index = ((BotRepeatAction)action).from();
                    Log.info("Repeating from " + ((BotRepeatAction)action).from() + "  [Run: " + runs + "]");
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