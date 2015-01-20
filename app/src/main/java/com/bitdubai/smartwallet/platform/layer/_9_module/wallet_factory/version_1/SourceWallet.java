package com.bitdubai.smartwallet.platform.layer._9_module.wallet_factory.version_1;

import com.bitdubai.smartwallet.platform.layer._8_middleware.wallet.developer.bitdubai.version_1.engine.license.SourceForkLicense;
import com.bitdubai.smartwallet.platform.layer._8_middleware.wallet.developer.bitdubai.version_1.engine.license.SourceRebrandLicense;
import com.bitdubai.smartwallet.platform.layer._8_middleware.wallet.developer.bitdubai.version_1.engine.license.SourceUserLicense;

/**
 * Created by ciencias on 28.12.14.
 */
public interface SourceWallet extends Wallet {

    public ParentWallet getParent ();
    public SourceForkLicense getForkLicense ();
    public SourceRebrandLicense getRebrandLicense ();
    public SourceUserLicense getUseLicense ();
}
