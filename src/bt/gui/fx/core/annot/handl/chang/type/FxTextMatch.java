package bt.gui.fx.core.annot.handl.chang.type;

import java.util.regex.Pattern;

import bt.gui.fx.core.exc.FxException;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextInputControl;

/**
 * @author &#8904
 *
 */
public class FxTextMatch extends FxStringChange<TextInputControl, String>
{
    /**
     * @see bt.gui.fx.core.annot.handl.chang.FxChangeHandlerType#getDefaultListener(java.lang.Object, java.lang.Object,
     *      java.lang.String)
     */
    @Override
    protected ChangeListener<String> getDefaultListener(TextInputControl fieldObj, Object handlingObj, String regex)
    {
        if (regex.isEmpty())
        {
            throw new FxException("Missing regular expression in 'method' field of FxHandler annotation.");
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