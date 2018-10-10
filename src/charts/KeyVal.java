package charts;

public class KeyVal
{
    private String key;
    private String val;

    public KeyVal(String key, String val)
    {
        this.key = key;
        this.val = val;
    }

    public KeyVal(Object key, Object val)
    {
        this.key = String.valueOf(key);
        this.val = String.valueOf(val);
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getVal()
    {
        return val;
    }

    public void setVal(String val)
    {
        this.val = val;
    }
}
