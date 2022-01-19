package bt.gui.fx.core;

import bt.gui.fx.core.annot.FxAnnotationUtils;
import bt.gui.fx.core.annot.FxmlElement;
import bt.gui.fx.core.exc.FxException;
import bt.gui.fx.core.instance.ScreenInstanceDispatcher;
import bt.io.text.intf.TextLoader;
import bt.log.Log;
import bt.reflect.annotation.Annotations;
import bt.types.Killable;
import bt.utils.Null;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.stream.Stream;

/**
 * @author &#8904
 */
public abstract class FxScreen implements Killable
{
    protected FXMLLoader loader;
    protected Parent root;
    protected Stage stage;
    protected Stage parentStage;
    protected Scene scene;
    protected FxScreenManager screenManager;
    protected double width = -1;
    protected double height = -1;
    protected int x = Integer.MIN_VALUE;
    protected int y = Integer.MIN_VALUE;
    protected boolean shouldMaximize;
    protected TextLoader textLoader;

    public FxScreen()
    {
        ScreenInstanceDispatcher.get().dispatch(this);
    }

    private Parent loadFxml(String fxmlFile) throws IOException
    {
        Log.entry(fxmlFile);
        Log.info("Trying to load FXML file '" + fxmlFile + "' for class " + getClass().getName() + ".");

        URL fxmlUrl = getClass().getResource(fxmlFile);

        // maybe the file is not inside the jar file
        // check for an external file
        if (fxmlUrl == null)
        {
            fxmlUrl = new File(fxmlFile).toURI().toURL();
        }

        this.loader = new FXMLLoader(fxmlUrl);
        Parent root = (Parent)this.loader.load();
        populateFxmlElements();

        Log.exit(root);

        return root;
    }

    protected void populateFxmlElements()
    {
        Log.entry();

        for (var field : Annotations.getFieldsAnnotatedWith(getClass(), FxmlElement.class))
        {
            FxmlElement annot = field.getAnnotation(FxmlElement.class);
            String elementID = annot.value();

            if (elementID.isEmpty())
            {
                elementID = field.getName();
            }

            field.setAccessible(true);

            try
            {
                field.set(this, getElement(field.getType(), elementID));
            }
            catch (IllegalArgumentException | IllegalAccessException e)
            {
                Log.error("Failed to set field value", e);
            }
        }

        Log.exit();
    }

    protected void populateFxComboBoxOptions()
    {
        Log.entry();

        FxAnnotationUtils.populateFxComboBoxOptions(this, this.textLoader);

        Object obj;

        for (var field : getClass().getDeclaredFields())
        {
            try
            {
                field.setAccessible(true);
                obj = field.get(this);

                if (obj != null)
                {
                    FxAnnotationUtils.populateFxComboBoxOptions(obj, this.textLoader);
                }
            }
            catch (IllegalArgumentException | IllegalAccessException e)
            {
                Log.error("Failed to populate handler", e);
            }
        }

        Log.exit();
    }

    protected void populateFxHandlers()
    {
        Log.entry();

        FxAnnotationUtils.populateFxHandlers(this);

        Object obj;

        for (var field : getClass().getDeclaredFields())
        {
            try
            {
                field.setAccessible(true);
                obj = field.get(this);

                if (obj != null)
                {
                    FxAnnotationUtils.populateFxHandlers(obj);
                }
            }
            catch (IllegalArgumentException | IllegalAccessException e)
            {
                Log.error("Failed to populate handler", e);
            }
        }

        Log.exit();
    }

    protected void loadCssClasses()
    {
        Log.entry();

        FxAnnotationUtils.loadCssClasses(this.scene, this);

        Object obj;

        for (var field : getClass().getDeclaredFields())
        {
            try
            {
                field.setAccessible(true);
                obj = field.get(this);

                if (obj != null)
                {
                    FxAnnotationUtils.loadCssClasses(this.scene, obj);
                }
            }
            catch (IllegalArgumentException | IllegalAccessException e)
            {
                Log.error("Failed to load CSS classes", e);
            }
        }

        Log.exit();
    }

    protected void applyTexts()
    {
        Log.entry();

        FxAnnotationUtils.applyText(this, this.textLoader);

        Object obj;

        for (var field : getClass().getDeclaredFields())
        {
            try
            {
                field.setAccessible(true);
                obj = field.get(this);

                if (obj != null)
                {
                    FxAnnotationUtils.applyText(obj, this.textLoader);
                }
            }
            catch (IllegalArgumentException | IllegalAccessException e)
            {
                Log.error("Failed to apply text", e);
            }
        }

        Log.exit();
    }

    protected void setupFields()
    {
        Log.entry();

        FxAnnotationUtils.setupFields(this);

        Object obj;

        for (var field : getClass().getDeclaredFields())
        {
            try
            {
                field.setAccessible(true);
                obj = field.get(this);

                if (obj != null)
                {
                    FxAnnotationUtils.setupFields(obj);
                }
            }
            catch (IllegalArgumentException | IllegalAccessException e)
            {
                Log.error("Failed to setup field", e);
            }
        }

        Log.exit();
    }

    public <T> T getElement(Class<T> type, String elementID)
    {
        Log.entry(type, elementID);

        if (this.loader == null)
        {
            throw new FxException("FXMLLoader has not been constructed yet. Call load() first.");
        }

        T element = (T)this.loader.getNamespace().get(elementID);

        if (element == null)
        {
            throw new FxException("Could not find an FX element with the fx:id '" + elementID + "'.");
        }

        Log.exit(element);

        return element;
    }

    public Parent load()
    {
        Log.entry();

        if (this.textLoader != null)
        {
            this.textLoader.load(getContextName());
        }

        if (this.root == null)
        {
            try
            {
                this.root = loadFxml(getFxmlPath().toLowerCase());
            }
            catch (IOException e)
            {
                Log.error("Failed to load FXML", e);
            }

            prepareScreen();
        }

        Log.exit(this.root);

        return this.root;
    }

    protected String getContextName()
    {
        String contextName = getClass().getSimpleName();

        if (contextName.contains("Screen"))
        {
            contextName = contextName.substring(0, contextName.contains("Screen") ? contextName.lastIndexOf("Screen") : contextName.length());
        }
        else if (contextName.contains("View"))
        {
            contextName = contextName.substring(0, contextName.contains("View") ? contextName.lastIndexOf("View") : contextName.length());
        }

        return contextName;
    }

    protected String getFxmlPath()
    {
        return this.screenManager.getFxmlBasePath() + getContextName() + ".fxml";
    }

    /**
     * @return the screenManager
     */
    public FxScreenManager getScreenManager()
    {
        return this.screenManager;
    }

    /**
     * @param fxScreenManager the screenManager to set
     */
    public void setScreenManager(FxScreenManager fxScreenManager)
    {
        this.screenManager = fxScreenManager;
    }

    public Parent getRoot()
    {
        return this.root;
    }

    public double getWidth()
    {
        return this.width;
    }

    public double getHeight()
    {
        return this.height;
    }

    public void reset()
    {
        this.root = null;
        this.loader = null;
    }

    /**
     * @return the textLoader
     */
    public TextLoader getTextLoader()
    {
        return this.textLoader;
    }

    /**
     * @param textLoader the textLoader to set
     */
    public void setTextLoader(TextLoader textLoader)
    {
        this.textLoader = textLoader;
    }

    /**
     * @return the stage
     */
    public Stage getStage()
    {
        return this.stage;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    protected void setupStageListeners()
    {
        setupIconifiedListener();
        setupMaximizedListener();
    }

    protected void setupMaximizedListener()
    {
        this.stage.maximizedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean ol, Boolean ne)
            {
                onMaximizedChange(ne);
            }
        });
    }

    public void onMaximizedChange(boolean isMaximized)
    {

    }

    protected void setupIconifiedListener()
    {
        this.stage.iconifiedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean ol, Boolean ne)
            {
                onIconifiedChange(ne);
            }
        });
    }

    public void onIconifiedChange(boolean isIconified)
    {

    }

    /**
     * @return the parentStage
     */
    public Stage getParentStage()
    {
        return this.parentStage;
    }

    /**
     * @param parentStage the parentStage to set
     */
    public void setParentStage(Stage parentStage)
    {
        this.parentStage = parentStage;
    }

    public Scene getScene()
    {
        return this.scene;
    }

    public void setScene(Scene scene)
    {
        this.scene = scene;
    }

    public void show()
    {
        this.stage.show();
    }

    public void setIcons(String... iconPaths)
    {
        setIcons(Stream.of(iconPaths)
                       .map(path -> new Image(getClass().getResourceAsStream(path)))
                       .toArray(Image[]::new));
    }

    public void setIcons(Image... icons)
    {
        Null.checkRun(this.stage, () -> this.stage.getIcons().setAll(icons));
    }

    public void setModal()
    {
        if (this.stage != null)
        {
            this.stage.initModality(Modality.APPLICATION_MODAL);

            this.stage.focusedProperty().addListener(e ->
                                                     {
                                                         if (this.stage.isFocused())
                                                         {
                                                             Null.checkRun(this.parentStage, () -> this.parentStage.toFront());
                                                             this.stage.toFront();
                                                         }
                                                     });
        }
    }

    public void ignoreCloseRequest()
    {
        Null.checkRun(this.stage, () -> this.stage.setOnCloseRequest(e -> e.consume()));
    }

    @Override
    public void kill()
    {
        Log.entry();

        Null.checkRun(this.stage, () -> this.stage.close());
        Null.checkRun(this.parentStage, () -> this.parentStage.requestFocus());

        Log.exit();
    }

    /**
     * Invoked during {@link #load()}.
     */
    protected abstract void prepareScreen();

    /**
     * Invoked by the {@link FxScreenManager} within {@link FxScreenManager#setScreen(Class, Stage, boolean)}
     */
    protected abstract void prepareStage(Stage stage);

    /**
     * Invoked by the {@link FxScreenManager} within {@link FxScreenManager#setScreen(Class, Stage, boolean)}
     */
    protected Scene createScene(Stage stage)
    {
        if (this.scene != null)
        {
            // replace old root so that it can be reused in the new scene
            this.scene.setRoot(new BorderPane());
        }

        Scene scene = null;

        if (getWidth() > 0 || getHeight() > 0)
        {
            scene = new Scene(this.root, getWidth(), getHeight());
        }
        else if (stage.getScene() != null)
        {
            scene = new Scene(this.root, stage.getScene().getWidth(), stage.getScene().getHeight());
        }
        else
        {
            scene = new Scene(this.root);
        }

        return scene;
    }

    /**
     * Method called right before this Screen is set as the active one.
     */
    protected abstract void onStart();
}