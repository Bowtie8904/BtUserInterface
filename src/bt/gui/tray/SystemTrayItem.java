package bt.gui.tray;

import java.awt.Frame;

/**
 * @author &#8904
 *
 */
public interface SystemTrayItem
{
    public SystemTraySettings getSystemTraySettings();

    public void setSystemTraySettings(SystemTraySettings settings);

    public void sendToSystemTray();

    public void openFromSystemTray();

    public Frame getFrame();
}