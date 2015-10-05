package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.exceptions.CantCreateCustomerBrokerBankMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.exceptions.CantDeleteCustomerBrokerBankMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.exceptions.CantupdateCustomerBrokerBankMoneyPurchaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerBankMoneyPurchaseManager {

    List<CustomerBrokerBankMoneyPurchase> getAllCustomerBrokerBankMoneyPurchaseFromCurrentDeviceUser();

    List<CustomerBrokerBankMoneyPurchase> getCustomerBrokerBankMoneyPurchaseForContractId(final UUID ContractId);

    CustomerBrokerBankMoneyPurchase createCustomerBrokerBankMoneyPurchase(
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
    ) throws CantCreateCustomerBrokerBankMoneyPurchaseException;

    void updateCustomerBrokerBankMoneyPurchase(final UUID ContractId) throws CantupdateCustomerBrokerBankMoneyPurchaseException;

    void deleteCustomerBrokerBankMoneyPurchase(UUID contractID) throws CantDeleteCustomerBrokerBankMoneyPurchaseException;

    DatabaseTableRecord getCustomerBrokerBankMoneySaleContractTable();
}
