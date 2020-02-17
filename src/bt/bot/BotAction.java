package bt.bot;

import bt.utils.log.Logger;

/**
 * @author &#8904
 *
 */
public class BotAction
{
    public static final int RELEASE = 1;
    public static final int PRESS = 2;
    public static final int WAIT = 4;
    public static final int REPEAT = 5;
    public static final int DO = 6;

    private BotKey key;
    private int wait = -1;
    private int repeat = -1;
    private int runs = -1;
    private int pressRelease;

    public BotAction(int type, int value)
    {
        if (type == WAIT)
        {
            this.wait = value;
        }
        else if (type == REPEAT)
        {
            this.repeat = value;
        }
        else if (type == DO)
        {
            this.runs = value;
        }
    }

    public BotAction(BotKey key, int pressRelease)
    {
        this.key = key;
        this.pressRelease = pressRelease;
    }

    public void execute(BotActionExecutor executor)
    {
        if (this.wait > 0)
        {
            try
            {
                Logger.global().print("Waiting " + this.wait);
                Thread.sleep(this.wait);
            }
            catch (InterruptedException e)
            {}
        }
        else if (this.runs > 0)
        {
            executor.setMaxRuns(this.runs);
        }
        else if (this.key != null && executor.getRobot() != null)
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

    public boolean isRepeat()
    {
        return this.repeat > -1;
    }

    public int getRepeatLine()
    {
        return this.repeat;
    }
}