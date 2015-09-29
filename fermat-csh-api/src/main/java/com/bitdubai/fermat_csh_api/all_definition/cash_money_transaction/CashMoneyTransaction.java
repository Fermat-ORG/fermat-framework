package com.bitdubai.fermat_csh_api.all_definition.cash_money_transaction;


import com.bitdubai.fermat_csh_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 18.09.2015
 */

public interface CashMoneyTransaction {

    UUID getCashTransactionId();

    String getPublicKeyCustomer();

    String getPublicKeyBroker();

    CashTransactionStatus getStatus();

    float getMerchandiseAmount();

    CashCurrencyType getCashCurrencyType();

    String getCashReference();

}