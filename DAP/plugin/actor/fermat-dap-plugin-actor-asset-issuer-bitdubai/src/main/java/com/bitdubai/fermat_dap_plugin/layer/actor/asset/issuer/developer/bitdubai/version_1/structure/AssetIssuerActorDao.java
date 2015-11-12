package com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
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
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.database.AssetIssuerActorDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.database.AssetIssuerActorDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.AssetIssuerNotFoundException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantAddPendingAssetIssuerException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantGetAssetIssuerActorProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantGetAssetIssuersListException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantInitializeAssetIssuerActorDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantUpdateAssetIssuerException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nerio on 06/10/15.
 *
 * @throws NullPointerException if the constructor failed
 * to initalize the database and you ignored that exception.
 */
public class AssetIssuerActorDao implements Serializable {

    String ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME = "assetIssuerActorProfileImage";

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

    public AssetIssuerActorDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) throws CantInitializeAssetIssuerActorDatabaseException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        initializeDatabase();
    }

    /**
     * Represent de Database where i will be working with
     */
    Database database;

    /**
     * This method open or creates the database i'll be working with     *
     *
     * @throws CantInitializeAssetIssuerActorDatabaseException
     */
    private void initializeDatabase() throws CantInitializeAssetIssuerActorDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DATABASE_NAME);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeAssetIssuerActorDatabaseException(CantInitializeAssetIssuerActorDatabaseException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetIssuerActorDatabaseFactory databaseFactory = new AssetIssuerActorDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = databaseFactory.createDatabase(this.pluginId, AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DATABASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetIssuerActorDatabaseException(CantInitializeAssetIssuerActorDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        } catch (Exception e) {
            throw new CantInitializeAssetIssuerActorDatabaseException(CantInitializeAssetIssuerActorDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a unknown problem and i cannot open the database.");
        }
    }

    public void createNewAssetIssuer(ActorAssetIssuer assetIssuerActorRecord) throws CantAddPendingAssetIssuerException {
        try {
            /**
             * if Asset Issuer exist on table
             * change status
             */
//            if (assetIssuerExists(assetIssuerActorRecord.getPublicKey())) {
//                this.updateAssetIssuerDAPConnectionState(assetIssuerLoggedInPublicKey, assetIssuerActorRecord.getPublicKey(), assetIssuerActorRecord.getDAPConnectionState());
//            } else {
                /**
                 * Get actual date
                 */
            DatabaseTable table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("CANT GET ASSET ISSUER ACTOR, TABLE NOT FOUND.", " ASSET ISSUER ACTOR", "");
            } else {
                table.loadToMemory();
                if (table.getRecords().size() == 0) {
                    DatabaseTableRecord record = table.getEmptyRecord();

                    record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");

                    record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, assetIssuerActorRecord.getPublicKey());
                    record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME, assetIssuerActorRecord.getName());

                    record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, assetIssuerActorRecord.getDapConnectionState().getCode());

                    record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocationLatitude());
                    record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocationLongitude());

                    record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTRATION_DATE_COLUMN_NAME, assetIssuerActorRecord.getRegistrationDate());
                    record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());

                    record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_EXTENDED_COLUMN_NAME, "-");
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_CURRENCY_COLUMN_NAME, assetIssuerActorRecord.getCryptoAddress().getCryptoCurrency().getCode());
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_ADDRESS_COLUMN_NAME, assetIssuerActorRecord.getCryptoAddress().getAddress());
                    record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME, assetIssuerActorRecord.getDescription());

                    table.insertRecord(record);
                    /**
                     * Persist profile image on a file
                     */
                    persistNewAssetIssuerProfileImage(assetIssuerActorRecord.getPublicKey(), assetIssuerActorRecord.getProfileImage());
                }
            }
            database.closeDatabase();
        } catch (CantInsertRecordException e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", e, "", "Cant create new ASSET ISSUER, insert database problems.");
        } catch (Exception e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant create new ASSET ISSUER, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    public void createNewAssetIssuer(String assetIssuerLoggedInPublicKey, ActorAssetIssuer assetIssuerActorRecord) throws CantAddPendingAssetIssuerException {
        try {
            /**
             * if Asset Issuer exist on table
             * change status
             */
            if (assetIssuerExists(assetIssuerActorRecord.getPublicKey())) {
                this.updateAssetIssuerDAPConnectionState(assetIssuerLoggedInPublicKey, assetIssuerActorRecord.getPublicKey(), assetIssuerActorRecord.getDapConnectionState());
            } else {
                /**
                 * Get actual date
                 */
                DatabaseTable table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, assetIssuerActorRecord.getPublicKey());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetIssuerLoggedInPublicKey);
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME, assetIssuerActorRecord.getName());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, assetIssuerActorRecord.getDapConnectionState().getCode());
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTRATION_DATE_COLUMN_NAME, System.currentTimeMillis());
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_EXTENDED_COLUMN_NAME, "-");
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_CURRENCY_COLUMN_NAME, assetIssuerActorRecord.getCryptoAddress().getCryptoCurrency().getCode());
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_ADDRESS_COLUMN_NAME, assetIssuerActorRecord.getCryptoAddress().getAddress());
                record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocation().getLatitude());
                record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocation().getLongitude());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME, assetIssuerActorRecord.getDescription());

                table.insertRecord(record);
                /**
                 * Persist profile image on a file
                 */
                persistNewAssetIssuerProfileImage(assetIssuerActorRecord.getPublicKey(), assetIssuerActorRecord.getProfileImage());
            }
        } catch (CantInsertRecordException e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", e, "", "Cant create new ASSET ISSUER, insert database problems.");
        } catch (CantUpdateAssetIssuerException e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant update exist ASSET ISSUER state, unknown failure.");
        } catch (Exception e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant create new ASSET ISSUER, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    public void createNewAssetIssuerRegistered(ActorAssetIssuer assetIssuerActorRecord) throws CantAddPendingAssetIssuerException {
        try {
            /**
             * if Asset Issuer exist on table
             * change status
             */
            if (assetIssuerRegisteredExists(assetIssuerActorRecord.getPublicKey())) {
                updateAssetIssuerDAPConnectionStateRegistered(assetIssuerActorRecord.getPublicKey(), DAPConnectionState.REGISTERED_ONLINE);
            } else {
                /**
                 * Get actual date
                 */
                DatabaseTable table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetIssuerActorRecord.getPublicKey());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME, assetIssuerActorRecord.getName());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, assetIssuerActorRecord.getDapConnectionState().getCode());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME, System.currentTimeMillis());
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_ISSUER_CRYPTO_CURRENCY_COLUMN_NAME, assetIssuerActorRecord.getCryptoAddress().getCryptoCurrency().getCode());
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_ISSUER_CRYPTO_ADDRESS_COLUMN_NAME, assetIssuerActorRecord.getCryptoAddress().getAddress());
                record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocationLatitude());
                record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocationLongitude());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_DESCRIPTION_COLUMN_NAME, assetIssuerActorRecord.getDescription());

                table.insertRecord(record);
                /**
                 * Persist profile image on a file
                 */
                persistNewAssetIssuerProfileImage(assetIssuerActorRecord.getPublicKey(), assetIssuerActorRecord.getProfileImage());

            }
        } catch (CantInsertRecordException e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", e, "", "Cant create new ASSET ISSUER, insert database problems.");
        } catch (CantUpdateAssetIssuerException e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant update exist ASSET ISSUER state, unknown failure.");
        } catch (Exception e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant create new ASSET ISSUER, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    public void updateAssetIssuer(ActorAssetIssuer actorAssetIssuer) throws CantUpdateAssetIssuerException, AssetIssuerNotFoundException {

        DatabaseTable table;

        try {
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer actor list, table not found.", "Asset Issuer Actor", "");
            }

            // 2) Find the Asset Issuer , filter by keys.
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, actorAssetIssuer.getPublicKey(), DatabaseFilterType.EQUAL);

            table.loadToMemory();

            if (table.getRecords().isEmpty()) {
                throw new AssetIssuerNotFoundException("The following public key was not found: " + actorAssetIssuer.getPublicKey());
            }

            // 3) Get Asset Issuer record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                //UPDATE PROPERTIES

                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, actorAssetIssuer.getDapConnectionState().getCode());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME, actorAssetIssuer.getName());
                record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME, actorAssetIssuer.getLocation().getLatitude());
                record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME, actorAssetIssuer.getLocation().getLongitude());
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_ADDRESS_COLUMN_NAME, actorAssetIssuer.getCryptoAddress().getAddress());
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_CURRENCY_COLUMN_NAME, actorAssetIssuer.getCryptoAddress().getCryptoCurrency().getCode());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME, actorAssetIssuer.getDescription());
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());

                //UPDATE PROFILE IMAGE
                updateAssetIssuerProfileImage(actorAssetIssuer.getPublicKey(), actorAssetIssuer.getProfileImage());

                table.updateRecord(record);
            }

        } catch (CantLoadTableToMemoryException | CantUpdateRecordException e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), e, "asset issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "asset issuer Actor", "Cant get developer identity list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }


    public void updateAssetIssuerDAPConnectionState(String assetIssuerLoggedInPublicKey, String assetIssuerToAddPublicKey, DAPConnectionState dapConnectionState) throws CantUpdateAssetIssuerException {

        DatabaseTable table;

        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer actor list, table not found.", "Asset Issuer Actor", "");
            }

            // 2) Find the Asset Issuer , filter by keys.
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, assetIssuerToAddPublicKey, DatabaseFilterType.EQUAL);
//            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetIssuerLoggedInPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            // 3) Get Asset Issuer record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, dapConnectionState.getCode());
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                table.updateRecord(record);
            }

        } catch (CantLoadTableToMemoryException | CantUpdateRecordException e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), e, "asset issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "asset issuer Actor", "Cant get developer identity list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }


    public void updateAssetIssuerDAPConnectionStateRegistered(String assetIssuerToAddPublicKey, DAPConnectionState dapDAPConnectionState) throws CantUpdateAssetIssuerException {

        DatabaseTable table;

        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer actor list, table not found.", "Asset Issuer Actor", "");
            }

            // 2) Find the Asset Issuer , filter by keys.
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetIssuerToAddPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            // 3) Get Asset Issuer record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, dapDAPConnectionState.getCode());
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                table.updateRecord(record);
            }

        } catch (CantLoadTableToMemoryException | CantUpdateRecordException e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), e, "asset issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "asset issuer Actor", "Cant get developer identity list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    public int createNewAssetIssuerRegisterInNetworkServiceByList(List<ActorAssetIssuer> actorAssetIssuerRecord) throws CantAddPendingAssetIssuerException {
        int recordInsert = 0;
        try {
            /**
             * if Asset User exist on table
             * change status
             */

            for (ActorAssetIssuer actorAssetIssuer : actorAssetIssuerRecord) {
                    if (assetIssuerRegisteredExists(actorAssetIssuer.getPublicKey())) {
                        this.updateAssetIssuerDAPConnectionStateActorNetworService(actorAssetIssuer.getPublicKey(), actorAssetIssuer.getDapConnectionState());
                    } else {
                        /**
                         * Get actual date
                         */
//                Date d = new Date();
//                long milliseconds = d.getTime();
//                String locationLatitude, locationLongitude;
//                if (location.getLatitude() == null || location.getLongitude() == null) {
//                    locationLatitude = "-";
//                    locationLongitude = "-";
//                } else {
//                    locationLatitude = location.getLatitude().toString();
//                    locationLongitude = location.getLongitude().toString();
//                }

                        DatabaseTable table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);
                        DatabaseTableRecord record = table.getEmptyRecord();

                        record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");
                        record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorAssetIssuer.getPublicKey());
                        record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME, actorAssetIssuer.getName());

                       record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, DAPConnectionState.REGISTERED_ONLINE.getCode());//actorAssetUser.getDAPConnectionState().getCode());

                       if (actorAssetIssuer.getLocationLatitude() != null) {
                           record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME, actorAssetIssuer.getLocationLatitude());
                       }
                       if (actorAssetIssuer.getLocationLongitude() != null) {
                           record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME, actorAssetIssuer.getLocationLongitude());
                       }

//                        if (actorAssetIssuer.getCryptoAddress() != null) {
//                            record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME, actorAssetUser.getCryptoAddress().getAddress());
//                            record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME, actorAssetUser.getCryptoAddress().getCryptoCurrency().getCode());
//                        }

                       record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME, actorAssetIssuer.getRegistrationDate());
                        record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());

                       if (actorAssetIssuer.getDescription() != null)
                           record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME, actorAssetIssuer.getDescription());

                       table.insertRecord(record);
                        recordInsert = recordInsert + 1;
                        /**
                         * Persist profile image on a file
                         */
                        persistNewAssetIssuerProfileImage(actorAssetIssuer.getPublicKey(), actorAssetIssuer.getProfileImage());
                    }
                }
            if(actorAssetIssuerRecord.isEmpty()){
                this.updateAssetIssuerDAPConnectionStateActorNetworService(DAPConnectionState.REGISTERED_OFFLINE);
            }

            database.closeDatabase();
        } catch (CantInsertRecordException e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER REGISTERED IN ACTOR NETWORK SERVICE", e, "", "Cant create new ASSET ISSUER REGISTERED IN ACTOR NETWORK SERVICE, insert database problems.");
        } catch (CantUpdateAssetIssuerException e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER REGISTERED IN ACTOR NETWORK SERVICE", FermatException.wrapException(e), "", "Cant update exist ASSET USER REGISTERED IN ACTOR NETWORK SERVICE state, unknown failure.");
        } catch (Exception e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant create new ASSET USER, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        return recordInsert;
    }

    public void updateAssetIssuerDAPConnectionStateActorNetworService(DAPConnectionState dapDAPConnectionState) throws CantUpdateAssetIssuerException {
        DatabaseTable table;

        try {
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset Issuer actor list, table not found.", "Asset Issuer Actor", "");
            }

            // 2) Find the Asset User , filter by keys.
//            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetUserPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            if(table.getRecords().size() > 0) {
                // 3) Get Asset User record and update state.
                for (DatabaseTableRecord record : table.getRecords()) {

                    record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, dapDAPConnectionState.getCode());

//                if(cryptoAddress != null) {
//                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME, cryptoAddress.getAddress());
//                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME, cryptoAddress.getCryptoCurrency().getCode());
//                }

                    record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                    table.updateRecord(record);
                }
            }
            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), e, "asset Issuer REGISTERED Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), e, "asset Issuer REGISTERED Actor", "Cant Update " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "Asset User REGISTERED Actor", "Cant get developer identity list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    public void updateAssetIssuerDAPConnectionStateActorNetworService(String assetIssuerToAddPublicKey, DAPConnectionState dapDAPConnectionState) throws CantUpdateAssetIssuerException {

        DatabaseTable table;

        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer actor list, table not found.", "Asset Issuer Actor", "");
            }

            // 2) Find the Asset Issuer , filter by keys.
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetIssuerToAddPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            // 3) Get Asset Issuer record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, dapDAPConnectionState.getCode());
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                table.updateRecord(record);
            }

        } catch (CantLoadTableToMemoryException | CantUpdateRecordException e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), e, "asset issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "asset issuer Actor", "Cant get developer identity list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    public List<ActorAssetIssuer> getAllAssetIssuers(String assetIssuerLoggedInPublicKey, int max, int offset) throws CantGetAssetIssuersListException {

        // Setup method.
        List<ActorAssetIssuer> list = new ArrayList<ActorAssetIssuer>(); // Asset Issuer Actor list.
        DatabaseTable table;

        // Get Asset Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Asset Issuer identity list, table not found.", "Plugin Identity", "Cant get Asset Issuer identity list, table not found.");
            }

            // 2) Find all Asset Users.
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetIssuerLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, DAPConnectionState.REGISTERED_ONLINE.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Asset Users Recorod.

            this.addRecordsToList(list, table.getRecords());

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            // Failure unknown.
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor", "Cant get Asset Issuer Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the list values.
        return list;
    }

    public List<ActorAssetIssuer> getAssetIssuers(String aasetUserLoggedInPublicKey, DAPConnectionState dapDAPConnectionState, int max, int offset) throws CantGetAssetIssuersListException {

        // Setup method.
        List<ActorAssetIssuer> list = new ArrayList<>(); // Asset Issuer Actor list.
        DatabaseTable table;

        // Get Asset Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer identity list, table not found.", "Plugin Identity", "Cant get asset issuer identity list, table not found.");
            }
            // 2) Find  Asset Users by state.
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, aasetUserLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, dapDAPConnectionState.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Asset Users Recorod.
            this.addRecordsToList(list, table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            // Failure unknown.
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor", "Cant get Asset Issuer Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the list values.
        return list;
    }

    public ActorAssetIssuer getActorAssetIssuer() throws CantGetAssetIssuerActorsException {

        ActorAssetIssuer assetIssuerActorRecord = new AssetIssuerActorRecord();
        DatabaseTable table;

        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }
            table.loadToMemory();
            // 3) Get Asset Users Record.
            assetIssuerActorRecord = this.addRecords(table.getRecords());
            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetIssuerActorsException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            throw new CantGetAssetIssuerActorsException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetIssuerActorsException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the values.
        return assetIssuerActorRecord;
    }

    public ActorAssetIssuer getActorByPublicKey(String actorPublicKey) throws CantGetAssetIssuerActorsException {
        ActorAssetIssuer actorAssetIssuerrRecord = null;
        DatabaseTable table;

        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset Actor Issuer, table not found.", "Plugin Identity", "Cant get asset Actor Issuer, table not found.");
            }
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();
            // 3) Get Asset Users Record.
            actorAssetIssuerrRecord = this.addRecords(table.getRecords());

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetIssuerActorsException(e.getMessage(), e, "Asset Issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            throw new CantGetAssetIssuerActorsException(e.getMessage(), e, "Asset Issuer Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetIssuerActorsException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor", "Cant get Asset User Actor, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the values.
        return actorAssetIssuerrRecord;
    }

    public List<ActorAssetIssuer> getAllAssetIssuerActorRegistered() throws CantGetAssetIssuersListException {
        List<ActorAssetIssuer> list = new ArrayList<>(); // Asset Issuer Actor list.

        DatabaseTable table;

        // Get Asset Issuer identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset Issuer identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }

            table.loadToMemory();

            // 3) Get Asset Issuer Recorod.
            for (DatabaseTableRecord record : table.getRecords()) {
                // Add records to list.
                list.add(new AssetIssuerActorRecord(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME),
                        record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME),
                        getAssetIssuerProfileImagePrivateKey(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME)));
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor Registered", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor Registered", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor Registered", "Cant get Asset Issuer Actor Registered list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the list values.
        return list;
    }

    public List<ActorAssetIssuer> getAllAssetIssuerActorConnected() throws CantGetAssetIssuersListException {
        List<ActorAssetIssuer> list = new ArrayList<>(); // Asset User Actor list.
        DatabaseTable table;
        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset Issuer identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }
            // 2) Find  Asset Users by Connection State.

            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, DAPConnectionState.CONNECTED_ONLINE.getCode(), DatabaseFilterType.EQUAL);

            table.loadToMemory();

            this.addRecordsTableRegisteredToList(list, table.getRecords());

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset Issuer Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the list values.
        return list;
    }

    private void updateAssetIssuerProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            //If the profileImage hasn't been update don't modify it.
            if (file.getContent().equals(profileImage)) {
                return;
            }
            file.delete();
            file.setContent(profileImage);
            file.persistToMedia();
        } catch (CantPersistFileException | CantCreateFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);
        } catch (Exception e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * Private Methods
     */
    private void persistNewAssetIssuerProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
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

    private void addRecordsToList(List<ActorAssetIssuer> list, List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetAssetIssuerActorProfileImageException {
        for (DatabaseTableRecord record : records) {

            //Inicializar el AssetIssuerActorRecord, nombre y public key son obligatorios.
            AssetIssuerActorRecord assetIssuerActorRecord = new AssetIssuerActorRecord(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME), record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME));

            assetIssuerActorRecord.setDapConnectionState(DAPConnectionState.getByCode(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME)));
            assetIssuerActorRecord.setProfileImage(getAssetIssuerProfileImagePrivateKey(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME)));
            assetIssuerActorRecord.setDescription(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME));
            assetIssuerActorRecord.setRegistrationDate(record.getLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTRATION_DATE_COLUMN_NAME));

            //CRYPTOADDRESS
//            CryptoAddress cryptoAddress = new CryptoAddress();
//            cryptoAddress.setCryptoCurrency(CryptoCurrency.getByCode(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_CURRENCY_COLUMN_NAME)));
//            cryptoAddress.setAddress(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_ADDRESS_COLUMN_NAME));
//            assetIssuerActorRecord.setCryptoAddress(cryptoAddress);

            //LOCATION
            DeviceLocation deviceLocation = new DeviceLocation();
            deviceLocation.setLatitude(record.getDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME));
            deviceLocation.setLongitude(record.getDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME));
            assetIssuerActorRecord.setLocation(deviceLocation);
            // Add records to list. AssetIssuerActorRecord

            list.add(assetIssuerActorRecord);

        }
    }

    private byte[] getAssetIssuerProfileImagePrivateKey(String publicKey) throws CantGetAssetIssuerActorProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetAssetIssuerActorProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);

        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetAssetIssuerActorProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting Asset Issuer Actor private keys file.", null);
        } catch (Exception e) {
            throw new CantGetAssetIssuerActorProfileImageException("CAN'T GET IMAGE PROFILE ", FermatException.wrapException(e), "", "");
        }
        return profileImage;
    }

    /**
     * <p>Method that check if Asset Issuer public key exists.
     *
     * @param assetIssuerToAddPublicKey
     * @return boolean exists
     * @throws CantCreateNewDeveloperException
     */
    private boolean assetIssuerExists(String assetIssuerToAddPublicKey) throws CantCreateNewDeveloperException {

        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */
        try {
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists, table not  found.", "Asset Issuer Actor", "Cant check if alias exists, table not found.");
            }
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, assetIssuerToAddPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Asset Issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor", "Cant check if alias exists, unknown failure.");
        }
    }

    private boolean assetIssuerRegisteredExists(String assetIssuerPublicKey) throws CantCreateNewDeveloperException {

        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */
        try {
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists, table not  found.", "Actor Asset Issuer Registered", "Cant check if alias exists, table not found.");
            }
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetIssuerPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Actor Asset Issuer Registered", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Actor Asset Issuer Registered", "Cant check if alias exists, unknown failure.");
        }
    }

    private ActorAssetIssuer addRecords(List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetAssetIssuerActorProfileImageException {
        AssetIssuerActorRecord actorAssetIssuer = null;
        for (DatabaseTableRecord record : records) {

            actorAssetIssuer = new AssetIssuerActorRecord(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME),
                    DAPConnectionState.getByCode(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME)),
                    record.getDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME),
                    record.getDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME),
                    record.getLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTRATION_DATE_COLUMN_NAME),
//                    record.getLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LAST_CONNECTION_DATE_COLUMN_NAME),
                    getAssetIssuerProfileImagePrivateKey(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME)),
                    record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME));
        }
        return actorAssetIssuer;
    }

    private void addRecordsTableRegisteredToList(List<ActorAssetIssuer> list, List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetAssetIssuerActorProfileImageException {

        for (DatabaseTableRecord record : records) {

            list.add(new AssetIssuerActorRecord(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME),
                    DAPConnectionState.getByCode(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME)),
                    record.getDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME),
                    record.getDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME),
                    record.getLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME),
//                    record.getLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME),
                    getAssetIssuerProfileImagePrivateKey(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME)),
                    record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_DESCRIPTION_COLUMN_NAME)));
        }
    }
}
