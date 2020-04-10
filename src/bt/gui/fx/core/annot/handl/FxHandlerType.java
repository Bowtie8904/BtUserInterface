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
public abstract class FxHandlerType<T extends Event>
{
    public void setHandlerMethod(Object obj, Runnable handler)
    {
        try
        {
            Method handlerSetMethod = obj.getClass().getMethod(getHandlerSetMethodName(), EventHandler.class);
            handlerSetMethod.invoke(obj, createEventHandler(handler));
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            Logger.global().print(e);
        }
    }

    protected abstract String getHandlerSetMethodName();

    protected abstract EventHandler<T> createEventHandler(Runnable handler);
}