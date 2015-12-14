package com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationPaymentCurrency;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateBankAccountSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreatePaymentCurrencySaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteBankAccountSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeletePaymentCurrencySaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListBankAccountsSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListLocationsSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListPaymentCurrencySaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateBankAccountSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetNextClauseTypeException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 10-10-2015.
 * Update by Angel on 03/12/15
 */
public interface CustomerBrokerSaleNegotiationManager extends FermatManager {

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
    void cancelNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException;

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
     * @param uri
     * @throws CantCreateLocationSaleException
     */
    void createNewLocation(String location, String uri) throws CantCreateLocationSaleException;

    /**
     *
     * @param location
     * @throws CantUpdateLocationSaleException
     */
    void updateLocation(NegotiationLocations location) throws CantUpdateLocationSaleException;

    /**
     *
     * @param location
     * @throws CantDeleteLocationSaleException
     */
    void deleteLocation(NegotiationLocations location) throws CantDeleteLocationSaleException;

    /**
     *
     * @return Collection<NegotiationLocations>
     * @throws CantGetListLocationsSaleException
     */
    Collection<NegotiationLocations> getAllLocations() throws CantGetListLocationsSaleException;

    /**
     *
     * @param bankAccount
     * @throws CantCreateBankAccountSaleException
     */
    void createNewBankAccount(NegotiationBankAccount bankAccount) throws CantCreateBankAccountSaleException;

    /**
     *
     * @param bankAccount
     * @throws CantUpdateBankAccountSaleException
     */
    void updateBankAccount(NegotiationBankAccount bankAccount) throws CantUpdateBankAccountSaleException;

    /**
     *
     * @param bankAccount
     * @throws CantDeleteBankAccountSaleException
     */
    void deleteBankAccount(NegotiationBankAccount bankAccount) throws CantDeleteBankAccountSaleException;

    /**
     *
     * @param currency
     * @return Collection<NegotiationBankAccount>
     * @throws CantGetListBankAccountsSaleException
     */
    Collection<NegotiationBankAccount> getBankAccountByCurrencyType(FiatCurrency currency) throws CantGetListBankAccountsSaleException;

    /**
     *
     * @param paymentCurrency
     * @throws CantCreatePaymentCurrencySaleException
     */
    void createNewPaymentCurrency(NegotiationPaymentCurrency paymentCurrency) throws CantCreatePaymentCurrencySaleException;

    /**
     *
     * @param paymentCurrency
     * @throws CantDeletePaymentCurrencySaleException
     */
    void deletePaymentCurrency(NegotiationPaymentCurrency paymentCurrency) throws CantDeletePaymentCurrencySaleException;

    /**
     *
     * @return Collection<Currency>
     * @throws CantGetListPaymentCurrencySaleException
     */
    Collection<NegotiationPaymentCurrency> getAllPaymentCurrencies() throws CantGetListPaymentCurrencySaleException;
}
