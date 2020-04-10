package bt.gui.fx.core.annot.handl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field as an JavaFX element that is configured in the views fxml file.
 *
 * @author &#8904
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FxHandler
{
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
    String method();

    /**
     * Whether the parameters of the given type should be used.
     *
     * If this is set to false then the method may not have any parameters.
     *
     * @return
     */
    boolean withParameters() default true;
}