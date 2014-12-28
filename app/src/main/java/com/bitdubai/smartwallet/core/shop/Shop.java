package com.bitdubai.smartwallet.core.shop;

import com.bitdubai.smartwallet.core.crypto.user.Person;

/**
 * Created by ciencias on 20.12.14.
 */
public class Shop implements Likeable, Dislikeable, Reviewable{

    private Person mManager;
    private Product[] mProducts;
    private Service[] mServices;
    private String mPicture;
}
