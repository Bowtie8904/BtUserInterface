package bt.bot;

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
     * @param sequenceFile
     * @return A list containing all actions that were loaded from the given file.
     */
    public List<BotAction> load(File sequenceFile)
    {
        String[] lines = FileUtils.readLines(sequenceFile);

        List<BotAction> currentActions = new ArrayList<>();

        for (String line : lines)
        {
            BotAction action = null;

            if (line.trim().toLowerCase().startsWith("repeatfrom:"))
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
                    Logger.global().print(e1);
                }
            }
            else if (line.trim().toLowerCase().startsWith("wait:"))
            {
                try
                {
                    int wait = Integer.parseInt(line.split(" ")[1]);
                    currentActions.add(new BotWaitAction(wait));
                }
                catch (Exception e)
                {
                    Logger.global().print("Invalid wait line '" + line + "'.");
                    Logger.global().print(e);
                }
            }
            else if (line.trim().toLowerCase().startsWith("do:"))
            {
                try
                {
                    int doRuns = Integer.parseInt(line.split(" ")[1]);
                    currentActions.add(new BotDoAction(doRuns));
                }
                catch (Exception e1)
                {
                    Logger.global().print("Invalid do line '" + line + "'.");
                    Logger.global().print(e1);
                }
            }
            else if (line.trim().toLowerCase().startsWith("press:"))
            {
                BotKey key = BotKey.forLiteral(line.replace("press:",
                                                            "")
                                                   .trim());

                if (key != null)
                {
                    currentActions.add(new BotKeyAction(key,
                                                        BotKeyAction.PRESS));
                }
                else
                {
                    Logger.global().print("Invalid key line '" + line + "'.");
                }
            }
            else if (line.trim().toLowerCase().startsWith("release:"))
            {
                BotKey key = BotKey.forLiteral(line.replace("release:",
                                                            "")
                                                   .trim());

                if (key != null)
                {
                    currentActions.add(new BotKeyAction(key,
                                                        BotKeyAction.RELEASE));
                }
                else
                {
                    Logger.global().print("Invalid key line '" + line + "'.");
                }
            }
            else if (line.trim().toLowerCase().startsWith("move:"))
            {
                try
                {
                    String[] parts = line.replace("move:", "").split(" ");
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    currentActions.add(new BotMouseMoveAction(x, y));
                }
                catch (Exception e1)
                {
                    Logger.global().print("Invalid move line '" + line + "'.");
                    Logger.global().print(e1);
                }
            }
            else if (line.trim().toLowerCase().startsWith("click:"))
            {
                String button = line.toLowerCase().split(" ")[1].trim();

                switch (button)
                {
                    case "left":
                        currentActions.add(new BotMouseClickAction(BotMouseClickAction.LEFT));
                        break;
                    case "middle":
                        currentActions.add(new BotMouseClickAction(BotMouseClickAction.MIDDLE));
                        break;
                    case "right":
                        currentActions.add(new BotMouseClickAction(BotMouseClickAction.RIGHT));
                        break;
                }
            }
        }

        this.actionSet.addAll(currentActions);

        return currentActions;
    }

}