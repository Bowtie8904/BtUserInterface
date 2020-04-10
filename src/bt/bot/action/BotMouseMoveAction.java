package bt.bot.action;

import java.awt.MouseInfo;

import bt.bot.BotActionExecutor;
import bt.utils.exc.Exceptions;
import bt.utils.log.Logger;

/**
 * @author &#8904
 *
 */
public class BotMouseMoveAction extends BotAction
{
    private int x;
    private int y;
    private long steps = 0;

    public BotMouseMoveAction(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public BotMouseMoveAction(int x, int y, long steps)
    {
        this.x = x;
        this.y = y;
        this.steps = steps;
    }

    @Override
    public void execute(BotActionExecutor executor)
    {
        if (this.steps == 0)
        {
            executor.getRobot().mouseMove(this.x, this.y);
        }
        else
        {
            var currentPosition = MouseInfo.getPointerInfo().getLocation();
            double currentX = currentPosition.x;
            double currentY = currentPosition.y;

            // calculate distance for each step
            double xStep = (this.x - currentX) / (double)this.steps;
            double yStep = (this.y - currentY) / (double)this.steps;

            Logger.global().print(xStep + "   " + yStep);

            for (int i = 0; i < this.steps; i ++ )
            {
                if (currentX != this.x)
                {
                    currentX += xStep;
                }

                if (currentY != this.y)
                {
                    currentY += yStep;
                }

                executor.getRobot().mouseMove((int)currentX, (int)currentY);
                Exceptions.ignoreThrow(Thread::sleep, 1);

                if (currentX == this.x && currentY == this.y)
                {
                    break;
                }
            }

            // just to be save
            executor.getRobot().mouseMove(this.x, this.y);
        }

        Logger.global().print("Moved [" + this.x + " | " + this.y + "]");
    }

    @Override
    public String toString()
    {
        return this.getClass().getName() + " [x=" + this.x + ", y=" + this.y + ", steps=" + this.steps + "]";
    }
}