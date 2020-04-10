package bt.gui.fx.core.instance;

import bt.runtime.evnt.Dispatcher;

/**
 * @author &#8904
 *
 */
public class ScreenInstanceDispatcher extends Dispatcher
{
    private static volatile ScreenInstanceDispatcher instance;

    public static ScreenInstanceDispatcher get()
    {
        if (instance == null)
        {
            instance = new ScreenInstanceDispatcher();
        }

        return instance;
    }
}