package bt.gui.fx.core.annot.setup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Lukas Hartwig
 * @since 15.01.2022
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FxComboBoxOptions
{
    FxComboBoxOption[] value();
}