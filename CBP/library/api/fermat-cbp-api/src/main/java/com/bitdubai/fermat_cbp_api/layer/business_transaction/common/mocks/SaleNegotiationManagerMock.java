package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ActorType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListLocationsSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetNextClauseTypeException;

import java.util.Collection;
import java.util.UUID;

/**
 * This object is only for testing
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 28/12/15.
 */
public class SaleNegotiationManagerMock implements CustomerBrokerSaleNegotiationManager {

    @Override
    public void createCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantCreateCustomerBrokerSaleNegotiationException {

    }

    @Override
    public void updateCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {

    }

    @Override
    public void updateNegotiationNearExpirationDatetime(UUID negotiationId, Boolean status) throws CantUpdateCustomerBrokerSaleException {

    }

    @Override
    public void cancelNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {

    }

    @Override
    public boolean closeNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        return false;
    }

    @Override
    public boolean closeNegotiation(UUID negotiation) throws CantUpdateCustomerBrokerSaleException {
        return false;
    }

    @Override
    public void sendToCustomer(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {

    }

    @Override
    public void waitForCustomer(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {

    }

    @Override
    public void waitForClosing(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {

    }

    @Override
    public void sendToBroker(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {

    }

    @Override
    public void waitForBroker(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {

    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiations() throws CantGetListSaleNegotiationsException {
        return null;
    }

    @Override
    public CustomerBrokerSaleNegotiation getNegotiationsByNegotiationId(UUID negotiationId) throws CantGetListSaleNegotiationsException {
        return new SaleNegotiationOfflineMock();
        //return new SaleNegotiationOnlineMock();
    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiationsByStatus(NegotiationStatus status) throws CantGetListSaleNegotiationsException {
        return null;
    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiationsBySendAndWaiting(ActorType actorType) throws CantGetListSaleNegotiationsException {
        return null;
    }

    @Override
    public ClauseType getNextClauseType(ClauseType type) throws CantGetNextClauseTypeException {
        return null;
    }

    @Override
    public ClauseType getNextClauseTypeByCurrencyType(MoneyType paymentMethod) throws CantGetNextClauseTypeException {
        return null;
    }

    @Override
    public void createNewLocation(String location, String uri) throws CantCreateLocationSaleException {

    }

    @Override
    public void updateLocation(NegotiationLocations location) throws CantUpdateLocationSaleException {

    }

    @Override
    public void deleteLocation(NegotiationLocations location) throws CantDeleteLocationSaleException {

    }

    @Override
    public Collection<NegotiationLocations> getAllLocations() throws CantGetListLocationsSaleException {
        return null;
    }
}
