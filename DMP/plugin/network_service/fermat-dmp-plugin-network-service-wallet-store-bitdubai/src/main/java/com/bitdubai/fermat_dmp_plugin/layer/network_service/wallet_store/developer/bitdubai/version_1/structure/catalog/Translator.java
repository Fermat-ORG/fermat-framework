package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog;

import java.util.UUID;

/**
 * Created by rodrigo on 7/23/15.
 */
public class Translator implements com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Translator {
    UUID id;
    String name;
    String publicKey;

    /**
     * default constructor
     */
    public Translator() {
    }

    /**
     * overloaded constructor
     * @param id
     * @param name
     * @param publicKey
     */
    public Translator(UUID id, String name, String publicKey) {
        this.id = id;
        this.name = name;
        this.publicKey = publicKey;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
