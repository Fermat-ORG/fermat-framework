package com.bitdubai.fermat_draft.layer._9_network_service.shop.version_1;

import com.bitdubai.fermat_draft.layer._1_definition.crypto_user.Person;
import com.bitdubai.fermat_draft.layer._9_network_service.review.version_1.Dislikeable;
import com.bitdubai.fermat_draft.layer._9_network_service.review.version_1.Likeable;
import com.bitdubai.fermat_draft.layer._9_network_service.review.version_1.Reviewable;

/**
 * Created by ciencias on 20.12.14.
 */
public class Shop implements Likeable, Dislikeable, Reviewable {

    private Person mManager;
    private Product[] mProducts;
    private Service[] mServices;
    private String mPicture;
}
