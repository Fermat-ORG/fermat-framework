package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces;

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
    List<NegotiationBasicInformation> getListOfNegotiationsWaitingForBroker(int max, int offset);

    /**
     * Return as much as "max" results from the list of Negotiation's Basic Info in this wallet waiting for the Customer's response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Negotiation Basic Info
     */
    List<NegotiationBasicInformation> getListOfNegotiationsWaitingForCustomer(int max, int offset);

    /**
     * Return as much as "max" results from the list of Contract Basic Info in this wallet waiting for my response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Contract Basic Info
     */
    List<ContractBasicInformation> getListOfContractsWaitingForBroker(int max, int offset);

    /**
     * Return as much as "max" results from the list of Contract Basic Info in this wallet waiting for the Customer's response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Contract Basic Info
     */
    List<ContractBasicInformation> getListOfContractsWaitingForCustomer(int max, int offset);

    /**
     * Return the contract details associated to a given contract ID
     *
     * @param contractId the id of the contract
     * @return the contract details
     */
    CryptoBrokerContractInformation getContractDetails(UUID contractId);

    void updateContractInformation(CryptoBrokerContractInformation contractInformation);

    void acceptDeal(UUID contractId);

    void acceptBuyRequest(UUID requestId, float merchandisePrice);

    void cancelBuyRequest(UUID requestId);

    void confirmPayment(UUID contractId);

    StockInformation getCurrentStock(String stockCurrency);
}
