package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks;

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
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransaction;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.Quote;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CurrencyMatching;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/01/16.
 */
public class CryptoBrokerWalletMock implements CryptoBrokerWallet {
    @Override
    public StockBalance getStockBalance() throws CantGetStockCryptoBrokerWalletException {
        return null;
    }

    @Override
    public CryptoBrokerWalletSetting getCryptoWalletSetting() throws CantGetCryptoBrokerWalletSettingException {
        return new CryptoBrokerWalletSettingMock();
    }

    @Override
    public List<CryptoBrokerStockTransaction> getCryptoBrokerStockTransactionsByMerchandise(Currency merchandise, MoneyType moneyType, TransactionType transactionType, BalanceType balanceType) throws CantGetCryptoBrokerStockTransactionException {
        return null;
    }

    @Override
    public FiatIndex getMarketRate(Currency merchandise, FiatCurrency fiatCurrency, MoneyType moneyType) throws CantGetCryptoBrokerMarketRateException {
        return null;
    }

    @Override
    public Quote getQuote(Currency merchandise, float quantity, Currency payment) throws CantGetCryptoBrokerQuoteException {
        return null;
    }

    /**
     * This method load the list CryptoBrokerStockTransaction
     *
     * @param transactionId
     * @return void
     * @throws CantGetTransactionCryptoBrokerWalletMatchingException
     */
    @Override
    public void markAsSeen(UUID transactionId) throws CantMarkAsSeenException {

    }

    @Override
    public void markAsSeen(List<UUID> transactionId) throws CantMarkAsSeenException {

    }

    /**
     * This method load the list CurrencyMatching
     *
     * @return CurrencyMatching
     * @throws CantGetTransactionCryptoBrokerWalletMatchingException
     */
    @Override
    public List<CurrencyMatching> getCryptoBrokerTransactionCurrencyInputs() throws CantGetTransactionCryptoBrokerWalletMatchingException {
        return null;
    }
}
