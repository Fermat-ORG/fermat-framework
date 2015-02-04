package com.bitdubai.wallet_platform_core.layer._11_module.wallet_store.version_1;

import com.bitdubai.wallet_platform_core.layer._9_network_service.review.version_1.Dislikeable;
import com.bitdubai.wallet_platform_core.layer._9_network_service.review.version_1.Likeable;
import com.bitdubai.wallet_platform_core.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.License;
import com.bitdubai.wallet_platform_plugin.layer._11_module.wallet_store.developer.bitdubai.version_1.PersonalWallet;

import java.util.List;

/**
 * Created by ciencias on 28.12.14.
 */
public class PersonalNotInstalledWallet implements PersonalWallet,  Likeable, Dislikeable {


    private List<License> mLicense;
}
