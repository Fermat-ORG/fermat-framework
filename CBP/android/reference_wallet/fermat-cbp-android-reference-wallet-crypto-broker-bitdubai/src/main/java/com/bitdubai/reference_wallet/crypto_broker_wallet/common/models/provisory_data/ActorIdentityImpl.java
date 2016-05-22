package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.provisory_data;


import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;

import java.io.Serializable;


public class ActorIdentityImpl implements ActorIdentity, Serializable {

    private String alias;
    private byte[] img;

    public ActorIdentityImpl(String alias, byte[] img) {
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
