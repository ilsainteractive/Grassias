package com.ilsa.grassis.apivo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Strands {

    @SerializedName("strand")
    private Strand strand;

    public Strand getStrands() {
        return strand;
    }

    public void setStrands(Strand strands) {
        this.strand = strands;
    }
}
