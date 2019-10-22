package bt.gui.fx.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import bt.gui.fx.core.exc.BowtieFxException;
import bt.utils.log.Logger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author &#8904
 *
 */
public abstract class FxViewManager extends Application
{
    private static FxViewManager instance;

    protected Stage primaryStage;
    protected Map<Class<? extends FxView>, FxView> views;

    public static FxViewManager get()
    {
        return instance;
    }

    public FxViewManager()
    {
        instance = this;
        this.views = new HashMap<>();
    }

    /**
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;
        loadViews();
        startApplication();
    }

    public <T extends FxView> void addView(Class<T> viewType, T view)
    {
        addView(viewType, view, false);
    }

    public <T extends FxView> void addView(Class<T> viewType, T view, boolean preload)
    {
        this.views.put(viewType, view);

        if (preload)
        {
            view.load();
        }
    }

    public <T extends FxView> void setView(Class<T> viewType)
    {
        setView(viewType, false);
    }

    public <T extends FxView> void setView(Class<T> viewType, Stage stage)
    {
        setView(viewType, stage, false);
    }

    public <T extends FxView> void setView(Class<T> viewType, boolean forceReload)
    {
        setView(viewType, this.primaryStage, forceReload);
    }

    public <T extends FxView> void setView(Class<T> viewType, Stage stage, boolean forceReload)
    {
        FxView view = this.views.get(viewType);

        if (view == null || forceReload)
        {
            if (forceReload && view != null)
            {
                view.reset();
            }

            view = constructViewInstance(viewType);
        }

        Parent root = view.load();
        view.setStage(stage);
        view.prepareStage(stage);
        Scene scene = new Scene(root, view.getWidth(), view.getHeight());
        view.prepareScene(scene);
        stage.hide();
        stage.setScene(scene);

        if (view.shouldMaximize())
        {
            stage.setMaximized(true);
        }
        else if (view.getParentStage() != null)
        {
            double centerXPosition = view.getParentStage().getX() + view.getParentStage().getWidth() / 2d;
            double centerYPosition = view.getParentStage().getY() + view.getParentStage().getHeight() / 2d;

            stage.setOnShowing(ev -> stage.hide());

            stage.setOnShown(ev ->
            {
                stage.setX(centerXPosition - stage.getWidth() / 2d);
                stage.setY(centerYPosition - stage.getHeight() / 2d);
                stage.show();
            });
        }
        else
        {
            stage.setMaximized(false);
            stage.centerOnScreen();
        }
        view.show();
    }

    private <T extends FxView> T constructViewInstance(Class<T> viewType)
    {
        T view = null;

        try
        {
            Constructor<T> construct = viewType.getConstructor();
            construct.setAccessible(true);
            view = construct.newInstance();
            this.views.put(viewType, view);
        }
        catch (InstantiationException | IllegalAccessException
               | InvocationTargetException | SecurityException e1)
        {
            Logger.global().print(e1);
        }
        catch (NoSuchMethodException noEx)
        {
            throw new BowtieFxException("View class must implement a constructor without arguments.");
        }

        return view;
    }

    protected abstract void loadViews();

    protected abstract void startApplication();
}