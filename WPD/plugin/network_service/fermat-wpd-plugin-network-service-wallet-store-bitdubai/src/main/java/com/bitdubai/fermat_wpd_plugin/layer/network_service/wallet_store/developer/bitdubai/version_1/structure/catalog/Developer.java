package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog;

import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantSingMessageException;

import java.util.UUID;

/**
 * Created by rodrigo on 7/23/15.
 */
public class Developer implements com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentity {
    UUID id;
    String alias;
    String publicKey;

    /**
     * default constructor
     */
    public Developer() {
    }

    /**
     * Overloaded constructor
     * @param id
     * @param alias
     * @param publicKey
     */
    public Developer(UUID id, String alias, String publicKey) {
        this.id = id;
        this.alias = alias;
        this.publicKey = publicKey;
    }


    public UUID getId() {
        return this.id;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }
    @Override
    public String getAlias(){ return this.alias; }

    @Override
    public String createMessageSignature(String mensage) throws CantSingMessageException
    { return  null;}

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
