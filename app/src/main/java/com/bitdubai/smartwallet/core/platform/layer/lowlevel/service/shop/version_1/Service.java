package com.bitdubai.smartwallet.core.platform.layer.lowlevel.service.shop.version_1;

import com.bitdubai.smartwallet.core.platform.layer.lowlevel.service.review.version_1.Dislikeable;
import com.bitdubai.smartwallet.core.platform.layer.lowlevel.service.review.version_1.Likeable;
import com.bitdubai.smartwallet.core.platform.layer.lowlevel.service.review.version_1.Reviewable;

/**
 * Created by ciencias on 20.12.14.
 */
public class Service implements Sellable, Likeable, Dislikeable, Reviewable {
    private double mPrice;
    private String mName;
    private String mDescription;
    private String mPicture;

}
