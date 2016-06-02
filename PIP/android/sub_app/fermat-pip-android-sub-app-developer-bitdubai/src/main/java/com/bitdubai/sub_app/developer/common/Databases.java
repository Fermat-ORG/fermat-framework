package com.bitdubai.sub_app.developer.common;

/**
 * Created by mati on 2015.07.02..
 */
public class Databases {

    public static final int TYPE_PLUGIN = 1;
    public static final int TYPE_ADDON = 2;


    private static final long serialVersionUID = -7700467726058196758L;

    public String picture;

    public String databases;

    public int type;

    @Override
    public String toString() {
        return "Resource{" +
                "picture='" + picture + '\'' +
                ", databases='" + databases + '\'' +
                ", type=" + type +
                '}';
    }

}
