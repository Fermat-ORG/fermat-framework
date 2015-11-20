package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.actor.Actor;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantCreateSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantGetSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 26-10-2015.
 */
public interface CryptoBrokerActor extends Actor {

    Collection<ActorIdentity>               createCustomerConnection(ActorIdentity cryptoCustomer);
    Collection<ActorIdentity>               getConnectedCustomers();

    /* Negotiations  */

        CustomerBrokerNegotiation               createNegotiationSale(ActorIdentity cryptoCustomer, Collection<Clause> clauses) throws CantCreateSaleNegotiationException;

        CustomerBrokerNegotiation               getNegotiationSale(UUID negotiationId) throws CantGetSaleNegotiationException;
        Collection<CustomerBrokerNegotiation>   getNegotiationSales() throws CantGetSaleNegotiationException;
        Collection<CustomerBrokerNegotiation>   getNegotiationSales(NegotiationStatus status) throws CantGetSaleNegotiationException;

    /* Contracts  */

        CustomerBrokerContractSale               createContractSale(ActorIdentity cryptoCustomer, Collection<Clause> clauses) throws CantCreateCustomerBrokerContractSaleException;

        CustomerBrokerContractSale getContractSale(UUID ContractId) throws CantGetListCustomerBrokerContractSaleException;
        Collection<CustomerBrokerContractSale>   getContractSales() throws CantGetListCustomerBrokerContractSaleException;
        Collection<CustomerBrokerContractSale>   getContractSales(ContractStatus status) throws CantGetListCustomerBrokerContractSaleException;
}
