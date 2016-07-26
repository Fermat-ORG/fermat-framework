package com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.exceptions.CantCreateCashMoneyDestockException;

import java.math.BigDecimal;


/**
 * Created by franklin on 16/11/15.
 */
public interface CashMoneyDestockManager extends FermatManager {
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
     * @throws CantCreateCashMoneyDestockException
     */
    void createTransactionDestock(
            String publicKeyActor,
            FiatCurrency fiatCurrency,
            String cbpWalletPublicKey,
            String cshWalletPublicKey,
            String cashReference,
            BigDecimal amount,
            String memo,
            BigDecimal priceReference,
            OriginTransaction originTransaction,
            String originTransactionId
    ) throws CantCreateCashMoneyDestockException;
}
