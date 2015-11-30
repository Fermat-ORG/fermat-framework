package com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.exceptions.CantCreateCashMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.exceptions.CantCreateCashMoneyRestockException;


/**
 * Created by franklin on 16/11/15.
 */
public interface CashMoneyRestockManager {
        /**
         * Method that create the transaction Destock
         *
         * @param publicKeyActor
         * @param fiatCurrency
         * @param cbpWalletPublicKey
         * @param cshWalletPublicKey
         * @param cashReference
         * @param amount
         * @param memo
         * @param priceReference
         * @param originTransaction
         *
         * @throws CantCreateCashMoneyRestockException
         */
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
