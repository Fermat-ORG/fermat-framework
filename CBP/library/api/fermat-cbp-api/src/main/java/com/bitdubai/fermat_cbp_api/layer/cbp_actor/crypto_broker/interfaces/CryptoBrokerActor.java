package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.actor.Actor;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantCreateSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantGetSaleException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 26-10-2015.
 */
public interface CryptoBrokerActor extends Actor {

    Collection<ActorIdentity> getConnectedCustomers();

    CustomerBrokerNegotiation createSale(ActorIdentity cryptoCustomer, Collection<Clause> clauses) throws CantCreateSaleException;
    CustomerBrokerNegotiation getSale(UUID negotiationId) throws CantGetSaleException;
    Collection<CustomerBrokerNegotiation> getSales() throws CantGetSaleException;
    Collection<CustomerBrokerNegotiation> getSales(NegotiationStatus status) throws CantGetSaleException;
}
