package com.bitdubai.reference_wallet.fan_wallet.common.models;

import android.graphics.Bitmap;

/**
 * Created by Miguel Payarez on 16/03/16.
 * Updated by Manuel Perez on 18/04/2016.
 */
public class FollowingItems {
    private String url;
    private Bitmap image;
    private String username;
    private String description;

    /**
     * Default constructor with parameters.
     * @param image
     * @param url
     * @param username
     */
    public FollowingItems(
            Bitmap image,
            String url,
            String username){
        this.image = image;
        this.url = url;
        this.username = username;
    }

    /**
     * Default constructor with parameters.
     * @param url
     * @param image
     * @param username
     * @param description
     */
    public FollowingItems(
            Bitmap image,
            String url,
            String username,
            String description) {
        this.url = url;
        this.image = image;
        this.username = username;
        this.description = description;
    }

    /**
     * This method returns the following item image.
     * @return
     */
    public Bitmap getImage(){
        return image;
    }

    /**
     * This method returns the following item URL.
     * @return
     */
    public String getURL(){
        return url;
    }

    /**
     * This method returns the following item username.
     * @return
     */
    public String getUsername(){
        return username;
    }

    /**
     * This method returns the following item description.
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method returns the the following item description inside the <html> tags.
     * @return
     */
    public String getDescriptionHTML(){
        String descriptionHTML = this.description.replace("<p>","").replace("</p>","");
        int stringSize = descriptionHTML.length();
        if(stringSize>=100){
            stringSize=100;
        }
        descriptionHTML = "<html>"+descriptionHTML.substring(0,stringSize)+"...</html>";
        return descriptionHTML;
    }

    @Override
    public String toString() {
        return "FollowingItems{" +
                "url='" + url + '\'' +
                ", image=" + image +
                ", username='" + username + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
