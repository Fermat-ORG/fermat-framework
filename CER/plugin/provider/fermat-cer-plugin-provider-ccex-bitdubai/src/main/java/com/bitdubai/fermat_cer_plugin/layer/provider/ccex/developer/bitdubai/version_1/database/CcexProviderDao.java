package com.bitdubai.fermat_cer_plugin.layer.provider.ccex.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cer_api.all_definition.enums.ExchangeRateType;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.all_definition.utils.ExchangeRateImpl;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantCreateExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantInitializeProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantSaveExchangeRateException;
import com.bitdubai.fermat_cer_plugin.layer.provider.ccex.developer.bitdubai.version_1.exceptions.CantInitializeCcexProviderDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public class CcexProviderDao {


    private final ErrorManager errorManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public CcexProviderDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }


    public void initialize() throws CantInitializeCcexProviderDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            CcexProviderDatabaseFactory databaseFactory = new CcexProviderDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.CCEX, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeCcexProviderDatabaseException("Database could not be opened", cantCreateDatabaseException, new StringBuilder().append("Database Name: ").append(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TABLE_NAME).toString(), "");
            }
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.CCEX, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeCcexProviderDatabaseException("Database could not be opened", cantOpenDatabaseException, new StringBuilder().append("Database Name: ").append(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TABLE_NAME).toString(), "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CCEX, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeCcexProviderDatabaseException("Database could not be opened", FermatException.wrapException(e), new StringBuilder().append("Database Name: ").append(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TABLE_NAME).toString(), "");
        }
    }

    public void initializeProvider(String providerName) throws CantInitializeProviderInfoException {
        //Try to get info, if there's no info, populate.
        try {
            this.getProviderInfo();
        } catch (CantGetProviderInfoException e) {
            this.populateProviderInfo(providerName);
        }
    }



    /* CURRENT EXCHANGE RATE METHODS */

    public void saveCurrentExchangeRate(ExchangeRate exchangeRate) throws CantSaveExchangeRateException {

        DatabaseTable table = this.database.getTable(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TABLE_NAME);
        DatabaseTableRecord newRecord = table.getEmptyRecord();
        constructRecordFromExchangeRate(newRecord, exchangeRate);
        try {
            table.insertRecord(newRecord);
        } catch (CantInsertRecordException e) {
            throw new CantSaveExchangeRateException(e.getMessage(), e, "Ccex provider plugin", "Cant save new record in table");
        }
    }


    public List<ExchangeRate> getQueriedExchangeRateHistory(ExchangeRateType exchangeRateType, CurrencyPair currencyPair) throws CantGetExchangeRateException {
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        DatabaseTable table = null;

        switch (exchangeRateType) {
            case CURRENT:
                table = this.database.getTable(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TABLE_NAME);
                table.addStringFilter(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_FROM_CURRENCY_COLUMN_NAME, currencyPair.getFrom().getCode(), DatabaseFilterType.EQUAL);
                table.addStringFilter(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TO_CURRENCY_COLUMN_NAME, currencyPair.getTo().getCode(), DatabaseFilterType.EQUAL);
                break;
        }

        try {
            table.loadToMemory();

            for (DatabaseTableRecord record : table.getRecords()) {
                ExchangeRate exchangeRate = constructExchangeRateFromRecord(record);
                exchangeRateList.add(exchangeRate);
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, e, new StringBuilder().append("Failed to get History for currencyPair: ").append(currencyPair.toString()).toString(), "Couldn't load table to memory");
        } catch (CantCreateExchangeRateException e) {
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, e, new StringBuilder().append("Failed to get History for currencyPair: ").append(currencyPair.toString()).toString(), "Couldn't create ExchangeRate object");
        }

        return exchangeRateList;
    }





    /* PROVIDER INFO METHODS */


    public String getProviderName() throws CantGetProviderInfoException {
        DatabaseTableRecord record = this.getProviderInfo();
        return record.getStringValue(CcexProviderDatabaseConstants.PROVIDER_INFO_NAME_COLUMN_NAME);
    }

    public UUID getProviderId() throws CantGetProviderInfoException {
        DatabaseTableRecord record = this.getProviderInfo();
        return record.getUUIDValue(CcexProviderDatabaseConstants.PROVIDER_INFO_ID_COLUMN_NAME);
    }

    private DatabaseTableRecord getProviderInfo() throws CantGetProviderInfoException {
        List<DatabaseTableRecord> records;
        DatabaseTable table = this.database.getTable(CcexProviderDatabaseConstants.PROVIDER_INFO_TABLE_NAME);

        try {
            table.loadToMemory();
            records = table.getRecords();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetProviderInfoException(CantGetProviderInfoException.DEFAULT_MESSAGE);
        }

        if (records.size() != 1)
            throw new CantGetProviderInfoException(new StringBuilder().append("Inconsistent number of fetched records (").append(records.size()).append("), should be 1.").toString());

        return records.get(0);
    }

    private void populateProviderInfo(String providerName) throws CantInitializeProviderInfoException {
        DatabaseTable table = this.database.getTable(CcexProviderDatabaseConstants.PROVIDER_INFO_TABLE_NAME);
        DatabaseTableRecord newRecord = table.getEmptyRecord();

        newRecord.setUUIDValue(CcexProviderDatabaseConstants.PROVIDER_INFO_ID_COLUMN_NAME, UUID.randomUUID());
        newRecord.setStringValue(CcexProviderDatabaseConstants.PROVIDER_INFO_NAME_COLUMN_NAME, providerName);

        try {
            table.insertRecord(newRecord);
        } catch (CantInsertRecordException e) {
            throw new CantInitializeProviderInfoException(e.getMessage());
        }
    }


    /* INTERNAL HELPER FUNCTIONS */
    private void constructRecordFromExchangeRate(DatabaseTableRecord newRecord, ExchangeRate exchangeRate) {

        newRecord.setUUIDValue(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_ID_COLUMN_NAME, UUID.randomUUID());
        newRecord.setStringValue(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_FROM_CURRENCY_COLUMN_NAME, exchangeRate.getFromCurrency().getCode());
        newRecord.setStringValue(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TO_CURRENCY_COLUMN_NAME, exchangeRate.getToCurrency().getCode());
        newRecord.setStringValue(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_SALE_PRICE_COLUMN_NAME, String.valueOf(exchangeRate.getSalePrice()));
        newRecord.setStringValue(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_PURCHASE_PRICE_COLUMN_NAME, String.valueOf(exchangeRate.getPurchasePrice()));
        newRecord.setLongValue(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TIMESTAMP_COLUMN_NAME, exchangeRate.getTimestamp());

    }

    private ExchangeRate constructExchangeRateFromRecord(DatabaseTableRecord record) throws CantCreateExchangeRateException {

        UUID id = record.getUUIDValue(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_ID_COLUMN_NAME);
        double salePrice = record.getDoubleValue(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_SALE_PRICE_COLUMN_NAME);
        double purchasePrice = record.getDoubleValue(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_PURCHASE_PRICE_COLUMN_NAME);
        long timestamp = record.getLongValue(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TIMESTAMP_COLUMN_NAME);

        Currency fromCurrency;
        try {
            String fromCurrencyStr = record.getStringValue(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_FROM_CURRENCY_COLUMN_NAME);

            if (FiatCurrency.codeExists(fromCurrencyStr))
                fromCurrency = FiatCurrency.getByCode(fromCurrencyStr);
            else if (CryptoCurrency.codeExists(fromCurrencyStr))
                fromCurrency = CryptoCurrency.getByCode(fromCurrencyStr);
            else throw new InvalidParameterException();

        } catch (InvalidParameterException e) {
            throw new CantCreateExchangeRateException(e.getMessage(), e, "Ccex provider plugin", new StringBuilder().append("Invalid From Currency value stored in table").append(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TABLE_NAME).append(" for id ").append(id).toString());
        }

        Currency toCurrency;
        try {
            String toCurrencyStr = record.getStringValue(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TO_CURRENCY_COLUMN_NAME);

            if (FiatCurrency.codeExists(toCurrencyStr))
                toCurrency = FiatCurrency.getByCode(toCurrencyStr);
            else if (CryptoCurrency.codeExists(toCurrencyStr))
                toCurrency = CryptoCurrency.getByCode(toCurrencyStr);
            else throw new InvalidParameterException();

        } catch (InvalidParameterException e) {
            throw new CantCreateExchangeRateException(e.getMessage(), e, "Ccex provider plugin", new StringBuilder().append("Invalid To Currency value stored in table").append(CcexProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TABLE_NAME).append(" for id ").append(id).toString());
        }

        return new ExchangeRateImpl(fromCurrency, toCurrency, salePrice, purchasePrice, timestamp);
    }


}
