package org.thisum.sensorlist;

import java.io.Serializable;

public class DataObj implements Serializable
{
    private double ori, a, b;
    private int count = 0;

    public DataObj(double ori, double a, double b, int count)
    {
        this.ori = ori;
        this.a = a;
        this.b = b;
        this.count = count;
    }

    public double getOri()
    {
        return ori;
    }

    public void setOri(double ori)
    {
        this.ori = ori;
    }

    public double getA()
    {
        return a;
    }

    public void setA(double a)
    {
        this.a = a;
    }

    public double getB()
    {
        return b;
    }

    public void setB(double b)
    {
        this.b = b;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }
}
