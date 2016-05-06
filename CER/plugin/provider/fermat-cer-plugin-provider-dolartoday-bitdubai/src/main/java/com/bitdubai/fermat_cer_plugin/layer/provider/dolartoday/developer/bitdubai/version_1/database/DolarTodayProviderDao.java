package com.bitdubai.fermat_cer_plugin.layer.provider.dolartoday.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantCreateExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantInitializeProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantSaveExchangeRateException;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_plugin.layer.provider.dolartoday.developer.bitdubai.version_1.exceptions.CantInitializeDolarTodayProviderDatabaseException;
import com.bitdubai.fermat_cer_api.all_definition.utils.ExchangeRateImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public class DolarTodayProviderDao {


    private final ErrorManager errorManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public DolarTodayProviderDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }


    public void initialize() throws CantInitializeDolarTodayProviderDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            DolarTodayProviderDatabaseFactory databaseFactory = new DolarTodayProviderDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.DOLARTODAY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeDolarTodayProviderDatabaseException("Database could not be opened", cantCreateDatabaseException, "Database Name: " + DolarTodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME, "");
            }
        }catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.DOLARTODAY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeDolarTodayProviderDatabaseException("Database could not be opened", cantOpenDatabaseException, "Database Name: " + DolarTodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME, "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.DOLARTODAY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeDolarTodayProviderDatabaseException("Database could not be opened", FermatException.wrapException(e), "Database Name: " + DolarTodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME, "");
        }
    }

    public void initializeProvider(String providerName) throws CantInitializeProviderInfoException {
        //Try to get info, if there's no info, populate.
        try{
            this.getProviderInfo();
        }catch (CantGetProviderInfoException e){
            this.populateProviderInfo(providerName);
        }
    }


    public void saveExchangeRate(ExchangeRate exchangeRate) throws CantSaveExchangeRateException {

        DatabaseTable table = this.database.getTable(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME);
        DatabaseTableRecord newRecord = table.getEmptyRecord();
        constructRecordFromExchangeRate(newRecord, exchangeRate);
        try {
            table.insertRecord(newRecord);
        }catch (CantInsertRecordException e) {
            throw new CantSaveExchangeRateException(e.getMessage(), e, "Dolartoday provider plugin", "Cant save new record in table");
        }
    }

    public List<ExchangeRate> getQueriedExchangeRateHistory(CurrencyPair currencyPair) throws CantGetExchangeRateException
    {
        List<ExchangeRate> exchangeRateList = new ArrayList<>();

        DatabaseTable table = this.database.getTable(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME);

        table.addStringFilter(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_FROM_CURRENCY_COLUMN_NAME, currencyPair.getFrom().getCode(), DatabaseFilterType.EQUAL);
        table.addStringFilter(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_TO_CURRENCY_COLUMN_NAME, currencyPair.getTo().getCode(), DatabaseFilterType.EQUAL);

        try {
            table.loadToMemory();

            for (DatabaseTableRecord record : table.getRecords()) {
                ExchangeRate exchangeRate = constructExchangeRateFromRecord(record);
                exchangeRateList.add(exchangeRate);
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, e, "Failed to get History for currencyPair: " + currencyPair.toString(), "Couldn't load table to memory");
        }catch (CantCreateExchangeRateException e) {
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, e, "Failed to get History for currencyPair: " + currencyPair.toString(), "Couldn't create ExchangeRate object");
        }

        return exchangeRateList;
    }






    /* PROVIDER INFO GETTERS */
    public String getProviderName() throws CantGetProviderInfoException {
        DatabaseTableRecord record = this.getProviderInfo();
        return record.getStringValue(DolarTodayProviderDatabaseConstants.PROVIDER_INFO_NAME_COLUMN_NAME);
    }

    public UUID getProviderId() throws CantGetProviderInfoException {
        DatabaseTableRecord record = this.getProviderInfo();
        return record.getUUIDValue(DolarTodayProviderDatabaseConstants.PROVIDER_INFO_ID_COLUMN_NAME);
    }

    private DatabaseTableRecord getProviderInfo() throws CantGetProviderInfoException {
        List<DatabaseTableRecord> records;
        DatabaseTable table = this.database.getTable(DolarTodayProviderDatabaseConstants.PROVIDER_INFO_TABLE_NAME);

        try{
            table.loadToMemory();
            records = table.getRecords();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetProviderInfoException(CantGetProviderInfoException.DEFAULT_MESSAGE);
        }

        if (records.size() != 1)
            throw new CantGetProviderInfoException("Inconsistent number of fetched records (" + records.size() + "), should be 1.");

        return records.get(0);
    }
    private void populateProviderInfo(String providerName) throws CantInitializeProviderInfoException {
        DatabaseTable table = this.database.getTable(DolarTodayProviderDatabaseConstants.PROVIDER_INFO_TABLE_NAME);
        DatabaseTableRecord newRecord = table.getEmptyRecord();

        newRecord.setUUIDValue(DolarTodayProviderDatabaseConstants.PROVIDER_INFO_ID_COLUMN_NAME, UUID.randomUUID());
        newRecord.setStringValue(DolarTodayProviderDatabaseConstants.PROVIDER_INFO_NAME_COLUMN_NAME, providerName);

        try {
            table.insertRecord(newRecord);
        }catch (CantInsertRecordException e) {
            throw new CantInitializeProviderInfoException(e.getMessage());
        }
    }



    /* INTERNAL HELPER FUNCTIONS */
    private DatabaseTableFilter getEmptyTableFilter() {
        return this.database.getTable(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME).getEmptyTableFilter();
    }



    private List<DatabaseTableRecord> getRecordsByFilter(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = this.database.getTable(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();
        return table.getRecords();
    }


    private void constructRecordFromExchangeRate(DatabaseTableRecord newRecord, ExchangeRate exchangeRate) {

        newRecord.setUUIDValue(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_ID_COLUMN_NAME, UUID.randomUUID());
        newRecord.setStringValue(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_FROM_CURRENCY_COLUMN_NAME, exchangeRate.getFromCurrency().getCode());
        newRecord.setStringValue(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_TO_CURRENCY_COLUMN_NAME, exchangeRate.getToCurrency().getCode());
        newRecord.setDoubleValue(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_SALE_PRICE_COLUMN_NAME, exchangeRate.getSalePrice());
        newRecord.setDoubleValue(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_PURCHASE_PRICE_COLUMN_NAME, exchangeRate.getPurchasePrice());
        newRecord.setLongValue(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_TIMESTAMP_COLUMN_NAME, (new Date().getTime() / 1000));

    }

    private ExchangeRate constructExchangeRateFromRecord(DatabaseTableRecord record) throws CantCreateExchangeRateException {

        UUID id = record.getUUIDValue(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_ID_COLUMN_NAME);
        double salePrice = record.getDoubleValue(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_SALE_PRICE_COLUMN_NAME);
        double purchasePrice = record.getDoubleValue(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_PURCHASE_PRICE_COLUMN_NAME);
        long timestamp = record.getLongValue(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_TIMESTAMP_COLUMN_NAME);

        Currency fromCurrency;
        try {
            String fromCurrencyStr = record.getStringValue(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_FROM_CURRENCY_COLUMN_NAME);

            if(FiatCurrency.codeExists(fromCurrencyStr))
                fromCurrency = FiatCurrency.getByCode(fromCurrencyStr);
            else if(CryptoCurrency.codeExists(fromCurrencyStr))
                fromCurrency = CryptoCurrency.getByCode(fromCurrencyStr);
            else throw new InvalidParameterException();

        } catch (InvalidParameterException e) {
            throw new CantCreateExchangeRateException(e.getMessage(), e, "Dolartoday provider plugin", "Invalid From Currency value stored in table"
                    + DolarTodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME + " for id " + id);
        }

        Currency toCurrency;
        try {
            String toCurrencyStr = record.getStringValue(DolarTodayProviderDatabaseConstants.QUERY_HISTORY_TO_CURRENCY_COLUMN_NAME);

            if(FiatCurrency.codeExists(toCurrencyStr))
                toCurrency = FiatCurrency.getByCode(toCurrencyStr);
            else if(CryptoCurrency.codeExists(toCurrencyStr))
                toCurrency = CryptoCurrency.getByCode(toCurrencyStr);
            else throw new InvalidParameterException();

        } catch (InvalidParameterException e) {
            throw new CantCreateExchangeRateException(e.getMessage(), e, "Dolartoday provider plugin", "Invalid To Currency value stored in table"
                    + DolarTodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME + " for id " + id);
        }

        return new ExchangeRateImpl(fromCurrency, toCurrency, salePrice, purchasePrice, timestamp);
    }



}
