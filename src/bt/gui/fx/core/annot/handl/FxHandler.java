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
@Repeatable(value = FxHandlers.class)
public @interface FxHandler
{
    /**
     * Whether the field should be passed to the handler method.
     *
     * The field will be the last parameter, if multiple parameters will be passed.
     *
     * @return
     */
    boolean passField() default false;

    /**
     * The name of a property getter method that should be used to optain the property which will then have the handler
     * added to it.
     *
     * @return
     */
    String property() default "";

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
    Class<? extends FxHandlerType> type();

    /**
     * The method that should be called on action.
     *
     * @return
     */
    String method() default "";

    /**
     * Whether the parameters of the given type should be used.
     *
     * If this is set to false then the method may not have any parameters.
     *
     * @return
     */
    boolean withParameters() default true;
}