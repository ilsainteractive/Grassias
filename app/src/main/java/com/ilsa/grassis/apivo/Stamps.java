package com.ilsa.grassis.apivo;

/**
 * Created by Zeeshan Ali Basbasah on 5/8/2017.
 */

public class Stamps
{
    private String total;

    private String progress;

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public String getProgress ()
    {
        return progress;
    }

    public void setProgress (String progress)
    {
        this.progress = progress;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [total = "+total+", progress = "+progress+"]";
    }
}

