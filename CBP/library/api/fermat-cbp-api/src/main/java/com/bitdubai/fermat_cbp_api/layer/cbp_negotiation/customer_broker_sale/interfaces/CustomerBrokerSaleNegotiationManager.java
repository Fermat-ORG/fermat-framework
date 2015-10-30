package com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.exceptions.CantListSaleNegotianionsException;

import java.util.Collection;

/**
 * Created by jorge on 10-10-2015.
 */
public interface CustomerBrokerSaleNegotiationManager {

    CustomerBrokerSaleNegotiation createNegotiation(String publicKeyCustomer, String publicKeyBroker, Collection<Clause> clauses) throws CantCreateCustomerBrokerSaleNegotiationException;
    void cancelNegotiation(CustomerBrokerSaleNegotiation negotiation);
    void closeNegotiation(CustomerBrokerSaleNegotiation negotiation);

    Collection<CustomerBrokerSaleNegotiation> getNegotiations() throws CantListSaleNegotianionsException;
    Collection<CustomerBrokerSaleNegotiation> getNegotiations(NegotiationStatus status) throws CantListSaleNegotianionsException;
    Collection<CustomerBrokerSaleNegotiation> getNegotiationsByCustomer(ActorIdentity customer) throws CantListSaleNegotianionsException;
    Collection<CustomerBrokerSaleNegotiation> getNegotiationsByBroker(ActorIdentity broker) throws CantListSaleNegotianionsException;
}
