package bt.gui.fx.core.exc;

/**
 * @author &#8904
 *
 */
public class FxException extends RuntimeException
{
    /**
     * Creates a new instance without a message.
     */
    public FxException()
    {
        super();
    }

    /**
     * Creates a new instance with the given message.
     *
     * @param message
     *            The message to use.
     */
    public FxException(String message)
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
    public FxException(String message, Throwable cause)
    {
        super(message,
              cause);
    }
}