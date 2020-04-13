package bt.gui.fx.core.annot.handl.evnt.type;

import bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.TouchEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link TouchEvent} parameter.
 *
 * @see {@link Node#onTouchStationaryProperty()}
 * @author &#8904
 */
public class FxOnTouchStationary extends FxEventHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          TouchEvent.class
        };
    }

    /**
     * @see bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType#getEventType()
     */
    @Override
    protected EventType getEventType()
    {
        return TouchEvent.TOUCH_STATIONARY;
    }
}