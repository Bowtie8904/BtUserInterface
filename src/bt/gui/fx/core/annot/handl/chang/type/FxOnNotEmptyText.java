package bt.gui.fx.core.annot.handl.chang.type;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextInputControl;

/**
 * @author &#8904
 *
 */
public class FxOnNotEmptyText extends FxStringChange<TextInputControl, String>
{
    /**
     * @see bt.gui.fx.core.annot.handl.chang.FxChangeHandlerType#getSpecialListener(java.lang.Object, java.lang.Object,
     *      java.lang.String)
     */
    @Override
    protected ChangeListener<String> getSpecialListener(TextInputControl fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters, boolean passField, String additionalValue)
    {
        ChangeListener<String> defaultListener = getDefaultListener(fieldObj, handlingObj, handlerMethodName, withParameters, passField, additionalValue);

        return (obs, ol, ne) ->
        {
            if (!ne.isEmpty())
            {
                defaultListener.changed(obs, ol, ne);
            }
        };
    }
}