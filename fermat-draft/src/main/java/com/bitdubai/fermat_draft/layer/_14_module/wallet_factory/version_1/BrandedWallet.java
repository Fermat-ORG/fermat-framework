package com.bitdubai.fermat_draft.layer._12_module.wallet_factory.version_1;

import com.bitdubai.fermat_draft.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.BrandUserLicense;
import com.bitdubai.fermat_draft.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.SourceUserLicense;

/**
 * Created by ciencias on 28.12.14.
 */
public interface BrandedWallet extends Wallet {

    public SourceWallet getSourceWallet();
    public BrandUserLicense getRebrandLicense ();
    public SourceUserLicense getUseLicense ();

}
