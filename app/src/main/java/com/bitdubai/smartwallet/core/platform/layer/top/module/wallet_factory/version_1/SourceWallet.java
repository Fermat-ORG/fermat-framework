package com.bitdubai.smartwallet.core.platform.layer.top.module.wallet_factory.version_1;

import com.bitdubai.smartwallet.core.platform.layer.middleware.engine.wallet.version_1.license.SourceForkLicense;
import com.bitdubai.smartwallet.core.platform.layer.middleware.engine.wallet.version_1.license.SourceRebrandLicense;
import com.bitdubai.smartwallet.core.platform.layer.middleware.engine.wallet.version_1.license.SourceUserLicense;

/**
 * Created by ciencias on 28.12.14.
 */
public interface SourceWallet extends Wallet {

    public ParentWallet getParent ();
    public SourceForkLicense getForkLicense ();
    public SourceRebrandLicense getRebrandLicense ();
    public SourceUserLicense getUseLicense ();
}
