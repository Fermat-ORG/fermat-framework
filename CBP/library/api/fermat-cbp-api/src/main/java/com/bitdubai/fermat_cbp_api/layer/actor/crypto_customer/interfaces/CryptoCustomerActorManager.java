package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;

/**
 * Created by jorge on 26-10-2015.
 */
public interface CryptoCustomerActorManager extends FermatManager {
    CryptoCustomerActor createNewCryptoCustomerActor(ActorIdentity identity) throws com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateCryptoCustomerActorException;
    CryptoCustomerActor getCryptoCustomer(ActorIdentity identity) throws com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCryptoCustomerActorException;
}
