package com.ilsa.grassis.rootvo;

import com.google.gson.annotations.SerializedName;
import com.ilsa.grassis.apivo.Reward;

/**
 * Created by Zeeshan Ali Basbasah on 5/8/2017.
 */

public class GetCards
{

    private Reward reward;

    public Reward getReward ()
    {
        return reward;
    }

    public void setReward (Reward reward)
    {
        this.reward = reward;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [reward = "+reward+"]";
    }
}
