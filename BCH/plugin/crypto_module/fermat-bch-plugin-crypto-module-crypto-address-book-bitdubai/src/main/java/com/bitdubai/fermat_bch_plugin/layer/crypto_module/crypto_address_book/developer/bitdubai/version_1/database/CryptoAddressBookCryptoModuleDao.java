package com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database;

/**
 * The interface <code>com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDao</code>
 * haves all the methods that interact with the database.
 * Manages the relationship of the crypto addresses by storing them on a Database Table.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/09/2015.
 *
 * @version 1.0
 */

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantGetCryptoAddressBookRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantListCryptoAddressBookRecordsException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CryptoAddressBookRecordNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;
import com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressBookCryptoModuleDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.exceptions.InvalidCryptoAddressBookRecordParametersException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.structure.CryptoAddressBookCryptoModuleRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CryptoAddressBookCryptoModuleDao implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public CryptoAddressBookCryptoModuleDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * CryptoAddressBook Interface implementation.
     */
    public void initialize() throws CantInitializeCryptoAddressBookCryptoModuleDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CryptoAddressBookCryptoModuleDatabaseFactory databaseFactory = new CryptoAddressBookCryptoModuleDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCryptoAddressBookCryptoModuleDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCryptoAddressBookCryptoModuleDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCryptoAddressBookCryptoModuleDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCryptoAddressBookCryptoModuleDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    public CryptoAddressBookRecord getCryptoAddressBookRecordByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetCryptoAddressBookRecordException, CryptoAddressBookRecordNotFoundException {

        if (cryptoAddress == null) {
            throw new CantGetCryptoAddressBookRecordException(CantGetCryptoAddressBookRecordException.DEFAULT_MESSAGE, null, "", "CryptoAddress, can not be null");
        }

        try {
            DatabaseTable cryptoAddressBookTable = database.getTable(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
            cryptoAddressBookTable.addStringFilter(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_CRYPTO_ADDRESS_COLUMN_NAME, cryptoAddress.getAddress(), DatabaseFilterType.EQUAL);
            //cryptoAddressBookTable.addStringFilter(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_CRYPTO_CURRENCY_COLUMN_NAME, cryptoAddress.getCryptoCurrency().getCode(), DatabaseFilterType.EQUAL);
            cryptoAddressBookTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoAddressBookTable.getRecords();

            if (!records.isEmpty())
                return buildCryptoAddressBookRecord(records.get(0));
            else
            /**
             * if record is empty, then I'm returning null
             */
                return null;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetCryptoAddressBookRecordException(CantGetCryptoAddressBookRecordException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException exception) {
            throw new CantGetCryptoAddressBookRecordException(CantGetCryptoAddressBookRecordException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        } catch (InvalidCryptoAddressBookRecordParametersException exception) {
            throw new CantGetCryptoAddressBookRecordException(CantGetCryptoAddressBookRecordException.DEFAULT_MESSAGE, exception, "", "There's a problem with the data in database.");
        } catch (Exception exception) {
            throw new CantGetCryptoAddressBookRecordException(CantGetCryptoAddressBookRecordException.DEFAULT_MESSAGE, exception, "", "Generic Exception.");
        }
    }

    public List<CryptoAddressBookRecord> listCryptoAddressBookRecordsByWalletPublicKey(String walletPublicKey) throws CantListCryptoAddressBookRecordsException {

        if (walletPublicKey == null) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, null, "", "walletPublicKey, can not be null");
        }

        try {
            DatabaseTable cryptoAddressBookTable = database.getTable(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
            cryptoAddressBookTable.addStringFilter(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublicKey, DatabaseFilterType.EQUAL);
            cryptoAddressBookTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoAddressBookTable.getRecords();

            List<CryptoAddressBookRecord> cryptoAddressBookRecords = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                cryptoAddressBookRecords.add(buildCryptoAddressBookRecord(record));
            }

            return cryptoAddressBookRecords;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException exception) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        } catch (InvalidCryptoAddressBookRecordParametersException exception) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, exception, "", "There's a problem with the data in database.");
        } catch (Exception exception) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, exception, "", "Generic Exception.");
        }
    }

    public List<CryptoAddressBookRecord> listCryptoAddressBookRecordsByDeliveredByActorPublicKey(String deliveredByActorPublicKey) throws CantListCryptoAddressBookRecordsException {

        if (deliveredByActorPublicKey == null) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, null, "", "deliveredByActorPublicKey, can not be null");
        }

        try {
            DatabaseTable cryptoAddressBookTable = database.getTable(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
            cryptoAddressBookTable.addStringFilter(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_DELIVERED_BY_ACTOR_PUBLIC_KEY_COLUMN_NAME, deliveredByActorPublicKey, DatabaseFilterType.EQUAL);
            cryptoAddressBookTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoAddressBookTable.getRecords();

            List<CryptoAddressBookRecord> cryptoAddressBookRecords = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                cryptoAddressBookRecords.add(buildCryptoAddressBookRecord(record));
            }

            return cryptoAddressBookRecords;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException exception) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        } catch (InvalidCryptoAddressBookRecordParametersException exception) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, exception, "", "There's a problem with the data in database.");
        }
    }

    public List<CryptoAddressBookRecord> listCryptoAddressBookRecordsByDeliveredToActorPublicKey(String deliveredToActorPublicKey) throws CantListCryptoAddressBookRecordsException {

        if (deliveredToActorPublicKey == null) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, null, "", "deliveredToActorPublicKey, can not be null");
        }

        try {
            DatabaseTable cryptoAddressBookTable = database.getTable(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
            cryptoAddressBookTable.addStringFilter(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_DELIVERED_TO_ACTOR_PUBLIC_KEY_COLUMN_NAME, deliveredToActorPublicKey, DatabaseFilterType.EQUAL);
            cryptoAddressBookTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoAddressBookTable.getRecords();

            List<CryptoAddressBookRecord> cryptoAddressBookRecords = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                cryptoAddressBookRecords.add(buildCryptoAddressBookRecord(record));
            }

            return cryptoAddressBookRecords;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException exception) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        } catch (InvalidCryptoAddressBookRecordParametersException exception) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, exception, "", "There's a problem with the data in database.");
        }
    }

    public void registerCryptoAddress(CryptoAddressBookRecord cryptoAddressBookRecord) throws CantRegisterCryptoAddressBookRecordException {

        if (cryptoAddressBookRecord == null) {
            throw new CantRegisterCryptoAddressBookRecordException(CantRegisterCryptoAddressBookRecordException.DEFAULT_MESSAGE, null, "", "cryptoAddressBookRecord, can not be null");
        }

        try {
            CryptoAddressBookRecord existingRecord = getCryptoAddressBookRecordByCryptoAddress(cryptoAddressBookRecord.getCryptoAddress());
            if (existingRecord != null)
                // the record already exists, won't insert it.
                return;
        } catch (Exception e) {
            //If there was an error, I will continue, because if it already exists, then I won't be able to insert it due to PK constraint.
        }

        try {
            DatabaseTable cryptoAddressBookTable = database.getTable(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
            DatabaseTableRecord entityRecord = buildDatabaseRecord(cryptoAddressBookTable.getEmptyRecord(), cryptoAddressBookRecord);

            cryptoAddressBookTable.insertRecord(entityRecord);

        } catch (CantInsertRecordException exception) {
            throw new CantRegisterCryptoAddressBookRecordException(CantRegisterCryptoAddressBookRecordException.DEFAULT_MESSAGE, exception, "", "There is a problem and i cannot insert the record in the table.");
        }
    }

    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord record, CryptoAddressBookRecord cryptoAddressBookRecord) {

        record.setStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_CRYPTO_ADDRESS_COLUMN_NAME               , cryptoAddressBookRecord.getCryptoAddress()            .getAddress());
        record.setStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_CRYPTO_CURRENCY_COLUMN_NAME              , cryptoAddressBookRecord.getCryptoAddress()            .getCryptoCurrency().getCode());
        record.setStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_DELIVERED_BY_ACTOR_PUBLIC_KEY_COLUMN_NAME, cryptoAddressBookRecord.getDeliveredByActorPublicKey());
        record.setStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_DELIVERED_BY_ACTOR_TYPE_COLUMN_NAME      , cryptoAddressBookRecord.getDeliveredByActorType()     .getCode());
        record.setStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_DELIVERED_TO_ACTOR_PUBLIC_KEY_COLUMN_NAME, cryptoAddressBookRecord.getDeliveredToActorPublicKey());
        record.setStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_DELIVERED_TO_ACTOR_TYPE_COLUMN_NAME      , cryptoAddressBookRecord.getDeliveredToActorType()     .getCode());
        record.setStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_PLATFORM_COLUMN_NAME                     , cryptoAddressBookRecord.getPlatform()                 .getCode());
        record.setStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_VAULT_TYPE_COLUMN_NAME                   , cryptoAddressBookRecord.getVaultType()                .getCode());
        record.setStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_VAULT_IDENTIFIER_COLUMN_NAME             , cryptoAddressBookRecord.getVaultIdentifier()          );
        record.setStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_WALLET_PUBLIC_KEY_COLUMN_NAME            , cryptoAddressBookRecord.getWalletPublicKey());
        record.setStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_WALLET_TYPE_COLUMN_NAME                  , cryptoAddressBookRecord.getWalletType()               .getCode());

        return record;
    }

    private CryptoAddressBookRecord buildCryptoAddressBookRecord(DatabaseTableRecord record) throws InvalidParameterException, InvalidCryptoAddressBookRecordParametersException {

        String address = record.getStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_CRYPTO_ADDRESS_COLUMN_NAME);
        CryptoCurrency currency = CryptoCurrency.getByCode(record.getStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_CRYPTO_CURRENCY_COLUMN_NAME));
        CryptoAddress cryptoAddress = new CryptoAddress(address, currency);

        String deliveredByActorPublicKey = record.getStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_DELIVERED_BY_ACTOR_PUBLIC_KEY_COLUMN_NAME);
        Actors deliveredByActorType = Actors.getByCode(record.getStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_DELIVERED_BY_ACTOR_TYPE_COLUMN_NAME));

        String deliveredToActorPublicKey = record.getStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_DELIVERED_TO_ACTOR_PUBLIC_KEY_COLUMN_NAME);
        Actors deliveredToActorType = Actors.getByCode(record.getStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_DELIVERED_TO_ACTOR_TYPE_COLUMN_NAME));

        Platforms platform = Platforms.getByCode(record.getStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_PLATFORM_COLUMN_NAME));

        VaultType vaultType = VaultType.getByCode(record.getStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_VAULT_TYPE_COLUMN_NAME));

        String vaultIdentifier = record.getStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_VAULT_IDENTIFIER_COLUMN_NAME);

        String walletPublicKey = record.getStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_WALLET_PUBLIC_KEY_COLUMN_NAME);
        ReferenceWallet walletType = ReferenceWallet.getByCode(record.getStringValue(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_WALLET_TYPE_COLUMN_NAME));

        return new CryptoAddressBookCryptoModuleRecord(
                cryptoAddress,
                deliveredByActorPublicKey,
                deliveredByActorType,
                deliveredToActorPublicKey,
                deliveredToActorType,
                platform,
                vaultType,
                vaultIdentifier,
                walletPublicKey,
                walletType
        );
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginIdentity interface implementation.
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * gets from database the list of crypto addresses for a given actor type
     * @param actorType
     * @return
     */
    public List<CryptoAddressBookRecord> listCryptoAddressBookRecordsByDeliveredToActorType(Actors actorType) throws CantListCryptoAddressBookRecordsException{
        if (actorType == null) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, null, "", "actorType, can not be null");
        }

        try {
            DatabaseTable cryptoAddressBookTable = database.getTable(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
            cryptoAddressBookTable.addStringFilter(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_DELIVERED_TO_ACTOR_TYPE_COLUMN_NAME, actorType.getCode(), DatabaseFilterType.EQUAL);
            cryptoAddressBookTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoAddressBookTable.getRecords();

            List<CryptoAddressBookRecord> cryptoAddressBookRecords = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                cryptoAddressBookRecords.add(buildCryptoAddressBookRecord(record));
            }

            return cryptoAddressBookRecords;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException exception) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        } catch (InvalidCryptoAddressBookRecordParametersException exception) {
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, exception, "", "There's a problem with the data in database.");
        }
    }
}