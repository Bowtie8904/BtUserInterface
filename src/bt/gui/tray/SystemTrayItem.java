package bt.gui.tray;

import java.awt.TrayIcon;

/**
 * 
 * 
 * @author &#8904
 */
public interface SystemTrayItem
{
    /**
     * Gets the set configartion instance of this SystemTrayItem.
     * 
     * @return The configuration.
     */
    public SystemTraySettings getSystemTraySettings();

    /**
     * Passes a configuration object which holds settings that are mirrored in the underlying {@link TrayIcon}.
     * 
     * <p>
     * Setting a new configuration while a tray item is in the system tray can have unexpected side effects. The item
     * instance can most likely not be recovered from the tray. Implementations should handle this by either recovering
     * the item (calling {@link #openFromSystemTray()}) before setting the new configuration or by not accepting
     * configurations while it is in the system tray.
     * </p>
     * 
     * @param settings
     *            The new configuration.
     */
    public void setSystemTraySettings(SystemTraySettings settings);

    /**
     * Executes all operations to send this item to the system tray.
     */
    public void sendToSystemTray();

    /**
     * Executes all operations to recover this item from the system tray.
     */
    public void openFromSystemTray();
}