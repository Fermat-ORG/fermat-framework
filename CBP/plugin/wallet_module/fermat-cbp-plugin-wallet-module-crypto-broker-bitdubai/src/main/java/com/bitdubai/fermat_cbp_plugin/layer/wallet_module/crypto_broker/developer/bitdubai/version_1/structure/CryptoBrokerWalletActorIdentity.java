package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;

import java.io.Serializable;


public class CryptoBrokerWalletActorIdentity implements ActorIdentity, Serializable {

    private String alias;
    private byte[] img;
    private String publicKey;
    private long accuracy;
    private GeoFrequency frequency;

    public CryptoBrokerWalletActorIdentity(String alias, byte[] img, long accuracy, GeoFrequency frequency) {
        this.alias = alias;
        this.img = img;
        this.accuracy = accuracy;
        this.frequency = frequency;
    }

    public CryptoBrokerWalletActorIdentity(String publicKey, String alias, byte[] img, long accuracy, GeoFrequency frequency) {
        this.alias = alias;
        this.img = img;
        this.publicKey = publicKey;
        this.accuracy = accuracy;
        this.frequency = frequency;
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

    @Override
    public long getAccuracy() {
        return accuracy;
    }

    @Override
    public GeoFrequency getFrequency() {
        return frequency;
    }
}
