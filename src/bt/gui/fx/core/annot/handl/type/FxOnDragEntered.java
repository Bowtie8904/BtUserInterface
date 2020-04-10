package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link DragEvent} parameter.
 *
 * @see {@link Node#onDragEnteredProperty()}
 * @author &#8904
 */
public class FxOnDragEntered extends FxHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          DragEvent.class
        };
    }
}