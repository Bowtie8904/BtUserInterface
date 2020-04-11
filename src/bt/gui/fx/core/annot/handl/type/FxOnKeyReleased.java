package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import javafx.scene.control.ButtonBase;
import javafx.scene.input.KeyEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link KeyEvent} parameter.
 *
 * @see {@link ButtonBase#onKeyReleasedProperty()}
 * @author &#8904
 */
public class FxOnKeyReleased extends FxHandlerType
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