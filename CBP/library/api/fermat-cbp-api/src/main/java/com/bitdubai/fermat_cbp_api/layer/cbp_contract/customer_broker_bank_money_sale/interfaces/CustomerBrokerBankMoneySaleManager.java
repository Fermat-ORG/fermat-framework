package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_sale.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_sale.exceptions.CantCreateCustomerBrokerBankMoneySaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_sale.exceptions.CantDeleteCustomerBrokerBankMoneySaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_sale.exceptions.CantupdateCustomerBrokerBankMoneySaleException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerBankMoneySaleManager {

    List<CustomerBrokerBankMoneySale> getAllCustomerBrokerBankMoneySaleFromCurrentDeviceUser();

    CustomerBrokerBankMoneySale createCustomerBrokerBankMoneySale(
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
    ) throws CantCreateCustomerBrokerBankMoneySaleException;

    void updateStatusCustomerBrokerBankMoneySale(final UUID ContractId) throws CantupdateCustomerBrokerBankMoneySaleException;

    void deleteCustomerBrokerBankMoneySale(UUID contractID) throws CantDeleteCustomerBrokerBankMoneySaleException;

    DatabaseTableRecord getCustomerBrokerBankMoneySaleContractTable();
}