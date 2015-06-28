package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.BitcoinTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionState;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.enums.TransactionStatus;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.CantInitializeDaoException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.InconsistentTableStateException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.util.TransactionWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.06.25..
 */
public class OutgoingExtraUserDao implements DealsWithErrors, DealsWithPluginDatabaseSystem {

    /*
     * DealsWithErrors Interface member variables
     */
    private ErrorManager errorManager;

    /*
     * DealsWithPluginDatabaseSystem Interface member variables
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
     * OutgoingExtraUserDao member variables
     */
    Database database;

    /*
     * DealsWithErrors Interface methods implementation
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /*
     * DealsWithPluginDatabaseSystem Interface methods implementation
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    public void initialize(UUID pluginId) throws CantInitializeDaoException {

        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {

            OutgoingExtraUserDatabaseFactory databaseFactory = new OutgoingExtraUserDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            try {
                this.database = databaseFactory.createDatabase(pluginId, OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeDaoException("I couldn't create the database",cantCreateDatabaseException,"Database Name: "+OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_DATABASE_NAME,"");
            }
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeDaoException("I couldn't open the database",cantOpenDatabaseException,"Database Name: "+OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_DATABASE_NAME,"");
        }
    }

    public void registerNewTransaction(UUID walletID, CryptoAddress destinationAddress, long cryptoAmount) throws CantInsertRecordException {
        DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);

        DatabaseTableRecord recordToInsert = transactionTable.getEmptyRecord();

        loadRecordAsNew(recordToInsert, walletID, destinationAddress, cryptoAmount);

        transactionTable.insertRecord(recordToInsert);

    }

    public List<TransactionWrapper> getNewTransactions() throws CantLoadTableToMemory, InvalidParameterException {
        return getAllInState(TransactionStatus.NEW);
    }

    public List<TransactionWrapper> getPersistedInWalletTransactions() throws CantLoadTableToMemory, InvalidParameterException {
        return getAllInState(TransactionStatus.PERSISTED_IN_WALLET);
    }

    public List<TransactionWrapper> getSentToCryptoVaultTransactions() throws CantLoadTableToMemory, InvalidParameterException {
        return getAllInState(TransactionStatus.SENT_TO_CRYPTO_VOULT);
    }

    public void cancelTransaction(TransactionWrapper bitcoinTransaction) throws CantUpdateRecord, InconsistentTableStateException, CantLoadTableToMemory {
        setToState(bitcoinTransaction, TransactionStatus.CANCELED);
    }

    public void setToNew(TransactionWrapper bitcoinTransaction) throws CantUpdateRecord, InconsistentTableStateException, CantLoadTableToMemory {
        setToState(bitcoinTransaction, TransactionStatus.NEW);
    }

    public void setToPIW(TransactionWrapper bitcoinTransaction) throws CantUpdateRecord, InconsistentTableStateException, CantLoadTableToMemory {
        setToState(bitcoinTransaction,TransactionStatus.PERSISTED_IN_WALLET);
    }

    public void setToSTCV(TransactionWrapper bitcoinTransaction) throws CantUpdateRecord, InconsistentTableStateException, CantLoadTableToMemory {
        setToState(bitcoinTransaction,TransactionStatus.SENT_TO_CRYPTO_VOULT);
    }


    private  void loadRecordAsNew(DatabaseTableRecord databaseTableRecord, UUID walletId, CryptoAddress destinationAddress, long cryptoAmount){

        UUID transactionId = UUID.randomUUID();

        databaseTableRecord.setUUIDValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_ID_COLUMN_NAME,transactionId);
        databaseTableRecord.setUUIDValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_WALLET_ID_TO_DEBIT_COLUMN_NAME, walletId);

        // TODO: This will be completed when the vault gives it to us
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_HASH_COLUMN_NAME, "UNKNOWN YET");

        // TODO: This need to be completed in the future
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ADDRESS_FROM_COLUMN_NAME, "MY_ADDRESS");
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ADDRESS_TO_COLUMN_NAME, destinationAddress.getAddress());
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_CURRENY_COLUMN_NAME, destinationAddress.getCryptoCurrency().getCode());
        databaseTableRecord.setLongValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_STATUS_COLUMN_NAME, TransactionState.NEW.getCode());

        // TODO: This have to be changed fir the tinestamp when the network recognize the transaction
        databaseTableRecord.setLongValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis()/1000L);
    }


    private void setToState(TransactionWrapper bitcoinTransaction, TransactionStatus status) throws CantUpdateRecord, InconsistentTableStateException, CantLoadTableToMemory {
        DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);

        DatabaseTableRecord recordToUpdate = getByPrimaryKey(bitcoinTransaction.getTransactionId());

        recordToUpdate.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_STATUS_COLUMN_NAME,status.getCode());

        transactionTable.updateRecord(recordToUpdate);
    }


    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemory, InconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        records = transactionTable.getRecords();

        if(records.size() != 1)
            throw new InconsistentTableStateException("The number of records with a primary key is different thatn one ",null,"The id is: "+id.toString(),"");

        return records.get(0);
    }

    private List<TransactionWrapper> getAllInState(TransactionStatus transactionState) throws CantLoadTableToMemory, InvalidParameterException {

        DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_STATUS_COLUMN_NAME, transactionState.getCode(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();

        records = transactionTable.getRecords();

        return mapConvertToBT(records);

    }

    private TransactionWrapper convertToBT(DatabaseTableRecord record) throws InvalidParameterException {
        TransactionWrapper bitcoinTransaction = new TransactionWrapper();

        UUID walletId = record.getUUIDValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_WALLET_ID_TO_DEBIT_COLUMN_NAME);
        UUID transactionId = record.getUUIDValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_ID_COLUMN_NAME);
        String transactionHash = record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_HASH_COLUMN_NAME);
        CryptoAddress addressFrom = new CryptoAddress(
                record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ADDRESS_FROM_COLUMN_NAME),
                CryptoCurrency.getByCode(record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_CURRENY_COLUMN_NAME))
        );
        CryptoAddress addressTo = new CryptoAddress(
                record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ADDRESS_TO_COLUMN_NAME),
                CryptoCurrency.getByCode(record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_CURRENY_COLUMN_NAME))
        );
        long amount = record.getLongValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_AMOUNT_COLUMN_NAME);
        TransactionType type = TransactionType.DEBIT;
        TransactionState state = TransactionState.getByCode(record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_STATUS_COLUMN_NAME));
        long timestamp = record.getLongValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TIMESTAMP_COLUMN_NAME);
        String memo = "";

        bitcoinTransaction.setWalletId(walletId);
        bitcoinTransaction.setTransactionId(transactionId);
        bitcoinTransaction.setTramsactionHash(transactionHash);
        bitcoinTransaction.setAddressFrom(addressFrom);
        bitcoinTransaction.setAddressTo(addressTo);
        bitcoinTransaction.setAmount(amount);
        bitcoinTransaction.setType(type);
        bitcoinTransaction.setState(state);
        bitcoinTransaction.setTimestamp(timestamp);
        bitcoinTransaction.setMemo(memo);

        /*
        private String transactionHash;
        private CryptoAddress addressFrom;
        private CryptoAddress addressTo;
        private long amount;
        private TransactionType type;
        private TransactionState state;
        private long timestamp;
        private String memo;
        */

        return bitcoinTransaction;
    }

    // Apply convertToBT to all the elements in a list
    private List<TransactionWrapper> mapConvertToBT(List<DatabaseTableRecord> transactions) throws InvalidParameterException {
        List<TransactionWrapper> bitcoinTransactionList = new ArrayList<>();

        for(DatabaseTableRecord record : transactions)
            bitcoinTransactionList.add(convertToBT(record));

        return bitcoinTransactionList;
    }


}
