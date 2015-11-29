package com.bitdubai.fermat_cbp_api.layer.cbp_stock_transactions.cash_money_destock.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.layer.cbp_stock_transactions.cash_money_destock.exceptions.CantCreateCashMoneyDestockException;


/**
 * Created by franklin on 16/11/15.
 */
public interface CashMoneyDestockManager {

    void createTransactionDestock(
            String publicKeyActor,
            FiatCurrency fiatCurrency,
            String cbpWalletPublicKey,
            String cshWalletPublicKey,
            String cashReference,
            float amount,
            String memo
    ) throws CantCreateCashMoneyDestockException;
}
