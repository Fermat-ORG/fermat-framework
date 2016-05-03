package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces;

import java.util.UUID;

/**
 * Created by rodrigo on 7/23/15.
 */
public interface Translator {

    UUID getId();

    String getName();

    String getPublicKey();

    void setId(UUID id);

    void setName(String name);

    void setPublicKey(String publicKey);

}
