package com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseException;

import java.util.Collection;

/**
 * Created by jorge on 10-10-2015.
 */
public interface CustomerBrokerPurchaseNegotiationManager {

    CustomerBrokerPurchaseNegotiation createCustomerBrokerPurchaseNegotiation(String publicKeyCustomer, String publicKeyBroker) throws CantCreateCustomerBrokerPurchaseNegotiationException;
    void cancelNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseException;
    void closeNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseException;

    CustomerBrokerPurchaseNegotiation sendToBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseException;
    CustomerBrokerPurchaseNegotiation waitForBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseException;

    Collection<CustomerBrokerPurchaseNegotiation> getNegotiations() throws CantGetListPurchaseNegotiationsException;
    Collection<CustomerBrokerPurchaseNegotiation> getNegotiations(NegotiationStatus status) throws CantGetListPurchaseNegotiationsException;
    Collection<CustomerBrokerPurchaseNegotiation> getNegotiationsByCustomer(ActorIdentity customer) throws CantGetListPurchaseNegotiationsException;
    Collection<CustomerBrokerPurchaseNegotiation> getNegotiationsByBroker(ActorIdentity broker) throws CantGetListPurchaseNegotiationsException;
}
