package com.bitdubai.smartwallet.platform.layer._9_module.wallet_factory.version_1;

import com.bitdubai.smartwallet.platform.layer._8_middleware.wallet.version_1.license.BrandUserLicense;
import com.bitdubai.smartwallet.platform.layer._8_middleware.wallet.version_1.license.SourceUserLicense;

/**
 * Created by ciencias on 28.12.14.
 */
public interface BrandedWallet extends Wallet {

    public SourceWallet getSourceWallet();
    public BrandUserLicense getRebrandLicense ();
    public SourceUserLicense getUseLicense ();

}
