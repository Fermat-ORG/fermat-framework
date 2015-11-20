package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_api.layer.world.enums.FiatCurrency;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantCreateHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransactionParameters;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.exceptions.CantInitializeHoldCashMoneyTransactionDatabaseException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.exceptions.HoldCashMoneyTransactionInconsistentTableStateException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.structure.CashHoldTransactionImpl;

import java.util.Date;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 11/18/2015.
 */
public class HoldCashMoneyTransactionDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;

    public HoldCashMoneyTransactionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    public void initializeDatabase() throws CantInitializeHoldCashMoneyTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeHoldCashMoneyTransactionDatabaseException(cantOpenDatabaseException.getMessage());
        } catch (DatabaseNotFoundException e) {
            HoldCashMoneyTransactionDatabaseFactory databaseFactory = new HoldCashMoneyTransactionDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                throw new CantInitializeHoldCashMoneyTransactionDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    CashHoldTransaction createCashHoldTransaction(CashHoldTransactionParameters holdParameters) throws CantCreateHoldTransactionException {

        DatabaseTable transactionTable = this.database.getTable(HoldCashMoneyTransactionDatabaseConstants.HOLD_TABLE_NAME);
        DatabaseTableRecord newRecord = transactionTable.getEmptyRecord();
        newHoldTransactionRecord(newRecord, holdParameters);        //Insertar valores en el record
        try {
            transactionTable.insertRecord(newRecord);
        }catch (CantInsertRecordException e) {
            throw new CantCreateHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant insert new record in table");
        }

        return constructHoldTransactionFromRecord(newRecord);
    }


    CashTransactionStatus getCashHoldTransactionStatus(UUID transactionId) throws CantGetHoldTransactionException {
        DatabaseTableRecord record;
        CashTransactionStatus transactionStatus;

        try{
            record = getRecordByPrimaryKey(transactionId);
            transactionStatus = CashTransactionStatus.getByCode(record.getStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_STATUS_COLUMN_NAME));

        } catch (CantLoadTableToMemoryException e){
            throw new CantGetHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant get record in table. Cannot load table into memory");
        } catch (HoldCashMoneyTransactionInconsistentTableStateException e) {
            throw new CantGetHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant get record in table. Inconsistent number of fetched records, should be between 0 and 1.");
        } catch (InvalidParameterException e) {
            throw new CantGetHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant get record in table. Invalid CashTransactionStatus value stored in table.");
        }

        return transactionStatus;
    }

    



    /* INTERNAL HELPER FUNCTIONS */
    private DatabaseTableRecord getRecordByPrimaryKey(UUID transactionId) throws CantLoadTableToMemoryException, HoldCashMoneyTransactionInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(HoldCashMoneyTransactionDatabaseConstants.HOLD_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(HoldCashMoneyTransactionDatabaseConstants.HOLD_TRANSACTION_ID_COLUMN_NAME, transactionId.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new HoldCashMoneyTransactionInconsistentTableStateException("Inconsistent ("+ records.size() +") number of fetched records, should be between 0 and 1.", null, "The id is: " + transactionId.toString(), "");

        return records.get(0);
    }




    private void newHoldTransactionRecord(DatabaseTableRecord newRecord, CashHoldTransactionParameters holdParameters) {

        newRecord.setUUIDValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_TRANSACTION_ID_COLUMN_NAME, holdParameters.getTransactionId());
        newRecord.setStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_WALLET_PUBLIC_KEY_COLUMN_NAME, holdParameters.getPublicKeyWallet());
        newRecord.setStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME, holdParameters.getPublicKeyActor());
        newRecord.setStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME, holdParameters.getPublicKeyPlugin());
        newRecord.setFloatValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_AMOUNT_COLUMN_NAME, holdParameters.getAmount());
        newRecord.setStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_CURRENCY_COLUMN_NAME, holdParameters.getCurrency().getCode());
        newRecord.setStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_MEMO_COLUMN_NAME, holdParameters.getMemo());
        newRecord.setStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_STATUS_COLUMN_NAME, CashTransactionStatus.ACKNOWLEDGED.getCode());
        newRecord.setLongValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME, (new Date().getTime() / 1000));
        newRecord.setLongValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME, 0);
    }

    private CashHoldTransaction constructHoldTransactionFromRecord(DatabaseTableRecord record) throws CantCreateHoldTransactionException{

        UUID transactionId = record.getUUIDValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_TRANSACTION_ID_COLUMN_NAME);
        String publicKeyWallet = record.getStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_WALLET_PUBLIC_KEY_COLUMN_NAME);
        String publicKeyActor = record.getStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME);
        String publicKeyPlugin = record.getStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME);
        float amount = record.getFloatValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_AMOUNT_COLUMN_NAME);
        String memo = record.getStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_MEMO_COLUMN_NAME);
        long timestampAcknowledged = record.getLongValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME);
        long timestampConfirmedRejected = record.getLongValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME);

        FiatCurrency currency;
        try {
            currency = FiatCurrency.getFiatCurrencyTypeByCode(record.getStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_CURRENCY_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            throw new CantCreateHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Invalid FiatCurrency value stored in table"
                    + HoldCashMoneyTransactionDatabaseConstants.HOLD_TABLE_NAME + " for id " + transactionId);
        }

        CashTransactionStatus transactionStatus;
        try {
            transactionStatus = CashTransactionStatus.getByCode(record.getStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_STATUS_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            throw new CantCreateHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Invalid CashTransactionStatus value stored in table"
                    + HoldCashMoneyTransactionDatabaseConstants.HOLD_TABLE_NAME + " for id " + transactionId);
        }

        return new CashHoldTransactionImpl(transactionId, publicKeyWallet, publicKeyActor, publicKeyPlugin,
                                           amount, currency, memo, transactionStatus, timestampAcknowledged,
                                           timestampConfirmedRejected);
    }

}
