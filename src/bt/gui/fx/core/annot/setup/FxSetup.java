package bt.gui.fx.core.annot.setup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author &#8904
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(value = FxSetups.class)
public @interface FxSetup
{
    Class<?> methodClass() default void.class;

    String method() default "";

    String css() default "";

    boolean passField() default true;
}