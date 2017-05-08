package com.ilsa.grassis.apivo;

import com.google.gson.annotations.SerializedName;
import com.ilsa.grassis.unknow.Dispensaries;

/**
 * Created by Zeeshan Ali Basbasah on 5/4/2017.
 */


public class Favorites {
    @SerializedName("dispensaries")
    private com.ilsa.grassis.unknow.Dispensaries[] dispensaries;
    @SerializedName("strands")
    private Strand[] strands;

    public Dispensaries[] getDispensaries() {
        return dispensaries;
    }

    public void setDispensaries(Dispensaries[] dispensaries) {
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