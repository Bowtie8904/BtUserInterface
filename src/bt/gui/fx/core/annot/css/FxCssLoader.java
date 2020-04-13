package bt.gui.fx.core.annot.css;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import bt.gui.fx.core.exc.FxException;
import bt.utils.refl.anot.Annotations;

/**
 * @author &#8904
 *
 */
public final class FxCssLoader
{
    public static List<String> loadCssClasses(Class<?> type)
    {
        List<String> styleClasses = new ArrayList<>();

        for (var field : Annotations.getFieldsAnnotatedWith(type, FxStyleClass.class))
        {
            if (field.getType().equals(String.class))
            {
                styleClasses.add(loadCssClass(field));
            }
            else
            {
                styleClasses.addAll(loadCssClasses(field.getType()));
            }
        }

        return styleClasses;
    }

    public static String loadCssClass(Field field)
    {
        String styleClass = null;

        if (!Modifier.isStatic(field.getModifiers()))
        {
            throw new FxException("A field annotated with FxStyleClass must be static. "
                                  + "{" + field.getDeclaringClass().getName() + "." + field.getName() + "}");
        }

        try
        {
            field.setAccessible(true);
            Object value = field.get(null);

            if (value == null)
            {
                throw new FxException("A field annotated with FxStyleClass must be initialized with the name of the css file without the file extension. "
                                      + "{" + field.getDeclaringClass().getName() + "." + field.getName() + "}");
            }

            styleClass = value.toString();
        }
        catch (IllegalArgumentException | IllegalAccessException e)
        {
            throw new FxException("Failed to load style class. "
                                  + "{" + field.getDeclaringClass().getName() + "." + field.getName() + "}", e);
        }

        return styleClass;
    }
}