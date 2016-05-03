package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces;

import java.util.UUID;

/**
 * Created by rodrigo on 7/23/15.
 */
public interface Designer {
    UUID getId();

    String getName();

    String getPublicKey();

    void setiD(UUID id);

    void setName(String name);

    void setPublicKey(String publicKey);
}
