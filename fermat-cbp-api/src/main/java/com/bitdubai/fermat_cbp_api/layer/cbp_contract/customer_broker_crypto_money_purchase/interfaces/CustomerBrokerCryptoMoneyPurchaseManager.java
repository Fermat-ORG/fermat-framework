package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_purchase.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_sale.interfaces.CustomerBrokerBankMoneySale;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_purchase.exceptions.CantCreateCustomerBrokerCashMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_purchase.exceptions.CantupdateStatusCustomerBrokerCryptoMoneyPurchaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerCryptoMoneyPurchaseManager {

    List<CustomerBrokerCryptoMoneyPurchase> getAllCustomerBrokerCryptoMoneyPurchaseFromCurrentDeviceUser();

    CustomerBrokerBankMoneySale createCustomerBrokerCryptoMoneyPurchase(
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

    void updateStatusCustomerBrokerCryptoMoneyPurchase(final UUID ContractId) throws CantupdateStatusCustomerBrokerCryptoMoneyPurchaseException;

}
