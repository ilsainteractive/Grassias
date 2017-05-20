package com.ilsa.grassis.apivo;

import com.google.gson.annotations.SerializedName;
import com.ilsa.grassis.unknow.Dispensaries;

import java.util.ArrayList;

/**
 * Created by Zeeshan Ali Basbasah on 5/4/2017.
 */


public class Favorites {
    @SerializedName("dispensaries")
    private ArrayList<Dispensaries> dispensaries;
    @SerializedName("strands")
    private Strand[] strands;

    public ArrayList<Dispensaries> getDispensaries() {
        return dispensaries;
    }

    public void setDispensaries(ArrayList<Dispensaries> dispensaries) {
        this.dispensaries = dispensaries;
    }

    public Strand[] getStrands() {
        return strands;
    }

    public void setStrands(Strand[] strands) {
        this.strands = strands;
    }

    @Override
    public String toString() {
        return "ClassPojo [dispensaries = " + dispensaries + ", strands = " + strands + "]";
    }
}
