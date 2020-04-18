package bt.gui.fx.util.group;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * @author &#8904
 *
 */
public class GroupStatus
{
    private String name;
    private Map<Object, Boolean> status;
    private BooleanProperty collectedStatus;

    public GroupStatus(String name)
    {
        this.status = new HashMap<>();
        this.collectedStatus = new SimpleBooleanProperty(false);
    }

    public boolean containsKey(Object obj)
    {
        return this.status.containsKey(obj);
    }

    public void put(Object obj, boolean status)
    {
        this.status.put(obj, status);
    }

    public BooleanProperty collectedStatus()
    {
        return this.collectedStatus;
    }

    public void collectStatus()
    {
        boolean collectStatus = !this.status.values().stream().filter(b -> !b).findAny().isPresent();
        this.collectedStatus.set(collectStatus);
    }
}