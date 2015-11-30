package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.make_offline_bank_transfer.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.make_offline_bank_transfer.interfaces.MakeOfflineBankTransfer;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.make_offline_bank_transfer.developer.bitdubai.version_1.exceptions.MakeOfflineBankTransferBankMoneyTransactionInconsistentTableStateException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.make_offline_bank_transfer.developer.bitdubai.version_1.exceptions.CantInitializeMakeOfflineBankTransferBankMoneyTransactionDatabaseException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.make_offline_bank_transfer.developer.bitdubai.version_1.exceptions.CantInsertRecordMakeOfflineBankTransferBankMoneyTransactionException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.make_offline_bank_transfer.developer.bitdubai.version_1.exceptions.CantListMakeOfflineBankTransferException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.make_offline_bank_transfer.developer.bitdubai.version_1.exceptions.CantUpdateStatusMakeOfflineBankTransferBankMoneyTransactionException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.make_offline_bank_transfer.developer.bitdubai.version_1.structure.MakeOfflineBankTransferBankMoneyTransactionImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 14.10.15.
 */
public class MakeOfflineBankTransferBankMoneyTransactionDao {

    private Database database;

    PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    public MakeOfflineBankTransferBankMoneyTransactionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeMakeOfflineBankTransferBankMoneyTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                MakeOfflineBankTransferBankMoneyTransactionDatabaseFactory databaseFactory = new MakeOfflineBankTransferBankMoneyTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeMakeOfflineBankTransferBankMoneyTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeMakeOfflineBankTransferBankMoneyTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeMakeOfflineBankTransferBankMoneyTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeMakeOfflineBankTransferBankMoneyTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /*CREATE NEW TRANSACTION*/
    public void createNewMakeOfflineBankTransfer(
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
    ) throws CantInsertRecordMakeOfflineBankTransferBankMoneyTransactionException {
        try {
            DatabaseTable transactionTable = this.database.getTable(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_TABLE_NAME);
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
            throw new CantInsertRecordMakeOfflineBankTransferBankMoneyTransactionException("An exception happened", e, "", "");
        } catch (Exception exception) {
            throw new CantInsertRecordMakeOfflineBankTransferBankMoneyTransactionException(CantInsertRecordMakeOfflineBankTransferBankMoneyTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /*UPDATE STATUS TRANSACTION*/
    public void updateStatusMakeOfflineBankTransfer(MakeOfflineBankTransferBankMoneyTransactionImpl moneyTransaction, BankTransactionStatus transactionStatus) throws CantUpdateStatusMakeOfflineBankTransferBankMoneyTransactionException {
        try {
            setToState(moneyTransaction, transactionStatus);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException exception) {
            throw new CantUpdateStatusMakeOfflineBankTransferBankMoneyTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new CantUpdateStatusMakeOfflineBankTransferBankMoneyTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    /*GENERATE LIST TRANSACTION*/
    public List<MakeOfflineBankTransfer> getAllMakeOfflineBankTransferListFromCurrentDeviceUser() throws CantListMakeOfflineBankTransferException, InvalidParameterException {
        try{
            DatabaseTable identityTable = this.database.getTable(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_TABLE_NAME);
            identityTable.loadToMemory();
            List<DatabaseTableRecord> records = identityTable.getRecords();
            identityTable.clearAllFilters();
            List<MakeOfflineBankTransfer> bankMoneyStockReplenishment = new ArrayList<>();
            for (DatabaseTableRecord record : records) {
                bankMoneyStockReplenishment.add(constructMakeOfflineBankTransferFromRecord(record));
            }
            return bankMoneyStockReplenishment;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListMakeOfflineBankTransferException(e.getMessage(), e, "Make Offline Bank Transfer", "Cant load " + MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantListMakeOfflineBankTransferException(e.getMessage(), FermatException.wrapException(e), "Make Offline Bank Transfer", "Cant get Make Offline Bank Transfer list, unknown failure.");
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
        databaseTableRecord.setUUIDValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_TRANSACTION_ID_COLUMN_NAME, transactionId);
        databaseTableRecord.setStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_STATUS_COLUMN_NAME, transactionStatus.getCode());
        databaseTableRecord.setStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_PUBLIC_KEY_ACTOR_TO_COLUMN_NAME, publicKeyActorTo);
        databaseTableRecord.setStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_PUBLIC_KEY_ACTOR_FROM_COLUMN_NAME, publicKeyActorFrom);
        databaseTableRecord.setStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
        databaseTableRecord.setStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_TRANSACTION_TYPE_COLUMN_NAME, transactionType.getCode());
        databaseTableRecord.setFloatValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_AMOUNT_COLUMN_NAME, amount);
        databaseTableRecord.setStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_CURRENCY_TYPE_COLUMN_NAME, bankCurrencyType.getCode());
        databaseTableRecord.setStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_OPERATION_TYPE_COLUMN_NAME, bankOperationType.getCode());
        databaseTableRecord.setStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_DOCUMENT_REFERENCE_COLUMN_NAME, bankDocumentReference);
        databaseTableRecord.setStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_TO_NAME_COLUMN_NAME, bankToName);
        databaseTableRecord.setStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_TO_ACCOUNT_NUMBER_COLUMN_NAME,bankToAccountNumber);
        databaseTableRecord.setStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_TO_ACCOUNTTYPE_COLUMN_NAME,bankToAccountType.getCode());
        databaseTableRecord.setStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_FROM_NAME_COLUMN_NAME,bankFromName);
        databaseTableRecord.setStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_FROM_ACCOUNT_NUMBER_COLUMN_NAME,bankFromAccountNumber);
        databaseTableRecord.setStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_FROM_ACCOUNT_TYPE_COLUMN_NAME,bankFromAccountType.getCode());
        databaseTableRecord.setLongValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_TIMESTAMP_COLUMN_NAME, timeStamp);

    }

    private void setToState(MakeOfflineBankTransferBankMoneyTransactionImpl moneyTransaction, BankTransactionStatus status) throws CantUpdateRecordException, MakeOfflineBankTransferBankMoneyTransactionInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable       transactionTable = this.database.getTable(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_TABLE_NAME);
        DatabaseTableRecord recordToUpdate   = getByPrimaryKey(moneyTransaction.getBankTransactionId());
        recordToUpdate.setStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_STATUS_COLUMN_NAME, status.getCode());
        transactionTable.updateRecord(recordToUpdate);
    }

    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, MakeOfflineBankTransferBankMoneyTransactionInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new MakeOfflineBankTransferBankMoneyTransactionInconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        return records.get(0);
    }

    private MakeOfflineBankTransfer constructMakeOfflineBankTransferFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                    bankTransactionId       = record.getUUIDValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_TRANSACTION_ID_COLUMN_NAME);
        String                  publicKeyActorTo        = record.getStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_PUBLIC_KEY_ACTOR_TO_COLUMN_NAME);
        String                  publicKeyActorFrom      = record.getStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_PUBLIC_KEY_ACTOR_FROM_COLUMN_NAME);
        BalanceType             balanceType             = BalanceType.getByCode(record.getStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BALANCE_TYPE_COLUMN_NAME));
        TransactionType         transactionType         = TransactionType.getByCode(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_TRANSACTION_TYPE_COLUMN_NAME);
        float                   amount                  = record.getFloatValue(record.getStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_AMOUNT_COLUMN_NAME));
        BankCurrencyType        bankCurrencyType        = BankCurrencyType.getByCode(record.getStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_CURRENCY_TYPE_COLUMN_NAME));
        BankOperationType       bankOperationType       = BankOperationType.getByCode(record.getStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_OPERATION_TYPE_COLUMN_NAME));
        String                  bankDocumentReference   = record.getStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_DOCUMENT_REFERENCE_COLUMN_NAME);
        String                  bankToName              = record.getStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_TO_NAME_COLUMN_NAME);
        String                  bankToAccountNumber     = record.getStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_TO_ACCOUNT_NUMBER_COLUMN_NAME);
        BankAccountType         bankToAccountType       = BankAccountType.getByCode(record.getStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_TO_ACCOUNTTYPE_COLUMN_NAME));
        String                  bankFromName            = record.getStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_FROM_NAME_COLUMN_NAME);
        String                  bankFromAccountNumber   = record.getStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_FROM_ACCOUNT_NUMBER_COLUMN_NAME);
        BankAccountType         bankFromAccountType     = BankAccountType.getByCode(record.getStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_BANK_FROM_ACCOUNT_TYPE_COLUMN_NAME));
        BankTransactionStatus   transactionStatus       = BankTransactionStatus.getByCode(record.getStringValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_STATUS_COLUMN_NAME));
        long                    timeStamp               = record.getLongValue(MakeOfflineBankTransferBankMoneyTransactionDatabaseConstants.MAKE_OFFLINE_BANK_TRANSFER_TIMESTAMP_COLUMN_NAME);
        return new MakeOfflineBankTransferBankMoneyTransactionImpl(
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