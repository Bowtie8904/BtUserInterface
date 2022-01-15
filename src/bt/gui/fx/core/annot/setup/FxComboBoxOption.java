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

    boolean selected() default false;
}