package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCryptoCustomerActorException;

/**
 * Created by jorge on 26-10-2015.
 */
public interface CryptoCustomerActorManager {
    CryptoCustomerActorRecord createNewCryptoCustomerActor(String actorPublicKey, String actorName, byte[] actorPhoto) throws CantCreateCryptoCustomerActorException;
    CryptoCustomerActorRecord getCryptoCustomerActor(String actorLoggedInPublicKey, String actorPublicKey) throws CantGetCryptoCustomerActorException;
}
