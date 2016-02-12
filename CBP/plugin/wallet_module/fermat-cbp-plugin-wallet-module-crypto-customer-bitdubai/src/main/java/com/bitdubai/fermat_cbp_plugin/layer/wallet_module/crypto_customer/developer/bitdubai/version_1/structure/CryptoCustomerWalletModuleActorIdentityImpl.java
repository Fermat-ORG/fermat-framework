package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;

/**
 * Created by nelson on 24/11/15.
 */
public class CryptoCustomerWalletModuleActorIdentityImpl implements ActorIdentity {

    private String alias;
    private byte[] img;

    public CryptoCustomerWalletModuleActorIdentityImpl(String alias, byte[] img) {
        this.alias = alias;
        this.img = img;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getPublicKey() {
        return "54as65d4a8sd4ds8fv2vr3as2df6a85";
    }

    @Override
    public byte[] getProfileImage() {
        return img;
    }

    @Override
    public void setNewProfileImage(byte[] imageBytes) {

    }

    @Override
    public ExposureLevel getExposureLevel() {
        return ExposureLevel.PUBLISH;
    }

    @Override
    public boolean isPublished() {
        return true;
    }

    @Override
    public String createMessageSignature(String message) throws CantCreateMessageSignatureException {
        return null;
    }
}
