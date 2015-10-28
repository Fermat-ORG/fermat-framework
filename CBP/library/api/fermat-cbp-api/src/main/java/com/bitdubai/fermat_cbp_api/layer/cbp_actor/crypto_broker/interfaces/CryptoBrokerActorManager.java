package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantCreateCryptoBrokerActorException;

/**
 * Created by jorge on 26-10-2015.
 */
public interface CryptoBrokerActorManager {
    CryptoBrokerActor createNewCryptoBroker(ActorIdentity identity) throws CantCreateCryptoBrokerActorException;

    CryptoBrokerActor getCryptoBroker(ActorIdentity identity);
}
