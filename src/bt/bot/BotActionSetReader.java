package bt.bot;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bt.bot.action.BotAction;
import bt.bot.action.BotDoAction;
import bt.bot.action.BotKeyAction;
import bt.bot.action.BotMouseClickAction;
import bt.bot.action.BotMouseMoveAction;
import bt.bot.action.BotRepeatAction;
import bt.bot.action.BotWaitAction;
import bt.utils.files.FileUtils;
import bt.utils.log.Logger;

/**
 * @author &#8904
 *
 */
public class BotActionSetReader
{
    private List actionSet;

    public BotActionSetReader()
    {
        this.actionSet = new ArrayList<>();
    }

    public void addBotAction(BotAction action)
    {
        this.actionSet.add(action);
    }

    public List<BotAction> getBotActions()
    {
        return this.actionSet;
    }

    /**
     * Loads all actions from the given file.
     *
     * <p>
     * Each action has to be on a separate line.
     * </p>
     * <p>
     * All loaded actions will be added to the list of all loaded actions. Accessible via {@link #getBotActions()}.
     * </p>
     *
     * <h1>Available actions</h1>
     *
     * Any line that does not start with any of the action commands below will be ignored.
     *
     * <pre>
     * <b>press: {key}</b>
     * <b>press: f</b>
     * Presses (and holds) the given key.
     * The literals for the keys are determined by {@link KeyEvent#getKeyText}.
     * A list of all available keys can be obtained from {@link BotKey#keys}.
     * Each key offers a {@link BotKey#getLiteral() getLiteral} method.
     * </pre>
     *
     * <pre>
     * <b>release: {key}</b>
     * <b>release: f</b>
     * Releases the given key.
     * The literals for the keys are determined by {@link KeyEvent#getKeyText}.
     * A list of all available keys can be obtained from {@link BotKey#keys}.
     * Each key offers a {@link BotKey#getLiteral() getLiteral} method.
     * </pre>
     *
     * <pre>
     * <b>mouse press: {mouseKey}</b>
     * <b>mouse press: left</b>
     * Presses (and holds) the given mouse key.
     * Available mouse keys are left, right and middle.
     * </pre>
     *
     * <pre>
     * <b>mouse release: {mouseKey}</b>
     * <b>mouse release: left</b>
     * Releases the given mouse key.
     * Available mouse keys are left, right and middle.
     * </pre>
     *
     * <pre>
     * <b>move fast: {x} {y}</b>
     * <b>move fast: 123 456</b>
     * Moves the mouse cursor to the given x and y coordinates with a single
     * action.
     * </pre>
     *
     * <pre>
     * <b>move slow {steps}: {x} {y}</b>
     * <b>move slow 500: 123 456</b>
     * Moves the mouse cursor to the given x and y coordinates with the given
     * amount of steps. If 500 steps are given, then this action will move
     * the cursor 500 times until it reaches its final coordinates.
     * </pre>
     *
     * <pre>
     * <b>wait: {time}</b>
     * <b>wait: 1000</b>
     * Waits for the given amount of milliseconds before continueing with the
     * next action.
     * </pre>
     *
     * <pre>
     * <b>repeatfrom: {actionIndex}</b>
     * <b>repeatFrom: 1</b>
     * Restarts the sequence from the given zero based action index.
     * This is not necessarely equal to the line number in the sequence file.
     * Comment lines and empty lines are not taken into account.
     * </pre>
     *
     * <pre>
     * <b>do: {loops}</b>
     * <b>do: 10</b>
     * Sets how many times the next repeatfrom action should be executed
     * before exiting that loop. The action 'do: 10' means that the next
     * repeatfrom action will be executed 10 times, including the repeated
     * block, before the loop exits and the next acttion is executed.
     * The do line has to appear before the repeatfrom line but does need
     * to appear before the repeated block of actions.
     * It is not possible to create a loop inside another loop with this.
     * </pre>
     *
     * @param sequenceFile
     *            The file to load the action sequence from. Each action has to be on a separate line. Empty lines and
     *            lines with content that is not an action are allowed.
     * @return A list containing all actions that were loaded from the given file.
     */
    public List<BotAction> load(File sequenceFile)
    {
        String[] lines = FileUtils.readLines(sequenceFile);

        List<BotAction> currentActions = new ArrayList<>();

        for (String line : lines)
        {
            line = line.toLowerCase().trim();
            BotAction action = null;

            if (line.startsWith("repeatfrom:"))
            {
                try
                {
                    int from = Integer.parseInt(line.split(" ")[1]);
                    currentActions.add(new BotRepeatAction(from));
                }
                catch (IndexOutOfBoundsException e)
                {
                    currentActions.add(new BotRepeatAction(0));
                }
                catch (Exception e1)
                {
                    Logger.global().print("Invalid repeatfrom line '" + line + "'.");
                    Logger.global().print("Example: 'repeatfrom: 1'");
                    Logger.global().print(e1);
                }
            }
            else if (line.startsWith("wait:"))
            {
                try
                {
                    int wait = Integer.parseInt(line.split(" ")[1]);
                    currentActions.add(new BotWaitAction(wait));
                }
                catch (Exception e)
                {
                    Logger.global().print("Invalid wait line '" + line + "'.");
                    Logger.global().print("Example: 'wait: 1000'");
                    Logger.global().print(e);
                }
            }
            else if (line.startsWith("do:"))
            {
                try
                {
                    int doRuns = Integer.parseInt(line.split(" ")[1]);
                    currentActions.add(new BotDoAction(doRuns));
                }
                catch (Exception e1)
                {
                    Logger.global().print("Invalid do line '" + line + "'.");
                    Logger.global().print("Example: 'do: 10'");
                    Logger.global().print(e1);
                }
            }
            else if (line.startsWith("press:"))
            {
                BotKey key = BotKey.forLiteral(line.replace("press:", "").trim());

                if (key != null)
                {
                    currentActions.add(new BotKeyAction(key, BotKeyAction.PRESS));
                }
                else
                {
                    Logger.global().print("Invalid key line '" + line + "'.");
                    Logger.global().print("Example: 'press: f'");
                }
            }
            else if (line.startsWith("release:"))
            {
                BotKey key = BotKey.forLiteral(line.replace("release:", "").trim());

                if (key != null)
                {
                    currentActions.add(new BotKeyAction(key, BotKeyAction.RELEASE));
                }
                else
                {
                    Logger.global().print("Invalid key line '" + line + "'.");
                    Logger.global().print("Example: 'release: f'");
                }
            }
            else if (line.startsWith("move fast:"))
            {
                try
                {
                    String[] parts = line.replace("move fast:", "").trim().split(" ");
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    currentActions.add(new BotMouseMoveAction(x, y));
                }
                catch (Exception e1)
                {
                    Logger.global().print("Invalid move line '" + line + "'.");
                    Logger.global().print("Example: 'move fast: 123 456'");
                    Logger.global().print(e1);
                }
            }
            else if (line.startsWith("move slow"))
            {
                try
                {
                    String[] parts = line.split(":");
                    int x = Integer.parseInt(parts[1].trim().split(" ")[0]);
                    int y = Integer.parseInt(parts[1].trim().split(" ")[1]);

                    long time = Long.parseLong(parts[0].replace("move slow", "").trim());

                    currentActions.add(new BotMouseMoveAction(x, y, time));
                }
                catch (Exception e1)
                {
                    Logger.global().print("Invalid move line '" + line + "'.");
                    Logger.global().print("Example: 'move slow 500: 123 456'");
                    Logger.global().print(e1);
                }
            }
            else if (line.startsWith("mouse press:"))
            {
                try
                {
                    String button = line.replace("mouse press:", "").split(" ")[1].trim();

                    switch (button)
                    {
                        case "left":
                            currentActions.add(new BotMouseClickAction(BotMouseClickAction.LEFT, BotMouseClickAction.PRESS));
                            break;
                        case "middle":
                            currentActions.add(new BotMouseClickAction(BotMouseClickAction.MIDDLE, BotMouseClickAction.PRESS));
                            break;
                        case "right":
                            currentActions.add(new BotMouseClickAction(BotMouseClickAction.RIGHT, BotMouseClickAction.PRESS));
                            break;
                        default:
                            throw new IllegalArgumentException("Use [left, right, middle]");
                    }
                }
                catch (Exception e1)
                {
                    Logger.global().print("Invalid mouse press line '" + line + "'.");
                    Logger.global().print("Example: 'mouse press: right'");
                    Logger.global().print(e1);
                }
            }
            else if (line.startsWith("mouse release:"))
            {
                try
                {
                    String button = line.replace("mouse release:", "").split(" ")[1].trim();

                    switch (button)
                    {
                        case "left":
                            currentActions.add(new BotMouseClickAction(BotMouseClickAction.LEFT, BotMouseClickAction.RELEASE));
                            break;
                        case "middle":
                            currentActions.add(new BotMouseClickAction(BotMouseClickAction.MIDDLE, BotMouseClickAction.RELEASE));
                            break;
                        case "right":
                            currentActions.add(new BotMouseClickAction(BotMouseClickAction.RIGHT, BotMouseClickAction.RELEASE));
                            break;
                        default:
                            throw new IllegalArgumentException("Use [left, right, middle]");
                    }
                }
                catch (Exception e1)
                {
                    Logger.global().print("Invalid mouse release line '" + line + "'.");
                    Logger.global().print("Example: 'mouse release: left'");
                    Logger.global().print(e1);
                }
            }
        }

        this.actionSet.addAll(currentActions);

        return currentActions;
    }

}