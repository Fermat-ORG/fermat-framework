package com.bitdubai.smartwallet.core.system.wallet.factory;

import com.bitdubai.smartwallet.core.system.wallet.license.SourceForkLicense;
import com.bitdubai.smartwallet.core.system.wallet.license.SourceRebrandLicense;
import com.bitdubai.smartwallet.core.system.wallet.license.SourceUserLicense;

/**
 * Created by ciencias on 28.12.14.
 */
public interface SourceWallet extends Wallet {

    public ParentWallet getParent ();
    public SourceForkLicense getForkLicense ();
    public SourceRebrandLicense getRebrandLicense ();
    public SourceUserLicense getUseLicense ();
}
