package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.exceptions.CantDeleteCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.exceptions.CantupdateCustomerBrokerContractPurchaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerContractPurchaseManager {

    List<CustomerBrokerContractPurchase> getAllCustomerBrokerContractPurchaseFromCurrentDeviceUser() throws CantGetListCustomerBrokerContractPurchaseException;

    CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseForContractId(final UUID ContractId) throws CantGetListCustomerBrokerContractPurchaseException;

    DatabaseTableRecord getCustomerBrokerSaleContractTable();

    public CustomerBrokerContractPurchase createCustomerBrokerContractPurchase(
            String publicKeyCustomer,
            String publicKeyBroker,
            Float merchandiseAmount,
            CurrencyType merchandiseCurrency,
            Float referencePrice,
            ReferenceCurrency referenceCurrency,
            Float paymentAmount,
            CurrencyType paymentCurrency,
            long paymentExpirationDate,
            long merchandiseDeliveryExpirationDate) throws CantCreateCustomerBrokerContractPurchaseException;

    public void updateCustomerBrokerContractPurchase(UUID contractId, ContractStatus status) throws CantupdateCustomerBrokerContractPurchaseException;

    void deleteCustomerBrokerContractPurchase(UUID contractID) throws CantDeleteCustomerBrokerContractPurchaseException;
}
