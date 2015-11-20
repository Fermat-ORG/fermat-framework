package com.bitdubai.fermat_cbp_api.layer.cbp_stock_transactions.bank_money_destock.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;;
import com.bitdubai.fermat_cbp_api.layer.cbp_stock_transactions.bank_money_destock.exceptions.CantCreateBankMoneyDestockException;


/**
 * Created by franklin on 16/11/15.
 */
public interface BankMoneyDestockManager {

    void createTransactionDestock(
            String publicKeyActor,
            FiatCurrency fiatCurrency,
            String cbpWalletPublicKey,
            String bankWalletPublicKey,
            String bankAccount,
            float amount,
            String memo
    ) throws CantCreateBankMoneyDestockException;
}
