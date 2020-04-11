package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxEventHandlerType;
import javafx.scene.Node;
import javafx.scene.input.ZoomEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link ZoomEvent} parameter.
 *
 * @see {@link Node#onZoomStartedProperty()}
 * @author &#8904
 */
public class FxOnZoomStarted extends FxEventHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          ZoomEvent.class
        };
    }
}