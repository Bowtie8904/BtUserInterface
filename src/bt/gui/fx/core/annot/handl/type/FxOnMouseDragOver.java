package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link MouseDragEvent} parameter.
 *
 * @see {@link Node#onMouseDragOverProperty()}
 * @author &#8904
 */
public class FxOnMouseDragOver extends FxHandlerType
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