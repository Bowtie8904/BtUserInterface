package bt.key;

import java.util.*;

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
        if (this.keyActions.containsKey(KeyAction.UNDEFINED_KEY_ACTION_CODE))
        {
            List<KeyAction> copyActions = new ArrayList<>(this.keyActions.get(KeyAction.UNDEFINED_KEY_ACTION_CODE));

            for (KeyAction action : copyActions)
            {
                action.execute(actionType, e);
            }
        }

        if (this.keyActions.containsKey(e.getKeyCode()))
        {
            List<KeyAction> copyActions = new ArrayList<>(this.keyActions.get(e.getKeyCode()));

            for (KeyAction action : copyActions)
            {
                action.execute(actionType, e);
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

        System.out.println("Registered KeyAction: " + action);
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