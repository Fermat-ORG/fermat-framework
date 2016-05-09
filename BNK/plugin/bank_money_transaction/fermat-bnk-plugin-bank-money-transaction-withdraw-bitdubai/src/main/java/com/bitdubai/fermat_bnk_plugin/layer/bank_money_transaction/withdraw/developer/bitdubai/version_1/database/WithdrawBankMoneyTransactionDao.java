package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.exceptions.CantInitializeWithdrawBankMoneyTransactionDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.Date;
import java.util.UUID;

/**
 * Created by memo on 18/11/15.
 */
public class WithdrawBankMoneyTransactionDao {
    UUID pluginId;
    Database database;
    PluginDatabaseSystem pluginDatabaseSystem;
    ErrorManager errorManager;

    public WithdrawBankMoneyTransactionDao(UUID pluginId,PluginDatabaseSystem pluginDatabaseSystem,ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }


    public void initialize()throws CantInitializeWithdrawBankMoneyTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            WithdrawBankMoneyTransactionDatabaseFactory databaseFactory = new WithdrawBankMoneyTransactionDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_WITHDRAW_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeWithdrawBankMoneyTransactionDatabaseException("Database could not be opened", cantCreateDatabaseException, "Database Name: " + pluginId.toString(), "");
            }
        }catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_WITHDRAW_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeWithdrawBankMoneyTransactionDatabaseException("Database could not be opened", cantOpenDatabaseException, "Database Name: " + pluginId.toString(), "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_WITHDRAW_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeWithdrawBankMoneyTransactionDatabaseException("Database could not be opened", FermatException.wrapException(e), "Database Name: " + pluginId.toString(), "");
        }
    }

    public void registerWithdrawTransaction(BankTransactionParameters bankTransactionParameters){
        DatabaseTable table = database.getTable(WithdrawBankMoneyTransactionDatabaseConstants.WITHDRAW_TABLE_NAME);
        DatabaseTableRecord record = table.getEmptyRecord();
        //TODO: Revisar Guillermo BigDecimal
        record.setFloatValue(WithdrawBankMoneyTransactionDatabaseConstants.WITHDRAW_AMOUNT_COLUMN_NAME, bankTransactionParameters.getAmount().floatValue());
        record.setStringValue(WithdrawBankMoneyTransactionDatabaseConstants.WITHDRAW_ID_COLUMN_NAME,bankTransactionParameters.getTransactionId().toString());
        record.setStringValue(WithdrawBankMoneyTransactionDatabaseConstants.WITHDRAW_PLUGIN_PUBLIC_KEY_COLUMN_NAME,bankTransactionParameters.getPublicKeyPlugin());
        record.setStringValue(WithdrawBankMoneyTransactionDatabaseConstants.WITHDRAW_ACCOUNT_NUMBER_COLUMN_NAME,bankTransactionParameters.getAccount());
        record.setStringValue(WithdrawBankMoneyTransactionDatabaseConstants.WITHDRAW_MEMO_COLUMN_NAME,bankTransactionParameters.getMemo());
        record.setLongValue(WithdrawBankMoneyTransactionDatabaseConstants.WITHDRAW_TIMESTAMP_COLUMN_NAME,new Date().getTime());
        try {
            table.insertRecord(record);
        }catch (CantInsertRecordException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_WITHDRAW_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }
}
