package bt.gui.fx.core.annot.handl.evnt.type;

import bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.SwipeEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link SwipeEvent} parameter.
 *
 * @see {@link Node#onSwipeDownProperty()}
 * @author &#8904
 */
public class FxOnSwipeDown extends FxEventHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          SwipeEvent.class
        };
    }

    /**
     * @see bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType#getEventType()
     */
    @Override
    protected EventType getEventType()
    {
        return SwipeEvent.SWIPE_DOWN;
    }
}