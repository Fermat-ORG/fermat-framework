package com.bitdubai.wallet_platform_draft.layer._11_module.wallet_factory.version_1;

import com.bitdubai.wallet_platform_draft.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.BrandUserLicense;
import com.bitdubai.wallet_platform_draft.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.SourceForkLicense;
import com.bitdubai.wallet_platform_draft.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.SourceRebrandLicense;
import com.bitdubai.wallet_platform_draft.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.SourceUserLicense;

import java.util.List;

/**
 * Created by ciencias on 28.12.14.
 */
public class AndroidBrandedWallet implements BrandedWallet {

    private Configuration mConfiguration;
    private List<Fragment> mFragments;
    private List<Resource> mResources;
    private AndroidSourceWallet mSourceWallet;
    private SourceForkLicense mSourceForkLicense;
    private SourceRebrandLicense mSourceRebrandLicense;
    private SourceUserLicense mSourceUserLicense;

    @Override
    public SourceWallet getSourceWallet() {
        return null;
    }


    @Override
    public BrandUserLicense getRebrandLicense() {
        return null;
    }

    @Override
    public SourceUserLicense getUseLicense() {
        return null;
    }

    @Override
    public boolean uploadToWalletStore() {
        return false;
    }
}
