package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
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

    public CustomerBrokerBankMoneyPurchase createCustomerBrokerBankMoneyPurchase(
            String publicKeyCustomer,
            String publicKeyBroker,
            Float merchandiseAmount,
            CurrencyType merchandiseCurrency,
            Float referencePrice,
            ReferenceCurrency referenceCurrency,
            Float paymentAmount,
            CurrencyType paymentCurrency,
            long paymentExpirationDate,
            long merchandiseDeliveryExpirationDate) throws CantCreateCustomerBrokerBankMoneyPurchaseException;

    void updateCustomerBrokerBankMoneyPurchase(final UUID ContractId) throws CantupdateCustomerBrokerBankMoneyPurchaseException;

    void deleteCustomerBrokerBankMoneyPurchase(UUID contractID) throws CantDeleteCustomerBrokerBankMoneyPurchaseException;

    DatabaseTableRecord getCustomerBrokerBankMoneySaleContractTable();
}
