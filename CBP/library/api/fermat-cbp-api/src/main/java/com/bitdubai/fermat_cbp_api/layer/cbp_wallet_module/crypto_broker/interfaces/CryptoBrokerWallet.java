package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetContractsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetContractsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetCurrentIndexSumaryForStockCurrenciesException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetNegotiationsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetNegotiationsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerIdentityListException;

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
     * @return the {@link CustomerBrokerNegotiationInformation} with added clause
     */
    CustomerBrokerNegotiationInformation addClause(CustomerBrokerNegotiationInformation negotiation, ClauseInformation clause);

    /**
     * modify the data of a clause's negotiation
     *
     * @param negotiation the negotiation with the clause to be modified
     * @param clause      the modified clause
     * @return the {@link CustomerBrokerNegotiationInformation} with modified clause
     */
    CustomerBrokerNegotiationInformation changeClause(CustomerBrokerNegotiationInformation negotiation, ClauseInformation clause);

    /**
     * Return as much as "max" results from the list of Negotiation's Basic Info in this wallet waiting for my response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Negotiation Basic Info
     */
    Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForBroker(int max, int offset) throws CantGetNegotiationsWaitingForBrokerException;

    /**
     * Return as much as "max" results from the list of Negotiation's Basic Info in this wallet waiting for the Customer's response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Negotiation Basic Info
     */
    Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForCustomer(int max, int offset) throws CantGetNegotiationsWaitingForCustomerException;

    /**
     * Return as much as "max" results from the list of Contract Basic Info in this wallet waiting for my response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Contract Basic Info
     */
    Collection<ContractBasicInformation> getContractsWaitingForBroker(int max, int offset) throws CantGetContractsWaitingForBrokerException;

    /**
     * Return as much as "max" results from the list of Contract Basic Info in this wallet waiting for the Customer's response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Contract Basic Info
     */
    Collection<ContractBasicInformation> getContractsWaitingForCustomer(int max, int offset) throws CantGetContractsWaitingForCustomerException;

    /**
     * @return a summary of the current market rate for the different currencies the broker have as stock
     */
    Collection<IndexInfoSummary> getCurrentIndexSummaryForStockCurrencies() throws CantGetCurrentIndexSumaryForStockCurrenciesException;
    /**
     * Confirm the given negotiation to create a contract based on this negotiation
     *
     * @param negotiation the negotiation ID
     */
    CustomerBrokerNegotiationInformation confirmNegotiation(CustomerBrokerNegotiationInformation negotiation);

    /**
     * Cancel a current negotiation
     *
     * @param negotiation the negotiation ID
     * @param reason      the reason to cancel
     */
    CustomerBrokerNegotiationInformation cancelNegotiation(CustomerBrokerNegotiationInformation negotiation, String reason);


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
    List<CryptoBrokerIdentity> getListOfIdentities() throws CantGetCryptoCustomerIdentityListException;
}
