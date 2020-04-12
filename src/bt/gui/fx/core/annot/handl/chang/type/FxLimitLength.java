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
     * @see bt.gui.fx.core.annot.handl.chang.FxChangeHandlerType#getDefaultListener(java.lang.Object, java.lang.Object,
     *      java.lang.String)
     */
    @Override
    protected ChangeListener<String> getDefaultListener(BtTextField fieldObj, Object handlingObj, String handlerMethodName)
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