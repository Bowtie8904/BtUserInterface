package bt.gui.fx.core.annot.handl.chang.type;

import java.util.regex.Pattern;

import bt.gui.fx.core.exc.FxException;
import bt.gui.fx.util.CssUtils;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextInputControl;

/**
 * @author &#8904
 *
 */
public class FxOnTextMatchRemoveCss extends FxStringChange<TextInputControl, String>
{
    /**
     * @see bt.gui.fx.core.annot.handl.chang.FxChangeHandlerType#getSpecialListener(java.lang.Object, java.lang.Object,
     *      java.lang.String)
     */
    @Override
    protected ChangeListener<String> getSpecialListener(TextInputControl fieldObj, Object handlingObj, String cssClass, boolean withParameters, boolean passField, String regex)
    {
        if (regex.isEmpty())
        {
            throw new FxException("Missing regular expression in 'value' field of FxHandler annotation.");
        }

        if (cssClass.isEmpty())
        {
            throw new FxException("Missing css class in 'method' field of FxHandler annotation.");
        }

        Pattern regexPattern = Pattern.compile(regex);

        return (obs, ol, ne) ->
        {
            if (regexPattern.matcher(ne).matches())
            {
                CssUtils.removeStyleClass(fieldObj, cssClass);
            }
        };
    }
}