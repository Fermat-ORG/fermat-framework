package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;

/**
 * Created by memo on 24/11/15.
 */
public interface BankAccountNumber {

    String getAlias();

    String getAccount();

    FiatCurrency getCurrencyType();

}
