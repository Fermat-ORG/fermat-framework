package com.bitdubai.smartwallet.core.platform.layer.top.module.wallet_factory.version_1;


import com.bitdubai.smartwallet.core.platform.layer.middleware.engine.wallet.version_1.license.SourceForkLicense;
import com.bitdubai.smartwallet.core.platform.layer.middleware.engine.wallet.version_1.license.SourceRebrandLicense;
import com.bitdubai.smartwallet.core.platform.layer.middleware.engine.wallet.version_1.license.SourceUserLicense;

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
