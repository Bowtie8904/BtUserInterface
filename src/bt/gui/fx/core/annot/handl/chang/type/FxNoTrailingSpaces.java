package bt.gui.fx.core.annot.handl.chang.type;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextInputControl;

/**
 * @author &#8904
 *
 */
public class FxNoTrailingSpaces extends FxStringChange<TextInputControl, String>
{
    /**
     * @see bt.gui.fx.core.annot.handl.chang.FxChangeHandlerType#getSpecialListener(java.lang.Object, java.lang.Object,
     *      java.lang.String)
     */
    @Override
    protected ChangeListener<String> getSpecialListener(TextInputControl fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters, boolean passField, String additionalValue)
    {
        return (obs, ol, ne) ->
        {
            fieldObj.setText(ne.stripTrailing());
        };
    }
}