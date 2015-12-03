package com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantupdateCustomerBrokerContractPurchaseException;

import java.util.List;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerContractPurchaseManager {

    List<CustomerBrokerContractPurchase> getAllCustomerBrokerContractPurchaseFromCurrentDeviceUser() throws CantGetListCustomerBrokerContractPurchaseException;

    CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseForContractId(final String ContractId) throws CantGetListCustomerBrokerContractPurchaseException;

    CustomerBrokerContractPurchase createCustomerBrokerContractPurchase(CustomerBrokerContractPurchase contract) throws CantCreateCustomerBrokerContractPurchaseException;

    void updateStatusCustomerBrokerPurchaseContractStatus(String contractId, ContractStatus status) throws CantupdateCustomerBrokerContractPurchaseException;

    void updateStatusCustomerBrokerPurchaseContractClauseStatus(String contractId, ContractClause clause) throws CantupdateCustomerBrokerContractPurchaseException;

}
