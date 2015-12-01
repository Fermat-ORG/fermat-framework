package com.bitdubai.fermat_cbp_api.all_definition.wallet;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddCreditCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddDebitCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetAvailableBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetBookedBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletBalanceRecord;

import java.util.List;

/**
 * Created by franklin on 30/11/15.
 */
public interface StockBalance {
    float getBookedBalance() throws CantGetBookedBalanceCryptoBrokerWalletException;
    float getBookedAvailable() throws CantGetAvailableBalanceCryptoBrokerWalletException;
    float getBookedAvailableFrozed() throws CantGetAvailableBalanceCryptoBrokerWalletException;
    List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceBook() throws CantGetBookedBalanceCryptoBrokerWalletException;
    List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceAvailable() throws CantGetBookedBalanceCryptoBrokerWalletException;
    void debit() throws CantAddDebitCryptoBrokerWalletException;
    void credit() throws CantAddCreditCryptoBrokerWalletException;
}
