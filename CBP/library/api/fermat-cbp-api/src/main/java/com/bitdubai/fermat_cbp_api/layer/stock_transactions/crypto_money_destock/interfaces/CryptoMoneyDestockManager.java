package com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.exceptions.CantCreateCryptoMoneyDestockException;


/**
 * Created by franklin on 16/11/15.
 */
public interface CryptoMoneyDestockManager {

    void createTransactionDestock(
            String publicKeyActor,
            CryptoCurrency cryptoCurrency,
            String cbpWalletPublicKey,
            String cryWalletPublicKey,
            float amount,
            String memo,
            float priceReference,
            OriginTransaction originTransaction
    ) throws CantCreateCryptoMoneyDestockException;
}
