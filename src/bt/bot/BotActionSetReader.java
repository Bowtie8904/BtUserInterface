package bt.bot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bt.utils.files.FileUtils;
import bt.utils.log.Logger;

/**
 * @author &#8904
 *
 */
public class BotActionSetReader
{
    private List actionSet;

    public BotActionSetReader(File sequenceFile)
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
                    currentActions.add(new BotAction(BotAction.REPEAT,
                                                     from));
                }
                catch (IndexOutOfBoundsException e)
                {
                    currentActions.add(new BotAction(BotAction.REPEAT,
                                                     0));
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
                    currentActions.add(new BotAction(BotAction.WAIT,
                                                     wait));
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
                    currentActions.add(new BotAction(BotAction.DO,
                                                     doRuns));
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
                    currentActions.add(new BotAction(key,
                                                     BotAction.PRESS));
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
                    currentActions.add(new BotAction(key,
                                                     BotAction.RELEASE));
                }
                else
                {
                    Logger.global().print("Invalid key line '" + line + "'.");
                }
            }
        }

        this.actionSet.addAll(currentActions);

        return currentActions;
    }

}