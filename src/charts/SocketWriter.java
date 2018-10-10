package charts;

import org.thisum.sensorlist.DataObj;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketWriter implements Runnable
{
    private Socket socket;
    private boolean runThread;

    @Override
    public void run()
    {
        OutputStream os = null;
        DataOutputStream dos = null;
        double d=6.0d;
        try
        {
            socket = new Socket( "172.24.61.99", 9876 );

            os = socket.getOutputStream();
//            dos = new DataOutputStream(os);
            while( true )
            {
                d = Math.random();
                os.write(String.valueOf(d).getBytes());
//                d = ;
                System.out.println(d);
//                dos.writeInt(6);
                Thread.sleep( 100 );
            }
        }
        catch( InterruptedException e )
        {
            e.printStackTrace();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionUtil.close( os );
            ConnectionUtil.close( socket );
        }
    }
}
