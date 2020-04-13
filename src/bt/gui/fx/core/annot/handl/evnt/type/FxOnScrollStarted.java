package bt.gui.fx.core.annot.handl.evnt.type;

import bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.ScrollEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link ScrollEvent} parameter.
 *
 * @see {@link Node#onScrollStartedProperty()}
 * @author &#8904
 */
public class FxOnScrollStarted extends FxEventHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          ScrollEvent.class
        };
    }

    /**
     * @see bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType#getEventType()
     */
    @Override
    protected EventType getEventType()
    {
        return ScrollEvent.SCROLL_STARTED;
    }
}