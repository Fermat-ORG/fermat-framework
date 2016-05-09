package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.exceptions.CantGetUnholdTransactionException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantCreateUnholdTransactionException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantInitializeUnholdBankMoneyTransactionDatabaseException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantUpdateUnholdTransactionException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.exceptions.UnholdBankMoneyTransactionInconsistentTableStateException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.structure.BankTransactionImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by memo on 18/11/15.
 */
public class UnholdBankMoneyTransactionDao {
    private final ErrorManager errorManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public UnholdBankMoneyTransactionDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }

    public void initialize() throws CantInitializeUnholdBankMoneyTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            UnholdBankMoneyTransactionDatabaseFactory databaseFactory = new UnholdBankMoneyTransactionDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeUnholdBankMoneyTransactionDatabaseException("Database could not be opened", cantCreateDatabaseException, "Database Name: " + pluginId.toString(), "");
            }
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeUnholdBankMoneyTransactionDatabaseException("Database could not be opened", cantOpenDatabaseException, "Database Name: " + pluginId.toString(), "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeUnholdBankMoneyTransactionDatabaseException("Database could not be opened", FermatException.wrapException(e), "Database Name: " + pluginId.toString(), "");
        }
    }


    public BankTransaction createUnholdTransaction(BankTransactionParameters holdParameters) throws CantCreateUnholdTransactionException {

        DatabaseTable transactionTable = this.database.getTable(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME);
        DatabaseTableRecord newRecord = transactionTable.getEmptyRecord();
        newUnholdTransactionRecord(newRecord, holdParameters);        //Insertar valores en el record
        try {
            transactionTable.insertRecord(newRecord);
        } catch (CantInsertRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateUnholdTransactionException(e.getMessage(), e, "Hold Transaction", "Cant insert new record in table");
        }

        return constructUnholdTransactionFromRecord(newRecord);
    }

    public BankTransaction getUnholdTransaction(UUID transactionId) throws CantGetUnholdTransactionException {
        DatabaseTableRecord record;
        BankTransaction HoldTransaction;
        try {
            record = getRecordByPrimaryKey(transactionId);
            HoldTransaction = constructUnholdTransactionFromRecord(record);
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetUnholdTransactionException(e.getMessage(), e, "Hold Transaction", "Cant get record in table. Cannot load table into memory");
        } catch (UnholdBankMoneyTransactionInconsistentTableStateException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetUnholdTransactionException(e.getMessage(), e, "Hold Transaction", "Cant get record in table. Inconsistent number of fetched records, should be between 0 and 1.");
        } catch (CantCreateUnholdTransactionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetUnholdTransactionException(e.getMessage(), e, "Hold Transaction", "Cant get record in table. Failed while constructing transaction from record.");
        } catch (Exception e) {
            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetUnholdTransactionException(e.getMessage(), e, "Hold Transaction", "Cant get record in table. Failed while constructing transaction from record.");
        }
        return HoldTransaction;
    }

    public List<BankTransaction> getUnholdTransactionList(DatabaseTableFilter filter) throws CantGetUnholdTransactionException {
        List<BankTransaction> transactions = new ArrayList<>();
        try {
            for (DatabaseTableRecord record : getRecordsByFilter(filter)) {
                BankTransaction transaction = constructUnholdTransactionFromRecord(record);
                transactions.add(transaction);
            }
        } catch (CantCreateUnholdTransactionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetUnholdTransactionException(CantGetUnholdTransactionException.DEFAULT_MESSAGE, e, "Failed to get Cash Hold Transaction list. Filter: " + filter.toString(), "");
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetUnholdTransactionException(CantGetUnholdTransactionException.DEFAULT_MESSAGE, e, "Failed to get Cash Hold Transaction list. Filter: " + filter.toString(), "");
        }
        return transactions;
    }

    public List<BankTransaction> getAcknowledgedTransactionList() throws CantGetUnholdTransactionException {
        DatabaseTableFilter filter = getEmptyHoldTableFilter();
        filter.setColumn(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_STATUS_COLUMN_NAME);
        filter.setValue(BankTransactionStatus.ACKNOWLEDGE.getCode());
        filter.setType(DatabaseFilterType.EQUAL);

        return getUnholdTransactionList(filter);
    }

    public void updateUnholdTransactionStatus(UUID transactionId, BankTransactionStatus status) throws CantUpdateUnholdTransactionException {
        DatabaseTableRecord record;
        try {
            record = getRecordByPrimaryKey(transactionId);
            record.setStringValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_STATUS_COLUMN_NAME, status.getCode());
            if (status == BankTransactionStatus.CONFIRMED || status == BankTransactionStatus.REJECTED)
                record.setLongValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME, new Date().getTime());

            DatabaseTable table = database.getTable(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME);
            table.updateRecord(record);

        } catch (CantUpdateRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantUpdateUnholdTransactionException(e.getMessage(), e, "Hold Transaction", "Cant update bank hold transaction status. Cant update the record");
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantUpdateUnholdTransactionException(e.getMessage(), e, "Hold Transaction", "Cant update bank hold transaction status. Cant load table into memory.");
        } catch (UnholdBankMoneyTransactionInconsistentTableStateException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantUpdateUnholdTransactionException(e.getMessage(), e, "Hold Transaction", "Cant update bank hold transaction status. Inconsistent table state.");
        }

    }


    /* INTERNAL HELPER FUNCTIONS */
    private DatabaseTableFilter getEmptyHoldTableFilter() {
        return this.database.getTable(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME).getEmptyTableFilter();
    }


    private DatabaseTableRecord getRecordByPrimaryKey(UUID transactionId) throws CantLoadTableToMemoryException, UnholdBankMoneyTransactionInconsistentTableStateException {
        List<DatabaseTableRecord> records;
        DatabaseTable table = database.getTable(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME);

        table.addStringFilter(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_ID_COLUMN_NAME, transactionId.toString(), DatabaseFilterType.EQUAL);
        table.loadToMemory();
        records = table.getRecords();
        //TODO: fix this
        /*if (records.size() != 1)
            throw new UnholdBankMoneyTransactionInconsistentTableStateException("Inconsistent ("+ records.size() +") number of fetched records, should be between 0 and 1.", null, "The id is: " + transactionId.toString(), "");
        */
        return records.get(0);
    }


    private List<DatabaseTableRecord> getRecordsByFilter(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = this.database.getTable(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();
        return table.getRecords();
    }


    private void newUnholdTransactionRecord(DatabaseTableRecord newRecord, BankTransactionParameters holdParameters) {

        newRecord.setUUIDValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_ID_COLUMN_NAME, holdParameters.getTransactionId());
        newRecord.setStringValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_WALLET_PUBLIC_KEY_COLUMN_NAME, holdParameters.getPublicKeyWallet());
        newRecord.setStringValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME, holdParameters.getPublicKeyActor());
        newRecord.setStringValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME, holdParameters.getPublicKeyPlugin());
        //TODO: Colocar BigDecimal holdParameters.getAmount().floatValue()
        newRecord.setStringValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_ACCOUNT_NUMBER_COLUMN_NAME, holdParameters.getAccount());
        newRecord.setDoubleValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_AMOUNT_COLUMN_NAME, holdParameters.getAmount().floatValue());
        newRecord.setStringValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_CURRENCY_COLUMN_NAME, holdParameters.getCurrency().getCode());
        newRecord.setStringValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_MEMO_COLUMN_NAME, holdParameters.getMemo());
        newRecord.setStringValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_STATUS_COLUMN_NAME, BankTransactionStatus.ACKNOWLEDGE.getCode());
        newRecord.setLongValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME, (new Date().getTime() / 1000));
        newRecord.setLongValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME, 0);
    }

    private BankTransaction constructUnholdTransactionFromRecord(DatabaseTableRecord record) throws CantCreateUnholdTransactionException {

        UUID transactionId = record.getUUIDValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_ID_COLUMN_NAME);
        String publicKeyWallet = record.getStringValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_WALLET_PUBLIC_KEY_COLUMN_NAME);
        String publicKeyActor = record.getStringValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME);
        String publicKeyPlugin = record.getStringValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME);
        float amount = record.getFloatValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_AMOUNT_COLUMN_NAME);
        String memo = record.getStringValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_MEMO_COLUMN_NAME);
        long timestampAcknowledged = record.getLongValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME);
        long timestampConfirmedRejected = record.getLongValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME);
        String accountNumber = record.getStringValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_ACCOUNT_NUMBER_COLUMN_NAME);
        FiatCurrency currency;
        try {
            currency = FiatCurrency.getByCode(record.getStringValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_CURRENCY_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateUnholdTransactionException(e.getMessage(), e, "Hold Transaction", "Invalid FiatCurrency value stored in table"
                    + UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME + " for id " + transactionId);
        }

        BankTransactionStatus transactionStatus;
        try {
            transactionStatus = BankTransactionStatus.getByCode(record.getStringValue(UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_STATUS_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateUnholdTransactionException(e.getMessage(), e, "Hold Transaction", "Invalid CashTransactionStatus value stored in table"
                    + UnholdBankMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME + " for id " + transactionId);
        }
        return new BankTransactionImpl(transactionId, publicKeyPlugin, publicKeyWallet, publicKeyActor, amount, accountNumber, currency, memo, BankOperationType.HOLD, TransactionType.HOLD, timestampAcknowledged, transactionStatus);

    }


}
