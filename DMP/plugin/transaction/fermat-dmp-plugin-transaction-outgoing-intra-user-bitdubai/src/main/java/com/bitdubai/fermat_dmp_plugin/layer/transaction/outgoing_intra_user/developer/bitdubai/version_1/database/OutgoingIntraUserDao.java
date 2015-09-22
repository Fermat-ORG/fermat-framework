package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.enums.TransactionState;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraUserDaoException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserCantInsertRecordException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserInconsistentTableStateException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.util.OutgoingIntraUserTransactionWrapper;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.09.21..
 */
public class OutgoingIntraUserDao {

    private Database             database;
    private ErrorManager         errorManager;
    private PluginDatabaseSystem pluginDatabaseSystem;

    public OutgoingIntraUserDao(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem) {
        this.errorManager         = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public void initialize(UUID pluginId) throws CantInitializeOutgoingIntraUserDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {

            OutgoingIntraUserTransactionDatabaseFactory databaseFactory = new OutgoingIntraUserTransactionDatabaseFactory(this.pluginDatabaseSystem);
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            try {
                this.database = databaseFactory.createDatabase(pluginId, OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeOutgoingIntraUserDaoException("I couldn't create the database", cantCreateDatabaseException, "Database Name: " + OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_DATABASE_NAME, "");
            } catch (Exception exception) {
                throw new CantInitializeOutgoingIntraUserDaoException(CantInitializeOutgoingIntraUserDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
            }

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeOutgoingIntraUserDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_DATABASE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeOutgoingIntraUserDaoException(CantInitializeOutgoingIntraUserDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }


    public void registerNewTransaction(String        walletPublicKey,
                                       CryptoAddress destinationAddress,
                                       long          cryptoAmount,
                                       String        notes,
                                       String        deliveredByActorPublicKey,
                                       Actors        deliveredByActorType,
                                       String        deliveredToActorPublicKey,
                                       Actors        deliveredToActorType) throws OutgoingIntraUserCantInsertRecordException {

    }

    public List<OutgoingIntraUserTransactionWrapper> getNewTransactions() {
        return new ArrayList<>();
    }

    public List<OutgoingIntraUserTransactionWrapper> getPersistedInAvailable() {
        return new ArrayList<>();
    }

    public List<OutgoingIntraUserTransactionWrapper> getDiscountedAvailableBalance() {
        return new ArrayList<>();
    }

    public List<OutgoingIntraUserTransactionWrapper> getPersistedInWalletTransactions() {
        return new ArrayList<>();
    }

    public List<OutgoingIntraUserTransactionWrapper> getSentToCryptoVaultTransactions() {
        return new ArrayList<>();
    }

    public void cancelTransaction(OutgoingIntraUserTransactionWrapper bitcoinTransaction) {
    }

    public void setToNew(OutgoingIntraUserTransactionWrapper bitcoinTransaction) {
    }

    public void setToPIA(OutgoingIntraUserTransactionWrapper bitcoinTransaction) {
    }

    public void setToPIW(OutgoingIntraUserTransactionWrapper bitcoinTransaction) {
    }

    public void setToSTCV(OutgoingIntraUserTransactionWrapper bitcoinTransaction) {
    }
    
    public void setTransactionHash(OutgoingIntraUserTransactionWrapper bitcoinTransaction, String hash) {
    }


    private void loadRecordAsNew(DatabaseTableRecord databaseTableRecord,
                                 String walletPublicKey,
                                 CryptoAddress destinationAddress,
                                 long cryptoAmount,
                                 String notes,
                                 String deliveredByActorPublicKey,
                                 Actors deliveredByActorType,
                                 String deliveredToActorPublicKey,
                                 Actors deliveredToActorType) {

        UUID transactionId = UUID.randomUUID();

        databaseTableRecord.setUUIDValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_TRANSACTION_ID_COLUMN_NAME, transactionId);
        databaseTableRecord.setStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_WALLET_ID_TO_DEBIT_FROM_COLUMN_NAME, walletPublicKey);

        // TODO: This will be completed when the vault gives it to us
        databaseTableRecord.setStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_TRANSACTION_HASH_COLUMN_NAME, "UNKNOWN YET");

        // TODO: This need to be completed in the future
        databaseTableRecord.setStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_ADDRESS_FROM_COLUMN_NAME, "MY_ADDRESS");
        databaseTableRecord.setStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_ADDRESS_TO_COLUMN_NAME, destinationAddress.getAddress());
        databaseTableRecord.setStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_CRYPTO_CURRENCY_COLUMN_NAME, destinationAddress.getCryptoCurrency().getCode());
        databaseTableRecord.setLongValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);
        databaseTableRecord.setStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_TRANSACTION_STATUS_COLUMN_NAME, TransactionState.NEW.getCode());

        // TODO: This have to be changed for the tinestamp when the network recognize the transaction

        // eze te saco la division para obtener el timestamp bien
        databaseTableRecord.setLongValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());
        databaseTableRecord.setStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_DESCRIPTION_COLUMN_NAME, notes);
        databaseTableRecord.setStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_CRYPTO_STATUS_COLUMN_NAME, CryptoStatus.PENDING_SUBMIT.getCode());

        databaseTableRecord.setStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_ACTOR_FROM_PUBLIC_KEY_COLUMN_NAME, deliveredByActorPublicKey);
        databaseTableRecord.setStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_ACTOR_FROM_TYPE_COLUMN_NAME, deliveredByActorType.getCode());
        databaseTableRecord.setStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_ACTOR_TO_PUBLIC_KEY_COLUMN_NAME, deliveredToActorPublicKey);
        databaseTableRecord.setStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_ACTOR_TO_TYPE_COLUMN_NAME, deliveredToActorType.getCode());
    }


    private void setToState(OutgoingIntraUserTransactionWrapper bitcoinTransaction, TransactionState status) throws CantUpdateRecordException, OutgoingIntraUserInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable       transactionTable = this.database.getTable(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_TABLE_NAME);
        DatabaseTableRecord recordToUpdate   = getByPrimaryKey(bitcoinTransaction.getIdTransaction());
        recordToUpdate.setStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_TRANSACTION_STATUS_COLUMN_NAME, status.getCode());
        transactionTable.updateRecord(recordToUpdate);
    }


    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, OutgoingIntraUserInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_TABLE_NAME);

        transactionTable.setStringFilter(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        List<DatabaseTableRecord> records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new OutgoingIntraUserInconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        transactionTable.clearAllFilters();

        return records.get(0);
    }

    private List<OutgoingIntraUserTransactionWrapper> getAllInState(TransactionState transactionState) throws CantLoadTableToMemoryException, InvalidParameterException {

        DatabaseTable transactionTable = this.database.getTable(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_TABLE_NAME);
        transactionTable.setStringFilter(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_TRANSACTION_STATUS_COLUMN_NAME, transactionState.getCode(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        List<DatabaseTableRecord> records = transactionTable.getRecords();
        transactionTable.clearAllFilters();

        return mapConvertToBT(records);
    }

    public void setToCryptoStatus(OutgoingIntraUserTransactionWrapper transactionWrapper, CryptoStatus cryptoStatus) throws CantUpdateRecordException, CantLoadTableToMemoryException, OutgoingIntraUserInconsistentTableStateException {
        try {
            DatabaseTable transactionTable = this.database.getTable(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = getByPrimaryKey(transactionWrapper.getIdTransaction());
            recordToUpdate.setStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());
            transactionTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException | OutgoingIntraUserInconsistentTableStateException | CantLoadTableToMemoryException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }
    
    private OutgoingIntraUserTransactionWrapper convertToBT(DatabaseTableRecord record) throws InvalidParameterException {
        OutgoingIntraUserTransactionWrapper bitcoinTransaction = new OutgoingIntraUserTransactionWrapper();

        String           walletPublicKey    = record.getStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_WALLET_ID_TO_DEBIT_FROM_COLUMN_NAME);
        UUID             transactionId      = record.getUUIDValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_TRANSACTION_ID_COLUMN_NAME);
        String           transactionHash    = record.getStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_TRANSACTION_HASH_COLUMN_NAME);
        long             amount             = record.getLongValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_CRYPTO_AMOUNT_COLUMN_NAME);
        TransactionState state              = TransactionState.getByCode(record.getStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_TRANSACTION_STATUS_COLUMN_NAME));
        long             timestamp          = record.getLongValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_TIMESTAMP_COLUMN_NAME);
        String           memo               = record.getStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_DESCRIPTION_COLUMN_NAME);
        CryptoStatus     cryptoStatus       = CryptoStatus.getByCode(record.getStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_CRYPTO_STATUS_COLUMN_NAME));
        Actors           actorFromType      = Actors.getByCode(record.getStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_ACTOR_FROM_TYPE_COLUMN_NAME));
        Actors           actorToType        = Actors.getByCode(record.getStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_ACTOR_TO_TYPE_COLUMN_NAME));
        String           actorFromPublicKey = record.getStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_ACTOR_FROM_PUBLIC_KEY_COLUMN_NAME);
        String           actorToPublicKey   = record.getStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_ACTOR_TO_PUBLIC_KEY_COLUMN_NAME);
        CryptoAddress    addressFrom        = new CryptoAddress(
                                                    record.getStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_ADDRESS_FROM_COLUMN_NAME),
                                                    CryptoCurrency.getByCode(record.getStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_CRYPTO_CURRENCY_COLUMN_NAME))
                                                  );
        CryptoAddress    addressTo          = new CryptoAddress(
                                                    record.getStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_ADDRESS_TO_COLUMN_NAME),
                                                    CryptoCurrency.getByCode(record.getStringValue(OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_CRYPTO_CURRENCY_COLUMN_NAME))
                                                  );


        bitcoinTransaction.setWalletPublicKey(walletPublicKey);
        bitcoinTransaction.setIdTransaction(transactionId);
        bitcoinTransaction.setTransactionHash(transactionHash);
        bitcoinTransaction.setAddressFrom(addressFrom);
        bitcoinTransaction.setAddressTo(addressTo);
        bitcoinTransaction.setAmount(amount);
        bitcoinTransaction.setState(state);
        bitcoinTransaction.setTimestamp(timestamp);
        bitcoinTransaction.setMemo(memo);
        bitcoinTransaction.setCryptoStatus(cryptoStatus);
        bitcoinTransaction.setActorFromPublicKey(actorFromPublicKey);
        bitcoinTransaction.setActorFromType(actorFromType);
        bitcoinTransaction.setActorToPublicKey(actorToPublicKey);
        bitcoinTransaction.setActorToType(actorToType);

        return bitcoinTransaction;
    }

    // Apply convertToBT to all the elements in a list
    private List<OutgoingIntraUserTransactionWrapper> mapConvertToBT(List<DatabaseTableRecord> transactions) throws InvalidParameterException {
        List<OutgoingIntraUserTransactionWrapper> bitcoinTransactionList = new ArrayList<>();

        for (DatabaseTableRecord record : transactions)
            bitcoinTransactionList.add(convertToBT(record));

        return bitcoinTransactionList;
    }
    
}
