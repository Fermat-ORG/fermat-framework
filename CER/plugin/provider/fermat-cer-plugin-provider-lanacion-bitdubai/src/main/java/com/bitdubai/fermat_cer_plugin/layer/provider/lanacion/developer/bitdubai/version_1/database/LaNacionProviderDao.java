package com.bitdubai.fermat_cer_plugin.layer.provider.lanacion.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cer_plugin.layer.provider.lanacion.developer.bitdubai.version_1.exceptions.CantInitializeLaNacionProviderDatabaseException;
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
public class LaNacionProviderDao {


    private final ErrorManager errorManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public LaNacionProviderDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }


    public void initialize() throws CantInitializeLaNacionProviderDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            LaNacionProviderDatabaseFactory databaseFactory = new LaNacionProviderDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.LANACION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeLaNacionProviderDatabaseException("Database could not be opened", cantCreateDatabaseException, "Database Name: " + LaNacionProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME, "");
            }
        }catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.LANACION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeLaNacionProviderDatabaseException("Database could not be opened", cantOpenDatabaseException, "Database Name: " + LaNacionProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME, "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.LANACION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeLaNacionProviderDatabaseException("Database could not be opened", FermatException.wrapException(e), "Database Name: " + LaNacionProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME, "");
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

        DatabaseTable table = this.database.getTable(LaNacionProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME);
        DatabaseTableRecord newRecord = table.getEmptyRecord();
        constructRecordFromExchangeRate(newRecord, exchangeRate);
        try {
            table.insertRecord(newRecord);
        }catch (CantInsertRecordException e) {
            throw new CantSaveExchangeRateException(e.getMessage(), e, "LaNacion provider plugin", "Cant save new record in table");
        }
    }

    public List<ExchangeRate> getQueriedExchangeRateHistory(CurrencyPair currencyPair) throws CantGetExchangeRateException
    {
        List<ExchangeRate> exchangeRateList = new ArrayList<>();

        DatabaseTable table = this.database.getTable(LaNacionProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME);

        table.addStringFilter(LaNacionProviderDatabaseConstants.QUERY_HISTORY_FROM_CURRENCY_COLUMN_NAME, currencyPair.getFrom().getCode(), DatabaseFilterType.EQUAL);
        table.addStringFilter(LaNacionProviderDatabaseConstants.QUERY_HISTORY_TO_CURRENCY_COLUMN_NAME, currencyPair.getTo().getCode(), DatabaseFilterType.EQUAL);

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
        return record.getStringValue(LaNacionProviderDatabaseConstants.PROVIDER_INFO_NAME_COLUMN_NAME);
    }

    public UUID getProviderId() throws CantGetProviderInfoException {
        DatabaseTableRecord record = this.getProviderInfo();
        return record.getUUIDValue(LaNacionProviderDatabaseConstants.PROVIDER_INFO_ID_COLUMN_NAME);
    }

    private DatabaseTableRecord getProviderInfo() throws CantGetProviderInfoException {
        List<DatabaseTableRecord> records;
        DatabaseTable table = this.database.getTable(LaNacionProviderDatabaseConstants.PROVIDER_INFO_TABLE_NAME);

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
        DatabaseTable table = this.database.getTable(LaNacionProviderDatabaseConstants.PROVIDER_INFO_TABLE_NAME);
        DatabaseTableRecord newRecord = table.getEmptyRecord();

        newRecord.setUUIDValue(LaNacionProviderDatabaseConstants.PROVIDER_INFO_ID_COLUMN_NAME, UUID.randomUUID());
        newRecord.setStringValue(LaNacionProviderDatabaseConstants.PROVIDER_INFO_NAME_COLUMN_NAME, providerName);

        try {
            table.insertRecord(newRecord);
        }catch (CantInsertRecordException e) {
            throw new CantInitializeProviderInfoException(e.getMessage());
        }
    }



    /* INTERNAL HELPER FUNCTIONS */
    private DatabaseTableFilter getEmptyTableFilter() {
        return this.database.getTable(LaNacionProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME).getEmptyTableFilter();
    }



    private List<DatabaseTableRecord> getRecordsByFilter(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = this.database.getTable(LaNacionProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();
        return table.getRecords();
    }


    private void constructRecordFromExchangeRate(DatabaseTableRecord newRecord, ExchangeRate exchangeRate) {

        newRecord.setUUIDValue(LaNacionProviderDatabaseConstants.QUERY_HISTORY_ID_COLUMN_NAME, UUID.randomUUID());
        newRecord.setStringValue(LaNacionProviderDatabaseConstants.QUERY_HISTORY_FROM_CURRENCY_COLUMN_NAME, exchangeRate.getFromCurrency().getCode());
        newRecord.setStringValue(LaNacionProviderDatabaseConstants.QUERY_HISTORY_TO_CURRENCY_COLUMN_NAME, exchangeRate.getToCurrency().getCode());
        newRecord.setDoubleValue(LaNacionProviderDatabaseConstants.QUERY_HISTORY_SALE_PRICE_COLUMN_NAME, exchangeRate.getSalePrice());
        newRecord.setDoubleValue(LaNacionProviderDatabaseConstants.QUERY_HISTORY_PURCHASE_PRICE_COLUMN_NAME, exchangeRate.getPurchasePrice());
        newRecord.setLongValue(LaNacionProviderDatabaseConstants.QUERY_HISTORY_TIMESTAMP_COLUMN_NAME, (new Date().getTime() / 1000));

    }

    private ExchangeRate constructExchangeRateFromRecord(DatabaseTableRecord record) throws CantCreateExchangeRateException {

        UUID id = record.getUUIDValue(LaNacionProviderDatabaseConstants.QUERY_HISTORY_ID_COLUMN_NAME);
        double salePrice = record.getDoubleValue(LaNacionProviderDatabaseConstants.QUERY_HISTORY_SALE_PRICE_COLUMN_NAME);
        double purchasePrice = record.getDoubleValue(LaNacionProviderDatabaseConstants.QUERY_HISTORY_PURCHASE_PRICE_COLUMN_NAME);
        long timestamp = record.getLongValue(LaNacionProviderDatabaseConstants.QUERY_HISTORY_TIMESTAMP_COLUMN_NAME);

        Currency fromCurrency;
        try {
            String fromCurrencyStr = record.getStringValue(LaNacionProviderDatabaseConstants.QUERY_HISTORY_FROM_CURRENCY_COLUMN_NAME);

            if(FiatCurrency.codeExists(fromCurrencyStr))
                fromCurrency = FiatCurrency.getByCode(fromCurrencyStr);
            else if(CryptoCurrency.codeExists(fromCurrencyStr))
                fromCurrency = CryptoCurrency.getByCode(fromCurrencyStr);
            else throw new InvalidParameterException();

        } catch (InvalidParameterException e) {
            throw new CantCreateExchangeRateException(e.getMessage(), e, "LaNacion provider plugin", "Invalid From Currency value stored in table"
                    + LaNacionProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME + " for id " + id);
        }

        Currency toCurrency;
        try {
            String toCurrencyStr = record.getStringValue(LaNacionProviderDatabaseConstants.QUERY_HISTORY_TO_CURRENCY_COLUMN_NAME);

            if(FiatCurrency.codeExists(toCurrencyStr))
                toCurrency = FiatCurrency.getByCode(toCurrencyStr);
            else if(CryptoCurrency.codeExists(toCurrencyStr))
                toCurrency = CryptoCurrency.getByCode(toCurrencyStr);
            else throw new InvalidParameterException();

        } catch (InvalidParameterException e) {
            throw new CantCreateExchangeRateException(e.getMessage(), e, "LaNacion provider plugin", "Invalid To Currency value stored in table"
                    + LaNacionProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME + " for id " + id);
        }

        return new ExchangeRateImpl(fromCurrency, toCurrency, salePrice, purchasePrice, timestamp);
    }



}
