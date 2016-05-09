package com.bitdubai.reference_wallet.fan_wallet.common.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Miguel Payarez on 16/03/16.
 */
public class FollowingItems {
    private String artist_url;
    private String imageUrl;
    private String artist_name;

    public FollowingItems(String imageUrl, String artist_url, String artist_name){

        this.imageUrl = imageUrl;
        this.artist_url = artist_url;
        this.artist_name=artist_name;

    }

    public Bitmap getImagen(){
        URL url;
        Bitmap bmp=null;
        try {
            url = new URL(imageUrl);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bmp;}

    public String getArtist_url(){return artist_url;}

    public String getArtist_name(){return artist_name;}


}
