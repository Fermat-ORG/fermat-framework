package com.bitdubai.smartwallet.core.platform.layer.lowlevel.service.shop.version_1;

import com.bitdubai.smartwallet.core.platform.system_wide.definitions.crypto_user.Person;
import com.bitdubai.smartwallet.core.platform.layer.lowlevel.service.review.version_1.Dislikeable;
import com.bitdubai.smartwallet.core.platform.layer.lowlevel.service.review.version_1.Likeable;
import com.bitdubai.smartwallet.core.platform.layer.lowlevel.service.review.version_1.Reviewable;

/**
 * Created by ciencias on 20.12.14.
 */
public class Shop implements Likeable, Dislikeable, Reviewable {

    private Person mManager;
    private Product[] mProducts;
    private Service[] mServices;
    private String mPicture;
}
