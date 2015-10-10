package com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.interfaces.CustomerBrokerSale;

import java.util.Collection;

/**
 * Created by jorge on 10-10-2015.
 */
public interface CustomerBrokerPurchaseManager {

    CustomerBrokerPurchase createNegotiation() throws CantCreateCustomerBrokerPurchaseException;
    void cancelNegotiation(CustomerBrokerSale negotiation);
    void closeNegotiation(CustomerBrokerSale negotiation);

    Collection<CustomerBrokerPurchase> getNegotiations();
    Collection<CustomerBrokerPurchase> getNegotiations(NegotiationStatus status);
    Collection<CustomerBrokerPurchase> getNegotiationsByCustomer(ActorIdentity customer);
    Collection<CustomerBrokerPurchase> getNegotiationsByBroker(ActorIdentity broker);
}
