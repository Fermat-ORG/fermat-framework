package com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerContractPurchaseManager {

    List<CustomerBrokerContractPurchase> getAllCustomerBrokerContractPurchaseFromCurrentDeviceUser() throws com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;

    CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseForContractId(final UUID ContractId) throws com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;

    DatabaseTableRecord getCustomerBrokerPurchaseContractTable();

    CustomerBrokerContractPurchase createCustomerBrokerContractPurchase(
            String publicKeyCustomer,
            String publicKeyBroker,
            Float merchandiseAmount,
            CurrencyType merchandiseCurrency,
            Float referencePrice,
            ReferenceCurrency referenceCurrency,
            Float paymentAmount,
            CurrencyType paymentCurrency,
            long paymentExpirationDate,
            long merchandiseDeliveryExpirationDate) throws com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;

    void updateCustomerBrokerContractPurchase(UUID contractId, ContractStatus status) throws com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantupdateCustomerBrokerContractPurchaseException;

    void deleteCustomerBrokerContractPurchase(UUID contractID) throws com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantDeleteCustomerBrokerContractPurchaseException;
}
