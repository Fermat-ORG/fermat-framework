package com.bitdubai.smartwallet.core.shop;

/**
 * Created by ciencias on 20.12.14.
 */
public class Product implements Sellable, Likeable, Dislikeable, Reviewable{
    private double mPrice;
    private String mName;
    private String mDescription;
    private String mPicture;
}
