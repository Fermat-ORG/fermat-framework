package com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ActorType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListBankAccountsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListLocationsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetNextClauseTypeException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 10-10-2015.
 * Update by Angel on 29/11/2015
 */

public interface CustomerBrokerPurchaseNegotiationManager extends FermatManager {
    /**
     * @param negotiation
     * @throws CantCreateCustomerBrokerPurchaseNegotiationException
     */
    void createCustomerBrokerPurchaseNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantCreateCustomerBrokerPurchaseNegotiationException;

    /**
     * @param negotiation
     * @throws CantUpdateCustomerBrokerPurchaseNegotiationException
     */
    void updateCustomerBrokerPurchaseNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException;

    /**
     * @param negotiationId
     * @param status
     * @throws CantUpdateCustomerBrokerPurchaseNegotiationException
     */
    void updateNegotiationNearExpirationDatetime(UUID negotiationId, Boolean status) throws CantUpdateCustomerBrokerPurchaseNegotiationException;

    /**
     * @param negotiation
     * @throws CantUpdateCustomerBrokerPurchaseNegotiationException
     */
    void cancelNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException;

    /**
     * @param negotiation
     * @return
     * @throws CantUpdateCustomerBrokerPurchaseNegotiationException
     */
    boolean closeNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException;

    /**
     * @param negotiationId
     * @return
     * @throws CantUpdateCustomerBrokerPurchaseNegotiationException
     */
    boolean closeNegotiation(UUID negotiationId) throws CantUpdateCustomerBrokerPurchaseNegotiationException;

    /**
     * @param negotiation
     * @throws CantUpdateCustomerBrokerPurchaseNegotiationException
     */
    void sendToBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException;

    /**
     * @param negotiation
     * @throws CantUpdateCustomerBrokerPurchaseNegotiationException
     */
    void waitForBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException;

    /**
     * @param negotiation
     * @throws CantUpdateCustomerBrokerPurchaseNegotiationException
     */
    void waitForCustomer(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException;

    /**
     * @param negotiation
     * @throws CantUpdateCustomerBrokerPurchaseNegotiationException
     */
    void waitForClosing(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException;

    /**
     * @return
     * @throws CantGetListPurchaseNegotiationsException
     */
    Collection<CustomerBrokerPurchaseNegotiation> getNegotiations() throws CantGetListPurchaseNegotiationsException;

    /**
     * @param negotiationId
     * @return
     * @throws CantGetListPurchaseNegotiationsException
     */
    CustomerBrokerPurchaseNegotiation getNegotiationsByNegotiationId(UUID negotiationId) throws CantGetListPurchaseNegotiationsException;

    /**
     * @param status
     * @return
     * @throws CantGetListPurchaseNegotiationsException
     */
    Collection<CustomerBrokerPurchaseNegotiation> getNegotiationsByStatus(NegotiationStatus status) throws CantGetListPurchaseNegotiationsException;

    /**
     * @param actorType
     * @return
     * @throws CantGetListPurchaseNegotiationsException
     */
    Collection<CustomerBrokerPurchaseNegotiation> getNegotiationsBySendAndWaiting(ActorType actorType) throws CantGetListPurchaseNegotiationsException;

    /**
     * @param type
     * @return
     * @throws CantGetNextClauseTypeException
     */
    ClauseType getNextClauseType(ClauseType type) throws CantGetNextClauseTypeException;

    /**
     * @param paymentMethod
     * @return
     * @throws CantGetNextClauseTypeException
     */
    ClauseType getNextClauseTypeByCurrencyType(MoneyType paymentMethod) throws CantGetNextClauseTypeException;

    /**
     * @param location
     * @param uri
     * @throws CantCreateLocationPurchaseException
     */
    void createNewLocation(String location, String uri) throws CantCreateLocationPurchaseException;

    /**
     * @param location
     * @throws CantUpdateLocationPurchaseException
     */
    void updateLocation(NegotiationLocations location) throws CantUpdateLocationPurchaseException;

    /**
     * @param location
     * @throws CantDeleteLocationPurchaseException
     */
    void deleteLocation(NegotiationLocations location) throws CantDeleteLocationPurchaseException;

    /**
     * @return Collection<NegotiationLocations>
     * @throws CantGetListLocationsPurchaseException
     */
    Collection<NegotiationLocations> getAllLocations() throws CantGetListLocationsPurchaseException;

    /**
     * @param bankAccount
     * @throws CantCreateBankAccountPurchaseException
     */
    void createNewBankAccount(NegotiationBankAccount bankAccount) throws CantCreateBankAccountPurchaseException;

    /**
     * @param bankAccount
     * @throws CantUpdateBankAccountPurchaseException
     */
    void updateBankAccount(NegotiationBankAccount bankAccount) throws CantUpdateBankAccountPurchaseException;

    /**
     * @param bankAccount
     * @throws CantDeleteBankAccountPurchaseException
     */
    void deleteBankAccount(NegotiationBankAccount bankAccount) throws CantDeleteBankAccountPurchaseException;

    /**
     * @return a collection with all the NegotiationBankAccount
     * @throws CantGetListBankAccountsPurchaseException
     */
    Collection<NegotiationBankAccount> getAllBankAccount() throws CantGetListBankAccountsPurchaseException;

    /**
     * @param currency
     * @return Collection<NegotiationBankAccount>
     * @throws CantGetListBankAccountsPurchaseException
     */
    Collection<NegotiationBankAccount> getBankAccountByCurrencyType(FiatCurrency currency) throws CantGetListBankAccountsPurchaseException;

    /**
     * @return Collection<FiatCurrency> with the currencies available in the Bank Wallet
     * @throws CantGetListBankAccountsPurchaseException
     */
    Collection<FiatCurrency> getCurrencyTypeAvailableBankAccount() throws CantGetListBankAccountsPurchaseException;
}