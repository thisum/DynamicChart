package charts;

import org.thisum.sensorlist.DataObj;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SocketListener implements Runnable
{
    private ServerSocket socket;
    private boolean runThread;
    private DataObserver dataObjserver;


    @Override
    public void run()
    {
        InputStream is = null;
        ObjectInputStream ois = null;
        DataInputStream dis = null;
        DataObj dataObj;
        Object o = null;
        int lastDataCount = 0;
        try
        {
            this.runThread = true;
            int val = -1;
            socket = new ServerSocket( 6789 );
            Socket server = socket.accept();
            byte[] bary = new byte[4];

            is = server.getInputStream();
            dis = new DataInputStream(is);
//
            Thread.sleep(1000);
            while( runThread )
            {
//                o = ois.readObject();
//                if( o != null && dataObjserver != null )
//                {
//                    DataObj obj = (DataObj ) o;
//                    if(lastDataCount != obj.getCount() )
//                    {
//                        dataObjserver.onDataAvailable(obj);
//                    }
//                }

//
                val = dis.readInt();
                if( dataObjserver != null )
                {
                    dataObjserver.onDataAvailable(val);
                }


                   ;
//                dataObjserver.onDataAvailable((int)(Math.random() * 10000));
//                Thread.sleep(40);

//                is.read(bary);
//                System.out.println(ByteBuffer.wrap(bary, 0, 4).getInt());
//
//                Thread.sleep( 0 );
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionUtil.close( is );
            ConnectionUtil.close( socket );
        }
    }

    public void setDataObjserver( DataObserver dataObjserver )
    {
        this.dataObjserver = dataObjserver;
    }
}
