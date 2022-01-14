package bt.gui.fx.core.annot.handl.chang.type;

import bt.gui.fx.core.exc.FxException;
import javafx.beans.value.ChangeListener;

/**
 * @author &#8904
 *
 */
public class FxGroupStatusChange extends FxBooleanChange<Object, Boolean>
{
    @Override
    protected Class<?>[] getSetMethodParameterTypes()
    {
        return new Class[]
        {
          ChangeListener.class, String.class
        };
    }

    @Override
    protected Object[] createSetMethodParameters(Object fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters, boolean passField, String groupName,
                                                 Class<?> fieldObjType, String fieldName)
    {
        if (groupName.isEmpty())
        {
            throw new FxException("Missing group name in 'value' field of FxHandler.");
        }

        ChangeListener<Boolean> changeListener = getSpecialListener(fieldObj, handlingObj, handlerMethodName, withParameters, passField, groupName, fieldObjType, fieldName);

        if (changeListener == null)
        {
            changeListener = getDefaultListener(fieldObj, handlingObj, handlerMethodName, withParameters, passField, groupName, fieldObjType, fieldName);
        }

        return new Object[]
        {
          changeListener, groupName
        };
    }
}