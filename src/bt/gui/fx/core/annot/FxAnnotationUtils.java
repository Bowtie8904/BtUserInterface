package bt.gui.fx.core.annot;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import bt.gui.fx.core.annot.css.FxCssLoader;
import bt.gui.fx.core.annot.handl.FxHandler;
import bt.gui.fx.core.annot.handl.FxHandlers;
import bt.gui.fx.core.annot.setup.FxSetup;
import bt.gui.fx.core.annot.setup.FxSetups;
import bt.gui.fx.core.exc.FxException;
import bt.utils.log.Logger;
import bt.utils.refl.anot.Annotations;
import javafx.scene.Node;
import javafx.scene.Scene;

/**
 * @author &#8904
 *
 */
public final class FxAnnotationUtils
{
    public static void populateFxHandlers(Object setupObj)
    {
        for (var field : Annotations.getFieldsAnnotatedWith(setupObj.getClass(), FxHandler.class, FxHandlers.class))
        {
            FxHandler[] annotations = field.getAnnotationsByType(FxHandler.class);
            field.setAccessible(true);

            for (FxHandler annot : annotations)
            {
                try
                {
                    Object methodClassObj = null;

                    if (annot.methodClass().equals(void.class))
                    {
                        methodClassObj = setupObj;
                    }
                    else
                    {
                        var constr = annot.methodClass()
                                          .getDeclaredConstructor();

                        constr.setAccessible(true);

                        methodClassObj = constr.newInstance();
                    }

                    Object actionObj = null;

                    if (annot.property().isEmpty())
                    {
                        actionObj = field.get(setupObj);

                        if (actionObj == null)
                        {
                            throw new FxException("Attempting to register a handler to a null value.");
                        }
                    }
                    else
                    {
                        // maybe the handler should not be added to the field directly but rather to a property of it
                        Object fieldObj = field.get(setupObj);

                        if (fieldObj == null)
                        {
                            throw new FxException("Attempting to register a handler to a null value.");
                        }

                        Method propertyGetter = fieldObj.getClass().getMethod(annot.property());
                        propertyGetter.setAccessible(true);
                        actionObj = propertyGetter.invoke(fieldObj);
                    }

                    Class<?> fieldType = annot.fieldType().equals(void.class) ? field.getType() : annot.fieldType();

                    annot.type()
                         .getConstructor()
                         .newInstance()
                         .setHandlerMethod(field.get(setupObj), actionObj, methodClassObj, annot.method(), annot.withParameters(), annot.passField(), annot.value(), fieldType);
                }
                catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | FxException e1)
                {
                    Logger.global().print(e1);
                }
            }
        }
    }

    public static void loadCssClasses(Scene scene, Object setupObj)
    {
        String styleClassFile = null;

        for (var styleClass : FxCssLoader.loadCssClasses(setupObj.getClass()))
        {
            styleClassFile = "/" + styleClass + ".css";
            Logger.global().print("Loading style class '" + styleClassFile + "' for class " + setupObj.getClass().getName() + ".");
            scene.getStylesheets().add(setupObj.getClass().getResource(styleClassFile).toString());
        }
    }

    public static void setupFields(Object setupObj)
    {
        for (var field : Annotations.getFieldsAnnotatedWith(setupObj.getClass(), FxSetup.class, FxSetups.class))
        {
            FxSetup[] annotations = field.getAnnotationsByType(FxSetup.class);

            field.setAccessible(true);

            for (FxSetup annot : annotations)
            {
                if (!annot.method().isEmpty())
                {
                    callSetupMethod(setupObj, field, annot);
                }

                if (!annot.css().isEmpty())
                {
                    try
                    {
                        Node node = (Node)field.get(setupObj);

                        if (!node.getStyleClass().contains(annot.css()))
                        {
                            node.getStyleClass().add(annot.css());
                        }
                    }
                    catch (ClassCastException e)
                    {
                        throw new FxException("Only subclasses of Node can have CSS added to them automatically. {" + setupObj.getClass().getName() + "." + field.getName() + "}", e);
                    }
                    catch (IllegalArgumentException | IllegalAccessException e)
                    {
                        Logger.global().print(e);
                    }
                }
            }
        }
    }

    private static void callSetupMethod(Object setupObj, Field field, FxSetup annot)
    {
        Class<?> methodClass = annot.methodClass().equals(void.class) ? setupObj.getClass() : annot.methodClass();

        Class<?> fieldType = annot.fieldType().equals(void.class) ? field.getType() : annot.fieldType();

        try
        {
            Method setupMethod = null;

            if (annot.passField())
            {
                if (annot.value().isEmpty())
                {
                    setupMethod = methodClass.getDeclaredMethod(annot.method(), fieldType);
                }
                else
                {
                    setupMethod = methodClass.getDeclaredMethod(annot.method(), fieldType, String.class);
                }
            }
            else
            {
                if (annot.value().isEmpty())
                {
                    setupMethod = methodClass.getDeclaredMethod(annot.method());
                }
                else
                {
                    setupMethod = methodClass.getDeclaredMethod(annot.method(), String.class);
                }
            }

            if (Modifier.isStatic(setupMethod.getModifiers()))
            {
                if (annot.passField())
                {
                    if (annot.value().isEmpty())
                    {
                        setupMethod.invoke(null, field.get(setupObj));
                    }
                    else
                    {
                        setupMethod.invoke(null, field.get(setupObj), annot.value());
                    }
                }
                else
                {
                    if (annot.value().isEmpty())
                    {
                        setupMethod.invoke(null);
                    }
                    else
                    {
                        setupMethod.invoke(null, annot.value());
                    }
                }
            }
            else if (annot.methodClass().equals(void.class))
            {
                if (annot.passField())
                {
                    if (annot.value().isEmpty())
                    {
                        setupMethod.invoke(setupObj, field.get(setupObj));
                    }
                    else
                    {
                        setupMethod.invoke(setupObj, field.get(setupObj), annot.value());
                    }
                }
                else
                {
                    if (annot.value().isEmpty())
                    {
                        setupMethod.invoke(setupObj);
                    }
                    else
                    {
                        setupMethod.invoke(setupObj, annot.value());
                    }
                }
            }
            else
            {
                Object callObj = methodClass.getConstructor().newInstance();

                if (annot.passField())
                {
                    if (annot.value().isEmpty())
                    {
                        setupMethod.invoke(callObj, field.get(setupObj));
                    }
                    else
                    {
                        setupMethod.invoke(callObj, field.get(setupObj), annot.value());
                    }
                }
                else
                {
                    if (annot.value().isEmpty())
                    {
                        setupMethod.invoke(callObj);
                    }
                    else
                    {
                        setupMethod.invoke(callObj, annot.value());
                    }
                }
            }
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e)
        {
            Logger.global().print(e);
        }
    }
}