package com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions.CantListPurchaseNegotianionsException;

import java.util.Collection;

/**
 * Created by jorge on 10-10-2015.
 */
public interface CustomerBrokerPurchaseManager {

    CustomerBrokerPurchase createCustomerBrokerPurchaseNegotiation(String publicKeyCustomer, String publicKeyBroker) throws CantCreateCustomerBrokerPurchaseException;
    void cancelNegotiation(CustomerBrokerPurchase negotiation);
    void closeNegotiation(CustomerBrokerPurchase negotiation);

    Collection<CustomerBrokerPurchase> getNegotiations() throws CantListPurchaseNegotianionsException;
    Collection<CustomerBrokerPurchase> getNegotiations(NegotiationStatus status) throws CantListPurchaseNegotianionsException;
    Collection<CustomerBrokerPurchase> getNegotiationsByCustomer(ActorIdentity customer) throws CantListPurchaseNegotianionsException;
    Collection<CustomerBrokerPurchase> getNegotiationsByBroker(ActorIdentity broker) throws CantListPurchaseNegotianionsException;
}
