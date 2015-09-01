package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog;

import com.bitdubai.fermat_api.layer.dmp_identity.translator.exceptions.CantSingMessageException;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.TranslatorIdentity;

import java.util.UUID;

/**
 * Created by rodrigo on 7/23/15.
 */
public class Translator implements TranslatorIdentity {
    UUID id;
    String alias;
    String publicKey;

    /**
     * default constructor
     */
    public Translator() {
    }

    /**
     * overloaded constructor
     * @param id
     * @param alias
     * @param publicKey
     */
    public Translator(UUID id, String alias, String publicKey) {
        this.id = id;
        this.alias = alias;
        this.publicKey = publicKey;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String createMessageSignature(String mensage) throws CantSingMessageException
    {
        return null;
    }

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
