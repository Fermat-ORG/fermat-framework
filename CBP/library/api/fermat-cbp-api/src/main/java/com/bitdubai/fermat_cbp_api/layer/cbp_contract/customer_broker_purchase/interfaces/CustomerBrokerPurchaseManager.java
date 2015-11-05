package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.exceptions.CantDeleteCustomerBrokerPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.exceptions.CantupdateCustomerBrokerPurchaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerPurchaseManager {

    List<CustomerBrokerPurchase> getAllCustomerBrokerPurchaseFromCurrentDeviceUser() throws CantGetListCustomerBrokerPurchaseException;

    CustomerBrokerPurchase getCustomerBrokerPurchaseForContractId(final UUID ContractId) throws CantGetListCustomerBrokerPurchaseException;

    DatabaseTableRecord getCustomerBrokerSaleContractTable();

    public CustomerBrokerPurchase createCustomerBrokerPurchase(
            String publicKeyCustomer,
            String publicKeyBroker,
            Float merchandiseAmount,
            CurrencyType merchandiseCurrency,
            Float referencePrice,
            ReferenceCurrency referenceCurrency,
            Float paymentAmount,
            CurrencyType paymentCurrency,
            long paymentExpirationDate,
            long merchandiseDeliveryExpirationDate) throws CantCreateCustomerBrokerPurchaseException;

    public void updateCustomerBrokerPurchase(UUID contractId, ContractStatus status) throws CantupdateCustomerBrokerPurchaseException;

    void deleteCustomerBrokerPurchase(UUID contractID) throws CantDeleteCustomerBrokerPurchaseException;
}
