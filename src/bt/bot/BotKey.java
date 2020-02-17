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