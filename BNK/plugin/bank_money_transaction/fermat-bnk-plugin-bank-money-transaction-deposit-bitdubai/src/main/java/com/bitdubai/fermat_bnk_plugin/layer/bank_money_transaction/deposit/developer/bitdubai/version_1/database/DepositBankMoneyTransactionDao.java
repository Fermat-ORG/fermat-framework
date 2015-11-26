package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;

import java.util.Date;
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
        record.setFloatValue(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_AMOUNT_COLUMN_NAME, bankTransactionParameters.getAmount());
        record.setStringValue(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_ID_COLUMN_NAME,bankTransactionParameters.getTransactionId().toString());
        record.setStringValue(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_PLUGIN_PUBLIC_KEY_COLUMN_NAME,bankTransactionParameters.getPublicKeyPlugin());
        record.setStringValue(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_ACCOUNT_NUMBER_COLUMN_NAME,bankTransactionParameters.getAccount());
        record.setStringValue(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_MEMO_COLUMN_NAME,bankTransactionParameters.getMemo());
        record.setStringValue(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_FIAT_CURRENCY_TYPE_COLUMN_NAME,bankTransactionParameters.getCurrency().getCode());
        record.setLongValue(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_TIMESTAMP_COLUMN_NAME,new Date().getTime());
        try {
            table.insertRecord(record);
        }catch (CantInsertRecordException e){

        }
    }
}
