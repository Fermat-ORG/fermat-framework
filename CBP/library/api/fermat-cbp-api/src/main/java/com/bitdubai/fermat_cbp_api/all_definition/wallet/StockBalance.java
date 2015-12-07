package com.bitdubai.fermat_cbp_api.all_definition.wallet;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddCreditCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddDebitCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetAvailableBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetBookedBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletBalanceRecord;

import java.util.List;

/**
 * Created by franklin on 30/11/15.
 */
public interface StockBalance {
    float getBookedBalance(FermatEnum merchandise) throws CantGetBookedBalanceCryptoBrokerWalletException;
    float getAvailableBalance(FermatEnum merchandise) throws CantGetAvailableBalanceCryptoBrokerWalletException;
    float getAvailableBalanceFrozen(FermatEnum merchandise) throws CantGetAvailableBalanceCryptoBrokerWalletException;
    List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceBook() throws CantGetBookedBalanceCryptoBrokerWalletException;
    List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceAvailable() throws CantGetBookedBalanceCryptoBrokerWalletException;
    List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceBookFrozen() throws CantGetBookedBalanceCryptoBrokerWalletException;
    List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceAvailableFrozen() throws CantGetBookedBalanceCryptoBrokerWalletException;
    void debit(CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, BalanceType balanceType) throws CantAddDebitCryptoBrokerWalletException;
    void credit(CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, BalanceType balanceType) throws CantAddCreditCryptoBrokerWalletException;
}
