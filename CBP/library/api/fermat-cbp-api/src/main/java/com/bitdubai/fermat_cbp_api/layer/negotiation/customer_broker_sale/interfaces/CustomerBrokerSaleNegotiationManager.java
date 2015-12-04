package com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
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

    /**
     *
     * @param negotiation
     * @throws CantCreateCustomerBrokerSaleNegotiationException
     */
    void createCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantCreateCustomerBrokerSaleNegotiationException;

    /**
     *
     * @param negotiation
     * @throws CantUpdateCustomerBrokerSaleException
     */
    void updateCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException;

    /**
     *
     * @param negotiation
     * @throws CantUpdateCustomerBrokerSaleException
     */
    void    cancelNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException;

    /**
     *
     * @param negotiation
     * @return a boolean that indicating if negotiation is closed
     * @throws CantUpdateCustomerBrokerSaleException
     */
    boolean closeNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException;

    /**
     *
     * @param negotiation
     * @throws CantUpdateCustomerBrokerSaleException
     */
    void sendToBroker(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException;

    /**
     *
     * @param negotiation
     * @throws CantUpdateCustomerBrokerSaleException
     */
    void waitForBroker(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException;

    /**
     *
     * @return a list with all negotiations
     * @throws CantGetListSaleNegotiationsException
     */
    Collection<CustomerBrokerSaleNegotiation> getNegotiations() throws CantGetListSaleNegotiationsException;

    /**
     *
     * @param negotiationId
     * @return a CustomerBrokerSaleNegotiation with data of Negotiation with id negotiationId
     * @throws CantGetListSaleNegotiationsException
     */
    CustomerBrokerSaleNegotiation getNegotiationsByNegotiationId(UUID negotiationId) throws CantGetListSaleNegotiationsException;

    /**
     *
     * @param status
     * @return a list CustomerBrokerSaleNegotiations with data that match status
     * @throws CantGetListSaleNegotiationsException
     */
    Collection<CustomerBrokerSaleNegotiation> getNegotiationsByStatus(NegotiationStatus status) throws CantGetListSaleNegotiationsException;

    /**
     *
     * @param type
     * @return the following ClauseType to specify
     * @throws CantGetNextClauseTypeException
     */
    ClauseType getNextClauseType(ClauseType type) throws CantGetNextClauseTypeException;

    /**
     *
     * @param paymentMethod
     * @return the following ClauseType to specify depending on the paymentMethod
     * @throws CantGetNextClauseTypeException
     */
    ClauseType getNextClauseTypeByCurrencyType(CurrencyType paymentMethod) throws CantGetNextClauseTypeException;

    /**
     *
     * @param location
     */
    void createNewLocation(String location);

    /**
     *
     * @param location
     */
    void updateLocation(NegotiationLocations location);

    /**
     *
     * @param location
     */
    void deleteLocation(NegotiationLocations location);

    /**
     *
     * @return a list with all Locations
     */
    Collection<NegotiationLocations> getAllLocations();

    /**
     *
     * @param bankAccount
     */
    void createNewBankAccount(NegotiationBankAccount bankAccount);

    /**
     *
     * @param bankAccount
     */
    void updateBankAccount(NegotiationBankAccount bankAccount);

    /**
     *
     * @param bankAccount
     */
    void deleteBankAccount(NegotiationBankAccount bankAccount);

    /**
     *
     * @param currency
     * @return  a list BankAccount with data that match currency
     */
    Collection<NegotiationBankAccount> getBankAccountByCurrencyType(Currency currency);

    /**
     *
     * @param currency
     */
    void createNewPaymentCurrency(Currency currency);

    /**
     *
     * @param currency
     */
    void deletePaymentCurrency(Currency currency);

    /**
     *
     * @return a list with all PaymentCurrencies
     */
    Collection<Currency> getAllPaymentCurrencies();

}
