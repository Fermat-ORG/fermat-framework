package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantSignExtraUserMessageException;

import java.io.Serializable;

/**
 * Created by natalia on 28/10/15.
 */
public class IntraUserActorRecord implements Actor,Serializable {

    private String actorPublicKey;

    private String name;

    private byte[] photo;

    private String privateKey;

    public IntraUserActorRecord(String actorPublicKey, String privateKey, String name, byte[] photo) {

        this.actorPublicKey = actorPublicKey;
        this.name           = name;
        this.photo          = photo;
        this.privateKey     = privateKey;

    }

    public IntraUserActorRecord(String actorPublicKey, String privateKey, String name) {

        this.actorPublicKey = actorPublicKey;
        this.name           = name;
        this.photo          = new byte[0];
        this.privateKey     = privateKey;

    }

    @Override
    public String getActorPublicKey() {
        return this.actorPublicKey;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPhrase() {
        return "";
    }

    @Override
    public Actors getType() {
        return Actors.INTRA_USER;
    }

    @Override
    public byte[] getPhoto() {
        return this.photo;
    }

    @Override
    public String createMessageSignature(String message) throws CantSignExtraUserMessageException {
        try {
            return AsymmetricCryptography.createMessageSignature(message, this.privateKey);
        } catch (Exception e) {
            throw new CantSignExtraUserMessageException("Fatal Error Signed message", e, "", "");
        }
    }
}
