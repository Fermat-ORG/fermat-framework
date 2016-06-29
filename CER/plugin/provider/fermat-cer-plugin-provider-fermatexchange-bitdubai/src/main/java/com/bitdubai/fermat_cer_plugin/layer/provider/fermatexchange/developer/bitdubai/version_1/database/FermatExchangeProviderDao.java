package com.bitdubai.fermat_cer_plugin.layer.provider.fermatexchange.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cer_api.all_definition.enums.ExchangeRateType;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.all_definition.utils.CurrencyPairImpl;
import com.bitdubai.fermat_cer_api.all_definition.utils.ExchangeRateImpl;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantCreateExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantInitializeProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantSaveExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.utils.DateHelper;
import com.bitdubai.fermat_cer_plugin.layer.provider.fermatexchange.developer.bitdubai.version_1.ProviderFermatExchangePluginRoot;
import com.bitdubai.fermat_cer_plugin.layer.provider.fermatexchange.developer.bitdubai.version_1.exceptions.CantInitializeFermatExchangeProviderDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public class FermatExchangeProviderDao {


    private final ProviderFermatExchangePluginRoot pluginRoot;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public FermatExchangeProviderDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final ProviderFermatExchangePluginRoot pluginRoot) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.pluginRoot = pluginRoot;
    }


    public void initialize() throws CantInitializeFermatExchangeProviderDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            FermatExchangeProviderDatabaseFactory databaseFactory = new FermatExchangeProviderDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeFermatExchangeProviderDatabaseException("Database could not be opened", cantCreateDatabaseException, "Database Name: " + FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TABLE_NAME, "");
            }
        }catch (CantOpenDatabaseException cantOpenDatabaseException) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeFermatExchangeProviderDatabaseException("Database could not be opened", cantOpenDatabaseException, "Database Name: " + FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TABLE_NAME, "");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeFermatExchangeProviderDatabaseException("Database could not be opened", FermatException.wrapException(e), "Database Name: " + FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TABLE_NAME, "");
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



    /* CURRENT EXCHANGE RATE METHODS */

    public void saveCurrentExchangeRate(ExchangeRate exchangeRate) throws CantSaveExchangeRateException {

        DatabaseTable table = this.database.getTable(FermatExchangeProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TABLE_NAME);
        DatabaseTableRecord newRecord = table.getEmptyRecord();
        constructRecordFromExchangeRate(newRecord, exchangeRate);
        try {
            table.insertRecord(newRecord);
        }catch (CantInsertRecordException e) {
            throw new CantSaveExchangeRateException(e.getMessage(), e, "FermatExchange provider plugin", "Cant save new record in table");
        }
    }




    /* DAILY EXCHANGE RATE METHODS */


    public void saveDailyExchangeRate(ExchangeRate e) throws CantSaveExchangeRateException {

        //Create new exchangeRate with standarized daily timestamp
        e = new ExchangeRateImpl(e.getFromCurrency(), e.getToCurrency(), e.getSalePrice(), e.getPurchasePrice(),
                DateHelper.getStandarizedTimestampFromTimestamp(e.getTimestamp()));

        //If it exists, return
        if(this.dailyExchangeRateExists(e))
            return;

        //Else, save it
        DatabaseTable table = this.database.getTable(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TABLE_NAME);
        DatabaseTableRecord newRecord = table.getEmptyRecord();
        constructRecordFromExchangeRate(newRecord, e);
        try {
            table.insertRecord(newRecord);
        }catch (CantInsertRecordException ex) {
            throw new CantSaveExchangeRateException(CantSaveExchangeRateException.DEFAULT_MESSAGE, ex, "FermatExchange provider plugin", "Cant save new record in DAILY_EXCHANGE_RATES_TABLE");
        }
    }

    public void updateDailyExchangeRateTable(CurrencyPair currencyPair, List<ExchangeRate> exchangeRates) throws CantSaveExchangeRateException
    {
        List<String> exchangeRateTimestampsInDatabase = new ArrayList<>();

        DatabaseTable table = this.database.getTable(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TABLE_NAME);
        table.addStringFilter(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_FROM_CURRENCY_COLUMN_NAME, currencyPair.getFrom().getCode(), DatabaseFilterType.EQUAL);
        table.addStringFilter(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TO_CURRENCY_COLUMN_NAME, currencyPair.getTo().getCode(), DatabaseFilterType.EQUAL);

        try {
            table.loadToMemory();

            for (DatabaseTableRecord record : table.getRecords()) {
                String timestamp = record.getStringValue(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TIMESTAMP_COLUMN_NAME);
                exchangeRateTimestampsInDatabase.add(timestamp);
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantSaveExchangeRateException(CantSaveExchangeRateException.DEFAULT_MESSAGE, e, "Failed to get ExchangeRates in database for CurrencyPair: " + currencyPair.toString(), "Couldn't load table to memory");
        }

        for(ExchangeRate e : exchangeRates)
        {
            String currentTimestamp = String.valueOf(e.getTimestamp());
            if(!exchangeRateTimestampsInDatabase.contains(currentTimestamp)) {
                this.saveDailyExchangeRate(e);              //TODO: improve this.. saving one by one..
            }
        }
    }

    public boolean dailyExchangeRateExists(ExchangeRate e)
    {
        try{
            getDailyExchangeRateFromDate(new CurrencyPairImpl(e.getFromCurrency(), e.getToCurrency()), e.getTimestamp());
            return true;
        } catch (CantGetExchangeRateException ex) {
            return false;
        }
    }

    public ExchangeRate getDailyExchangeRateFromDate(CurrencyPair currencyPair, long timestamp) throws CantGetExchangeRateException
    {
        DatabaseTable table = this.database.getTable(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TABLE_NAME);

        table.addStringFilter(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_FROM_CURRENCY_COLUMN_NAME, currencyPair.getFrom().getCode(), DatabaseFilterType.EQUAL);
        table.addStringFilter(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TO_CURRENCY_COLUMN_NAME, currencyPair.getTo().getCode(), DatabaseFilterType.EQUAL);
        table.addStringFilter(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TIMESTAMP_COLUMN_NAME, String.valueOf(timestamp), DatabaseFilterType.EQUAL);

        try {
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            if (records.size() > 0){
                DatabaseTableRecord record = records.get(0);
                return constructExchangeRateFromRecord(record);
            }
            else {
                throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, null, "Failed to get daily exchange rate for timestamp: " + timestamp, "Exchange Rate not found in database");
            }

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, e, "Failed to get daily exchange rate for timestamp: " + timestamp, "Couldn't load table to memory");
        }catch (CantCreateExchangeRateException e) {
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, e, "Failed to get daily exchange rate for timestamp: " + timestamp, "Couldn't create ExchangeRate object");
        }
    }

    public List<ExchangeRate> getDailyExchangeRatesForPeriod(CurrencyPair currencyPair, long startTimestamp, long endTimestamp) throws CantGetExchangeRateException
    {
        List<ExchangeRate> exchangeRates = new ArrayList<>();


        String query = "SELECT * FROM " +
                FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TABLE_NAME +
                " WHERE " +
                FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_FROM_CURRENCY_COLUMN_NAME +
                " = '" +
                currencyPair.getFrom().getCode() +
                "' AND " +
                FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TO_CURRENCY_COLUMN_NAME +
                " = '" +
                currencyPair.getTo().getCode() +
                "' " +
                " AND " +
                FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TIMESTAMP_COLUMN_NAME +
                " >= " +
                startTimestamp +
                " AND " +
                FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TIMESTAMP_COLUMN_NAME +
                " <= " +
                endTimestamp +
                " ORDER BY " +
                FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TIMESTAMP_COLUMN_NAME +
                " ASC ";


        DatabaseTable table = this.database.getTable(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TABLE_NAME);

        try {
            for (DatabaseTableRecord record : table.customQuery(query, false))
                exchangeRates.add(constructExchangeRateFromRecord(record));
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, e, "Failed to get daily exchange rates for period: " + startTimestamp + " to " + endTimestamp, "Couldn't load table to memory");
        }catch (CantCreateExchangeRateException e) {
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, e, "Failed to get daily exchange rates for period: " + startTimestamp + " to " + endTimestamp, "Couldn't create ExchangeRate object");
        }

        return exchangeRates;
    }












    public List<ExchangeRate> getQueriedExchangeRateHistory(ExchangeRateType exchangeRateType, CurrencyPair currencyPair) throws CantGetExchangeRateException
    {
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        DatabaseTable table = null;

        switch(exchangeRateType) {
            case CURRENT:
                table = this.database.getTable(FermatExchangeProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TABLE_NAME);
                table.addStringFilter(FermatExchangeProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_FROM_CURRENCY_COLUMN_NAME, currencyPair.getFrom().getCode(), DatabaseFilterType.EQUAL);
                table.addStringFilter(FermatExchangeProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TO_CURRENCY_COLUMN_NAME, currencyPair.getTo().getCode(), DatabaseFilterType.EQUAL);
                break;
            case DAILY:
                table = this.database.getTable(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TABLE_NAME);
                table.addStringFilter(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_FROM_CURRENCY_COLUMN_NAME, currencyPair.getFrom().getCode(), DatabaseFilterType.EQUAL);
                table.addStringFilter(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TO_CURRENCY_COLUMN_NAME, currencyPair.getTo().getCode(), DatabaseFilterType.EQUAL);
                break;
        }

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





    /* PROVIDER INFO METHODS */


    public String getProviderName() throws CantGetProviderInfoException {
        DatabaseTableRecord record = this.getProviderInfo();
        return record.getStringValue(FermatExchangeProviderDatabaseConstants.PROVIDER_INFO_NAME_COLUMN_NAME);
    }

    public UUID getProviderId() throws CantGetProviderInfoException {
        DatabaseTableRecord record = this.getProviderInfo();
        return record.getUUIDValue(FermatExchangeProviderDatabaseConstants.PROVIDER_INFO_ID_COLUMN_NAME);
    }

    private DatabaseTableRecord getProviderInfo() throws CantGetProviderInfoException {
        List<DatabaseTableRecord> records;
        DatabaseTable table = this.database.getTable(FermatExchangeProviderDatabaseConstants.PROVIDER_INFO_TABLE_NAME);

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
        DatabaseTable table = this.database.getTable(FermatExchangeProviderDatabaseConstants.PROVIDER_INFO_TABLE_NAME);
        DatabaseTableRecord newRecord = table.getEmptyRecord();

        newRecord.setUUIDValue(FermatExchangeProviderDatabaseConstants.PROVIDER_INFO_ID_COLUMN_NAME, UUID.randomUUID());
        newRecord.setStringValue(FermatExchangeProviderDatabaseConstants.PROVIDER_INFO_NAME_COLUMN_NAME, providerName);

        try {
            table.insertRecord(newRecord);
        }catch (CantInsertRecordException e) {
            throw new CantInitializeProviderInfoException(e.getMessage());
        }
    }






    /* INTERNAL HELPER FUNCTIONS */
    private DatabaseTableFilter getEmptyTableFilter() {
        return this.database.getTable(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TABLE_NAME).getEmptyTableFilter();
    }



    private List<DatabaseTableRecord> getRecordsByFilter(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = this.database.getTable(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();
        return table.getRecords();
    }


    private void constructRecordFromExchangeRate(DatabaseTableRecord newRecord, ExchangeRate exchangeRate) {

        newRecord.setUUIDValue(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_ID_COLUMN_NAME, UUID.randomUUID());
        newRecord.setStringValue(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_FROM_CURRENCY_COLUMN_NAME, exchangeRate.getFromCurrency().getCode());
        newRecord.setStringValue(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TO_CURRENCY_COLUMN_NAME, exchangeRate.getToCurrency().getCode());
        newRecord.setStringValue(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_SALE_PRICE_COLUMN_NAME, String.valueOf(exchangeRate.getSalePrice()));
        newRecord.setStringValue(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_PURCHASE_PRICE_COLUMN_NAME, String.valueOf(exchangeRate.getPurchasePrice()));
        newRecord.setLongValue(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TIMESTAMP_COLUMN_NAME, exchangeRate.getTimestamp());

    }

    private ExchangeRate constructExchangeRateFromRecord(DatabaseTableRecord record) throws CantCreateExchangeRateException {

        UUID id = record.getUUIDValue(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_ID_COLUMN_NAME);
        double salePrice = record.getDoubleValue(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_SALE_PRICE_COLUMN_NAME);
        double purchasePrice = record.getDoubleValue(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_PURCHASE_PRICE_COLUMN_NAME);
        long timestamp = record.getLongValue(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TIMESTAMP_COLUMN_NAME);

        Currency fromCurrency;
        try {
            String fromCurrencyStr = record.getStringValue(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_FROM_CURRENCY_COLUMN_NAME);

            if(FiatCurrency.codeExists(fromCurrencyStr))
                fromCurrency = FiatCurrency.getByCode(fromCurrencyStr);
            else if(CryptoCurrency.codeExists(fromCurrencyStr))
                fromCurrency = CryptoCurrency.getByCode(fromCurrencyStr);
            else throw new InvalidParameterException();

        } catch (InvalidParameterException e) {
            throw new CantCreateExchangeRateException(e.getMessage(), e, "FermatExchange provider plugin", "Invalid From Currency value stored in table"
                    + FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TABLE_NAME + " for id " + id);
        }

        Currency toCurrency;
        try {
            String toCurrencyStr = record.getStringValue(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TO_CURRENCY_COLUMN_NAME);

            if(FiatCurrency.codeExists(toCurrencyStr))
                toCurrency = FiatCurrency.getByCode(toCurrencyStr);
            else if(CryptoCurrency.codeExists(toCurrencyStr))
                toCurrency = CryptoCurrency.getByCode(toCurrencyStr);
            else throw new InvalidParameterException();

        } catch (InvalidParameterException e) {
            throw new CantCreateExchangeRateException(e.getMessage(), e, "FermatExchange provider plugin", "Invalid To Currency value stored in table"
                    + FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TABLE_NAME + " for id " + id);
        }

        return new ExchangeRateImpl(fromCurrency, toCurrency, salePrice, purchasePrice, timestamp);
    }



}
