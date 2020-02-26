package bt.bot;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import bt.utils.log.Logger;

/**
 * @author &#8904
 *
 */
public class BotKey
{
    /**
     * Filled in a static block when this class is first loaded.
     *
     * <p>
     * This list may be edited at will to extend or limit the available keys that the {@link BotActionSetReader} can
     * parse.
     * </p>
     */
    public static List<BotKey> keys;
    private String literal;
    private int code;

    static
    {
        keys = new ArrayList<>();

        Field[] fields = KeyEvent.class.getDeclaredFields();

        for (Field f : fields)
        {
            if (Modifier.isStatic(f.getModifiers())
                && Modifier.isPublic(f.getModifiers())
                && Modifier.isFinal(f.getModifiers())
                && f.getName().startsWith("VK_"))
            {
                try
                {
                    keys.add(new BotKey(f.getInt(null),
                                        KeyEvent.getKeyText(f.getInt(null))));
                }
                catch (Exception e)
                {
                    Logger.global().print(e);
                }
            }
        }
    }

    /**
     * Trys to retrieve a {@link BotKey} instance for the given case-insensitive literal.
     *
     * @param literal
     *            The key literal i.e. 'g' or 'f1'.
     * @return The {@link BotKey} instance or null if no key was found.
     */
    public static BotKey forLiteral(String literal)
    {
        var opt = keys.stream().filter(k -> k.getLiteral().equalsIgnoreCase(literal)).findAny();

        if (opt.isPresent())
        {
            return opt.get();
        }

        return null;
    }

    public BotKey(int code, String literal)
    {
        this.code = code;
        this.literal = literal;
    }

    /**
     * @return the literal
     */
    public String getLiteral()
    {
        return this.literal;
    }

    /**
     * @param literal
     *            the literal to set
     */
    public void setLiteral(String literal)
    {
        this.literal = literal;
    }

    /**
     * @return the code
     */
    public int getCode()
    {
        return this.code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(int code)
    {
        this.code = code;
    }
}