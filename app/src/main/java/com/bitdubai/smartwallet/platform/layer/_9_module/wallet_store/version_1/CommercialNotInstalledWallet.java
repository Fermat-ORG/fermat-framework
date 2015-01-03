package com.bitdubai.smartwallet.platform.layer._9_module.wallet_store.version_1;

import com.bitdubai.smartwallet.platform.layer._7_network_service.review.version_1.Dislikeable;
import com.bitdubai.smartwallet.platform.layer._7_network_service.review.version_1.Likeable;
import com.bitdubai.smartwallet.platform.layer._8_middleware.wallet.version_1.license.License;

import java.util.List;

/**
 * Created by ciencias on 28.12.14.
 */
public class CommercialNotInstalledWallet implements  CommercialWallet,  Likeable, Dislikeable {


    private List<License> mLicense;
}