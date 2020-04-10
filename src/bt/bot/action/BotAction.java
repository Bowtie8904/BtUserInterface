package bt.bot.action;

import bt.bot.BotActionExecutor;

/**
 * @author &#8904
 *
 */
public abstract class BotAction
{
    protected String keyword;
    protected String value;

    /**
     * @return the keyword
     */
    public String getKeyword()
    {
        return this.keyword;
    }

    /**
     * @param keyword
     *            the keyword to set
     */
    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    public abstract void execute(BotActionExecutor executor);
}