package com.bitdubai.smartwallet.core.system.module.walletfactory.factory;

import com.bitdubai.smartwallet.core.system.license.SourceForkLicense;
import com.bitdubai.smartwallet.core.system.license.SourceRebrandLicense;
import com.bitdubai.smartwallet.core.system.license.SourceUserLicense;

/**
 * Created by ciencias on 28.12.14.
 */
public interface SourceWallet extends Wallet {

    public ParentWallet getParent ();
    public SourceForkLicense getForkLicense ();
    public SourceRebrandLicense getRebrandLicense ();
    public SourceUserLicense getUseLicense ();
}
