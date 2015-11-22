package com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.exceptions.CantCreateCryptoMoneyRestockException;


/**
 * Created by franklin on 16/11/15.
 */
public interface CryptoMoneyRestockManager {

    void createTransactionRestock(
            String publicKeyActor,
            CryptoCurrency cryptoCurrency,
            String cbpWalletPublicKey,
            String bankWalletPublicKey,
            String bankAccount,
            float amount,
            String memo
    ) throws CantCreateCryptoMoneyRestockException;
}
