package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.ListsForStatusPurchase;

import java.util.Collection;

/**
 * Created by angel on 10/12/15.
 */

public class ListsForStatusPurchaseInformation implements ListsForStatusPurchase {

    private Collection<CustomerBrokerContractPurchase> contractsWaitingForBroker;
    private Collection<CustomerBrokerContractPurchase> contractsWaitingForCustomer;
    private Collection<CustomerBrokerContractPurchase> historyContracts;

    public ListsForStatusPurchaseInformation() {

    }

    @Override
    public Collection<CustomerBrokerContractPurchase> getContractsWaitingForBroker() {
        return this.contractsWaitingForBroker;
    }

    @Override
    public Collection<CustomerBrokerContractPurchase> getContractsWaitingForCustomer() {
        return this.contractsWaitingForCustomer;
    }

    @Override
    public Collection<CustomerBrokerContractPurchase> getHistoryContracts() {
        return this.historyContracts;
    }

    @Override
    public void setContractsWaitingForBroker(Collection<CustomerBrokerContractPurchase> contractsWaitingForBroker) {
        this.contractsWaitingForBroker = contractsWaitingForBroker;
    }

    @Override
    public void setContractsWaitingForCustomer(Collection<CustomerBrokerContractPurchase> contractsWaitingForCustomer) {
        this.contractsWaitingForCustomer = contractsWaitingForCustomer;
    }

    @Override
    public void setHistoryContracts(Collection<CustomerBrokerContractPurchase> historyContracts) {
        this.historyContracts = historyContracts;
    }

}
