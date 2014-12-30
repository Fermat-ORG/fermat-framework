package com.bitdubai.smartwallet.core.platform.innersystem.layer.top.module.walletstore.store;

import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.review.Dislikeable;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.review.Likeable;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.review.Reviewable;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.license.License;

import java.util.List;

/**
 * Created by ciencias on 28.12.14.
 */
public class PersonalInstalledWallet implements  PersonalWallet, Reviewable, Likeable, Dislikeable {


    private List<License> mLicense;
}
