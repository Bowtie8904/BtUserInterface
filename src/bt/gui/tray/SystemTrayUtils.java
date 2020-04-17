package bt.gui.tray;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import bt.gui.fx.core.tray.FxSystemTrayItem;
import bt.gui.swing.tray.SwingSystemTrayItem;

/**
 * Offers static methods to work with {@link SystemTrayItem}s, i.e. sending them to and opening them from the system
 * tray.
 *
 * @author &#8904
 */
public final class SystemTrayUtils
{
    private static SystemTray tray = SystemTray.getSystemTray();

    /**
     * Sends the given instance to the system tray if it isn't already in there.
     *
     * <p>
     * The frame returned by {@link SwingSystemTrayItem#getFrame()} will be made invisible.
     * </p>
     *
     * @param trayItem
     *            The item to send to the system tray.
     */
    public static void sendToSystemTray(SwingSystemTrayItem trayItem)
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

    /**
     * Recovers the given instance from the system tray.
     *
     * <p>
     * The frame returned by {@link SwingSystemTrayItem#getFrame()} will be made visible and its state is set to
     * {@link Frame#NORMAL}.
     * </p>
     *
     * @param trayItem
     *            The item to recover from the system tray.
     */
    public static void openFromSystemTray(SwingSystemTrayItem trayItem)
    {
        trayItem.getFrame().setVisible(true);
        trayItem.getFrame().setState(Frame.NORMAL);
        tray.remove(trayItem.getSystemTraySettings().getTrayIcon());
    }

    /**
     * Sends the given instance to the system tray if it isn't already in there.
     *
     * @param trayItem
     *            The item to send to the system tray.
     */
    public static void sendToSystemTray(FxSystemTrayItem trayItem)
    {
        if (!isInTray(trayItem))
        {
            trayItem.getStage().show();

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

    /**
     * Recovers the given instance from the system tray.
     *
     * @param trayItem
     *            The item to recover from the system tray.
     */
    public static void openFromSystemTray(FxSystemTrayItem trayItem)
    {
        trayItem.getStage().hide();
        tray.remove(trayItem.getSystemTraySettings().getTrayIcon());
    }

    /**
     * Checks whether the {@link TrayIcon} configured by the {@link SystemTraySettings} of the given
     * {@link SystemTrayItem} is already present in the system tray.
     *
     * <p>
     * This method might not work correctly if the {@link SystemTraySettings} of the given item were changed while it
     * was in the system tray because the {@link SystemTray} probably wont recognize the new {@link TrayIcon}.
     * </p>
     *
     * @param trayItem
     *            The item to check.
     * @return true = item is in tray, false = item is not in tray.
     */
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