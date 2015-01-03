package com.bitdubai.smartwallet.platform.layer._7_service.shop.version_1;

import com.bitdubai.smartwallet.platform.layer._2_definition.crypto_user.Person;
import com.bitdubai.smartwallet.platform.layer._7_service.review.version_1.Dislikeable;
import com.bitdubai.smartwallet.platform.layer._7_service.review.version_1.Likeable;
import com.bitdubai.smartwallet.platform.layer._7_service.review.version_1.Reviewable;

/**
 * Created by ciencias on 20.12.14.
 */
public class Shop implements Likeable, Dislikeable, Reviewable {

    private Person mManager;
    private Product[] mProducts;
    private Service[] mServices;
    private String mPicture;
}
