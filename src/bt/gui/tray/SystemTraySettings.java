package bt.gui.tray;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;

/**
 * A class which holds configuration settings for a {@link SystemTrayItem}.
 * 
 * @author &#8904
 */
public class SystemTraySettings
{
    private final TrayIcon trayIcon;
    private PopupMenu popup;

    /**
     * Creates a new configuration instance which mirrors its settings in a {@link TrayIcon}.
     * 
     * @param trayImage
     *            The image that should be displayed in the system tray.
     */
    public SystemTraySettings(Image trayImage)
    {
        this.trayIcon = new TrayIcon(trayImage, null, null);
        this.trayIcon.setImageAutoSize(true);
        this.trayIcon.addActionListener((e) ->
        {
        });
    }

    /**
     * Calls {@link TrayIcon#setToolTip(String)} and passes the given tooltip.
     * 
     * @see java.awt.TrayIcon#setToolTip(String)
     */
    public void setToolTip(String tooltip)
    {
        this.trayIcon.setToolTip(tooltip);
    }

    /**
     * Adds the given {@link MenuItem} to the pop-up menu of the {@link TrayIcon}.
     * 
     * @param option
     *            The option to add.
     */
    public void addOption(MenuItem option)
    {
        if (this.popup == null)
        {
            this.popup = new PopupMenu();
            this.trayIcon.setPopupMenu(this.popup);
        }
        this.popup.add(option);
    }

    /**
     * Adds a new option to the pop-up menu of the {@link TrayIcon}.
     * 
     * @param name
     *            The name of the new option in the pop-up menu.
     * @param action
     *            The action that is executed when a user clicks on the option.
     */
    public void addOption(String name, ActionListener action)
    {
        MenuItem item = new MenuItem(name);
        item.addActionListener(action);
        addOption(item);
    }

    /**
     * Sets the image that is displayed in the system tray.
     * 
     * @param image
     *            The new image.
     */
    public void setImage(Image image)
    {
        this.trayIcon.setImage(image);
    }

    /**
     * Gets the {@link TrayIcon} that uses the set values of this settings instance.
     * 
     * @return The TrayIcon.
     */
    public TrayIcon getTrayIcon()
    {
        return this.trayIcon;
    }
}