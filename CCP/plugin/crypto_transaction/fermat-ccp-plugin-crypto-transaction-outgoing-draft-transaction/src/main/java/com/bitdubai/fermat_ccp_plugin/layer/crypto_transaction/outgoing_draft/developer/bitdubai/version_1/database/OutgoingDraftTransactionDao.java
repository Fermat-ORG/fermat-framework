package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
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
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetCryptoStatusException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.enums.TransactionState;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionHashException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantInsertRecordException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.util.OutgoingDraftTransactionWrapper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by eze on 2015.09.21..
 */
public class OutgoingDraftTransactionDao {

    private Database database;
    private ErrorManager errorManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private CryptoVaultManager cryptoVaultManager;

    public OutgoingDraftTransactionDao(ErrorManager errorManager,
                                       PluginDatabaseSystem pluginDatabaseSystem,
                                       CryptoVaultManager cryptoVaultManager) {
        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.cryptoVaultManager = cryptoVaultManager;
    }

    public void initialize(UUID pluginId) throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {

            OutgoingDraftTransactionDatabaseFactory databaseFactory = new OutgoingDraftTransactionDatabaseFactory(this.pluginDatabaseSystem);
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            try {
                this.database = databaseFactory.createDatabase(pluginId, OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorDaoException("I couldn't create the database", cantCreateDatabaseException, "Database Name: " + OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_DATABASE_NAME, "");
            } catch (Exception exception) {
                throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorDaoException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
            }

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_DATABASE_NAME, "");
        } catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorDaoException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }


    public void registerNewTransaction(UUID transactionId,
                                       String txHash,
                                       String walletPublicKey,
                                       CryptoAddress destinationAddress,
                                       long cryptoAmount,
                                       String op_Return,
                                       String notes,
                                       String deliveredByActorPublicKey,
                                       Actors deliveredByActorType,
                                       String deliveredToActorPublicKey,
                                       Actors deliveredToActorType,
                                       ReferenceWallet referenceWallet,
                                       boolean sameDevice,
                                       BlockchainNetworkType blockchainNetworkType) throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantInsertRecordException {
        try {
            DatabaseTable transactionTable = this.database.getTable(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TABLE_NAME);
            DatabaseTableRecord recordToInsert = transactionTable.getEmptyRecord();
            loadRecordAsNew(recordToInsert, transactionId, txHash, walletPublicKey, destinationAddress, cryptoAmount, op_Return, notes, deliveredByActorPublicKey, deliveredByActorType, deliveredToActorPublicKey, deliveredToActorType, referenceWallet, sameDevice, blockchainNetworkType);
            transactionTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantInsertRecordException("An exception happened", e, "", "");
        } catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantInsertRecordException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantInsertRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public void updateTxHash(UUID transactionId,
                             String newHash) throws OutgoingIntraActorCantInsertRecordException {
        try {
            DatabaseTable transactionTable = this.database.getTable(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TABLE_NAME);
            transactionTable.addStringFilter(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_ID_COLUMN_NAME, transactionId.toString(), DatabaseFilterType.EQUAL);
            transactionTable.loadToMemory();

            if (transactionTable.getRecords().isEmpty()) throw new RecordsNotFoundException();

            DatabaseTableRecord record = transactionTable.getRecords().get(0);
            record.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_HASH_COLUMN_NAME, newHash);
            transactionTable.updateRecord(record);
        } catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantInsertRecordException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantInsertRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public List<OutgoingDraftTransactionWrapper> getNewTransactions() throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionsException {
        try {
            return getAllInState(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.enums.TransactionState.NEW);
        } catch (CantLoadTableToMemoryException | InvalidParameterException exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionsException("An exception happened", exception, "", "");
        } catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionsException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public List<OutgoingDraftTransactionWrapper> getSentToCryptoVaultTransactions() throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionsException {
        try {
            return getAllInState(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.enums.TransactionState.SENT_TO_CRYPTO_VOULT);
        } catch (CantLoadTableToMemoryException | InvalidParameterException exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionsException("An exception happened", exception, "", "");
        } catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionsException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public List<OutgoingDraftTransactionWrapper> getSentToCryptoVaultTransactionsAndNotRead() throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionsException {
        try {
            return getAllInStateAndNotRead(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.enums.TransactionState.SENT_TO_CRYPTO_VOULT);
        } catch (CantLoadTableToMemoryException | InvalidParameterException exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionsException("An exception happened", exception, "", "");
        } catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionsException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public void cancelTransaction(OutgoingDraftTransactionWrapper bitcoinTransaction) throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException {
        try {
            setToState(bitcoinTransaction, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.enums.TransactionState.CANCELED);
        } catch (CantUpdateRecordException | com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException("An exception happened", exception, "", "");
        } catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public void setToNew(OutgoingDraftTransactionWrapper bitcoinTransaction) throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException {
        try {
            setToState(bitcoinTransaction, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.enums.TransactionState.NEW);
        } catch (CantUpdateRecordException | com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException("An exception happened", exception, "", "");
        } catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }


    public void setToDIW(OutgoingDraftTransactionWrapper bitcoinTransaction) throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException {
        try {
            setToState(bitcoinTransaction, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.enums.TransactionState.DEBITED_IN_WALLET);
        } catch (CantUpdateRecordException | com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException("An exception happened", exception, "", "");
        } catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public void setToSTCV(OutgoingDraftTransactionWrapper bitcoinTransaction) throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException {
        try {
            setToState(bitcoinTransaction, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.enums.TransactionState.SENT_TO_CRYPTO_VOULT);
        } catch (CantUpdateRecordException | com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException("An exception happened", exception, "", "");
        } catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }


    public void setToSS(OutgoingDraftTransactionWrapper bitcoinTransaction) throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException {
        try {
            setToState(bitcoinTransaction, TransactionState.SUCCESSFUL_SIG);
        } catch (CantUpdateRecordException | com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException("An exception happened", exception, "", "");
        } catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public void setToCompleted(OutgoingDraftTransactionWrapper bitcoinTransaction) throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException {
        try {
            setToState(bitcoinTransaction, TransactionState.COMPLETED);
        } catch (CantUpdateRecordException | com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException("An exception happened", exception, "", "");
        } catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }
    private void loadRecordAsNew(DatabaseTableRecord databaseTableRecord,
                                 UUID trxId,
                                 String txHash,
                                 String walletPublicKey,
                                 CryptoAddress destinationAddress,
                                 long cryptoAmount,
                                 String op_Return,
                                 String notes,
                                 String deliveredByActorPublicKey,
                                 Actors deliveredByActorType,
                                 String deliveredToActorPublicKey,
                                 Actors deliveredToActorType,
                                 ReferenceWallet referenceWallet,
                                 boolean sameDevice,
                                 BlockchainNetworkType blockchainNetworkType) {

        databaseTableRecord.setUUIDValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_ID_COLUMN_NAME, trxId);
//        if(requestId != null)
//            databaseTableRecord.setUUIDValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_DRAFT_REQUEST_ID_COLUMN_NAME, requestId);

        databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_WALLET_ID_TO_DEBIT_FROM_COLUMN_NAME, walletPublicKey);

        // TODO: This will be completed when the vault gives it to us
        databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_HASH_COLUMN_NAME, txHash);

        // TODO: This need to be completed in the future
        databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_ADDRESS_FROM_COLUMN_NAME, "MY_ADDRESS");
        databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_ADDRESS_TO_COLUMN_NAME, destinationAddress.getAddress());
        databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_CRYPTO_CURRENCY_COLUMN_NAME, destinationAddress.getCryptoCurrency().getCode());
        databaseTableRecord.setLongValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);
        if (op_Return != null)
            databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_OP_RETURN_COLUMN_NAME, op_Return);
        databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_STATUS_COLUMN_NAME, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.enums.TransactionState.NEW.getCode());

        // TODO: This have to be changed for the tinestamp when the network recognize the transaction

        // eze te saco la division para obtener el timestamp bien
        databaseTableRecord.setLongValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());
        databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_DESCRIPTION_COLUMN_NAME, notes);
        databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_CRYPTO_STATUS_COLUMN_NAME, CryptoStatus.PENDING_SUBMIT.getCode());

        databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_ACTOR_FROM_PUBLIC_KEY_COLUMN_NAME, deliveredByActorPublicKey);
        databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_ACTOR_FROM_TYPE_COLUMN_NAME, deliveredByActorType.getCode());
        databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_ACTOR_TO_PUBLIC_KEY_COLUMN_NAME, deliveredToActorPublicKey);
        databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_ACTOR_TO_TYPE_COLUMN_NAME, deliveredToActorType.getCode());
        databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_SAME_DEVICE_COLUMN_NAME, String.valueOf(sameDevice));
        databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_WALLET_REFERENCE_TYPE_COLUMN_NAME, referenceWallet.getCode());
        databaseTableRecord.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_RUNNING_NETWORK_TYPE, blockchainNetworkType.getCode());

    }


    private void setToState(OutgoingDraftTransactionWrapper bitcoinTransaction, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.enums.TransactionState status) throws CantUpdateRecordException, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable transactionTable = this.database.getTable(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TABLE_NAME);
        DatabaseTableRecord recordToUpdate = getByPrimaryKey(bitcoinTransaction.getRequestId());

        recordToUpdate.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_STATUS_COLUMN_NAME, status.getCode());

        transactionTable.addStringFilter(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_ID_COLUMN_NAME, bitcoinTransaction.getRequestId().toString(), DatabaseFilterType.EQUAL);
        transactionTable.updateRecord(recordToUpdate);
    }


    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TABLE_NAME);

        transactionTable.addStringFilter(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();

        List<DatabaseTableRecord> records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorInconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        transactionTable.clearAllFilters();

        return records.get(0);
    }

    public List<OutgoingDraftTransactionWrapper> getAllInState(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.enums.TransactionState transactionState) throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable transactionTable = this.database.getTable(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TABLE_NAME);
        transactionTable.addStringFilter(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_STATUS_COLUMN_NAME, transactionState.getCode(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        List<DatabaseTableRecord> records = transactionTable.getRecords();
        transactionTable.clearAllFilters();

        return mapConvertToBT(records);
    }

    public List<OutgoingDraftTransactionWrapper> getAllInStateAndNotRead(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.enums.TransactionState transactionState) throws CantLoadTableToMemoryException, InvalidParameterException {

        DatabaseTable transactionTable = this.database.getTable(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TABLE_NAME);
        transactionTable.addStringFilter(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_STATUS_COLUMN_NAME, transactionState.getCode(), DatabaseFilterType.EQUAL);
        transactionTable.addStringFilter(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_MARK_COLUMN_NAME, Boolean.FALSE.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        List<DatabaseTableRecord> records = transactionTable.getRecords();
        transactionTable.clearAllFilters();

        return mapConvertToBT(records);
    }

    public OutgoingDraftTransactionWrapper getTransaction(UUID txId) throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable transactionTable = this.database.getTable(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TABLE_NAME);
        transactionTable.addUUIDFilter(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_ID_COLUMN_NAME, txId, DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        List<DatabaseTableRecord> records = transactionTable.getRecords();
        transactionTable.clearAllFilters();

        return mapConvertToBT(records).get(0);
    }

    //TODO: mejorar, el plugin de mierda de db es un asco
    public void markReadTransaction(UUID requestId) {
        try {
            DatabaseTable transactionTable = this.database.getTable(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TABLE_NAME);
            transactionTable.addUUIDFilter(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);
            transactionTable.loadToMemory();
            List<DatabaseTableRecord> records = transactionTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);
                //set new record values
                record.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_MARK_COLUMN_NAME, Boolean.TRUE.toString());

                transactionTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        } catch (CantUpdateRecordException e) {
            e.printStackTrace();
        }


    }


    public void setToCryptoStatus(OutgoingDraftTransactionWrapper transactionWrapper, CryptoStatus cryptoStatus) throws CantUpdateRecordException, CantLoadTableToMemoryException, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorInconsistentTableStateException {
        try {
            DatabaseTable transactionTable = this.database.getTable(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = getByPrimaryKey(transactionWrapper.getRequestId());

            recordToUpdate.setStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());
            transactionTable.addStringFilter(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_ID_COLUMN_NAME, transactionWrapper.getRequestId().toString(), DatabaseFilterType.EQUAL);

            transactionTable.updateRecord(recordToUpdate);

        } catch (CantUpdateRecordException | com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private OutgoingDraftTransactionWrapper convertToBT(DatabaseTableRecord record) throws InvalidParameterException {
//        boolean sameDevice = Boolean.valueOf(record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_SAME_DEVICE_COLUMN_NAME));
        String walletPublicKey = record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_WALLET_ID_TO_DEBIT_FROM_COLUMN_NAME);
        UUID transactionId = record.getUUIDValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_ID_COLUMN_NAME);
        String transactionHash = record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_HASH_COLUMN_NAME);
        long amount = record.getLongValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_CRYPTO_AMOUNT_COLUMN_NAME);
//        String op_Return = record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_OP_RETURN_COLUMN_NAME);
        com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.enums.TransactionState state = com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.enums.TransactionState.getByCode(record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_STATUS_COLUMN_NAME));
        long timestamp = record.getLongValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TIMESTAMP_COLUMN_NAME);
        String memo = record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_DESCRIPTION_COLUMN_NAME);
//        CryptoStatus cryptoStatus = CryptoStatus.getByCode(record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_CRYPTO_STATUS_COLUMN_NAME));
        Actors actorFromType = Actors.getByCode(record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_ACTOR_FROM_TYPE_COLUMN_NAME));
        Actors actorToType = Actors.getByCode(record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_ACTOR_TO_TYPE_COLUMN_NAME));
        String actorFromPublicKey = record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_ACTOR_FROM_PUBLIC_KEY_COLUMN_NAME);
        String actorToPublicKey = record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_ACTOR_TO_PUBLIC_KEY_COLUMN_NAME);
        ReferenceWallet referenceWallet = ReferenceWallet.getByCode(record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_WALLET_REFERENCE_TYPE_COLUMN_NAME));
        String addressFrom = record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_ADDRESS_FROM_COLUMN_NAME);
        String addressTo = record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_ADDRESS_TO_COLUMN_NAME);
//        CryptoAddress cryptoAddressFrom = null;
//        if (addressFrom != null) {
//            cryptoAddressFrom = new CryptoAddress(
//                    addressFrom,
//                    CryptoCurrency.getByCode(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_CRYPTO_CURRENCY_COLUMN_NAME));
//        }
        CryptoAddress cryptoAddressTo = null;
        if (addressFrom != null) {
            cryptoAddressTo = new CryptoAddress(
                    addressTo,
                    CryptoCurrency.getByCode(record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_CRYPTO_CURRENCY_COLUMN_NAME)));
        }
        BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.getByCode(record.getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_RUNNING_NETWORK_TYPE));

        return new OutgoingDraftTransactionWrapper(transactionId, walletPublicKey, amount, cryptoAddressTo, referenceWallet, blockchainNetworkType, actorFromPublicKey, actorToPublicKey, actorFromType, actorToType, memo, timestamp, transactionHash);
    }

    // Apply convertToBT to all the elements in a list
    private List<OutgoingDraftTransactionWrapper> mapConvertToBT(List<DatabaseTableRecord> transactions) throws InvalidParameterException {
        List<OutgoingDraftTransactionWrapper> bitcoinTransactionList = new ArrayList<>();

        for (DatabaseTableRecord record : transactions)
            bitcoinTransactionList.add(convertToBT(record));

        return bitcoinTransactionList;
    }

    public CryptoStatus getCryptoStatus(String transactionHash) throws OutgoingIntraActorCantGetCryptoStatusException {
        try {
            DatabaseTable transactionTable = this.database.getTable(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TABLE_NAME);
            transactionTable.addStringFilter(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_HASH_COLUMN_NAME, transactionHash, DatabaseFilterType.EQUAL);
            transactionTable.loadToMemory();
            List<DatabaseTableRecord> records = transactionTable.getRecords();
            transactionTable.clearAllFilters();

            return CryptoStatus.getByCode(records.get(0).getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_CRYPTO_STATUS_COLUMN_NAME));
        } catch (InvalidParameterException | CantLoadTableToMemoryException e) {
            throw new OutgoingIntraActorCantGetCryptoStatusException("An exception happened", e, "", "");
        } catch (Exception e) {
            throw new OutgoingIntraActorCantGetCryptoStatusException("An unexpected exception happened", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * Gets the transaction Hash of the specified transaction
     *
     * @param transactionId
     * @return
     * @throws OutgoingIntraActorCantGetTransactionHashException
     */
    public String getSendCryptoTransactionHash(UUID transactionId) throws OutgoingIntraActorCantGetTransactionHashException {
        try {
            DatabaseTable transactionTable = this.database.getTable(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TABLE_NAME);
            transactionTable.addUUIDFilter(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);

            transactionTable.loadToMemory();
            List<DatabaseTableRecord> records = transactionTable.getRecords();
            transactionTable.clearAllFilters();

            if (records.size() != 0)
                return records.get(0).getStringValue(OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_TRANSACTION_HASH_COLUMN_NAME);
            else
                return null;
        } catch (CantLoadTableToMemoryException e) {
            throw new OutgoingIntraActorCantGetTransactionHashException("There was an error getting the transaction hash of the transaction.", e, null, "database issue");
        }

    }


}
