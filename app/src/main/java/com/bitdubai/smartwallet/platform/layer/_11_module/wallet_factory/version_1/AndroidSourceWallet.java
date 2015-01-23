package com.bitdubai.smartwallet.platform.layer._11_module.wallet_factory.version_1;


import com.bitdubai.smartwallet.platform.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.SourceForkLicense;
import com.bitdubai.smartwallet.platform.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.SourceRebrandLicense;
import com.bitdubai.smartwallet.platform.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.SourceUserLicense;

import java.util.List;

/**
 * Created by ciencias on 28.12.14.
 */
public class AndroidSourceWallet implements SourceWallet, Brandable {

    private Configuration mConfiguration;
    private List<Fragment> mFragments;
    private List<Resource> mResources;
    private ParentWallet mParent;
    private SourceForkLicense mSourceForkLicense;
    private SourceRebrandLicense mSourceRebrandLicense;
    private SourceUserLicense mSourceUserLicense;

    @Override
    public ParentWallet getParent() {
        return null;
    }

    @Override
    public SourceForkLicense getForkLicense() {
        return null;
    }

    @Override
    public SourceRebrandLicense getRebrandLicense() {
        return null;
    }

    @Override
    public SourceUserLicense getUseLicense() {
        return null;
    }

    @Override
    public BrandedWallet rebrand() {
        return null;
    }

    @Override
    public boolean uploadToWalletStore() {
        return false;
    }
}
