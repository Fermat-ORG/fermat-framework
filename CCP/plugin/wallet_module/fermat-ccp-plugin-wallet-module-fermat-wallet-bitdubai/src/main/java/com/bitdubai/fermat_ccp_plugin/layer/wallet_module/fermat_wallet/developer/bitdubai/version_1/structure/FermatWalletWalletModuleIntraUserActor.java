package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.fermat_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletIntraUserActor;

import java.io.Serializable;

/**
 * The class <code>FermatWalletWalletModuleIntraUserActor</code>
 * implements the interface FermatWalletIntraUserActor and all its methods.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/15.
 * @version 1.0
 */
public class FermatWalletWalletModuleIntraUserActor implements FermatWalletIntraUserActor,Serializable {

    private final String     alias;
    private final boolean    isContact;
    private final byte[]     profileImage;
    private final String     publicKey;
    private boolean isSelected=false;

    public FermatWalletWalletModuleIntraUserActor(final String alias,
                                                  final boolean isContact,
                                                  final byte[] profileImage,
                                                  final String publicKey) {

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
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean b) {
        this.isSelected = b;
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
