package bt.gui.fx.core.annot.handl.evnt.type;

import bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.InputMethodEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link InputMethodEvent} parameter.
 *
 * @see {@link Node#onInputMethodTextChangedProperty()}
 * @author &#8904
 */
public class FxOnInputMethodTextChanged extends FxEventHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          InputMethodEvent.class
        };
    }

    /**
     * @see bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType#getEventType()
     */
    @Override
    protected EventType getEventType()
    {
        return InputMethodEvent.INPUT_METHOD_TEXT_CHANGED;
    }
}