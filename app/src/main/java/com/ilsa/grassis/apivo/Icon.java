package com.ilsa.grassis.apivo;

/**
 * Created by Zeeshan Ali Basbasah on 5/8/2017.
 */

public class Icon
{
    private Inactive inactive;

    private Active active;

    public Inactive getInactive ()
    {
        return inactive;
    }

    public void setInactive (Inactive inactive)
    {
        this.inactive = inactive;
    }

    public Active getActive ()
    {
        return active;
    }

    public void setActive (Active active)
    {
        this.active = active;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [inactive = "+inactive+", active = "+active+"]";
    }
}

