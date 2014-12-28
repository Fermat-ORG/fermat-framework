package com.bitdubai.smartwallet.core.wallet.factory;

/**
 * Created by ciencias on 28.12.14.
 */
public interface BrandedWallet {

    public SourceWallet getSourceWallet();
    public BrandUserLicense getRebrandLicense ();
    public SourceUserLicense getUseLicense ();

}
