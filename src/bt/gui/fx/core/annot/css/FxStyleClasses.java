package bt.gui.fx.core.annot.css;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FxStyleClasses
{
    FxStyleClass[] value();
}