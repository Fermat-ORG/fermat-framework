package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.actor.Actor;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantCreateSaleException;

import java.util.Collection;

/**
 * Created by jorge on 26-10-2015.
 */
public interface CryptoBrokerActor extends Actor {

    Collection<ActorIdentity> getConnectedCustomers();

    CustomerBrokerNegotiation createSale(ActorIdentity cryptoCustomer, Iterable<Clause> clauses) throws CantCreateSaleException;
    Collection<CustomerBrokerNegotiation> getSales();
    Collection<CustomerBrokerNegotiation> getSales(NegotiationStatus status);
}
