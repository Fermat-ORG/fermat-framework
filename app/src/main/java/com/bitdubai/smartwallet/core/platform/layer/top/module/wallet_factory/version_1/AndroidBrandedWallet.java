package com.bitdubai.smartwallet.core.platform.layer.top.module.wallet_factory.version_1;

import com.bitdubai.smartwallet.core.platform.layer.middleware.engine.wallet.version_1.license.BrandUserLicense;
import com.bitdubai.smartwallet.core.platform.layer.middleware.engine.wallet.version_1.license.SourceForkLicense;
import com.bitdubai.smartwallet.core.platform.layer.middleware.engine.wallet.version_1.license.SourceRebrandLicense;
import com.bitdubai.smartwallet.core.platform.layer.middleware.engine.wallet.version_1.license.SourceUserLicense;

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
