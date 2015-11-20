package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces;

import java.util.UUID;

/**
 * Created by angel on 17/11/15.
 */
public interface BrokerIdentityWalletRelationship {
    UUID getRelationshipId();
    String getCryptoBroker();
    UUID getWallet();
}
