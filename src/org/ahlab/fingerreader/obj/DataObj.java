package org.ahlab.fingerreader.obj;

import java.io.Serializable;
import java.util.Hashtable;

public class DataObj implements Serializable
{
    private boolean object;
    private String command;
    private Hashtable<String, String> dataTable;

    public DataObj(boolean object, String command, Hashtable<String, String> dataTable)
    {
        this.object = object;
        this.command = command;
        this.dataTable = dataTable;
    }

    public boolean isObject()
    {
        return object;
    }

    public void setObject(boolean object)
    {
        this.object = object;
    }

    public String getCommand()
    {
        return command;
    }

    public void setCommand(String command)
    {
        this.command = command;
    }

    public Hashtable<String, String> getDataTable()
    {
        return dataTable;
    }

    public void setDataTable(Hashtable<String, String> dataTable)
    {
        this.dataTable = dataTable;
    }
}