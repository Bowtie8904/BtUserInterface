package bt.gui.fx.core;

import java.io.IOException;

import bt.gui.fx.core.annot.FxAnnotationUtils;
import bt.gui.fx.core.annot.FxmlElement;
import bt.gui.fx.core.exc.FxException;
import bt.gui.fx.core.instance.ScreenInstanceDispatcher;
import bt.runtime.Killable;
import bt.utils.log.Logger;
import bt.utils.nulls.Null;
import bt.utils.refl.anot.Annotations;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author &#8904
 *
 */
public abstract class FxScreen implements Killable
{
    protected FXMLLoader loader;
    protected String screenName;
    protected Parent root;
    protected Stage stage;
    protected Stage parentStage;
    protected Scene scene;
    protected FxScreenManager screenManager;
    protected double width = -1;
    protected double height = -1;
    protected int x = -1;
    protected int y = -1;
    protected boolean shouldMaximize;

    public FxScreen()
    {
        ScreenInstanceDispatcher.get().dispatch(this);
    }

    private Parent loadFxml(String fxmlFile) throws IOException
    {
        Logger.global().print("Trying to load FXML file '" + fxmlFile + "' for class " + getClass().getName() + ".");

        this.loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = (Parent)this.loader.load();
        populateFxmlElements();

        return root;
    }

    protected void populateFxmlElements()
    {
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
                Logger.global().print(e);
            }
        }
    }

    protected void populateFxHandlers()
    {
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
                Logger.global().print(e);
            }
        }
    }

    protected void loadCssClasses()
    {
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
                Logger.global().print(e);
            }
        }
    }

    protected void setupFields()
    {
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
                Logger.global().print(e);
            }
        }
    }

    public <T> T getElement(Class<T> type, String elementID)
    {
        if (this.loader == null)
        {
            throw new FxException("FXMLLoader has not been constructed yet. Call load() first.");
        }

        T element = (T)this.loader.getNamespace().get(elementID);

        if (element == null)
        {
            throw new FxException("Could not find an FX element with the fx:id '" + elementID + "'.");
        }

        return element;
    }

    public Parent load()
    {
        if (this.root == null)
        {
            if (this.screenName == null)
            {
                String className = getClass().getSimpleName();
                this.screenName = className.substring(0, className.contains("Screen") ? className.lastIndexOf("Screen") : className.length());
            }

            try
            {
                this.root = loadFxml("/" + this.screenName.toLowerCase() + ".fxml");
            }
            catch (IOException e)
            {
                Logger.global().print(e);
            }

            prepareScreen();
        }

        return this.root;
    }

    /**
     * @return the screenManager
     */
    public FxScreenManager getScreenManager()
    {
        return this.screenManager;
    }

    /**
     * @param fxScreenManager
     *            the screenManager to set
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
     * @return the shouldMaximize
     */
    public boolean shouldMaximize()
    {
        return this.shouldMaximize;
    }

    /**
     * @param shouldMaximize
     *            the shouldMaximize to set
     */
    public void setShouldMaximize(boolean shouldMaximize)
    {
        this.shouldMaximize = shouldMaximize;
    }

    /**
     * @return the stage
     */
    public Stage getStage()
    {
        return this.stage;
    }

    /**
     * @param stage
     *            the stage to set
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
     * @param parentStage
     *            the parentStage to set
     */
    public void setParentStage(Stage parentStage)
    {
        this.parentStage = parentStage;
    }

    public void setScene(Scene scene)
    {
        this.scene = scene;
    }

    public Scene getScene()
    {
        return this.scene;
    }

    public void show()
    {
        this.stage.show();
    }

    public void setIcon(String iconPath)
    {
        Null.checkRun(this.stage, () -> this.stage.getIcons().add(new Image(getClass().getResourceAsStream(iconPath))));
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
        Null.checkRun(this.stage, () -> this.stage.close());
        Null.checkRun(this.parentStage, () -> this.parentStage.requestFocus());
    }

    /**
     * @return the x
     */
    public int getX()
    {
        return this.x;
    }

    /**
     * @param x
     *            the x to set
     */
    public void setX(int x)
    {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY()
    {
        return this.y;
    }

    /**
     * @param y
     *            the y to set
     */
    public void setY(int y)
    {
        this.y = y;
    }

    /**
     * Invoked during {@link #load()}.
     *
     * <p>
     * First prepare method to be called.
     * </p>
     */
    protected abstract void prepareScreen();

    /**
     * Invoked by the {@link FxScreenManager} within {@link FxScreenManager#setScreen(Class, Stage, boolean)}
     *
     * <p>
     * Second prepare method to be called.
     * </p>
     */
    protected abstract void prepareStage(Stage stage);

    /**
     * Invoked by the {@link FxScreenManager} within {@link FxScreenManager#setScreen(Class, Stage, boolean)}
     *
     * <p>
     * Third prepare method to be called.
     * </p>
     */
    protected abstract void prepareScene(Scene scene);
}