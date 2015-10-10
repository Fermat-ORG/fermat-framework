package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_purchase.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_purchase.exceptions.CantCreateCustomerBrokerCashMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_purchase.exceptions.CantDeleteCustomerBrokerCashMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_purchase.exceptions.CantupdateCustomerBrokerCashMoneyPurchaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerCashMoneyPurchaseManager {

    List<CustomerBrokerCashMoneyPurchase> getAllCustomerBrokerCashMoneyPurchaseFromCurrentDeviceUser();

    CustomerBrokerCashMoneyPurchase createCustomerBrokerCashMoneyPurchase(
            final String publicKeyCustomer,
            final String publicKeyBroker,
            final Float merchandiseAmount,
            final String merchandiseCurrency,
            final Float referencePrice,
            final String referenceCurrency,
            final Float paymentAmount,
            final String paymentCurrency,
            final long paymentExpirationDate,
            final long merchandiseDeliveryExpirationDate
    ) throws CantCreateCustomerBrokerCashMoneyPurchaseException;

    void updateStatusCustomerBrokerCashMoneyPurchase(final UUID ContractId) throws CantupdateCustomerBrokerCashMoneyPurchaseException;

    void deleteCustomerBrokerCashMoneyPurchase(UUID contractID) throws CantDeleteCustomerBrokerCashMoneyPurchaseException;

    DatabaseTableRecord getCustomerBrokerCashMoneyPurchaseContractTable();
}