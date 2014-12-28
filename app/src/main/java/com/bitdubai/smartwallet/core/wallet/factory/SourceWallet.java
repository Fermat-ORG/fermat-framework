package com.bitdubai.smartwallet.core.wallet.factory;

/**
 * Created by ciencias on 28.12.14.
 */
public interface SourceWallet {

    public ParentWallet getParent ();
    public SourceForkLicense getForkLicense ();
    public SourceRebrandLicense getRebrandLicense ();
    public SourceUserLicense getUseLicense ();
}
