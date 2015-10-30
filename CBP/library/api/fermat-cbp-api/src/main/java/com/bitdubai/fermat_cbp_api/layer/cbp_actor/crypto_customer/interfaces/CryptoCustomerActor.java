package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.actor.Actor;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreatePurchaseException;

import java.util.Collection;

/**
 * Created by jorge on 26-10-2015.
 */
public interface CryptoCustomerActor extends Actor {
    CustomerBrokerNegotiation createPurchase(ActorIdentity cryptoBroker) throws CantCreatePurchaseException;

    Collection<CustomerBrokerNegotiation> getPurchases();

    Collection<CustomerBrokerNegotiation> getPurchases(NegotiationStatus status);
}
