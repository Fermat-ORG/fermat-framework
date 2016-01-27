package com.bitdubai.sub_app.crypto_broker_community.common;

import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;

import java.util.List;

/**
 * Created by Alex on 26/1/2016.
 */
public class CryptoBrokerCommunityInformationImpl implements CryptoBrokerCommunityInformation {

    String publicKey;
    String alias;
    byte[] image;

    public CryptoBrokerCommunityInformationImpl(String publicKey, String alias, byte[] image){
        this.publicKey = publicKey;
        this.alias = alias;
        this.image = image;
    }



    @Override
    public String getPublicKey() {return this.publicKey;}


    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public byte[] getImage() {
        return this.image;
    }


    @Override
    public List listCryptoBrokerWallets() {
        return null;
    }
}
