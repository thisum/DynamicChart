package charts;

import org.thisum.sensorlist.DataObj;

public interface DataObserver
{
    public void onDataAvailable(DataObj dataObj);

    public void onDataAvailable(int d);
}
