package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.exceptions.CantInitializeDepositBankMoneyTransactionDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.Date;
import java.util.UUID;

/**
 * Created by memo on 18/11/15.
 */
public class DepositBankMoneyTransactionDao {
    UUID pluginId;
    Database database;
    PluginDatabaseSystem pluginDatabaseSystem;
    ErrorManager errorManager;

    public DepositBankMoneyTransactionDao(UUID pluginId,PluginDatabaseSystem pluginDatabaseSystem,ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }


    public void initialize()throws CantInitializeDepositBankMoneyTransactionDatabaseException{
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            DepositBankMoneyTransactionDatabaseFactory databaseFactory = new DepositBankMoneyTransactionDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeDepositBankMoneyTransactionDatabaseException("Database could not be opened", cantCreateDatabaseException, "Database Name: " + pluginId.toString(), "");
            }
        }catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeDepositBankMoneyTransactionDatabaseException("Database could not be opened", cantOpenDatabaseException, "Database Name: " + pluginId.toString(), "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeDepositBankMoneyTransactionDatabaseException("Database could not be opened", FermatException.wrapException(e), "Database Name: " + pluginId.toString(), "");
        }
    }
    public void registerDepositTransaction(BankTransactionParameters bankTransactionParameters){
        DatabaseTable table = database.getTable(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_TABLE_NAME);
        DatabaseTableRecord record = table.getEmptyRecord();
        //TODO: Colocar BigDecimal bankTransactionParameters.getAmount().floatValue()
        record.setFloatValue(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_AMOUNT_COLUMN_NAME, bankTransactionParameters.getAmount().floatValue());
        record.setStringValue(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_ID_COLUMN_NAME,bankTransactionParameters.getTransactionId().toString());
        record.setStringValue(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_PLUGIN_PUBLIC_KEY_COLUMN_NAME,bankTransactionParameters.getPublicKeyPlugin());
        record.setStringValue(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_ACCOUNT_NUMBER_COLUMN_NAME,bankTransactionParameters.getAccount());
        record.setStringValue(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_MEMO_COLUMN_NAME,bankTransactionParameters.getMemo());
        record.setLongValue(DepositBankMoneyTransactionDatabaseConstants.DEPOSIT_TIMESTAMP_COLUMN_NAME,new Date().getTime());
        try {
            table.insertRecord(record);
        }catch (CantInsertRecordException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

}
