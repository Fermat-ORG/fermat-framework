package com.bitdubai.wallet_platform_core.layer._11_module.wallet_store.version_1;

import com.bitdubai.wallet_platform_core.layer._9_network_service.review.version_1.Dislikeable;
import com.bitdubai.wallet_platform_core.layer._9_network_service.review.version_1.Likeable;
import com.bitdubai.wallet_platform_core.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.License;

import java.util.List;

/**
 * Created by ciencias on 28.12.14.
 */
public class CommercialNotInstalledWallet implements  CommercialWallet,  Likeable, Dislikeable {


    private List<License> mLicense;
}