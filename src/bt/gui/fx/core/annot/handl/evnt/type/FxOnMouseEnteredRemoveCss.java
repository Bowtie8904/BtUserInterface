package bt.gui.fx.core.annot.handl.evnt.type;

import bt.gui.fx.core.exc.FxException;
import bt.gui.fx.util.CssUtils;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link MouseEvent} parameter.
 *
 * @see {@link Node#onMouseEnteredProperty()}
 * @author &#8904
 */
public class FxOnMouseEnteredRemoveCss extends FxOnMouseEntered
{
    /**
     * @see bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType#getSpecialHandler(java.lang.Object, java.lang.Object,
     *      java.lang.String, boolean, boolean, java.lang.String)
     */
    @Override
    protected EventHandler getSpecialHandler(Node fieldObj, Object handlingObj, String cssClass, boolean withParameters, boolean passField, String additionalValue, Class<?> fieldObjType, String fieldName)
    {
        if (cssClass.isEmpty())
        {
            throw new FxException("Missing css class in 'method' field of FxHandler annotation.");
        }

        return e ->
        {
            CssUtils.removeStyleClass(fieldObj, cssClass);
        };
    }
}