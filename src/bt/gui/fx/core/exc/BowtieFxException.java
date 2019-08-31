package bt.gui.fx.core.exc;

/**
 * @author &#8904
 *
 */
public class BowtieFxException extends RuntimeException
{
    /**
     * Creates a new instance without a message.
     */
    public BowtieFxException()
    {
        super();
    }

    /**
     * Creates a new instance with the given message.
     *
     * @param message
     *            The message to use.
     */
    public BowtieFxException(String message)
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
    public BowtieFxException(String message, Throwable cause)
    {
        super(message,
              cause);
    }
}