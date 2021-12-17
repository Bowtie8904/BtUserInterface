package bt.gui.fx.core.annot.setup;

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
public @interface FxTextApply
{
    /**
     * The method that will be used to apply the text.
     *
     * @return
     */
    String method() default "setText";

    /**
     * The name of a property getter method that should be used to optain the property which will then have the text
     * applied to it.
     *
     * @return
     */
    String property() default "";

    /**
     * The literal text that will be applied if no textId is defined.
     *
     * @return
     */
    String text() default "";

    /**
     * The textId to retrieve the text from the screens textLoader. Will be preferred over a literal text value.
     *
     * @return
     */
    String textId() default "";
}