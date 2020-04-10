package bt.gui.fx.core;

import java.io.IOException;

import bt.gui.fx.core.exc.FxException;
import bt.utils.log.Logger;
import bt.utils.refl.anot.Annotations;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author &#8904
 *
 */
public abstract class FxScreen
{
    protected FXMLLoader loader;
    protected String screenName;
    protected Parent root;
    protected Stage stage;
    protected Stage parentStage;
    protected Scene scene;
    protected double width = -1;
    protected double height = -1;
    protected boolean shouldMaximize;

    private Parent loadFxml(String fxmlFile) throws IOException
    {
        Logger.global().print("Trying to load FXML file '" + fxmlFile + "' for class " + getClass().getName() + ".");

        this.loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = (Parent)this.loader.load();
        populateFxmlElements();
        prepareScreen();

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
        }

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

    protected abstract void prepareScreen();

    protected abstract void prepareStage(Stage stage);

    protected abstract void prepareScene(Scene scene);
}