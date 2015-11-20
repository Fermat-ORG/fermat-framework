package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces;

import java.util.UUID;

/**
 * Created by rodrigo on 7/23/15.
 */
public interface Designer {
    public UUID getId();

    public String getName();

    public String getPublicKey();

    public void setiD(UUID id);

    public void setName(String name);

    public void setPublicKey(String publicKey);
}
