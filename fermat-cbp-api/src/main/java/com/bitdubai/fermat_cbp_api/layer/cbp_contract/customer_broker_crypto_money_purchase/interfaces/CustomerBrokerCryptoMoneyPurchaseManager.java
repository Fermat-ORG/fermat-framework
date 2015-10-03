package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_purchase.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_purchase.exceptions.CantCreateCustomerBrokerCryptoMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_purchase.exceptions.CantDeleteCustomerBrokerCryptoMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_purchase.exceptions.CantupdateCustomerBrokerCryptoMoneyPurchaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerCryptoMoneyPurchaseManager {

    List<CustomerBrokerCryptoMoneyPurchase> getAllCustomerBrokerCryptoMoneyPurchaseFromCurrentDeviceUser();

    CustomerBrokerCryptoMoneyPurchase createCustomerBrokerCryptoMoneyPurchase(
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
    ) throws CantCreateCustomerBrokerCryptoMoneyPurchaseException;

    void updateStatusCustomerBrokerCryptoMoneyPurchase(final UUID ContractId) throws CantupdateCustomerBrokerCryptoMoneyPurchaseException;

    void deleteCustomerBrokerCryptoMoneyPurchase(UUID contractID) throws CantDeleteCustomerBrokerCryptoMoneyPurchaseException;

    DatabaseTableRecord getCustomerBrokerCryptoMoneyPurchaseContractTable();
}
