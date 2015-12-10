package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.give_cash_on_hand.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.give_cash_on_hand.interfaces.GiveCashOnHand;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.give_cash_on_hand.developer.bitdubai.version_1.exceptions.GiveCashOnHandCashMoneyTransactionInconsistentTableStateException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.give_cash_on_hand.developer.bitdubai.version_1.exceptions.CantInitializeGiveCashOnHandCashMoneyTransactionDatabaseException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.give_cash_on_hand.developer.bitdubai.version_1.exceptions.CantInsertRecordGiveCashOnHandCashMoneyTransactionException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.give_cash_on_hand.developer.bitdubai.version_1.exceptions.CantListGiveCashOnHandCashMoneyTransactionException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.give_cash_on_hand.developer.bitdubai.version_1.exceptions.CantUpdateStatusGiveCashOnHandCashMoneyTransactionException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.give_cash_on_hand.developer.bitdubai.version_1.structure.GiveCashOnHandCashMoneyTransactionImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by yordin on 29/09/15.
 */
public class GiveCashOnHandCashMoneyTransactionDao {
    private Database database;

    PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    public GiveCashOnHandCashMoneyTransactionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeGiveCashOnHandCashMoneyTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                GiveCashOnHandCashMoneyTransactionDatabaseFactory databaseFactory = new GiveCashOnHandCashMoneyTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeGiveCashOnHandCashMoneyTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeGiveCashOnHandCashMoneyTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeGiveCashOnHandCashMoneyTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeGiveCashOnHandCashMoneyTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /*CREATE NEW TRANSACTION*/
    public void createNewGiveCashOnHand(
            String publicKeyActorTo
            ,String publicKeyActorFrom
            ,BalanceType balanceType
            ,TransactionType transactionType
            ,float amount
            ,CashCurrencyType cashCurrencyType
            ,String cashReference
            ,long timeStamp
    ) throws CantInsertRecordGiveCashOnHandCashMoneyTransactionException {
        try {
            DatabaseTable transactionTable = this.database.getTable(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = transactionTable.getEmptyRecord();
            CashTransactionStatus transactionStatus = CashTransactionStatus.PENDING;
            loadRecordAsNew(
                    recordToInsert
                    ,transactionStatus
                    ,publicKeyActorTo
                    ,publicKeyActorFrom
                    ,balanceType
                    ,transactionType
                    ,amount
                    ,cashCurrencyType
                    ,cashReference
                    ,timeStamp);
            transactionTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantInsertRecordGiveCashOnHandCashMoneyTransactionException("An exception happened", e, "", "");
        } catch (Exception exception) {
            throw new CantInsertRecordGiveCashOnHandCashMoneyTransactionException(CantInsertRecordGiveCashOnHandCashMoneyTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /*UPDATE STATUS TRANSACTION*/
    public void updateStatusGiveCashOnHand(GiveCashOnHandCashMoneyTransactionImpl moneyTransaction, CashTransactionStatus transactionStatus) throws CantUpdateStatusGiveCashOnHandCashMoneyTransactionException {
        try {
            setToState(moneyTransaction, transactionStatus);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException exception) {
            throw new CantUpdateStatusGiveCashOnHandCashMoneyTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new CantUpdateStatusGiveCashOnHandCashMoneyTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    /*GENERATE LIST TRANSACTION*/
    public List<GiveCashOnHand> getAllGiveCashOnHandListFromCurrentDeviceUser() throws CantListGiveCashOnHandCashMoneyTransactionException, InvalidParameterException {
        try{
            DatabaseTable identityTable = this.database.getTable(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_TABLE_NAME);
            identityTable.loadToMemory();
            List<DatabaseTableRecord> records = identityTable.getRecords();
            identityTable.clearAllFilters();
            List<GiveCashOnHand> bankMoneyStockReplenishment = new ArrayList<>();
            for (DatabaseTableRecord record : records) {
                bankMoneyStockReplenishment.add(constructGiveCashOnHandFromRecord(record));
            }
            return bankMoneyStockReplenishment;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListGiveCashOnHandCashMoneyTransactionException(e.getMessage(), e, "Give Cash On Hand", "Cant load " + GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantListGiveCashOnHandCashMoneyTransactionException(e.getMessage(), FermatException.wrapException(e), "Give Cash On Hand", "Cant get Give Cash On Hand list, unknown failure.");
        }
    }

    private void loadRecordAsNew(
            DatabaseTableRecord databaseTableRecord
            ,CashTransactionStatus transactionStatus
            ,String publicKeyActorTo
            ,String publicKeyActorFrom
            ,BalanceType balanceType
            ,TransactionType transactionType
            ,float amount
            ,CashCurrencyType cashCurrencyType
            ,String cashReference
            ,long timeStamp
    ) {
        UUID transactionId = UUID.randomUUID();
        databaseTableRecord.setUUIDValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_CASH_TRANSACTION_ID_COLUMN_NAME, transactionId);
        databaseTableRecord.setStringValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_STATUS_COLUMN_NAME, transactionStatus.getCode());
        databaseTableRecord.setStringValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_PUBLIC_KEY_ACTOR_TO_COLUMN_NAME, publicKeyActorTo);
        databaseTableRecord.setStringValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_PUBLIC_KEY_ACTOR_FROM_COLUMN_NAME, publicKeyActorFrom);
        databaseTableRecord.setStringValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
        databaseTableRecord.setStringValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_TRANSACTION_TYPE_COLUMN_NAME, transactionType.getCode());
        databaseTableRecord.setFloatValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_AMOUNT_COLUMN_NAME, amount);
        databaseTableRecord.setStringValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_CASH_CURRENCY_TYPE_COLUMN_NAME, cashCurrencyType.getCode());
        databaseTableRecord.setStringValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_CASH_REFERENCE_COLUMN_NAME, cashReference);
        databaseTableRecord.setLongValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_TIMESTAMP_COLUMN_NAME, timeStamp);

    }

    private void setToState(GiveCashOnHandCashMoneyTransactionImpl moneyTransaction, CashTransactionStatus status) throws CantUpdateRecordException, GiveCashOnHandCashMoneyTransactionInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable       transactionTable = this.database.getTable(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_TABLE_NAME);
        DatabaseTableRecord recordToUpdate   = getByPrimaryKey(moneyTransaction.getCashTransactionId());
        recordToUpdate.setStringValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_STATUS_COLUMN_NAME, status.getCode());
        transactionTable.updateRecord(recordToUpdate);
    }

    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, GiveCashOnHandCashMoneyTransactionInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_CASH_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new GiveCashOnHandCashMoneyTransactionInconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        return records.get(0);
    }

    private GiveCashOnHand constructGiveCashOnHandFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                    cashTransactionId   = record.getUUIDValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_CASH_TRANSACTION_ID_COLUMN_NAME);
        String                  publicKeyActorTo    = record.getStringValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_PUBLIC_KEY_ACTOR_TO_COLUMN_NAME);
        String                  publicKeyActorFrom  = record.getStringValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_PUBLIC_KEY_ACTOR_FROM_COLUMN_NAME);
        BalanceType             balanceType         = BalanceType.getByCode(record.getStringValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_BALANCE_TYPE_COLUMN_NAME));
        TransactionType         transactionType     = TransactionType.getByCode(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_TRANSACTION_TYPE_COLUMN_NAME);
        float                   amount              = record.getFloatValue(record.getStringValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_AMOUNT_COLUMN_NAME));
        CashCurrencyType        cashCurrencyType    = null; //CashCurrencyType.getByCode(record.getStringValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_CASH_CURRENCY_TYPE_COLUMN_NAME));
        long                    timeStamp           = record.getLongValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_TIMESTAMP_COLUMN_NAME);
        String                  cashReference       = record.getStringValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_CASH_REFERENCE_COLUMN_NAME);
        CashTransactionStatus   transactionStatus = CashTransactionStatus.ACKNOWLEDGED;
        // CashTransactionStatus.getByCode(record.getStringValue(GiveCashOnHandCashMoneyTransactionDatabaseConstants.GIVE_CASH_ON_HAND_STATUS_COLUMN_NAME));
        return new GiveCashOnHandCashMoneyTransactionImpl(
                cashTransactionId,
                publicKeyActorTo,
                publicKeyActorFrom,
                balanceType,
                transactionType,
                amount,
                cashCurrencyType,
                cashReference,
                timeStamp,
                transactionStatus
        );
    }
}
