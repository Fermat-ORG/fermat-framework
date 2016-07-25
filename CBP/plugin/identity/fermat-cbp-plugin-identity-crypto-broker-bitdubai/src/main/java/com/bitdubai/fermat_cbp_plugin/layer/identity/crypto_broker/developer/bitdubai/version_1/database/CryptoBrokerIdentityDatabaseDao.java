package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
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
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUpdateBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityExtraData;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.CryptoBrokerIdentityPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantChangeExposureLevelException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantGetCryptoBrokerIdentityPrivateKeyException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantGetCryptoBrokerIdentityProfileImageException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantGetIdentityException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerIdentityDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerIdentityImpl;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Yordin Alayn on 09.10.15.
 */
public class CryptoBrokerIdentityDatabaseDao implements DealsWithPluginDatabaseSystem {

    private PluginDatabaseSystem pluginDatabaseSystem;
    private PluginFileSystem pluginFileSystem;
    private UUID pluginId;
    private Database database;

    public CryptoBrokerIdentityDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    public void initialize() throws CantInitializeCryptoBrokerIdentityDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, CryptoBrokerIdentityDatabaseConstants.DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {
            try {
                CryptoBrokerIdentityDatabaseFactory databaseFactory = new CryptoBrokerIdentityDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, CryptoBrokerIdentityDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCryptoBrokerIdentityDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCryptoBrokerIdentityDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCryptoBrokerIdentityDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCryptoBrokerIdentityDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    public void createNewCryptoBrokerIdentity(final CryptoBrokerIdentity cryptoBroker, final String privateKey, final DeviceUser deviceUser) throws CantCreateNewDeveloperException {
        try {
            if (aliasExists(cryptoBroker.getAlias())) {
                throw new CantCreateNewDeveloperException("Cant create new Crypto Broker Identity, alias exists.", "Crypto Broker Identity", "Cant create new Crypto Broker Identity, alias exists.");
            }

            persistNewCryptoBrokerIdentityPrivateKeysFile(cryptoBroker.getPublicKey(), privateKey);
            DatabaseTable table = this.database.getTable(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_TABLE_NAME);
            CryptoBrokerIdentityExtraData cryptoBrokerExtraData;

            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, cryptoBroker.getPublicKey());
            record.setStringValue(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_ALIAS_COLUMN_NAME, cryptoBroker.getAlias());
            record.setStringValue(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey());
            record.setFermatEnum(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_EXPOSURE_LEVEL_COLUMN_NAME, cryptoBroker.getExposureLevel());
            record.setLongValue(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_ACCURACY_COLUMN_NAME, cryptoBroker.getAccuracy());
            record.setFermatEnum(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_FRECUENCY_COLUMN_NAME, cryptoBroker.getFrequency());
            //New fields
            cryptoBrokerExtraData = cryptoBroker.getCryptoBrokerIdentityExtraData();
            record.setFermatEnum(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_PAYMENT_CURRENCY_COLUMN_NAME, cryptoBrokerExtraData.getPaymentCurrency());
            record.setFermatEnum(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_MERCHANDISE_CURRENCY_COLUMN_NAME, cryptoBrokerExtraData.getMerchandise());
            record.setStringValue(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_EXTRA_TEXT_COLUMN_NAME, cryptoBrokerExtraData.getExtraText());

            table.insertRecord(record);

            persistNewCryptoBrokerIdentityProfileImage(cryptoBroker.getPublicKey(), cryptoBroker.getProfileImage());

        } catch (CantInsertRecordException e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Crypto Broker Identity", "Cant create new Crypto Broker Identity, insert database problems.");
        } catch (CantPersistPrivateKeyException e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Crypto Broker Identity", "Cant create new Crypto Broker Identity,persist private key error.");
        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Crypto Broker Identity", "Cant create new Crypto Broker Identity, unknown failure.");
        }
    }

    public void updateCryptoBrokerIdentity(String alias, String publicKey, byte[] imageProfile,
                                           long accuracy,
                                           GeoFrequency frequency) throws CantUpdateBrokerIdentityException {

        try {
            DatabaseTable table = this.database.getTable(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_TABLE_NAME);

            DatabaseTableRecord record = table.getEmptyRecord();
            table.addStringFilter(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            record.setStringValue(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_ALIAS_COLUMN_NAME, alias);
            record.setLongValue(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_ACCURACY_COLUMN_NAME, accuracy);
            record.setFermatEnum(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_FRECUENCY_COLUMN_NAME, frequency);

            table.updateRecord(record);

            updateCryptoBrokerIdentityProfileImage(publicKey, imageProfile);

        } catch (Exception e) {
            throw new CantUpdateBrokerIdentityException(
                    e.getMessage(),
                    e,
                    "Updating the Crypto Broker Identity",
                    "Unexpected exception, please, check the cause");
        }
    }

    /**
     * This method updates a stored CryptoBrokerIdentity
     *
     * @param cryptoBrokerIdentity
     * @throws CantUpdateBrokerIdentityException
     */
    public void updateCryptoBrokerIdentity(CryptoBrokerIdentity cryptoBrokerIdentity)
            throws CantUpdateBrokerIdentityException {
        try {
            DatabaseTable table = this.database.getTable(
                    CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            CryptoBrokerIdentityExtraData cryptoBrokerExtraData;
            String publicKey = cryptoBrokerIdentity.getPublicKey();
            String alias = cryptoBrokerIdentity.getAlias();
            long accuracy = cryptoBrokerIdentity.getAccuracy();
            GeoFrequency frequency = cryptoBrokerIdentity.getFrequency();
            byte[] imageProfile = cryptoBrokerIdentity.getProfileImage();
            table.addStringFilter(
                    CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME,
                    publicKey,
                    DatabaseFilterType.EQUAL);
            record.setStringValue(
                    CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_ALIAS_COLUMN_NAME,
                    alias);
            record.setLongValue(
                    CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_ACCURACY_COLUMN_NAME,
                    accuracy);
            record.setFermatEnum(
                    CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_FRECUENCY_COLUMN_NAME,
                    frequency);

            cryptoBrokerExtraData = cryptoBrokerIdentity.getCryptoBrokerIdentityExtraData();
            record.setFermatEnum(
                    CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_PAYMENT_CURRENCY_COLUMN_NAME,
                    cryptoBrokerExtraData.getPaymentCurrency());
            record.setFermatEnum(
                    CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_MERCHANDISE_CURRENCY_COLUMN_NAME,
                    cryptoBrokerExtraData.getMerchandise());
            record.setStringValue(
                    CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_EXTRA_TEXT_COLUMN_NAME,
                    cryptoBrokerExtraData.getExtraText());

            table.updateRecord(record);

            updateCryptoBrokerIdentityProfileImage(publicKey, imageProfile);

        } catch (Exception e) {
            throw new CantUpdateBrokerIdentityException(
                    e.getMessage(),
                    e,
                    "Updating the Crypto Broker Identity",
                    "Unexpected exception, please, check the cause");
        }
    }

    public final List<CryptoBrokerIdentity> listIdentitiesFromDeviceUser(final DeviceUser deviceUser) throws CantListCryptoBrokerIdentitiesException {
        try {
            final DatabaseTable table = this.database.getTable(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_TABLE_NAME);
            table.addStringFilter(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            final List<CryptoBrokerIdentity> list = new ArrayList<>();
            for (DatabaseTableRecord record : table.getRecords())
                list.add(getIdentityFromRecord(record));
            return list;
        } catch (final CantGetCryptoBrokerIdentityProfileImageException e) {
            throw new CantListCryptoBrokerIdentitiesException(e.getMessage(), e, "Crypto Broker Identity", "Problem trying to get the profile image of the identity.");
        } catch (final CantLoadTableToMemoryException e) {
            throw new CantListCryptoBrokerIdentitiesException(e.getMessage(), e, "Crypto Broker Identity", new StringBuilder().append("Cant load ").append(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_TABLE_NAME).append(" table in memory.").toString());
        } catch (final CantGetCryptoBrokerIdentityPrivateKeyException e) {
            throw new CantListCryptoBrokerIdentitiesException(e.getMessage(), e, "Crypto Broker Identity", "Can't get private key.");
        } catch (final InvalidParameterException e) {
            throw new CantListCryptoBrokerIdentitiesException(e.getMessage(), FermatException.wrapException(e), "Crypto Broker Identity", "Error trying to identify some enum.");
        }
    }

    public final void changeExposureLevel(final String publicKey,
                                          final ExposureLevel exposureLevel) throws CantChangeExposureLevelException, IdentityNotFoundException {
        try {
            final DatabaseTable table = this.database.getTable(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_TABLE_NAME);
            table.addStringFilter(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);
                record.setFermatEnum(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_EXPOSURE_LEVEL_COLUMN_NAME, exposureLevel);
                table.updateRecord(record);
            } else
                throw new IdentityNotFoundException(new StringBuilder().append("publicKey: ").append(publicKey).toString(), "Cannot find an Identity with that publicKey.");
        } catch (final CantUpdateRecordException e) {
            throw new CantChangeExposureLevelException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        } catch (final CantLoadTableToMemoryException e) {
            throw new CantChangeExposureLevelException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    public final CryptoBrokerIdentity getIdentity(final String publicKey) throws CantGetIdentityException, IdentityNotFoundException {
        try {
            final DatabaseTable table = this.database.getTable(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_TABLE_NAME);
            table.addStringFilter(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            if (!records.isEmpty())
                return getIdentityFromRecord(records.get(0));
            else
                throw new IdentityNotFoundException(new StringBuilder().append("publicKey: ").append(publicKey).toString(), "Cannot find a Broker Identity with that publicKey.");

        } catch (CantGetCryptoBrokerIdentityProfileImageException |
                CantGetCryptoBrokerIdentityPrivateKeyException |
                InvalidParameterException e) {
            throw new CantGetIdentityException(e, "", "Exception not handled by the plugin, there is a problem in database and I cannot update the record.");

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetIdentityException(e, "", "Exception not handled by the plugin, there is a problem in database and  cannot load the table.");
        }
    }

    private byte[] getCryptoBrokerIdentityProfileImagePrivateKey(String publicKey) throws CantGetCryptoBrokerIdentityProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    new StringBuilder().append(CryptoBrokerIdentityPluginRoot.CRYPTO_BROKER_IDENTITY_PROFILE_IMAGE_FILE_NAME).append("_").append(publicKey).toString(),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.loadFromMedia();
            profileImage = file.getContent();
        } catch (CantLoadFileException e) {
            throw new CantGetCryptoBrokerIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetCryptoBrokerIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            throw new CantGetCryptoBrokerIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", FermatException.wrapException(e), "", "");
        }
        return profileImage;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    private void persistNewCryptoBrokerIdentityPrivateKeysFile(String publicKey, String privateKey) throws CantPersistPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.createTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    new StringBuilder().append(CryptoBrokerIdentityPluginRoot.CRYPTO_BROKER_IDENTITY_PRIVATE_KEYS_FILE_NAME).append("_").append(publicKey).toString(),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.setContent(privateKey);
            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error creating file.", null);
        }
    }

    private void persistNewCryptoBrokerIdentityProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    new StringBuilder().append(CryptoBrokerIdentityPluginRoot.CRYPTO_BROKER_IDENTITY_PROFILE_IMAGE_FILE_NAME).append("_").append(publicKey).toString(),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.setContent(profileImage);
            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
        }
    }

    private String getCryptoBrokerIdentityPrivateKey(String publicKey) throws CantGetCryptoBrokerIdentityPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.getTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    new StringBuilder().append(CryptoBrokerIdentityPluginRoot.CRYPTO_BROKER_IDENTITY_PRIVATE_KEYS_FILE_NAME).append("_").append(publicKey).toString(),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.loadFromMedia();
            return file.getContent();
        } catch (CantLoadFileException e) {
            throw new CantGetCryptoBrokerIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error loaded file.", null);
        } catch (CantCreateFileException e) {
            throw new CantGetCryptoBrokerIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error getting developer identity private keys file.", null);
        } catch (FileNotFoundException e) {
            throw new CantGetCryptoBrokerIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error getting developer identity private keys file.", "File not found.");
        }
    }

    private boolean aliasExists(String alias) throws CantCreateNewDeveloperException {
        try {
            DatabaseTable table = this.database.getTable(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_TABLE_NAME);
            table.addStringFilter(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_ALIAS_COLUMN_NAME, alias, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords().size() > 0;
        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Crypto Broker Identity", new StringBuilder().append("Cant load ").append(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_TABLE_NAME).append(" table in memory.").toString());
        }
    }

    private CryptoBrokerIdentity getIdentityFromRecord(final DatabaseTableRecord record) throws CantGetCryptoBrokerIdentityPrivateKeyException, CantGetCryptoBrokerIdentityProfileImageException, InvalidParameterException {
        String alias = record.getStringValue(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_ALIAS_COLUMN_NAME);
        String privateKey = getCryptoBrokerIdentityPrivateKey(record.getStringValue(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME));
        byte[] profileImage = getCryptoBrokerIdentityProfileImagePrivateKey(record.getStringValue(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME));
        ExposureLevel published = ExposureLevel.getByCode(record.getStringValue(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_EXPOSURE_LEVEL_COLUMN_NAME));
        KeyPair keyPair = AsymmetricCryptography.createKeyPair(privateKey);
        long accuracy = record.getLongValue(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_ACCURACY_COLUMN_NAME);
        GeoFrequency frequency = GeoFrequency.getByCode(record.getStringValue(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_FRECUENCY_COLUMN_NAME));
        //New fields
        String paymentString = record.getStringValue(
                CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_PAYMENT_CURRENCY_COLUMN_NAME);
        Currency paymentCurrency = getCurrencyFromString(paymentString);
        String merchandiseString = record.getStringValue(
                CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_MERCHANDISE_CURRENCY_COLUMN_NAME);
        Currency merchandiseCurrency = getCurrencyFromString(merchandiseString);
        String brokerExtraTest = record.getStringValue(
                CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_EXTRA_TEXT_COLUMN_NAME);
        CryptoBrokerIdentityExtraData cryptoBrokerIdentityExtraData = new CryptoBrokerIdentityExtraData(
                merchandiseCurrency,
                paymentCurrency,
                brokerExtraTest);
        return new CryptoBrokerIdentityImpl(
                alias,
                keyPair,
                profileImage,
                published,
                accuracy,
                frequency,
                cryptoBrokerIdentityExtraData);

    }

    private Currency getCurrencyFromString(String currencyString) {
        try {
            FiatCurrency fiatCurrency = FiatCurrency.getByCode(currencyString);
            return fiatCurrency;
        } catch (InvalidParameterException e) {
            try {
                CryptoCurrency cryptoCurrency = CryptoCurrency.getByCode(currencyString);
                return cryptoCurrency;
            } catch (InvalidParameterException e1) {
                //If everything fails, I'll return Bitcoin
                return CryptoCurrency.BITCOIN;
            }
        }
    }

    private void updateCryptoBrokerIdentityProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {
        try {
            this.pluginFileSystem.deleteBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    new StringBuilder().append(CryptoBrokerIdentityPluginRoot.CRYPTO_BROKER_IDENTITY_PRIVATE_KEYS_FILE_NAME).append("_").append(publicKey).toString(),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT);

            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    new StringBuilder().append(CryptoBrokerIdentityPluginRoot.CRYPTO_BROKER_IDENTITY_PROFILE_IMAGE_FILE_NAME).append("_").append(publicKey).toString(),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.setContent(profileImage);
            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
        } catch (FileNotFoundException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error removing file.", null);
        }
    }

}
