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
            Logger.global().print("Moved [" + this.x + " | " + this.y + "]");
        }
        else
        {
            var currentPosition = MouseInfo.getPointerInfo().getLocation();
            int currentX = currentPosition.x;
            int currentY = currentPosition.y;

            double xStep = (this.x - currentX) / (double)this.steps;

            if (xStep < 0)
            {
                xStep = Math.ceil(Math.abs(xStep)) * -1;
            }
            else
            {
                xStep = Math.ceil(xStep);
            }

            double yStep = (this.y - currentY) / (double)this.steps;

            if (yStep < 0)
            {
                yStep = Math.ceil(Math.abs(yStep)) * -1;
            }
            else
            {
                yStep = Math.ceil(yStep);
            }

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

                executor.getRobot().mouseMove(currentX, currentY);
                Exceptions.ignoreThrow(Thread::sleep, 1);

                if (currentX == this.x && currentY == this.y)
                {
                    break;
                }
            }
        }
    }
}