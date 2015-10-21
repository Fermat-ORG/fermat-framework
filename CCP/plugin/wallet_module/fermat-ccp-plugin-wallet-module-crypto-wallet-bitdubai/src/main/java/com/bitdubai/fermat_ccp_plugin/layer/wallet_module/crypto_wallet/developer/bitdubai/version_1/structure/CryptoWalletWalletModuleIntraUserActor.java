package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserActor;

/**
 * The class <code>CryptoWalletWalletModuleIntraUserActor</code>
 * implements the interface CryptoWalletIntraUserActor and all its methods.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/15.
 * @version 1.0
 */
public class CryptoWalletWalletModuleIntraUserActor implements CryptoWalletIntraUserActor {

    private final String     alias;
    private final boolean    isContact;
    private final byte[]     profileImage;
    private final String     publicKey;

    public CryptoWalletWalletModuleIntraUserActor(final String   alias,
                                                  final boolean  isContact,
                                                  final byte[]   profileImage,
                                                  final String   publicKey) {

        this.alias        = alias;
        this.isContact    = isContact;
        this.profileImage = profileImage;
        this.publicKey    = publicKey;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public boolean isContact() {
        return isContact;
    }

    @Override
    public byte[] getProfileImage() {
        return profileImage;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }
}
