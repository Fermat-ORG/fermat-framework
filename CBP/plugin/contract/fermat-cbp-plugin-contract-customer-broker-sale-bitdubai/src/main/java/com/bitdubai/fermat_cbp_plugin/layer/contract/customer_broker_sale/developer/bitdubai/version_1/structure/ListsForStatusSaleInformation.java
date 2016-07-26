package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.ListsForStatusSale;

import java.util.Collection;

/**
 * Created by angel on 10/12/15.
 */

public class ListsForStatusSaleInformation implements ListsForStatusSale {

    private Collection<CustomerBrokerContractSale> contractsWaitingForBroker;
    private Collection<CustomerBrokerContractSale> contractsWaitingForCustomer;
    private Collection<CustomerBrokerContractSale> historyContracts;

    public ListsForStatusSaleInformation() {

    }

    @Override
    public Collection<CustomerBrokerContractSale> getContractsWaitingForBroker() {
        return this.contractsWaitingForBroker;
    }

    @Override
    public Collection<CustomerBrokerContractSale> getContractsWaitingForCustomer() {
        return this.contractsWaitingForCustomer;
    }

    @Override
    public Collection<CustomerBrokerContractSale> getHistoryContracts() {
        return this.historyContracts;
    }

    @Override
    public void setContractsWaitingForBroker(Collection<CustomerBrokerContractSale> contractsWaitingForBroker) {
        this.contractsWaitingForBroker = contractsWaitingForBroker;
    }

    @Override
    public void setContractsWaitingForCustomer(Collection<CustomerBrokerContractSale> contractsWaitingForCustomer) {
        this.contractsWaitingForCustomer = contractsWaitingForCustomer;
    }

    @Override
    public void setHistoryContracts(Collection<CustomerBrokerContractSale> historyContracts) {
        this.historyContracts = historyContracts;
    }

}
