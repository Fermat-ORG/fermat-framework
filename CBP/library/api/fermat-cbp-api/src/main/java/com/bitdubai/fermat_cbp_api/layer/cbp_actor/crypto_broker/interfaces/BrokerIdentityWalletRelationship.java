package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;

import java.util.UUID;

/**
 * Created by angel on 17/11/15.
 */
public interface BrokerIdentityWalletRelationship {
    UUID getRelationshipId();
    ActorIdentity getCryptoBroker();
    UUID getWallet();
}
