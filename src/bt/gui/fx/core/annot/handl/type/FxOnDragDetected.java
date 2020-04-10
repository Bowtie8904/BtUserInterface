package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link MouseEvent} parameter.
 *
 * @see {@link Node#onDragDetectedProperty()}
 * @author &#8904
 */
public class FxOnDragDetected extends FxHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          MouseEvent.class
        };
    }
}