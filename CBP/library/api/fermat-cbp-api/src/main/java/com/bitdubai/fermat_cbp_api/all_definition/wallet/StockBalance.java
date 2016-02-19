package com.bitdubai.fermat_cbp_api.all_definition.wallet;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddCreditCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddDebitCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetAvailableBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetBookedBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletBalanceRecord;

import java.util.List;

/**
 * Created by franklin on 30/11/15.
 */
public interface StockBalance {


    /**
     * The method <code>getBookedBalance</code> returns the booked balance of the Stock Balance
     *
     * @param merchandise
     * @return a float of the booked balance
     * @throws CantGetBookedBalanceCryptoBrokerWalletException
     */
    float getBookedBalance(Currency merchandise) throws CantGetBookedBalanceCryptoBrokerWalletException, CantStartPluginException;

    /**
     * The method <code>getAvailableBalance</code> returns the available balance of the Stock Balance
     *
     * @param merchandise
     * @return a float of the available balance
     * @throws CantGetAvailableBalanceCryptoBrokerWalletException
     */
    float getAvailableBalance(Currency merchandise) throws CantGetAvailableBalanceCryptoBrokerWalletException, CantStartPluginException;

    /**
     * The method <code>getAvailableBalanceFrozen</code> returns the available balance frozen of the Stock Balance
     *
     * @param merchandise
     * @return a float of the available balance frozen
     * @throws CantGetAvailableBalanceCryptoBrokerWalletException
     */
    float getAvailableBalanceFrozen(Currency merchandise) throws CantGetAvailableBalanceCryptoBrokerWalletException, CantStartPluginException;

    /**
     * The method <code>getCryptoBrokerWalletBalanceBook</code> returns the crypto broker wallet balance book of the Stock Balance
     *
     * @return a List of CryptoBrokerWalletBalanceRecord
     * @throws CantGetBookedBalanceCryptoBrokerWalletException
     */
    List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceBook() throws CantGetBookedBalanceCryptoBrokerWalletException, CantStartPluginException;

    /**
     * The method <code>getCryptoBrokerWalletBalanceAvailable</code> returns the crypto broker wallet balance available book of the Stock Balance
     *
     * @return a List ofCryptoBrokerWalletBalanceRecord
     * @throws CantGetBookedBalanceCryptoBrokerWalletException
     */
    List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceAvailable() throws CantGetBookedBalanceCryptoBrokerWalletException, CantStartPluginException;


    /**
     * The method <code>getCryptoBrokerWalletBalanceAvailable</code> returns the crypto broker wallet balance book frozen of the Stock Balance
     *
     * @return a List of CryptoBrokerWalletBalanceRecord
     * @throws CantGetBookedBalanceCryptoBrokerWalletException
     */
    List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceBookFrozen() throws CantGetBookedBalanceCryptoBrokerWalletException, CantStartPluginException, CantCalculateBalanceException;

    /**
     * The method <code>getCryptoBrokerWalletBalanceAvailableFrozen</code> returns the crypto broker wallet balance available frozen of the Stock Balance
     *
     * @return a list of CryptoBrokerWalletBalanceRecord
     * @throws CantGetBookedBalanceCryptoBrokerWalletException
     */
    List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceAvailableFrozen() throws CantGetBookedBalanceCryptoBrokerWalletException, CantStartPluginException, CantCalculateBalanceException;

    /**
     * The method <code>debit</code> sets the crypto Broker Stock Transaction Record and the balance type of the debit of the Stock Balance
     *
     * @param cryptoBrokerStockTransactionRecord
     * @param balanceType
     * @throws CantAddDebitCryptoBrokerWalletException
     */
    void debit(CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, BalanceType balanceType) throws CantAddDebitCryptoBrokerWalletException, CantStartPluginException, CantCalculateBalanceException;

    /**
     * The method <code>credit</code> sets the crypto Broker Stock Transaction Record and the balance type of the credit of the Stock Balance
     *
     * @param cryptoBrokerStockTransactionRecord
     * @param balanceType
     * @throws CantAddCreditCryptoBrokerWalletException
     */
    void credit(CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, BalanceType balanceType) throws CantAddCreditCryptoBrokerWalletException, CantStartPluginException, CantCalculateBalanceException;

}
