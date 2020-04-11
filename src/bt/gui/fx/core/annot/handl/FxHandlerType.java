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
public abstract class FxHandlerType
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
            handlerSetMethod.setAccessible(true);
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
    protected abstract Class<?>[] getSetMethodParameterTypes();

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
    protected abstract Object[] createSetMethodParameters(Object handlingObj, String handlerMethodName, boolean withParameters);

    /**
     * Gets the name of the handler set method (i.e. setOnAction).
     *
     * @return
     */
    protected abstract String getHandlerSetMethodName();

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