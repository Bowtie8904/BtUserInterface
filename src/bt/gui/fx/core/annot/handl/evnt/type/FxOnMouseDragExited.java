package bt.gui.fx.core.annot.handl.evnt.type;

import bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType;
import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link MouseDragEvent} parameter.
 *
 * @see {@link Node#onMouseDragEnteredProperty()}
 * @author &#8904
 */
public class FxOnMouseDragExited extends FxEventHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          MouseDragEvent.class
        };
    }
}