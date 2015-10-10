package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CashOperationType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CryptoCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;

import java.util.UUID;

/**
 * Created by jorge on 09-10-2015.
 */
public interface Negotiation {

    UUID getNegotiationId();

    String getPublicKeyCustomer();
    String getPublicKeyBroker();

    CurrencyType getMerchandiseCurrency();
    CurrencyType getPaymentCurrency();

    CryptoCurrencyType getMerchandiseCryptoCurrencyType();
    CashCurrencyType getMerchandiseCashCurrencyType();
    BankCurrencyType getMerchandiseBankCurrencyType();

    CashOperationType getMerchandiseCashHandlingOperation();
    BankOperationType getMerchandiseBankHandlingOperation();

    CryptoCurrencyType getPaymentCryptoCurrencyType();
    CashCurrencyType getPaymentCashCurrencyType();
    BankCurrencyType getPaymentBankCurrencyType();

    CashOperationType getPaymentCashHandlingOperation();
    BankOperationType getPaymentBankHandlingOperation();

    float getMerchandiseAmount();

    float getReferencePrice();
    ReferenceCurrency getReferenceCurrency();

    float getPaymentAmount();


    long getPaymentExpirationDate();
    long getMerchandiseDeliveryExpirationDate();
}
