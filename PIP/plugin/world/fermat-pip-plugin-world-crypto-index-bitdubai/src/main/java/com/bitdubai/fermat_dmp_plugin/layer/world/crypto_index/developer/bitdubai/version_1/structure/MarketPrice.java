package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.CryptoIndexManager;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.CryptoCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.FiatCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database.CryptoIndexDao;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.enums.Providers;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetHistoricalExchangeRateException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetMarketPriceException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantInitializeCryptoIndexDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantSelectBestIndexException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.HistoricalExchangeRateNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndex;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndexInterface;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndexProvider;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.MarketPriceInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by francisco on 12/08/15.
 */
public class MarketPrice implements MarketPriceInterface, CryptoIndexManager {
    Double marketExchangeRate = null;
    CryptoIndexDao cryptoIndexDao;
    UUID pluginId;
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Get Historical ExchangeRate From CryptoProvidersManager
     *
     * @param cryptoCurrency
     * @param fiatCurrency
     * @param time
     * @return
     */
    @Override
    public double getHistoricalExchangeRate(CryptoCurrency cryptoCurrency, FiatCurrency fiatCurrency, long time) throws CantGetHistoricalExchangeRateException, HistoricalExchangeRateNotFoundException {
        List<Double> priceList = new ArrayList<>();
        try {
            for (Providers provider : Providers.values()) {
                CryptoIndexProvider cryptoIndexProvider = provider.getProviderInstance();
                //
                if (cryptoIndexProvider.getHistoricalExchangeRate(cryptoCurrency, fiatCurrency, time) != 0) {
                    priceList.add(cryptoIndexProvider.getHistoricalExchangeRate(cryptoCurrency, fiatCurrency, time));
                }
            }
            Collections.sort(priceList);
            if (priceList.isEmpty()) {
                throw new CantGetMarketPriceException(CantGetMarketPriceException.DEFAULT_MESSAGE, null, "Ger Market Price ", "Empty List Price");
            }

            marketExchangeRate = getBestMarketPrice(priceList);
        } catch (FiatCurrencyNotSupportedException e) {
            e.printStackTrace();
        } catch (CryptoCurrencyNotSupportedException e) {
            e.printStackTrace();
        } catch (CantGetMarketPriceException e) {
            e.printStackTrace();
        }


        return marketExchangeRate;
    }

    /**
     * @param cryptoCurrency
     * @param fiatCurrency
     * @param time
     * @return
     * @throws FiatCurrencyNotSupportedException
     * @throws CryptoCurrencyNotSupportedException
     * @throws CantGetHistoricalExchangeRateException
     * @throws HistoricalExchangeRateNotFoundException
     */
    @Override
    public double getHistoricalExchangeRateFromDatabase(CryptoCurrency cryptoCurrency, FiatCurrency fiatCurrency, long time) throws FiatCurrencyNotSupportedException, CryptoCurrencyNotSupportedException, CantGetHistoricalExchangeRateException, HistoricalExchangeRateNotFoundException {
        /**
         * get market price from database, filtering by time
         */
        List<CryptoIndexInterface> list;
        String crypto = cryptoCurrency.getCode();
        String fiat = fiatCurrency.getCode();
        cryptoIndexDao = new CryptoIndexDao(pluginDatabaseSystem, pluginId);
        try {
            cryptoIndexDao.initializeDatabase();
            list = cryptoIndexDao.getHistoricalExchangeRateList(crypto, fiat, time);
            marketExchangeRate = Double.valueOf(list.get(0).toString());
        } catch (CantInitializeCryptoIndexDatabaseException e) {
            e.printStackTrace();
        }

        return marketExchangeRate;
    }

    /**
     * Return the best market price returns
     *
     * @param fiatCurrency
     * @param cryptoCurrency
     * @param time
     * @return
     * @throws FiatCurrencyNotSupportedException
     * @throws CryptoCurrencyNotSupportedException
     */
    @Override
    public double getMarketPrice(FiatCurrency fiatCurrency, CryptoCurrency cryptoCurrency, long time) throws FiatCurrencyNotSupportedException, CryptoCurrencyNotSupportedException {

        try {

            List<Double> priceList = new ArrayList<>();

            for (Providers provider : Providers.values()) {
                CryptoIndexProvider cryptoIndexProvider = provider.getProviderInstance();
                //
                priceList.add(cryptoIndexProvider.getMarketPrice(cryptoCurrency, fiatCurrency, time));
                Collections.sort(priceList);
                if (priceList.isEmpty()) {
                    throw new CantGetMarketPriceException(CantGetMarketPriceException.DEFAULT_MESSAGE, null, "Ger Market Price ", "Empty List Price");
                }
            }
            marketExchangeRate = getBestMarketPrice(priceList);
        } catch (CantGetMarketPriceException cantGetMarketPriceException) {
            new CantGetMarketPriceException(CantGetMarketPriceException.DEFAULT_MESSAGE, cantGetMarketPriceException, "Crypto Index GetMarketPrice", "Can't Get Market Price Exception");
        }
        return marketExchangeRate;
    }

    public double getBestMarketPrice(List<Double> priceList) {
        /**
         * Get the average
         */
        double average = 0;
        double bestPrice = 0;
        for (int i = 0; i < priceList.size(); i++) {
            average = average + priceList.get(i);
        }
        average = average / priceList.size();
        /**
         * Through the FOR loop I get best price close to the average
         */
        for (int i = 0; i < priceList.size(); i++) {
            if (priceList.get(i) <= average & priceList.get(i) >= bestPrice) {
                bestPrice = priceList.get(i);
            }
        }
        return marketExchangeRate = bestPrice;
    }

    public CryptoIndex getBestPrice(List<CryptoIndex> indexList, FiatCurrency fiatCurrency, CryptoCurrency cryptoCurrency) {
        long time = 0;

        try {

            List<Double> priceList = new ArrayList<>();

            for (Providers provider : Providers.values()) {
                CryptoIndexProvider cryptoIndexProvider = provider.getProviderInstance();
                //
                priceList.add(cryptoIndexProvider.getMarketPrice(cryptoCurrency, fiatCurrency, time));
                Collections.sort(priceList);
                if (priceList.isEmpty()) {
                    System.out.println("");
                }
            }
            CryptoIndexHelper.selectBestIndex(indexList);
        } catch (CantSelectBestIndexException e) {
            e.printStackTrace();
        } catch (FiatCurrencyNotSupportedException e) {
            e.printStackTrace();
        } catch (CantGetMarketPriceException e) {
            e.printStackTrace();
        } catch (CryptoCurrencyNotSupportedException e) {
            e.printStackTrace();
        }

        return indexList.get(0);
    }

}









