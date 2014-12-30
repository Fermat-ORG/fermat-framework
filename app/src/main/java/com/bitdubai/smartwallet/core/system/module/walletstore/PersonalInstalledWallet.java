package com.bitdubai.smartwallet.core.system.module.walletstore;

import com.bitdubai.smartwallet.core.system.review.Dislikeable;
import com.bitdubai.smartwallet.core.system.review.Likeable;
import com.bitdubai.smartwallet.core.system.review.Reviewable;
import com.bitdubai.smartwallet.core.system.license.License;

import java.util.List;

/**
 * Created by ciencias on 28.12.14.
 */
public class PersonalInstalledWallet implements  PersonalWallet, Reviewable, Likeable, Dislikeable {


    private List<License> mLicense;
}
