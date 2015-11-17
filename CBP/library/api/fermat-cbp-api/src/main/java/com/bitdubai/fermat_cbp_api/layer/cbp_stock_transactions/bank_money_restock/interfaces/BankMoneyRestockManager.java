package com.bitdubai.fermat_cbp_api.layer.cbp_stock_transactions.bank_money_restock.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.bank_money_restock.exceptions.CantCreateBankMoneyRestockException;

/**
 * Created by franklin on 16/11/15.
 */
public interface BankMoneyRestockManager  {

    void createTransactionRestock(
            String       publicKeyActor,
            FiatCurrency fiatCurrency,
            String       cbpWalletPublicKey,
            String       bankWalletPublicKey,
            String       bankAccount,
            String       memo
    ) throws CantCreateBankMoneyRestockException;
}
