package charts;

import java.io.Closeable;
import java.net.HttpURLConnection;

public class ConnectionUtil
{
    public static final String TAG = ConnectionUtil.class.getSimpleName();

    public static void close( Closeable closeable )
    {
        try
        {
            if( closeable != null )
            {
                closeable.close();
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public static void close( HttpURLConnection urlConnection )
    {
        try
        {
            if( urlConnection != null )
            {
                urlConnection.disconnect();
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

}
