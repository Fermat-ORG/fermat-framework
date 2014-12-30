package com.bitdubai.smartwallet.core.system.module.walletfactory.factory;

import com.bitdubai.smartwallet.core.system.license.BrandUserLicense;
import com.bitdubai.smartwallet.core.system.license.SourceUserLicense;

/**
 * Created by ciencias on 28.12.14.
 */
public interface BrandedWallet extends Wallet {

    public SourceWallet getSourceWallet();
    public BrandUserLicense getRebrandLicense ();
    public SourceUserLicense getUseLicense ();

}
