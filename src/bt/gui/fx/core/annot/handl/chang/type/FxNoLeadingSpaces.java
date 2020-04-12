package bt.gui.fx.core.annot.handl.chang.type;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;

/**
 * @author &#8904
 *
 */
public class FxNoLeadingSpaces extends FxStringChange<TextField, String>
{
    /**
     * @see bt.gui.fx.core.annot.handl.chang.FxChangeHandlerType#getDefaultListener(java.lang.Object, java.lang.Object,
     *      java.lang.String)
     */
    @Override
    protected ChangeListener<String> getDefaultListener(TextField fieldObj, Object handlingObj, String handlerMethodName)
    {
        return (obs, ol, ne) ->
        {
            fieldObj.setText(ne.stripTrailing());
        };
    }
}