package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog;

import java.util.UUID;

/**
 * Created by rodrigo on 7/23/15.
 */
public class Developer implements com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Developer {
    UUID id;
    String Name;
    String publicKey;

    /**
     * default constructor
     */
    public Developer() {
    }

    /**
     * Overloaded constructor
     * @param id
     * @param name
     * @param publicKey
     */
    public Developer(UUID id, String name, String publicKey) {
        this.id = id;
        Name = name;
        this.publicKey = publicKey;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.Name;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    public void setid(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
