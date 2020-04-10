package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link MouseEvent} parameter.
 *
 * @see {@link Node#onMouseExitedProperty()}
 * @author &#8904
 */
public class FxOnMouseExited extends FxHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          MouseEvent.class
        };
    }
}