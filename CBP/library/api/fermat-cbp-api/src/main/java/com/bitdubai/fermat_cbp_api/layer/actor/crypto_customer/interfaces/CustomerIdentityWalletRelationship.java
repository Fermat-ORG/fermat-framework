package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 12.11.15.
 */
public interface CustomerIdentityWalletRelationship {
    UUID getRelationship();
    String getWalletPublicKey();
    String getIdentityPublicKey();
}
