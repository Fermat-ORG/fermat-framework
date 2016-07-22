package com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantUpdateCustomerBrokerContractSaleException;

import java.util.Collection;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerContractSaleManager extends FermatManager {

    /**
     * @return a list of all contracts
     * @throws CantGetListCustomerBrokerContractSaleException
     */
    Collection<CustomerBrokerContractSale> getAllCustomerBrokerContractSale() throws CantGetListCustomerBrokerContractSaleException;

    /**
     * @param ContractId
     * @return a CustomerBrokerContractSale with information of contract with ContractId
     * @throws CantGetListCustomerBrokerContractSaleException
     */
    CustomerBrokerContractSale getCustomerBrokerContractSaleForContractId(final String ContractId) throws CantGetListCustomerBrokerContractSaleException;

    /**
     * @param status
     * @return an Collection of CustomerBrokerContractSale with information of contract with status
     * @throws CantGetListCustomerBrokerContractSaleException
     */
    Collection<CustomerBrokerContractSale> getCustomerBrokerContractSaleForStatus(final ContractStatus status) throws CantGetListCustomerBrokerContractSaleException;

    /**
     * @return an ListsForStatus with separate lists and sorted by status
     */
    ListsForStatusSale getCustomerBrokerContractHistory() throws CantGetListCustomerBrokerContractSaleException;

    /**
     * @param contract
     * @return a CustomerBrokerContractSale with information of contract created
     * @throws CantCreateCustomerBrokerContractSaleException
     */
    CustomerBrokerContractSale createCustomerBrokerContractSale(CustomerBrokerContractSale contract) throws CantCreateCustomerBrokerContractSaleException;

    /**
     * @param contractId
     * @param status
     * @throws CantUpdateCustomerBrokerContractSaleException
     */
    void updateStatusCustomerBrokerSaleContractStatus(String contractId, ContractStatus status) throws CantUpdateCustomerBrokerContractSaleException;

    /**
     * @param contractId
     * @param status
     * @throws CantUpdateCustomerBrokerContractSaleException
     */
    void updateContractNearExpirationDatetime(String contractId, Boolean status) throws CantUpdateCustomerBrokerContractSaleException;

    /**
     * @param contractId
     * @param reason
     * @throws CantUpdateCustomerBrokerContractSaleException
     */
    void cancelContract(String contractId, String reason) throws CantUpdateCustomerBrokerContractSaleException;

}
