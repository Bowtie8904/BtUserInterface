package bt.gui.fx.core.annot.handl.evnt.type;

import bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.control.ButtonBase;

/**
 * Marks a field to be handled by a method with either no arguments or an {@link ActionEvent} parameter.
 *
 * @see {@link ButtonBase#onActionProperty()}
 * @author &#8904
 */
public class FxOnAction extends FxEventHandlerType
{
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          ActionEvent.class
        };
    }

    /**
     * @see bt.gui.fx.core.annot.handl.evnt.FxEventHandlerType#getEventType()
     */
    @Override
    protected EventType getEventType()
    {
        return ActionEvent.ACTION;
    }
}