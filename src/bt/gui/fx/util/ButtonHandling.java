package bt.gui.fx.util;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;

/**
 * @author &#8904
 *
 */
public final class ButtonHandling
{
    public static Color HOVER_TEXT_COLOR = Color.WHITE;
    public static Color NO_HOVER_TEXT_COLOR = Color.BLACK;

    public static void onMouseEnter(Button b)
    {
        b.setTextFill(HOVER_TEXT_COLOR);
    }

    public static void onMouseExit(Button b)
    {
        b.setTextFill(NO_HOVER_TEXT_COLOR);
    }
}