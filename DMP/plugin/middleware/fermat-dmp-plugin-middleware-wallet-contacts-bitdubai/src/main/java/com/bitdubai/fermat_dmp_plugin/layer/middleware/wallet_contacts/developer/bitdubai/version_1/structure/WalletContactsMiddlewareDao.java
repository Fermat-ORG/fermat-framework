package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantUpdateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseSystemException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDao</code> have
 * all methods implementation to access the data base (CRUD)
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletContactsMiddlewareDao implements DealsWithPluginDatabaseSystem {

    /**
     * Represent the Plugin Database.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Represent de Database where i will be working with
     */
    Database database;


    /**
     * Constructor with parameters
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public WalletContactsMiddlewareDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @param ownerId      plugin id
     * @param databaseName database name
     * @throws CantInitializeWalletContactsDatabaseException
     */
    public void initializeDatabase(UUID ownerId, String databaseName) throws CantInitializeWalletContactsDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(ownerId, databaseName);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeWalletContactsDatabaseException(CantInitializeWalletContactsDatabaseException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            WalletContactsMiddlewareDatabaseFactory walletContactsDatabaseFactory = new WalletContactsMiddlewareDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = walletContactsDatabaseFactory.createDatabase(ownerId, databaseName);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeWalletContactsDatabaseException(CantInitializeWalletContactsDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            } catch (Exception exc) {
                throw new CantInitializeWalletContactsDatabaseException(CantInitializeWalletContactsDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exc));
            }
        } catch (Exception e) {
            throw new CantInitializeWalletContactsDatabaseException(CantInitializeWalletContactsDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    /**
     * Method that list the all entities on the database.
     *
     * @return All Wallet Contacts.
     */
    public List<WalletContactRecord> findAll(String walletPublicKey) throws CantGetAllWalletContactsException {

        List<WalletContactRecord> walletContactsListRecord = new ArrayList<>();

        try {
            database.openDatabase();
            DatabaseTable walletContactAddressBookTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_NAME);
            walletContactAddressBookTable.setStringFilter(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublicKey, DatabaseFilterType.EQUAL);
            walletContactAddressBookTable.setFilterOrder(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_NAME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            walletContactAddressBookTable.loadToMemory();

            List<DatabaseTableRecord> records = walletContactAddressBookTable.getRecords();

            for (DatabaseTableRecord record : records) {

                UUID contactId = record.getUUIDValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_CONTACT_ID_COLUMN_NAME);
                UUID actorId = record.getUUIDValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_ID_COLUMN_NAME);
                String actorName = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_NAME_COLUMN_NAME);
                Actors actorType = Actors.getByCode(record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_TYPE_COLUMN_NAME));

                String receivedAddress = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_CRYPTO_ADDRESS_COLUMN_NAME);
                String receivedCurrency = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_ADDRESS_CRYPTO_CURRENCY_COLUMN_NAME);
                CryptoAddress receivedCryptoAddress = new CryptoAddress(receivedAddress, CryptoCurrency.getByCode(receivedCurrency));

                WalletContactsMiddlewareRecord walletContact = new WalletContactsMiddlewareRecord(actorId, actorName, actorType, contactId, receivedCryptoAddress, walletPublicKey);

                walletContactsListRecord.add(walletContact);
            }
            database.closeDatabase();
            return walletContactsListRecord;
        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        } catch(InvalidParameterException exception){
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that list the all entities on the database.
     *
     * @return All Wallet Contacts.
     */
    public List<WalletContactRecord> findAllScrolling(String walletPublicKey, Integer max, Integer offset) throws CantGetAllWalletContactsException {

        List<WalletContactRecord> walletContactsListRecord = new ArrayList<>();

        try {
            database.openDatabase();
            DatabaseTable walletContactAddressBookTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_NAME);
            walletContactAddressBookTable.setStringFilter(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublicKey, DatabaseFilterType.EQUAL);
            walletContactAddressBookTable.setFilterOrder(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_NAME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            walletContactAddressBookTable.setFilterTop(max.toString());
            walletContactAddressBookTable.setFilterOffSet(offset.toString());
            walletContactAddressBookTable.loadToMemory();

            List<DatabaseTableRecord> records = walletContactAddressBookTable.getRecords();

            for (DatabaseTableRecord record : records) {

                UUID contactId = record.getUUIDValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_CONTACT_ID_COLUMN_NAME);
                UUID actorId = record.getUUIDValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_ID_COLUMN_NAME);
                String actorName = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_NAME_COLUMN_NAME);
                Actors actorType = Actors.getByCode(record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_TYPE_COLUMN_NAME));

                String receivedAddress = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_CRYPTO_ADDRESS_COLUMN_NAME);
                String receivedCurrency = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_ADDRESS_CRYPTO_CURRENCY_COLUMN_NAME);
                CryptoAddress receivedCryptoAddress = new CryptoAddress();
                receivedCryptoAddress.setAddress(receivedAddress);
                receivedCryptoAddress.setCryptoCurrency(CryptoCurrency.getByCode(receivedCurrency));

                WalletContactsMiddlewareRecord walletContact = new WalletContactsMiddlewareRecord(actorId, actorName, actorType, contactId, receivedCryptoAddress, walletPublicKey);

                walletContactsListRecord.add(walletContact);
            }
            database.closeDatabase();
            return walletContactsListRecord;

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        } catch(InvalidParameterException exception){
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that create a new entity in the data base.
     *
     * @param walletContactRecord WalletContactRecord to create.
     */
    public void create(WalletContactRecord walletContactRecord) throws CantCreateWalletContactException {

        if (walletContactRecord == null) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, null, "", "The entity is required, can not be null");
        }

        try {
            database.openDatabase();
            DatabaseTable walletContactAddressBookTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_NAME);
            DatabaseTableRecord entityRecord = walletContactAddressBookTable.getEmptyRecord();
            entityRecord.setUUIDValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_CONTACT_ID_COLUMN_NAME, walletContactRecord.getContactId());
            entityRecord.setStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME, walletContactRecord.getWalletPublicKey());
            entityRecord.setUUIDValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_ID_COLUMN_NAME, walletContactRecord.getActorId());
            entityRecord.setStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_NAME_COLUMN_NAME, walletContactRecord.getActorName());
            entityRecord.setStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_TYPE_COLUMN_NAME, walletContactRecord.getActorType().getCode());

            if (walletContactRecord.getReceivedCryptoAddress() != null) {
                entityRecord.setStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_CRYPTO_ADDRESS_COLUMN_NAME, walletContactRecord.getReceivedCryptoAddress().getAddress());
                entityRecord.setStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_ADDRESS_CRYPTO_CURRENCY_COLUMN_NAME, walletContactRecord.getReceivedCryptoAddress().getCryptoCurrency().getCode());
            }
            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToInsert(walletContactAddressBookTable, entityRecord);
            database.executeTransaction(transaction);
            database.closeDatabase();
        } catch (DatabaseTransactionFailedException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that update a wallet contact in the database.
     *
     * @param walletContactRecord WalletContactRecord to update.
     */
    public void update(WalletContactRecord walletContactRecord) throws CantUpdateWalletContactException {

        if (walletContactRecord == null) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, null, "", "The entity is required, can not be null");
        }

        try {
            database.openDatabase();
            DatabaseTable walletContactAddressBookTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_NAME);
            walletContactAddressBookTable.setUUIDFilter(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_CONTACT_ID_COLUMN_NAME, walletContactRecord.getContactId(), DatabaseFilterType.EQUAL);
            walletContactAddressBookTable.loadToMemory();

            DatabaseTableRecord record = walletContactAddressBookTable.getRecords().get(0);

            if (walletContactRecord.getActorName() != null)
                record.setStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_NAME_COLUMN_NAME, walletContactRecord.getActorName());
            if (walletContactRecord.getReceivedCryptoAddress() != null) {
                record.setStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_CRYPTO_ADDRESS_COLUMN_NAME, walletContactRecord.getReceivedCryptoAddress().getAddress());
                record.setStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_ADDRESS_CRYPTO_CURRENCY_COLUMN_NAME, walletContactRecord.getReceivedCryptoAddress().getCryptoCurrency().getCode());
            }

            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToUpdate(walletContactAddressBookTable, record);
            database.executeTransaction(transaction);
            database.closeDatabase();
        } catch (DatabaseTransactionFailedException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that delete a entity in the database.
     *
     * @param contactId UUID contactId.
     */
    public void delete(UUID contactId) throws CantDeleteWalletContactException {

        if (contactId == null) {
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, null, "", "The id is required, can not be null");
        }

        try {
            database.openDatabase();
            DatabaseTable walletContactAddressBookTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_NAME);
            walletContactAddressBookTable.setStringFilter(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_CONTACT_ID_COLUMN_NAME, contactId.toString(), DatabaseFilterType.EQUAL);
            walletContactAddressBookTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecordList = walletContactAddressBookTable.getRecords();

            if (databaseTableRecordList.size() > 0) {
                DatabaseTableRecord record = databaseTableRecordList.get(0);

                walletContactAddressBookTable.deleteRecord(record);
            } else {
                database.closeDatabase();
                throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, null, "", "Cannot find a wallet contact with that id");
            }
            database.closeDatabase();
        } catch (CantDeleteRecordException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot delete the record.");

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that find a WalletContactRecord in database by actorName and walletPublicKey.
     *
     * @param actorName String actorName.
     * @param walletPublicKey  UUID wallet id
     */
    public WalletContactRecord findByNameAndWalletPublicKey(String actorName, String walletPublicKey) throws CantGetWalletContactException {

        WalletContactRecord walletContactRecord;

        if (actorName == null || walletPublicKey == null) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, null, "", "The walletPublicKey and the actorName is required, can not be null");

        }

        try {
            database.openDatabase();
            DatabaseTable walletContactAddressBookTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_NAME);
            walletContactAddressBookTable.setStringFilter(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_NAME_COLUMN_NAME, actorName, DatabaseFilterType.EQUAL);
            walletContactAddressBookTable.setStringFilter(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublicKey, DatabaseFilterType.EQUAL);
            walletContactAddressBookTable.loadToMemory();
            if (walletContactAddressBookTable.getRecords().size() > 0) {
                DatabaseTableRecord record = walletContactAddressBookTable.getRecords().get(0);

                UUID contactId = record.getUUIDValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_CONTACT_ID_COLUMN_NAME);
                UUID actorId = record.getUUIDValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_ID_COLUMN_NAME);
                Actors actorType = Actors.getByCode(record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_TYPE_COLUMN_NAME));

                String receivedAddress = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_CRYPTO_ADDRESS_COLUMN_NAME);
                String receivedCurrency = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_ADDRESS_CRYPTO_CURRENCY_COLUMN_NAME);
                CryptoAddress receivedCryptoAddress = new CryptoAddress();
                receivedCryptoAddress.setAddress(receivedAddress);
                receivedCryptoAddress.setCryptoCurrency(CryptoCurrency.getByCode(receivedCurrency));

                walletContactRecord = new WalletContactsMiddlewareRecord(actorId, actorName, actorType, contactId, receivedCryptoAddress, walletPublicKey);
            } else {
                walletContactRecord = null;
            }
            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        } catch(InvalidParameterException exception){
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
        return walletContactRecord;
    }

    /**
     * Method that find a WalletContactRecord in database by actorName contains (string) and walletPublicKey.
     *
     * @param actorNameContains String actorNameContains.
     * @param walletPublicKey          UUID wallet id
     */
    public List<WalletContactRecord> findByNameContainsAndWalletPublicKey(String actorNameContains, String walletPublicKey) throws CantGetWalletContactException {

        List<WalletContactRecord> walletContactRecordList = new ArrayList<>();

        if (actorNameContains == null || walletPublicKey == null) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, null, "", "The walletPublicKey and the actorName is required, can not be null");
        }

        try {
            database.openDatabase();
            DatabaseTable walletContactAddressBookTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_NAME);
            walletContactAddressBookTable.setStringFilter(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_NAME_COLUMN_NAME, actorNameContains, DatabaseFilterType.LIKE);
            walletContactAddressBookTable.setStringFilter(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublicKey, DatabaseFilterType.EQUAL);
            walletContactAddressBookTable.loadToMemory();

            List<DatabaseTableRecord> records = walletContactAddressBookTable.getRecords();

            for (DatabaseTableRecord record : records) {

                UUID contactId = record.getUUIDValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_CONTACT_ID_COLUMN_NAME);
                UUID actorId = record.getUUIDValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_ID_COLUMN_NAME);
                String actorName = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_NAME_COLUMN_NAME);
                Actors actorType = Actors.getByCode(record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_TYPE_COLUMN_NAME));

                String receivedAddress = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_CRYPTO_ADDRESS_COLUMN_NAME);
                String receivedCurrency = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_ADDRESS_CRYPTO_CURRENCY_COLUMN_NAME);
                CryptoAddress receivedCryptoAddress = new CryptoAddress();
                receivedCryptoAddress.setAddress(receivedAddress);
                receivedCryptoAddress.setCryptoCurrency(CryptoCurrency.getByCode(receivedCurrency));

                WalletContactsMiddlewareRecord walletContact = new WalletContactsMiddlewareRecord(actorId, actorName, actorType, contactId, receivedCryptoAddress, walletPublicKey);

                walletContactRecordList.add(walletContact);
            }
            database.closeDatabase();
            return walletContactRecordList;
        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        } catch(InvalidParameterException exception){
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that find a WalletContactRecord in database by contact id.
     *
     * @param actorId UUID actor id
     */
    public List<WalletContactRecord> findAllByActorId(UUID actorId) throws CantGetAllWalletContactsException {

        List<WalletContactRecord> walletContactRecordList = new ArrayList<>();
        try {
            if (actorId == null) {
                throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, null, "", "The actorId is required, can not be null");
            }else {
                database.openDatabase();
                DatabaseTable walletContactAddressBookTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_NAME);
                walletContactAddressBookTable.setUUIDFilter(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_ID_COLUMN_NAME, actorId, DatabaseFilterType.EQUAL);
                walletContactAddressBookTable.loadToMemory();
                List<DatabaseTableRecord> records = walletContactAddressBookTable.getRecords();

                for (DatabaseTableRecord record : records) {
                    UUID contactId = record.getUUIDValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_CONTACT_ID_COLUMN_NAME);
                    String walletPublicKey = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME);
                    String actorName = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_NAME_COLUMN_NAME);
                    Actors actorType = Actors.getByCode(record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_TYPE_COLUMN_NAME));

                    String receivedAddress = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_CRYPTO_ADDRESS_COLUMN_NAME);
                    String receivedCurrency = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_ADDRESS_CRYPTO_CURRENCY_COLUMN_NAME);
                    CryptoAddress receivedCryptoAddress = new CryptoAddress(receivedAddress, CryptoCurrency.getByCode(receivedCurrency));

                    walletContactRecordList.add(new WalletContactsMiddlewareRecord(actorId, actorName, actorType, contactId, receivedCryptoAddress, walletPublicKey));
                }
                database.closeDatabase();
            }
        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        } catch (Exception exception) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
        return walletContactRecordList;
    }

    /**
     * Method that find a WalletContactRecord in database by contact id.
     *
     * @param contactId UUID contact id
     */
    public WalletContactRecord findByContactId(UUID contactId) throws CantGetWalletContactException, WalletContactNotFoundException {

        WalletContactRecord walletContactRecord;
        try {
            if (contactId == null) {
                throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, null, "", "The contactId is required, can not be null");
            }else {
                database.openDatabase();
                DatabaseTable walletContactAddressBookTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_NAME);
                walletContactAddressBookTable.setUUIDFilter(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_CONTACT_ID_COLUMN_NAME, contactId, DatabaseFilterType.EQUAL);
                walletContactAddressBookTable.loadToMemory();
                List<DatabaseTableRecord> records = walletContactAddressBookTable.getRecords();
                if (!records.isEmpty()) {
                    DatabaseTableRecord record = records.get(0);

                    UUID actorId = record.getUUIDValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_ID_COLUMN_NAME);
                    String walletPublicKey = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME);
                    String actorName = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_NAME_COLUMN_NAME);
                    Actors actorType = Actors.getByCode(record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_TYPE_COLUMN_NAME));

                    String receivedAddress = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_CRYPTO_ADDRESS_COLUMN_NAME);
                    String receivedCurrency = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_ADDRESS_CRYPTO_CURRENCY_COLUMN_NAME);
                    CryptoAddress receivedCryptoAddress = new CryptoAddress(receivedAddress, CryptoCurrency.getByCode(receivedCurrency));

                    walletContactRecord = new WalletContactsMiddlewareRecord(actorId, actorName, actorType, contactId, receivedCryptoAddress, walletPublicKey);
                } else {
                    throw new WalletContactNotFoundException(WalletContactNotFoundException.DEFAULT_MESSAGE, null, "", "Youre sending a wrong contact id.");
                }

                database.closeDatabase();
            }
        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        } catch (Exception exception) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
        return walletContactRecord;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
