package bt.gui.fx.util;

import java.util.regex.Pattern;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * Provides utility methods to configure JavaFX TextFields.
 *
 * @author &#8904
 */
public final class TextFieldUtils
{
    /**
     * Configures the given textfield so that no leading spaces will be permitted.
     *
     * <p>
     * Any types space will be ignored as long as the textfield is empty. Upon pasting of text the entire text will have
     * its leading spaces stripped.
     * </p>
     *
     * @param field
     */
    public static void noLeadingSpaces(TextField field)
    {
        field.addEventFilter(KeyEvent.KEY_TYPED, e ->
        {
            if (field.getText().isEmpty() && e.getCharacter().equals(" "))
            {
                e.consume();
            }
        });

        field.textProperty().addListener((observable, oldValue, newValue) ->
        {
            field.setText(newValue.stripLeading());
        });
    }

    /**
     * Limits the content length of the given textfield to <i>maxLength</i>. Any typed character that would exceed the
     * maxLength wll be ignored. If the content exceeds the maxLEngth after text has been pasted, the entire content
     * will be trimmed to maxLength.
     *
     * @param field
     * @param maxLength
     */
    public static void limitLength(TextField field, int maxLength)
    {
        field.addEventFilter(KeyEvent.KEY_TYPED, e ->
        {
            if (field.getText().length() >= maxLength)
            {
                e.consume();
            }
        });

        field.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.length() >= maxLength)
            {
                field.setText(newValue.substring(0, maxLength));
            }
        });
    }

    /**
     * Executes the given action if the content of the given textfield is empty as a result of a content change.
     *
     * @param field
     * @param action
     */
    public static void onEmpty(TextField field, Runnable action)
    {
        field.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.isEmpty())
            {
                action.run();
            }
        });
    }

    /**
     * Executes the given action if content of the given textfield is no longer empty (but was empty before) as a result
     * of a content change.
     *
     * @param field
     * @param action
     */
    public static void onNotEmpty(TextField field, Runnable action)
    {
        field.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (oldValue.isEmpty() && !newValue.isEmpty())
            {
                action.run();
            }
        });
    }

    /**
     * Adds the given styleclass to the given textfield if the content of the given textfield is empty as a result of a
     * content change.
     *
     * @param field
     * @param styleClass
     */
    public static void onEmptyAdd(TextField field, String styleClass)
    {
        field.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.isEmpty())
            {
                field.getStyleClass().add(styleClass);
            }
        });
    }

    /**
     * Removes the given styleclass to the given textfield if the content of the given textfield is empty as a result of
     * a content change.
     *
     * @param field
     * @param styleClass
     */
    public static void onEmptyRemove(TextField field, String styleClass)
    {
        field.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.isEmpty())
            {
                field.getStyleClass().remove(styleClass);
            }
        });
    }

    /**
     * Adds the given styleclass to the given textfield if the content of the given textfield is no longer empty (but
     * was empty before) as a result content change.
     *
     * @param field
     * @param styleClass
     */
    public static void onNotEmptyAdd(TextField field, String styleClass)
    {
        field.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (oldValue.isEmpty() && !newValue.isEmpty())
            {
                field.getStyleClass().add(styleClass);
            }
        });
    }

    /**
     * Removes the given styleclass to the given textfield if the content of the given textfield is no longer empty (but
     * was empty before) as a result content change.
     *
     * @param field
     * @param styleClass
     */
    public static void onNotEmptyRemove(TextField field, String styleClass)
    {
        field.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (oldValue.isEmpty() && !newValue.isEmpty())
            {
                field.getStyleClass().remove(styleClass);
            }
        });
    }

    /**
     * Limits the content of the textfield so that only charcters that match the given regex will be allowed. Any typed
     * character that does not match the regex will be ignored. Any pasted content that does not match the regex will be
     * ignored.
     *
     * @param field
     * @param regex
     */
    public static void limitContent(TextField field, String regex)
    {
        Pattern regexPattern = Pattern.compile(regex);

        field.addEventFilter(KeyEvent.KEY_TYPED, e ->
        {
            if (!regexPattern.matcher(e.getCharacter()).matches())
            {
                e.consume();
            }
        });

        field.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (!regexPattern.matcher(newValue).matches())
            {
                field.setText(oldValue);
            }
        });
    }
}