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
import bt.gui.fx.core.annot.setup.FxTextApply;
import bt.gui.fx.core.exc.FxException;
import bt.io.text.intf.TextLoader;
import bt.log.Log;
import bt.reflect.annotation.Annotations;
import javafx.application.Platform;
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
        Log.entry(setupObj);

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
                            throw new FxException("Attempting to register a handler to a null value. {" + field.getType() + "}");
                        }
                    }
                    else
                    {
                        // maybe the handler should not be added to the field directly but rather to a property of it
                        Object fieldObj = field.get(setupObj);

                        if (fieldObj == null)
                        {
                            throw new FxException("Attempting to register a handler to a null value. {" + field.getType() + "}");
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
                catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                        InvocationTargetException | NoSuchMethodException | SecurityException | FxException e1)
                {
                    Log.error("Failed to populate handler", e1);
                }
            }
        }

        Log.exit();
    }

    public static void applyText(Object setupObj, TextLoader textLoader)
    {
        Log.entry(setupObj, textLoader);

        for (var field : Annotations.getFieldsAnnotatedWith(setupObj.getClass(), FxTextApply.class))
        {
            FxTextApply[] annotations = field.getAnnotationsByType(FxTextApply.class);
            field.setAccessible(true);

            for (FxTextApply annot : annotations)
            {
                try
                {
                    Object actionObj = null;

                    if (annot.property().isEmpty())
                    {
                        actionObj = field.get(setupObj);

                        if (actionObj == null)
                        {
                            throw new FxException("Attempting to apply a text to a null value. {" + field.getType() + "}");
                        }
                    }
                    else
                    {
                        // maybe the text should not be applied to the field directly but rather to a property of it
                        Object fieldObj = field.get(setupObj);

                        if (fieldObj == null)
                        {
                            throw new FxException("Attempting to apply a text to a null value. {" + field.getType() + "}");
                        }

                        Method propertyGetter = fieldObj.getClass().getMethod(annot.property());
                        propertyGetter.setAccessible(true);
                        actionObj = propertyGetter.invoke(fieldObj);
                    }

                    Method setter = actionObj.getClass().getMethod(annot.method(), String.class);
                    setter.setAccessible(true);

                    String text = annot.text();

                    if (textLoader != null && !annot.textId().isEmpty())
                    {
                        text = textLoader.get(annot.textId()).toString();
                    }

                    Object obj = actionObj;
                    String finalText = text;

                    Platform.runLater(() ->
                    {
                        try
                        {
                            setter.invoke(obj, finalText);
                        }
                        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                        {
                            Log.error("Failed to invoke text setter", e);
                        }
                    });

                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | FxException e1)
                {
                    Log.error("Failed to apply text", e1);
                }
            }
        }

        Log.exit();
    }

    public static void loadCssClasses(Scene scene, Object setupObj)
    {
        Log.entry(scene, setupObj);

        String styleClassFile = null;

        for (var styleClass : FxCssLoader.loadCssClasses(setupObj.getClass()))
        {
            styleClassFile = "/" + styleClass + ".css";
            Log.info("Loading style class '" + styleClassFile + "' for class " + setupObj.getClass().getName() + ".");
            scene.getStylesheets().add(setupObj.getClass().getResource(styleClassFile).toString());
        }

        Log.exit();
    }

    public static void setupFields(Object setupObj)
    {
        Log.entry(setupObj);

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
                        Log.error("Failed add CSS class to node", e);
                    }
                }
            }
        }

        Log.exit();
    }

    private static void callSetupMethod(Object setupObj, Field field, FxSetup annot)
    {
        Log.entry(setupObj, field, annot);

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

            setupMethod.setAccessible(true);

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
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException | InstantiationException e)
        {
            Log.error("Failed to call setup method", e);
        }

        Log.exit();
    }
}