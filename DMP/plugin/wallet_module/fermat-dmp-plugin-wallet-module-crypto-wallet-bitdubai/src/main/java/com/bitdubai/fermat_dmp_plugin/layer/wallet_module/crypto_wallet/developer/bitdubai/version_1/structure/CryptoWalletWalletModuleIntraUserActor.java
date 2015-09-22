package com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserActor;

/**
 * The class <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleIntraUserActor</code>
 * implements the interface CryptoWalletIntraUserActor and all its methods.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/15.
 * @version 1.0
 */
public class CryptoWalletWalletModuleIntraUserActor implements CryptoWalletIntraUserActor {

    private final String publicKey;

    private final String alias;

    private final byte[] profileImage;

    private final boolean isContact;

    public CryptoWalletWalletModuleIntraUserActor(final String publicKey, final String alias, final byte[] profileImage, final boolean isContact) {
        this.publicKey = publicKey;
        this.alias = alias;
        this.profileImage = profileImage;
        this.isContact = isContact;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public byte[] getProfileImage() {
        return profileImage;
    }

    @Override
    public boolean isContact() {
        return isContact;
    }
}
