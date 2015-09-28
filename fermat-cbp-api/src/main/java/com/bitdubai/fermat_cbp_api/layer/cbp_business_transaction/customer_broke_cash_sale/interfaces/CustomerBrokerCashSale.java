package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_cash_sale.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BusinessTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 17.09.2015
 */

public interface CustomerBrokerCashSale extends BusinessTransaction {

    UUID getContractId();

    String getPublicKeyCustomer();

    UUID getPaymentBankMoneyTransactionId();

    CurrencyType getPaymentCurrency();

    UUID getExecutionCashMoneyTransactionId();

    CashCurrencyType getCashCurrencyType();

    /*UUID getContractId();

    String getPublicKeyCustomer();

    float getPaymentAmount();

    CurrencyType getPaymentCurrency();

    CashCurrencyType getCashCurrencyType();*/

}
