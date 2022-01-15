package bt.gui.fx.core.annot.setup;

import java.lang.annotation.*;

/**
 * @author Lukas Hartwig
 * @since 15.01.2022
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(value = FxComboBoxOptions.class)
public @interface FxComboBoxOption
{
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

    /**
     * Determines if this option will be selected by default.
     *
     * @return
     */
    boolean selected() default false;

    /**
     * Determines the value of this option.
     * If no value is given then the textId is used as a value. If no textId is given then the text is used as a value.
     *
     * @return
     */
    String value() default "";
}