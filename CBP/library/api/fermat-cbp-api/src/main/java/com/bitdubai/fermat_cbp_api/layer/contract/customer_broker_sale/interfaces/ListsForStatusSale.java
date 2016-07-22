package com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces;

import java.util.Collection;

/**
 * Created by angel on 8/12/15.
 */

public interface ListsForStatusSale {

    /**
     * @return a list of contracts on waiting for Broker
     */
    Collection<CustomerBrokerContractSale> getContractsWaitingForBroker();

    /**
     * @return a list of contracts on waiting for Customer
     */
    Collection<CustomerBrokerContractSale> getContractsWaitingForCustomer();

    /**
     * @return a list of all contracts cancelled and closed
     */
    Collection<CustomerBrokerContractSale> getHistoryContracts();

    /**
     * Set the list of contract that waiting for Broker
     *
     * @param contractsWaitingForBroker
     */
    void setContractsWaitingForBroker(Collection<CustomerBrokerContractSale> contractsWaitingForBroker);

    /**
     * Set the list of contract that waiting for Customer
     *
     * @param contractsWaitingForCustomer
     */
    void setContractsWaitingForCustomer(Collection<CustomerBrokerContractSale> contractsWaitingForCustomer);

    /**
     * Set the list with history of contracts
     *
     * @param historyContracts
     */
    void setHistoryContracts(Collection<CustomerBrokerContractSale> historyContracts);

}
