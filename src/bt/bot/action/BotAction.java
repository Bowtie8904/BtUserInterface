package bt.bot.action;

import bt.bot.BotActionExecutor;

/**
 * @author &#8904
 *
 */
public abstract class BotAction
{
    protected String keyword;

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

    public abstract void execute(BotActionExecutor executor);
}