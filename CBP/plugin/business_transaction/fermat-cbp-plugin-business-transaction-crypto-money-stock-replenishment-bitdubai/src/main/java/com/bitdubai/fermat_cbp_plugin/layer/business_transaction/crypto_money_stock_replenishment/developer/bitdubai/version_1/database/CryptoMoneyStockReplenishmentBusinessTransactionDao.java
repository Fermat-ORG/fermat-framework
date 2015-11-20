package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.crypto_money_stock_replenishment.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CryptoCurrencyType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.crypto_money_stock_replenishment.interfaces.CryptoMoneyStockReplenishment;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.crypto_money_stock_replenishment.developer.bitdubai.version_1.structure.CryptoMoneyStockReplenishmentBusinessTransactionImpl;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.crypto_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoMoneyStockReplenishmentBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.crypto_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantInsertRecordCryptoMoneyStockReplenishmentBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.crypto_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantUpdateStatusCryptoMoneyStockReplenishmentBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.crypto_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CryptoMoneyStockReplenishmentBusinessTransactionInconsistentTableStateException;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 27.09.15.
 */
public class CryptoMoneyStockReplenishmentBusinessTransactionDao {
    private Database database;

    PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    public CryptoMoneyStockReplenishmentBusinessTransactionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCryptoMoneyStockReplenishmentBusinessTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CryptoMoneyStockReplenishmentBusinessTransactionDatabaseFactory databaseFactory = new CryptoMoneyStockReplenishmentBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCryptoMoneyStockReplenishmentBusinessTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCryptoMoneyStockReplenishmentBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCryptoMoneyStockReplenishmentBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCryptoMoneyStockReplenishmentBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /*CREATE NEW TRANSACTION*/
    public void createNewCryptoMoneyStockReplenishment(
            String publicKeyBroker,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            String executionTransactionId,
            CryptoCurrencyType cryptoCurrencyType
    ) throws CantInsertRecordCryptoMoneyStockReplenishmentBusinessTransactionException {
        try {
            DatabaseTable transactionTable = this.database.getTable(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = transactionTable.getEmptyRecord();
            BusinessTransactionStatus transactionStatus = BusinessTransactionStatus.PENDING_PAYMENT;
            loadRecordAsNew(recordToInsert, transactionStatus, publicKeyBroker, merchandiseCurrency, merchandiseAmount, executionTransactionId, cryptoCurrencyType);
            transactionTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantInsertRecordCryptoMoneyStockReplenishmentBusinessTransactionException("An exception happened", e, "", "");
        } catch (Exception exception) {
            throw new CantInsertRecordCryptoMoneyStockReplenishmentBusinessTransactionException(CantInsertRecordCryptoMoneyStockReplenishmentBusinessTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /*UPDATE STATUS TRANSACTION*/
    public void updateTransactionStatusCryptoMoneyStockReplenishment(CryptoMoneyStockReplenishmentBusinessTransactionImpl businessTransaction, BusinessTransactionStatus transactionStatus) throws CantUpdateStatusCryptoMoneyStockReplenishmentBusinessTransactionException {
        try {
            setToState(businessTransaction, transactionStatus);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException exception) {
            throw new CantUpdateStatusCryptoMoneyStockReplenishmentBusinessTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new CantUpdateStatusCryptoMoneyStockReplenishmentBusinessTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    /*GENERATE LIST TRANSACTION*/
    public List<CryptoMoneyStockReplenishment> getAllCryptoMoneyStockReplenishmentListFromCurrentDeviceUser() throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_TABLE_NAME);
        identityTable.loadToMemory();
        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();
        List<CryptoMoneyStockReplenishment> CryptoMoneyStockReplenishment = new ArrayList<>();
        for (DatabaseTableRecord record : records) {
            CryptoMoneyStockReplenishment.add(constructCryptoMoneyStockReplenishmentFromRecord(record));
        }
        return CryptoMoneyStockReplenishment;
    }


    private void loadRecordAsNew(
            DatabaseTableRecord databaseTableRecord,
            BusinessTransactionStatus transactionStatus,
            String publicKeyBroker,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            String executionTransactionId,
            CryptoCurrencyType cryptoCurrencyType
    ) {
        UUID transactionId = UUID.randomUUID();

        databaseTableRecord.setUUIDValue(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_TRANSACTION_ID_COLUMN_NAME, transactionId);
        databaseTableRecord.setStringValue(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_STATUS_COLUMN_NAME, transactionStatus.getCode());
        databaseTableRecord.setStringValue(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_PUBLIC_KEY_BROKER_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setStringValue(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_EXECUTION_TRANSACTION_ID_COLUMN_NAME, executionTransactionId);
        databaseTableRecord.setStringValue(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_CRYPTO_CURRENCY_TYPE_COLUMN_NAME, cryptoCurrencyType.getCode());

    }

    private void setToState(CryptoMoneyStockReplenishmentBusinessTransactionImpl businessTransaction, BusinessTransactionStatus status) throws CantUpdateRecordException, CryptoMoneyStockReplenishmentBusinessTransactionInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable       transactionTable = this.database.getTable(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_TABLE_NAME);
        DatabaseTableRecord recordToUpdate   = getByPrimaryKey(businessTransaction.getTransactionId());
        recordToUpdate.setStringValue(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_STATUS_COLUMN_NAME, status.getCode());
        transactionTable.updateRecord(recordToUpdate);
    }

    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, CryptoMoneyStockReplenishmentBusinessTransactionInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new CryptoMoneyStockReplenishmentBusinessTransactionInconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        return records.get(0);
    }

    private CryptoMoneyStockReplenishment constructCryptoMoneyStockReplenishmentFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                        transactionId           = record.getUUIDValue(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_TRANSACTION_ID_COLUMN_NAME);
        String                      brokerPublicKey         = record.getStringValue(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_PUBLIC_KEY_BROKER_COLUMN_NAME);
        CurrencyType                merchandiseCurrency     = CurrencyType.getByCode(record.getStringValue(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float                       amountCurrency          = record.getFloatValue(record.getStringValue(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_MERCHANDISE_AMOUNT_COLUMN_NAME));
        UUID                        executionTransactionId  = record.getUUIDValue(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_EXECUTION_TRANSACTION_ID_COLUMN_NAME);
        CryptoCurrencyType          cryptoCurrencyType      = CryptoCurrencyType.getByCode(record.getStringValue(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_CRYPTO_CURRENCY_TYPE_COLUMN_NAME));
        BusinessTransactionStatus   status                  = BusinessTransactionStatus.getByCode(record.getStringValue(CryptoMoneyStockReplenishmentBusinessTransactionDatabaseConstants.CRYPTO_MONEY_STOCK_REPLENISHMENT_STATUS_COLUMN_NAME));

        return new CryptoMoneyStockReplenishmentBusinessTransactionImpl(
                transactionId,
                brokerPublicKey,
                merchandiseCurrency,
                amountCurrency,
                executionTransactionId,
                cryptoCurrencyType,
                status
        );
    }
}
