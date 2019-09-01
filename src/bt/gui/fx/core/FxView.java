package bt.gui.fx.core;

import java.io.IOException;

import bt.gui.fx.core.exc.BowtieFxException;
import bt.utils.log.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author &#8904
 *
 */
public abstract class FxView
{
    protected FXMLLoader loader;
    protected String viewName;
    protected Parent root;
    protected double width = -1;
    protected double height = -1;
    protected boolean shouldMaximize;


    public FxView()
    {

    }

    private Parent loadFxml(String fxmlFile) throws IOException
    {
        Logger.global().print("Trying to load FXML file '" + fxmlFile + "' for class " + getClass().getName() + ".");

        this.loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = (Parent)this.loader.load();
        prepareView();

        return root;
    }

    public <T> T getElement(Class<T> type, String elementID)
    {
        if (this.loader == null)
        {
            throw new BowtieFxException("FXMLLoader has not been constructed yet. Call load() first.");
        }

        return (T)this.loader.getNamespace().get(elementID);
    }

    public Parent load()
    {
        if (this.root == null)
        {
            if (this.viewName == null)
            {
                String className = getClass().getSimpleName();
                this.viewName = className.substring(0, className.contains("View") ? className.lastIndexOf("View") : className.length());
            }

            try
            {
                this.root = loadFxml("/" + this.viewName.toLowerCase() + ".fxml");
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

    protected abstract void prepareView();

    protected abstract void prepareStage(Stage stage);

    protected abstract void prepareScene(Scene scene);
}