package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.actor.Actor;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.interfaces.CustomerBrokerPurchase;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreatePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreatePurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetPurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.interfaces.CustomerBrokerPurchase;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 26-10-2015.
 * Modified by Yordin Alayn 11.11.2015
 */
public interface CryptoCustomerActor extends Actor {

    Collection<ActorIdentity> getConnectedBrokers();

    CustomerBrokerNegotiation createNegotiationPurchase(ActorIdentity cryptoBroker,Collection<Clause> clauses) throws CantCreatePurchaseNegotiationException;
    CustomerBrokerNegotiation getNegotiationPurchase(UUID negotiationId) throws CantGetPurchaseNegotiationException;
    Collection<CustomerBrokerNegotiation> getNegotiationPurchases() throws CantGetPurchaseNegotiationException;
    Collection<CustomerBrokerNegotiation> getNegotiationPurchases(NegotiationStatus status) throws CantGetPurchaseNegotiationException;

    CustomerBrokerPurchase createContractPurchase(ActorIdentity cryptoBroker,Collection<Clause> clauses) throws CantCreatePurchaseContractException;
    CustomerBrokerPurchase getContractPurchase(UUID negotiationId) throws CantGetPurchaseContractException;
    Collection<CustomerBrokerPurchase> getContractPurchases() throws CantGetPurchaseContractException;
    Collection<CustomerBrokerPurchase> getContractPurchases(NegotiationStatus status) throws CantGetPurchaseContractException;

}
