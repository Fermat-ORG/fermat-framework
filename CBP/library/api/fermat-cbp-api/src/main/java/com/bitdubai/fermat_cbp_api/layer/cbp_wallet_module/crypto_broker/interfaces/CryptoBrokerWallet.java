package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetContractsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetContractsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetNegotiationsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetNegotiationsWaitingForCustomerException;

import java.util.List;
import java.util.UUID;

/**
 * Created by nelson on 22/09/15.
 */
public interface CryptoBrokerWallet {

    /**
     * Return as much as "max" results from the list of Negotiation's Basic Info in this wallet waiting for my response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Negotiation Basic Info
     */
    List<NegotiationBasicInformation> getNegotiationsWaitingForBroker(int max, int offset) throws CantGetNegotiationsWaitingForBrokerException;

    /**
     * Return as much as "max" results from the list of Negotiation's Basic Info in this wallet waiting for the Customer's response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Negotiation Basic Info
     */
    List<NegotiationBasicInformation> getNegotiationsWaitingForCustomer(int max, int offset) throws CantGetNegotiationsWaitingForCustomerException;

    /**
     * Return as much as "max" results from the list of Contract Basic Info in this wallet waiting for my response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Contract Basic Info
     */
    List<ContractBasicInformation> getContractsWaitingForBroker(int max, int offset) throws CantGetContractsWaitingForBrokerException;

    /**
     * Return as much as "max" results from the list of Contract Basic Info in this wallet waiting for the Customer's response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Contract Basic Info
     */
    List<ContractBasicInformation> getContractsWaitingForCustomer(int max, int offset) throws CantGetContractsWaitingForCustomerException;

    /**
     * Return the negotiation details associated to a given contract ID
     *
     * @param negotiationId the id of the contract
     * @return the negotiation details
     */
    CryptoBrokerNegotiationInformation getNegotiationDetails(UUID negotiationId);

    /**
     * Return the contract details associated to a given contract ID
     *
     * @param contractId the id of the contract
     * @return the contract details
     */
    CryptoBrokerContractInformation getContractDetails(UUID contractId);

    /**
     * Update the negotiation information
     *
     * @param negotiationId          the negotiation ID
     * @param negotiationInformation object with the updated negotiation information
     */
    void updateNegotiationInformation(UUID negotiationId, CryptoBrokerNegotiationInformation negotiationInformation);

    /**
     * Confirm the given negotiation to create a contract based on this negotiation
     *
     * @param negotiationId the negotiation ID
     */
    void confirmNegotiation(UUID negotiationId);

    /**
     * Cancel a current negotiation
     *
     * @param negotiationId the negotiation ID
     * @param reason        the reason to cancel
     */
    void cancelNegotiation(UUID negotiationId, String reason);

    /**
     * Confirm the payment of a contract
     *
     * @param contractId the contract ID
     */
    void confirmPayment(UUID contractId);

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
}
