package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;

import java.io.Serializable;


public class CryptoCustomerWalletActorIdentity implements ActorIdentity, Serializable {

    private String alias;
    private byte[] img;
    private String publicKey;

    public CryptoCustomerWalletActorIdentity(String alias, byte[] img) {
        this.alias = alias;
        this.img = img;
    }

    public CryptoCustomerWalletActorIdentity(String publicKey, String alias, byte[] img) {
        this.alias = alias;
        this.img = img;
        this.publicKey = publicKey;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public byte[] getProfileImage() {
        return img;
    }

    @Override
    public void setNewProfileImage(byte[] imageBytes) {
        this.img = imageBytes;
    }

    @Override
    public boolean isPublished() {
        return true;
    }

    @Override
    public ExposureLevel getExposureLevel() {
        return ExposureLevel.PUBLISH;
    }

    @Override
    public String createMessageSignature(String message) {
        return "";
    }
}
