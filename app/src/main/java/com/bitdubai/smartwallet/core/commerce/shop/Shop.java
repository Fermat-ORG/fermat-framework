package com.bitdubai.smartwallet.core.commerce.shop;

import com.bitdubai.smartwallet.core.crypto.user.Person;
import com.bitdubai.smartwallet.core.system.review.Dislikeable;
import com.bitdubai.smartwallet.core.system.review.Likeable;
import com.bitdubai.smartwallet.core.system.review.Reviewable;

/**
 * Created by ciencias on 20.12.14.
 */
public class Shop implements Likeable, Dislikeable, Reviewable {

    private Person mManager;
    private Product[] mProducts;
    private Service[] mServices;
    private String mPicture;
}
