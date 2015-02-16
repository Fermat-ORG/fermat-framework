package com.bitdubai.wallet_platform_draft.layer._12_module.wallet_factory.version_1;

import com.bitdubai.wallet_platform_draft.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.SourceForkLicense;
import com.bitdubai.wallet_platform_draft.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.SourceRebrandLicense;
import com.bitdubai.wallet_platform_draft.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.SourceUserLicense;

/**
 * Created by ciencias on 28.12.14.
 */
public interface SourceWallet extends Wallet {

    public ParentWallet getParent ();
    public SourceForkLicense getForkLicense ();
    public SourceRebrandLicense getRebrandLicense ();
    public SourceUserLicense getUseLicense ();
}
