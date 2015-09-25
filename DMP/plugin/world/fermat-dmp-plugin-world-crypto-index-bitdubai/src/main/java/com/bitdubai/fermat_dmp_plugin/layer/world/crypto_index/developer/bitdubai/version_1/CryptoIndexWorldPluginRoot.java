package com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.ccp_world.crypto_index.CryptoIndexManager;
import com.bitdubai.fermat_api.layer.ccp_world.crypto_index.exceptions.CryptoCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer.ccp_world.crypto_index.exceptions.FiatCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database.CryptoIndexDao;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantInitializeCryptoIndexDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantSaveLastRateExchangeException;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndexInterface;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.MarketPriceInterface;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.MarketPrice;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by loui on 12/02/15.
 */

/**
 * This plugin mission is to provide the current market price of the different crypto currencies. To accomplish that
 * goal, it will check one or more indexes as needed.
 * * *
 */

public class CryptoIndexWorldPluginRoot implements MarketPriceInterface, Service, CryptoIndexManager, DealsWithErrors, DealsWithPluginFileSystem, DealsWithPluginDatabaseSystem, Plugin {


    MarketPrice marketPrice = new MarketPrice();

    private CryptoIndexDao cryptoIndexDao;
    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    /**
     * DealsWithLogger interface member variable
     */
    private LogManager logManager;
    private static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    @Override
    public void start() throws CantStartPluginException {
        try {

            this.cryptoIndexDao = new CryptoIndexDao(pluginDatabaseSystem, this.pluginId);
            cryptoIndexDao.initializeDatabase();

        } catch (CantInitializeCryptoIndexDatabaseException e) {
            e.printStackTrace();
        }
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    /**
     * CryptoIndex Interface implementation.
     */

    @Override
    public double getMarketPrice(FiatCurrency fiatCurrency, CryptoCurrency cryptoCurrency, long time) throws FiatCurrencyNotSupportedException, CryptoCurrencyNotSupportedException {
        double price = 0;
        try {
            /**
             * implement the interface to get the last price of market from different providers
             */
            price = marketPrice.getMarketPrice(fiatCurrency, cryptoCurrency, 0);
            /**
             * save in database the last price consulted
             */
            String c = cryptoCurrency.getCode();
            String f = fiatCurrency.getCode();
            cryptoIndexDao.saveLastRateExchange(c, f, price);
        } catch (CantSaveLastRateExchangeException e) {
            // TODO manage exceptions
            // TODO add exception CantGetMarketPriceException
            // TODO use errorManager to report unexpected exceptions
            // TODO use generic exceptions for other unexpected exceptions
            e.printStackTrace();
        }
        return price;
    }

    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {

    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * mplement the interface MarketPriceInterface
     *
     * @param cryptoCurrency
     * @param fiatCurrency
     * @param time
     * @return
     */
    @Override
    public double getHistoricalExchangeRate(CryptoCurrency cryptoCurrency, FiatCurrency fiatCurrency, long time) {
        /**
         * get market price from database, filtering by time
         */
        double marketExchangeRate;
        List<CryptoIndexInterface> list;
        String crypto = cryptoCurrency.getCode();
        String fiat = fiatCurrency.getCode();
        cryptoIndexDao = new CryptoIndexDao(pluginDatabaseSystem, pluginId);
        list = cryptoIndexDao.getHistoricalExchangeRateList(crypto, fiat, time);
        marketExchangeRate = Double.valueOf(list.get(0).toString());
        return marketExchangeRate;
        // TODO manage exceptions
        // TODO add exception CantGetHistoricalExchangeRateException
        // TODO maybe there's no record for the currencies pair: HistoricalExchangeRateNotFoundException
        // TODO use errorManager to report unexpected exceptions
        // TODO use generic exceptions for other unexpected exceptions
    }


}
