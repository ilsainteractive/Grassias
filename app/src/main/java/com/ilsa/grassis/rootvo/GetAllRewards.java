package com.ilsa.grassis.rootvo;

import com.ilsa.grassis.apivo.Product;

/**
 * Created by Zeeshan Ali Basbasah on 5/8/2017.
 */

public class GetAllRewards
{
    private Product product;

    public Product getProduct ()
    {
        return product;
    }

    public void setProduct (Product product)
    {
        this.product = product;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [product = "+product+"]";
    }
}

