package bt.key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bt.log.Logger;

/**
 * @author &#8904
 *
 */
public class KeyActionSet
{
    private Map<Integer, List<KeyAction>> keyActions;

    protected KeyActionSet()
    {
        this.keyActions = new HashMap<>();
    }

    protected void dispatchEvent(KeyAction.Action actionType, KeyEventWrapper e)
    {
        List<KeyAction> undefinedKeyActions = this.keyActions.get(KeyAction.UNDEFINED_KEY_ACTION_CODE);

        if (undefinedKeyActions != null)
        {
            for (KeyAction action : undefinedKeyActions)
            {
                action.execute(actionType,
                               e);
            }
        }

        List<KeyAction> actions = this.keyActions.get(e.getKeyCode());

        if (actions != null)
        {
            for (KeyAction action : actions)
            {
                action.execute(actionType,
                               e);
            }
        }
    }

    protected void addKeyAction(KeyAction action)
    {
        int keyCode = action.getKeyCode();

        if (!this.keyActions.containsKey(keyCode))
        {
            this.keyActions.put(keyCode,
                                new ArrayList<KeyAction>());
        }

        List<KeyAction> actions = this.keyActions.get(keyCode);
        actions.add(action);

        Logger.global()
              .print(KeyBoardHook.get(),
                     "Registered KeyAction: " + action);
    }

    protected void removeKeyAction(KeyAction action)
    {
        int keyCode = action.getKeyCode();

        if (this.keyActions.containsKey(keyCode))
        {
            this.keyActions.get(keyCode).remove(action);
        }
    }

    protected void clear(int keyCode)
    {
        List<KeyAction> actions = this.keyActions.get(keyCode);

        if (actions != null)
        {
            actions.clear();
        }
    }

    protected void clear()
    {
        this.keyActions.clear();
    }
}