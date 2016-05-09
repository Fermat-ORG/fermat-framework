package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantSelectIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySelectableIdentity;

/**
 * Created by Alejandro Bicelis on 2/2/2016.
 */
public class CryptoCustomerCommunitySelectableIdentityImpl implements CryptoCustomerCommunitySelectableIdentity {

    String publicKey;
    Actors actorType;
    String alias;
    byte[] image;

    CryptoCustomerCommunitySelectableIdentityImpl(String publicKey, Actors actorType, String alias, byte[] image){
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
