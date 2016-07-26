package com.bitdubai.fermat_cbp_api.all_definition.wallet;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddCreditCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddDebitCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetAvailableBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetBookedBalanceCryptoBrokerWalletException;

/**
 * Created by jorge on 30-09-2015.
 */
public interface Stock {
    //TODO: Eliminar
    float getBookedBalance() throws CantGetBookedBalanceCryptoBrokerWalletException;

    float getAvailableBalance() throws CantGetAvailableBalanceCryptoBrokerWalletException;

    void addDebit(final StockTransaction transaction) throws CantAddDebitCryptoBrokerWalletException;

    void addCredit(final StockTransaction transaction) throws CantAddCreditCryptoBrokerWalletException;
}