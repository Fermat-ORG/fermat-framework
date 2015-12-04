package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.CryptoCustomerIdentityPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions.CantGetCryptoCustomerIdentityPrivateKeyException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions.CantGetCryptoCustomerIdentityProfileImageException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeCryptoCustomerIdentityDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions.CantListCryptoCustomerIdentitiesException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerIdentityImpl;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 09.10.15.
 */
public class CryptoCustomerIdentityDatabaseDao implements DealsWithPluginDatabaseSystem {

    private PluginDatabaseSystem pluginDatabaseSystem;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    public CryptoCustomerIdentityDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    Database database;

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCryptoCustomerIdentityDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CryptoCustomerIdentityDatabaseFactory databaseFactory = new CryptoCustomerIdentityDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCryptoCustomerIdentityDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCryptoCustomerIdentityDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCryptoCustomerIdentityDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCryptoCustomerIdentityDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /*CREATE NEW IDENTITY*/
    public void createNewCryptoCustomerIdentity (final CryptoCustomerIdentity cryptoCustomer, final String privateKey,final DeviceUser deviceUser) throws CantCreateNewDeveloperException {
        try {
            if (aliasExists(cryptoCustomer.getAlias())) {
                throw new CantCreateNewDeveloperException ("Cant create new Crypto Customer Identity, alias exists.", "Crypto Customer Identity", "Cant create new Crypto Customer Identity, alias exists.");
            }
            persistNewCryptoCustomerIdentityPrivateKeysFile(cryptoCustomer.getPublicKey(), privateKey);
            DatabaseTable table = this.database.getTable(CryptoCustomerIdentityDatabaseConstants.CRYPTO_CUSTOMER_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CryptoCustomerIdentityDatabaseConstants.CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, cryptoCustomer.getPublicKey());
            record.setStringValue(CryptoCustomerIdentityDatabaseConstants.CRYPTO_CUSTOMER_ALIAS_COLUMN_NAME, cryptoCustomer.getAlias());
            record.setStringValue(CryptoCustomerIdentityDatabaseConstants.CRYPTO_CUSTOMER_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey());
            record.setIntegerValue(CryptoCustomerIdentityDatabaseConstants.CRYPTO_CUSTOMER_CRYPTO_CUSTOMER_PUBLIC_KEY_PUBLISHED_COLUMN_NAME, cryptoCustomer.isPublished() ? 1 : 0 );
            table.insertRecord(record);
            persistNewCryptoCustomerIdentityProfileImage(cryptoCustomer.getPublicKey(), cryptoCustomer.getProfileImage());
        } catch (CantInsertRecordException e){
            throw new CantCreateNewDeveloperException (e.getMessage(), e, "Crypto Customer Identity", "Cant create new Crypto Customer Identity, insert database problems.");
        } catch (CantPersistPrivateKeyException e){
            throw new CantCreateNewDeveloperException (e.getMessage(), e, "Crypto Customer Identity", "Cant create new Crypto Customer Identity,persist private key error.");
        } catch (Exception e) {
            throw new CantCreateNewDeveloperException (e.getMessage(), FermatException.wrapException(e), "Crypto Customer Identity", "Cant create new Crypto Customer Identity, unknown failure.");
        }
    }

    /*GENERATE LIST IDENTITY*/
    public List<CryptoCustomerIdentity> getAllCryptoCustomerIdentitiesFromCurrentDeviceUser (DeviceUser deviceUser) throws CantListCryptoCustomerIdentitiesException {
        List<CryptoCustomerIdentity> list = new ArrayList<CryptoCustomerIdentity>();
        DatabaseTable table;
        try {
            table = this.database.getTable (CryptoCustomerIdentityDatabaseConstants.CRYPTO_CUSTOMER_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException ("Cant get Crypto Customer Identity list, table not found.", "Crypto Customer Identity", "Cant get Crypto Customer Identity list, table not found.");
            }
            table.setStringFilter(CryptoCustomerIdentityDatabaseConstants.CRYPTO_CUSTOMER_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey(), DatabaseFilterType.EQUAL);
            table.loadToMemory();

            for (DatabaseTableRecord record : table.getRecords ()) {
                list.add(getIdentityFromRecord(record));
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListCryptoCustomerIdentitiesException(e.getMessage(), e, "Crypto Customer Identity", "Cant load " + CryptoCustomerIdentityDatabaseConstants.CRYPTO_CUSTOMER_TABLE_NAME + " table in memory.");
        } catch (CantGetCryptoCustomerIdentityPrivateKeyException e) {
            throw new CantListCryptoCustomerIdentitiesException(e.getMessage(), e, "Crypto Customer Identity", "Can't get private key.");
        } catch (Exception e) {
            throw new CantListCryptoCustomerIdentitiesException(e.getMessage(), FermatException.wrapException(e), "Crypto Customer Identity", "Cant get Crypto Customer Identity list, unknown failure.");
        }
        return list;
    }


    /*GET PROFILE IMAGE PRIVATE KEY*/
    private byte[] getCryptoCustomerIdentityProfileImagePrivateKey(String publicKey) throws CantGetCryptoCustomerIdentityProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    CryptoCustomerIdentityPluginRoot.CRYPTO_CUSTOMER_IDENTITY_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.loadFromMedia();
            profileImage = file.getContent();
        } catch (CantLoadFileException e) {
            throw new CantGetCryptoCustomerIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);
        } catch (FileNotFoundException |CantCreateFileException e) {
            throw new CantGetCryptoCustomerIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            throw  new CantGetCryptoCustomerIdentityProfileImageException("CAN'T GET IMAGE PROFILE ",FermatException.wrapException(e),"", "");
        }
        return profileImage;
    }
    
    /*INTERFACE IMPLEMENTATION*/
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*CREATE FILE THE PRIVATE KEY*/
    private void  persistNewCryptoCustomerIdentityPrivateKeysFile(String publicKey,String privateKey) throws CantPersistPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.createTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    CryptoCustomerIdentityPluginRoot.CRYPTO_CUSTOMER_IDENTITY_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.setContent(privateKey);
            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error creating file.", null);
        } catch (Exception e) {
            throw  new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ",FermatException.wrapException(e),"", "");
        }
    }

    /*CREATE FILE THE PROFILE IMEAGE*/
    private void  persistNewCryptoCustomerIdentityProfileImage(String publicKey,byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    CryptoCustomerIdentityPluginRoot.CRYPTO_CUSTOMER_IDENTITY_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.setContent(profileImage);
            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
        } catch (Exception e) {
            throw  new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ",FermatException.wrapException(e),"", "");
        }
    }

    /*GET PRIVATE KEY*/
    public String getCryptoCustomerIdentityPrivateKey(String publicKey) throws CantGetCryptoCustomerIdentityPrivateKeyException {
        String privateKey = "";
        try {
            PluginTextFile file = this.pluginFileSystem.getTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    CryptoCustomerIdentityPluginRoot.CRYPTO_CUSTOMER_IDENTITY_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.loadFromMedia();
            privateKey = file.getContent();
        } catch (CantLoadFileException e) {
            throw new CantGetCryptoCustomerIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error loaded file.", null);
        } catch (FileNotFoundException |CantCreateFileException e) {
            throw new CantGetCryptoCustomerIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            throw  new CantGetCryptoCustomerIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ",FermatException.wrapException(e),"", "");
        }
        return privateKey;
    }

    /*GET ALIAS IDENTITY*/
    private boolean aliasExists (String alias) throws CantCreateNewDeveloperException {
        DatabaseTable table;
        try {
            table = this.database.getTable (CryptoCustomerIdentityDatabaseConstants.CRYPTO_CUSTOMER_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists", "Crypto Customer Identity", "");
            }
            table.setStringFilter(CryptoCustomerIdentityDatabaseConstants.CRYPTO_CUSTOMER_ALIAS_COLUMN_NAME, alias, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords ().size () > 0;
        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException (em.getMessage(), em, "Crypto Customer Identity", "Cant load " + CryptoCustomerIdentityDatabaseConstants.CRYPTO_CUSTOMER_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantCreateNewDeveloperException (e.getMessage(), FermatException.wrapException(e), "Crypto Customer Identity", "unknown failure.");
        }
    }

    private CryptoCustomerIdentity getIdentityFromRecord(final DatabaseTableRecord record) throws CantGetCryptoCustomerIdentityProfileImageException, CantGetCryptoCustomerIdentityPrivateKeyException {
        String alias = record.getStringValue(CryptoCustomerIdentityDatabaseConstants.CRYPTO_CUSTOMER_ALIAS_COLUMN_NAME);
        String privateKey = getCryptoCustomerIdentityPrivateKey(record.getStringValue(CryptoCustomerIdentityDatabaseConstants.CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
        byte[] profileImage = getCryptoCustomerIdentityProfileImagePrivateKey(record.getStringValue(CryptoCustomerIdentityDatabaseConstants.CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
        boolean published = record.getIntegerValue(CryptoCustomerIdentityDatabaseConstants.CRYPTO_CUSTOMER_CRYPTO_CUSTOMER_PUBLIC_KEY_PUBLISHED_COLUMN_NAME) == 1;
        KeyPair keyPair = AsymmetricCryptography.createKeyPair(privateKey);
        return new CryptoCustomerIdentityImpl(alias, keyPair, profileImage, published);
    }

}
