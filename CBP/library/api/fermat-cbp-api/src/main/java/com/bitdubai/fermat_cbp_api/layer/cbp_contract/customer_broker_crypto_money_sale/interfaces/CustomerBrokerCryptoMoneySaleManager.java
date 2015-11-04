package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_sale.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_sale.exceptions.CantCreateCustomerBrokerCryptoMoneySaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_sale.exceptions.CantDeleteCustomerBrokerCryptoMoneySaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_sale.exceptions.CantupdateCustomerBrokerCryptoMoneySaleException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerCryptoMoneySaleManager {

    List<CustomerBrokerCryptoMoneySale> getAllCustomerBrokerCryptoMoneySaleFromCurrentDeviceUser();

    CustomerBrokerCryptoMoneySale createCustomerBrokerCryptoMoneySale(
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
    ) throws CantCreateCustomerBrokerCryptoMoneySaleException;

    void updateStatusCustomerBrokerCryptoMoneySale(final UUID ContractId) throws CantupdateCustomerBrokerCryptoMoneySaleException;

    void deleteCustomerBrokerCryptoMoneySale(UUID contractID) throws CantDeleteCustomerBrokerCryptoMoneySaleException;

    DatabaseTableRecord getCustomerBrokerCryptoMoneySaleContractTable();
}
