package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantCreateSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantGetSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces.CryptoBrokerActor;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.exceptions.CantListSaleNegotianionsException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by jorge on 30-10-2015.
 */
public class CryptoBrokerActorImpl implements CryptoBrokerActor {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1117;
    private static final int HASH_PRIME_NUMBER_ADD = 3001;

    private final ActorIdentity identity;
    private final CustomerBrokerSaleNegotiationManager saleNegotiationManager;

    public CryptoBrokerActorImpl(final ActorIdentity identity, final CustomerBrokerSaleNegotiationManager saleNegotiationManager){
        this.identity = identity;
        this.saleNegotiationManager = saleNegotiationManager;
    }

    @Override
    public Collection<ActorIdentity> getConnectedCustomers() {
        return null;
    }

    @Override
    public CustomerBrokerNegotiation createSale(ActorIdentity cryptoCustomer, Collection<Clause> clauses) throws CantCreateSaleException {
        try {
            return saleNegotiationManager.createNegotiation(cryptoCustomer.getPublicKey(), identity.getPublicKey(), clauses);
        } catch (CantCreateCustomerBrokerSaleNegotiationException exception) {
            throw new CantCreateSaleException(CantCreateSaleException.DEFAULT_MESSAGE, exception, "", "");
        }
    }

    @Override
    public CustomerBrokerNegotiation getSale(UUID negotiationId) throws CantGetSaleException{
        try {
            for(CustomerBrokerNegotiation sale : saleNegotiationManager.getNegotiationsByBroker(identity)){
                if(sale.getNegotiationId().equals(negotiationId))
                    return sale;
            }
            throw new CantGetSaleException(CantGetSaleException.DEFAULT_MESSAGE, null, "Negotiation ID: " + negotiationId.toString(), "NegotiationId not Found");
        } catch (CantListSaleNegotianionsException e) {
            throw new CantGetSaleException(CantGetSaleException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerNegotiation> getSales() throws CantGetSaleException{
        try {
            HashSet<CustomerBrokerNegotiation> sales = new HashSet<>();
            sales.addAll(saleNegotiationManager.getNegotiationsByBroker(identity));
            return sales;
        } catch (CantListSaleNegotianionsException e) {
            throw new CantGetSaleException(CantGetSaleException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerNegotiation> getSales(NegotiationStatus status) throws CantGetSaleException{
        try {
            HashSet<CustomerBrokerNegotiation> sales = new HashSet<>();
            for(CustomerBrokerNegotiation sale : saleNegotiationManager.getNegotiationsByBroker(identity)){
                if(sale.getStatus() == status)
                    sales.add(sale);
            }
            return sales;
        } catch (CantListSaleNegotianionsException e) {
            throw new CantGetSaleException(CantGetSaleException.DEFAULT_MESSAGE, e, "", "");
        }

    }

    @Override
    public ActorIdentity getIdentity() {
        return identity;
    }

    @Override
    public boolean equals(Object o){
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
