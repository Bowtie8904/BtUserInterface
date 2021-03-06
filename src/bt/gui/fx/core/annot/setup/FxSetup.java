package bt.gui.fx.core.annot.setup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows for the calling of setup methods (method, methodClass, value and passField values) and the applying of css
 * (css value) classes to a Node field during the loading of an FxScreen.
 *
 * @author &#8904
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(value = FxSetups.class)
public @interface FxSetup
{
    /**
     * The class in which the method to call is located.
     *
     * @return
     */
    Class<?> methodClass() default void.class;

    /**
     * The name of the method that should be called.
     *
     * @return
     */
    String method() default "";

    /**
     * A single css class that should be added to the node.
     *
     * @return
     */
    String css() default "";

    /**
     * Indicates whether to pass the field value itself to the configured setup method.
     *
     * @return
     */
    boolean passField() default true;

    /**
     * An additional value that will be passed to the setup method.
     *
     * @return
     */
    String value() default "";

    /**
     * The type of the field object. Used to find a fitting setup method.
     *
     * @return
     */
    Class<?> fieldType() default void.class;
}