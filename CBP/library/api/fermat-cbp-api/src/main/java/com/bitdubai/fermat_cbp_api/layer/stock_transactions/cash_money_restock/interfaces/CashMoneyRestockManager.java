package com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;


/**
 * Created by franklin on 16/11/15.
 */
public interface CashMoneyRestockManager {

    void createTransactionRestock(
            String publicKeyActor,
            FiatCurrency fiatCurrency,
            String cbpWalletPublicKey,
            String cshWalletPublicKey,
            String cashReference,
            float amount,
            String memo,
            float priceReference,
            OriginTransaction originTransaction
    ) throws com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.exceptions.CantCreateCashMoneyRestockException;
}
