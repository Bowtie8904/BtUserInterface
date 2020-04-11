package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxEventHandlerType;
import javafx.scene.Node;
import javafx.scene.input.SwipeEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link SwipeEvent} parameter.
 *
 * @see {@link Node#onSwipeLeftProperty()}
 * @author &#8904
 */
public class FxOnSwipeLeft extends FxEventHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          SwipeEvent.class
        };
    }
}