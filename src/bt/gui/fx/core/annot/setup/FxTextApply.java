package bt.gui.fx.core.annot.setup;

/**
 * @author &#8904
 *
 */
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
    int textId() default 0;
}