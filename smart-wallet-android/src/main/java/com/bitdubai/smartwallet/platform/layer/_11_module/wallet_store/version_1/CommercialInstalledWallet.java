package com.bitdubai.smartwallet.platform.layer._11_module.wallet_store.version_1;

import com.bitdubai.smartwallet.platform.layer._9_network_service.review.version_1.Dislikeable;
import com.bitdubai.smartwallet.platform.layer._9_network_service.review.version_1.Likeable;
import com.bitdubai.smartwallet.platform.layer._9_network_service.review.version_1.Reviewable;
import com.bitdubai.smartwallet.platform.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.License;

import java.util.List;

/**
 * Created by ciencias on 28.12.14.
 */
public class CommercialInstalledWallet implements  CommercialWallet, Reviewable, Likeable, Dislikeable {


    private List<License> mLicense;
}

