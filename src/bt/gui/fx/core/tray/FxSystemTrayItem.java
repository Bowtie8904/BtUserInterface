package bt.gui.fx.core.tray;

import bt.gui.tray.SystemTrayItem;
import javafx.stage.Stage;

/**
 * @author &#8904
 *
 */
public interface FxSystemTrayItem extends SystemTrayItem
{
    public Stage getStage();
}