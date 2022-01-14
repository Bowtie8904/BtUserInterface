package bt.gui.fx.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
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
            screen.load();
        }

        return screen;
    }

    public <T extends FxScreen> void addScreens(Class<T>... screenTypes)
    {
        for (Class<T> type : screenTypes)
        {
            constructScreenInstance(type);
        }
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
            screen.load();
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

        FxScreen screen = this.screens.get(screenType);

        if (screen == null || forceReload)
        {
            if (forceReload && screen != null)
            {
                screen.reset();
            }

            screen = constructScreenInstance(screenType);

            Parent root = screen.load();
            screen.setStage(stage);
            screen.prepareStage(stage);
            Scene scene = new Scene(root, screen.getWidth(), screen.getHeight());
            screen.setScene(scene);
            screen.prepareScene(scene);
            screen.loadCssClasses();
            screen.setupFields();
            screen.populateFxHandlers();
            screen.setupStageListeners();
            screen.applyTexts();
        }

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

    protected abstract void loadScreens();

    protected abstract void startApplication();
}