package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantUpdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.ListsForStatusSale;

import java.util.Collection;

/**
 * This mock is only for testing
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/01/16.
 */
public class CustomerBrokerContractSaleManagerMock implements CustomerBrokerContractSaleManager {
    @Override
    public Collection<CustomerBrokerContractSale> getAllCustomerBrokerContractSale() throws CantGetListCustomerBrokerContractSaleException {
        return null;
    }

    @Override
    public CustomerBrokerContractSale getCustomerBrokerContractSaleForContractId(String ContractId) throws CantGetListCustomerBrokerContractSaleException {
        CustomerBrokerContractSale contract = new CustomerBrokerContractSaleMock();
        return contract;
    }

    @Override
    public Collection<CustomerBrokerContractSale> getCustomerBrokerContractSaleForStatus(ContractStatus status) throws CantGetListCustomerBrokerContractSaleException {
        return null;
    }

    @Override
    public ListsForStatusSale getCustomerBrokerContractHistory() throws CantGetListCustomerBrokerContractSaleException {
        return null;
    }

    @Override
    public CustomerBrokerContractSale createCustomerBrokerContractSale(CustomerBrokerContractSale contract) throws CantCreateCustomerBrokerContractSaleException {
        return null;
    }

    @Override
    public void updateStatusCustomerBrokerSaleContractStatus(String contractId, ContractStatus status) throws CantUpdateCustomerBrokerContractSaleException {

    }

    @Override
    public void updateContractNearExpirationDatetime(String contractId, Boolean status) throws CantUpdateCustomerBrokerContractSaleException {

    }

    @Override
    public void cancelContract(String contractId, String reason) throws CantUpdateCustomerBrokerContractSaleException {

    }
}
