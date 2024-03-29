package bt.gui.fx.core.annot.handl.evnt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import bt.log.Log;
import bt.utils.Array;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

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
          EventType.class,
          EventHandler.class
        };
    }

    @Override
    protected Object[] createSetMethodParameters(T fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters,
                                                 boolean passField, String additionalValue, Class<?> fieldObjType, String fieldName)
    {
        Log.entry(fieldObj, handlingObj, handlerMethodName, withParameters, passField, additionalValue, fieldObjType);

        EventHandler<K> eventHandler = getSpecialHandler(fieldObj, handlingObj, handlerMethodName, withParameters, passField, additionalValue, fieldObjType, fieldName);

        if (eventHandler == null)
        {
            eventHandler = getDefaultHandler(fieldObj, handlingObj, handlerMethodName, withParameters, passField, additionalValue, fieldObjType, fieldName);
        }

        Object[] ret = new Object[]
                {
                        getEventType(),
                        eventHandler
                };

        Log.exit(ret);

        return ret;
    }

    /**
     * Provides the option to specify a handler that will be preferred over the normal way of constructing one.
     *
     * <p>
     * An implementation can decide if and which parameters will be utilized.
     * </p>
     *
     * @param fieldObj
     *            The value of the field that the handler is registered for.
     * @param handlingObj
     *            The object of the class that contains the handling method.
     * @param handlerMethodName
     *            The name of the handling method. Will never be null, but may be empty.
     * @return A fully usable EventHandler or null if no handler will be provided and one should be constructed
     *         normally.
     */
    protected EventHandler<K> getSpecialHandler(T fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters,
                                                boolean passField, String additionalValue, Class<?> fieldObjType, String fieldName)
    {
        return null;
    }

    protected EventHandler<K> getDefaultHandler(T fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters,
                                                boolean passField, String additionalValue, Class<?> fieldObjType, String fieldName)
    {
        Log.entry(fieldObj, handlingObj, handlerMethodName, withParameters, passField, additionalValue, fieldObjType, fieldName);

        EventHandler<K> eventHandler = null;
        Method handlerMethod;

        handlerMethodName = determineHandlerMethodName(handlerMethodName, fieldName);

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
                            Log.error("Failed to invoke handler method", e1);
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
                            Log.error("Failed to invoke handler method", e1);
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
                            Log.error("Failed to invoke handler method", e1);
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
                            Log.error("Failed to invoke handler method", e1);
                        }
                    };
                }
            }
        }
        catch (NoSuchMethodException | SecurityException | IllegalArgumentException e)
        {
            Log.error("Failed to find handler method", e);
        }

        Log.exit(eventHandler);

        return eventHandler;
    }

    @Override
    protected String getHandlerSetMethodName()
    {
        return "addEventFilter";
    }

    @Override
    protected abstract Class<?>[] getHandlerParameterTypes();

    protected abstract EventType<K> getEventType();
}