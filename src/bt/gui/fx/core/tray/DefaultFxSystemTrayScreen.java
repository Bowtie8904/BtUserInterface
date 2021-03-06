package bt.gui.fx.core.tray;

import java.awt.Image;

import bt.gui.fx.core.FxScreen;
import bt.gui.tray.SystemTrayItem;
import bt.gui.tray.SystemTraySettings;
import bt.gui.tray.SystemTrayUtils;

/**
 * A basic implementation of the {@link SystemTrayItem} interface.
 *
 * @author &#8904
 */
public abstract class DefaultFxSystemTrayScreen extends FxScreen implements FxSystemTrayItem
{
    protected SystemTraySettings traySettings;

    /**
     * Creates a new instance.
     *
     * <p>
     * This will pass the given image to a new {@link SystemTraySettings} instance. To adjust the configuration either
     * modify the settings object returned by {@link #getSystemTraySettings()} or pass a new one to
     * {@link #setSystemTraySettings(SystemTraySettings)}.
     * </p>
     *
     * @param trayImage
     *            The image that should be displayed in the system tray.
     */
    public DefaultFxSystemTrayScreen(Image trayImage)
    {
        this.traySettings = new SystemTraySettings(trayImage);
    }

    /**
     * @see bowt.gui.tray.SystemTrayItem#getSystemTraySettings()
     */
    @Override
    public SystemTraySettings getSystemTraySettings()
    {
        return this.traySettings;
    }

    /**
     * A simple call to {@link SystemTrayUtils#openFromSystemTray(FxSystemTrayItem)}.
     *
     * @see bowt.gui.tray.SystemTrayItem#openFromSystemTray()
     */
    @Override
    public void openFromSystemTray()
    {
        SystemTrayUtils.openFromSystemTray(this);
    }

    /**
     * A simple call to {@link SystemTrayUtils#sendToSystemTray(FxSystemTrayItem)}.
     *
     * @see bowt.gui.tray.SystemTrayItem#sendToSystemTray()
     */
    @Override
    public void sendToSystemTray()
    {
        SystemTrayUtils.sendToSystemTray(this);
    }

    /**
     * @see bowt.gui.tray.SystemTrayItem#setSystemTraySettings(bowt.gui.tray.SystemTraySettings)
     */
    @Override
    public void setSystemTraySettings(SystemTraySettings arg0)
    {
        if (SystemTrayUtils.isInTray(this))
        {
            throw new IllegalStateException("Can't change the configuration of an item that is currently in the system tray.");
        }
        else
        {
            this.traySettings = arg0;
        }
    }
}