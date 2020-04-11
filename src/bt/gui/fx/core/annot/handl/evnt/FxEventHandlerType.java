package bt.gui.fx.core.annot.handl.evnt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import bt.utils.log.Logger;
import javafx.event.EventHandler;

/**
 * @author &#8904
 *
 */
public abstract class FxEventHandlerType extends FxHandlerType
{
    @Override
    protected Class<?>[] getSetMethodParameterTypes()
    {
        return new Class[]
        {
          EventHandler.class
        };
    }

    @Override
    protected Object[] createSetMethodParameters(Object handlingObj, String handlerMethodName, boolean withParameters)
    {
        EventHandler eventhandler = null;
        Method handlerMethod;

        try
        {
            if (withParameters)
            {
                handlerMethod = handlingObj.getClass().getDeclaredMethod(handlerMethodName, getHandlerParameterTypes());

                handlerMethod.setAccessible(true);

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
                handlerMethod = handlingObj.getClass().getDeclaredMethod(handlerMethodName);

                handlerMethod.setAccessible(true);

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

    @Override
    protected String getHandlerSetMethodName()
    {
        String className = getClass().getSimpleName();

        if (className.startsWith("Fx"))
        {
            className = className.replaceFirst("Fx", "set");
        }

        return className;
    }

    @Override
    protected abstract Class<?>[] getHandlerParameterTypes();
}