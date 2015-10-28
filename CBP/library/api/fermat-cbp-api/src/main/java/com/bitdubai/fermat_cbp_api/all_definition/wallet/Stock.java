package com.bitdubai.fermat_cbp_api.all_definition.wallet;

import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantGetBookedBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantGetAvailableBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantGetAddDebitCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantGetAddCreditCryptoBrokerWalletException;
/**
 * Created by jorge on 30-09-2015.
 */
public interface Stock {
    float getBookedBalance() throws CantGetBookedBalanceCryptoBrokerWalletException;
    float getAvailableBalance() throws CantGetAvailableBalanceCryptoBrokerWalletException;
    void addDebit(final StockTransaction transaction) throws CantGetAddDebitCryptoBrokerWalletException;
    void addCredit(final StockTransaction transaction) throws CantGetAddCreditCryptoBrokerWalletException;
}