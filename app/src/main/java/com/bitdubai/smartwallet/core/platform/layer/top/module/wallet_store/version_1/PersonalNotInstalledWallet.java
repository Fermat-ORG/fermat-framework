package com.bitdubai.smartwallet.core.platform.layer.top.module.wallet_store.version_1;

import com.bitdubai.smartwallet.core.platform.layer.lowlevel.service.review.version_1.Dislikeable;
import com.bitdubai.smartwallet.core.platform.layer.lowlevel.service.review.version_1.Likeable;
import com.bitdubai.smartwallet.core.platform.layer.middleware.engine.wallet.version_1.license.License;

import java.util.List;

/**
 * Created by ciencias on 28.12.14.
 */
public class PersonalNotInstalledWallet implements  PersonalWallet,  Likeable, Dislikeable {


    private List<License> mLicense;
}
