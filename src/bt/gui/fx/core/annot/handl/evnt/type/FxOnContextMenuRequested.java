package bt.gui.fx.core.annot.handl.evnt.type;

import bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType;
import javafx.scene.control.ButtonBase;
import javafx.scene.input.ContextMenuEvent;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link ContextMenuEvent} parameter.
 *
 * @see {@link ButtonBase#onContextMenuRequestedProperty()}
 * @author &#8904
 */
public class FxOnContextMenuRequested extends FxEventHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          ContextMenuEvent.class
        };
    }
}