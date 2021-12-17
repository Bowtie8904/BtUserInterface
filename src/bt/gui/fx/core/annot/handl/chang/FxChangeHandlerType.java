package bt.gui.fx.core.annot.handl.chang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import bt.log.Log;
import bt.utils.Array;
import javafx.beans.value.ChangeListener;

/**
 * @author &#8904
 *
 */
public abstract class FxChangeHandlerType<T, K> extends FxHandlerType<T>
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
    protected Object[] createSetMethodParameters(T fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters,
                                                 boolean passField, String additionalValue, Class<?> fieldObjType)
    {
        Log.entry(fieldObj, handlingObj, handlerMethodName, withParameters, passField, additionalValue, fieldObjType);

        ChangeListener<K> changeListener = getSpecialListener(fieldObj, handlingObj, handlerMethodName, withParameters, passField, additionalValue, fieldObjType);

        if (changeListener == null)
        {
            changeListener = getDefaultListener(fieldObj, handlingObj, handlerMethodName, withParameters, passField, additionalValue, fieldObjType);
        }

        Object[] ret = new Object[]
                {
                        changeListener
                };

        Log.exit(ret);

        return ret;
    }

    /**
     * Provides the option to specify a listener that will be preferred over the normal way of constructing one.
     *
     * <p>
     * An implementation can decide if and which parameters will be utilized.
     * </p>
     *
     * @param fieldObj
     *            The value of the field that the listener is registered for.
     * @param handlingObj
     *            The object of the class that contains the handling method.
     * @param handlerMethodName
     *            The name of the handling method. Will never be null, but may be empty.
     * @return A fully usable ChangeListener or null if no listener will be provided and one should be constructed
     *         normally.
     */
    protected ChangeListener<K> getSpecialListener(T fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters, boolean passField, String additionalValue, Class<?> fieldObjType)
    {
        return null;
    }

    protected ChangeListener<K> getDefaultListener(T fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters, boolean passField, String additionalValue, Class<?> fieldObjType)
    {
        Log.entry(fieldObj, handlingObj, handlerMethodName, withParameters, passField, additionalValue, fieldObjType);

        ChangeListener<K> changeListener = null;

        Method handlerMethod;

        try
        {
            if (withParameters)
            {
                if (passField)
                {
                    Class<?>[] params = Array.push(getHandlerParameterTypes(), fieldObjType);
                    handlerMethod = handlingObj.getClass().getDeclaredMethod(handlerMethodName, params);

                    handlerMethod.setAccessible(true);

                    changeListener = (obs, o, n) ->
                    {
                        try
                        {
                            handlerMethod.invoke(handlingObj, obs, o, n, fieldObj);
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

                    changeListener = (obs, o, n) ->
                    {
                        try
                        {
                            handlerMethod.invoke(handlingObj, obs, o, n);
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
                    handlerMethod = handlingObj.getClass().getDeclaredMethod(handlerMethodName, fieldObjType);

                    handlerMethod.setAccessible(true);

                    changeListener = (obs, o, n) ->
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

                    changeListener = (obs, o, n) ->
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

        Log.exit(changeListener);

        return changeListener;
    }

    @Override
    protected String getHandlerSetMethodName()
    {
        return "addListener";
    }

    @Override
    protected abstract Class<?>[] getHandlerParameterTypes();
}