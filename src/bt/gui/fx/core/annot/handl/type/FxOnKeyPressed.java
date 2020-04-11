package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxEventHandlerType;
import javafx.scene.control.ButtonBase;
import javafx.scene.input.KeyEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link KeyEvent} parameter.
 *
 * @see {@link ButtonBase#onKeyPressedProperty()}
 * @author &#8904
 */
public class FxOnKeyPressed extends FxEventHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          KeyEvent.class
        };
    }
}