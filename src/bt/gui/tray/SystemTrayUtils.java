package bt.gui.tray;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.SystemTray;
import java.awt.TrayIcon;

/**
 * @author &#8904
 *
 */
public class SystemTrayUtils
{
    private static SystemTray tray = SystemTray.getSystemTray();

    public static void sendToSystemTray(SystemTrayItem trayItem)
    {
        if (!isInTray(trayItem))
        {
            trayItem.getFrame().setVisible(false);
            try
            {
                tray.add(trayItem.getSystemTraySettings().getTrayIcon());
            }
            catch (AWTException awe)
            {
                awe.printStackTrace();
            }
        }
    }

    public static void openFromSystemTray(SystemTrayItem trayItem)
    {
        trayItem.getFrame().setVisible(true);
        trayItem.getFrame().setState(Frame.NORMAL);
        tray.remove(trayItem.getSystemTraySettings().getTrayIcon());
    }

    public static boolean isInTray(SystemTrayItem trayItem)
    {
        for (TrayIcon trayIcon : tray.getTrayIcons())
        {
            if (trayIcon.equals(trayItem.getSystemTraySettings().getTrayIcon()))
            {
                return true;
            }
        }
        return false;
    }
}