package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.fermat_wallet.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletIntraUserIdentity;

/**
 * Created by natalia on 01/10/15.
 */
public class FermatWalletWalletIntraUserIdentity implements FermatWalletIntraUserIdentity {

    String publicKey;
    String alias;
    byte[] profileImage;

    public FermatWalletWalletIntraUserIdentity(String publicKey, String alias, byte[] profileImage)
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
