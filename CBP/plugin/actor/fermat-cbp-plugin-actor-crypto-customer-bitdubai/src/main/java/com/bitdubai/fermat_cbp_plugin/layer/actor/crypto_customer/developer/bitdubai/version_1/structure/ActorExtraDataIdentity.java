package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;

import java.io.Serializable;


/**
 * Created by angel on 16/01/16.
 */
public class ActorExtraDataIdentity implements ActorIdentity, Serializable {

    private String alias;
    private String publicKey;
    private byte[] image;

    public ActorExtraDataIdentity(String alias, String publicKey, byte[] image){
        this.alias = alias;
        this.publicKey = publicKey;
        this.image = image;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public byte[] getProfileImage() {
        return this.image;
    }

    @Override
    public void setNewProfileImage(byte[] imageBytes) {

    }

    @Override
    public boolean isPublished() {
        return false;
    }

    @Override
    public ExposureLevel getExposureLevel() {
        return null;
    }

    @Override
    public String createMessageSignature(String message) throws CantCreateMessageSignatureException {
        return null;
    }
}
