package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionState;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.enums.TransactionStatus;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.InconsistentTableStateException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.util.TransactionWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.06.25..
 * Modified by lnacosta (laion.cj91@gmail.com) on 15/1062015.
 */
public class OutgoingExtraUserDao {

    private final ErrorManager         errorManager        ;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    private Database database;

    public OutgoingExtraUserDao(final ErrorManager         errorManager        ,
                                final PluginDatabaseSystem pluginDatabaseSystem,
                                final UUID                 pluginId            ) {

        this.errorManager         = errorManager        ;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    public void initialize() throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.CantInitializeDaoException {

        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {

            OutgoingExtraUserDatabaseFactory databaseFactory = new OutgoingExtraUserDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            try {
                this.database = databaseFactory.createDatabase(pluginId, OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.CantInitializeDaoException("I couldn't create the database", cantCreateDatabaseException, "Database Name: " + OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_DATABASE_NAME, "");
            } catch (Exception exception) {
                throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.CantInitializeDaoException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.CantInitializeDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
            }

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.CantInitializeDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_DATABASE_NAME, "");
        } catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.CantInitializeDaoException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.CantInitializeDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public void registerNewTransaction(String walletPublicKey,
                                       CryptoAddress destinationAddress,
                                       long cryptoAmount,
                                       String notes,
                                       String deliveredByActorPublicKey,
                                       Actors deliveredByActorType,
                                       String deliveredToActorPublicKey,
                                       Actors deliveredToActorType,
                                       BlockchainNetworkType blockchainNetworkType,
                                       CryptoCurrency cryptoCurrency, long fee, FeeOrigin feeOrigin) throws CantInsertRecordException {
        try {
            DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);

            DatabaseTableRecord recordToInsert = transactionTable.getEmptyRecord();

            loadRecordAsNew(recordToInsert, walletPublicKey, destinationAddress, cryptoAmount, notes, deliveredByActorPublicKey, deliveredByActorType, deliveredToActorPublicKey,
                            deliveredToActorType,blockchainNetworkType,cryptoCurrency,
                            fee,
                            feeOrigin);

            transactionTable.insertRecord(recordToInsert);
            database.closeDatabase();
        } catch (CantInsertRecordException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public List<TransactionWrapper> getNewTransactions() throws CantLoadTableToMemoryException, InvalidParameterException {
        try {
            return getAllInState(TransactionStatus.NEW);
        } catch (CantLoadTableToMemoryException | InvalidParameterException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public List<TransactionWrapper> getPersistedInAvailable() throws CantLoadTableToMemoryException, InvalidParameterException {

        try {
            return getAllInState(TransactionStatus.PERSISTED_IN_AVAILABLE);
        } catch (CantLoadTableToMemoryException | InvalidParameterException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public List<TransactionWrapper> getSentToCryptoVaultTransactions() throws CantLoadTableToMemoryException, InvalidParameterException {

        try {
            return getAllInState(TransactionStatus.SENT_TO_CRYPTO_VOULT);
        } catch (CantLoadTableToMemoryException | InvalidParameterException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public void cancelTransaction(TransactionWrapper bitcoinTransaction, String memo) throws CantUpdateRecordException, InconsistentTableStateException, CantLoadTableToMemoryException {

        try {
            setToStateWithMemo(
                    bitcoinTransaction,
                    TransactionStatus.CANCELED,
                    memo
            );

        } catch (CantUpdateRecordException | InconsistentTableStateException | CantLoadTableToMemoryException exception) {

            throw exception;
        } catch (Exception exception) {

            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public void setToPIA(TransactionWrapper bitcoinTransaction) throws CantUpdateRecordException, InconsistentTableStateException, CantLoadTableToMemoryException {
        try {
            setToState(bitcoinTransaction, TransactionStatus.PERSISTED_IN_AVAILABLE);
        } catch (CantUpdateRecordException | InconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public void setToPIW(TransactionWrapper bitcoinTransaction) throws CantUpdateRecordException, InconsistentTableStateException, CantLoadTableToMemoryException {
        try {
            setToState(bitcoinTransaction, TransactionStatus.PERSISTED_IN_WALLET);
        } catch (CantUpdateRecordException | InconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public void setToSTCV(TransactionWrapper bitcoinTransaction) throws CantUpdateRecordException, InconsistentTableStateException, CantLoadTableToMemoryException {
        try {
            setToState(bitcoinTransaction, TransactionStatus.SENT_TO_CRYPTO_VOULT);
        } catch (CantUpdateRecordException | InconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }


    public void setTransactionHash(final TransactionWrapper bitcoinTransaction,
                                   final String             hash              ) throws CantUpdateRecordException      ,
                                                                                       InconsistentTableStateException,
                                                                                       CantLoadTableToMemoryException {

        try {
            DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);

            DatabaseTableRecord recordToUpdate = getByPrimaryKey(transactionTable, bitcoinTransaction.getTransactionId());

            recordToUpdate.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_HASH_COLUMN_NAME, hash);

            transactionTable.updateRecord(recordToUpdate);

        } catch (CantUpdateRecordException | InconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }

    }


    private void loadRecordAsNew(DatabaseTableRecord databaseTableRecord,
                                 String walletPublicKey,
                                 CryptoAddress destinationAddress,
                                 long cryptoAmount,
                                 String notes,
                                 String deliveredByActorPublicKey,
                                 Actors deliveredByActorType,
                                 String deliveredToActorPublicKey,
                                 Actors deliveredToActorType,
                                 BlockchainNetworkType blockchainNetworkType,
                                 CryptoCurrency cryptoCurrency,
                                 long fee, FeeOrigin feeOrigin) {

        UUID transactionId = UUID.randomUUID();

        databaseTableRecord.setUUIDValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_ID_COLUMN_NAME, transactionId);
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_WALLET_ID_TO_DEBIT_COLUMN_NAME, walletPublicKey);

        // TODO: This will be completed when the vault gives it to us
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_HASH_COLUMN_NAME, "UNKNOWN YET");

        // TODO: This need to be completed in the future
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ADDRESS_FROM_COLUMN_NAME, "MY_ADDRESS");
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ADDRESS_TO_COLUMN_NAME, destinationAddress.getAddress());
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_CURRENY_COLUMN_NAME, destinationAddress.getCryptoCurrency().getCode());
        databaseTableRecord.setLongValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_STATUS_COLUMN_NAME, TransactionState.NEW.getCode());

        // TODO: This have to be changed for the tinestamp when the network recognize the transaction

        // eze te saco la division para obtener el timestamp bien
        databaseTableRecord.setLongValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_DESCRIPTION_COLUMN_NAME, notes);
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_STATUS_COLUMN_NAME, CryptoStatus.PENDING_SUBMIT.getCode());


        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_FROM_ID_COLUMN_NAME, deliveredByActorPublicKey);
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME, deliveredByActorType.getCode());
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_TO_ID_COLUMN_NAME, deliveredToActorPublicKey);
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_TO_TYPE_COLUMN_NAME, deliveredToActorType.getCode());
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_RUNNING_NETWORK_TYPE, blockchainNetworkType.getCode() );
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_CURRENCY_TYPE, cryptoCurrency.getCode() );
        databaseTableRecord.setLongValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_FEE, fee);
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_FEE_ORIGIN, feeOrigin.getCode());


    }


    private void setToState(TransactionWrapper bitcoinTransaction, TransactionStatus status) throws CantUpdateRecordException, InconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);

        DatabaseTableRecord recordToUpdate = getByPrimaryKey(transactionTable, bitcoinTransaction.getTransactionId());

        recordToUpdate.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_STATUS_COLUMN_NAME, status.getCode());

        transactionTable.updateRecord(recordToUpdate);
    }

    private void setToStateWithMemo(TransactionWrapper bitcoinTransaction,
                                    TransactionStatus  status            ,
                                    String             memo              ) throws CantUpdateRecordException      ,
                                                                                  InconsistentTableStateException,
                                                                                  CantLoadTableToMemoryException {

        DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);

        DatabaseTableRecord recordToUpdate = getByPrimaryKey(transactionTable, bitcoinTransaction.getTransactionId());

        recordToUpdate.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_STATUS_COLUMN_NAME, status.getCode());
        recordToUpdate.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_DESCRIPTION_COLUMN_NAME       , memo            );

        transactionTable.updateRecord(recordToUpdate);
    }


    private DatabaseTableRecord getByPrimaryKey(DatabaseTable transactionTable, UUID id) throws CantLoadTableToMemoryException, InconsistentTableStateException {
        List<DatabaseTableRecord> records;

        transactionTable.addStringFilter(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new InconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        return records.get(0);
    }

    private List<TransactionWrapper> getAllInState(TransactionStatus transactionState) throws CantLoadTableToMemoryException, InvalidParameterException {

        DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);
        List<DatabaseTableRecord> records;

        if(transactionState != null)
            transactionTable.addStringFilter(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_STATUS_COLUMN_NAME, transactionState.getCode(), DatabaseFilterType.EQUAL);

        transactionTable.loadToMemory();

        records = transactionTable.getRecords();

        return mapConvertToBT(records);

    }

    public void setToCryptoStatus(TransactionWrapper transactionWrapper, CryptoStatus cryptoStatus) throws CantUpdateRecordException, CantLoadTableToMemoryException, InconsistentTableStateException {
        try {
            DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);

            DatabaseTableRecord recordToUpdate = getByPrimaryKey(transactionTable, transactionWrapper.getTransactionId());

            recordToUpdate.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());

            transactionTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException | InconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private TransactionWrapper convertToBT(DatabaseTableRecord record) throws InvalidParameterException {

        String walletPublicKey = record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_WALLET_ID_TO_DEBIT_COLUMN_NAME);
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
        TransactionState state = TransactionState.getByCode(record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_STATUS_COLUMN_NAME));
        long timestamp = record.getLongValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TIMESTAMP_COLUMN_NAME);
        String memo = record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_DESCRIPTION_COLUMN_NAME);
        CryptoStatus cryptoStatus = CryptoStatus.getByCode(record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_STATUS_COLUMN_NAME));

        Actors actorFromType = Actors.getByCode(record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME));
        Actors actorToType = Actors.getByCode(record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_TO_TYPE_COLUMN_NAME));
        String actorFromPublicKey = record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_FROM_ID_COLUMN_NAME);
        String actorToPublicKey = record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_TO_ID_COLUMN_NAME);
        BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.getByCode(record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_RUNNING_NETWORK_TYPE));

        CryptoCurrency cryptoCurrency = CryptoCurrency.getByCode(record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_CURRENCY_TYPE));
        long fee = record.getLongValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_FEE);
        FeeOrigin feeOrigin = FeeOrigin.getByCode(record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_FEE_ORIGIN));


        long total = 0;

        if(feeOrigin.equals(FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS))
            total = amount + fee;
        else
            total = amount - fee;

        return new TransactionWrapper(
                transactionId     ,
                actorFromPublicKey,
                actorToPublicKey  ,
                actorFromType     ,
                actorToType       ,
                transactionHash   ,
                addressFrom       ,
                addressTo         ,
                amount            ,
                timestamp         ,
                memo              ,
                walletPublicKey   ,
                state             ,
                cryptoStatus,
                blockchainNetworkType,
                cryptoCurrency,
                fee,
                feeOrigin,
                total);
    }

    // Apply convertToBT to all the elements in a list
    private List<TransactionWrapper> mapConvertToBT(List<DatabaseTableRecord> transactions) throws InvalidParameterException {
        List<TransactionWrapper> bitcoinTransactionList = new ArrayList<>();

        for (DatabaseTableRecord record : transactions)
            bitcoinTransactionList.add(convertToBT(record));

        return bitcoinTransactionList;
    }


}
