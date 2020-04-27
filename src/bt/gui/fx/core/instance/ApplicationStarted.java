package bt.gui.fx.core.instance;

import bt.gui.fx.core.FxScreenManager;

/**
 * @author &#8904
 *
 */
public class ApplicationStarted
{
    private FxScreenManager screenManager;

    public ApplicationStarted(FxScreenManager screenManager)
    {
        this.screenManager = screenManager;
    }

    /**
     * @return the screenManager
     */
    public FxScreenManager getScreenManager()
    {
        return this.screenManager;
    }
}