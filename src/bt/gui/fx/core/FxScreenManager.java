package bt.gui.fx.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import bt.gui.fx.core.exc.FxException;
import bt.utils.log.Logger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author &#8904
 *
 */
public abstract class FxScreenManager extends Application
{
    private static FxScreenManager instance;

    protected Stage primaryStage;
    protected Map<Class<? extends FxScreen>, FxScreen> screens;

    public static FxScreenManager get()
    {
        return instance;
    }

    public FxScreenManager()
    {
        instance = this;
        this.screens = new HashMap<>();
    }

    /**
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;
        loadScreens();
        startApplication();
    }

    public <T extends FxScreen> void addScreen(Class<T> screenType, T screen)
    {
        addScreen(screenType, screen, false);
    }

    public <T extends FxScreen> void addScreen(Class<T> screenType, T screen, boolean preload)
    {
        this.screens.put(screenType, screen);

        if (preload)
        {
            screen.load();
        }
    }

    public <T extends FxScreen> void setScreen(Class<T> screenType)
    {
        setScreen(screenType, false);
    }

    public <T extends FxScreen> void setScreen(Class<T> screenType, Stage stage)
    {
        setScreen(screenType, stage, false);
    }

    public <T extends FxScreen> void setScreen(Class<T> screenType, boolean forceReload)
    {
        setScreen(screenType, this.primaryStage, forceReload);
    }

    public <T extends FxScreen> void setScreen(Class<T> screenType, Stage stage, boolean forceReload)
    {
        FxScreen screen = this.screens.get(screenType);

        if (screen == null || forceReload)
        {
            if (forceReload && screen != null)
            {
                screen.reset();
            }

            screen = constructScreenInstance(screenType);
        }

        Parent root = screen.load();
        screen.setStage(stage);
        screen.prepareStage(stage);
        Scene scene = new Scene(root, screen.getWidth(), screen.getHeight());
        screen.setScene(scene);
        screen.prepareScene(scene);
        stage.hide();
        stage.setScene(scene);

        if (screen.shouldMaximize())
        {
            stage.setMaximized(true);
        }
        else if (screen.getParentStage() != null)
        {
            double centerXPosition = screen.getParentStage().getX() + screen.getParentStage().getWidth() / 2d;
            double centerYPosition = screen.getParentStage().getY() + screen.getParentStage().getHeight() / 2d;

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
        screen.show();
    }

    private <T extends FxScreen> T constructScreenInstance(Class<T> screenType)
    {
        T screen = null;

        try
        {
            Constructor<T> construct = screenType.getConstructor();
            construct.setAccessible(true);
            screen = construct.newInstance();
            this.screens.put(screenType, screen);
        }
        catch (InstantiationException | IllegalAccessException
               | InvocationTargetException | SecurityException e1)
        {
            Logger.global().print(e1);
        }
        catch (NoSuchMethodException noEx)
        {
            throw new FxException("Screen class must implement a constructor without arguments.");
        }

        return screen;
    }

    protected abstract void loadScreens();

    protected abstract void startApplication();
}