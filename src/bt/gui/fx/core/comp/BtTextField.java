package bt.gui.fx.core.comp;

import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.IntegerProperty;
import javafx.css.CssMetaData;
import javafx.css.StyleableIntegerProperty;
import javafx.scene.control.TextField;

/**
 * @author &#8904
 *
 */
public class BtTextField extends JFXTextField
{
    private IntegerProperty maxTextLength = new StyleableIntegerProperty(Integer.MAX_VALUE)
    {
        @Override
        public CssMetaData<TextField, Number> getCssMetaData()
        {
            return null;
        }

        @Override
        public Object getBean()
        {
            return this;
        }

        @Override
        public String getName()
        {
            return "maxTextLength";
        }
    };

    public final IntegerProperty maxTextLength()
    {
        return this.maxTextLength;
    }

    public final int getMaxTextLength()
    {
        return this.maxTextLength.getValue();
    }

    public final void setMaxTextLength(int value)
    {
        this.maxTextLength.setValue(value);
    }
}