package com.bitdubai.wallet_platform_core.layer._9_network_service.shop.version_1;

import com.bitdubai.wallet_platform_core.layer._1_definition.crypto_user.Person;
import com.bitdubai.wallet_platform_core.layer._9_network_service.review.version_1.Dislikeable;
import com.bitdubai.wallet_platform_core.layer._9_network_service.review.version_1.Likeable;
import com.bitdubai.wallet_platform_core.layer._9_network_service.review.version_1.Reviewable;

/**
 * Created by ciencias on 20.12.14.
 */
public class Shop implements Likeable, Dislikeable, Reviewable {

    private Person mManager;
    private Product[] mProducts;
    private Service[] mServices;
    private String mPicture;
}
