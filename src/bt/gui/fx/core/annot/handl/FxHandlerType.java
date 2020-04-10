package bt.gui.fx.core.annot.handl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import bt.utils.log.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author &#8904
 *
 */
public abstract class FxHandlerType
{
    public void setHandlerMethod(Object actionObj, Object handlingObj, String handlerMethodName, boolean withParameters)
    {
        try
        {
            Method handlerSetMethod = actionObj.getClass().getMethod(getHandlerSetMethodName(), getSetMethodParameterTypes());
            handlerSetMethod.invoke(actionObj, createSetMethodParameters(handlingObj, handlerMethodName, withParameters));
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            Logger.global().print(e);
        }
    }

    protected Class<?>[] getSetMethodParameterTypes()
    {
        return new Class[]
        {
          EventHandler.class
        };
    }

    protected Class<?>[] getHandlerParameterTypes()
    {
        return new Class[]
        {
          ActionEvent.class
        };
    }

    protected Object[] createSetMethodParameters(Object handlingObj, String handlerMethodName, boolean withParameters)
    {
        EventHandler eventhandler = null;
        Method handlerMethod;

        try
        {
            if (withParameters)
            {
                handlerMethod = handlingObj.getClass().getMethod(handlerMethodName, getHandlerParameterTypes());
                eventhandler = e ->
                {
                    try
                    {
                        handlerMethod.invoke(handlingObj, e);
                    }
                    catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1)
                    {
                        Logger.global().print(e1);
                    }
                };
            }
            else
            {
                handlerMethod = handlingObj.getClass().getMethod(handlerMethodName);
                eventhandler = e ->
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

    protected String getHandlerSetMethodName()
    {
        String className = getClass().getSimpleName();

        if (className.startsWith("Fx"))
        {
            className = className.replaceFirst("Fx", "set");
        }

        return className;
    }
}