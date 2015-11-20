package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;

import java.util.UUID;

/**
 * Created by memo on 18/11/15.
 */
public class DepositBankMoneyTransactionDao {
    UUID pluginId;
    Database database;

    PluginDatabaseSystem pluginDatabaseSystem;

    public DepositBankMoneyTransactionDao(UUID pluginId,PluginDatabaseSystem pluginDatabaseSystem) throws CantExecuteDatabaseOperationException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        database = openDatabase();
    }

    private Database openDatabase() throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, DepositBankMoneyTransactionDatabaseConstants.DATABASE_NAME);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Opening the Asset Issuing Transaction Database", "Error in database plugin.");
        }
    }

    public void registerDepositTransaction(BankTransactionParameters bankTransactionParameters){
        DatabaseTable table = database.getTable(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_TABLE_NAME);
        DatabaseTableRecord record = table.getEmptyRecord();
        record.setLongValue(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_AMOUNT_COLUMN_NAME, bankTransactionParameters.getAccountNumber());
        record.setStringValue(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_ID_COLUMN_NAME,bankTransactionParameters.getTransactionId().toString());
    }
}
