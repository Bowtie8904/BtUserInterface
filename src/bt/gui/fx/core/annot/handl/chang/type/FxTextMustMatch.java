package bt.gui.fx.core.annot.handl.chang.type;

import java.util.regex.Pattern;

import bt.gui.fx.core.exc.FxException;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextInputControl;

/**
 * @author &#8904
 *
 */
public class FxTextMustMatch extends FxStringChange<TextInputControl, String>
{
    /**
     * @see bt.gui.fx.core.annot.handl.chang.FxChangeHandlerType#getSpecialListener(java.lang.Object, java.lang.Object,
     *      java.lang.String)
     */
    @Override
    protected ChangeListener<String> getSpecialListener(TextInputControl fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters, boolean passField, String regex,
                                                        Class<?> fieldObjType, String fieldName)
    {
        if (regex.isEmpty())
        {
            throw new FxException("Missing regular expression in 'value' field of FxHandler annotation.");
        }

        Pattern regexPattern = Pattern.compile(regex);

        return (obs, ol, ne) ->
        {
            if (!regexPattern.matcher(ne).matches())
            {
                fieldObj.setText(ol);
            }
        };
    }
}