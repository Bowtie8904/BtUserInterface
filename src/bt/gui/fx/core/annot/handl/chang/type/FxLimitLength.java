package bt.gui.fx.core.annot.handl.chang.type;

import bt.gui.fx.core.comp.BtTextField;
import javafx.beans.value.ChangeListener;

/**
 * @author &#8904
 *
 */
public class FxLimitLength extends FxStringChange<BtTextField, String>
{
    /**
     * @see bt.gui.fx.core.annot.handl.chang.FxChangeHandlerType#getSpecialListener(java.lang.Object, java.lang.Object,
     *      java.lang.String)
     */
    @Override
    protected ChangeListener<String> getSpecialListener(BtTextField fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters, boolean passField, String additionalValue)
    {
        return (obs, ol, ne) ->
        {
            if (ne.length() > fieldObj.getMaxTextLength())
            {
                fieldObj.setText(ne.substring(0, fieldObj.getMaxTextLength()));
            }
        };
    }
}