package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.world.exceptions.CantGetFiatIndexException;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.exceptions.CantInitializeFiatIndexWorldDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.FiatIndexImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cbp_api.layer.world.exceptions.CantCreateFiatIndexException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/21/2015.
 */
public class FiatIndexWorldDao {

    private final ErrorManager errorManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public FiatIndexWorldDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }


    public void initialize() throws CantInitializeFiatIndexWorldDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            FiatIndexWorldDatabaseFactory databaseFactory = new FiatIndexWorldDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeFiatIndexWorldDatabaseException("Database could not be opened", cantCreateDatabaseException, "Database Name: " + pluginId.toString(), "");
            }
        }catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeFiatIndexWorldDatabaseException("Database could not be opened", cantOpenDatabaseException, "Database Name: " + pluginId.toString(), "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeFiatIndexWorldDatabaseException("Database could not be opened", FermatException.wrapException(e), "Database Name: " + pluginId.toString(), "");
        }
    }



    public void saveFiatIndex(FiatIndex fiatIndex) throws CantCreateFiatIndexException {

        DatabaseTable transactionTable = this.database.getTable(FiatIndexWorldDatabaseConstants.FIAT_INDEX_TABLE_NAME);
        DatabaseTableRecord newRecord = transactionTable.getEmptyRecord();
        constructRecordFromFiatIndex(newRecord, fiatIndex);        //Insertar valores en el record
        try {
            transactionTable.insertRecord(newRecord);
        }catch (CantInsertRecordException e) {
            throw new CantCreateFiatIndexException(e.getMessage(), e, "Fiat Index Plugin", "Cant insert new record in table");
        }
    }

    public List<FiatIndex> getQueriedIndexHistory(FiatCurrency fiatCurrency) throws CantGetFiatIndexException
    {
        List<FiatIndex> fiatIndexList = new ArrayList<>();

        DatabaseTableFilter filter = getEmptyHoldTableFilter();
        filter.setColumn(FiatIndexWorldDatabaseConstants.FIAT_INDEX_CURRENCY_COLUMN_NAME);
        filter.setValue(fiatCurrency.getCode());
        filter.setType(DatabaseFilterType.EQUAL);


        try {
            for (DatabaseTableRecord record : getRecordsByFilter(filter)) {
                FiatIndex fiatIndex = constructFiatIndexFromRecord(record);
                fiatIndexList.add(fiatIndex);
            }
        } catch (CantCreateFiatIndexException e) {
                throw new CantGetFiatIndexException(CantGetFiatIndexException.DEFAULT_MESSAGE, e, "Failed to get Queried Fiat Index History list. Filter: " + filter.toString(), "");
        } catch (CantLoadTableToMemoryException e) {
                throw new CantGetFiatIndexException(CantGetFiatIndexException.DEFAULT_MESSAGE, e, "Failed to get Queried Fiat Index History list. Filter: " + filter.toString(), "");
        }

    return fiatIndexList;
    }









    /* INTERNAL HELPER FUNCTIONS */
    private DatabaseTableFilter getEmptyHoldTableFilter() {
        return this.database.getTable(FiatIndexWorldDatabaseConstants.FIAT_INDEX_TABLE_NAME).getEmptyTableFilter();
    }



    private List<DatabaseTableRecord> getRecordsByFilter(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = this.database.getTable(FiatIndexWorldDatabaseConstants.FIAT_INDEX_TABLE_NAME);

        if (filter != null)
            table.setStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();
        return table.getRecords();
    }


    private void constructRecordFromFiatIndex(DatabaseTableRecord newRecord, FiatIndex fiatIndex) {

        newRecord.setUUIDValue(FiatIndexWorldDatabaseConstants.FIAT_INDEX_ID_COLUMN_NAME, UUID.randomUUID());
        newRecord.setStringValue(FiatIndexWorldDatabaseConstants.FIAT_INDEX_CURRENCY_COLUMN_NAME, fiatIndex.getCurrency().getCode());
        newRecord.setDoubleValue(FiatIndexWorldDatabaseConstants.FIAT_INDEX_SALE_PRICE_COLUMN_NAME, fiatIndex.getSalePrice());
        newRecord.setDoubleValue(FiatIndexWorldDatabaseConstants.FIAT_INDEX_PURCHASE_PRICE_COLUMN_NAME, fiatIndex.getPurchasePrice());
        newRecord.setStringValue(FiatIndexWorldDatabaseConstants.FIAT_INDEX_PROVIDER_DESCRIPTION_COLUMN_NAME, fiatIndex.getProviderDescription());
        newRecord.setLongValue(FiatIndexWorldDatabaseConstants.FIAT_INDEX_TIMESTAMP_COLUMN_NAME, (new Date().getTime() / 1000));

    }

    private FiatIndex constructFiatIndexFromRecord(DatabaseTableRecord record) throws CantCreateFiatIndexException{

        UUID id = record.getUUIDValue(FiatIndexWorldDatabaseConstants.FIAT_INDEX_ID_COLUMN_NAME);
        double salePrice = record.getDoubleValue(FiatIndexWorldDatabaseConstants.FIAT_INDEX_SALE_PRICE_COLUMN_NAME);
        double purchasePrice = record.getDoubleValue(FiatIndexWorldDatabaseConstants.FIAT_INDEX_PURCHASE_PRICE_COLUMN_NAME);
        String providerDescription = record.getStringValue(FiatIndexWorldDatabaseConstants.FIAT_INDEX_PROVIDER_DESCRIPTION_COLUMN_NAME);
        long timestamp = record.getLongValue(FiatIndexWorldDatabaseConstants.FIAT_INDEX_TIMESTAMP_COLUMN_NAME);

        FiatCurrency currency;
        try {
            currency = FiatCurrency.getByCode(record.getStringValue(FiatIndexWorldDatabaseConstants.FIAT_INDEX_CURRENCY_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            throw new CantCreateFiatIndexException(e.getMessage(), e, "Hold Transaction", "Invalid FiatCurrency value stored in table"
                    + FiatIndexWorldDatabaseConstants.FIAT_INDEX_TABLE_NAME + " for id " + id);
        }

        return new FiatIndexImpl(currency, FiatCurrency.US_DOLLAR, salePrice, purchasePrice, timestamp, providerDescription);
    }







}
