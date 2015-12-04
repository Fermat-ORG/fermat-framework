package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.CryptoIndexManager;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.CryptoCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.FiatCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database.CryptoIndexDao;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetHistoricalExchangeRateException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetMarketPriceException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantInitializeCryptoIndexDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantSaveLastRateExchangeException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.HistoricalExchangeRateNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.MarketPriceInterface;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.MarketPrice;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.HashMap;
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
     * CryptoIndexImp Interface implementation.
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
        } catch (CantSaveLastRateExchangeException cantSaveLastRateExchangeException) {
            // TODO manage exceptions
            // ok TODO add exception CantGetMarketPriceException
            // ok TODO use errorManager to report unexpected exceptions
            // ok TODO use generic exceptions for other unexpected exceptions
          new CantGetMarketPriceException(CantGetMarketPriceException.DEFAULT_MESSAGE,cantSaveLastRateExchangeException,"CryptoIndexImp WorldPluginRoot GetMarketPrice","Cant Save Last Rate Exchange Exception");
        }catch(Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_INDEX, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,exception);
        }
        return price;
    }

    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {this.errorManager = errorManager;}

    /**
     * DealsWithPluginIdentity methods implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public FermatManager getManager() {
        return null;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {

    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * implement the interface MarketPriceInterface
     *
     * @param cryptoCurrency
     * @param fiatCurrency
     * @param time
     * @return
     */
    @Override
    public double getHistoricalExchangeRate(CryptoCurrency cryptoCurrency, FiatCurrency fiatCurrency, long time) {
        double marketExchangeRate = 0;
        try {
            marketExchangeRate=marketPrice.getHistoricalExchangeRate(cryptoCurrency,fiatCurrency,time);
        } catch (CantGetHistoricalExchangeRateException cantGetHistoricalExchangeRateException) {
            new CantGetHistoricalExchangeRateException(CantGetHistoricalExchangeRateException.DEFAULT_MESSAGE,cantGetHistoricalExchangeRateException,"CryptoIndexImp WorldPluginRoot GetMarketPrice","Cant Get Historical Exchange Rate ");
        } catch (HistoricalExchangeRateNotFoundException e) {
            new HistoricalExchangeRateNotFoundException(HistoricalExchangeRateNotFoundException.DEFAULT_MESSAGE,e,"CryptoIndexImp WorldPluginRoot GetMarketPrice","Historical Exchange Rate Not Found Exception");
        }catch (Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_INDEX, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,exception);
        }

        return marketExchangeRate;
    }

    //@Override
    public double getHistoricalExchangeRateFromDatabase(CryptoCurrency cryptoCurrency, FiatCurrency fiatCurrency, long time) throws FiatCurrencyNotSupportedException, CryptoCurrencyNotSupportedException, CantGetHistoricalExchangeRateException, HistoricalExchangeRateNotFoundException {
        /**
         * get market price from database, filtering by time
         */
        double marketExchangeRate = 0;
        try {
            marketExchangeRate =marketPrice.getHistoricalExchangeRateFromDatabase(cryptoCurrency, fiatCurrency, time);
            return marketExchangeRate;
        } catch (CantGetHistoricalExchangeRateException cantGetHistoricalExchangeRateException) {
            new CantGetHistoricalExchangeRateException(CantGetHistoricalExchangeRateException.DEFAULT_MESSAGE,cantGetHistoricalExchangeRateException,"CryptoIndexImp WorldPluginRoot GetMarketPrice","Cant Get Historical Exchange Rate ");
        }catch (HistoricalExchangeRateNotFoundException historicalExchangeRateNotFoundException){
            new HistoricalExchangeRateNotFoundException(HistoricalExchangeRateNotFoundException.DEFAULT_MESSAGE,null,"CryptoIndexImp WorldPluginRoot GetMarketPrice","Historical Exchange Rate Not Found Exception");
        }
        catch (Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_INDEX, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,exception);
        }
        // TODO manage exceptions
        // ok TODO add exception CantGetHistoricalExchangeRateException
        // TODO maybe there's no record for the currencies pair: HistoricalExchangeRateNotFoundException
        // ok TODO use errorManager to report unexpected exceptions
        // ok TODO use generic exceptions for other unexpected exceptions

        return marketExchangeRate;
    }


}
