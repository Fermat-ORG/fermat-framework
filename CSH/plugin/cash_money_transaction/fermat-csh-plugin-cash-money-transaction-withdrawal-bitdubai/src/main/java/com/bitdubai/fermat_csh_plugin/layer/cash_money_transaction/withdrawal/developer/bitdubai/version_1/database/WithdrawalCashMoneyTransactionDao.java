package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.exceptions.CantCreateWithdrawalTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransaction;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.exceptions.CantGetWithdrawalTransactionException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.exceptions.CantInitializeWithdrawalCashMoneyTransactionDatabaseException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.exceptions.WithdrawalCashMoneyTransactionInconsistentTableStateException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.structure.CashWithdrawalTransactionImpl;
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
public class WithdrawalCashMoneyTransactionDao {

    private final ErrorManager errorManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public WithdrawalCashMoneyTransactionDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }

    public void initialize() throws CantInitializeWithdrawalCashMoneyTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            WithdrawalCashMoneyTransactionDatabaseFactory databaseFactory = new WithdrawalCashMoneyTransactionDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_WITHDRAWAL, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeWithdrawalCashMoneyTransactionDatabaseException("Database could not be opened", cantCreateDatabaseException, "Database Name: " + pluginId.toString(), "");
            }
        }catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_WITHDRAWAL, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeWithdrawalCashMoneyTransactionDatabaseException("Database could not be opened", cantOpenDatabaseException, "Database Name: " + pluginId.toString(), "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_WITHDRAWAL, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeWithdrawalCashMoneyTransactionDatabaseException("Database could not be opened", FermatException.wrapException(e), "Database Name: " + pluginId.toString(), "");
        }
    }




    public CashWithdrawalTransaction createCashWithdrawalTransaction(CashTransactionParameters withdrawalParameters) throws CantCreateWithdrawalTransactionException {

        DatabaseTable transactionTable = this.database.getTable(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_TABLE_NAME);
        DatabaseTableRecord newRecord = transactionTable.getEmptyRecord();
        newWithdrawalTransactionRecord(newRecord, withdrawalParameters);        //Insertar valores en el record
        try {
            transactionTable.insertRecord(newRecord);
        }catch (CantInsertRecordException e) {
            throw new CantCreateWithdrawalTransactionException(e.getMessage(), e, "Withdrawal Transaction", "Cant insert new record in table");
        }

        return constructWithdrawalTransactionFromRecord(newRecord);
    }

    public CashWithdrawalTransaction getCashWithdrawalTransaction(UUID transactionId) throws CantGetWithdrawalTransactionException {
        DatabaseTableRecord record;
        CashWithdrawalTransaction cashWithdrawalTransaction;
        try {
            record = getRecordByPrimaryKey(transactionId);
            cashWithdrawalTransaction = constructWithdrawalTransactionFromRecord(record);
        } catch (CantLoadTableToMemoryException e){
            throw new CantGetWithdrawalTransactionException(e.getMessage(), e, "Withdrawal Transaction", "Cant get record in table. Cannot load table into memory");
        } catch (WithdrawalCashMoneyTransactionInconsistentTableStateException e) {
            throw new CantGetWithdrawalTransactionException(e.getMessage(), e, "Withdrawal Transaction", "Cant get record in table. Inconsistent number of fetched records, should be between 0 and 1.");
        } catch (CantCreateWithdrawalTransactionException e) {
            throw new CantGetWithdrawalTransactionException(e.getMessage(), e, "Withdrawal Transaction", "Cant get record in table. Failed while constructing transaction from record.");
        }

        return cashWithdrawalTransaction;
    }

    public List<CashWithdrawalTransaction> getCashWithdrawalTransactionList(DatabaseTableFilter filter) throws CantGetWithdrawalTransactionException
    {
        List<CashWithdrawalTransaction> transactions = new ArrayList<>();
        try {
            for (DatabaseTableRecord record : getRecordsByFilter(filter)) {
                CashWithdrawalTransaction transaction = constructWithdrawalTransactionFromRecord(record);
                transactions.add(transaction);
            }
        } catch (CantCreateWithdrawalTransactionException e) {
            throw new CantGetWithdrawalTransactionException(CantGetWithdrawalTransactionException.DEFAULT_MESSAGE, e, "Failed to get Cash Withdrawal Transaction list. Filter: " + filter.toString(), "");
        }catch (CantLoadTableToMemoryException e) {
            throw new CantGetWithdrawalTransactionException(CantGetWithdrawalTransactionException.DEFAULT_MESSAGE, e, "Failed to get Cash Withdrawal Transaction list. Filter: " + filter.toString(), "");
        }
        return transactions;
    }






    /* INTERNAL HELPER FUNCTIONS */
    private DatabaseTableFilter getEmptyWithdrawalTableFilter() {
        return this.database.getTable(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_TABLE_NAME).getEmptyTableFilter();
    }


    private DatabaseTableRecord getRecordByPrimaryKey(UUID transactionId) throws CantLoadTableToMemoryException, WithdrawalCashMoneyTransactionInconsistentTableStateException {
        List<DatabaseTableRecord> records;
        DatabaseTable table = database.getTable(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_TABLE_NAME);

        table.addStringFilter(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_TRANSACTION_ID_COLUMN_NAME, transactionId.toString(), DatabaseFilterType.EQUAL);
        table.loadToMemory();
        records = table.getRecords();

        if (records.size() != 1)
            throw new WithdrawalCashMoneyTransactionInconsistentTableStateException("Inconsistent ("+ records.size() +") number of fetched records, should be between 0 and 1.", null, "The id is: " + transactionId.toString(), "");

        return records.get(0);
    }


    private List<DatabaseTableRecord> getRecordsByFilter(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = this.database.getTable(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();
        return table.getRecords();
    }


    private void newWithdrawalTransactionRecord(DatabaseTableRecord newRecord, CashTransactionParameters withdrawalParameters) {

        newRecord.setUUIDValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_TRANSACTION_ID_COLUMN_NAME, withdrawalParameters.getTransactionId());
        newRecord.setStringValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_WALLET_PUBLIC_KEY_COLUMN_NAME, withdrawalParameters.getPublicKeyWallet());
        newRecord.setStringValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_ACTOR_PUBLIC_KEY_COLUMN_NAME, withdrawalParameters.getPublicKeyActor());
        newRecord.setStringValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_PLUGIN_PUBLIC_KEY_COLUMN_NAME, withdrawalParameters.getPublicKeyPlugin());
        newRecord.setStringValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_AMOUNT_COLUMN_NAME, withdrawalParameters.getAmount().toPlainString());
        newRecord.setStringValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_CURRENCY_COLUMN_NAME, withdrawalParameters.getCurrency().getCode());
        newRecord.setStringValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_MEMO_COLUMN_NAME, withdrawalParameters.getMemo());
        newRecord.setLongValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_TIMESTAMP_COLUMN_NAME, (new Date().getTime() / 1000));
    }

    private CashWithdrawalTransaction constructWithdrawalTransactionFromRecord(DatabaseTableRecord record) throws CantCreateWithdrawalTransactionException{

        UUID transactionId = record.getUUIDValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_TRANSACTION_ID_COLUMN_NAME);
        String publicKeyWallet = record.getStringValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_WALLET_PUBLIC_KEY_COLUMN_NAME);
        String publicKeyActor = record.getStringValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_ACTOR_PUBLIC_KEY_COLUMN_NAME);
        String publicKeyPlugin = record.getStringValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_PLUGIN_PUBLIC_KEY_COLUMN_NAME);
        BigDecimal amount = new BigDecimal(record.getStringValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_AMOUNT_COLUMN_NAME));
        String memo = record.getStringValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_MEMO_COLUMN_NAME);
        long timestamp = record.getLongValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_TIMESTAMP_COLUMN_NAME);

        FiatCurrency currency;
        try {
            currency = FiatCurrency.getByCode(record.getStringValue(WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_CURRENCY_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            throw new CantCreateWithdrawalTransactionException(e.getMessage(), e, "Withdrawal Transaction", "Invalid FiatCurrency value stored in table"
                    + WithdrawalCashMoneyTransactionDatabaseConstants.WITHDRAWAL_TABLE_NAME + " for id " + transactionId);
        }

        return new CashWithdrawalTransactionImpl(transactionId, publicKeyWallet, publicKeyActor, publicKeyPlugin,
                                           amount, currency, memo, timestamp);
    }

}
