package com.ilsa.grassis.rootvo;

import com.google.gson.annotations.SerializedName;
import com.ilsa.grassis.apivo.UserVo;

/**
 * Created by Zeeshan Ali Basbasah on 5/4/2017.
 */

public class UserDataVO
{
    @SerializedName("user")
    private UserVo user;

    public UserVo getUser ()
    {
        return user;
    }

    public void setUser (UserVo user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [user = "+user+"]";
    }
}