package bt.gui.fx.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;

import javafx.scene.paint.Color;

/**
 * @author &#8904
 *
 */
public final class ButtonHandling
{
    public static Color HOVER_TEXT_COLOR = Color.WHITE;
    public static Color NO_HOVER_TEXT_COLOR = Color.BLACK;

    public static ButtonType HOVER_BUTTON_TYPE = ButtonType.RAISED;
    public static ButtonType NO_HOVER_BUTTON_TYPE = ButtonType.FLAT;

    public static void onMouseEnter(JFXButton b)
    {
        b.setTextFill(HOVER_TEXT_COLOR);
        b.setButtonType(HOVER_BUTTON_TYPE);
    }

    public static void onMouseExit(JFXButton b)
    {
        b.setTextFill(NO_HOVER_TEXT_COLOR);
        b.setButtonType(NO_HOVER_BUTTON_TYPE);
    }
}