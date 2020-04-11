package bt.gui.fx.core.annot.handl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import bt.utils.log.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * @author &#8904
 *
 */
public abstract class FxEventHandlerType
{
    /**
     * Attempts to add a handler based on the given parameters.
     *
     * @param actionObj
     *            The object that will receive a handler.
     * @param handlingObj
     *            The object that contains the handling method.
     * @param handlerMethodName
     *            The name of the handling method.
     * @param withParameters
     *            Indicates whether the handling method will use the defined parameters of the specific handler type.
     *            The parameters are defined by {@link #getHandlerParameterTypes()} in the subclasses.
     */
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

    /**
     * Gets an array of parameter types that the handler setting method expects.
     *
     * <p>
     * For example setOnAction expects an EvenHandler.
     * </p>
     *
     * @return
     */
    protected Class<?>[] getSetMethodParameterTypes()
    {
        return new Class[]
        {
          EventHandler.class
        };
    }

    /**
     * Creates an array of parameters for the set method. Usually this will be the created {@link EventHandler} that
     * calls the defined handling method.
     *
     * @param handlingObj
     *            The object that contains the handling method.
     * @param handlerMethodName
     *            The name of the handling method.
     * @param withParameters
     *            Indicates whether the handling method will use the defined parameters of the specific handler type.
     *            The parameters are defined by {@link #getHandlerParameterTypes()} in the subclasses.
     * @return
     */
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

    /**
     * Gets the name of the handler set method (i.e. setOnAction).
     *
     * <p>
     * The default implementation will use the class name and replace the 'Fx' prefix with 'set'. <br>
     * So FxOnAction will become setOnAction.
     * </p>
     *
     * @return
     */
    protected String getHandlerSetMethodName()
    {
        String className = getClass().getSimpleName();

        if (className.startsWith("Fx"))
        {
            className = className.replaceFirst("Fx", "set");
        }

        return className;
    }

    /**
     * Gets an array of parameter types that the handling method must be able to receive if it was specified that
     * parameters will be used.
     *
     * <p>
     * Usually this will be the {@link Event} type that is received by the {@link EventHandler}.
     * </p>
     *
     * @return
     */
    protected abstract Class<?>[] getHandlerParameterTypes();
}