package com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_cash_sale.interfaces;

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

    UUID getPaymentTransactionId();

    CurrencyType getPaymentCurrency();

    CashCurrencyType getCashCurrencyType();

}
