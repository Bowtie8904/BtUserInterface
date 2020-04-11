package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxEventHandlerType;
import javafx.event.ActionEvent;
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
}