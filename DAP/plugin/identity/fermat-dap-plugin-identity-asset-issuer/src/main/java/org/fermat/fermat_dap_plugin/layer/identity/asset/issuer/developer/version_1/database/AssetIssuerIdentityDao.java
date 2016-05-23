package org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.database;

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

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantCreateNewDeveloperException;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetUserDeveloperIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantGetAssetIssuerIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantUpdateIdentityAssetIssuerException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.AssetIssuerIdentityPluginRoot;
import org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuerIdentityPrivateKeyException;
import org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuerIdentityProfileImageException;
import org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerIdentityDatabaseException;
import org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.exceptions.CantListAssetIssuerIdentitiesException;
import org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.exceptions.CantPersistPrivateKeyException;
import org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.exceptions.CantPersistProfileImageException;
import org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.structure.IdentityAssetIssuerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 02/11/15.
 */
public class AssetIssuerIdentityDao implements DealsWithPluginDatabaseSystem {
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

    public AssetIssuerIdentityDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) throws CantInitializeAssetIssuerIdentityDatabaseException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;

        try {
            initializeDatabase();
        } catch (CantInitializeAssetIssuerIdentityDatabaseException e) {
            throw new CantInitializeAssetIssuerIdentityDatabaseException(e.getMessage());
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
     * @throws CantInitializeAssetIssuerIdentityDatabaseException
     */
    private void initializeDatabase() throws CantInitializeAssetIssuerIdentityDatabaseException {
        try {

             /*
              * Open new database connection
              */

            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_DB_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeAssetIssuerIdentityDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetIssuerIdentityDatabaseFactory assetIssuerIdentityDatabaseFactory = new AssetIssuerIdentityDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = assetIssuerIdentityDatabaseFactory.createDatabase(pluginId);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetIssuerIdentityDatabaseException(cantCreateDatabaseException.getMessage());
            }
        } catch (Exception e) {

            throw new CantInitializeAssetIssuerIdentityDatabaseException(e.getMessage());

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
    public void createNewUser(String alias, String publicKey, String privateKey, DeviceUser deviceUser, byte[] profileImage) throws CantCreateNewDeveloperException {

        try {
            if (aliasExists(alias)) {
                throw new CantCreateNewDeveloperException("Cant create new Asset Issuer, alias exists.", "Asset Issuer Identity", "Cant create new Asset Issuer, alias exists.");
            }

            persistNewUserPrivateKeysFile(publicKey, privateKey);

            DatabaseTable table = this.database.getTable(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setStringValue(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publicKey);
            record.setStringValue(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_ALIAS_COLUMN_NAME, alias);
            record.setStringValue(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey());

            table.insertRecord(record);

            if (profileImage != null)
                persistNewUserProfileImage(publicKey, profileImage);

        } catch (CantInsertRecordException e) {
            // Cant insert record.
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Asset Issuer Identity", "Cant create new Asset Issuer, insert database problems.");
        } catch (CantPersistPrivateKeyException e) {
            // Cant insert record.
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Asset Issuer Identity", "Cant create new Asset Issuer,persist private key error.");
        } catch (Exception e) {
            // Failure unknown.
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Identity", "Cant create new Asset Issuer, unknown failure.");
        }
    }

    public void updateIdentityAssetIssuer(String publicKey, String alias, byte[] profileImage) throws CantUpdateIdentityAssetIssuerException {
        try {
            /**
             * 1) Get the table.
             */
            DatabaseTable table = this.database.getTable(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Asset Issuer Identity list, table not found.", "Asset Issuer Identity", "Cant get Asset Issuer Identity list, table not found.");
            }

            // 2) Find the Intra users.
            table.addStringFilter(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();


            // 3) Get Intra users.
            for (DatabaseTableRecord record : table.getRecords()) {
                //set new values
                record.setStringValue(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_ALIAS_COLUMN_NAME, alias);
                table.updateRecord(record);
            }

            if (profileImage != null)
                persistNewUserProfileImage(publicKey, profileImage);

        } catch (CantUpdateRecordException e) {
            throw new CantUpdateIdentityAssetIssuerException(e.getMessage(), e, "Asset Issuer Identity", "Cant update Asset Issuer Identity, database problems.");
        } catch (CantPersistProfileImageException e) {
            throw new CantUpdateIdentityAssetIssuerException(e.getMessage(), e, "Asset Issuer Identity", "Cant update Asset Issuer Identity, persist image error.");
        } catch (Exception e) {
            throw new CantUpdateIdentityAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Identity", "Cant update Asset Issuer Identity, unknown failure.");
        }
    }

    public IdentityAssetIssuer getIdentityAssetIssuer() throws CantGetAssetIssuerIdentitiesException {

        // Setup method.
        IdentityAssetIssuer IdentityAssetIssuerRecord = null;
        DatabaseTable table; // Intra User table.

        // Get Asset Issuers identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_TABLE_NAME);

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
                IdentityAssetIssuerRecord = new IdentityAssetIssuerImpl(
                        record.getStringValue(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_ALIAS_COLUMN_NAME),
                        record.getStringValue(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                        getAssetIssuerProfileImagePrivateKey(record.getStringValue(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_PUBLIC_KEY_COLUMN_NAME)));
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetIssuerIdentitiesException(e.getMessage(), e, "Asset Issuer Identity", "Cant load " + AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetAssetIssuerIdentitiesException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Identity", "Cant get Asset Issuer identity list, unknown failure.");
        }

        // Return the list values.
        return IdentityAssetIssuerRecord;
    }

    public List<IdentityAssetIssuer> getIdentityAssetIssuersFromCurrentDeviceUser(DeviceUser deviceUser) throws CantListAssetIssuerIdentitiesException {


        // Setup method.
        List<IdentityAssetIssuer> list = new ArrayList<IdentityAssetIssuer>(); // Issuer list.
        DatabaseTable table; // Intra User table.

        // Get Asset Issuers identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Asset Issuer identity list, table not found.", "Asset IssuerIdentity", "Cant get Intra User identity list, table not found.");
            }


            // 2) Find the Identity Issuers.

            table.addStringFilter(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey(), DatabaseFilterType.EQUAL);
            table.loadToMemory();

            // 3) Get Identity Issuers.

            for (DatabaseTableRecord record : table.getRecords()) {

                // Add records to list.
                list.add(new IdentityAssetIssuerImpl(record.getStringValue(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_ALIAS_COLUMN_NAME),
                        record.getStringValue(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                        getAssetIssuerIdentityPrivateKey(record.getStringValue(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        getAssetIssuerProfileImagePrivateKey(record.getStringValue(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_PUBLIC_KEY_COLUMN_NAME))));
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListAssetIssuerIdentitiesException(e.getMessage(), e, "Asset Issuer Identity", "Cant load " + AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerIdentityPrivateKeyException e) {
            // Failure unknown.
            throw new CantListAssetIssuerIdentitiesException(e.getMessage(), e, "Asset Issuer Identity", "Can't get private key.");

        } catch (Exception e) {
            throw new CantListAssetIssuerIdentitiesException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Identity", "Cant get Asset Issuer identity list, unknown failure.");
        }

        // Return the list values.
        return list;
    }

    public byte[] getAssetIssuerProfileImagePrivateKey(String publicKey) throws CantGetAssetIssuerIdentityProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    AssetIssuerIdentityPluginRoot.ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetAssetIssuerIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            profileImage = new byte[0];
            // throw new CantGetIntraWalletUserIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            throw new CantGetAssetIssuerIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", FermatException.wrapException(e), "", "");
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
                    AssetIssuerIdentityPluginRoot.ASSET_ISSUER_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
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
                    AssetIssuerIdentityPluginRoot.ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
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
            table = this.database.getTable(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists", "Asset Issuer Identity", "");
            }

            table.addStringFilter(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_ALIAS_COLUMN_NAME, alias, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;


        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Asset Issuer  Identity", "Cant load " + AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer  Identity", "unknown failure.");
        }
    }

    public String getAssetIssuerIdentityPrivateKey(String publicKey) throws CantGetAssetIssuerIdentityPrivateKeyException {
        String privateKey = "";
        try {
            PluginTextFile file = this.pluginFileSystem.getTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    AssetIssuerIdentityPluginRoot.ASSET_ISSUER_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );


            file.loadFromMedia();

            privateKey = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetAssetIssuerIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error loaded file.", null);

        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetAssetIssuerIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            throw new CantGetAssetIssuerIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", FermatException.wrapException(e), "", "");
        }

        return privateKey;
    }
}
