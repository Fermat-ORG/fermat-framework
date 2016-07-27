package org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.database;

import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;

import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantCreateNewDeveloperException;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetUserDeveloperIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.exceptions.CantGetAssetUserIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.exceptions.CantUpdateIdentityAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.AssetUserIdentityPluginRoot;
import org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.exceptions.CantGetAssetUserIdentityPrivateKeyException;
import org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.exceptions.CantGetAssetUserIdentityProfileImageException;
import org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.exceptions.CantInitializeAssetUserIdentityDatabaseException;
import org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.exceptions.CantListAssetUserIdentitiesException;
import org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.exceptions.CantPersistPrivateKeyException;
import org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.exceptions.CantPersistProfileImageException;
import org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.structure.IdentityAssetUsermpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 02/11/15.
 */
public class AssetUserIdentityDao implements DealsWithPluginDatabaseSystem {
    /**
     * Represent the Plugin Database.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    /**
     * Constructor with parameters
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */

    public AssetUserIdentityDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) throws CantInitializeAssetUserIdentityDatabaseException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;

        try {
            initializeDatabase();
        } catch (CantInitializeAssetUserIdentityDatabaseException e) {
            throw new CantInitializeAssetUserIdentityDatabaseException(e.getMessage());
        }
    }

    /**
     * Represent de Database where i will be working with
     */
    Database database;

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.exceptions.CantInitializeAssetUserIdentityDatabaseException
     */
    private void initializeDatabase() throws CantInitializeAssetUserIdentityDatabaseException {
        try {

             /*
              * Open new database connection
              */

            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_DB_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeAssetUserIdentityDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetUserIdentityDatabaseFactory assetUserIdentityDatabaseFactory = new AssetUserIdentityDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = assetUserIdentityDatabaseFactory.createDatabase(pluginId);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetUserIdentityDatabaseException(cantCreateDatabaseException.getMessage());
            }
        } catch (Exception e) {
            throw new CantInitializeAssetUserIdentityDatabaseException(e.getMessage());
        }
    }

    /**
     * first i persist private key on a file
     * second i insert the record in database
     * third i save the profile image file
     *
     * @param alias
     * @param publicKey
     * @param privateKey
     * @param deviceUser
     * @param profileImage
     * @throws CantCreateNewDeveloperException
     */
    public void createNewUser(String alias, String publicKey, String privateKey, DeviceUser deviceUser, byte[] profileImage, int accuracy, GeoFrequency frequency) throws CantCreateNewDeveloperException {

        try {
            if (aliasExists(alias)) {
                throw new CantCreateNewDeveloperException("Cant create new Asset User, alias exists.", "Asset User Identity", "Cant create new Asset User, alias exists.");
            }

            persistNewUserPrivateKeysFile(publicKey, privateKey);

            DatabaseTable table = this.database.getTable(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setStringValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publicKey);
            record.setStringValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_ALIAS_COLUMN_NAME, alias);
            record.setStringValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey());
            record.setIntegerValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_ACCURACY_KEY_COLUMN, accuracy);
            record.setStringValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_FREQUENCY_KEY_COLUMN, frequency.getCode());

            table.insertRecord(record);

            if (profileImage != null)
                persistNewUserProfileImage(publicKey, profileImage);

        } catch (CantInsertRecordException e) {
            // Cant insert record.
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Asset User Identity", "Cant create new Asset User, insert database problems.");

        } catch (org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.exceptions.CantPersistPrivateKeyException e) {
            // Cant insert record.
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Asset User Identity", "Cant create new Asset User,persist private key error.");
        } catch (Exception e) {
            // Failure unknown.
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Asset User Identity", "Cant create new Asset User, unknown failure.");
        }
    }

    public void updateIdentityAssetUser(String publicKey, String alias, byte[] profileImage, int accuracy, GeoFrequency frequency) throws CantUpdateIdentityAssetUserException {
        try {
            /**
             * 1) Get the table.
             */
            DatabaseTable table = this.database.getTable(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Asset User Identity list, table not found.", "Asset User Identity", "Cant get Asset User Identity list, table not found.");
            }

            // 2) Find the Intra users.
            table.addStringFilter(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            // 3) Get Intra users.
            for (DatabaseTableRecord record : table.getRecords()) {
                //set new values
                record.setStringValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_ALIAS_COLUMN_NAME, alias);
                record.setIntegerValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_ACCURACY_KEY_COLUMN, accuracy);
                record.setStringValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_FREQUENCY_KEY_COLUMN, frequency.getCode());
                table.updateRecord(record);
            }

            if (profileImage != null)
                persistNewUserProfileImage(publicKey, profileImage);

        } catch (CantUpdateRecordException e) {
            throw new CantUpdateIdentityAssetUserException(e.getMessage(), e, "Asset User Identity", "Cant update Asset User Identity, database problems.");
        } catch (org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.exceptions.CantPersistProfileImageException e) {
            throw new CantUpdateIdentityAssetUserException(e.getMessage(), e, "Asset User Identity", "Cant update Asset User Identity, persist image error.");
        } catch (Exception e) {
            throw new CantUpdateIdentityAssetUserException(e.getMessage(), FermatException.wrapException(e), "Asset User Identity", "Cant update Asset User Identity, unknown failure.");
        }
    }

    public IdentityAssetUser getIdentityAssetUser() throws CantGetAssetUserIdentitiesException {

        // Setup method.
        IdentityAssetUser IdentityAssetUserrRecord = null;
        DatabaseTable table; // Intra User table.
        // Get Asset Issuers identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Asset Issuer identity list, table not found.", "Asset IssuerIdentity", "Cant get Intra User identity list, table not found.");
            }
            // 2) Find the Identity Issuers.

//            table.addStringFilter(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey(), DatabaseFilterType.EQUAL);
            table.loadToMemory();

            // 3) Get Identity Issuers.

            for (DatabaseTableRecord record : table.getRecords()) {

                // Add records to list.
                IdentityAssetUserrRecord = new IdentityAssetUsermpl(
                        record.getStringValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_ALIAS_COLUMN_NAME),
                        record.getStringValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                        getAssetUserProfileImagePrivateKey(record.getStringValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        record.getIntegerValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_ACCURACY_KEY_COLUMN),
                        GeoFrequency.getByCode(record.getStringValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_FREQUENCY_KEY_COLUMN)));
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUserIdentitiesException(e.getMessage(), e, "Asset User Identity", "Cant load " + AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetAssetUserIdentitiesException(e.getMessage(), FermatException.wrapException(e), "Asset User Identity", "Cant get Asset User identity list, unknown failure.");
        }
        // Return the list values.
        return IdentityAssetUserrRecord;
    }

    public List<IdentityAssetUser> getIdentityAssetUsersFromCurrentDeviceUser(DeviceUser deviceUser) throws CantListAssetUserIdentitiesException {
        // Setup method.
        List<IdentityAssetUser> list = new ArrayList<IdentityAssetUser>(); // Intra User list.
        DatabaseTable table; // Intra User table.

        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Asset User identity list, table not found.", "Asset User", "Cant get Intra User identity list, table not found.");
            }
            // 2) Find the Intra users.
            table.addStringFilter(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            // 3) Get Intra users.
            for (DatabaseTableRecord record : table.getRecords()) {

                // Add records to list.
                list.add(new IdentityAssetUsermpl(record.getStringValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_ALIAS_COLUMN_NAME),
                        record.getStringValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                        getAssetUserIdentityPrivateKey(record.getStringValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        getAssetUserProfileImagePrivateKey(record.getStringValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        record.getIntegerValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_ACCURACY_KEY_COLUMN),
                        GeoFrequency.getByCode(record.getStringValue(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_FREQUENCY_KEY_COLUMN))));
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.exceptions.CantListAssetUserIdentitiesException(e.getMessage(), e, "Asset User Identity", "Cant load " + AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_TABLE_NAME + " table in memory.");
        } catch (org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.exceptions.CantGetAssetUserIdentityPrivateKeyException e) {
            // Failure unknown.
            throw new org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.exceptions.CantListAssetUserIdentitiesException(e.getMessage(), e, "Asset User Identity", "Can't get private key.");

        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.exceptions.CantListAssetUserIdentitiesException(e.getMessage(), FermatException.wrapException(e), "Asset User Identity", "Cant get Asset User identity list, unknown failure.");
        }

        // Return the list values.
        return list;
    }

    public byte[] getAssetUserProfileImagePrivateKey(String publicKey) throws CantGetAssetUserIdentityProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    AssetUserIdentityPluginRoot.ASSET_USER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetAssetUserIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            profileImage = new byte[0];
            // throw new CantGetIntraWalletUserIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            throw new CantGetAssetUserIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", FermatException.wrapException(e), "", "");
        }

        return profileImage;
    }

    /**
     * Private Methods
     */

    private void persistNewUserPrivateKeysFile(String publicKey, String privateKey) throws CantPersistPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.createTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.AssetUserIdentityPluginRoot.ASSET_USER_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
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
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", FermatException.wrapException(e), "", "");
        }
    }

    private void persistNewUserProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.AssetUserIdentityPluginRoot.ASSET_USER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
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
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * <p>Method that check if alias exists.
     *
     * @param alias
     * @return boolean exists
     * @throws CantCreateNewDeveloperException
     */
    private boolean aliasExists(String alias) throws CantCreateNewDeveloperException {


        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */

        try {
            table = this.database.getTable(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists", "Asset User Identity", "");
            }

            table.addStringFilter(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_ALIAS_COLUMN_NAME, alias, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;


        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Asset User  Identity", "Cant load " + AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Asset User  Identity", "unknown failure.");
        }
    }

    public String getAssetUserIdentityPrivateKey(String publicKey) throws CantGetAssetUserIdentityPrivateKeyException {
        String privateKey = "";
        try {
            PluginTextFile file = this.pluginFileSystem.getTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.AssetUserIdentityPluginRoot.ASSET_USER_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.loadFromMedia();

            privateKey = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetAssetUserIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetAssetUserIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            throw new CantGetAssetUserIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", FermatException.wrapException(e), "", "");
        }
        return privateKey;
    }
}
