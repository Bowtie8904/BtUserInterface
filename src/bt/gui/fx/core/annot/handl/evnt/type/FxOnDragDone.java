package bt.gui.fx.core.annot.handl.evnt.type;

import bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link DragEvent} parameter.
 *
 * @see {@link Node#onDragDoneProperty()}
 * @author &#8904
 */
public class FxOnDragDone extends FxEventHandlerType
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