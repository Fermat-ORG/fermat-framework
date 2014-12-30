package com.bitdubai.smartwallet.core.system.module.walletfactory.factory;


import com.bitdubai.smartwallet.core.system.license.SourceForkLicense;
import com.bitdubai.smartwallet.core.system.license.SourceRebrandLicense;
import com.bitdubai.smartwallet.core.system.license.SourceUserLicense;

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
