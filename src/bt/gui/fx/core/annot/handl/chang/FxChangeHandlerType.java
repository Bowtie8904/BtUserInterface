package bt.gui.fx.core.annot.handl.chang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import bt.gui.fx.core.annot.handl.FxHandlerType;
import bt.utils.collections.array.Array;
import bt.utils.log.Logger;
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
    protected Object[] createSetMethodParameters(T fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters, boolean passField)
    {
        ChangeListener<K> changeListener = getDefaultListener(fieldObj);
        Method handlerMethod;

        if (changeListener == null)
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

                        changeListener = (obs, o, n) ->
                        {
                            try
                            {
                                handlerMethod.invoke(handlingObj, obs, o, n, fieldObj);
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

                        changeListener = (obs, o, n) ->
                        {
                            try
                            {
                                handlerMethod.invoke(handlingObj, obs, o, n);
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

                        changeListener = (obs, o, n) ->
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

                        changeListener = (obs, o, n) ->
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
          changeListener
        };
    }

    protected ChangeListener<K> getDefaultListener(T fieldObj)
    {
        return null;
    }

    @Override
    protected String getHandlerSetMethodName()
    {
        return "addListener";
    }

    @Override
    protected abstract Class<?>[] getHandlerParameterTypes();
}