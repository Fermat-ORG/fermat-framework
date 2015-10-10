package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.cash_money_stock_replenishment.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.cash_money_stock_replenishment.developer.bitdubai.version_1.util.CashMoneyStockReplenishmentBusinessTransactionWrapper;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.cash_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantInitializeCashMoneyStockReplenishmentBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.cash_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantInsertRecordCashMoneyStockReplenishmentBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.cash_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CashMoneyStockReplenishmentBusinessTransactionInconsistentTableStateException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.cash_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantUpdateStatusCashMoneyStockReplenishmentBusinessTransactionException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 27.09.15.
 */
public class CashMoneyStockReplenishmentBusinessTransactionDao{

    /**
     * CryptoAddressBook Interface member variables.
     */
    private Database database;

    /**
     * DealsWithDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;


    /**
     * Constructor.
     */
    public CashMoneyStockReplenishmentBusinessTransactionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * CashMoneyStockReplenishment Interface implementation.
     */
    public void initialize() throws CantInitializeCashMoneyStockReplenishmentBusinessTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CashMoneyStockReplenishmentBusinessTransactionDatabaseFactory databaseFactory = new CashMoneyStockReplenishmentBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCashMoneyStockReplenishmentBusinessTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCashMoneyStockReplenishmentBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCashMoneyStockReplenishmentBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCashMoneyStockReplenishmentBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }


    public void createNewCashMoneyStockReplenishment(
            String publicKeyBroker,
            String merchandiseCurrency,
            String executionTransactionId,
            CashCurrencyType cashCurrencyType
    ) throws CantInsertRecordCashMoneyStockReplenishmentBusinessTransactionException {
        try {
            DatabaseTable transactionTable = this.database.getTable(CashMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CASH_MONEY_STOCK_REPLENISHMENT_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = transactionTable.getEmptyRecord();
            BusinessTransactionStatus transactionStatus = BusinessTransactionStatus.PENDING_PAYMENT;
            loadRecordAsNew(recordToInsert, transactionStatus,publicKeyBroker, merchandiseCurrency, executionTransactionId, cashCurrencyType);
            transactionTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantInsertRecordCashMoneyStockReplenishmentBusinessTransactionException("An exception happened", e, "", "");
        } catch (Exception exception) {
            throw new CantInsertRecordCashMoneyStockReplenishmentBusinessTransactionException(CantInsertRecordCashMoneyStockReplenishmentBusinessTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public void updateTransactionStatusCashMoneyStockReplenishment(CashMoneyStockReplenishmentBusinessTransactionWrapper businessTransaction, BusinessTransactionStatus transactionStatus) throws CantUpdateStatusCashMoneyStockReplenishmentBusinessTransactionException {
        try {
            setToState(businessTransaction, transactionStatus);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException exception) {
            throw new CantUpdateStatusCashMoneyStockReplenishmentBusinessTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new CantUpdateStatusCashMoneyStockReplenishmentBusinessTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    private void loadRecordAsNew(
            DatabaseTableRecord databaseTableRecord,
            BusinessTransactionStatus transactionStatus,
            String publicKeyBroker,
            String merchandiseCurrency,
            String executionTransactionId,
            CashCurrencyType cashCurrencyType
    ) {
        UUID transactionId = UUID.randomUUID();

        databaseTableRecord.setUUIDValue(CashMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CASH_MONEY_STOCK_REPLENISHMENT_TRANSACTION_ID_COLUMN_NAME, transactionId);
        databaseTableRecord.setStringValue(CashMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CASH_MONEY_STOCK_REPLENISHMENT_STATUS_COLUMN_NAME, transactionStatus.getCode());
        databaseTableRecord.setStringValue(CashMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CASH_MONEY_STOCK_REPLENISHMENT_PUBLIC_KEY_BROKER_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setStringValue(CashMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CASH_MONEY_STOCK_REPLENISHMENT_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency);
        databaseTableRecord.setStringValue(CashMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CASH_MONEY_STOCK_REPLENISHMENT_EXECUTION_TRANSACTION_ID_COLUMN_NAME, executionTransactionId);
        databaseTableRecord.setStringValue(CashMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CASH_MONEY_STOCK_REPLENISHMENT_CASH_CURRENCY_TYPE_COLUMN_NAME,cashCurrencyType.getCode());

    }

    private void setToState(CashMoneyStockReplenishmentBusinessTransactionWrapper businessTransaction, BusinessTransactionStatus status) throws CantUpdateRecordException, CashMoneyStockReplenishmentBusinessTransactionInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable       transactionTable = this.database.getTable(CashMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CASH_MONEY_STOCK_REPLENISHMENT_TABLE_NAME);
        DatabaseTableRecord recordToUpdate   = getByPrimaryKey(businessTransaction.getTransactionId());
        recordToUpdate.setStringValue(CashMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CASH_MONEY_STOCK_REPLENISHMENT_STATUS_COLUMN_NAME, status.getCode());
        transactionTable.updateRecord(recordToUpdate);
    }

    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, CashMoneyStockReplenishmentBusinessTransactionInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(CashMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CASH_MONEY_STOCK_REPLENISHMENT_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(CashMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CASH_MONEY_STOCK_REPLENISHMENT_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new CashMoneyStockReplenishmentBusinessTransactionInconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        return records.get(0);
    }

}
