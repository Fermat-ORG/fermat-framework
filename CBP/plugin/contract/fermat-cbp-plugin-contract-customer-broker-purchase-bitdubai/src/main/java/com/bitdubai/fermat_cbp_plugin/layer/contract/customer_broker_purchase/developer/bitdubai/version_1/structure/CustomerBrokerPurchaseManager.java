package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantupdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.ListsForStatusPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerContractPurchaseDao;

import java.util.Collection;

/**
 * Created by angel on 8/12/15.
 */
public class CustomerBrokerPurchaseManager implements CustomerBrokerContractPurchaseManager {

    private CustomerBrokerContractPurchaseDao customerBrokerContractPurchaseDao;

    public CustomerBrokerPurchaseManager(CustomerBrokerContractPurchaseDao customerBrokerContractPurchaseDao){
        this.customerBrokerContractPurchaseDao = customerBrokerContractPurchaseDao;
    }

    @Override
    public Collection<CustomerBrokerContractPurchase> getAllCustomerBrokerContractPurchase() throws CantGetListCustomerBrokerContractPurchaseException {
        return this.customerBrokerContractPurchaseDao.getAllCustomerBrokerContractPurchase();
    }

    @Override
    public CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseForContractId(String ContractId) throws CantGetListCustomerBrokerContractPurchaseException {
        return this.customerBrokerContractPurchaseDao.getCustomerBrokerPurchaseContractForcontractID(ContractId);
    }

    @Override
    public Collection<CustomerBrokerContractPurchase> getCustomerBrokerContractPurchaseForStatus(ContractStatus status) throws CantGetListCustomerBrokerContractPurchaseException {
        return this.customerBrokerContractPurchaseDao.getCustomerBrokerContractPurchaseForStatus(status);
    }

    @Override
    public ListsForStatusPurchase getCustomerBrokerContractHistory() throws CantGetListCustomerBrokerContractPurchaseException{
        return this.customerBrokerContractPurchaseDao.getCustomerBrokerContractHistory();
    }

    @Override
    public CustomerBrokerContractPurchase createCustomerBrokerContractPurchase(CustomerBrokerContractPurchase contract) throws CantCreateCustomerBrokerContractPurchaseException {
        return this.customerBrokerContractPurchaseDao.createCustomerBrokerPurchaseContract(contract);
    }

    @Override
    public void updateStatusCustomerBrokerPurchaseContractStatus(String contractId, ContractStatus status) throws CantupdateCustomerBrokerContractPurchaseException {
        this.customerBrokerContractPurchaseDao.updateStatusCustomerBrokerPurchaseContract(contractId, status);
    }

    @Override
    public void updateContractNearExpirationDatetime(String contractId, Boolean status) throws CantupdateCustomerBrokerContractPurchaseException {
        this.updateContractNearExpirationDatetime(contractId, status);
    }

}
