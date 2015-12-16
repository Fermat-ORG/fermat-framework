package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;

public class CryptoBrokerWalletActorIdentity implements ActorIdentity {

    private String alias;
    private byte[] img;

    public CryptoBrokerWalletActorIdentity(String alias, byte[] img) {
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
        return null;
    }
}
