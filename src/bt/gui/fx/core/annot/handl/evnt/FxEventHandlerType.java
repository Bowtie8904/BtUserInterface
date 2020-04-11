package bt.gui.fx.core.annot.handl.evnt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import bt.utils.collections.array.Array;
import bt.utils.log.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * @author &#8904
 * @param <T>
 *
 */
public abstract class FxEventHandlerType<T, K extends Event> extends FxHandlerType<T>
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
    protected Object[] createSetMethodParameters(T fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters, boolean passField)
    {
        EventHandler<K> eventHandler = getDefaultHandler(fieldObj);
        Method handlerMethod;

        if (eventHandler == null)
        {
            try
            {
                if (withParameters)
                {
                    if (passField)
                    {
                        Class<?>[] params = Array.push(getHandlerParameterTypes(), fieldObj.getClass());
                        handlerMethod = handlingObj.getClass().getDeclaredMethod(handlerMethodName, params);

                        handlerMethod.setAccessible(true);

                        eventHandler = e ->
                        {
                            try
                            {
                                handlerMethod.invoke(handlingObj, e, fieldObj);
                            }
                            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1)
                            {
                                Logger.global().print(e1);
                            }
                        };
                    }
                    else
                    {
                        handlerMethod = handlingObj.getClass().getDeclaredMethod(handlerMethodName, getHandlerParameterTypes());

                        handlerMethod.setAccessible(true);

                        eventHandler = e ->
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
                }
                else
                {
                    if (passField)
                    {
                        handlerMethod = handlingObj.getClass().getDeclaredMethod(handlerMethodName, fieldObj.getClass());

                        handlerMethod.setAccessible(true);

                        eventHandler = e ->
                        {
                            try
                            {
                                handlerMethod.invoke(handlingObj, fieldObj);
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

                        eventHandler = e ->
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
            }
            catch (NoSuchMethodException | SecurityException | IllegalArgumentException e)
            {
                Logger.global().print(e);
            }
        }

        return new Object[]
        {
          eventHandler
        };
    }

    protected EventHandler<K> getDefaultHandler(T fieldObj)
    {
        return null;
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