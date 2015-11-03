package com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;

import java.util.Collection;

/**
 * Created by jorge on 10-10-2015.
 */
public interface CustomerBrokerSaleNegotiationManager {

    CustomerBrokerSaleNegotiation createNegotiation(String publicKeyCustomer, String publicKeyBroker) throws CantCreateCustomerBrokerSaleNegotiationException;
    void cancelNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException;
    void closeNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException ;

    CustomerBrokerSaleNegotiation sendToCustomer(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException;
    CustomerBrokerSaleNegotiation waitForCustomer(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException;

    Collection<CustomerBrokerSaleNegotiation> getNegotiations() throws CantGetListSaleNegotiationsException;
    Collection<CustomerBrokerSaleNegotiation> getNegotiations(NegotiationStatus status) throws CantGetListSaleNegotiationsException;
    Collection<CustomerBrokerSaleNegotiation> getNegotiationsByCustomer(ActorIdentity customer) throws CantGetListSaleNegotiationsException;
    Collection<CustomerBrokerSaleNegotiation> getNegotiationsByBroker(ActorIdentity broker) throws CantGetListSaleNegotiationsException;
}
