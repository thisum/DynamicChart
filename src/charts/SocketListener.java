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
//        InputStream is = null;
//        ObjectInputStream ois = null;
//        DataInputStream dis = null;
//        DataObj dataObj;
//        Object o = null;
//        int lastDataCount = 0;
//        try
//        {
//            this.runThread = true;
//            int val = -1;
//            socket = new ServerSocket( 6789 );
//            Socket server = socket.accept();
//            byte[] bary = new byte[4];
//
//            is = server.getInputStream();
//            dis = new DataInputStream(is);
////            ois = new ObjectInputStream(is);
//
//            Thread.sleep(1000);
//            while( runThread )
//            {
////                o = ois.readObject();
////                if( o != null && dataObjserver != null )
////                {
////                    DataObj obj = (DataObj ) o;
////                    if(lastDataCount != obj.getCount() )
////                    {
////                        dataObjserver.onDataAvailable(obj);
////                    }
////                }
//
////
//                val = dis.readInt();
//                if( dataObjserver != null )
//                {
//                    dataObjserver.onDataAvailable(val);
//                }
//
//
//                   ;
////                dataObjserver.onDataAvailable((int)(Math.random() * 10000));
////                Thread.sleep(40);
//
////                is.read(bary);
////                System.out.println(ByteBuffer.wrap(bary, 0, 4).getInt());
////
////                Thread.sleep( 0 );
//                System.out.println("");
//            }
//        }
//        catch( Exception e )
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            ConnectionUtil.close( is );
//            ConnectionUtil.close( socket );
//        }


        try
        {
            Thread.sleep(1000);
        }
        catch( InterruptedException e )
        {
            e.printStackTrace();
        }

        int[] array = new int[]{
                2090443,2090443,2090338,2090420,2090367,2090239,2090239,2090144,2090264
                ,2090352,2090526,2090526,2090691,2090615,2090686,2090686,2090686,2090533
                ,2090262,2089960,2089827,2089827,2089751,2089654,2089654,2089577,2089577
                ,2089449,2089717,2089688,2089657,2089657,2089747,2089902,2089880,2089586
                ,2089586,2089091,2089038,2088689,2088633,2088633,2088633,
                2089244,2089244,2089322,2089001,2088630,2088169,2088169,2088169,2088169,2088219,2088158,2088158,2088367,2088440,
                2089657,2089657,2089747,2089902,2089880,2089586
                ,2089586,2089091,2089038,2088689,2088633,2088633,2088633,
                2089244,2089244,2089322,2089001,2088630,2088169,2088169,2088169,2088169,2088219,2088158,2088158,2088367,2088440,
                2088259,2087883,2087883,2087642,2087402,2087510,
                2090196,2090304,2090202,2090202,2090786,2090643,2090590,2090590,2090700,2090636,2090491,2090491,2090491
                ,2090491,2090249,2090105,2090049,2090049,2089975,
                2090786,2090643,2090590,2090590,2090700,
                2088259,2087883,2087883,2087642,2087402,2087510,
                2090196,2090304,2090202,2090202,2090786,2090643,2090590,2090590,2090700,2090636,2090491,2090491,2090491
                ,2090491,2090249,2090105,2090049,2090049,2089975,
                2090786,2090643,2090590,2090590,2090700,2090636,2090491,2090491,2090491,
                2091302,2091408,2091408,2091408,2091408,2091616,2091555,2091555,2091413
                ,2091116,2090688,2090761,2090761,2090737,2090745,2090403,2090529,2090529
                ,2090451,2090337,2090417,2090358,2090358,2090504,2090602,2090650,2090517
                ,2090517,2090518,2090340,2090248,2090120,2090120,2089838,2089847,2089620,
                2089827,2089751,2089654,2089654,2089577,2089577
                ,2089449,2089717,2089688,2089657,2089657,2089747,2089902,2089880,2089586
                ,2089586,2089091,2089038,2088689,2088633,2088633,2088633,
                2089244,2089244,2089322,2089001,2088630,2088169,2088169,2088169,2088169,2088219,2088158,2088158,2088367,2088440,
                2088259,2087883,2087883,2087642,2087402,2087510,
                2090196,2090304,2090202,2090202,2090786,2090643,2090590,2090590,2090700,2090636,2090491,2090491,2090491
                ,2090491,2090249,2090105,2090049,2090049,2089975,
                2090786,2090643,2090590,2090590,2090700,2090636,2090491,2090491,2090491,
                2091302,2091408,2091408,2091408,2091408,2091616,2091555,2091555,2091413
                ,2091116,2090688,2090761,2090761,2090737,2090745,2090403,2090529,2090529
                ,2090451,2090337,2090417,2090358,2090358
                ,2089686,2089686,2089569,2089431,2089633,2089574,2089574
                ,2090491,2090249,2090105,2090049,2090049,2089975,2089842,2089861,2089631
                ,2089631,2089644,2089580,2089453,2089484,2089484,2089397,2089531,2089484
                ,2089436,2089436,2089229,2088978,2088978,2088956,2088917,2088917,2088768
                ,2088664,2088664,2088664,2088692,2088710,2088656,2088679,2089842,2089861,2089631
                ,2089631,2089644,2089580,2089453,2089484,2089484,2089397,2089531,2089484
                ,2089436,2089436,2089229,2088978,2088978,2088956,2088917,2088917,2088768
                ,2088664,2088664,2088664,2088692,2088710,2088656,2088679,2090963,2091775,2091820,2091849,2091849,2092193,2092665,2093334,2093684,2093684,2093884,2093993,2093920,2093711,2093711,2093744,2093701,2093701,2093665,2093665,2093461,2093254,2093217,2093233,2092993,2092993,2092796,2092443,
                2090196,2090304,2090202,2090202,2090963,2091775,2091820,2091849,
                2090443,2090443,2090338,2090420,2090367,2090239,2090239,2090144,2090264
                ,2090352,2090526,2090526,2090691,2089657,2089657,2089747,2089902,2089880,2089586
                ,2089586,2089091,2089038,2088689,2088633,2088633,2088633,
                2089244,2089244,2089322,2089001,2088630,2088169,2088169,2088169,2088169,2088219,2088158,2088158,2088367,2088440,
                2088259,2087883,2087883,2087642,2087402,2087510,
                2090196,2090304,2090202,2090202,2090786,2090643,2090590,2090590,2090700,2090636,2090491,2090491,2090491
                ,2090491,2090249,2090105,2090049,2090049,2089975,
                2090786,2090643,2090590,2090590,2090700,2090615,2090686,2090686,2090686,2090533
                ,2090262,2089960,2089827,2089827,2089751,2089654,2089654,2089577,2089577,
                2089657,2089747,2089902,2089880,2089586
                ,2089586,2089091,2089038,2088689,2088633,2088633,2088633,
                2089244,2089244,2089322,2089001,2088630,2088169,2088169,2088169,2088169,2088219,2088158,2088158,2088367,2088440,
                2088259,2087883,2087883,2087642,2087402,2087510,
                2090196,2090304,2090202,2090202,2090786,2090643,2090590,2090590,2090700,2090636,2090491,2090491,2090491
                ,2090491,2090249,2090105,2090049,2090049,2089975,2089842,2089861,2089631
                ,2089631,2089644,2089580,2089453,2089484
                ,2089449,2089717,2089688,2089657,2089657,2089747,2089902,2089880,2089586
                ,2089586,2089091,2089038,2088689,2088633,2088633,2088633,2091849,2092193,2092665,2093334,2093684,2093684,2093884,2093993,2093920,2093711,2093711,2093744,2093701,2093701,2093665,2093665,2093461,2093254,2093217,2093233,2092993,2092993,2092796,2092443,
                2090298,2090073,2090196,2090304,2090202,2090202,2090963,2091775,2091820,2091849,2091849,2092193,2092665,2093334,2093684,2093684,2093884,2093993,2093920,2093711,2093711,2093744,2093701,2093701,2093665,2093665,2093461,2093254,2093217,2093233,2092993,2092993,2092796,2092443,2091918,2091786,2091786,2091513,2091234,2091092,2090856,2090856,2090590,2090159,2090031,2089894,2089894,2089475,2089505,2089475,2089096,2089096,2088708,2088514,2088252,2088126,2088126,2087858,2087427,2087427,2087427,2087427,2087412,2087413,2087424
                ,2087235,2087235,2087251,2087343,2087128,
                2093734,2093734,2094118,2094438
                ,2093792,2091027,2091027,2083939,2076624,2077023,2078281,2078281,2082879
                ,2086548,2088436,2087151,2087151,2076124,2044268,2026927,2036086,2036086,2051929,2060778,2065068,2068181,2068181,2070214,2072234,2076417,2090136,
                2090363,2090363,2090164,2090164,2090283,2090114,2089897,2089671,2089671,2089691,2089502,2089662,2089484,2089484,2089579,2089320,2089465,2089437,
                2089437,2089594,2089543,2089366,2089244,2089244,2089322,2089001,2088630,2088169,2088169,2088169,2088169,2088219,2088158,2088158,2088367,2088440,
                2088259,2087883,2087883,2087642,2087402,2087510};

        int index = 0;

        try
        {
            while(index<array.length)
            {
                dataObjserver.onDataAvailable(array[index]);
                index++;
                Thread.sleep(40);
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }


    }



    public void setDataObjserver( DataObserver dataObjserver )
    {
        this.dataObjserver = dataObjserver;
    }
}
