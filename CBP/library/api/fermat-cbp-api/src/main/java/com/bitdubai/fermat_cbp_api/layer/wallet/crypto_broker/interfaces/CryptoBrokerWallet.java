package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockBalance;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerMarketRateException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerQuoteException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerStockTransactionException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetStockCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetTransactionCryptoBrokerWalletMatchingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantMarkAsSeenException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CurrencyMatching;

import java.util.List;

/**
 * Created by Yordin Alayn on 30.09.15.
 */
public interface CryptoBrokerWallet {

    /**
     * This method load the instance the StockBalance
     *
     * @return StockBalance
     * @throws CantGetStockCryptoBrokerWalletException
     */
    StockBalance getStockBalance() throws CantGetStockCryptoBrokerWalletException;

    /**
     * This method load the instance the CryptoBrokerWalletSetting
     *
     * @return StockBalance
     * @throws CantGetCryptoBrokerWalletSettingException
     */
    CryptoBrokerWalletSetting getCryptoWalletSetting() throws CantGetCryptoBrokerWalletSettingException;

    /**
     * This method load the list CryptoBrokerStockTransaction
     *
     * @param merchandise
     * @param moneyType
     * @param transactionType
     * @param balanceType
     * @return List<CryptoBrokerStockTransaction>
     * @throws CantGetCryptoBrokerStockTransactionException
     */
    List<CryptoBrokerStockTransaction> getCryptoBrokerStockTransactionsByMerchandise(Currency merchandise, MoneyType moneyType, TransactionType transactionType, BalanceType balanceType) throws CantGetCryptoBrokerStockTransactionException;


    /**
     * This method load the list CryptoBrokerStockTransaction
     *
     * @param merchandise
     * @param moneyType
     * @param offset
     * @param timeStamp
     * @return List<CryptoBrokerStockTransaction>
     * @throws CantGetCryptoBrokerStockTransactionException
     */
    List<CryptoBrokerStockTransaction> getStockHistory(Currency merchandise, MoneyType moneyType, int offset, long timeStamp) throws CantGetCryptoBrokerStockTransactionException;

    /**
     * This method load the list CryptoBrokerStockTransaction
     *
     * @param merchandise
     * @param fiatCurrency
     * @param moneyType
     * @return FiatIndex
     * @throws CantGetCryptoBrokerMarketRateException
     */
    FiatIndex getMarketRate(Currency merchandise, FiatCurrency fiatCurrency, MoneyType moneyType) throws CantGetCryptoBrokerMarketRateException;

    /**
     * This method load the list CryptoBrokerStockTransaction
     *
     * @param merchandise
     * @param quantity
     * @param payment
     * @return Quote
     * @throws CantGetCryptoBrokerQuoteException
     */
    Quote getQuote(Currency merchandise, float quantity, Currency payment) throws CantGetCryptoBrokerQuoteException;

    /**
     * Through this method you can mark a transaction as seen by the matching engine plug-in.
     *
     * @param transactionIds a list with all the ids of the transaction that we want to mark as seen.
     * @throws CantMarkAsSeenException if something goes wrong.
     */
    void markAsSeen(List<String> transactionIds) throws CantMarkAsSeenException;

    /**
     * This method load the list CurrencyMatching
     *
     * @return CurrencyMatching
     * @throws CantGetTransactionCryptoBrokerWalletMatchingException
     */
    List<CurrencyMatching> getCryptoBrokerTransactionCurrencyInputs() throws CantGetTransactionCryptoBrokerWalletMatchingException;

}