package bt.gui.fx.core.annot.handl.chang.type;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextInputControl;

/**
 * @author &#8904
 *
 */
public class FxOnTextEmpty extends FxStringChange<TextInputControl, String>
{
    /**
     * @see bt.gui.fx.core.annot.handl.chang.FxChangeHandlerType#getSpecialListener(java.lang.Object, java.lang.Object,
     *      java.lang.String)
     */
    @Override
    protected ChangeListener<String> getSpecialListener(TextInputControl fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters, boolean passField, String additionalValue,
                                                        Class<?> fieldObjType)
    {
        ChangeListener<String> defaultListener = getDefaultListener(fieldObj, handlingObj, handlerMethodName, withParameters, passField, additionalValue, fieldObjType);

        return (obs, ol, ne) ->
        {
            if (ne.isEmpty())
            {
                defaultListener.changed(obs, ol, ne);
            }
        };
    }
}