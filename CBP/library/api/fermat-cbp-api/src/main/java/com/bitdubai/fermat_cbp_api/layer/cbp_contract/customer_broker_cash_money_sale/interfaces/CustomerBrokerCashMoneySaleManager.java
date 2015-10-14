package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_sale.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_sale.exceptions.CantCreateCustomerBrokerCashMoneySaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_sale.exceptions.CantDeleteCustomerBrokerCashMoneySaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_sale.exceptions.CantupdateCustomerBrokerCashMoneySaleException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerCashMoneySaleManager {

    List<CustomerBrokerCashMoneySale> getAllCustomerBrokerCashMoneySaleFromCurrentDeviceUser();

    CustomerBrokerCashMoneySale createCustomerBrokerCashMoneySale(
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
    ) throws CantCreateCustomerBrokerCashMoneySaleException;

    void updateStatusCustomerBrokerCashMoneySale(final UUID ContractId) throws CantupdateCustomerBrokerCashMoneySaleException;

    void deleteCustomerBrokerCashMoneySale(UUID contractID) throws CantDeleteCustomerBrokerCashMoneySaleException;

    DatabaseTableRecord getCustomerBrokerCashMoneySaleContractTable();
}