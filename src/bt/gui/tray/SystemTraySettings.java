package bt.gui.tray;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;

import bt.utils.img.ImageUtils;

/**
 * @author &#8904
 *
 */
public class SystemTraySettings
{
    public final static int TRAY_ICON_WIDTH = 16;
    public final static int TRAY_ICON_HEIGHT = 16;
    private Image trayImage;
    private TrayIcon trayIcon;
    private PopupMenu popup;

    public SystemTraySettings(Image trayImage)
    {
        this.popup = new PopupMenu();
        this.trayImage = ImageUtils.scaleImageCloseTo(ImageUtils.toBufferedImage(trayImage),
                TRAY_ICON_WIDTH,
                TRAY_ICON_HEIGHT);
        this.trayIcon = new TrayIcon(this.trayImage, "Bowtie System Tray Item", this.popup);
        this.trayIcon.addActionListener((e) ->
        {
        });
    }

    public void setToolTip(String tooltip)
    {
        this.trayIcon.setToolTip(tooltip);
    }

    public void addOption(MenuItem option)
    {
        this.popup.add(option);
    }

    public TrayIcon getTrayIcon()
    {
        return this.trayIcon;
    }
}