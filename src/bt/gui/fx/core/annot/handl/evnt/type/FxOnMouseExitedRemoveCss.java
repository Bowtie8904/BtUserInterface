package bt.gui.fx.core.annot.handl.evnt.type;

import bt.gui.fx.core.exc.FxException;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link MouseEvent} parameter.
 *
 * @see {@link Node#onMouseExitedProperty()}
 * @author &#8904
 */
public class FxOnMouseExitedRemoveCss extends FxOnMouseExited
{
    /**
     * @see bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType#getSpecialHandler(java.lang.Object, java.lang.Object,
     *      java.lang.String, boolean, boolean, java.lang.String)
     */
    @Override
    protected EventHandler getSpecialHandler(Node fieldObj, Object handlingObj, String cssClass, boolean withParameters, boolean passField, String additionalValue)
    {
        if (cssClass.isEmpty())
        {
            throw new FxException("Missing css class in 'method' field of FxHandler annotation.");
        }

        return e ->
        {
            fieldObj.getStyleClass().removeIf(s -> s.equalsIgnoreCase(cssClass));
        };
    }
}