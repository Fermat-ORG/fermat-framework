package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionState;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
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

    public void registerNewTransaction(UUID walletID, CryptoAddress destinationAddress, long cryptoAmount,String notes, UUID deliveredByActorId, Actors deliveredByActorType, UUID deliveredToActorId, Actors deliveredToActorType) throws CantInsertRecordException {
        DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);

        DatabaseTableRecord recordToInsert = transactionTable.getEmptyRecord();

        loadRecordAsNew(recordToInsert, walletID, destinationAddress, cryptoAmount, notes, deliveredByActorId, deliveredByActorType, deliveredToActorId, deliveredToActorType);
        transactionTable.insertRecord(recordToInsert);
    }

    public List<TransactionWrapper> getNewTransactions() throws CantLoadTableToMemoryException, InvalidParameterException {
        return getAllInState(TransactionStatus.NEW);
    }

    public List<TransactionWrapper> getPersistedInAvailable() throws CantLoadTableToMemoryException, InvalidParameterException {
        return getAllInState(TransactionStatus.PERSISTED_IN_AVAILABLE);
    }

    public List<TransactionWrapper> getDiscountedAvailableBalance() throws CantLoadTableToMemoryException, InvalidParameterException {
        return getAllInState(TransactionStatus.SENT_TO_CRYPTO_VOULT);
    }

    public List<TransactionWrapper> getPersistedInWalletTransactions() throws CantLoadTableToMemoryException, InvalidParameterException {
        return getAllInState(TransactionStatus.PERSISTED_IN_WALLET);
    }

    public List<TransactionWrapper> getSentToCryptoVaultTransactions() throws CantLoadTableToMemoryException, InvalidParameterException {
        return getAllInState(TransactionStatus.SENT_TO_CRYPTO_VOULT);
    }

    public void cancelTransaction(TransactionWrapper bitcoinTransaction) throws CantUpdateRecordException, InconsistentTableStateException, CantLoadTableToMemoryException {
        setToState(bitcoinTransaction, TransactionStatus.CANCELED);
    }

    public void setToNew(TransactionWrapper bitcoinTransaction) throws CantUpdateRecordException, InconsistentTableStateException, CantLoadTableToMemoryException {
        setToState(bitcoinTransaction, TransactionStatus.NEW);
    }

    public void setToPIA(TransactionWrapper bitcoinTransaction) throws CantUpdateRecordException, InconsistentTableStateException, CantLoadTableToMemoryException {
        setToState(bitcoinTransaction, TransactionStatus.PERSISTED_IN_AVAILABLE);
    }

    public void setToPIW(TransactionWrapper bitcoinTransaction) throws CantUpdateRecordException, InconsistentTableStateException, CantLoadTableToMemoryException {
        setToState(bitcoinTransaction,TransactionStatus.PERSISTED_IN_WALLET);
    }

    public void setToSTCV(TransactionWrapper bitcoinTransaction) throws CantUpdateRecordException, InconsistentTableStateException, CantLoadTableToMemoryException {
        setToState(bitcoinTransaction,TransactionStatus.SENT_TO_CRYPTO_VOULT);
    }


    public void setTransactionHash(TransactionWrapper bitcoinTransaction ,String hash) throws CantUpdateRecordException, InconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);

        DatabaseTableRecord recordToUpdate = getByPrimaryKey(bitcoinTransaction.getIdTransaction());

        recordToUpdate.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_HASH_COLUMN_NAME,hash);

        transactionTable.updateRecord(recordToUpdate);
    }



    private  void loadRecordAsNew(DatabaseTableRecord databaseTableRecord, UUID walletId, CryptoAddress destinationAddress, long cryptoAmount,String notes, UUID deliveredByActorId, Actors deliveredByActorType, UUID deliveredToActorId, Actors deliveredToActorType){

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

        // TODO: This have to be changed for the tinestamp when the network recognize the transaction

        // eze te saco la division para obtener el timestamp bien
        databaseTableRecord.setLongValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_DESCRIPTION_COLUMN_NAME, notes);
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_STATUS_COLUMN_NAME, CryptoStatus.PENDING_SUBMIT.getCode());


        databaseTableRecord.setUUIDValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_FROM_ID_COLUMN_NAME,deliveredByActorId);
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME,deliveredByActorType.getCode());
        databaseTableRecord.setUUIDValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_TO_ID_COLUMN_NAME,deliveredToActorId);
        databaseTableRecord.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_TO_TYPE_COLUMN_NAME,deliveredByActorType.getCode());
    }


    private void setToState(TransactionWrapper bitcoinTransaction, TransactionStatus status) throws CantUpdateRecordException, InconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);

        DatabaseTableRecord recordToUpdate = getByPrimaryKey(bitcoinTransaction.getIdTransaction());

        recordToUpdate.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_STATUS_COLUMN_NAME,status.getCode());

        transactionTable.updateRecord(recordToUpdate);
    }


    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, InconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        records = transactionTable.getRecords();

        if(records.size() != 1)
            throw new InconsistentTableStateException("The number of records with a primary key is different thatn one ",null,"The id is: "+id.toString(),"");

        return records.get(0);
    }

    private List<TransactionWrapper> getAllInState(TransactionStatus transactionState) throws CantLoadTableToMemoryException, InvalidParameterException {

        DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_STATUS_COLUMN_NAME, transactionState.getCode(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();

        records = transactionTable.getRecords();

        return mapConvertToBT(records);

    }

    public void setToCryptoStatus(TransactionWrapper transactionWrapper, CryptoStatus cryptoStatus) throws CantUpdateRecordException, CantLoadTableToMemoryException, InconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);

        DatabaseTableRecord recordToUpdate = getByPrimaryKey(transactionWrapper.getIdTransaction());

        recordToUpdate.setStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_STATUS_COLUMN_NAME,cryptoStatus.getCode());

        transactionTable.updateRecord(recordToUpdate);
    }

    /*
    private List<TransactionWrapper> getAllInCryptoStatus(CryptoStatus cryptoStatus) throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable transactionTable = this.database.getTable(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();

        records = transactionTable.getRecords();

        return mapConvertToBT(records);
    }
    */

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
        TransactionState state = TransactionState.getByCode(record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_STATUS_COLUMN_NAME));
        long timestamp = record.getLongValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TIMESTAMP_COLUMN_NAME);
        String memo = record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_DESCRIPTION_COLUMN_NAME);
        CryptoStatus cryptoStatus = CryptoStatus.getByCode(record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_STATUS_COLUMN_NAME));

        Actors actorFromType = Actors.getByCode(record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME));
        Actors actorToType  = Actors.getByCode(record.getStringValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_TO_TYPE_COLUMN_NAME));
        UUID actorFromId = record.getUUIDValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_FROM_ID_COLUMN_NAME);
        UUID actorToId = record.getUUIDValue(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_TO_ID_COLUMN_NAME);

        bitcoinTransaction.setWalletId(walletId);
        bitcoinTransaction.setIdTransaction(transactionId);
        bitcoinTransaction.setTransactionHash(transactionHash);
        bitcoinTransaction.setAddressFrom(addressFrom);
        bitcoinTransaction.setAddressTo(addressTo);
        bitcoinTransaction.setAmount(amount);
        bitcoinTransaction.setState(state);
        bitcoinTransaction.setTimestamp(timestamp);
        bitcoinTransaction.setMemo(memo);
        bitcoinTransaction.setCryptoStatus(cryptoStatus);
        bitcoinTransaction.setActorFromId(actorFromId);
        bitcoinTransaction.setActorFromType(actorFromType);
        bitcoinTransaction.setActorToId(actorToId);
        bitcoinTransaction.setActorToType(actorToType);

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
