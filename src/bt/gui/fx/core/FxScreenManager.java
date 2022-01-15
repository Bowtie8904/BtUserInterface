package bt.gui.fx.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bt.gui.fx.core.exc.FxException;
import bt.gui.fx.core.instance.ApplicationStarted;
import bt.gui.fx.core.instance.ScreenInstanceDispatcher;
import bt.log.Log;
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
    protected Class<? extends FxScreen> currentScreen;
    protected Class<? extends FxScreen> previousScreen;

    public static FxScreenManager get()
    {
        return instance;
    }

    public FxScreenManager()
    {
        instance = this;
        this.screens = new HashMap<>();
        ScreenInstanceDispatcher.get().dispatch(this);
    }

    /**
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Log.entry(primaryStage);

        this.primaryStage = primaryStage;
        loadScreens();
        startApplication();
        ScreenInstanceDispatcher.get().dispatch(new ApplicationStarted(this));

        Log.exit();
    }

    public <T extends FxScreen> T getScreen(Class<T> screenType)
    {
        return getScreen(screenType, true);
    }

    public <T extends FxScreen> T getScreen(Class<T> screenType, boolean preload)
    {
        T screen = (T)this.screens.get(screenType);

        if (preload)
        {
            loadScreen(screen);
        }

        return screen;
    }

    public <T extends FxScreen> void addScreens(Class<T>... screenTypes)
    {
        for (Class<T> type : screenTypes)
        {
            addScreen(type);
        }
    }

    public <T extends FxScreen> void addScreen(Class<T> screenType)
    {
        addScreen(screenType, false);
    }

    public <T extends FxScreen> void addScreen(Class<T> screenType, boolean preload)
    {
        T screen = constructScreenInstance(screenType);
        addScreen(screenType, screen, preload);
    }

    public <T extends FxScreen> void addScreen(Class<T> screenType, T screen)
    {
        addScreen(screenType, screen, false);
    }

    public <T extends FxScreen> void addScreen(Class<T> screenType, T screen, boolean preload)
    {
        Log.entry(screenType, screen, preload);

        this.screens.put(screenType, screen);

        screen.setScreenManager(this);

        if (preload)
        {
            loadScreen(screen);
        }

        Log.exit();
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
        Log.entry(screenType, stage, forceReload);

        this.previousScreen = this.currentScreen;
        this.currentScreen = screenType;

        FxScreen screen = this.screens.get(screenType);

        if (screen == null || forceReload)
        {
            if (forceReload && screen != null)
            {
                screen.reset();
            }

            screen = constructScreenInstance(screenType);
            loadScreen(screen);
        }
        else
        {
            screen.setStage(stage);
            screen.prepareStage(stage);
            screen.setupStageListeners();
        }

        Scene scene = screen.createScene(stage);
        screen.setScene(scene);

        final FxScreen finalScreen = screen;
        stage.setOnCloseRequest(e -> finalScreen.kill());

        screen.onStart();
        stage.setScene(screen.getScene());

        finalScreen.show();

        Log.exit(finalScreen);
    }

    private <T extends FxScreen> T constructScreenInstance(Class<T> screenType)
    {
        Log.entry(screenType);

        T screen = null;

        try
        {
            Constructor<T> construct = screenType.getConstructor();
            construct.setAccessible(true);
            screen = construct.newInstance();
            addScreen(screenType, screen);
        }
        catch (InstantiationException | IllegalAccessException
               | InvocationTargetException | SecurityException e1)
        {
            Log.error("Failed to construct screen instance", e1);
        }
        catch (NoSuchMethodException noEx)
        {
            throw new FxException("Screen class must implement a constructor without arguments.");
        }

        Log.exit(screen);

        return screen;
    }

    protected <T extends FxScreen> void loadScreen(T screen)
    {
        Parent root = screen.load();
        screen.loadCssClasses();
        screen.setupFields();
        screen.populateFxHandlers();
        screen.applyTexts();
    }

    public Class<? extends FxScreen> getCurrentScreen()
    {
        return currentScreen;
    }

    public void setCurrentScreen(Class<? extends FxScreen> currentScreen)
    {
        this.currentScreen = currentScreen;
    }

    public Class<? extends FxScreen> getPreviousScreen()
    {
        return previousScreen;
    }

    public void setPreviousScreen(Class<? extends FxScreen> previousScreen)
    {
        this.previousScreen = previousScreen;
    }

    protected abstract void loadScreens();

    protected abstract void startApplication();
}