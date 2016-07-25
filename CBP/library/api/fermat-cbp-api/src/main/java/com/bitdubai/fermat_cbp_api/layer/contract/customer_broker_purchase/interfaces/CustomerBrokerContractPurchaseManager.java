package com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerContractPurchaseException;

import java.util.Collection;

/**
 * Created by angel on 16/9/15.
 */

public interface CustomerBrokerContractPurchaseManager extends FermatManager {

    /**
     * @return a list of all contracts
     * @throws CantGetListCustomerBrokerContractPurchaseException
     */
    Collection<CustomerBrokerContractPurchase> getAllCustomerBrokerContractPurchase() throws CantGetListCustomerBrokerContractPurchaseException;

    /**
     * @param ContractId
     * @return a CustomerBrokerContractPurchase with information of contract with ContractId
     * @throws CantGetListCustomerBrokerContractPurchaseException
     */
    CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseForContractId(final String ContractId) throws CantGetListCustomerBrokerContractPurchaseException;

    /**
     * @param status
     * @return an Collection of CustomerBrokerContractPurchase with information of contract with status
     * @throws CantGetListCustomerBrokerContractPurchaseException
     */
    Collection<CustomerBrokerContractPurchase> getCustomerBrokerContractPurchaseForStatus(final ContractStatus status) throws CantGetListCustomerBrokerContractPurchaseException;

    /**
     * @return an ListsForStatus with separate lists and sorted by status
     */
    ListsForStatusPurchase getCustomerBrokerContractHistory() throws CantGetListCustomerBrokerContractPurchaseException;

    /**
     * @param contract
     * @return a CustomerBrokerContractPurchase with information of contract created
     * @throws CantCreateCustomerBrokerContractPurchaseException
     */
    CustomerBrokerContractPurchase createCustomerBrokerContractPurchase(CustomerBrokerContractPurchase contract) throws CantCreateCustomerBrokerContractPurchaseException;

    /**
     * @param contractId
     * @param status
     * @throws CantUpdateCustomerBrokerContractPurchaseException
     */
    void updateStatusCustomerBrokerPurchaseContractStatus(String contractId, ContractStatus status) throws CantUpdateCustomerBrokerContractPurchaseException;

    /**
     * @param contractId
     * @param status
     * @throws CantUpdateCustomerBrokerContractPurchaseException
     */
    void updateContractNearExpirationDatetime(String contractId, Boolean status) throws CantUpdateCustomerBrokerContractPurchaseException;

    /**
     * @param contractId
     * @param reason
     * @throws CantUpdateCustomerBrokerContractPurchaseException
     */
    void cancelContract(String contractId, String reason) throws CantUpdateCustomerBrokerContractPurchaseException;
}
