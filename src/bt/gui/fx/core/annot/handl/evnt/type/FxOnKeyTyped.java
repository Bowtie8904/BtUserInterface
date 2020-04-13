package bt.gui.fx.core.annot.handl.evnt.type;

import bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType;
import javafx.event.EventType;
import javafx.scene.control.ButtonBase;
import javafx.scene.input.KeyEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link KeyEvent} parameter.
 *
 * @see {@link ButtonBase#onKeyTypedProperty()}
 * @author &#8904
 */
public class FxOnKeyTyped extends FxEventHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          KeyEvent.class
        };
    }

    /**
     * @see bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType#getEventType()
     */
    @Override
    protected EventType getEventType()
    {
        return KeyEvent.KEY_TYPED;
    }
}