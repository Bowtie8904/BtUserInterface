package bt.gui.fx.core.annot.handl.chang.type;

import bt.gui.fx.core.exc.FxException;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextInputControl;

/**
 * @author &#8904
 *
 */
public class FxLimitLength extends FxStringChange<TextInputControl, String>
{
    /**
     * @see bt.gui.fx.core.annot.handl.chang.FxChangeHandlerType#getSpecialListener(java.lang.Object, java.lang.Object,
     *      java.lang.String)
     */
    @Override
    protected ChangeListener<String> getSpecialListener(TextInputControl fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters, boolean passField, String maxLengthValue,
                                                        Class<?> fieldObjType, String fieldName)
    {
        ChangeListener<String> listener = null;

        if (maxLengthValue.isEmpty())
        {
            throw new FxException("Missing length limitation in 'value' field of FxHandler annotation.");
        }

        try
        {
            int maxLength = Integer.parseInt(maxLengthValue);

            if (maxLength < 0)
            {
                throw new FxException("Invalid length limitation in 'value' field of FxHandler annotation. Expected positive integer and got '" + maxLengthValue + "'.");
            }

            listener = (obs, ol, ne) ->
            {
                if (ne.length() > maxLength)
                {
                    fieldObj.setText(ne.substring(0, maxLength));
                }
            };
        }
        catch (NumberFormatException e)
        {
            throw new FxException("Invalid length limitation in 'value' field of FxHandler annotation. Expected positive integer and got '" + maxLengthValue + "'.");
        }

        return listener;
    }
}