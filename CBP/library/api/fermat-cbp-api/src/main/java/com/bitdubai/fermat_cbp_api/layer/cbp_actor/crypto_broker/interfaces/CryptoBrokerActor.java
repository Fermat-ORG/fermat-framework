package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.actor.Actor;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantCreateSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantGetSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantCreateContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantGetListContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.interfaces.CustomerBrokerSaleContract;

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

        CustomerBrokerNegotiation               createContractSale(ActorIdentity cryptoCustomer, Collection<Clause> clauses) throws CantCreateContractSaleException;

        CustomerBrokerSaleContract getContractSale(UUID ContractId) throws CantGetListContractSaleException;
        Collection<CustomerBrokerSaleContract>   getContractSales() throws CantGetListContractSaleException;
        Collection<CustomerBrokerSaleContract>   getContractSales(ContractStatus status) throws CantGetListContractSaleException;
}
