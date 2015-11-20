package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetNegotiationsWaitingForCustomerException;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by nelson on 22/09/15.
 */
public interface CryptoBrokerWallet {

    /**
     * Add a new clause to the negotiation
     *
     * @param negotiation the negotiation where is going the clause is going to be added
     * @param clause      the clause to be added
     * @return the {@link com.bitdubai.fermat_cbp_api.layer.wallet_module.common.CustomerBrokerNegotiationInformation} with added clause
     */
    com.bitdubai.fermat_cbp_api.layer.wallet_module.common.CustomerBrokerNegotiationInformation addClause(com.bitdubai.fermat_cbp_api.layer.wallet_module.common.CustomerBrokerNegotiationInformation negotiation, com.bitdubai.fermat_cbp_api.layer.wallet_module.common.ClauseInformation clause);

    /**
     * modify the data of a clause's negotiation
     *
     * @param negotiation the negotiation with the clause to be modified
     * @param clause      the modified clause
     * @return the {@link com.bitdubai.fermat_cbp_api.layer.wallet_module.common.CustomerBrokerNegotiationInformation} with modified clause
     */
    com.bitdubai.fermat_cbp_api.layer.wallet_module.common.CustomerBrokerNegotiationInformation changeClause(com.bitdubai.fermat_cbp_api.layer.wallet_module.common.CustomerBrokerNegotiationInformation negotiation, com.bitdubai.fermat_cbp_api.layer.wallet_module.common.ClauseInformation clause);

    /**
     * Return as much as "max" results from the list of Negotiation's Basic Info in this wallet waiting for my response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Negotiation Basic Info
     */
    Collection<com.bitdubai.fermat_cbp_api.layer.wallet_module.common.CustomerBrokerNegotiationInformation> getNegotiationsWaitingForBroker(int max, int offset) throws com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetNegotiationsWaitingForBrokerException;

    /**
     * Return as much as "max" results from the list of Negotiation's Basic Info in this wallet waiting for the Customer's response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Negotiation Basic Info
     */
    Collection<com.bitdubai.fermat_cbp_api.layer.wallet_module.common.CustomerBrokerNegotiationInformation> getNegotiationsWaitingForCustomer(int max, int offset) throws CantGetNegotiationsWaitingForCustomerException;

    /**
     * Return as much as "max" results from the list of Contract Basic Info in this wallet waiting for my response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Contract Basic Info
     */
    Collection<ContractBasicInformation> getContractsWaitingForBroker(int max, int offset) throws com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetContractsWaitingForBrokerException;

    /**
     * Return as much as "max" results from the list of Contract Basic Info in this wallet waiting for the Customer's response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Contract Basic Info
     */
    Collection<ContractBasicInformation> getContractsWaitingForCustomer(int max, int offset) throws com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetContractsWaitingForCustomerException;

    /**
     * @return a summary of the current market rate for the different currencies the broker have as stock
     */
    Collection<com.bitdubai.fermat_cbp_api.layer.wallet_module.common.IndexInfoSummary> getCurrentIndexSummaryForStockCurrencies() throws com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCurrentIndexSummaryForStockCurrenciesException;

    /**
     * Return as much as "max" results from the list of Contract Basic Info in this wallet waiting for my response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Contract Basic Info
     */
    Collection<ContractBasicInformation> getContractsHistory(ContractStatus status, int max, int offset) throws com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetContractHistoryException;

    /**
     * Confirm the given negotiation to create a contract based on this negotiation
     *
     * @param negotiation the negotiation ID
     */
    com.bitdubai.fermat_cbp_api.layer.wallet_module.common.CustomerBrokerNegotiationInformation confirmNegotiation(com.bitdubai.fermat_cbp_api.layer.wallet_module.common.CustomerBrokerNegotiationInformation negotiation);

    /**
     * Cancel a current negotiation
     *
     * @param negotiation the negotiation ID
     * @param reason      the reason to cancel
     */
    com.bitdubai.fermat_cbp_api.layer.wallet_module.common.CustomerBrokerNegotiationInformation cancelNegotiation(com.bitdubai.fermat_cbp_api.layer.wallet_module.common.CustomerBrokerNegotiationInformation negotiation, String reason);


    /**
     * Get information about the current stock
     *
     * @param stockCurrency the stock currency
     * @return information about the current stock
     */
    StockInformation getCurrentStock(String stockCurrency);

    /**
     * Get stock staticstics data about the given stock currency
     *
     * @param stockCurrency the stock currency
     * @return stock statistics data
     */
    StockStatistics getStockStatistics(String stockCurrency);

    /**
     * associate an Identity to this wallet
     *
     * @param brokerId the Crypto Broker ID who is going to be associated with this wallet
     */
    void associateIdentity(UUID brokerId);

    /**
     * @return list of identities associated with this wallet
     */
    List<CryptoBrokerIdentity> getListOfIdentities() throws com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerIdentityListException;
}
