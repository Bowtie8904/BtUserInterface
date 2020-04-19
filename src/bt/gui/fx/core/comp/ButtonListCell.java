package bt.gui.fx.core.comp;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * @author &#8904
 *
 */
public class ButtonListCell<T> extends HBox
{
    private T item;

    public ButtonListCell(T item, Button... buttons)
    {
        this.item = item;
        Label label = new Label(item.toString());

        label.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(label, Priority.ALWAYS);
        getChildren().add(label);

        addButtons(buttons);
    }

    public void addButtons(Button... buttons)
    {
        for (var b : buttons)
        {
            HBox.setMargin(b, new Insets(0, 2, 0, 0));

            switch (b.getAlignment().getHpos())
            {
                case LEFT:
                    getChildren().add(0, b);
                    break;
                default:
                    getChildren().add(b);
                    break;
            }
        }
    }

    public T getItem()
    {
        return this.item;
    }
}