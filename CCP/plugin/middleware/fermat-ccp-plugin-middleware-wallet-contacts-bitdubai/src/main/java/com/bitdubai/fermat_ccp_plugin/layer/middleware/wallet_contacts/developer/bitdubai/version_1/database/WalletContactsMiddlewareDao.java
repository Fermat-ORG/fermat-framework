package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Compatibility;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantAddCryptoAddressException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantDeleteCryptoAddressException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantUpdateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantDeleteCryptoAddressesException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsMiddlewareDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInsertCryptoAddressesException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The interface <code>WalletContactsMiddlewareDao</code>
 * haves all the methods that interact with the database.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/09/2015.
 *
 * @version 1.0
 */
public class WalletContactsMiddlewareDao {

    private Database database;

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    /**
     * Constructor.
     */
    public WalletContactsMiddlewareDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                       final UUID                 pluginId            ) {

        this.pluginId             = pluginId            ;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * CryptoAddressBook Interface implementation.
     */
    public void initialize() throws CantInitializeWalletContactsMiddlewareDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                WalletContactsMiddlewareDatabaseFactory databaseFactory = new WalletContactsMiddlewareDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeWalletContactsMiddlewareDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeWalletContactsMiddlewareDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        }
    }

    public List<WalletContactRecord> listWalletContactRecords(DatabaseTable walletContactsTable) throws CantGetAllWalletContactsException {
        try {
            walletContactsTable.loadToMemory();

            List<DatabaseTableRecord> records = walletContactsTable.getRecords();

            List<WalletContactRecord> walletContactRecordList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                walletContactRecordList.add(buildRecord(record));
            }
            return walletContactRecordList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantGetWalletContactException e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e, "", "This contact cannot be loaded.");
        } catch (InvalidParameterException exception) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, exception);
        } catch (Exception exception) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, exception,"", "Generic Error");
        }
    }

    public List<WalletContactRecord> listWalletContactRecords(DatabaseTable walletContactsTable,
                                                              Integer       max                ,
                                                              Integer       offset             ) throws CantGetAllWalletContactsException {

        try {
            walletContactsTable.setFilterTop(max.toString());
            walletContactsTable.setFilterOffSet(offset.toString());

            walletContactsTable.loadToMemory();

            List<DatabaseTableRecord> records = walletContactsTable.getRecords();

            List<WalletContactRecord> walletContactRecordList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                walletContactRecordList.add(buildRecord(record));
            }
            return walletContactRecordList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }  catch (CantGetWalletContactException e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e, "", "This contact cannot be loaded.");
        } catch(InvalidParameterException exception){
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, exception);
        }
    }

    public WalletContactRecord createWalletContact(UUID                contactId      ,
                                                   String              actorPublicKey ,
                                                   String              actorAlias     ,
                                                   String              actorFirstName ,
                                                   String              actorLastName  ,
                                                   Actors              actorType      ,
                                                   List<CryptoAddress> cryptoAddresses,
                                                   String walletPublicKey             ) throws CantCreateWalletContactException {

        try {
            DatabaseTable walletContactTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_TABLE_NAME);

            DatabaseTableRecord entityRecord = walletContactTable.getEmptyRecord();

            entityRecord.setUUIDValue  (WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_CONTACT_ID_COLUMN_NAME       , contactId                   );
            entityRecord.setStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_PUBLIC_KEY_COLUMN_NAME , actorPublicKey              );
            entityRecord.setStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_ALIAS_COLUMN_NAME      , actorAlias                  );
            entityRecord.setStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_FIRST_NAME_COLUMN_NAME , actorFirstName              );
            entityRecord.setStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_LAST_NAME_COLUMN_NAME  , actorLastName               );
            entityRecord.setStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_TYPE_COLUMN_NAME       , actorType.getCode()         );
            entityRecord.setStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublicKey             );
            entityRecord.setStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_COMPATIBILITY_COLUMN_NAME    , Compatibility.NONE.getCode());

            insertCryptoAddresses(contactId, cryptoAddresses);

            walletContactTable.insertRecord(entityRecord);

            return new WalletContactsMiddlewareRecord(
                    contactId         ,
                    actorPublicKey    ,
                    actorAlias        ,
                    actorFirstName    ,
                    actorLastName     ,
                    actorType         ,
                    cryptoAddresses   ,
                    walletPublicKey   ,
                    Compatibility.NONE
            );

        } catch (CantInsertCryptoAddressesException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "", "There's a problem inserting crypto addresses");
        } catch (CantInsertRecordException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        }
    }

    public void updateWalletContact(WalletContactRecord walletContactRecord) throws CantUpdateWalletContactException, WalletContactNotFoundException {
        if (walletContactRecord == null) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, null, "", "The entity is required, can not be null");
        }

        try {
            DatabaseTable walletContactAddressBookTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_TABLE_NAME);
            walletContactAddressBookTable.setUUIDFilter(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_CONTACT_ID_COLUMN_NAME, walletContactRecord.getContactId(), DatabaseFilterType.EQUAL);
            walletContactAddressBookTable.loadToMemory();

            List<DatabaseTableRecord> records = walletContactAddressBookTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                if (walletContactRecord.getActorAlias()     != null)
                    record.setStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_ALIAS_COLUMN_NAME     , walletContactRecord.getActorAlias()    );
                if (walletContactRecord.getActorFirstName() != null)
                    record.setStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_FIRST_NAME_COLUMN_NAME, walletContactRecord.getActorFirstName());
                if (walletContactRecord.getActorLastName()  != null)
                    record.setStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_LAST_NAME_COLUMN_NAME , walletContactRecord.getActorLastName() );

                deleteCryptoAddresses(walletContactRecord.getContactId());
                insertCryptoAddresses(walletContactRecord.getContactId(), walletContactRecord.getCryptoAddresses());

                walletContactAddressBookTable.updateRecord(record);
            } else {
                throw new WalletContactNotFoundException(WalletContactNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a wallet contact with that id");
            }
        } catch (CantInsertCryptoAddressesException | CantDeleteCryptoAddressesException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, e, "", "There's a problem updating crypto addresses");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantUpdateRecordException exception) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, exception, "", "Cant update record exception.");
        }
    }

    public void updateCompatibility(UUID          contactId    ,
                                    Compatibility compatibility) throws CantUpdateWalletContactException {
        if (contactId == null) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, null, "", "The contactId is required, can not be null");
        }

        try {
            DatabaseTable walletContactAddressBookTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_TABLE_NAME);

            walletContactAddressBookTable.setUUIDFilter(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_CONTACT_ID_COLUMN_NAME, contactId, DatabaseFilterType.EQUAL);

            walletContactAddressBookTable.loadToMemory();

            List<DatabaseTableRecord> records = walletContactAddressBookTable.getRecords();

            DatabaseTableRecord record = records.get(0);

            record.setStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_COMPATIBILITY_COLUMN_NAME, compatibility.getCode());

            walletContactAddressBookTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantUpdateRecordException exception) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, exception, "", "Cant update record exception.");
        }
    }

    public void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException, WalletContactNotFoundException {

        if (contactId == null) {
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, null, "", "The id is required, can not be null");
        }

        try {
            DatabaseTable walletContactsTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_TABLE_NAME);
            walletContactsTable.setUUIDFilter(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_CONTACT_ID_COLUMN_NAME, contactId, DatabaseFilterType.EQUAL);
            walletContactsTable.loadToMemory();
            List<DatabaseTableRecord> walletContactsTableRecordList = walletContactsTable.getRecords();

            if (!walletContactsTableRecordList.isEmpty()) {

                deleteCryptoAddresses(contactId);

                DatabaseTableRecord record = walletContactsTableRecordList.get(0);
                walletContactsTable.deleteRecord(record);

            } else {
                throw new WalletContactNotFoundException(WalletContactNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a wallet contact with that id");
            }
        } catch (CantDeleteCryptoAddressesException e) {
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, e, "", "Cant delete crypto addresses linked to this wallet contact.");
        }  catch (CantDeleteRecordException e) {
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot delete the record.");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        }
    }

    public WalletContactRecord findWalletContactByActorAndWalletPublicKey(String actorPublicKey,
                                                                          String walletPublicKey) throws CantGetWalletContactException, WalletContactNotFoundException {
        if (actorPublicKey == null) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, null, "", "The actorPublicKey is required, can not be null");
        }

        if (walletPublicKey == null) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, null, "", "The walletPublicKey is required, can not be null");
        }

        try {
            DatabaseTable walletContactsTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_TABLE_NAME);
            walletContactsTable.setStringFilter(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_PUBLIC_KEY_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            walletContactsTable.setStringFilter(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublicKey, DatabaseFilterType.EQUAL);
            walletContactsTable.loadToMemory();
            List<DatabaseTableRecord> walletContactsTableRecordList = walletContactsTable.getRecords();

            if (!walletContactsTableRecordList.isEmpty()) {

                DatabaseTableRecord record = walletContactsTableRecordList.get(0);
                return buildRecord(record);

            } else {
                throw new WalletContactNotFoundException(WalletContactNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a wallet contact with that parameters");
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException e) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, e, "", "Invalid parameter exception.");
        }
    }

    public WalletContactRecord findWalletContactByContactId(UUID contactId) throws CantGetWalletContactException ,
                                                                                   WalletContactNotFoundException {
        if (contactId == null) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, null, "", "The id is required, can not be null");
        }

        try {
            DatabaseTable walletContactsTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_TABLE_NAME);
            walletContactsTable.setUUIDFilter(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_CONTACT_ID_COLUMN_NAME, contactId, DatabaseFilterType.EQUAL);
            walletContactsTable.loadToMemory();
            List<DatabaseTableRecord> walletContactsTableRecordList = walletContactsTable.getRecords();

            if (!walletContactsTableRecordList.isEmpty()) {

                DatabaseTableRecord record = walletContactsTableRecordList.get(0);
                return buildRecord(record);

            } else {
                throw new WalletContactNotFoundException(WalletContactNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a wallet contact with that id");
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException e) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, e, "", "Invalid parameter exception.");
        }
    }

    public WalletContactRecord findWalletContactByAliasAndWalletPublicKey(String actorAlias     ,
                                                                          String walletPublicKey) throws CantGetWalletContactException ,
                                                                                                         WalletContactNotFoundException {
        if (actorAlias == null) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, null, "", "The actorAlias is required, can not be null");
        }
        if (walletPublicKey == null) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, null, "", "The walletPublicKey is required, can not be null");
        }

        try {
            DatabaseTable walletContactsTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_TABLE_NAME);

            walletContactsTable.setStringFilter(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_ALIAS_COLUMN_NAME      , actorAlias     , DatabaseFilterType.EQUAL);
            walletContactsTable.setStringFilter(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublicKey, DatabaseFilterType.EQUAL);

            walletContactsTable.loadToMemory();

            List<DatabaseTableRecord> walletContactsTableRecordList = walletContactsTable.getRecords();

            if (!walletContactsTableRecordList.isEmpty()) {

                DatabaseTableRecord record = walletContactsTableRecordList.get(0);
                return buildRecord(record);

            } else {
                throw new WalletContactNotFoundException(WalletContactNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a wallet contact with these params");
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException e) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, e, "", "Invalid parameter exception.");
        }
    }

    public void addCryptoAddress(UUID          contactId,
                                 CryptoAddress cryptoAddress) throws CantAddCryptoAddressException {
        try {
            DatabaseTable cryptoAddressesTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_TABLE_NAME);

            DatabaseTableRecord record = cryptoAddressesTable.getEmptyRecord();

            long unixTime = System.currentTimeMillis() / 1000L;

            record.setUUIDValue  (WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CONTACT_ID_COLUMN_NAME     , contactId                                  );
            record.setStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CRYPTO_ADDRESS_COLUMN_NAME , cryptoAddress.getAddress()                 );
            record.setStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CRYPTO_CURRENCY_COLUMN_NAME, cryptoAddress.getCryptoCurrency().getCode());
            record.setLongValue  (WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_TIME_STAMP_COLUMN_NAME     , unixTime                                   );


            cryptoAddressesTable.insertRecord(record);

        } catch (CantInsertRecordException e) {
            throw new CantAddCryptoAddressException(CantAddCryptoAddressException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot delete the record.");
        } catch (Exception e) {
            throw new CantAddCryptoAddressException(CantAddCryptoAddressException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    public void deleteCryptoAddress(UUID          contactId,
                                    CryptoAddress cryptoAddress) throws CantDeleteCryptoAddressException {

        try {
            DatabaseTable cryptoAddressesTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_TABLE_NAME);

            cryptoAddressesTable.setUUIDFilter  (WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CONTACT_ID_COLUMN_NAME     , contactId                                  , DatabaseFilterType.EQUAL);
            cryptoAddressesTable.setStringFilter(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CRYPTO_ADDRESS_COLUMN_NAME , cryptoAddress.getAddress()                 , DatabaseFilterType.EQUAL);
            cryptoAddressesTable.setStringFilter(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CRYPTO_CURRENCY_COLUMN_NAME, cryptoAddress.getCryptoCurrency().getCode(), DatabaseFilterType.EQUAL);

            cryptoAddressesTable.loadToMemory();

            List<DatabaseTableRecord> cryptoAddressesTableRecordList = cryptoAddressesTable.getRecords();

            for(DatabaseTableRecord cryptoAddressRecord : cryptoAddressesTableRecordList)
                cryptoAddressesTable.deleteRecord(cryptoAddressRecord);

        } catch (CantDeleteRecordException e) {

            throw new CantDeleteCryptoAddressException(CantDeleteCryptoAddressException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot delete the record.");
        } catch (CantLoadTableToMemoryException e) {

            throw new CantDeleteCryptoAddressException(CantDeleteCryptoAddressException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (Exception e) {

            throw new CantDeleteCryptoAddressException(CantDeleteCryptoAddressException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    private void insertCryptoAddresses(UUID                contactId      ,
                                       List<CryptoAddress> cryptoAddresses) throws CantInsertCryptoAddressesException {
        try {
            DatabaseTable cryptoAddressesTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_TABLE_NAME);

            for(CryptoAddress cryptoAddress : cryptoAddresses) {
                DatabaseTableRecord record = cryptoAddressesTable.getEmptyRecord();

                long unixTime = System.currentTimeMillis() / 1000L;

                record.setUUIDValue  (WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CONTACT_ID_COLUMN_NAME     , contactId                                  );
                record.setStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CRYPTO_ADDRESS_COLUMN_NAME , cryptoAddress.getAddress()                 );
                record.setStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CRYPTO_CURRENCY_COLUMN_NAME, cryptoAddress.getCryptoCurrency().getCode());
                record.setLongValue  (WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_TIME_STAMP_COLUMN_NAME     , unixTime                                   );

                cryptoAddressesTable.insertRecord(record);
            }

        } catch (CantInsertRecordException e) {
            throw new CantInsertCryptoAddressesException(CantInsertCryptoAddressesException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot delete the record.");
        } catch (Exception e) {
            throw new CantInsertCryptoAddressesException(CantInsertCryptoAddressesException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    private void deleteCryptoAddresses(UUID contactId) throws CantDeleteCryptoAddressesException {
        try {
            DatabaseTable cryptoAddressesTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_TABLE_NAME);
            cryptoAddressesTable.setUUIDFilter(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CONTACT_ID_COLUMN_NAME, contactId, DatabaseFilterType.EQUAL);
            cryptoAddressesTable.loadToMemory();
            List<DatabaseTableRecord> cryptoAddressesTableRecordList = cryptoAddressesTable.getRecords();

            for(DatabaseTableRecord cryptoAddressRecord : cryptoAddressesTableRecordList)
                cryptoAddressesTable.deleteRecord(cryptoAddressRecord);

        } catch (CantDeleteRecordException e) {
            throw new CantDeleteCryptoAddressesException(CantDeleteCryptoAddressesException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot delete the record.");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantDeleteCryptoAddressesException(CantDeleteCryptoAddressesException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (Exception e) {
            throw new CantDeleteCryptoAddressesException(CantDeleteCryptoAddressesException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    private List<CryptoAddress> getCryptoAddresses(UUID contactId) throws CantGetWalletContactException {
        try {
            DatabaseTable cryptoAddressesTable = database.getTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_TABLE_NAME);
            cryptoAddressesTable.setUUIDFilter(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CONTACT_ID_COLUMN_NAME, contactId, DatabaseFilterType.EQUAL);
            cryptoAddressesTable.loadToMemory();
            List<DatabaseTableRecord> records = cryptoAddressesTable.getRecords();

            List<CryptoAddress> cryptoAddresses = new ArrayList<>();

            for(DatabaseTableRecord record : records) {
                try {
                    String address = record.getStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CRYPTO_ADDRESS_COLUMN_NAME);
                    CryptoCurrency currency = CryptoCurrency.getByCode(record.getStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CRYPTO_CURRENCY_COLUMN_NAME));
                    cryptoAddresses.add(new CryptoAddress(address, currency));
                } catch (InvalidParameterException e) {
                    throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, e, "", "CryptoCurrency not found. Invalid Parameter Exception.");
                }
            }
            return cryptoAddresses;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    private WalletContactRecord buildRecord(DatabaseTableRecord databaseTableRecord) throws InvalidParameterException, CantGetWalletContactException {

        UUID   contactId           = databaseTableRecord.getUUIDValue  (WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_CONTACT_ID_COLUMN_NAME       );
        String actorPublicKey      = databaseTableRecord.getStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_PUBLIC_KEY_COLUMN_NAME );
        String actorTypeString     = databaseTableRecord.getStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_TYPE_COLUMN_NAME       );
        String actorAlias          = databaseTableRecord.getStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_ALIAS_COLUMN_NAME      );
        String actorFirstName      = databaseTableRecord.getStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_FIRST_NAME_COLUMN_NAME );
        String actorLastName       = databaseTableRecord.getStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_LAST_NAME_COLUMN_NAME  );
        String walletPublicKey     = databaseTableRecord.getStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_WALLET_PUBLIC_KEY_COLUMN_NAME);
        String compatibilityString = databaseTableRecord.getStringValue(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_COMPATIBILITY_COLUMN_NAME    );

        Actors        actorType     = Actors       .getByCode(actorTypeString);
        Compatibility compatibility = Compatibility.getByCode(compatibilityString);

        List<CryptoAddress> cryptoAddresses = getCryptoAddresses(contactId);

        return new WalletContactsMiddlewareRecord(
                contactId      ,
                actorPublicKey ,
                actorAlias     ,
                actorFirstName ,
                actorLastName  ,
                actorType      ,
                cryptoAddresses,
                walletPublicKey,
                compatibility
        );
    }

    public DatabaseTable getWalletContactsTable() {
        return database.getTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_TABLE_NAME);
    }

}
