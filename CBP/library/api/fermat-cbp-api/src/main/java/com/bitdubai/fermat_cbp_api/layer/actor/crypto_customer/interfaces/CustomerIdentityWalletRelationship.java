package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces;

import java.util.UUID;

/**
 * Created by angel on 17/11/15.
 */
public interface CustomerIdentityWalletRelationship {
    UUID getRelationshipId();

    String getCryptoCustomer();

    String getWallet();
}
