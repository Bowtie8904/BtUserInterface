package bt.gui.fx.core.comp;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author Lukas Hartwig
 * @since 14.01.2022
 */
public class PercentageSizedTableColumn<T> extends TableColumn<T, String>
{
    private final DoubleProperty percentageWidth = new SimpleDoubleProperty(1);

    public PercentageSizedTableColumn()
    {
        tableViewProperty().addListener(new ChangeListener<TableView<T>>()
        {
            @Override
            public void changed(ObservableValue<? extends TableView<T>> observable, TableView<T> oldValue, TableView<T> newValue)
            {
                if (prefWidthProperty().isBound())
                {
                    prefWidthProperty().unbind();
                }
                prefWidthProperty().bind(newValue.widthProperty().multiply(PercentageSizedTableColumn.this.percentageWidth));
            }
        });
    }

    public final DoubleProperty percentageWidthProperty()
    {
        return this.percentageWidth;
    }

    public final double getPercentageWidth()
    {
        return percentageWidthProperty().get();
    }

    public final void setPercentageWidth(double value)
    {
        percentageWidthProperty().set(value / 100);
    }
}