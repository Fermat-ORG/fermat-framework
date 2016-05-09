package com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.enums.TransactionState;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorDaoException;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.exceptions.TransferIntraWalletUsersCantCancelTransactionException;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.exceptions.TransferIntraWalletUsersCantGetTransactionsException;

import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.exceptions.TransferIntraWalletUsersCantGetTransactionHashException;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.exceptions.TransferIntraWalletUsersCantInsertRecordException;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.exceptions.TransferIntraWalletUsersInconsistentTableStateException;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.utils.TransferIntraWalletUsersWrapper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Joaquin Carrasquero on 18/03/16.
 */
public class TransferIntraWalletUsersDao {


    private Database database;
    private ErrorManager errorManager;
    private PluginDatabaseSystem pluginDatabaseSystem;

    public TransferIntraWalletUsersDao(ErrorManager errorManager,
                                       PluginDatabaseSystem pluginDatabaseSystem) {
        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public void initialize(UUID pluginId) throws CantInitializeOutgoingIntraActorDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {

            TransferIntraWalletUsersDatabaseFactory databaseFactory = new TransferIntraWalletUsersDatabaseFactory(this.pluginDatabaseSystem);
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            try {
                this.database = databaseFactory.createDatabase(pluginId, TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeOutgoingIntraActorDaoException("I couldn't create the database", cantCreateDatabaseException, "Database Name: " + TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_DATABASE_NAME, "");
            } catch (Exception exception) {
                throw new CantInitializeOutgoingIntraActorDaoException(CantInitializeOutgoingIntraActorDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
            }

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeOutgoingIntraActorDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_DATABASE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeOutgoingIntraActorDaoException(CantInitializeOutgoingIntraActorDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }


    public void registerNewTransaction(UUID trxId,
                                       String txHash,
                                       long cryptoAmount,
                                       String notes,
                                       Actors actortype,
                                       ReferenceWallet reference_wallet_sending,
                                       ReferenceWallet reference_wallet_receiving,
                                       String wallet_public_key_sending,
                                       String wallet_public_key_receiving,
                                       TransactionState transactionState,
                                       BlockchainNetworkType blockchainNetworkType) throws TransferIntraWalletUsersCantInsertRecordException {
        try {
            DatabaseTable transactionTable = this.database.getTable(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TABLE_NAME);
            DatabaseTableRecord recordToInsert = transactionTable.getEmptyRecord();
            loadRecordAsNew(
                    recordToInsert,
                    trxId,
                    txHash,
                    cryptoAmount,
                    notes,
                    actortype,
                    reference_wallet_sending,
                    reference_wallet_receiving,
                    wallet_public_key_sending,
                    wallet_public_key_receiving,
                    transactionState,
                    blockchainNetworkType);
            transactionTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {

            throw new TransferIntraWalletUsersCantInsertRecordException("An exception happened", e, "", "");
        } catch (Exception exception) {
            throw new TransferIntraWalletUsersCantInsertRecordException(TransferIntraWalletUsersCantInsertRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public void updateTxHash(UUID transactionId,
                             String newHash) throws TransferIntraWalletUsersCantInsertRecordException {
        try {
            DatabaseTable transactionTable = this.database.getTable(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TABLE_NAME);
            transactionTable.addStringFilter(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_ID_COLUMN_NAME, transactionId.toString(), DatabaseFilterType.EQUAL);
            transactionTable.loadToMemory();

            if (transactionTable.getRecords().isEmpty()) throw new RecordsNotFoundException();

            DatabaseTableRecord record = transactionTable.getRecords().get(0);
            record.setStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_HASH_COLUMN_NAME, newHash);
            transactionTable.updateRecord(record);
        } catch (Exception exception) {
            throw new TransferIntraWalletUsersCantInsertRecordException(TransferIntraWalletUsersCantInsertRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }


    public List<TransferIntraWalletUsersWrapper> getSentToWalletTransactions() throws TransferIntraWalletUsersCantGetTransactionsException {
        try {
            return getAllInState(TransactionState.SENT_TO_WALLET);
        } catch (CantLoadTableToMemoryException | InvalidParameterException exception) {
            throw new TransferIntraWalletUsersCantGetTransactionsException("An exception happened", exception, "", "");
        } catch (Exception exception) {
            throw new TransferIntraWalletUsersCantGetTransactionsException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public List<TransferIntraWalletUsersWrapper> getCompletedTransactions() throws TransferIntraWalletUsersCantGetTransactionsException {
        try {
            return getAllInState(TransactionState.COMPLETED);
        } catch (CantLoadTableToMemoryException | InvalidParameterException exception) {
            throw new TransferIntraWalletUsersCantGetTransactionsException("An exception happened", exception, "", "");
        } catch (Exception exception) {
            throw new TransferIntraWalletUsersCantGetTransactionsException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public void cancelTransaction(TransferIntraWalletUsersWrapper bitcoinTransaction) throws TransferIntraWalletUsersCantCancelTransactionException {
        try {
            setToState(bitcoinTransaction, TransactionState.CANCELED);
        } catch (CantUpdateRecordException | TransferIntraWalletUsersInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new TransferIntraWalletUsersCantCancelTransactionException("An exception happened", exception, "", "");
        } catch (Exception exception) {
            throw new TransferIntraWalletUsersCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }


    public void setToSTCV(TransferIntraWalletUsersWrapper bitcoinTransaction) throws TransferIntraWalletUsersCantCancelTransactionException {
        try {
            setToState(bitcoinTransaction, TransactionState.SENT_TO_WALLET);
        } catch (CantUpdateRecordException | TransferIntraWalletUsersInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new TransferIntraWalletUsersCantCancelTransactionException("An exception happened", exception, "", "");
        } catch (Exception exception) {
            throw new TransferIntraWalletUsersCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public void setToError(TransferIntraWalletUsersWrapper bitcoinTransaction) throws TransferIntraWalletUsersCantCancelTransactionException {
        try {
            setToState(bitcoinTransaction, TransactionState.ERROR);
        } catch (CantUpdateRecordException | TransferIntraWalletUsersInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new TransferIntraWalletUsersCantCancelTransactionException("An exception happened", exception, "", "");
        } catch (Exception exception) {
            throw new TransferIntraWalletUsersCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public void setToCompleted(TransferIntraWalletUsersWrapper bitcoinTransaction) throws TransferIntraWalletUsersCantCancelTransactionException {
        try {
            setToState(bitcoinTransaction, TransactionState.COMPLETED);
        } catch (CantUpdateRecordException | TransferIntraWalletUsersInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new TransferIntraWalletUsersCantCancelTransactionException("An exception happened", exception, "", "");
        } catch (Exception exception) {
            throw new TransferIntraWalletUsersCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    private void loadRecordAsNew(DatabaseTableRecord databaseTableRecord,
                                 UUID trxId,
                                 String txHash,
                                 long cryptoAmount,
                                 String notes,
                                 Actors actortype,
                                 ReferenceWallet reference_wallet_sending,
                                 ReferenceWallet reference_wallet_receiving,
                                 String wallet_public_key_sending,
                                 String wallet_public_key_receiving,
                                 TransactionState transactionState,
                                 BlockchainNetworkType blockchainNetworkType) {

        databaseTableRecord.setUUIDValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_ID_COLUMN_NAME, trxId);
        // TODO: This will be completed when the vault gives it to us
        databaseTableRecord.setStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_HASH_COLUMN_NAME, txHash);

        databaseTableRecord.setLongValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);

        databaseTableRecord.setStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_STATUS_COLUMN_NAME, transactionState.getCode());

        databaseTableRecord.setStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_DESCRIPTION_COLUMN_NAME, notes);

        databaseTableRecord.setLongValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());

        databaseTableRecord.setStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_ACTOR_TYPE_COLUMN_NAME, actortype.getCode());

        databaseTableRecord.setStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_REFERENCE_TYPE_SENDING_COLUMN_NAME, reference_wallet_sending.getCode());

        databaseTableRecord.setStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_REFERENCE_TYPE_RECEIVING_COLUMN_NAME, reference_wallet_receiving.getCode());

        databaseTableRecord.setStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_PUBLIC_KEY_SENDING_COLUMN_NAME, wallet_public_key_sending);

        databaseTableRecord.setStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_PUBLIC_KEY_RECEIVING_COLUMN_NAME, wallet_public_key_receiving);

        databaseTableRecord.setStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_RUNNING_NETWORK_TYPE, blockchainNetworkType.getCode());

    }


    private void setToState(TransferIntraWalletUsersWrapper bitcoinTransaction, TransactionState status) throws CantUpdateRecordException, TransferIntraWalletUsersInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable transactionTable = this.database.getTable(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TABLE_NAME);
        DatabaseTableRecord recordToUpdate = getByPrimaryKey(bitcoinTransaction.getTransactionId());

        recordToUpdate.setStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_STATUS_COLUMN_NAME, status.getCode());

        transactionTable.addStringFilter(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_ID_COLUMN_NAME, bitcoinTransaction.getTransactionId().toString(), DatabaseFilterType.EQUAL);
        transactionTable.updateRecord(recordToUpdate);
    }


    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, TransferIntraWalletUsersInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TABLE_NAME);

        transactionTable.addStringFilter(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();

        List<DatabaseTableRecord> records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new TransferIntraWalletUsersInconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        transactionTable.clearAllFilters();

        return records.get(0);
    }

    public List<TransferIntraWalletUsersWrapper> getAllInState(TransactionState transactionState) throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable transactionTable = this.database.getTable(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TABLE_NAME);
        transactionTable.addStringFilter(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_STATUS_COLUMN_NAME, transactionState.getCode(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        List<DatabaseTableRecord> records = transactionTable.getRecords();
        transactionTable.clearAllFilters();

        return mapConvertToBT(records);
    }


    public TransferIntraWalletUsersWrapper getTransaction(UUID txId) throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable transactionTable = this.database.getTable(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TABLE_NAME);
        transactionTable.addUUIDFilter(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_ID_COLUMN_NAME, txId, DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        List<DatabaseTableRecord> records = transactionTable.getRecords();
        transactionTable.clearAllFilters();

        return mapConvertToBT(records).get(0);
    }


    private TransferIntraWalletUsersWrapper convertToBT(DatabaseTableRecord record) throws InvalidParameterException {

        UUID transactionId = record.getUUIDValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_ID_COLUMN_NAME);
        String transactionHash = record.getStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_HASH_COLUMN_NAME);
        long amount = record.getLongValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_CRYPTO_AMOUNT_COLUMN_NAME);
        TransactionState state = TransactionState.getByCode(record.getStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_STATUS_COLUMN_NAME));
        long timestamp = record.getLongValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TIMESTAMP_COLUMN_NAME);
        String memo = record.getStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_DESCRIPTION_COLUMN_NAME);
        Actors actorType = Actors.getByCode(record.getStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_ACTOR_TYPE_COLUMN_NAME));
        ReferenceWallet reference_wallet_sending = ReferenceWallet.getByCode(record.getStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_REFERENCE_TYPE_SENDING_COLUMN_NAME));
        ReferenceWallet reference_wallet_receiving = ReferenceWallet.getByCode(record.getStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_REFERENCE_TYPE_RECEIVING_COLUMN_NAME));
        String wallet_public_key_sending = record.getStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_PUBLIC_KEY_SENDING_COLUMN_NAME);
        String wallet_public_key_receiving = record.getStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_PUBLIC_KEY_RECEIVING_COLUMN_NAME);

        BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.getByCode(record.getStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_RUNNING_NETWORK_TYPE));

        return new TransferIntraWalletUsersWrapper(transactionId, transactionHash, amount, state, memo, timestamp, actorType, reference_wallet_sending, reference_wallet_receiving, wallet_public_key_sending, wallet_public_key_receiving, blockchainNetworkType);
    }

    // Apply convertToBT to all the elements in a list
    private List<TransferIntraWalletUsersWrapper> mapConvertToBT(List<DatabaseTableRecord> transactions) throws InvalidParameterException {
        List<TransferIntraWalletUsersWrapper> bitcoinTransactionList = new ArrayList<>();

        for (DatabaseTableRecord record : transactions)
            bitcoinTransactionList.add(convertToBT(record));

        return bitcoinTransactionList;
    }

    /**
     * Gets the transaction Hash of the specified transaction
     *
     * @param transactionId
     * @return
     * @throws TransferIntraWalletUsersCantGetTransactionHashException
     */
    public String getSendCryptoTransactionHash(UUID transactionId) throws TransferIntraWalletUsersCantGetTransactionHashException {
        try {
            DatabaseTable transactionTable = this.database.getTable(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TABLE_NAME);
            transactionTable.addUUIDFilter(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);

            transactionTable.loadToMemory();
            List<DatabaseTableRecord> records = transactionTable.getRecords();
            transactionTable.clearAllFilters();

            if (records.size() != 0)
                return records.get(0).getStringValue(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_HASH_COLUMN_NAME);
            else
                return null;
        } catch (CantLoadTableToMemoryException e) {
            throw new TransferIntraWalletUsersCantGetTransactionHashException("There was an error getting the transaction hash of the transaction.", e, null, "database issue");
        }

    }

}
