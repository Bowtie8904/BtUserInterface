package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import javafx.scene.Node;
import javafx.scene.input.SwipeEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link SwipeEvent} parameter.
 *
 * @see {@link Node#onSwipeRightProperty()}
 * @author &#8904
 */
public class FxOnSwipeRight extends FxHandlerType
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