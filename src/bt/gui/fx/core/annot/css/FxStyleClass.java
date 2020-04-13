package bt.gui.fx.core.annot.css;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marks a field that contains information about css files.
 *
 * <p>
 * This annotation can be used in three different ways:
 * <p>
 * <b>on String fields</b>
 * <ul>
 * <li>must be static</li>
 * <li>must be initialized</li>
 * <li>value must contain the name of the css file without the .css extension</li>
 * <li>the value of the annotation is ignored</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>on non-String fields</b>
 * <ul>
 * <li>dont have to be initialized</li>
 * <li>class can contain fields that are marked with this annotation</li>
 * <li>contained marked fields can fall into both of these described categories</li>
 * <li>the value of the annotation is ignored</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>on classes</b>
 * <ul>
 * <li>annotation value must contain the class that should be searched for css information</li>
 * </ul>
 * </p>
 *
 * The annotation may only be repeated when used on a class rather than a field. <br>
 * <br>
 *
 * With this structure it is possible to create collection classes that bundle constants of css file names for specific
 * purposes. This way only required css classes will be loaded, but also only one single constant is needed for each
 * style class.
 * </p>
 *
 * @author &#8904
 */
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = FxStyleClasses.class)
public @interface FxStyleClass
{
    Class<?> value() default void.class;
}