package com.bitdubai.smartwallet.core.platform.innersystem.layer.top.module.wallet_factory.version_1;

import com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.license.BrandUserLicense;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.license.SourceUserLicense;

/**
 * Created by ciencias on 28.12.14.
 */
public interface BrandedWallet extends Wallet {

    public SourceWallet getSourceWallet();
    public BrandUserLicense getRebrandLicense ();
    public SourceUserLicense getUseLicense ();

}
