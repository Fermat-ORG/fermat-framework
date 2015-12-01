package com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantupdateCustomerBrokerContractSaleException;

import java.util.List;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerContractSaleManager {

    List<CustomerBrokerContractSale> getAllCustomerBrokerContractSaleFromCurrentDeviceUser() throws CantGetListCustomerBrokerContractSaleException;

    CustomerBrokerContractSale getCustomerBrokerContractSaleForContractId(final String ContractId) throws CantGetListCustomerBrokerContractSaleException;

    CustomerBrokerContractSale createCustomerBrokerContractSale(CustomerBrokerContractSale contract) throws CantCreateCustomerBrokerContractSaleException;

    void updateStatusCustomerBrokerSaleContractStatus(String contractId, ContractStatus status) throws CantupdateCustomerBrokerContractSaleException;

    void updateStatusCustomerBrokerSaleContractClauseStatus(String contractId, ContractClause clause) throws CantupdateCustomerBrokerContractSaleException;

}
