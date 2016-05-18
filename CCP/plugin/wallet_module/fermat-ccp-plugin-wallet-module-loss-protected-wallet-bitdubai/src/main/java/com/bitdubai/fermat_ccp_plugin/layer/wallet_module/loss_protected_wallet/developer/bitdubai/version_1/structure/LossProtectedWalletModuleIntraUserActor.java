package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletIntraUserActor;
import java.io.Serializable;

/**
 * The class <code>CryptoWalletWalletModuleIntraUserActor</code>
 * implements the interface CryptoWalletIntraUserActor and all its methods.
 *
 * Created Natalia Cortez on 07/03/2016.
 * @version 1.0
 */
public class LossProtectedWalletModuleIntraUserActor implements LossProtectedWalletIntraUserActor ,Serializable{

    private final String     alias;
    private final boolean    isContact;
    private final byte[]     profileImage;
    private final String     publicKey;
    private boolean isSelected=false;

    public LossProtectedWalletModuleIntraUserActor(final String   alias,
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
