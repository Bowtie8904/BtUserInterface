package bt.gui.fx.core.annot.handl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field with information to automatically assign a method for specific event handling.
 *
 * @author &#8904
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(value = FxEventHandlers.class)
public @interface FxEventHandler
{
    /**
     * The class that contains the method to call.
     *
     * @return
     */
    Class<?> methodClass() default void.class;

    /**
     * The type of the handler.
     *
     * @return
     */
    Class<? extends FxEventHandlerType> type();

    /**
     * The method that should be called on action.
     *
     * @return
     */
    String method();

    /**
     * Whether the parameters of the given type should be used.
     *
     * If this is set to false then the method may not have any parameters.
     *
     * @return
     */
    boolean withParameters() default true;
}