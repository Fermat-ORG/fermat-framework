package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.*;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;

import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.exceptions.CantCreateHoldTransactionException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.exceptions.CantInitializeHoldBankMoneyTransactionDatabaseException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.exceptions.CantUpdateHoldTransactionException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.exceptions.HoldBankMoneyTransactionInconsistentTableStateException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.structure.BankTransactionImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by memo on 18/11/15.
 */
public class HoldBankMoneyTransactionDao {
    private final ErrorManager errorManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public HoldBankMoneyTransactionDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }

    public void initialize() throws CantInitializeHoldBankMoneyTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            HoldBankMoneyTransactionDatabaseFactory databaseFactory = new HoldBankMoneyTransactionDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeHoldBankMoneyTransactionDatabaseException("Database could not be opened", cantCreateDatabaseException, "Database Name: " + pluginId.toString(), "");
            }
        }catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeHoldBankMoneyTransactionDatabaseException("Database could not be opened", cantOpenDatabaseException, "Database Name: " + pluginId.toString(), "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeHoldBankMoneyTransactionDatabaseException("Database could not be opened", FermatException.wrapException(e), "Database Name: " + pluginId.toString(), "");
        }
    }




    public BankTransaction createHoldTransaction(BankTransactionParameters holdParameters) throws CantCreateHoldTransactionException {

        DatabaseTable transactionTable = this.database.getTable(HoldBankMoneyTransactionDatabaseConstants.HOLD_TABLE_NAME);
        DatabaseTableRecord newRecord = transactionTable.getEmptyRecord();
        newHoldTransactionRecord(newRecord, holdParameters);        //Insertar valores en el record
        try {
            transactionTable.insertRecord(newRecord);
        }catch (CantInsertRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant insert new record in table");
        }

        return constructHoldTransactionFromRecord(newRecord);
    }

    public BankTransaction getHoldTransaction(UUID transactionId) throws CantGetHoldTransactionException {
        DatabaseTableRecord record;
        BankTransaction HoldTransaction;
        try {
            record = getRecordByPrimaryKey(transactionId);
            HoldTransaction = constructHoldTransactionFromRecord(record);
        } catch (CantLoadTableToMemoryException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant get record in table. Cannot load table into memory");
        } catch (HoldBankMoneyTransactionInconsistentTableStateException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant get record in table. Inconsistent number of fetched records, should be between 0 and 1.");
        } catch (CantCreateHoldTransactionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant get record in table. Failed while constructing transaction from record.");
        } catch (Exception e) {
            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant get record in table. Failed while constructing transaction from record.");
        }
        return HoldTransaction;
    }

    public List<BankTransaction> getHoldTransactionList(DatabaseTableFilter filter) throws CantGetHoldTransactionException
    {
        List<BankTransaction> transactions = new ArrayList<>();
        try {
            for (DatabaseTableRecord record : getRecordsByFilter(filter)) {
                BankTransaction transaction = constructHoldTransactionFromRecord(record);
                transactions.add(transaction);
            }
        } catch (CantCreateHoldTransactionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetHoldTransactionException(CantGetHoldTransactionException.DEFAULT_MESSAGE, e, "Failed to get bank Hold Transaction list. Filter: " + filter.toString(), "");
        }catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetHoldTransactionException(CantGetHoldTransactionException.DEFAULT_MESSAGE, e, "Failed to get bank Hold Transaction list. Filter: " + filter.toString(), "");
        }
        return transactions;
    }

    public List<BankTransaction> getAcknowledgedTransactionList() throws CantGetHoldTransactionException
    {
        DatabaseTableFilter filter = getEmptyHoldTableFilter();
        filter.setColumn(HoldBankMoneyTransactionDatabaseConstants.HOLD_STATUS_COLUMN_NAME);
        filter.setValue(BankTransactionStatus.ACKNOWLEDGE.getCode());
        filter.setType(DatabaseFilterType.EQUAL);

        return getHoldTransactionList(filter);
    }

    public void updateHoldTransactionStatus(UUID transactionId, BankTransactionStatus status) throws CantUpdateHoldTransactionException
    {
        DatabaseTableRecord record;
        try {
            record = getRecordByPrimaryKey(transactionId);
            record.setStringValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_STATUS_COLUMN_NAME, status.getCode());
            if(status == BankTransactionStatus.CONFIRMED || status == BankTransactionStatus.REJECTED)
                record.setLongValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME, new Date().getTime());

            DatabaseTable table = database.getTable(HoldBankMoneyTransactionDatabaseConstants.HOLD_TABLE_NAME);
            table.updateRecord(record);

        } catch (CantUpdateRecordException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantUpdateHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant update bank hold transaction status. Cant update the record");
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantUpdateHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant update bank hold transaction status. Cant load table into memory.");
        } catch (HoldBankMoneyTransactionInconsistentTableStateException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantUpdateHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant update bank hold transaction status. Inconsistent table state.");
        }

    }





    /* INTERNAL HELPER FUNCTIONS */
    private DatabaseTableFilter getEmptyHoldTableFilter() {
        return this.database.getTable(HoldBankMoneyTransactionDatabaseConstants.HOLD_TABLE_NAME).getEmptyTableFilter();
    }


    private DatabaseTableRecord getRecordByPrimaryKey(UUID transactionId) throws CantLoadTableToMemoryException, HoldBankMoneyTransactionInconsistentTableStateException {
        List<DatabaseTableRecord> records;
        DatabaseTable table = database.getTable(HoldBankMoneyTransactionDatabaseConstants.HOLD_TABLE_NAME);

        table.addStringFilter(HoldBankMoneyTransactionDatabaseConstants.HOLD_ID_COLUMN_NAME, transactionId.toString(), DatabaseFilterType.EQUAL);
        table.loadToMemory();
        records = table.getRecords();
        //TODO: fix this
        /*if (records.size() != 1)
            throw new HoldBankMoneyTransactionInconsistentTableStateException("Inconsistent ("+ records.size() +") number of fetched records, should be between 0 and 1.", null, "The id is: " + transactionId.toString(), "");
        */
        return records.get(0);
    }


    private List<DatabaseTableRecord> getRecordsByFilter(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = this.database.getTable(HoldBankMoneyTransactionDatabaseConstants.HOLD_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();
        return table.getRecords();
    }


    private void newHoldTransactionRecord(DatabaseTableRecord newRecord, BankTransactionParameters holdParameters) {

        newRecord.setUUIDValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_ID_COLUMN_NAME, holdParameters.getTransactionId());
        newRecord.setStringValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_WALLET_PUBLIC_KEY_COLUMN_NAME, holdParameters.getPublicKeyWallet());
        newRecord.setStringValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME, holdParameters.getPublicKeyActor());
        newRecord.setStringValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME, holdParameters.getPublicKeyPlugin());
        //TODO: Colocar BigDecimal holdParameters.getAmount().floatValue()
        newRecord.setStringValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_ACCOUNT_NUMBER_COLUMN_NAME,holdParameters.getAccount());
        newRecord.setDoubleValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_AMOUNT_COLUMN_NAME, holdParameters.getAmount().floatValue());
        newRecord.setStringValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_CURRENCY_COLUMN_NAME, holdParameters.getCurrency().getCode());
        newRecord.setStringValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_MEMO_COLUMN_NAME, holdParameters.getMemo());
        newRecord.setStringValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_STATUS_COLUMN_NAME, BankTransactionStatus.ACKNOWLEDGE.getCode());
        newRecord.setLongValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME, (new Date().getTime() / 1000));
        newRecord.setLongValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME, 0);
    }

    private BankTransaction constructHoldTransactionFromRecord(DatabaseTableRecord record) throws CantCreateHoldTransactionException{

        UUID transactionId = record.getUUIDValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_ID_COLUMN_NAME);
        String publicKeyWallet = record.getStringValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_WALLET_PUBLIC_KEY_COLUMN_NAME);
        String publicKeyActor = record.getStringValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME);
        String publicKeyPlugin = record.getStringValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME);
        float amount = record.getFloatValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_AMOUNT_COLUMN_NAME);
        String memo = record.getStringValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_MEMO_COLUMN_NAME);
        long timestampAcknowledged = record.getLongValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME);
        long timestampConfirmedRejected = record.getLongValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME);
        String accountNumber=record.getStringValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_ACCOUNT_NUMBER_COLUMN_NAME);
        FiatCurrency currency;
        try {
            currency = FiatCurrency.getByCode(record.getStringValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_CURRENCY_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Invalid FiatCurrency value stored in table"
                    + HoldBankMoneyTransactionDatabaseConstants.HOLD_TABLE_NAME + " for id " + transactionId);
        }

        BankTransactionStatus transactionStatus;
        try {
            transactionStatus = BankTransactionStatus.getByCode(record.getStringValue(HoldBankMoneyTransactionDatabaseConstants.HOLD_STATUS_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Invalid bankTransactionStatus value stored in table"
                    + HoldBankMoneyTransactionDatabaseConstants.HOLD_TABLE_NAME + " for id " + transactionId);
        }
        return new BankTransactionImpl(transactionId,publicKeyPlugin,publicKeyWallet,publicKeyActor,amount,accountNumber,currency,memo, BankOperationType.HOLD, TransactionType.HOLD,timestampAcknowledged,transactionStatus);
        
    }

}
