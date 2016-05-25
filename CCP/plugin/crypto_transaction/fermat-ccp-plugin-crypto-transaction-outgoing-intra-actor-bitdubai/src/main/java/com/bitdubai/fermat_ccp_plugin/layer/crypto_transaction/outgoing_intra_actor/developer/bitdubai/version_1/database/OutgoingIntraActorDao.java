package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
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
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetCryptoStatusException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.enums.TransactionState;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorDaoException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionHashException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionsException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantInsertRecordException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantSetTranactionHashException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorInconsistentTableStateException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.util.OutgoingIntraActorTransactionWrapper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.09.21..
 */
public class OutgoingIntraActorDao {

    private Database             database;
    private ErrorManager         errorManager;
    private PluginDatabaseSystem pluginDatabaseSystem;

    public OutgoingIntraActorDao(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem) {
        this.errorManager         = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public void initialize(UUID pluginId) throws CantInitializeOutgoingIntraActorDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {

            OutgoingIntraActorTransactionDatabaseFactory databaseFactory = new OutgoingIntraActorTransactionDatabaseFactory(this.pluginDatabaseSystem);
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            try {
                this.database = databaseFactory.createDatabase(pluginId, OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeOutgoingIntraActorDaoException("I couldn't create the database", cantCreateDatabaseException, "Database Name: " + OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_DATABASE_NAME, "");
            } catch (Exception exception) {
                throw new CantInitializeOutgoingIntraActorDaoException(CantInitializeOutgoingIntraActorDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
            }

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeOutgoingIntraActorDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_DATABASE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeOutgoingIntraActorDaoException(CantInitializeOutgoingIntraActorDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }


    public void registerNewTransaction( UUID            transactionId,
                                        UUID            requestId,
                                        String          walletPublicKey,
                                        CryptoAddress   destinationAddress,
                                        long            cryptoAmount,
                                        String          op_Return,
                                        String          notes,
                                        String          deliveredByActorPublicKey,
                                        Actors          deliveredByActorType,
                                        String          deliveredToActorPublicKey,
                                        Actors          deliveredToActorType,
                                        ReferenceWallet referenceWallet,
                                        boolean sameDevice,
                                        BlockchainNetworkType blockchainNetworkType) throws OutgoingIntraActorCantInsertRecordException {
        try {
            DatabaseTable       transactionTable = this.database.getTable(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TABLE_NAME);
          //check transaction Id not exist
            if(!isTransactionInTable(transactionId)) {
                DatabaseTableRecord recordToInsert = transactionTable.getEmptyRecord();
                loadRecordAsNew(recordToInsert, transactionId, requestId, walletPublicKey, destinationAddress, cryptoAmount, op_Return, notes, deliveredByActorPublicKey, deliveredByActorType, deliveredToActorPublicKey, deliveredToActorType, referenceWallet, sameDevice, blockchainNetworkType);
                transactionTable.insertRecord(recordToInsert);
            }
        } catch (CantInsertRecordException e) {
            throw new OutgoingIntraActorCantInsertRecordException("An exception happened",e,"","");
        } catch (Exception exception) {
            throw new OutgoingIntraActorCantInsertRecordException(OutgoingIntraActorCantInsertRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public List<OutgoingIntraActorTransactionWrapper> getNewTransactions() throws OutgoingIntraActorCantGetTransactionsException {
        try {
            return getAllInState(TransactionState.NEW);
        } catch (CantLoadTableToMemoryException | InvalidParameterException exception) {
            throw new OutgoingIntraActorCantGetTransactionsException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new OutgoingIntraActorCantGetTransactionsException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public List<OutgoingIntraActorTransactionWrapper> getPersistedInAvailable() throws OutgoingIntraActorCantGetTransactionsException {
        try {
            return getAllInState(TransactionState.PERSISTED_IN_AVAILABLE);
        } catch (CantLoadTableToMemoryException | InvalidParameterException exception) {
            throw new OutgoingIntraActorCantGetTransactionsException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new OutgoingIntraActorCantGetTransactionsException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public List<OutgoingIntraActorTransactionWrapper> getSentToCryptoVaultTransactions() throws OutgoingIntraActorCantGetTransactionsException {
        try {
            return getAllInState(TransactionState.SENT_TO_CRYPTO_VOULT);
        } catch (CantLoadTableToMemoryException | InvalidParameterException exception) {
            throw new OutgoingIntraActorCantGetTransactionsException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new OutgoingIntraActorCantGetTransactionsException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public void cancelTransaction(OutgoingIntraActorTransactionWrapper bitcoinTransaction) throws OutgoingIntraActorCantCancelTransactionException {
        try {
            setToState(bitcoinTransaction, TransactionState.CANCELED);
        } catch (CantUpdateRecordException | OutgoingIntraActorInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new OutgoingIntraActorCantCancelTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new OutgoingIntraActorCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public void setToNew(OutgoingIntraActorTransactionWrapper bitcoinTransaction) throws OutgoingIntraActorCantCancelTransactionException {
        try {
            setToState(bitcoinTransaction, TransactionState.NEW);
        } catch (CantUpdateRecordException | OutgoingIntraActorInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new OutgoingIntraActorCantCancelTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new OutgoingIntraActorCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public void setToPIA(OutgoingIntraActorTransactionWrapper bitcoinTransaction) throws OutgoingIntraActorCantCancelTransactionException {
        try {
            setToState(bitcoinTransaction, TransactionState.PERSISTED_IN_AVAILABLE);
        } catch (CantUpdateRecordException | OutgoingIntraActorInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new OutgoingIntraActorCantCancelTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new OutgoingIntraActorCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public void setToPIW(OutgoingIntraActorTransactionWrapper bitcoinTransaction) throws OutgoingIntraActorCantCancelTransactionException {
        try {
            setToState(bitcoinTransaction, TransactionState.PERSISTED_IN_WALLET);
        } catch (CantUpdateRecordException | OutgoingIntraActorInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new OutgoingIntraActorCantCancelTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new OutgoingIntraActorCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    public void setToSTCV(OutgoingIntraActorTransactionWrapper bitcoinTransaction) throws OutgoingIntraActorCantCancelTransactionException {
        try {
            setToState(bitcoinTransaction, TransactionState.SENT_TO_CRYPTO_VOULT);
        } catch (CantUpdateRecordException | OutgoingIntraActorInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new OutgoingIntraActorCantCancelTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new OutgoingIntraActorCantCancelTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }
    
    public void setTransactionHash(OutgoingIntraActorTransactionWrapper bitcoinTransaction, String hash) throws OutgoingIntraActorCantSetTranactionHashException {
        try {
            DatabaseTable       transactionTable = this.database.getTable(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TABLE_NAME);
            DatabaseTableRecord recordToUpdate   = getByPrimaryKey(bitcoinTransaction.getTransactionId());
            recordToUpdate.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_HASH_COLUMN_NAME, hash);
            transactionTable.addStringFilter(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_ID_COLUMN_NAME, bitcoinTransaction.getTransactionId().toString(), DatabaseFilterType.EQUAL);

            transactionTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException | OutgoingIntraActorInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw new OutgoingIntraActorCantSetTranactionHashException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new OutgoingIntraActorCantSetTranactionHashException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }


    private void loadRecordAsNew(DatabaseTableRecord databaseTableRecord,
                                 UUID                trxId,
                                 UUID                requestId,
                                 String              walletPublicKey,
                                 CryptoAddress       destinationAddress,
                                 long                cryptoAmount,
                                 String              op_Return,
                                 String              notes,
                                 String              deliveredByActorPublicKey,
                                 Actors              deliveredByActorType,
                                 String              deliveredToActorPublicKey,
                                 Actors              deliveredToActorType,
                                 ReferenceWallet referenceWallet,
                                 boolean sameDevice,
                                 BlockchainNetworkType blockchainNetworkType) {

        UUID transactionId = trxId;

        databaseTableRecord.setUUIDValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_ID_COLUMN_NAME, transactionId);
        if(requestId != null)
            databaseTableRecord.setUUIDValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_REQUEST_ID_COLUMN_NAME, requestId);

        databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_WALLET_ID_TO_DEBIT_FROM_COLUMN_NAME, walletPublicKey);

        // TODO: This will be completed when the vault gives it to us
        databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_HASH_COLUMN_NAME, "UNKNOWN YET");

        // TODO: This need to be completed in the future
        databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ADDRESS_FROM_COLUMN_NAME, "MY_ADDRESS");
        databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ADDRESS_TO_COLUMN_NAME, destinationAddress.getAddress());
        databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_CURRENCY_COLUMN_NAME, destinationAddress.getCryptoCurrency().getCode());
        databaseTableRecord.setLongValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);
        if (op_Return != null)
            databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_OP_RETURN_COLUMN_NAME, op_Return);
        databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_STATUS_COLUMN_NAME, TransactionState.NEW.getCode());

        // TODO: This have to be changed for the tinestamp when the network recognize the transaction

        // eze te saco la division para obtener el timestamp bien
        databaseTableRecord.setLongValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());
        databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_DESCRIPTION_COLUMN_NAME, notes);
        databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_STATUS_COLUMN_NAME, CryptoStatus.PENDING_SUBMIT.getCode());

        databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_FROM_PUBLIC_KEY_COLUMN_NAME, deliveredByActorPublicKey);
        databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_FROM_TYPE_COLUMN_NAME, deliveredByActorType.getCode());
        databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_TO_PUBLIC_KEY_COLUMN_NAME, deliveredToActorPublicKey);
        databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_TO_TYPE_COLUMN_NAME, deliveredToActorType.getCode());
        databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_SAME_DEVICE_COLUMN_NAME, String.valueOf(sameDevice));
        databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_WALLET_REFERENCE_TYPE_COLUMN_NAME, referenceWallet.getCode());
        databaseTableRecord.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_RUNNING_NETWORK_TYPE, blockchainNetworkType.getCode());

    }


    private void setToState(OutgoingIntraActorTransactionWrapper bitcoinTransaction, TransactionState status) throws CantUpdateRecordException, OutgoingIntraActorInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable       transactionTable = this.database.getTable(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TABLE_NAME);
        DatabaseTableRecord recordToUpdate   = getByPrimaryKey(bitcoinTransaction.getTransactionId());

        recordToUpdate.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_STATUS_COLUMN_NAME, status.getCode());

        transactionTable.addStringFilter(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_ID_COLUMN_NAME, bitcoinTransaction.getTransactionId().toString(), DatabaseFilterType.EQUAL);
        transactionTable.updateRecord(recordToUpdate);
    }


    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, OutgoingIntraActorInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TABLE_NAME);

        transactionTable.addStringFilter(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();

        List<DatabaseTableRecord> records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new OutgoingIntraActorInconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        transactionTable.clearAllFilters();

        return records.get(0);
    }

    private List<OutgoingIntraActorTransactionWrapper> getAllInState(TransactionState transactionState) throws CantLoadTableToMemoryException, InvalidParameterException {

        DatabaseTable transactionTable = this.database.getTable(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TABLE_NAME);
        transactionTable.addStringFilter(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_STATUS_COLUMN_NAME, transactionState.getCode(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        List<DatabaseTableRecord> records = transactionTable.getRecords();
        transactionTable.clearAllFilters();

        return mapConvertToBT(records);
    }

    public void setToCryptoStatus(OutgoingIntraActorTransactionWrapper transactionWrapper, CryptoStatus cryptoStatus) throws CantUpdateRecordException, CantLoadTableToMemoryException, OutgoingIntraActorInconsistentTableStateException {
        try {
            DatabaseTable       transactionTable = this.database.getTable(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TABLE_NAME);
            DatabaseTableRecord recordToUpdate   = getByPrimaryKey(transactionWrapper.getTransactionId());

            recordToUpdate.setStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());
            transactionTable.addStringFilter(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_ID_COLUMN_NAME, transactionWrapper.getTransactionId().toString(), DatabaseFilterType.EQUAL);

            transactionTable.updateRecord(recordToUpdate);

        } catch (CantUpdateRecordException | OutgoingIntraActorInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }
    
    private OutgoingIntraActorTransactionWrapper convertToBT(DatabaseTableRecord record) throws InvalidParameterException {
        OutgoingIntraActorTransactionWrapper bitcoinTransaction = new OutgoingIntraActorTransactionWrapper();
        boolean sameDevice = Boolean.valueOf(record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_SAME_DEVICE_COLUMN_NAME));
        String           walletPublicKey    = record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_WALLET_ID_TO_DEBIT_FROM_COLUMN_NAME);
        UUID             transactionId      = record.getUUIDValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_ID_COLUMN_NAME);
        String           transactionHash    = record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_HASH_COLUMN_NAME);
        long             amount             = record.getLongValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_AMOUNT_COLUMN_NAME);
        String           op_Return          = null;
        UUID            requestId = null;

        if (record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_OP_RETURN_COLUMN_NAME) != null){
            op_Return          = record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_OP_RETURN_COLUMN_NAME);
        }

        if (record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_REQUEST_ID_COLUMN_NAME)!= null){
            requestId          = record.getUUIDValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_REQUEST_ID_COLUMN_NAME);
        }

        TransactionState state              = TransactionState.getByCode(record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_STATUS_COLUMN_NAME));
        long             timestamp          = record.getLongValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TIMESTAMP_COLUMN_NAME);
        String           memo               = record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_DESCRIPTION_COLUMN_NAME);
        CryptoStatus     cryptoStatus       = CryptoStatus.getByCode(record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_STATUS_COLUMN_NAME));
        Actors           actorFromType      = Actors.getByCode(record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_FROM_TYPE_COLUMN_NAME));
        Actors           actorToType        = Actors.getByCode(record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_TO_TYPE_COLUMN_NAME));
        String           actorFromPublicKey = record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_FROM_PUBLIC_KEY_COLUMN_NAME);
        String           actorToPublicKey   = record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_TO_PUBLIC_KEY_COLUMN_NAME);
        ReferenceWallet  referenceWallet    = ReferenceWallet.getByCode(record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_WALLET_REFERENCE_TYPE_COLUMN_NAME));
        CryptoAddress    addressFrom        = new CryptoAddress(
                                                    record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ADDRESS_FROM_COLUMN_NAME),
                                                    CryptoCurrency.getByCode(record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_CURRENCY_COLUMN_NAME))
                                                  );
        CryptoAddress    addressTo          = new CryptoAddress(
                                                    record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ADDRESS_TO_COLUMN_NAME),
                                                    CryptoCurrency.getByCode(record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_CURRENCY_COLUMN_NAME))
                                                  );

        BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.getByCode(record.getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_RUNNING_NETWORK_TYPE));


        bitcoinTransaction.setWalletPublicKey(walletPublicKey);
        bitcoinTransaction.setIdTransaction(transactionId);
        bitcoinTransaction.setIdRequest(requestId);
        bitcoinTransaction.setTransactionHash(transactionHash);
        bitcoinTransaction.setAddressFrom(addressFrom);
        bitcoinTransaction.setAddressTo(addressTo);
        bitcoinTransaction.setAmount(amount);
        bitcoinTransaction.setOp_Return(op_Return);
        bitcoinTransaction.setState(state);
        bitcoinTransaction.setTimestamp(timestamp);
        bitcoinTransaction.setMemo(memo);
        bitcoinTransaction.setCryptoStatus(cryptoStatus);
        bitcoinTransaction.setActorFromPublicKey(actorFromPublicKey);
        bitcoinTransaction.setActorFromType(actorFromType);
        bitcoinTransaction.setActorToPublicKey(actorToPublicKey);
        bitcoinTransaction.setActorToType(actorToType);
        bitcoinTransaction.setReferenceWallet(referenceWallet);
        bitcoinTransaction.setSameDevice(sameDevice);
        bitcoinTransaction.setBlockchainNetworkType(blockchainNetworkType);


        return bitcoinTransaction;
    }

    // Apply convertToBT to all the elements in a list
    private List<OutgoingIntraActorTransactionWrapper> mapConvertToBT(List<DatabaseTableRecord> transactions) throws InvalidParameterException {
        List<OutgoingIntraActorTransactionWrapper> bitcoinTransactionList = new ArrayList<>();

        for (DatabaseTableRecord record : transactions)
            bitcoinTransactionList.add(convertToBT(record));

        return bitcoinTransactionList;
    }

    public CryptoStatus getCryptoStatus(String transactionHash) throws OutgoingIntraActorCantGetCryptoStatusException {
        try {
            DatabaseTable transactionTable = this.database.getTable(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TABLE_NAME);
            transactionTable.addStringFilter(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_HASH_COLUMN_NAME, transactionHash, DatabaseFilterType.EQUAL);
            transactionTable.loadToMemory();
            List<DatabaseTableRecord> records = transactionTable.getRecords();
            transactionTable.clearAllFilters();

            return CryptoStatus.getByCode(records.get(0).getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_STATUS_COLUMN_NAME));
        } catch (InvalidParameterException | CantLoadTableToMemoryException e) {
            throw new OutgoingIntraActorCantGetCryptoStatusException("An exception happened",e,"","");
        } catch (Exception e) {
            throw new OutgoingIntraActorCantGetCryptoStatusException("An unexpected exception happened",FermatException.wrapException(e),"","");
        }
    }

    /**
     * Gets the transaction Hash of the specified transaction
     * @param transactionId
     * @return
     * @throws OutgoingIntraActorCantGetTransactionHashException
     */
    public String getSendCryptoTransactionHash(UUID transactionId) throws OutgoingIntraActorCantGetTransactionHashException {
        try{
            DatabaseTable transactionTable = this.database.getTable(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TABLE_NAME);
            transactionTable.addUUIDFilter(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);

            transactionTable.loadToMemory();
            List<DatabaseTableRecord> records = transactionTable.getRecords();
            transactionTable.clearAllFilters();

            if (records.size() != 0)
                return records.get(0).getStringValue(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_HASH_COLUMN_NAME);
            else
                return null;
        } catch (CantLoadTableToMemoryException e) {
            throw new OutgoingIntraActorCantGetTransactionHashException("There was an error getting the transaction hash of the transaction.", e, null, "database issue");
        }

    }

    private boolean isTransactionInTable(final UUID transactionId) throws CantLoadTableToMemoryException {
        DatabaseTable       transactionTable = this.database.getTable(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TABLE_NAME);

        transactionTable.addUUIDFilter(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        return !transactionTable.getRecords().isEmpty();
    }
}
