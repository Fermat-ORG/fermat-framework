package org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.database;

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
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantGetRedeemPointIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantUpdateIdentityRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 02/11/15.
 */
public class AssetRedeemPointIdentityDao implements DealsWithPluginDatabaseSystem {
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

    public AssetRedeemPointIdentityDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) throws org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantInitializeAssetRedeemPointIdentityDatabaseException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;

        try {
            initializeDatabase();
        } catch (org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantInitializeAssetRedeemPointIdentityDatabaseException e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantInitializeAssetRedeemPointIdentityDatabaseException(e.getMessage());
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
     * @throws org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantInitializeAssetRedeemPointIdentityDatabaseException
     */
    private void initializeDatabase() throws org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantInitializeAssetRedeemPointIdentityDatabaseException {
        try {

             /*
              * Open new database connection
              */

            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_DB_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantInitializeAssetRedeemPointIdentityDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetRedeemPointIdentityDatabaseFactory assetRedeemPointIdentityDatabaseFactory = new AssetRedeemPointIdentityDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = assetRedeemPointIdentityDatabaseFactory.createDatabase(pluginId);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantInitializeAssetRedeemPointIdentityDatabaseException(cantCreateDatabaseException.getMessage());
            }
        } catch (Exception e) {

            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantInitializeAssetRedeemPointIdentityDatabaseException(e.getMessage());

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
                throw new CantCreateNewDeveloperException("Cant create new Redeem Point Identity, alias exists.", "Redeem Point Identity", "Cant create new Redeem Point, alias exists.");
            }

            persistNewUserPrivateKeysFile(publicKey, privateKey);

            DatabaseTable table = this.database.getTable(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publicKey);
            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ALIAS_COLUMN_NAME, alias);
            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey());

            table.insertRecord(record);

            if (profileImage != null)
                persistNewUserProfileImage(publicKey, profileImage);

        } catch (CantInsertRecordException e) {
            // Cant insert record.
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Redeem Point Identity", "Cant create new Redeem Point, insert database problems.");

        } catch (org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantPersistPrivateKeyException e) {
            // Cant insert record.
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "ARedeem Point Identity", "Cant create new Redeem Point, persist private key error.");

        } catch (Exception e) {
            // Failure unknown.

            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Redeem Point Identity", "Cant create new Redeem Point, unknown failure.");
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
    public void createNewUser(String alias, String publicKey, String privateKey, DeviceUser deviceUser, byte[] profileImage,
                              String contactInformation, String countryName, String provinceName, String cityName,
                              String postalCode, String streetName, String houseNumber) throws CantCreateNewDeveloperException {

        try {
            if (aliasExists(alias)) {
                throw new CantCreateNewDeveloperException("Cant create new Redeem Point Identity, alias exists.", "Redeem Point Identity", "Cant create new Redeem Point, alias exists.");
            }

            persistNewUserPrivateKeysFile(publicKey, privateKey);

            DatabaseTable table = this.database.getTable(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publicKey);
            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ALIAS_COLUMN_NAME, alias);
            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_CONTACT_INFORMATION_COLUMN_NAME, contactInformation);
            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_COUNTRY_NAME_COLUMN_NAME, countryName);
            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_PROVINCE_NAME_COLUMN_NAME, provinceName);
            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_CITY_NAME_COLUMN_NAME, cityName);
            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_POSTAL_CODE_COLUMN_NAME, postalCode);
            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_STREET_NAME_COLUMN_NAME, streetName);
            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_HOUSE_NUMBER_COLUMN_NAME, houseNumber);

            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey());

            table.insertRecord(record);

            if (profileImage != null)
                persistNewUserProfileImage(publicKey, profileImage);

        } catch (CantInsertRecordException e) {
            // Cant insert record.
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Redeem Point Identity", "Cant create new Redeem Point, insert database problems.");

        } catch (org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantPersistPrivateKeyException e) {
            // Cant insert record.
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "ARedeem Point Identity", "Cant create new Redeem Point, persist private key error.");

        } catch (Exception e) {
            // Failure unknown.

            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Redeem Point Identity", "Cant create new Redeem Point, unknown failure.");
        }
    }

    public void updateIdentityAssetUser(String publicKey, String alias, byte[] profileImage,
                                        String contactInformation, String countryName, String provinceName, String cityName,
                                        String postalCode, String streetName, String houseNumber) throws CantUpdateIdentityRedeemPointException {
        try {
            /**
             * 1) Get the table.
             */
            DatabaseTable table = this.database.getTable(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Redeem Point Identity list, table not found.", "Redeem Point Identity", "Cant get Redeem Point identity list, table not found.");
            }

            // 2) Find the Intra users.
            table.addStringFilter(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();


            // 3) Get Intra users.
            for (DatabaseTableRecord record : table.getRecords()) {
                //set new values
                record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ALIAS_COLUMN_NAME, alias);
                record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_CONTACT_INFORMATION_COLUMN_NAME, contactInformation);
                record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_COUNTRY_NAME_COLUMN_NAME, countryName);
                record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_PROVINCE_NAME_COLUMN_NAME, provinceName);
                record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_CITY_NAME_COLUMN_NAME, cityName);
                record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_POSTAL_CODE_COLUMN_NAME, postalCode);
                record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_STREET_NAME_COLUMN_NAME, streetName);
                record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_HOUSE_NUMBER_COLUMN_NAME, houseNumber);

                table.updateRecord(record);
            }

            if (profileImage != null)
                persistNewUserProfileImage(publicKey, profileImage);

        } catch (CantUpdateRecordException e) {
            throw new CantUpdateIdentityRedeemPointException(e.getMessage(), e, "Redeem Point Identity", "Cant update Redeem Point Identity, database problems.");
        } catch (org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantPersistProfileImageException e) {
            throw new CantUpdateIdentityRedeemPointException(e.getMessage(), e, "Redeem Point Identity", "Cant update Redeem Point Identity, persist image error.");
        } catch (Exception e) {
            throw new CantUpdateIdentityRedeemPointException(e.getMessage(), FermatException.wrapException(e), "Redeem Point Identity", "Cant update Redeem Point Identity, unknown failure.");
        }
    }

    public List<RedeemPointIdentity> getIdentityAssetRedeemPointsFromCurrentDeviceUser(DeviceUser deviceUser) throws org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantListAssetRedeemPointIdentitiesException {


        // Setup method.
        List<RedeemPointIdentity> list = new ArrayList<>(); // Intra User list.
        DatabaseTable table; // Intra User table.

        // Get Redeem Point identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Asset Issuer identity list, table not found.", "Asset IssuerIdentity", "Cant get Intra User identity list, table not found.");
            }


            // 2) Find the Redeem Point.
            table.addStringFilter(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey(), DatabaseFilterType.EQUAL);
            table.loadToMemory();


            // 3) Get Redeem Point.
            for (DatabaseTableRecord record : table.getRecords()) {

                // Add records to list.
                /*list.add(new IdentityAssetRedeemPointImpl(record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ALIAS_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                        getAssetRedeemPointIdentityPrivateKey(record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        getAssetRedeemPointProfileImagePrivateKey(record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        pluginFileSystem,
                        pluginId), );*/
                list.add(new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.structure.IdentityAssetRedeemPointImpl(record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ALIAS_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                        getAssetRedeemPointIdentityPrivateKey(record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        getAssetRedeemPointProfileImagePrivateKey(record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        pluginFileSystem,
                        pluginId,
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_CONTACT_INFORMATION_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_COUNTRY_NAME_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_PROVINCE_NAME_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_CITY_NAME_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_POSTAL_CODE_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_STREET_NAME_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_HOUSE_NUMBER_COLUMN_NAME) ));
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantListAssetRedeemPointIdentitiesException(e.getMessage(), e, "Asset Redeem Point Identity", "Cant load " + AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_TABLE_NAME + " table in memory.");
        } catch (org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantGetAssetRedeemPointIdentityPrivateKeyException e) {
            // Failure unknown.
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantListAssetRedeemPointIdentitiesException(e.getMessage(), e, "Asset Redeem Point Identity", "Can't get private key.");

        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantListAssetRedeemPointIdentitiesException(e.getMessage(), FermatException.wrapException(e), "Asset Redeem Point Identity", "Cant get Asset Issuer identity list, unknown failure.");
        }

        // Return the list values.
        return list;
    }

    public RedeemPointIdentity getIdentityRedeemPoint() throws CantGetRedeemPointIdentitiesException {

        // Setup method.
        RedeemPointIdentity redeemPointIdentityRecord = null;
        DatabaseTable table; // Intra User table.

        // Get Asset Issuers identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_TABLE_NAME);

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
                /*redeemPointIdentityRecord = new IdentityAssetRedeemPointImpl(
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ALIAS_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                        getAssetRedeemPointProfileImagePrivateKey(record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME)));*/
                redeemPointIdentityRecord = new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.structure.IdentityAssetRedeemPointImpl(
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ALIAS_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                        getAssetRedeemPointProfileImagePrivateKey(record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_CONTACT_INFORMATION_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_COUNTRY_NAME_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_PROVINCE_NAME_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_CITY_NAME_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_POSTAL_CODE_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_STREET_NAME_COLUMN_NAME),
                        record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_HOUSE_NUMBER_COLUMN_NAME));

            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetRedeemPointIdentitiesException(e.getMessage(), e, "Asset Redeem Point Identity", "Cant load " + AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetRedeemPointIdentitiesException(e.getMessage(), FermatException.wrapException(e), "Asset Redeem Point Identity", "Cant get Asset Redeem Point identity list, unknown failure.");
        }

        // Return the list values.
        return redeemPointIdentityRecord;
    }

    public byte[] getAssetRedeemPointProfileImagePrivateKey(String publicKey) throws org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantGetAssetRedeemPointIdentityProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.ReedemPointIdentityPluginRoot.ASSET_REDEEM_POINT_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantGetAssetRedeemPointIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            profileImage = new byte[0];
            // throw new CantGetIntraWalletUserIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantGetAssetRedeemPointIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", FermatException.wrapException(e), "", "");
        }

        return profileImage;
    }

    /**
     * Private Methods
     */

    private void persistNewUserPrivateKeysFile(String publicKey, String privateKey) throws org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantPersistPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.createTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.ReedemPointIdentityPluginRoot.ASSET_REDEEM_POINT_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(privateKey);

            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error creating file.", null);
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", FermatException.wrapException(e), "", "");
        }
    }

    private void persistNewUserProfileImage(String publicKey, byte[] profileImage) throws org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.ReedemPointIdentityPluginRoot.ASSET_REDEEM_POINT_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(profileImage);

            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", FermatException.wrapException(e), "", "");
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
            table = this.database.getTable(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists", "Asset Issuer Identity", "");
            }

            table.addStringFilter(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ALIAS_COLUMN_NAME, alias, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;


        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Asset Issuer  Identity", "Cant load " + AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer  Identity", "unknown failure.");
        }
    }

    public String getAssetRedeemPointIdentityPrivateKey(String publicKey) throws org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantGetAssetRedeemPointIdentityPrivateKeyException {
        String privateKey = "";
        try {
            PluginTextFile file = this.pluginFileSystem.getTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.ReedemPointIdentityPluginRoot.ASSET_REDEEM_POINT_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            privateKey = file.getContent();

        } catch (CantLoadFileException e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantGetAssetRedeemPointIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantGetAssetRedeemPointIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantGetAssetRedeemPointIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", FermatException.wrapException(e), "", "");
        }

        return privateKey;
    }
}
