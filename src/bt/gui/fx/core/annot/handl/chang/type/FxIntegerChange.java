package bt.gui.fx.core.annot.handl.chang.type;

import bt.gui.fx.core.annot.handl.chang.FxChangeHandlerType;
import javafx.beans.value.ObservableValue;

/**
 * @author &#8904
 *
 */
public class FxIntegerChange extends FxChangeHandlerType
{
    /**
     * @see bt.gui.fx.core.annot.handl.chang.FxChangeHandlerType#getHandlerParameterTypes()
     */
    @Override
    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class<?>[]
        {
          ObservableValue.class, Integer.class, Integer.class
        };
    }
}