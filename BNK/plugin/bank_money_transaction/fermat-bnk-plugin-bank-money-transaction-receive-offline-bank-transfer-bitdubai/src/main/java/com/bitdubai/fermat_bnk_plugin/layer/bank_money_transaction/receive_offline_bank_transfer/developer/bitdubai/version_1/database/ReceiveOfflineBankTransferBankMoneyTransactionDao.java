package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.receive_offline_bank_transfer.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.receive_offline_bank_transfer.interfaces.ReceiveOfflineBankTransfer;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.receive_offline_bank_transfer.developer.bitdubai.version_1.exceptions.ReceiveOfflineBankTransferBankMoneyTransactionInconsistentTableStateException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.receive_offline_bank_transfer.developer.bitdubai.version_1.exceptions.CantInitializeReceiveOfflineBankTransferBankMoneyTransactionDatabaseException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.receive_offline_bank_transfer.developer.bitdubai.version_1.exceptions.CantInsertRecordReceiveOfflineBankTransferBankMoneyTransactionException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.receive_offline_bank_transfer.developer.bitdubai.version_1.exceptions.CantListReceiveOfflineBankTransferException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.receive_offline_bank_transfer.developer.bitdubai.version_1.exceptions.CantUpdateStatusReceiveOfflineBankTransferBankMoneyTransactionException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.receive_offline_bank_transfer.developer.bitdubai.version_1.structure.ReceiveOfflineBankTransferBankMoneyTransactionImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by yordin on 29/09/15.
 */
public class ReceiveOfflineBankTransferBankMoneyTransactionDao {
    private Database database;

    PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    public ReceiveOfflineBankTransferBankMoneyTransactionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeReceiveOfflineBankTransferBankMoneyTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                ReceiveOfflineBankTransferBankMoneyTransactionDatabaseFactory databaseFactory = new ReceiveOfflineBankTransferBankMoneyTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeReceiveOfflineBankTransferBankMoneyTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeReceiveOfflineBankTransferBankMoneyTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeReceiveOfflineBankTransferBankMoneyTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeReceiveOfflineBankTransferBankMoneyTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /*CREATE NEW TRANSACTION*/
    public void createNewReceiveOfflineBankTransfer(
             String publicKeyActorTo
            ,String publicKeyActorFrom
            ,BalanceType balanceType
            ,TransactionType transactionType
            ,float amount
            ,BankCurrencyType bankCurrencyType
            ,BankOperationType bankOperationType
            ,String bankDocumentReference
            ,String bankToName
            ,String bankToAccountNumber
            ,BankAccountType bankToAccountType
            ,String bankFromName
            ,String bankFromAccountNumber
            ,BankAccountType bankFromAccountType
            ,long timeStamp
    ) throws CantInsertRecordReceiveOfflineBankTransferBankMoneyTransactionException {
        try {
            DatabaseTable transactionTable = this.database.getTable(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = transactionTable.getEmptyRecord();
            BankTransactionStatus transactionStatus = BankTransactionStatus.PENDING;
            loadRecordAsNew(
                    recordToInsert
                    ,transactionStatus
                    ,publicKeyActorTo
                    ,publicKeyActorFrom
                    ,balanceType
                    ,transactionType
                    ,amount
                    ,bankCurrencyType
                    ,bankOperationType
                    ,bankDocumentReference
                    ,bankToName
                    ,bankToAccountNumber
                    ,bankToAccountType
                    ,bankFromName
                    ,bankFromAccountNumber
                    ,bankFromAccountType
                    ,timeStamp);
            transactionTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantInsertRecordReceiveOfflineBankTransferBankMoneyTransactionException("An exception happened", e, "", "");
        } catch (Exception exception) {
            throw new CantInsertRecordReceiveOfflineBankTransferBankMoneyTransactionException(CantInsertRecordReceiveOfflineBankTransferBankMoneyTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /*UPDATE STATUS TRANSACTION*/
    public void updateStatusReceiveOfflineBankTransfer(ReceiveOfflineBankTransferBankMoneyTransactionImpl moneyTransaction, BankTransactionStatus transactionStatus) throws CantUpdateStatusReceiveOfflineBankTransferBankMoneyTransactionException {
        try {
            setToState(moneyTransaction, transactionStatus);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException exception) {
            throw new CantUpdateStatusReceiveOfflineBankTransferBankMoneyTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new CantUpdateStatusReceiveOfflineBankTransferBankMoneyTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    /*GENERATE LIST TRANSACTION*/
    public List<ReceiveOfflineBankTransfer> getAllReceiveOfflineBankTransferListFromCurrentDeviceUser() throws CantListReceiveOfflineBankTransferException, InvalidParameterException {
        try{
            DatabaseTable identityTable = this.database.getTable(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_TABLE_NAME);
            identityTable.loadToMemory();
            List<DatabaseTableRecord> records = identityTable.getRecords();
            identityTable.clearAllFilters();
            List<ReceiveOfflineBankTransfer> bankMoneyStockReplenishment = new ArrayList<>();
            for (DatabaseTableRecord record : records) {
                bankMoneyStockReplenishment.add(constructReceiveOfflineBankTransferFromRecord(record));
            }
            return bankMoneyStockReplenishment;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListReceiveOfflineBankTransferException(e.getMessage(), e, "Receive Off line Bank Transfer", "Cant load " + ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantListReceiveOfflineBankTransferException(e.getMessage(), FermatException.wrapException(e), "Receive Off line Bank Transfer", "Cant get Receive Off line Bank Transfer list, unknown failure.");
        }
    }

    private void loadRecordAsNew(
            DatabaseTableRecord databaseTableRecord
            ,BankTransactionStatus transactionStatus
            ,String publicKeyActorTo
            ,String publicKeyActorFrom
            ,BalanceType balanceType
            ,TransactionType transactionType
            ,float amount
            ,BankCurrencyType bankCurrencyType
            ,BankOperationType bankOperationType
            ,String bankDocumentReference
            ,String bankToName
            ,String bankToAccountNumber
            ,BankAccountType bankToAccountType
            ,String bankFromName
            ,String bankFromAccountNumber
            ,BankAccountType bankFromAccountType
            ,long timeStamp
    ) {
        UUID transactionId = UUID.randomUUID();
        databaseTableRecord.setUUIDValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_TRANSACTION_ID_COLUMN_NAME, transactionId);
        databaseTableRecord.setStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_STATUS_COLUMN_NAME, transactionStatus.getCode());
        databaseTableRecord.setStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_PUBLIC_KEY_ACTOR_TO_COLUMN_NAME, publicKeyActorTo);
        databaseTableRecord.setStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_PUBLIC_KEY_ACTOR_FROM_COLUMN_NAME, publicKeyActorFrom);
        databaseTableRecord.setStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
        databaseTableRecord.setStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_TRANSACTION_TYPE_COLUMN_NAME, transactionType.getCode());
        databaseTableRecord.setFloatValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_AMOUNT_COLUMN_NAME, amount);
        databaseTableRecord.setStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_CURRENCY_TYPE_COLUMN_NAME, bankCurrencyType.getCode());
        databaseTableRecord.setStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_OPERATION_TYPE_COLUMN_NAME, bankOperationType.getCode());
        databaseTableRecord.setStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_DOCUMENT_REFERENCE_COLUMN_NAME, bankDocumentReference);
        databaseTableRecord.setStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_TO_NAME_COLUMN_NAME, bankToName);
        databaseTableRecord.setStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_TO_ACCOUNT_NUMBER_COLUMN_NAME,bankToAccountNumber);
        databaseTableRecord.setStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_TO_ACCOUNTTYPE_COLUMN_NAME,bankToAccountType.getCode());
        databaseTableRecord.setStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_FROM_NAME_COLUMN_NAME,bankFromName);
        databaseTableRecord.setStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_FROM_ACCOUNT_NUMBER_COLUMN_NAME,bankFromAccountNumber);
        databaseTableRecord.setStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_FROM_ACCOUNT_TYPE_COLUMN_NAME,bankFromAccountType.getCode());
        databaseTableRecord.setLongValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_TIMESTAMP_COLUMN_NAME, timeStamp);

    }

    private void setToState(ReceiveOfflineBankTransferBankMoneyTransactionImpl moneyTransaction, BankTransactionStatus status) throws CantUpdateRecordException, ReceiveOfflineBankTransferBankMoneyTransactionInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable       transactionTable = this.database.getTable(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_TABLE_NAME);
        DatabaseTableRecord recordToUpdate   = getByPrimaryKey(moneyTransaction.getBankTransactionId());
        recordToUpdate.setStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_STATUS_COLUMN_NAME, status.getCode());
        transactionTable.updateRecord(recordToUpdate);
    }

    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, ReceiveOfflineBankTransferBankMoneyTransactionInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new ReceiveOfflineBankTransferBankMoneyTransactionInconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        return records.get(0);
    }

    private ReceiveOfflineBankTransfer constructReceiveOfflineBankTransferFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                    bankTransactionId       = record.getUUIDValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_TRANSACTION_ID_COLUMN_NAME);
        String                  publicKeyActorTo        = record.getStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_PUBLIC_KEY_ACTOR_TO_COLUMN_NAME);
        String                  publicKeyActorFrom      = record.getStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_PUBLIC_KEY_ACTOR_FROM_COLUMN_NAME);
        BalanceType             balanceType             = BalanceType.getByCode(record.getStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BALANCE_TYPE_COLUMN_NAME));
        TransactionType         transactionType         = TransactionType.getByCode(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_TRANSACTION_TYPE_COLUMN_NAME);
        float                   amount                  = record.getFloatValue(record.getStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_AMOUNT_COLUMN_NAME));
        BankCurrencyType        bankCurrencyType        = BankCurrencyType.getByCode(record.getStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_CURRENCY_TYPE_COLUMN_NAME));
        BankOperationType       bankOperationType       = BankOperationType.getByCode(record.getStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_OPERATION_TYPE_COLUMN_NAME));
        String                  bankDocumentReference   = record.getStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_DOCUMENT_REFERENCE_COLUMN_NAME);
        String                  bankToName              = record.getStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_TO_NAME_COLUMN_NAME);
        String                  bankToAccountNumber     = record.getStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_TO_ACCOUNT_NUMBER_COLUMN_NAME);
        BankAccountType         bankToAccountType       = BankAccountType.getByCode(record.getStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_TO_ACCOUNTTYPE_COLUMN_NAME));
        String                  bankFromName            = record.getStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_FROM_NAME_COLUMN_NAME);
        String                  bankFromAccountNumber   = record.getStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_FROM_ACCOUNT_NUMBER_COLUMN_NAME);
        BankAccountType         bankFromAccountType     = BankAccountType.getByCode(record.getStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_FROM_ACCOUNT_TYPE_COLUMN_NAME));
        BankTransactionStatus   transactionStatus       = BankTransactionStatus.getByCode(record.getStringValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_STATUS_COLUMN_NAME));
        long                    timeStamp               = record.getLongValue(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_TIMESTAMP_COLUMN_NAME);
        return new ReceiveOfflineBankTransferBankMoneyTransactionImpl(
                bankTransactionId,
                publicKeyActorTo,
                publicKeyActorFrom,
                balanceType,
                transactionType,
                amount,
                bankCurrencyType,
                bankOperationType,
                bankDocumentReference,
                bankToName,
                bankToAccountNumber,
                bankToAccountType,
                bankFromName,
                bankFromAccountNumber,
                bankFromAccountType,
                transactionStatus,
                timeStamp
        );
    }
}
