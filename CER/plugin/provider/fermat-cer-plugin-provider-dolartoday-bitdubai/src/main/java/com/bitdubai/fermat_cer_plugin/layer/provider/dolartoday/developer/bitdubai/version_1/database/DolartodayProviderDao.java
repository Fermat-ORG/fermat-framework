package com.bitdubai.fermat_cer_plugin.layer.provider.dolartoday.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_cer_api.all_definition.enums.Currency;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantCreateExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantSaveExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_plugin.layer.provider.dolartoday.developer.bitdubai.version_1.exceptions.CantInitializeDolartodayProviderDatabaseException;
import com.bitdubai.fermat_cer_plugin.layer.provider.dolartoday.developer.bitdubai.version_1.structure.ExchangeRateImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public class DolartodayProviderDao {


    private final ErrorManager errorManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public DolartodayProviderDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }


    public void initialize() throws CantInitializeDolartodayProviderDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            DolartodayProviderDatabaseFactory databaseFactory = new DolartodayProviderDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeDolartodayProviderDatabaseException("Database could not be opened", cantCreateDatabaseException, "Database Name: " + DolartodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME, "");
            }
        }catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeDolartodayProviderDatabaseException("Database could not be opened", cantOpenDatabaseException, "Database Name: " + DolartodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME, "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeDolartodayProviderDatabaseException("Database could not be opened", FermatException.wrapException(e), "Database Name: " + DolartodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME, "");
        }
    }



    public void saveExchangeRate(ExchangeRate exchangeRate) throws CantSaveExchangeRateException {

        DatabaseTable table = this.database.getTable(DolartodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME);
        DatabaseTableRecord newRecord = table.getEmptyRecord();
        constructRecordFromExchangeRate(newRecord, exchangeRate);
        try {
            table.insertRecord(newRecord);
        }catch (CantInsertRecordException e) {
            throw new CantSaveExchangeRateException(e.getMessage(), e, "Dolartoday provider plugin", "Cant save new record in table");
        }
    }

    /*public List<ExchangeRate> getQueriedExchangeRateHistory(CurrencyPair currencyPair) throws CantGetFiatIndexException
    {
        List<FiatIndex> fiatIndexList = new ArrayList<>();

        DatabaseTableFilter filter = getEmptyHoldTableFilter();
        filter.setColumn(FiatIndexWorldDatabaseConstants.FIAT_INDEX_CURRENCY_COLUMN_NAME);
        filter.setValue(fiatCurrency.getCode());
        filter.setType(DatabaseFilterType.EQUAL);


        try {
            for (DatabaseTableRecord record : getRecordsByFilter(filter)) {
                FiatIndex fiatIndex = constructExchangeRateFromRecord(record);
                fiatIndexList.add(fiatIndex);
            }
        } catch (CantCreateFiatIndexException e) {
            throw new CantGetFiatIndexException(CantGetFiatIndexException.DEFAULT_MESSAGE, e, "Failed to get Queried Fiat Index History list. Filter: " + filter.toString(), "");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetFiatIndexException(CantGetFiatIndexException.DEFAULT_MESSAGE, e, "Failed to get Queried Fiat Index History list. Filter: " + filter.toString(), "");
        }

        return fiatIndexList;
    }*/









    /* INTERNAL HELPER FUNCTIONS */
    private DatabaseTableFilter getEmptyHoldTableFilter() {
        return this.database.getTable(DolartodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME).getEmptyTableFilter();
    }



    private List<DatabaseTableRecord> getRecordsByFilter(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = this.database.getTable(DolartodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME);

        if (filter != null)
            table.setStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();
        return table.getRecords();
    }


    private void constructRecordFromExchangeRate(DatabaseTableRecord newRecord, ExchangeRate exchangeRate) {

        newRecord.setUUIDValue(DolartodayProviderDatabaseConstants.QUERY_HISTORY_ID_COLUMN_NAME, UUID.randomUUID());
        newRecord.setStringValue(DolartodayProviderDatabaseConstants.QUERY_HISTORY_FROM_CURRENCY_COLUMN_NAME, exchangeRate.getFromCurrency().getCode());
        newRecord.setStringValue(DolartodayProviderDatabaseConstants.QUERY_HISTORY_TO_CURRENCY_COLUMN_NAME, exchangeRate.getToCurrency().getCode());
        newRecord.setDoubleValue(DolartodayProviderDatabaseConstants.QUERY_HISTORY_SALE_PRICE_COLUMN_NAME, exchangeRate.getSalePrice());
        newRecord.setDoubleValue(DolartodayProviderDatabaseConstants.QUERY_HISTORY_PURCHASE_PRICE_COLUMN_NAME, exchangeRate.getPurchasePrice());
        newRecord.setLongValue(DolartodayProviderDatabaseConstants.QUERY_HISTORY_TIMESTAMP_COLUMN_NAME, (new Date().getTime() / 1000));

    }

    private ExchangeRate constructExchangeRateFromRecord(DatabaseTableRecord record) throws CantCreateExchangeRateException {

        UUID id = record.getUUIDValue(DolartodayProviderDatabaseConstants.QUERY_HISTORY_ID_COLUMN_NAME);
        double salePrice = record.getDoubleValue(DolartodayProviderDatabaseConstants.QUERY_HISTORY_SALE_PRICE_COLUMN_NAME);
        double purchasePrice = record.getDoubleValue(DolartodayProviderDatabaseConstants.QUERY_HISTORY_PURCHASE_PRICE_COLUMN_NAME);
        long timestamp = record.getLongValue(DolartodayProviderDatabaseConstants.QUERY_HISTORY_TIMESTAMP_COLUMN_NAME);

        Currency fromCurrency;
        try {
            fromCurrency = Currency.getByCode(record.getStringValue(DolartodayProviderDatabaseConstants.QUERY_HISTORY_FROM_CURRENCY_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            throw new CantCreateExchangeRateException(e.getMessage(), e, "Dolartoday provider plugin", "Invalid From Currency value stored in table"
                    + DolartodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME + " for id " + id);
        }

        Currency toCurrency;
        try {
            toCurrency = Currency.getByCode(record.getStringValue(DolartodayProviderDatabaseConstants.QUERY_HISTORY_TO_CURRENCY_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            throw new CantCreateExchangeRateException(e.getMessage(), e, "Dolartoday provider plugin", "Invalid To Currency value stored in table"
                    + DolartodayProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME + " for id " + id);
        }

        return new ExchangeRateImpl(fromCurrency, toCurrency, salePrice, purchasePrice, timestamp);
    }



}
