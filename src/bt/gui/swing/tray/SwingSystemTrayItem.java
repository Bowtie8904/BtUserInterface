package bt.gui.swing.tray;

import javax.swing.JFrame;

import bt.gui.tray.SystemTrayItem;
import bt.gui.tray.SystemTrayUtils;

/**
 * @author &#8904
 *
 */
public interface SwingSystemTrayItem extends SystemTrayItem
{
    /**
     * Gets the JFrame that is represented by this tray item.
     *
     * <p>
     * This frame will be made invisible when the item is sent to and made visible again when it is recovered from the
     * system tray by {@link SystemTrayUtils}.
     * </p>
     *
     * @return The frame.
     */
    public JFrame getFrame();
}