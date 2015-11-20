package com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_bank_sale.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BusinessTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 17.09.2015
 */

public interface CustomerBrokerBankSale extends BusinessTransaction {

    UUID getContractId();

    String getPublicKeyCustomer();

    UUID getPaymentTransactionId();

    CurrencyType getPaymentCurrency();

    BankCurrencyType getBankCurrencyType();

    BankOperationType getBankOperationType();

    /*UUID getNegotiationId();

    String getPublicKeyCustomer();

    float getPaymentAmount();

    CurrencyType getPaymentCurrency();

    String getBankName();

    String getBankAccountNumber();

    BankAccountType getBankAccountType();

    String getBankDocumentReference();*/

}
