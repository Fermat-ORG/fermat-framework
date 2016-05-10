package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.deposit.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.exceptions.CantCreateDepositTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransaction;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransactionParameters;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.deposit.developer.bitdubai.version_1.exceptions.CantGetDepositTransactionException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.deposit.developer.bitdubai.version_1.exceptions.CantInitializeDepositCashMoneyTransactionDatabaseException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.deposit.developer.bitdubai.version_1.exceptions.DepositCashMoneyTransactionInconsistentTableStateException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.deposit.developer.bitdubai.version_1.structure.CashDepositTransactionImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/18/2015.
 */
public class DepositCashMoneyTransactionDao {

    private final ErrorManager errorManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public DepositCashMoneyTransactionDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }

    public void initialize() throws CantInitializeDepositCashMoneyTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            DepositCashMoneyTransactionDatabaseFactory databaseFactory = new DepositCashMoneyTransactionDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeDepositCashMoneyTransactionDatabaseException("Database could not be opened", cantCreateDatabaseException, "Database Name: " + pluginId.toString(), "");
            }
        }catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeDepositCashMoneyTransactionDatabaseException("Database could not be opened", cantOpenDatabaseException, "Database Name: " + pluginId.toString(), "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeDepositCashMoneyTransactionDatabaseException("Database could not be opened", FermatException.wrapException(e), "Database Name: " + pluginId.toString(), "");
        }
    }




    public CashDepositTransaction createCashDepositTransaction(CashTransactionParameters depositParameters) throws CantCreateDepositTransactionException {

        DatabaseTable transactionTable = this.database.getTable(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_TABLE_NAME);
        DatabaseTableRecord newRecord = transactionTable.getEmptyRecord();
        newDepositTransactionRecord(newRecord, depositParameters);        //Insertar valores en el record
        try {
            transactionTable.insertRecord(newRecord);
        }catch (CantInsertRecordException e) {
            throw new CantCreateDepositTransactionException(e.getMessage(), e, "Deposit Transaction", "Cant insert new record in table");
        }

        return constructDepositTransactionFromRecord(newRecord);
    }

    public CashDepositTransaction getCashDepositTransaction(UUID transactionId) throws CantGetDepositTransactionException {
        DatabaseTableRecord record;
        CashDepositTransaction cashDepositTransaction;
        try {
            record = getRecordByPrimaryKey(transactionId);
            cashDepositTransaction = constructDepositTransactionFromRecord(record);
        } catch (CantLoadTableToMemoryException e){
            throw new CantGetDepositTransactionException(e.getMessage(), e, "Deposit Transaction", "Cant get record in table. Cannot load table into memory");
        } catch (DepositCashMoneyTransactionInconsistentTableStateException e) {
            throw new CantGetDepositTransactionException(e.getMessage(), e, "Deposit Transaction", "Cant get record in table. Inconsistent number of fetched records, should be between 0 and 1.");
        } catch (CantCreateDepositTransactionException e) {
            throw new CantGetDepositTransactionException(e.getMessage(), e, "Deposit Transaction", "Cant get record in table. Failed while constructing transaction from record.");
        }

        return cashDepositTransaction;
    }

    public List<CashDepositTransaction> getCashDepositTransactionList(DatabaseTableFilter filter) throws CantGetDepositTransactionException
    {
        List<CashDepositTransaction> transactions = new ArrayList<>();
        try {
            for (DatabaseTableRecord record : getRecordsByFilter(filter)) {
                CashDepositTransaction transaction = constructDepositTransactionFromRecord(record);
                transactions.add(transaction);
            }
        } catch (CantCreateDepositTransactionException e) {
            throw new CantGetDepositTransactionException(CantGetDepositTransactionException.DEFAULT_MESSAGE, e, "Failed to get Cash Deposit Transaction list. Filter: " + filter.toString(), "");
        }catch (CantLoadTableToMemoryException e) {
            throw new CantGetDepositTransactionException(CantGetDepositTransactionException.DEFAULT_MESSAGE, e, "Failed to get Cash Deposit Transaction list. Filter: " + filter.toString(), "");
        }
        return transactions;
    }






    /* INTERNAL HELPER FUNCTIONS */
    private DatabaseTableFilter getEmptyDepositTableFilter() {
        return this.database.getTable(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_TABLE_NAME).getEmptyTableFilter();
    }


    private DatabaseTableRecord getRecordByPrimaryKey(UUID transactionId) throws CantLoadTableToMemoryException, DepositCashMoneyTransactionInconsistentTableStateException {
        List<DatabaseTableRecord> records;
        DatabaseTable table = database.getTable(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_TABLE_NAME);

        table.addStringFilter(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_TRANSACTION_ID_COLUMN_NAME, transactionId.toString(), DatabaseFilterType.EQUAL);
        table.loadToMemory();
        records = table.getRecords();

        if (records.size() != 1)
            throw new DepositCashMoneyTransactionInconsistentTableStateException("Inconsistent ("+ records.size() +") number of fetched records, should be between 0 and 1.", null, "The id is: " + transactionId.toString(), "");

        return records.get(0);
    }


    private List<DatabaseTableRecord> getRecordsByFilter(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = this.database.getTable(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();
        return table.getRecords();
    }


    private void newDepositTransactionRecord(DatabaseTableRecord newRecord, CashTransactionParameters depositParameters) {

        newRecord.setUUIDValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_TRANSACTION_ID_COLUMN_NAME, depositParameters.getTransactionId());
        newRecord.setStringValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_WALLET_PUBLIC_KEY_COLUMN_NAME, depositParameters.getPublicKeyWallet());
        newRecord.setStringValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_ACTOR_PUBLIC_KEY_COLUMN_NAME, depositParameters.getPublicKeyActor());
        newRecord.setStringValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_PLUGIN_PUBLIC_KEY_COLUMN_NAME, depositParameters.getPublicKeyPlugin());
        newRecord.setStringValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_AMOUNT_COLUMN_NAME, depositParameters.getAmount().toPlainString());
        newRecord.setStringValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_CURRENCY_COLUMN_NAME, depositParameters.getCurrency().getCode());
        newRecord.setStringValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_MEMO_COLUMN_NAME, depositParameters.getMemo());
        newRecord.setLongValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_TIMESTAMP_COLUMN_NAME, (new Date().getTime() / 1000));
    }

    private CashDepositTransaction constructDepositTransactionFromRecord(DatabaseTableRecord record) throws CantCreateDepositTransactionException{

        UUID transactionId = record.getUUIDValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_TRANSACTION_ID_COLUMN_NAME);
        String publicKeyWallet = record.getStringValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_WALLET_PUBLIC_KEY_COLUMN_NAME);
        String publicKeyActor = record.getStringValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_ACTOR_PUBLIC_KEY_COLUMN_NAME);
        String publicKeyPlugin = record.getStringValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_PLUGIN_PUBLIC_KEY_COLUMN_NAME);
        BigDecimal amount = new BigDecimal(record.getStringValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_AMOUNT_COLUMN_NAME));
        String memo = record.getStringValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_MEMO_COLUMN_NAME);
        long timestamp = record.getLongValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_TIMESTAMP_COLUMN_NAME);

        FiatCurrency currency;
        try {
            currency = FiatCurrency.getByCode(record.getStringValue(DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_CURRENCY_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            throw new CantCreateDepositTransactionException(e.getMessage(), e, "Deposit Transaction", "Invalid FiatCurrency value stored in table"
                    + DepositCashMoneyTransactionDatabaseConstants.DEPOSIT_TABLE_NAME + " for id " + transactionId);
        }

        return new CashDepositTransactionImpl(transactionId, publicKeyWallet, publicKeyActor, publicKeyPlugin,
                                           amount, currency, memo, timestamp);
    }

}
