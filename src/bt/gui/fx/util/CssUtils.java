package bt.gui.fx.util;

import javafx.scene.Node;

/**
 * @author &#8904
 *
 */
public class CssUtils
{
    public static void addStyleClass(Node node, String styleClass)
    {
        if (!node.getStyleClass().contains(styleClass))
        {
            node.getStyleClass().add(styleClass);
        }
    }

    public static void removeStyleClass(Node node, String styleClass)
    {
        node.getStyleClass().removeIf(s -> s.equalsIgnoreCase(styleClass));
    }
}