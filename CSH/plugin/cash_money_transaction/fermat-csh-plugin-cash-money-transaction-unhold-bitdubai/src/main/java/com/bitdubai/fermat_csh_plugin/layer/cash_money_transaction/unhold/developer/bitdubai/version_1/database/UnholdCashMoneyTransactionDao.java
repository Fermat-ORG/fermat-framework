package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.exceptions.CantCreateUnholdTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.exceptions.CantGetUnholdTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransactionParameters;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantInitializeUnholdCashMoneyTransactionDatabaseException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantUpdateUnholdTransactionException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.exceptions.UnholdCashMoneyTransactionInconsistentTableStateException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.structure.CashUnholdTransactionImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 11/18/2015.
 */
public class UnholdCashMoneyTransactionDao {

    private final ErrorManager errorManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public UnholdCashMoneyTransactionDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }

    public void initialize() throws CantInitializeUnholdCashMoneyTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            UnholdCashMoneyTransactionDatabaseFactory databaseFactory = new UnholdCashMoneyTransactionDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_UNHOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeUnholdCashMoneyTransactionDatabaseException("Database could not be opened", cantCreateDatabaseException, "Database Name: " + pluginId.toString(), "");
            }
        }catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_UNHOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeUnholdCashMoneyTransactionDatabaseException("Database could not be opened", cantOpenDatabaseException, "Database Name: " + pluginId.toString(), "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_UNHOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeUnholdCashMoneyTransactionDatabaseException("Database could not be opened", FermatException.wrapException(e), "Database Name: " + pluginId.toString(), "");
        }
    }




    public CashUnholdTransaction createCashUnholdTransaction(CashUnholdTransactionParameters unholdParameters) throws CantCreateUnholdTransactionException {

        DatabaseTable transactionTable = this.database.getTable(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME);
        DatabaseTableRecord newRecord = transactionTable.getEmptyRecord();
        newUnholdTransactionRecord(newRecord, unholdParameters);        //Insertar valores en el record
        try {
            transactionTable.insertRecord(newRecord);
        }catch (CantInsertRecordException e) {
            throw new CantCreateUnholdTransactionException(e.getMessage(), e, "Unhold Transaction", "Cant insert new record in table");
        }

        return constructUnholdTransactionFromRecord(newRecord);
    }

    public CashUnholdTransaction getCashUnholdTransaction(UUID transactionId) throws CantGetUnholdTransactionException {
        DatabaseTableRecord record;
        CashUnholdTransaction cashUnholdTransaction;
        try {
            record = getRecordByPrimaryKey(transactionId);
            cashUnholdTransaction = constructUnholdTransactionFromRecord(record);
        } catch (CantLoadTableToMemoryException e){
            throw new CantGetUnholdTransactionException(e.getMessage(), e, "Unhold Transaction", "Cant get record in table. Cannot load table into memory");
        } catch (UnholdCashMoneyTransactionInconsistentTableStateException e) {
            throw new CantGetUnholdTransactionException(e.getMessage(), e, "Unhold Transaction", "Cant get record in table. Inconsistent number of fetched records, should be between 0 and 1.");
        } catch (CantCreateUnholdTransactionException e) {
            throw new CantGetUnholdTransactionException(e.getMessage(), e, "Unhold Transaction", "Cant get record in table. Failed while constructing transaction from record.");
        }

        return cashUnholdTransaction;
    }

    public List<CashUnholdTransaction> getCashUnholdTransactionList(DatabaseTableFilter filter) throws CantGetUnholdTransactionException
    {
        List<CashUnholdTransaction> transactions = new ArrayList<>();
        try {
            for (DatabaseTableRecord record : getRecordsByFilter(filter)) {
                CashUnholdTransaction transaction = constructUnholdTransactionFromRecord(record);
                transactions.add(transaction);
            }
        } catch (CantCreateUnholdTransactionException e) {
            throw new CantGetUnholdTransactionException(CantGetUnholdTransactionException.DEFAULT_MESSAGE, e, "Failed to get Cash Unhold Transaction list. Filter: " + filter.toString(), "");
        }catch (CantLoadTableToMemoryException e) {
            throw new CantGetUnholdTransactionException(CantGetUnholdTransactionException.DEFAULT_MESSAGE, e, "Failed to get Cash Unhold Transaction list. Filter: " + filter.toString(), "");
        }
        return transactions;
    }

    public List<CashUnholdTransaction> getAcknowledgedTransactionList() throws CantGetUnholdTransactionException
    {
        DatabaseTableFilter filter = getEmptyUnholdTableFilter();
        filter.setColumn(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_STATUS_COLUMN_NAME);
        filter.setValue(CashTransactionStatus.ACKNOWLEDGED.getCode());
        filter.setType(DatabaseFilterType.EQUAL);

        return getCashUnholdTransactionList(filter);
    }

    public void updateCashUnholdTransactionStatus(UUID transactionId, CashTransactionStatus status) throws CantUpdateUnholdTransactionException
    {
        DatabaseTableRecord record;
        try {
            record = getRecordByPrimaryKey(transactionId);
            record.setStringValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_STATUS_COLUMN_NAME, status.getCode());
            if(status == CashTransactionStatus.CONFIRMED || status == CashTransactionStatus.REJECTED)
                record.setLongValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME, (new Date().getTime() / 1000));

            DatabaseTable table = database.getTable(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME);
            table.addStringFilter(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TRANSACTION_ID_COLUMN_NAME, transactionId.toString(), DatabaseFilterType.EQUAL);
            table.updateRecord(record);

        } catch (CantUpdateRecordException e){
            throw new CantUpdateUnholdTransactionException(e.getMessage(), e, "Unhold Transaction", "Cant update cash unhold transaction status. Cant update the record");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateUnholdTransactionException(e.getMessage(), e, "Unhold Transaction", "Cant update cash unhold transaction status. Cant load table into memory.");
        } catch (UnholdCashMoneyTransactionInconsistentTableStateException e) {
            throw new CantUpdateUnholdTransactionException(e.getMessage(), e, "Unhold Transaction", "Cant update cash unhold transaction status. Inconsistent table state.");
        }

    }





    /* INTERNAL HELPER FUNCTIONS */
    private DatabaseTableFilter getEmptyUnholdTableFilter() {
        return this.database.getTable(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME).getEmptyTableFilter();
    }


    private DatabaseTableRecord getRecordByPrimaryKey(UUID transactionId) throws CantLoadTableToMemoryException, UnholdCashMoneyTransactionInconsistentTableStateException {
        List<DatabaseTableRecord> records;
        DatabaseTable table = database.getTable(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME);

        table.addStringFilter(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TRANSACTION_ID_COLUMN_NAME, transactionId.toString(), DatabaseFilterType.EQUAL);
        table.loadToMemory();
        records = table.getRecords();

        if (records.size() != 1)
            throw new UnholdCashMoneyTransactionInconsistentTableStateException("Inconsistent ("+ records.size() +") number of fetched records, should be between 0 and 1.", null, "The id is: " + transactionId.toString(), "");

        return records.get(0);
    }


    private List<DatabaseTableRecord> getRecordsByFilter(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = this.database.getTable(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();
        return table.getRecords();
    }


    private void newUnholdTransactionRecord(DatabaseTableRecord newRecord, CashUnholdTransactionParameters unholdParameters) {

        newRecord.setUUIDValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TRANSACTION_ID_COLUMN_NAME, unholdParameters.getTransactionId());
        newRecord.setStringValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_WALLET_PUBLIC_KEY_COLUMN_NAME, unholdParameters.getPublicKeyWallet());
        newRecord.setStringValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME, unholdParameters.getPublicKeyActor());
        newRecord.setStringValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME, unholdParameters.getPublicKeyPlugin());
        newRecord.setStringValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_AMOUNT_COLUMN_NAME, unholdParameters.getAmount().toPlainString());
        newRecord.setStringValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_CURRENCY_COLUMN_NAME, unholdParameters.getCurrency().getCode());
        newRecord.setStringValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_MEMO_COLUMN_NAME, unholdParameters.getMemo());
        newRecord.setStringValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_STATUS_COLUMN_NAME, CashTransactionStatus.ACKNOWLEDGED.getCode());
        newRecord.setLongValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME, (new Date().getTime() / 1000));
        newRecord.setLongValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME, 0);
    }

    private CashUnholdTransaction constructUnholdTransactionFromRecord(DatabaseTableRecord record) throws CantCreateUnholdTransactionException{

        UUID transactionId = record.getUUIDValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TRANSACTION_ID_COLUMN_NAME);
        String publicKeyWallet = record.getStringValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_WALLET_PUBLIC_KEY_COLUMN_NAME);
        String publicKeyActor = record.getStringValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME);
        String publicKeyPlugin = record.getStringValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME);
        BigDecimal amount = new BigDecimal(record.getStringValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_AMOUNT_COLUMN_NAME));
        String memo = record.getStringValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_MEMO_COLUMN_NAME);
        long timestampAcknowledged = record.getLongValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME);
        long timestampConfirmedRejected = record.getLongValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME);

        FiatCurrency currency;
        try {
            currency = FiatCurrency.getByCode(record.getStringValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_CURRENCY_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            throw new CantCreateUnholdTransactionException(e.getMessage(), e, "Unhold Transaction", "Invalid FiatCurrency value stored in table"
                    + UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME + " for id " + transactionId);
        }

        CashTransactionStatus transactionStatus;
        try {
            transactionStatus = CashTransactionStatus.getByCode(record.getStringValue(UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_STATUS_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            throw new CantCreateUnholdTransactionException(e.getMessage(), e, "Unhold Transaction", "Invalid CashTransactionStatus value stored in table"
                    + UnholdCashMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME + " for id " + transactionId);
        }

        return new CashUnholdTransactionImpl(transactionId, publicKeyWallet, publicKeyActor, publicKeyPlugin,
                amount, currency, memo, transactionStatus, timestampAcknowledged,
                timestampConfirmedRejected);
    }

}
