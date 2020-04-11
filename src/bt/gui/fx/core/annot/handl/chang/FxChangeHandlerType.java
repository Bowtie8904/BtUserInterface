package bt.gui.fx.core.annot.handl.chang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import bt.utils.log.Logger;
import javafx.beans.value.ChangeListener;

/**
 * @author &#8904
 *
 */
public abstract class FxChangeHandlerType extends FxHandlerType
{
    @Override
    protected Class<?>[] getSetMethodParameterTypes()
    {
        return new Class[]
        {
          ChangeListener.class
        };
    }

    @Override
    protected Object[] createSetMethodParameters(Object handlingObj, String handlerMethodName, boolean withParameters)
    {
        ChangeListener eventhandler = null;
        Method handlerMethod;

        try
        {
            if (withParameters)
            {
                handlerMethod = handlingObj.getClass().getDeclaredMethod(handlerMethodName, getHandlerParameterTypes());

                handlerMethod.setAccessible(true);

                eventhandler = (obs, oldVal, newVal) ->
                {
                    try
                    {
                        handlerMethod.invoke(handlingObj, obs, oldVal, newVal);
                    }
                    catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1)
                    {
                        Logger.global().print(e1);
                    }
                };
            }
            else
            {
                handlerMethod = handlingObj.getClass().getDeclaredMethod(handlerMethodName);

                handlerMethod.setAccessible(true);

                eventhandler = (obs, oldVal, newVal) ->
                {
                    try
                    {
                        handlerMethod.invoke(handlingObj);
                    }
                    catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1)
                    {
                        Logger.global().print(e1);
                    }
                };
            }
        }
        catch (NoSuchMethodException | SecurityException | IllegalArgumentException e)
        {
            Logger.global().print(e);
        }

        return new Object[]
        {
          eventhandler
        };
    }

    @Override
    protected String getHandlerSetMethodName()
    {
        return "addListener";
    }

    @Override
    protected abstract Class<?>[] getHandlerParameterTypes();
}