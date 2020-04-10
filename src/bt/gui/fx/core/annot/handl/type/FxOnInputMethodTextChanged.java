package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import javafx.scene.Node;
import javafx.scene.input.InputMethodEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link InputMethodEvent} parameter.
 *
 * @see {@link Node#onInputMethodTextChangedProperty()}
 * @author &#8904
 */
public class FxOnInputMethodTextChanged extends FxHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          InputMethodEvent.class
        };
    }
}