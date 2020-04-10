package bt.bot.exc;

/**
 * @author &#8904
 *
 */
public class BotActionFormatException extends Exception
{
    /**
     * Creates a new instance without a message.
     */
    public BotActionFormatException()
    {
        super();
    }

    /**
     * Creates a new instance with the given message.
     *
     * @param message
     *            The message to use.
     */
    public BotActionFormatException(String message)
    {
        super(message);
    }

    /**
     * Creates a new instance with the given message and cause.
     *
     * @param message
     *            The message to use.
     * @param cause
     *            The error that caused this exception.
     */
    public BotActionFormatException(String message, Throwable cause)
    {
        super(message,
              cause);
    }
}