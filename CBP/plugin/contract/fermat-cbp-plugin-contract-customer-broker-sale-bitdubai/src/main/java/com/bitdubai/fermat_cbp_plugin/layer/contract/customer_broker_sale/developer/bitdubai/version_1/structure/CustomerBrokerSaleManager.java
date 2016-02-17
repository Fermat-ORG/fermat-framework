package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantUpdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.ListsForStatusSale;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerContractSaleDao;

import java.util.Collection;

/**
 * Created by angel on 10/12/15.
 */

public class CustomerBrokerSaleManager implements CustomerBrokerContractSaleManager {

    private CustomerBrokerContractSaleDao customerBrokerContractSaleDao;

    public CustomerBrokerSaleManager(CustomerBrokerContractSaleDao customerBrokerContractSaleDao){
        this.customerBrokerContractSaleDao = customerBrokerContractSaleDao;
    }

    @Override
    public Collection<CustomerBrokerContractSale> getAllCustomerBrokerContractSale() throws CantGetListCustomerBrokerContractSaleException {
        return this.customerBrokerContractSaleDao.getAllCustomerBrokerContractSale();
    }

    @Override
    public CustomerBrokerContractSale getCustomerBrokerContractSaleForContractId(String contractId) throws CantGetListCustomerBrokerContractSaleException {
        return this.customerBrokerContractSaleDao.getCustomerBrokerSaleContractForcontractID(contractId);
    }

    @Override
    public Collection<CustomerBrokerContractSale> getCustomerBrokerContractSaleForStatus(ContractStatus status) throws CantGetListCustomerBrokerContractSaleException {
        return this.customerBrokerContractSaleDao.getCustomerBrokerContractSaleForStatus(status);
    }

    @Override
    public ListsForStatusSale getCustomerBrokerContractHistory() throws CantGetListCustomerBrokerContractSaleException {
        return this.customerBrokerContractSaleDao.getCustomerBrokerContractHistory();
    }

    @Override
    public CustomerBrokerContractSale createCustomerBrokerContractSale(CustomerBrokerContractSale contract) throws CantCreateCustomerBrokerContractSaleException {
        return this.customerBrokerContractSaleDao.createCustomerBrokerSaleContract(contract);
    }

    @Override
    public void updateStatusCustomerBrokerSaleContractStatus(String contractId, ContractStatus status) throws CantUpdateCustomerBrokerContractSaleException {
        this.customerBrokerContractSaleDao.updateStatusCustomerBrokerSaleContract(contractId, status);
    }

    @Override
    public void updateContractNearExpirationDatetime(String contractId, Boolean status) throws CantUpdateCustomerBrokerContractSaleException {
        this.customerBrokerContractSaleDao.updateContractNearExpirationDatetime(contractId, status);
    }

}
