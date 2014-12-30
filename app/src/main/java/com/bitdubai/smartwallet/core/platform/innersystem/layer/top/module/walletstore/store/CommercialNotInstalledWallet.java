package com.bitdubai.smartwallet.core.platform.innersystem.layer.top.module.walletstore.store;

import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.review.Dislikeable;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.review.Likeable;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.license.License;

import java.util.List;

/**
 * Created by ciencias on 28.12.14.
 */
public class CommercialNotInstalledWallet implements  CommercialWallet,  Likeable, Dislikeable {


    private List<License> mLicense;
}