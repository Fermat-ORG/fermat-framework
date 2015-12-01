package com.bitdubai.fermat_cbp_api.all_definition.wallet;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddStockCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetStockCollectionCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetStockCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantPerformTransactionException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletSetting;

import java.util.Collection;

/**
 * Created by jorge on 30-09-2015.
 */
public interface Wallet {
    //TODO: Documentar y manejo de excepciones
    StockBalance getStockBalance()  throws CantGetStockCryptoBrokerWalletException;
    CryptoBrokerWalletSetting getCryptoWalletSetting() throws CantGetCryptoBrokerWalletSettingException;
    //void getMarketRate();
    //void getQuote(FermatEnum merchandise, float quantity, FiatCurrency payment);
//    String getWalletPublicKey();
//    String getOwnerPublicKey();
//    void addStock(CurrencyType currencyType, FermatEnum merchandise) throws CantAddStockCryptoBrokerWalletException;
//    Stock getStock(CurrencyType currencyType) throws CantGetStockCryptoBrokerWalletException;
//    Collection<Stock> getStocks() throws CantGetStockCollectionCryptoBrokerWalletException;
//    void performTransaction(StockTransaction transaction) throws CantPerformTransactionException;
}