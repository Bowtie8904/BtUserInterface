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
import bt.bot.exc.BotActionFormatException;
import bt.utils.files.FileUtils;

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
     * @throws BotActionFormatException
     */
    public List<BotAction> load(File sequenceFile) throws BotActionFormatException
    {
        String[] lines = FileUtils.readLines(sequenceFile);

        List<BotAction> currentActions = new ArrayList<>();

        for (String line : lines)
        {
            line = line.toLowerCase().trim();
            BotAction action = null;

            if (line.startsWith("repeatfrom:"))
            {
                currentActions.add(createBotAction("repeatfrom", line.split(" ")[1]));
            }
            else if (line.startsWith("wait:"))
            {
                currentActions.add(createBotAction("wait", line.split(" ")[1]));
            }
            else if (line.startsWith("do:"))
            {
                currentActions.add(createBotAction("do", line.split(" ")[1]));
            }
            else if (line.startsWith("press:"))
            {
                currentActions.add(createBotAction("press", line.replace("press:", "").trim()));
            }
            else if (line.startsWith("release:"))
            {
                currentActions.add(createBotAction("release", line.replace("release:", "").trim()));
            }
            else if (line.startsWith("move fast:"))
            {
                currentActions.add(createBotAction("move fast", line.replace("move fast:", "").trim()));
            }
            else if (line.startsWith("move slow"))
            {
                currentActions.add(createBotAction("move slow", line.replace("move slow", "").trim()));
            }
            else if (line.startsWith("mouse press:"))
            {
                currentActions.add(createBotAction("mouse press", line.replace("mouse press:", "").trim()));
            }
            else if (line.startsWith("mouse release:"))
            {
                currentActions.add(createBotAction("mouse release", line.replace("mouse release:", "").trim()));
            }
            else if (!line.isEmpty())
            {
                throw new IllegalArgumentException("Invalid line: " + line);
            }
        }

        this.actionSet.addAll(currentActions);

        return currentActions;
    }

    /**
     * @throws BotActionFormatException
     * @see {@link #load(File)}
     */
    public BotAction createBotAction(String keyword, String value) throws BotActionFormatException
    {
        BotAction action = null;
        keyword = keyword.toLowerCase().trim();
        value = value.toLowerCase().trim();

        try
        {
            if (keyword.equals("repeatfrom"))
            {
                try
                {
                    int from = Integer.parseInt(value);
                    action = new BotRepeatAction(from);
                }
                catch (IndexOutOfBoundsException e)
                {
                    action = new BotRepeatAction(0);
                }
            }
            else if (keyword.equals("wait"))
            {
                action = new BotWaitAction(Integer.parseInt(value));
            }
            else if (keyword.equals("do"))
            {
                action = new BotDoAction(Integer.parseInt(value));
            }
            else if (keyword.equals("press"))
            {
                BotKey key = BotKey.forLiteral(value);

                if (key != null)
                {
                    action = new BotKeyAction(key, BotKeyAction.PRESS);
                }
                else
                {
                    throw new IllegalArgumentException("Invalid key literal: " + value);
                }
            }
            else if (keyword.equals("release"))
            {
                BotKey key = BotKey.forLiteral(value);

                if (key != null)
                {
                    action = new BotKeyAction(key, BotKeyAction.RELEASE);
                }
                else
                {
                    throw new IllegalArgumentException("Invalid key literal: " + value);
                }
            }
            else if (keyword.equals("move fast"))
            {
                String[] parts = value.split(" ");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                action = new BotMouseMoveAction(x, y);
            }
            else if (keyword.equals("move slow"))
            {
                String[] parts = value.split(":");
                int x = Integer.parseInt(parts[1].trim().split(" ")[0]);
                int y = Integer.parseInt(parts[1].trim().split(" ")[1]);

                long time = Long.parseLong(parts[0].trim());

                action = new BotMouseMoveAction(x, y, time);
            }
            else if (keyword.equals("mouse press"))
            {
                switch (value)
                {
                    case "left":
                        action = new BotMouseClickAction(BotMouseClickAction.LEFT, BotMouseClickAction.PRESS);
                        break;
                    case "middle":
                        action = new BotMouseClickAction(BotMouseClickAction.MIDDLE, BotMouseClickAction.PRESS);
                        break;
                    case "right":
                        action = new BotMouseClickAction(BotMouseClickAction.RIGHT, BotMouseClickAction.PRESS);
                        break;
                    default:
                        throw new IllegalArgumentException("Use [left, right, middle]");
                }
            }
            else if (keyword.equals("mouse release"))
            {
                switch (value)
                {
                    case "left":
                        action = new BotMouseClickAction(BotMouseClickAction.LEFT, BotMouseClickAction.RELEASE);
                        break;
                    case "middle":
                        action = new BotMouseClickAction(BotMouseClickAction.MIDDLE, BotMouseClickAction.RELEASE);
                        break;
                    case "right":
                        action = new BotMouseClickAction(BotMouseClickAction.RIGHT, BotMouseClickAction.RELEASE);
                        break;
                    default:
                        throw new IllegalArgumentException("Use [left, right, middle]");
                }
            }
            else
            {
                throw new IllegalArgumentException("Invalid keyword: " + keyword);
            }
        }
        catch (Exception e)
        {
            throw new BotActionFormatException("Invalid bot action format. keyword='" + keyword + "' value='" + value + "'.", e);
        }

        action.setKeyword(keyword);
        return action;
    }
}