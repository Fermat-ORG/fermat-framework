package com.bitdubai.sub_app.crypto_customer_community.common;

import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;

import java.util.List;

/**
 * Created by Alejandro Bicelis on 2/2/2016.
 */
public class CryptoCustomerCommunityInformationImpl implements CryptoCustomerCommunityInformation {

    String publicKey;
    String alias;
    byte[] image;

    public CryptoCustomerCommunityInformationImpl(String publicKey, String alias, byte[] image){
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
