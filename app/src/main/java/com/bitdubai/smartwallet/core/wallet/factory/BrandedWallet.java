package com.bitdubai.smartwallet.core.wallet.factory;

import com.bitdubai.smartwallet.core.wallet.license.BrandUserLicense;
import com.bitdubai.smartwallet.core.wallet.license.SourceUserLicense;

/**
 * Created by ciencias on 28.12.14.
 */
public interface BrandedWallet extends Wallet {

    public SourceWallet getSourceWallet();
    public BrandUserLicense getRebrandLicense ();
    public SourceUserLicense getUseLicense ();

}
