package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import javafx.scene.Node;
import javafx.scene.input.RotateEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link RotateEvent} parameter.
 *
 * @see {@link Node#onRotationFinishedProperty()}
 * @author &#8904
 */
public class FxOnRotationFinished extends FxHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          RotateEvent.class
        };
    }
}