package com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces;

import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;

import java.util.List;

/**
 * Created by memo on 08/12/15.
 */
public interface BankingWallet {
    List<BankAccountNumber> getAccounts()throws CantLoadBankMoneyWalletException;
}
