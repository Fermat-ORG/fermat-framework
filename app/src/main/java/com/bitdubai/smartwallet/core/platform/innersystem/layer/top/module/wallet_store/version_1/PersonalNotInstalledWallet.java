package com.bitdubai.smartwallet.core.platform.innersystem.layer.top.module.wallet_store.version_1;

import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.review.version_1.Dislikeable;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.review.version_1.Likeable;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.license.License;

import java.util.List;

/**
 * Created by ciencias on 28.12.14.
 */
public class PersonalNotInstalledWallet implements  PersonalWallet,  Likeable, Dislikeable {


    private List<License> mLicense;
}
