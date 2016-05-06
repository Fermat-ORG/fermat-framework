package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantCreateHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransactionParameters;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.exceptions.CantInitializeHoldCashMoneyTransactionDatabaseException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.exceptions.CantUpdateHoldTransactionException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.exceptions.HoldCashMoneyTransactionInconsistentTableStateException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.structure.CashHoldTransactionImpl;
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
public class HoldCashMoneyTransactionDao {

    private final ErrorManager errorManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public HoldCashMoneyTransactionDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }

    public void initialize() throws CantInitializeHoldCashMoneyTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            HoldCashMoneyTransactionDatabaseFactory databaseFactory = new HoldCashMoneyTransactionDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeHoldCashMoneyTransactionDatabaseException("Database could not be opened", cantCreateDatabaseException, "Database Name: " + pluginId.toString(), "");
            }
        }catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeHoldCashMoneyTransactionDatabaseException("Database could not be opened", cantOpenDatabaseException, "Database Name: " + pluginId.toString(), "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeHoldCashMoneyTransactionDatabaseException("Database could not be opened", FermatException.wrapException(e), "Database Name: " + pluginId.toString(), "");
        }
    }




    public CashHoldTransaction createCashHoldTransaction(CashHoldTransactionParameters holdParameters) throws CantCreateHoldTransactionException {

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

    public CashHoldTransaction getCashHoldTransaction(UUID transactionId) throws CantGetHoldTransactionException {
        DatabaseTableRecord record;
        CashHoldTransaction cashHoldTransaction;
        try {
            record = getRecordByPrimaryKey(transactionId);
            cashHoldTransaction = constructHoldTransactionFromRecord(record);
        } catch (CantLoadTableToMemoryException e){
            throw new CantGetHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant get record in table. Cannot load table into memory");
        } catch (HoldCashMoneyTransactionInconsistentTableStateException e) {
            throw new CantGetHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant get record in table. Inconsistent number of fetched records, should be between 0 and 1.");
        } catch (CantCreateHoldTransactionException e) {
            throw new CantGetHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant get record in table. Failed while constructing transaction from record.");
        }

        return cashHoldTransaction;
    }

    public List<CashHoldTransaction> getCashHoldTransactionList(DatabaseTableFilter filter) throws CantGetHoldTransactionException
    {
        List<CashHoldTransaction> transactions = new ArrayList<>();
        try {
            for (DatabaseTableRecord record : getRecordsByFilter(filter)) {
                CashHoldTransaction transaction = constructHoldTransactionFromRecord(record);
                transactions.add(transaction);
            }
        } catch (CantCreateHoldTransactionException e) {
            throw new CantGetHoldTransactionException(CantGetHoldTransactionException.DEFAULT_MESSAGE, e, "Failed to get Cash Hold Transaction list. Filter: " + filter.toString(), "");
        }catch (CantLoadTableToMemoryException e) {
            throw new CantGetHoldTransactionException(CantGetHoldTransactionException.DEFAULT_MESSAGE, e, "Failed to get Cash Hold Transaction list. Filter: " + filter.toString(), "");
        }
        return transactions;
    }

    public List<CashHoldTransaction> getAcknowledgedTransactionList() throws CantGetHoldTransactionException
    {
        DatabaseTableFilter filter = getEmptyHoldTableFilter();
        filter.setColumn(HoldCashMoneyTransactionDatabaseConstants.HOLD_STATUS_COLUMN_NAME);
        filter.setValue(CashTransactionStatus.ACKNOWLEDGED.getCode());
        filter.setType(DatabaseFilterType.EQUAL);

        return getCashHoldTransactionList(filter);
    }

    public void updateCashHoldTransactionStatus(UUID transactionId, CashTransactionStatus status) throws CantUpdateHoldTransactionException
    {
        DatabaseTableRecord record;
        try {
            record = getRecordByPrimaryKey(transactionId);
            record.setStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_STATUS_COLUMN_NAME, status.getCode());
            if(status == CashTransactionStatus.CONFIRMED || status == CashTransactionStatus.REJECTED)
                record.setLongValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME, (new Date().getTime() / 1000));

            DatabaseTable table = database.getTable(HoldCashMoneyTransactionDatabaseConstants.HOLD_TABLE_NAME);
            table.addStringFilter(HoldCashMoneyTransactionDatabaseConstants.HOLD_TRANSACTION_ID_COLUMN_NAME, transactionId.toString(), DatabaseFilterType.EQUAL);
            table.updateRecord(record);

        } catch (CantUpdateRecordException e){
            throw new CantUpdateHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant update cash hold transaction status. Cant update the record");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant update cash hold transaction status. Cant load table into memory.");
        } catch (HoldCashMoneyTransactionInconsistentTableStateException e) {
            throw new CantUpdateHoldTransactionException(e.getMessage(), e, "Hold Transaction", "Cant update cash hold transaction status. Inconsistent table state.");
        }

    }





    /* INTERNAL HELPER FUNCTIONS */
    private DatabaseTableFilter getEmptyHoldTableFilter() {
        return this.database.getTable(HoldCashMoneyTransactionDatabaseConstants.HOLD_TABLE_NAME).getEmptyTableFilter();
    }


    private DatabaseTableRecord getRecordByPrimaryKey(UUID transactionId) throws CantLoadTableToMemoryException, HoldCashMoneyTransactionInconsistentTableStateException {
        List<DatabaseTableRecord> records;
        DatabaseTable table = database.getTable(HoldCashMoneyTransactionDatabaseConstants.HOLD_TABLE_NAME);

        table.addStringFilter(HoldCashMoneyTransactionDatabaseConstants.HOLD_TRANSACTION_ID_COLUMN_NAME, transactionId.toString(), DatabaseFilterType.EQUAL);
        table.loadToMemory();
        records = table.getRecords();

        if (records.size() != 1)
            throw new HoldCashMoneyTransactionInconsistentTableStateException("Inconsistent ("+ records.size() +") number of fetched records, should be between 0 and 1.", null, "The id is: " + transactionId.toString(), "");

        return records.get(0);
    }


    private List<DatabaseTableRecord> getRecordsByFilter(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = this.database.getTable(HoldCashMoneyTransactionDatabaseConstants.HOLD_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();
        return table.getRecords();
    }


    private void newHoldTransactionRecord(DatabaseTableRecord newRecord, CashHoldTransactionParameters holdParameters) {

        newRecord.setUUIDValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_TRANSACTION_ID_COLUMN_NAME, holdParameters.getTransactionId());
        newRecord.setStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_WALLET_PUBLIC_KEY_COLUMN_NAME, holdParameters.getPublicKeyWallet());
        newRecord.setStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME, holdParameters.getPublicKeyActor());
        newRecord.setStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME, holdParameters.getPublicKeyPlugin());
        newRecord.setStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_AMOUNT_COLUMN_NAME, holdParameters.getAmount().toPlainString());
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
        BigDecimal amount = new BigDecimal(record.getStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_AMOUNT_COLUMN_NAME));
        String memo = record.getStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_MEMO_COLUMN_NAME);
        long timestampAcknowledged = record.getLongValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME);
        long timestampConfirmedRejected = record.getLongValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME);

        FiatCurrency currency;
        try {
            currency = FiatCurrency.getByCode(record.getStringValue(HoldCashMoneyTransactionDatabaseConstants.HOLD_CURRENCY_COLUMN_NAME));
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
