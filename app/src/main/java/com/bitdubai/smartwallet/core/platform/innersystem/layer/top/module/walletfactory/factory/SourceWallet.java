package com.bitdubai.smartwallet.core.platform.innersystem.layer.top.module.walletfactory.factory;

import com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.license.SourceForkLicense;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.license.SourceRebrandLicense;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.license.SourceUserLicense;

/**
 * Created by ciencias on 28.12.14.
 */
public interface SourceWallet extends Wallet {

    public ParentWallet getParent ();
    public SourceForkLicense getForkLicense ();
    public SourceRebrandLicense getRebrandLicense ();
    public SourceUserLicense getUseLicense ();
}
