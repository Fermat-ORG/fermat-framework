package com.bitdubai.sub_app.crypto_broker_community.common;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantSelectIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;

/**
 * Created by Alex on 26/1/2016.
 */
public class CryptoBrokerCommunitySelectableIdentityImpl implements CryptoBrokerCommunitySelectableIdentity {

    String publicKey;
    Actors actorType;
    String alias;
    byte[] image;

    CryptoBrokerCommunitySelectableIdentityImpl(String publicKey, Actors actorType, String alias, byte[] image){
        this.publicKey = publicKey;
        this.actorType = actorType;
        this.alias = alias;
        this.image = image;
    }

    @Override
    public void select() throws CantSelectIdentityException {}

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public Actors getActorType() {
        return this.actorType;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public byte[] getImage() {
        return this.image;
    }
}
