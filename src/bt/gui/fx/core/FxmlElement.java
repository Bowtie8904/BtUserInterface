package bt.gui.fx.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author &#8904
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FxmlElement
{
    /**
     * The fx:id of this element.
     *
     * @return
     */
    String value() default "";
}