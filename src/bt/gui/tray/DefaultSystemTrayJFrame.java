package bt.gui.tray;

import java.awt.Frame;
import java.awt.Image;

import javax.swing.JFrame;

/**
 * @author &#8904
 *
 */
public class DefaultSystemTrayJFrame extends JFrame implements SystemTrayItem
{
    protected SystemTraySettings traySettings;

    public DefaultSystemTrayJFrame(Image trayImage)
    {
        this.traySettings = new SystemTraySettings(trayImage);
    }

    /**
     * @see bowt.gui.tray.SystemTrayItem#getFrame()
     */
    @Override
    public Frame getFrame()
    {
        return this;
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
     * @see bowt.gui.tray.SystemTrayItem#openFromSystemTray()
     */
    @Override
    public void openFromSystemTray()
    {
        SystemTrayUtils.openFromSystemTray(this);
    }

    /**
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
        this.traySettings = arg0;
    }

}
