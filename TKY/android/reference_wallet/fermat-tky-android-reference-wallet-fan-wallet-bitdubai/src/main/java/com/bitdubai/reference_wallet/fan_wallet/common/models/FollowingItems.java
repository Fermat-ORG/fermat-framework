package com.bitdubai.reference_wallet.fan_wallet.common.models;

import android.graphics.Bitmap;

/**
 * Created by Miguel Payarez on 16/03/16.
 */
public class FollowingItems {
    private String artist_url;
    private Bitmap image;
    private String artist_name;

    public FollowingItems(Bitmap image, String artist_url, String artist_name){

        this.image = image;
        this.artist_url = artist_url;
        this.artist_name=artist_name;

    }

    public Bitmap getImagen(){ return image;}

    public String getArtist_url(){return artist_url;}

    public String getArtist_name(){return artist_name;}


}
