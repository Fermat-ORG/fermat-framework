package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;

import java.util.UUID;

/**
 * Created by franklin on 17/11/15.
 */
public class BusinessTransactionBankMoneyRestockDatabaseDao {

    Database database;
    UUID pluginId;
    /**
     * DealsWithPluginDatabaseSystem interface variable and implementation
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem interface member variables
     */
    PluginFileSystem pluginFileSystem;

    /**
     * Constructor
     */
    public BusinessTransactionBankMoneyRestockDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    private DatabaseTable getDatabaseTable(String tableName) {

        return database.getTable(tableName);
    }

    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.pluginId, BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_STOCK_DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            BusinessTransactionBankMoneyRestockDatabaseFactory businessTransactionBankMoneyRestockDatabaseFactory = new BusinessTransactionBankMoneyRestockDatabaseFactory(this.pluginDatabaseSystem);
            database = businessTransactionBankMoneyRestockDatabaseFactory.createDatabase(this.pluginId, BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_STOCK_DATABASE_NAME);
        }
        return database;
    }

    private DatabaseTableRecord getBankMoneyRestockRecord(BankMoneyTransaction bankMoneyTransaction
                                                          ) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_STOCK_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_TRANSACTION_ID_COLUMN_NAME, bankMoneyTransaction.getTransactionId().toString());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME, bankMoneyTransaction.getActorPublicKey());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_FIAT_CURRENCY_COLUMN_NAME, bankMoneyTransaction.getFiatCurrency().getCode());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME, bankMoneyTransaction.getActorPublicKey());

        return record;
    }
}
