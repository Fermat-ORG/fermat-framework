package com.bitdubai.fermat_cbp_api.all_definition.wallet;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerStockTransactionException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetStockCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransaction;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;

import java.util.List;

/**
 * Created by jorge on 30-09-2015.
 * Modified by Franklin Marcano 01.12.2015
 */
public interface Wallet {
    /**
     * This method load the instance the StockBalance
     * @param
     * @return StockBalance
     * @exception CantGetStockCryptoBrokerWalletException
     */
    StockBalance getStockBalance()  throws CantGetStockCryptoBrokerWalletException;
    /**
     * This method load the instance the CryptoBrokerWalletSetting
     * @param
     * @return StockBalance
     * @exception CantGetCryptoBrokerWalletSettingException
     */
    CryptoBrokerWalletSetting getCryptoWalletSetting() throws CantGetCryptoBrokerWalletSettingException;
    /**
     * This method load the list CryptoBrokerStockTransaction
     * @param merchandise
     * @param currencyType
     * @param transactionType
     * @param balanceType
     * @return List<CryptoBrokerStockTransaction>
     * @exception CantGetCryptoBrokerStockTransactionException
     */
    List<CryptoBrokerStockTransaction> getCryptoBrokerStockTransactionsByMerchandise(FermatEnum merchandise, CurrencyType currencyType, TransactionType transactionType, BalanceType balanceType) throws CantGetCryptoBrokerStockTransactionException;
    //void getMarketRate();
    //void getQuote(FermatEnum merchandise, float quantity, FiatCurrency payment);
}