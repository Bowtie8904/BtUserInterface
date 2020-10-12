package bt.gui.fx.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import bt.gui.fx.core.exc.FxException;
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
        super.load();
        loadScreens();

        for (FxScreen screen : this.screens.values())
        {
            screen.load();
        }

        return this.root;
    }

    /**
     * @see bt.gui.fx.core.FxScreen#prepareScreen()
     */
    @Override
    protected void prepareScreen()
    {
        for (FxScreen screen : this.screens.values())
        {
            screen.prepareScreen();
        }
    }

    /**
     * @see bt.gui.fx.core.FxScreen#prepareStage(javafx.stage.Stage)
     */
    @Override
    protected void prepareStage(Stage stage)
    {
        for (FxScreen screen : this.screens.values())
        {
            screen.setStage(stage);
            screen.prepareStage(stage);
        }

        setupStage(stage);
    }

    /**
     * @see bt.gui.fx.core.FxScreen#prepareScene(javafx.scene.Scene)
     */
    @Override
    protected void prepareScene(Scene scene)
    {
        for (FxScreen screen : this.screens.values())
        {
            screen.setScene(scene);
            screen.prepareScene(scene);
            screen.loadCssClasses();
            screen.setupFields();
            screen.populateFxHandlers();
            screen.setupStageListeners();
            screen.applyTexts();
        }

        setupScene(scene);
        finishSetup();
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
            e1.printStackTrace();
        }
        catch (NoSuchMethodException noEx)
        {
            throw new FxException("Screen class must implement a constructor without arguments.");
        }

        return screen;
    }

    protected abstract void loadScreens();

    protected abstract void setupStage(Stage stage);

    protected abstract void setupScene(Scene scene);

    protected abstract void finishSetup();
}