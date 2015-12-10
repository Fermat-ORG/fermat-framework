package com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces;

import java.util.Collection;

/**
 * Created by angel on 8/12/15.
 */
public interface ListsForStatus {

    /**
     *
     * @return a list of contracts on waiting for Broker
     */
    Collection<CustomerBrokerContractPurchase> getContractsWaitingForBroker();

    /**
     *
     * @return a list of contracts on waiting for Customer
     */
    Collection<CustomerBrokerContractPurchase> getContractsWaitingForCustomer();

    /**
     *
     * @return a list of all contracts cancelled and closed
     */
    Collection<CustomerBrokerContractPurchase> getHistoryContracts();

}
