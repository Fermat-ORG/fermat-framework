package com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;

import java.util.List;

/**
 * Created by memo on 08/12/15.
 */
public interface BankingWallet {
    List<BankAccountNumber> getAccounts()throws CantLoadBankMoneyWalletException;
    void addNewAccount(BankAccountType bankAccountType, String alias,String account,FiatCurrency fiatCurrency);
}
