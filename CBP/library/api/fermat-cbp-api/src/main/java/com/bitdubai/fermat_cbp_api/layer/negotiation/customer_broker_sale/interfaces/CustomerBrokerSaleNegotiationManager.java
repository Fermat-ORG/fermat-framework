package com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetNextClauseTypeException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 10-10-2015.
 * Update by Angel on 03/12/15
 */
public interface CustomerBrokerSaleNegotiationManager {
    void createCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantCreateCustomerBrokerSaleNegotiationException;
    void updateCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException;

    void    cancelNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException;
    boolean closeNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException;

    void sendToBroker(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException;
    void waitForBroker(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException;

    Collection<CustomerBrokerSaleNegotiation> getNegotiations() throws CantGetListSaleNegotiationsException;
    Collection<CustomerBrokerSaleNegotiation> getNegotiationsByContractId(UUID negotiationId) throws CantGetListSaleNegotiationsException;
    Collection<CustomerBrokerSaleNegotiation> getNegotiationsByStatus(NegotiationStatus status) throws CantGetListSaleNegotiationsException;

    ClauseType getNextClauseType(ClauseType type) throws CantGetNextClauseTypeException;
    ClauseType getNextClauseTypeByCurrencyType(CurrencyType paymentMethod) throws CantGetNextClauseTypeException;

    void createNewLocation(String location);
    void updateLocation(NegotiationLocations location);
    void deleteLocation(NegotiationLocations location);
    Collection<NegotiationLocations> getAllLocations();

    void createNewBankAccount(NegotiationBankAccount bankAccount);
    void updateBankAccount(NegotiationBankAccount bankAccount);
    void deleteBankAccount(NegotiationBankAccount bankAccount);
    Collection<NegotiationBankAccount> getLocationsByCurrencyType(FiatCurrency currency);

    void createNewPaymentCurrency(FiatCurrency currency);
    void deletePaymentCurrency(FiatCurrency currency);
    Collection<FiatCurrency> getAllPaymentCurrencies();

}
