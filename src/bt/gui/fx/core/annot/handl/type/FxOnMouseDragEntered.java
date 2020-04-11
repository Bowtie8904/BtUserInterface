package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxEventHandlerType;
import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link MouseDragEvent} parameter.
 *
 * @see {@link Node#onMouseDragExitedProperty()}
 * @author &#8904
 */
public class FxOnMouseDragEntered extends FxEventHandlerType
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