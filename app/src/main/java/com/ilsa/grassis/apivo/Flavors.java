package com.ilsa.grassis.apivo;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Flavors {
    private Flavor flavor;

    public Flavor getFlavor ()
    {
        return flavor;
    }

    public void setFlavor (Flavor flavor)
    {
        this.flavor = flavor;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [flavor = "+flavor+"]";
    }
}
