package com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces;

import java.util.Collection;

/**
 * Created by angel on 8/12/15.
 */

public interface ListsForStatusPurchase {

    /**
     * @return a list of contracts on waiting for Broker
     */
    Collection<CustomerBrokerContractPurchase> getContractsWaitingForBroker();

    /**
     * @return a list of contracts on waiting for Customer
     */
    Collection<CustomerBrokerContractPurchase> getContractsWaitingForCustomer();

    /**
     * @return a list of all contracts cancelled and closed
     */
    Collection<CustomerBrokerContractPurchase> getHistoryContracts();

    /**
     * Set the list of contract that waiting for Broker
     *
     * @param contractsWaitingForBroker
     */
    void setContractsWaitingForBroker(Collection<CustomerBrokerContractPurchase> contractsWaitingForBroker);

    /**
     * Set the list of contract that waiting for Customer
     *
     * @param contractsWaitingForCustomer
     */
    void setContractsWaitingForCustomer(Collection<CustomerBrokerContractPurchase> contractsWaitingForCustomer);

    /**
     * Set the list with history of contracts
     *
     * @param historyContracts
     */
    void setHistoryContracts(Collection<CustomerBrokerContractPurchase> historyContracts);

}
