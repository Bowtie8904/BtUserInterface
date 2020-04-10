package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import javafx.scene.Node;
import javafx.scene.input.ScrollEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link ScrollEvent} parameter.
 *
 * @see {@link Node#onScrollProperty()}
 * @author &#8904
 */
public class FxOnScroll extends FxHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          ScrollEvent.class
        };
    }
}