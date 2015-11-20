package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
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
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.bank_money_stock_replenishment.interfaces.BankMoneyStockReplenishment;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.structure.BankMoneyStockReplenishmentBusinessTransactionImpl;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantInitializeBankMoneyStockReplenishmentBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantInsertRecordBankMoneyStockReplenishmentBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.exceptions.BankMoneyStockReplenishmentBusinessTransactionInconsistentTableStateException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantUpdateStatusBankMoneyStockReplenishmentBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantListBankMoneyStockReplenishmentBusinessTransactionException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 27.09.15.
 */
public class BankMoneyStockReplenishmentBusinessTransactionDatabaseDao {

    private Database database;

    PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    public BankMoneyStockReplenishmentBusinessTransactionDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeBankMoneyStockReplenishmentBusinessTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                BankMoneyStockReplenishmentBusinessTransactionDatabaseFactory databaseFactory = new BankMoneyStockReplenishmentBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeBankMoneyStockReplenishmentBusinessTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeBankMoneyStockReplenishmentBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeBankMoneyStockReplenishmentBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeBankMoneyStockReplenishmentBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /*CREATE NEW TRANSACTION*/
    public void createNewBankMoneyStockReplenishment(final BankMoneyStockReplenishment bankMoneyStockReplenishment) throws CantInsertRecordBankMoneyStockReplenishmentBusinessTransactionException {
        try {
            DatabaseTable transactionTable = this.database.getTable(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_TABLE_NAME);
            DatabaseTableRecord recordToInsert = transactionTable.getEmptyRecord();
            loadRecordAsNew(recordToInsert, bankMoneyStockReplenishment);
            transactionTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantInsertRecordBankMoneyStockReplenishmentBusinessTransactionException("An exception happened", e, "", "");
        } catch (Exception exception) {
            throw new CantInsertRecordBankMoneyStockReplenishmentBusinessTransactionException(CantInsertRecordBankMoneyStockReplenishmentBusinessTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /*UPDATE STATUS TRANSACTION*/
    public void updateStatusBankMoneyStockReplenishmentTransaction(UUID transactionId, BusinessTransactionStatus transactionStatus) throws CantUpdateStatusBankMoneyStockReplenishmentBusinessTransactionException {
        try {
            setToState(transactionId, transactionStatus);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException exception) {
            throw new CantUpdateStatusBankMoneyStockReplenishmentBusinessTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new CantUpdateStatusBankMoneyStockReplenishmentBusinessTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    /*GENERATE LIST TRANSACTION*/
    public List<BankMoneyStockReplenishment> getAllBankMoneyStockReplenishmentListFromCurrentDeviceUser() throws CantListBankMoneyStockReplenishmentBusinessTransactionException {
        List<BankMoneyStockReplenishment> bankMoneyStockReplenishment = new ArrayList<BankMoneyStockReplenishment>();
        DatabaseTable identityTable;
        try {
            identityTable = this.database.getTable(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_TABLE_NAME);
            identityTable.loadToMemory();
            identityTable.clearAllFilters();
            bankMoneyStockReplenishment = new ArrayList<>();
            for (DatabaseTableRecord record : identityTable.getRecords()) {
                bankMoneyStockReplenishment.add(constructBankMoneyStockReplenishmentFromRecord(record));
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListBankMoneyStockReplenishmentBusinessTransactionException(e.getMessage(), e, "Crypto Broker Wallet", "Cant load " + BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantListBankMoneyStockReplenishmentBusinessTransactionException(e.getMessage(), FermatException.wrapException(e), "Crypto Broker Wallet", "Cant get Crypto Broker Wallet list, unknown failure.");
        }
        return bankMoneyStockReplenishment;
    }


    private void loadRecordAsNew(DatabaseTableRecord databaseTableRecord,BankMoneyStockReplenishment bankMoneyStockReplenishment ) {
        databaseTableRecord.setUUIDValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_TRANSACTION_ID_COLUMN_NAME, bankMoneyStockReplenishment.getTransactionId());
        databaseTableRecord.setStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_STATUS_COLUMN_NAME, bankMoneyStockReplenishment.getStatus().getCode());
        databaseTableRecord.setStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_PUBLIC_KEY_BROKER_COLUMN_NAME, bankMoneyStockReplenishment.getPublicKeyBroker());
        databaseTableRecord.setStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_MERCHANDISE_CURRENCY_COLUMN_NAME, bankMoneyStockReplenishment.getMerchandiseCurrency().getCode());
        databaseTableRecord.setFloatValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_MERCHANDISE_CURRENCY_COLUMN_NAME, bankMoneyStockReplenishment.getMerchandiseAmount());
        databaseTableRecord.setUUIDValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_EXECUTION_TRANSACTION_ID_COLUMN_NAME, bankMoneyStockReplenishment.getExecutionTransactionId());
        databaseTableRecord.setStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_BANK_CURRENCY_TYPE_COLUMN_NAME,bankMoneyStockReplenishment.getBankCurrencyType().getCode());
        databaseTableRecord.setStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_BANK_OPERATION_TYPE_COLUMN_NAME, bankMoneyStockReplenishment.getBankOperationType().getCode());
    }

    private void setToState(UUID transactionId, BusinessTransactionStatus status) throws CantUpdateRecordException, BankMoneyStockReplenishmentBusinessTransactionInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable       transactionTable = this.database.getTable(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_TABLE_NAME);
        DatabaseTableRecord recordToUpdate   = getByPrimaryKey(transactionId);
        recordToUpdate.setStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_STATUS_COLUMN_NAME, status.getCode());
        transactionTable.updateRecord(recordToUpdate);
    }

    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, BankMoneyStockReplenishmentBusinessTransactionInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new BankMoneyStockReplenishmentBusinessTransactionInconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        return records.get(0);
    }

    private BankMoneyStockReplenishment constructBankMoneyStockReplenishmentFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                        transactionId           = record.getUUIDValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_TRANSACTION_ID_COLUMN_NAME);
        String                      publickeyBroker         = record.getStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_PUBLIC_KEY_BROKER_COLUMN_NAME);
        CurrencyType                merchandiseCurrency     = CurrencyType.getByCode(record.getStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float                       merchandiseAmount       = record.getFloatValue(record.getStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_MERCHANDISE_AMOUNT_COLUMN_NAME));
        UUID                        executionTransactionId  = record.getUUIDValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_EXECUTION_TRANSACTION_ID_COLUMN_NAME);
        BankCurrencyType            bankCurrencyType        = BankCurrencyType.getByCode(record.getStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_BANK_CURRENCY_TYPE_COLUMN_NAME));
        BankOperationType           bankOperationType       = BankOperationType.getByCode(record.getStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_BANK_OPERATION_TYPE_COLUMN_NAME));
        BusinessTransactionStatus   status                  = BusinessTransactionStatus.getByCode(record.getStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_STATUS_COLUMN_NAME));
        KeyPair keyPairBroker                               = AsymmetricCryptography.createKeyPair(publickeyBroker);

        return new BankMoneyStockReplenishmentBusinessTransactionImpl(
            transactionId,
            keyPairBroker,
            merchandiseCurrency,
            merchandiseAmount,
            executionTransactionId,
            bankCurrencyType,
            bankOperationType,
            status
        );
    }

}
