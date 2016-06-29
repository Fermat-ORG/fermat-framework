package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.all_definition.enums.Frequency;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantSelectIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;

/**
 * Created by Alejandro Bicelis on 26/1/2016.
 */
public class CryptoBrokerCommunitySelectableIdentityImpl implements CryptoBrokerCommunitySelectableIdentity {

    String publicKey;
    Actors actorType;
    String alias;
    byte[] image;
    long   accuracy;
    Frequency frequency;

    CryptoBrokerCommunitySelectableIdentityImpl(String publicKey, Actors actorType, String alias, byte[] image,
                                                long accuracy, Frequency frequency){
        this.publicKey = publicKey;
        this.actorType = actorType;
        this.alias = alias;
        this.image = image;
        this.accuracy = accuracy;
        this.frequency = frequency;
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

    public long getAccuracy() {
        return accuracy;
    }

    public Frequency getFrequency() {
        return frequency;
    }

}
