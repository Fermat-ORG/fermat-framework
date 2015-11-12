package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantCreateSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantGetSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces.CryptoBrokerActor;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 30-10-2015.
 */
public class CryptoBrokerActorInformation implements CryptoBrokerActor {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1117;
    private static final int HASH_PRIME_NUMBER_ADD = 3001;

    private final ActorIdentity identity;
    private final CustomerBrokerSaleNegotiationManager saleNegotiationManager;

    public CryptoBrokerActorInformation(final ActorIdentity identity, final CustomerBrokerSaleNegotiationManager saleNegotiationManager){
        this.identity = identity;
        this.saleNegotiationManager = saleNegotiationManager;
    }

    @Override
    public Collection<ActorIdentity> createCustomerConnection(ActorIdentity cryptoCustomer) {
        return null;
    }

    @Override
    public Collection<ActorIdentity> getConnectedCustomers() {
        return null;
    }

    @Override
    public CustomerBrokerNegotiation createNegotiationSale(ActorIdentity cryptoCustomer, Collection<Clause> clauses) throws CantCreateSaleNegotiationException {
        return null;
    }

    @Override
    public CustomerBrokerNegotiation getNegotiationSale(UUID negotiationId) throws CantGetSaleNegotiationException {
        return null;
    }

    @Override
    public Collection<CustomerBrokerNegotiation> getNegotiationSales() throws CantGetSaleNegotiationException {
        return null;
    }

    @Override
    public Collection<CustomerBrokerNegotiation> getNegotiationSales(NegotiationStatus status) throws CantGetSaleNegotiationException {
        return null;
    }

    @Override
    public CustomerBrokerContractSale createContractSale(ActorIdentity cryptoCustomer, Collection<Clause> clauses) throws CantCreateCustomerBrokerContractSaleException {
        return null;
    }

    @Override
    public CustomerBrokerContractSale getContractSale(UUID ContractId) throws CantGetListCustomerBrokerContractSaleException {
        return null;
    }

    @Override
    public Collection<CustomerBrokerContractSale> getContractSales() throws CantGetListCustomerBrokerContractSaleException {
        return null;
    }

    @Override
    public Collection<CustomerBrokerContractSale> getContractSales(ContractStatus status) throws CantGetListCustomerBrokerContractSaleException {
        return null;
    }


    @Override
    public ActorIdentity getIdentity() {
        return identity;
    }

    @Override
    public boolean equals(final Object o){
        if(!(o instanceof CryptoBrokerActor))
            return false;
        CryptoBrokerActor compare = (CryptoBrokerActor) o;
        return this.identity.equals(compare.getIdentity());
    }

    @Override
    public int hashCode(){
        int c = 0;
        c += identity.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }
}
