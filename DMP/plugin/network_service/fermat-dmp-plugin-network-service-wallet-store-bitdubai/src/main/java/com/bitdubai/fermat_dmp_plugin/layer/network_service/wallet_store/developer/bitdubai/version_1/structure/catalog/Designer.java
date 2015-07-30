package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog;

import java.util.UUID;

/**
 * Created by rodrigo on 7/22/15.
 */
public class Designer implements com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Designer {
    UUID id;
    String name;
    String publicKey;

    /**
     * Default constructor
     */
    public Designer() {
    }

    /**
     * Overloaded constructor
     * @param id
     * @param name
     * @param publicKey
     */
    public Designer(UUID id, String name, String publicKey) {
        this.id = id;
        this.name = name;
        this.publicKey = publicKey;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    public void setiD(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
