package bt.gui.fx.core.annot.handl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import bt.gui.fx.core.exc.FxException;
import bt.log.Log;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * @author &#8904
 *
 */
public abstract class FxHandlerType<T>
{
    /**
     * Attempts to add a handler based on the given parameters.
     *
     * @param fieldObj
     *            The object of the field for which a handler is created. (i. e. a TextField)
     * @param actionObj
     *            The object that will receive a handler. (i. e. The textProperty of a TextField or the TextField
     *            itself)
     * @param handlingObj
     *            The object that contains the handling method.
     * @param handlerMethodName
     *            The name of the handling method.
     * @param withParameters
     *            Indicates whether the handling method will use the defined parameters of the specific handler type.
     *            The parameters are defined by {@link #getHandlerParameterTypes()} in the subclasses.
     */
    public void setHandlerMethod(T fieldObj, Object actionObj, Object handlingObj, String handlerMethodName,
                                 boolean withParameters, boolean passField, String additionalValue, Class<?> fieldObjType, String fieldName)
    {
        Log.entry(fieldObj, actionObj, handlingObj, handlerMethodName, withParameters, passField, additionalValue, fieldObjType);

        try
        {
            Method handlerSetMethod = actionObj.getClass().getMethod(getHandlerSetMethodName(), getSetMethodParameterTypes());
            handlerSetMethod.setAccessible(true);
            handlerSetMethod.invoke(actionObj, createSetMethodParameters(fieldObj, handlingObj, handlerMethodName, withParameters, passField, additionalValue, fieldObjType, fieldName));
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            Log.error("Failed to set handler method", e);
        }

        Log.exit();
    }

    protected String determineHandlerMethodName(String handlerMethodName, String fieldName)
    {
        Log.entry(handlerMethodName, fieldName);

        String finalHandlerMethodName = null;

        if (handlerMethodName != null && !handlerMethodName.isEmpty())
        {
            finalHandlerMethodName = handlerMethodName;
        }
        else if (fieldName != null && !fieldName.isEmpty())
        {
            finalHandlerMethodName = getMethodNamePrefix() + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        }
        else
        {
            throw new FxException("Failed to determine handler method name because of invalid parameters");
        }

        Log.exit(finalHandlerMethodName);

        return finalHandlerMethodName;
    }

    protected String getMethodNamePrefix()
    {
        String prefix = getClass().getSimpleName().replace("Fx", "");
        prefix = Character.toLowerCase(prefix.charAt(0)) + prefix.substring(1);
        return prefix;
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
    protected abstract Object[] createSetMethodParameters(T fieldObj, Object handlingObj, String handlerMethodName, boolean withParameters, boolean passField, String additionalValue,
                                                          Class<?> fieldObjType, String fieldName);

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