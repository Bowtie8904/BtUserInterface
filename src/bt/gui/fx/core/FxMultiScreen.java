package bt.gui.fx.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import bt.gui.fx.core.exc.FxException;
import bt.log.Log;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author &#8904
 *
 */
public abstract class FxMultiScreen extends FxScreen
{
    private Map<Class<? extends FxScreen>, FxScreen> screens;

    public FxMultiScreen()
    {
        super();
        this.screens = new HashMap<>();
    }

    public <T extends FxScreen> void addScreens(Class<T>... screenTypes)
    {
        for (Class<T> type : screenTypes)
        {
            T screen = constructScreenInstance(type);
            this.screenManager.addScreen(type, screen);
        }
    }

    public <T extends FxScreen> T getScreen(Class<T> screenType)
    {
        return (T)this.screens.get(screenType);
    }

    @Override
    public Parent load()
    {
        Log.entry();

        super.load();
        loadScreens();

        for (FxScreen screen : this.screens.values())
        {
            screen.load();
        }

        Log.exit(this.root);

        return this.root;
    }

    /**
     * @see bt.gui.fx.core.FxScreen#prepareScreen()
     */
    @Override
    protected void prepareScreen()
    {
        Log.entry();

        for (FxScreen screen : this.screens.values())
        {
            screen.prepareScreen();
        }

        Log.exit();
    }

    /**
     * @see bt.gui.fx.core.FxScreen#prepareStage(javafx.stage.Stage)
     */
    @Override
    protected void prepareStage(Stage stage)
    {
        Log.entry(stage);

        for (FxScreen screen : this.screens.values())
        {
            screen.setStage(stage);
            screen.prepareStage(stage);
        }

        setupStage(stage);

        Log.exit();
    }

    /**
     * @see bt.gui.fx.core.FxScreen#prepareScene(javafx.scene.Scene)
     */
    @Override
    protected Scene createScene(Stage stage)
    {
        Log.entry(stage);

        Scene scene = new Scene(this.root, -1, -1);

        for (FxScreen screen : this.screens.values())
        {
            screen.setScene(scene);
            screen.loadCssClasses();
            screen.setupFields();
            screen.populateFxHandlers();
            screen.setupStageListeners();
            screen.applyTexts();
        }

        setupScene(scene);
        finishSetup();

        Log.exit(scene);

        return scene;
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
            this.screens.put(screenType, screen);
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

    protected abstract void setupStage(Stage stage);

    protected abstract void setupScene(Scene scene);

    protected abstract void finishSetup();
}