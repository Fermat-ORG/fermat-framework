package com.bitdubai.smartwallet.platform.layer._10_module.wallet_store.version_1;

import com.bitdubai.smartwallet.platform.layer._8_network_service.review.version_1.Dislikeable;
import com.bitdubai.smartwallet.platform.layer._8_network_service.review.version_1.Likeable;
import com.bitdubai.smartwallet.platform.layer._9_middleware.wallet.developer.bitdubai.version_1.engine.license.License;

import java.util.List;

/**
 * Created by ciencias on 28.12.14.
 */
public class PersonalNotInstalledWallet implements  PersonalWallet,  Likeable, Dislikeable {


    private List<License> mLicense;
}
