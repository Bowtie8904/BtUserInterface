package bt.gui.fx.core.comp;

import java.util.Objects;

/**
 * @author Lukas Hartwig
 * @since 15.01.2022
 */
public class ComboBoxOption
{
    private String text;
    private String value;

    public ComboBoxOption(String text, String value)
    {
        this.text = text;
        this.value = value;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "" + this.text;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ComboBoxOption that = (ComboBoxOption)o;
        return Objects.equals(text, that.text) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(text, value);
    }
}
