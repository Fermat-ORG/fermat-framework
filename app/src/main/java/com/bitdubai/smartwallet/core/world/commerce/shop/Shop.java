package com.bitdubai.smartwallet.core.world.commerce.shop;

import com.bitdubai.smartwallet.core.platform.systemwide.cryptouser.Person;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.review.Dislikeable;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.review.Likeable;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.review.Reviewable;

/**
 * Created by ciencias on 20.12.14.
 */
public class Shop implements Likeable, Dislikeable, Reviewable {

    private Person mManager;
    private Product[] mProducts;
    private Service[] mServices;
    private String mPicture;
}
