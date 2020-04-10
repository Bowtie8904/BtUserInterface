package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import javafx.scene.Node;
import javafx.scene.input.TouchEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link TouchEvent} parameter.
 *
 * @see {@link Node#onTouchReleasedProperty()}
 * @author &#8904
 */
public class FxOnTouchReleased extends FxHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          TouchEvent.class
        };
    }
}