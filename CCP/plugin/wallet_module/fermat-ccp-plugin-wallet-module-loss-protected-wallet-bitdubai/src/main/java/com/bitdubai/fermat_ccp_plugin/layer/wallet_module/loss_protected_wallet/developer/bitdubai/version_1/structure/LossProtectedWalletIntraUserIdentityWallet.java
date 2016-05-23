package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletIntraUserIdentity;

import java.io.Serializable;

/**
 * Created Natalia Cortez on 07/03/2016.
 */
public class LossProtectedWalletIntraUserIdentityWallet  implements LossProtectedWalletIntraUserIdentity,Serializable {

    String publicKey;
    String alias;
    byte[] profileImage;

    public LossProtectedWalletIntraUserIdentityWallet(String publicKey, String alias, byte[] profileImage)
    {
        this.publicKey = publicKey;
        this.alias = alias;
        this.profileImage = profileImage;
    }


    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public byte[] getProfileImage() {
        return this.profileImage;
    }
}
