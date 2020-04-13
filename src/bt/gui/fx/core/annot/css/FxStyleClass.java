package bt.gui.fx.core.annot.css;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field that contains information about css files.
 *
 * <p>
 * Two types of fields can be annotated with this:
 * <p>
 * <b>String fields</b>
 * <ul>
 * <li>must be static</li>
 * <li>must be initialized</li>
 * <li>value must contain the name of the css file without the .css extension</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>Non-String fields</b>
 * <ul>
 * <li>dont have to be initialized</li>
 * <li>class can contain fields that are marked with this annotation</li>
 * <li>contained marked fields can fall into both of these described categories</li>
 * </ul>
 * </p>
 *
 * With this structure it is possible to create collection classes that bundle constants of css file names for specific
 * purposes. This way only required css classes will be loaded, but also only one single constant is needed for each
 * style class.
 * </p>
 *
 * @author &#8904
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FxStyleClass
{

}