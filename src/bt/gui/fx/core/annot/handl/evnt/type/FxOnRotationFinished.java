package bt.gui.fx.core.annot.handl.evnt.type;

import bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType;
import javafx.scene.Node;
import javafx.scene.input.RotateEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link RotateEvent} parameter.
 *
 * @see {@link Node#onRotationFinishedProperty()}
 * @author &#8904
 */
public class FxOnRotationFinished extends FxEventHandlerType
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