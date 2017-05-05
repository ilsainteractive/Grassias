package com.ilsa.grassis.apivo;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Widgets {

    private String gallery;

    private String strands;

    private String find_us;

    private String id_card;

    private String deals;
    public String getGallery ()
    {
        return gallery;
    }

    public void setGallery (String gallery)
    {
        this.gallery = gallery;
    }

    public String getStrands ()
    {
        return strands;
    }

    public void setStrands (String strands)
    {
        this.strands = strands;
    }

    public String getFind_us ()
    {
        return find_us;
    }

    public void setFind_us (String find_us)
    {
        this.find_us = find_us;
    }

    public String getId_card ()
    {
        return id_card;
    }

    public void setId_card (String id_card)
    {
        this.id_card = id_card;
    }

    public String getDeals ()
    {
        return deals;
    }

    public void setDeals (String deals)
    {
        this.deals = deals;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [gallery = "+gallery+", strands = "+strands+", find_us = "+find_us+", id_card = "+id_card+", deals = "+deals+"]";
    }
}
