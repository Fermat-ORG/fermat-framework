package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.util.BankMoneyStockReplenishmentBusinessTransactionWrapper;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantInitializeBankMoneyStockReplenishmentBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantInsertRecordBankMoneyStockReplenishmentException;

import java.util.UUID;

import sun.util.locale.StringTokenIterator;

/**
 * Created by Yordin Alayn on 27.09.15.
 */
public class BankMoneyStockReplenishmentBusinessTransactionDao{

    /**
     * CryptoAddressBook Interface member variables.
     */
    private Database database;

    /**
     * DealsWithDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;


    /**
     * Constructor.
     */
    public BankMoneyStockReplenishmentBusinessTransactionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * BankMoneyStockReplenishment Interface implementation.
     */
    public void initialize() throws CantInitializeBankMoneyStockReplenishmentBusinessTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                BankMoneyStockReplenishmentBusinessTransactionDatabaseFactory databaseFactory = new BankMoneyStockReplenishmentBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeBankMoneyStockReplenishmentBusinessTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeBankMoneyStockReplenishmentBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeBankMoneyStockReplenishmentBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeBankMoneyStockReplenishmentBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }


    public void createNewBankMoneyStockReplenishment(
            String publicKeyBroker,
            String merchandiseCurrency,
            String executionTransactionId,
            BankCurrencyType bankCurrencyType,
            BankOperationType bankOperationType
    ) throws CantInsertRecordBankMoneyStockReplenishmentException {
        try {
            DatabaseTable transactionTable = this.database.getTable(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = transactionTable.getEmptyRecord();
            BusinessTransactionStatus transactionStatus = BusinessTransactionStatus.PENDING_PAYMENT;
            loadRecordAsNew(recordToInsert, transactionStatus,publicKeyBroker, merchandiseCurrency, executionTransactionId, bankCurrencyType, bankOperationType);
            transactionTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantInsertRecordBankMoneyStockReplenishmentException("An exception happened", e, "", "");
        } catch (Exception exception) {
            throw new CantInsertRecordBankMoneyStockReplenishmentException(CantInsertRecordBankMoneyStockReplenishmentException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /*public void updateTransactionStatusBankMoneyStockReplenishment(OutgoingIntraActorTransactionWrapper businessTransaction) throws OutgoingIntraActorCantCancelTransactionException {
        try {
            setToState(businessTransaction, TransactionState.CANCELED);
        } catch (CantUpdateRecordException | OutgoingIntraActorInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new OutgoingIntraActorCantCancelTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new OutgoingIntraActorCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }*/

    private void loadRecordAsNew(
        DatabaseTableRecord databaseTableRecord,
        BusinessTransactionStatus transactionStatus,
        String publicKeyBroker,
        String merchandiseCurrency,
        String executionTransactionId,
        BankCurrencyType bankCurrencyType,
        BankOperationType bankOperationType
    ) {
        UUID transactionId = UUID.randomUUID();

        databaseTableRecord.setUUIDValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_TRANSACTION_ID_COLUMN_NAME, transactionId);
        databaseTableRecord.setStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_STATUS_COLUMN_NAME, transactionStatus.getCode());
        databaseTableRecord.setStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_PUBLIC_KEY_BROKER_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency);
        databaseTableRecord.setStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_EXECUTION_TRANSACTION_ID_COLUMN_NAME, executionTransactionId);
        databaseTableRecord.setStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_BANK_CURRENCY_TYPE_COLUMN_NAME,bankCurrencyType.getCode());
        databaseTableRecord.setStringValue(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_BANK_OPERATION_TYPE_COLUMN_NAME,bankOperationType.getCode());

    }

    /*private void setToState(BankMoneyStockReplenishmentBusinessTransactionWrapper businessTransaction, BusinessTransactionStatus status) throws CantUpdateRecordException, OutgoingIntraActorInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable       transactionTable = this.database.getTable(BankMoneyStockReplenishmentBusinessTransactionDatabaseConstants.BANK_MONEY_STOCK_REPLENISHMENT_TABLE_NAME);
        DatabaseTableRecord recordToUpdate   = getByPrimaryKey(businessTransaction.getIdTransaction());
        recordToUpdate.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_STATUS_COLUMN_NAME, status.getCode());
        transactionTable.updateRecord(recordToUpdate);
    }*/

}
