package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantSignExtraUserMessageException;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2015.10.15..
 */
public class Identity implements Actor,Serializable {


    private String publicKey;
    private String name;
    private Actors type;
    private byte[] image;
    private String phrase;


    public Identity(String publicKey, String name, String phrase, Actors type, byte[] image) {
        this.publicKey = publicKey;
        this.name = name;
        this.type = type;
        this.image = image;
        this.phrase = phrase;
    }

    @Override
    public String getActorPublicKey() {
        return publicKey;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhrase() {
        return phrase;
    }

    @Override
    public Actors getType() {
        return type;
    }

    @Override
    public byte[] getPhoto() {
        return image;
    }

    @Override
    public String createMessageSignature(String message) throws CantSignExtraUserMessageException {
        return null;
    }
}
