package com.bitdubai.smartwallet.core.world.commerce.shop;

import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.review.Dislikeable;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.review.Likeable;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.review.Reviewable;

/**
 * Created by ciencias on 20.12.14.
 */
public class Product implements Sellable, Likeable, Dislikeable, Reviewable {
    private double mPrice;
    private String mName;
    private String mDescription;
    private String mPicture;
}
