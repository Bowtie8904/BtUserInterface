package bt.gui.fx.core.annot.handl.type;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author &#8904
 *
 */
public class FxOnAction extends FxHandlerType<ActionEvent>
{
    /**
     * @see bt.gui.fx.core.annot.handl.FxHandlerType#getHandlerSetMethodName()
     */
    @Override
    protected String getHandlerSetMethodName()
    {
        return "setOnAction";
    }

    /**
     * @see bt.gui.fx.core.annot.handl.FxHandlerType#createEventHandler(java.lang.Runnable)
     */
    @Override
    protected EventHandler<ActionEvent> createEventHandler(Runnable handler)
    {
        return e -> handler.run();
    }
}