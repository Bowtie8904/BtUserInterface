package bt.gui.fx.util.group;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.value.ChangeListener;

/**
 * @author &#8904
 *
 */
public class GroupStatusCollector
{
    private static volatile GroupStatusCollector instance;

    public static GroupStatusCollector get()
    {
        if (instance == null)
        {
            instance = new GroupStatusCollector();
        }

        return instance;
    }

    private Map<String, GroupStatus> groups;

    protected GroupStatusCollector()
    {
        this.groups = new HashMap<>();
    }

    private static GroupStatus getGroup(String groupName)
    {
        GroupStatus group = get().groups.get(groupName);

        if (group == null)
        {
            group = new GroupStatus(groupName);
            get().groups.put(groupName, group);
        }

        return group;
    }

    private static GroupStatus getGroupFor(Object obj)
    {
        GroupStatus foundGroup = null;

        for (var group : get().groups.values())
        {
            if (group.containsKey(obj))
            {
                foundGroup = group;
                break;
            }
        }

        return foundGroup;
    }

    public static void addListener(ChangeListener<Boolean> listener, String groupName)
    {
        getGroup(groupName).collectedStatus().addListener(listener);
    }

    public static void register(Object obj, String groupName)
    {
        getGroup(groupName).put(obj, false);
    }

    public static void setTrue(Object obj)
    {
        set(obj, true);
    }

    public static void setFalse(Object obj)
    {
        set(obj, false);
    }

    public static void set(Object obj, boolean status)
    {
        GroupStatus group = getGroupFor(obj);

        if (group != null)
        {
            group.put(obj, status);
            group.collectStatus();
        }
    }
}