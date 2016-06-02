package org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
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

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantCreateNewDeveloperException;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetUserDeveloperIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantUpdateActorAssetIssuerException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAddPendingActorAssetException;
import org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuerActorProfileImageException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public AssetIssuerActorDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerActorDatabaseException {
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
     * @throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerActorDatabaseException
     */
    private void initializeDatabase() throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerActorDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerActorDatabaseException(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerActorDatabaseException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseFactory databaseFactory = new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = databaseFactory.createDatabase(this.pluginId, org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DATABASE_NAME);

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerActorDatabaseException(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerActorDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerActorDatabaseException(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerActorDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a unknown problem and i cannot open the database.");
        }
    }

    public void createNewAssetIssuer(ActorAssetIssuer assetIssuerActorRecord) throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException {
        try {
            /**
             * if Asset Issuer exist on table
             * change status
             */
//            if (assetIssuerExists(assetIssuerActorRecord.getActorPublicKey())) {
//                this.updateAssetIssuerDAPConnectionState(assetIssuerLoggedInPublicKey, assetIssuerActorRecord.getActorPublicKey(), assetIssuerActorRecord.getDAPConnectionState());
//            } else {
            /**
             * Get actual date
             */
            DatabaseTable table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("CANT GET ASSET ISSUER ACTOR, TABLE NOT FOUND.", " ASSET ISSUER ACTOR", "");
            } else {
                table.loadToMemory();
                if (table.getRecords().size() == 0) {
                    DatabaseTableRecord record = table.getEmptyRecord();

                    record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");

                    record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, assetIssuerActorRecord.getActorPublicKey());
                    record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME, assetIssuerActorRecord.getName());

                    record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, assetIssuerActorRecord.getDapConnectionState().getCode());

                    record.setDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocationLatitude());
                    record.setDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocationLongitude());

                    record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTRATION_DATE_COLUMN_NAME, assetIssuerActorRecord.getRegistrationDate());
                    record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());

                    record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_EXTENDED_COLUMN_NAME, "-");
                    record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME, assetIssuerActorRecord.getDescription());

                    record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TYPE_COLUMN_NAME, assetIssuerActorRecord.getType().getCode());

                    table.insertRecord(record);
                    /**
                     * Persist profile image on a file
                     */
                    persistNewAssetIssuerProfileImage(assetIssuerActorRecord.getActorPublicKey(), assetIssuerActorRecord.getProfileImage());
                }
            }

        } catch (CantInsertRecordException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", e, "", "Cant create new ASSET ISSUER, insert database problems.");
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant create new ASSET ISSUER, unknown failure.");
        } finally {

        }
    }

    public void createNewAssetIssuer(String assetIssuerLoggedInPublicKey, ActorAssetIssuer assetIssuerActorRecord) throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException {
        try {
            /**
             * if Asset Issuer exist on table
             * change status
             */
            if (assetIssuerExists(assetIssuerActorRecord.getActorPublicKey())) {
                this.updateAssetIssuerDAPConnectionState(assetIssuerLoggedInPublicKey, assetIssuerActorRecord.getActorPublicKey(), assetIssuerActorRecord.getDapConnectionState());
            } else {
                /**
                 * Get actual date
                 */
                DatabaseTable table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, assetIssuerActorRecord.getActorPublicKey());
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetIssuerLoggedInPublicKey);
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME, assetIssuerActorRecord.getName());
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, assetIssuerActorRecord.getDapConnectionState().getCode());
                record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTRATION_DATE_COLUMN_NAME, System.currentTimeMillis());
                record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_EXTENDED_COLUMN_NAME, "-");
                record.setDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocation().getLatitude());
                record.setDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocation().getLongitude());
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME, assetIssuerActorRecord.getDescription());

                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TYPE_COLUMN_NAME, assetIssuerActorRecord.getType().getCode());

                table.insertRecord(record);
                /**
                 * Persist profile image on a file
                 */
                persistNewAssetIssuerProfileImage(assetIssuerActorRecord.getActorPublicKey(), assetIssuerActorRecord.getProfileImage());
            }
        } catch (CantInsertRecordException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", e, "", "Cant create new ASSET ISSUER, insert database problems.");
        } catch (org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant update exist ASSET ISSUER state, unknown failure.");
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant create new ASSET ISSUER, unknown failure.");
        } finally {

        }
    }

    public void createNewAssetIssuerRegistered(ActorAssetIssuer assetIssuerActorRecord) throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException {
        try {
            /**
             * if Asset Issuer exist on table
             * change status
             */
            if (assetIssuerRegisteredExists(assetIssuerActorRecord.getActorPublicKey())) {
                updateAssetIssuerDAPConnectionStateRegistered(assetIssuerActorRecord, DAPConnectionState.REGISTERED_ONLINE);
            } else {
                /**
                 * Get actual date
                 */
                DatabaseTable table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetIssuerActorRecord.getActorPublicKey());
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME, assetIssuerActorRecord.getName());
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, assetIssuerActorRecord.getDapConnectionState().getCode());
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");
                record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME, System.currentTimeMillis());
                record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                record.setDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocationLatitude());
                record.setDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocationLongitude());
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_DESCRIPTION_COLUMN_NAME, assetIssuerActorRecord.getDescription());

                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TYPE_COLUMN_NAME, assetIssuerActorRecord.getType().getCode());

                table.insertRecord(record);
                /**
                 * Persist profile image on a file
                 */
                persistNewAssetIssuerProfileImage(assetIssuerActorRecord.getActorPublicKey(), assetIssuerActorRecord.getProfileImage());

            }
        } catch (CantInsertRecordException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", e, "", "Cant create new ASSET ISSUER, insert database problems.");
        } catch (org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant update exist ASSET ISSUER state, unknown failure.");
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant create new ASSET ISSUER, unknown failure.");
        } finally {

        }
    }

    public void updateAssetIssuer(ActorAssetIssuer actorAssetIssuer) throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException, org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.AssetIssuerNotFoundException {

        DatabaseTable table;

        try {
            table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer actor list, table not found.", "Asset Issuer Actor", "");
            }

            // 2) Find the Asset Issuer , filter by keys.
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, actorAssetIssuer.getActorPublicKey(), DatabaseFilterType.EQUAL);

            table.loadToMemory();

            if (table.getRecords().isEmpty()) {
                throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.AssetIssuerNotFoundException("The following public key was not found: " + actorAssetIssuer.getActorPublicKey());
            }

            // 3) Get Asset Issuer record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                //UPDATE PROPERTIES

                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME, actorAssetIssuer.getName());
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, actorAssetIssuer.getDapConnectionState().getCode());
                record.setDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME, actorAssetIssuer.getLocationLatitude());
                record.setDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME, actorAssetIssuer.getLocationLongitude());
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME, actorAssetIssuer.getDescription());
                record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());

                //UPDATE PROFILE IMAGE
                updateAssetIssuerProfileImage(actorAssetIssuer.getActorPublicKey(), actorAssetIssuer.getProfileImage());

                table.updateRecord(record);
            }

        } catch (CantLoadTableToMemoryException | CantUpdateRecordException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException(e.getMessage(), e, "asset issuer Actor", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "asset issuer Actor", "Cant get developer identity list, unknown failure.");
        } finally {

        }
    }


    public void updateAssetIssuerDAPConnectionState(String assetIssuerLoggedInPublicKey, String assetIssuerToAddPublicKey, DAPConnectionState dapConnectionState) throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException {

        DatabaseTable table;

        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer actor list, table not found.", "Asset Issuer Actor", "");
            }

            // 2) Find the Asset Issuer , filter by keys.
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, assetIssuerToAddPublicKey, DatabaseFilterType.EQUAL);
//            table.addStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetIssuerLoggedInPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            // 3) Get Asset Issuer record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, dapConnectionState.getCode());
                record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                table.updateRecord(record);
            }

        } catch (CantLoadTableToMemoryException | CantUpdateRecordException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException(e.getMessage(), e, "asset issuer Actor", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "asset issuer Actor", "Cant get developer identity list, unknown failure.");
        } finally {

        }
    }


    public void updateAssetIssuerDAPConnectionStateRegistered(ActorAssetIssuer actorAssetIssuer, DAPConnectionState dapDAPConnectionState) throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException {

        DatabaseTable table;

        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer actor list, table not found.", "Asset Issuer Actor", "");
            }

            // 2) Find the Asset Issuer , filter by keys.
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorAssetIssuer.getActorPublicKey(), DatabaseFilterType.EQUAL);

            table.loadToMemory();

            // 3) Get Asset Issuer record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {

                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, dapDAPConnectionState.getCode());
                record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                table.updateRecord(record);
            }

            //UPDATE PROFILE IMAGE
            //updateAssetIssuerProfileImage(actorAssetIssuer.getActorPublicKey(), actorAssetIssuer.getProfileImage());

        } catch (CantLoadTableToMemoryException | CantUpdateRecordException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException(e.getMessage(), e, "asset issuer Actor", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "asset issuer Actor", "Cant get developer identity list, unknown failure.");
        }
    }

    public void updateExtendedPublicKey(String actorPublicKey, String extendedPublicKey) throws CantGetUserDeveloperIdentitiesException, CantLoadTableToMemoryException, CantUpdateRecordException {
        DatabaseTable table;
        /**
         * 1) Get the table.
         */
        table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

        if (table == null) {
            /**
             * Table not found.
             */
            throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer actor list, table not found.", "Asset Issuer Actor", "");
        }

        // 2) Find the Asset Issuer , filter by keys.
        table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);

        table.loadToMemory();

        // 3) Get Asset Issuer record and update state.
        for (DatabaseTableRecord record : table.getRecords()) {
            record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_EXTENDED_COLUMN_NAME, extendedPublicKey);
            table.updateRecord(record);
        }
    }

    public int createNewAssetIssuerRegisterInNetworkServiceByList(List<ActorAssetIssuer> actorAssetIssuerRecord) throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException {
        int recordInsert = 0;
        try {
            /**
             * if Asset User exist on table
             * change status
             */

            for (ActorAssetIssuer actorAssetIssuer : actorAssetIssuerRecord) {
                if (assetIssuerRegisteredExists(actorAssetIssuer.getActorPublicKey())) {
                    this.updateAssetIssuerDAPConnectionStateActorNetworkService(actorAssetIssuer, null);
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

                    DatabaseTable table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);
                    DatabaseTableRecord record = table.getEmptyRecord();

                    record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");
                    record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorAssetIssuer.getActorPublicKey());
                    record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME, actorAssetIssuer.getName());

                    record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, DAPConnectionState.REGISTERED_ONLINE.getCode());//actorAssetUser.getDAPConnectionState().getCode());

                    if (actorAssetIssuer.getLocationLatitude() != null) {
                        record.setDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME, actorAssetIssuer.getLocationLatitude());
                    }
                    if (actorAssetIssuer.getLocationLongitude() != null) {
                        record.setDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME, actorAssetIssuer.getLocationLongitude());
                    }

                    record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME, actorAssetIssuer.getRegistrationDate());
                    record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());

                    if (actorAssetIssuer.getDescription() != null)
                        record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_DESCRIPTION_COLUMN_NAME, actorAssetIssuer.getDescription());

                    record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TYPE_COLUMN_NAME, actorAssetIssuer.getType().getCode());

                    table.insertRecord(record);
                    recordInsert = recordInsert + 1;
                    /**
                     * Persist profile image on a file
                     */
                    persistNewAssetIssuerProfileImage(actorAssetIssuer.getActorPublicKey(), actorAssetIssuer.getProfileImage());
                }
            }
            if (actorAssetIssuerRecord.isEmpty()) {
                this.updateAssetIssuerDAPConnectionStateActorNetworkService(DAPConnectionState.REGISTERED_OFFLINE);
            }


        } catch (CantInsertRecordException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER REGISTERED IN ACTOR NETWORK SERVICE", e, "", "Cant create new ASSET ISSUER REGISTERED IN ACTOR NETWORK SERVICE, insert database problems.");
        } catch (org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER REGISTERED IN ACTOR NETWORK SERVICE", FermatException.wrapException(e), "", "Cant update exist ASSET USER REGISTERED IN ACTOR NETWORK SERVICE state, unknown failure.");
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant create new ASSET USER, unknown failure.");
        } finally {

        }
        return recordInsert;
    }

    public void updateOfflineIssuerRegisterInNetworkService(List<ActorAssetIssuer> onlineIssuersInNetworkService) throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException, org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException {

        try {
            List<ActorAssetIssuer> list = getAllAssetIssuerActorRegistered();

            for (ActorAssetIssuer registeredIssuer : list) {
                if (notInNetworkService(registeredIssuer, onlineIssuersInNetworkService)) {
                    if (registeredIssuer.getDapConnectionState().equals(DAPConnectionState.CONNECTED_ONLINE))
                        updateAssetIssuerDAPConnectionStateActorNetworkService(registeredIssuer, DAPConnectionState.CONNECTED_OFFLINE);
                    else if (registeredIssuer.getDapConnectionState().equals(DAPConnectionState.REGISTERED_ONLINE))
                        updateAssetIssuerDAPConnectionStateActorNetworkService(registeredIssuer, DAPConnectionState.REGISTERED_OFFLINE);
                } else {
                    if (registeredIssuer.getDapConnectionState().equals(DAPConnectionState.CONNECTED_OFFLINE))
                        updateAssetIssuerDAPConnectionStateActorNetworkService(registeredIssuer, DAPConnectionState.CONNECTED_ONLINE);
                    else if (registeredIssuer.getDapConnectionState().equals(DAPConnectionState.REGISTERED_OFFLINE))
                        updateAssetIssuerDAPConnectionStateActorNetworkService(registeredIssuer, DAPConnectionState.REGISTERED_ONLINE);
                }
            }

        } catch (org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "Issuer Actor", "Cant update Issuer State");
        } catch (org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), e, "Redeem Point Actor", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DATABASE_NAME + " table in memory.");
        }

    }

    private boolean notInNetworkService(ActorAssetIssuer registeredIssuer, List<ActorAssetIssuer> onlineIssuersInNetworkService) {

        for (ActorAssetIssuer onlineIssuers : onlineIssuersInNetworkService) {
            if (onlineIssuers.getActorPublicKey().equals(registeredIssuer.getActorPublicKey()))
                return false;
        }
        return true;
    }

    public void updateAssetIssuerDAPConnectionStateActorNetworkService(DAPConnectionState dapDAPConnectionState) throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException {
        DatabaseTable table;

        try {
            table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset Issuer actor list, table not found.", "Asset Issuer Actor", "");
            }

            // 2) Find the Asset User , filter by keys.
//            table.addStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetUserPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            if (table.getRecords().size() > 0) {
                // 3) Get Asset User record and update state.
                for (DatabaseTableRecord record : table.getRecords()) {
                    record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, dapDAPConnectionState.getCode());
                    record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                    table.updateRecord(record);
                }
            }

        } catch (CantLoadTableToMemoryException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException(e.getMessage(), e, "asset Issuer REGISTERED Actor", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantUpdateRecordException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException(e.getMessage(), e, "asset Issuer REGISTERED Actor", "Cant Update " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "Asset User REGISTERED Actor", "Cant get developer identity list, unknown failure.");
        }
    }

    public void updateAssetIssuerDAPConnectionStateActorNetworkService(ActorAssetIssuer actorAssetIssuer, DAPConnectionState dapConnectionState) throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException {

        DatabaseTable table;

        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer actor list, table not found.", "Asset Issuer Actor", "");
            }

            // 2) Find the Asset Issuer , filter by keys.
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorAssetIssuer.getActorPublicKey(), DatabaseFilterType.EQUAL);

            table.loadToMemory();

            // 3) Get Asset Issuer record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME, actorAssetIssuer.getName());
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");
                record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME, System.currentTimeMillis());
                record.setDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME, actorAssetIssuer.getLocationLatitude());
                record.setDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME, actorAssetIssuer.getLocationLongitude());
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_DESCRIPTION_COLUMN_NAME, actorAssetIssuer.getDescription());

                if (record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_EXTENDED_COLUMN_NAME) == null) {

                    if (Objects.equals(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME), DAPConnectionState.REGISTERED_OFFLINE.getCode())) {
                        dapConnectionState = DAPConnectionState.REGISTERED_ONLINE;
                    }
                } else {
                    if (Objects.equals(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME), DAPConnectionState.CONNECTED_OFFLINE.getCode())) {
                        dapConnectionState = DAPConnectionState.CONNECTED_ONLINE;
                    }
                }

                if (dapConnectionState != null)
                    record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, dapConnectionState.getCode());

                record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                table.updateRecord(record);
            }

            //UPDATE PROFILE IMAGE
            updateAssetIssuerProfileImage(actorAssetIssuer.getActorPublicKey(), actorAssetIssuer.getProfileImage());

        } catch (CantLoadTableToMemoryException | CantUpdateRecordException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException(e.getMessage(), e, "asset issuer Actor", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantUpdateAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "asset issuer Actor", "Cant get developer identity list, unknown failure.");
        }
    }

    public List<ActorAssetIssuer> getAllAssetIssuers(String assetIssuerLoggedInPublicKey, int max, int offset) throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException {

        // Setup method.
        List<ActorAssetIssuer> list = new ArrayList<ActorAssetIssuer>(); // Asset Issuer Actor list.
        DatabaseTable table;

        // Get Asset Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Asset Issuer identity list, table not found.", "Plugin Identity", "Cant get Asset Issuer identity list, table not found.");
            }

            // 2) Find all Asset Users.
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetIssuerLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, DAPConnectionState.REGISTERED_ONLINE.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Asset Users Recorod.

            this.addRecordsToList(list, table.getRecords());

        } catch (CantLoadTableToMemoryException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            // Failure unknown.
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor", "Cant get Asset Issuer Actor list, unknown failure.");
        } finally {

        }
        // Return the list values.
        return list;
    }

    public List<ActorAssetIssuer> getAssetIssuers(String aasetUserLoggedInPublicKey, DAPConnectionState dapDAPConnectionState, int max, int offset) throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException {

        // Setup method.
        List<ActorAssetIssuer> list = new ArrayList<>(); // Asset Issuer Actor list.
        DatabaseTable table;

        // Get Asset Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer identity list, table not found.", "Plugin Identity", "Cant get asset issuer identity list, table not found.");
            }
            // 2) Find  Asset Users by state.
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, aasetUserLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, dapDAPConnectionState.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Asset Users Recorod.
            this.addRecordsToList(list, table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            // Failure unknown.
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor", "Cant get Asset Issuer Actor list, unknown failure.");
        } finally {

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
            table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }
            table.loadToMemory();
            // 3) Get Asset Users Record.
            assetIssuerActorRecord = this.getActorFromRecord(table.getRecords());

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetIssuerActorsException(e.getMessage(), e, "Asset User Actor", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            throw new CantGetAssetIssuerActorsException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetIssuerActorsException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
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
            table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset Actor Issuer, table not found.", "Plugin Identity", "Cant get asset Actor Issuer, table not found.");
            }
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();
            // 3) Get Asset Users Record.
            actorAssetIssuerrRecord = getActorFromRecordRegistered(table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetIssuerActorsException(e.getMessage(), e, "Asset Issuer Actor", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            throw new CantGetAssetIssuerActorsException(e.getMessage(), e, "Asset Issuer Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetIssuerActorsException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor", "Cant get Asset User Actor, unknown failure.");
        }
        // Return the values.
        return actorAssetIssuerrRecord;
    }

    public List<ActorAssetIssuer> getAllAssetIssuerActorRegistered() throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException {
        List<ActorAssetIssuer> list = new ArrayList<>(); // Asset Issuer Actor list.

        DatabaseTable table;

        // Get Asset Issuer identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset Issuer identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }

            table.loadToMemory();

            this.addRecordsTableRegisteredToList(list, table.getRecords());

        } catch (CantLoadTableToMemoryException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor Registered", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor Registered", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor Registered", "Cant get Asset Issuer Actor Registered list, unknown failure.");
        }
        // Return the list values.
        return list;
    }

    public List<ActorAssetIssuer> getAllAssetIssuerActorConnectedWithExtendedpk() throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException {
        List<ActorAssetIssuer> list = new ArrayList<>(); // Asset User Actor list.
        DatabaseTable table;
        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset Issuer identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }
            // 2) Find  Asset Users by Connection State.

            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_EXTENDED_COLUMN_NAME, "-", DatabaseFilterType.NOT_EQUALS);
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_EXTENDED_COLUMN_NAME, "", DatabaseFilterType.NOT_EQUALS);
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_EXTENDED_COLUMN_NAME, null, DatabaseFilterType.NOT_EQUALS);

            table.loadToMemory();

            this.addRecordsTableRegisteredToList(list, table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset Issuer Actor list, unknown failure.");
        }
        // Return the list values.
        return list;
    }

    public List<ActorAssetIssuer> getAllAssetIssuerActorConnected() throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException {
        List<ActorAssetIssuer> list = new ArrayList<>(); // Asset User Actor list.
        DatabaseTable table;
        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset Issuer identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }
            // 2) Find  Asset Users by Connection State.

            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, DAPConnectionState.CONNECTED_ONLINE.getCode(), DatabaseFilterType.EQUAL);

            table.loadToMemory();

            this.addRecordsTableRegisteredToList(list, table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset Issuer Actor list, unknown failure.");
        }
        // Return the list values.
        return list;
    }

    public void createNewAssetIssuerRequestRegistered(String actorAssetIssuerLogged,
                                                      String actorAssetIssuerPublicKey,
                                                      String actorAssetIssuerName,
                                                      byte[] profileImage,
                                                      DAPConnectionState dapConnectionState,
                                                      Actors actorsType) throws CantAddPendingActorAssetException {
        try {
            /**
             * if Asset Issuer exist on table
             * change status
             */
            if (assetIssuerRegisteredExists(actorAssetIssuerPublicKey)) {
                this.updateRegisteredConnectionState(actorAssetIssuerLogged, actorAssetIssuerPublicKey, dapConnectionState);
            } else {

                DatabaseTable table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");

                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorAssetIssuerPublicKey);
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME, actorAssetIssuerName);

                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, dapConnectionState.getCode());

//                if (actorAssetUserRecord.getLocationLatitude() != null)
//                    record.setDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME, actorAssetUserRecord.getLocationLatitude());
//                if (actorAssetUserRecord.getLocationLongitude() != null)
//                    record.setDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME, actorAssetUserRecord.getLocationLongitude());

                record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME, System.currentTimeMillis());
                record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                //TODO: Evaluar para cuando sea un USER el que realice la solicitud de conexion
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TYPE_COLUMN_NAME, actorsType.getCode());

                table.insertRecord(record);
                /**
                 * Persist profile image on a file
                 */
                persistNewAssetIssuerProfileImage(actorAssetIssuerPublicKey, profileImage);
            }

        } catch (CantInsertRecordException e) {
            throw new CantAddPendingActorAssetException("CAN'T INSERT ASSET USER REGISTERED IN ACTOR NETWORK SERVICE", e, "", "Cant create new ASSET USER REGISTERED IN ACTOR NETWORK SERVICE, insert database problems.");
        } catch (CantUpdateActorAssetIssuerException e) {
            throw new CantAddPendingActorAssetException("CAN'T INSERT ASSET USER REGISTERED IN ACTOR NETWORK SERVICE", FermatException.wrapException(e), "", "Cant update exist ASSET USER REGISTERED IN ACTOR NETWORK SERVICE state, unknown failure.");
        } catch (Exception e) {
            throw new CantAddPendingActorAssetException("CAN'T INSERT ASSET USER", FermatException.wrapException(e), "", "Cant create new ASSET USER, unknown failure.");
        }
    }

    public boolean actorAssetRegisteredRequestExists(final String actorAssetIssuerToAddPublicKey, DAPConnectionState dapConnectionState) throws CantGetAssetUserActorsException {
        try {

            final DatabaseTable table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (actorAssetIssuerToAddPublicKey == null) {
                throw new CantGetUserDeveloperIdentitiesException("actorAssetIssuerToAddPublicKey null", "actorAssetIssuerToAddPublicKey must not be null.");
            }

            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorAssetIssuerToAddPublicKey, DatabaseFilterType.EQUAL);
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, dapConnectionState.getCode(), DatabaseFilterType.EQUAL);

            table.loadToMemory();

            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantGetAssetUserActorsException(em.getMessage(), em, "ACTOR ASSET ISSUER", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetAssetUserActorsException(e.getMessage(), FermatException.wrapException(e), "ACTOR ASSET ISSUER", "Cant check if actor public key exists, unknown failure.");
        }
    }

    public void updateRegisteredConnectionState(final String actorAssetIssuerLoggedInPublicKey,
                                                final String actorAssetIssuerToAddPublicKey,
                                                final DAPConnectionState dapConnectionState) throws CantUpdateActorAssetIssuerException {

        try {

            /**
             * 1) Get the table.
             */
            final DatabaseTable table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant get intra user actor list, table not found.", "ACTOR ASSET ISSUER", "");

            // 2) Find the Intra User , filter by keys.
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorAssetIssuerToAddPublicKey, DatabaseFilterType.EQUAL);
//            table.addStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, actorAssetIssuerToAddPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            // 3) Get Intra user record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, dapConnectionState.getCode());
                record.setLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                table.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateActorAssetIssuerException(e.getMessage(), e, "ACTOR ASSET ISSUER", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateActorAssetIssuerException(e.getMessage(), e, "ACTOR ASSET ISSUER", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateActorAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "ACTOR ASSET ISSUER", "Cant get developer identity list, unknown failure.");
        }
    }

    public List<DAPActor> getAllWaitingActorAssetIssuer(final String actorAssetSelectedPublicKey,
                                                        final DAPConnectionState dapConnectionState,
                                                        final int max,
                                                        final int offset) throws CantGetAssetUserActorsException {

        // Setup method.
        List<ActorAssetIssuer> list = new ArrayList<>(); // Actor Issuer.
        List<DAPActor> dapActors = new ArrayList<>(); // Actor Issuer.
        DatabaseTable table;

        try {
            table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant get ACTOR ASSET ISSUER identity list, table not found.", "Plugin Identity", "Cant get ACTOR ISSUER identity list, table not found.");

            // 2) Find  Intra Users by state.
//            table.addStringFilter(AssetUserActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, actorAssetSelectedPublicKey, DatabaseFilterType.EQUAL);
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, dapConnectionState.getCode(), DatabaseFilterType.EQUAL);

            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));

            table.loadToMemory();

            this.addRecordsTableRegisteredToList(list, table.getRecords());

            for (ActorAssetIssuer record : list) {
                dapActors.add((new AssetIssuerActorRecord(record.getActorPublicKey(), record.getName(), record.getProfileImage(), record.getLocation())));
            }

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUserActorsException(e.getMessage(), e, "ACTOR ASSET ISSUER", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetAssetUserActorsException(e.getMessage(), FermatException.wrapException(e), "ACTOR ASSET ISSUER", "Cant get ACTOR ASSET ISSUER list, unknown failure.");
        }

        return dapActors;
    }

    public ActorAssetIssuer getLastNotification(String actorIssuerPublicKey) throws CantGetAssetUserActorsException {
        try {
            ActorAssetIssuer assetIssuerActorRecord = null;
            /**
             * 1) Get the table.
             */
            final DatabaseTable table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant get ACTOR ASSET ISSUER identity list, table not found.", "Plugin Identity", "Cant get ACTOR ASSET ISSUER, table not found.");

            // 2) Find all Intra Users.
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorIssuerPublicKey, DatabaseFilterType.EQUAL);
            table.setFilterTop("1");
            table.addFilterOrder(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            table.loadToMemory();

            assetIssuerActorRecord = this.getActorFromRecordRegistered(table.getRecords());

            return assetIssuerActorRecord;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUserActorsException(e.getMessage(), e, "ACTOR ASSET ISSUER", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetAssetUserActorsException(e.getMessage(), FermatException.wrapException(e), "ACTOR ASSET ISSUER", "Cant get ACTOR ASSET ISSUER, unknown failure.");
        }
    }

    /**
     * Private Methods
     */
    private void updateAssetIssuerProfileImage(String publicKey, byte[] profileImage) throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantPersistProfileImageException {
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
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", FermatException.wrapException(e), "", "");
        }
    }

    private void persistNewAssetIssuerProfileImage(String publicKey, byte[] profileImage) throws org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantPersistProfileImageException {
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
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);

        } catch (CantCreateFileException e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", FermatException.wrapException(e), "", "");
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

    private void addRecordsToList(List<ActorAssetIssuer> list, List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetAssetIssuerActorProfileImageException {
        for (DatabaseTableRecord record : records) {

            //Inicializar el AssetIssuerActorRecord, nombre y public key son obligatorios.
            AssetIssuerActorRecord assetIssuerActorRecord = new AssetIssuerActorRecord(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME), record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME));

            assetIssuerActorRecord.setDapConnectionState(DAPConnectionState.getByCode(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME)));
            assetIssuerActorRecord.setProfileImage(getAssetIssuerProfileImagePrivateKey(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME)));
            assetIssuerActorRecord.setDescription(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME));
            assetIssuerActorRecord.setRegistrationDate(record.getLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTRATION_DATE_COLUMN_NAME));

            //LOCATION
            DeviceLocation deviceLocation = new DeviceLocation();
            deviceLocation.setLatitude(record.getDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME));
            deviceLocation.setLongitude(record.getDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME));
            assetIssuerActorRecord.setLocation(deviceLocation);
            // Add records to list. AssetIssuerActorRecord

            list.add(assetIssuerActorRecord);

        }
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
            table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists, table not  found.", "Asset Issuer Actor", "Cant check if alias exists, table not found.");
            }
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, assetIssuerToAddPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Asset Issuer Actor", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");

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
            table = this.database.getTable(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists, table not  found.", "Actor Asset Issuer Registered", "Cant check if alias exists, table not found.");
            }
            table.addStringFilter(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetIssuerPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Actor Asset Issuer Registered", "Cant load " + org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Actor Asset Issuer Registered", "Cant check if alias exists, unknown failure.");
        }
    }

    private ActorAssetIssuer getActorFromRecord(List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetAssetIssuerActorProfileImageException {
        AssetIssuerActorRecord actorAssetIssuer = null;
        for (DatabaseTableRecord record : records) {
            actorAssetIssuer = new AssetIssuerActorRecord(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME),
                    DAPConnectionState.getByCode(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME)),
                    record.getDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME),
                    record.getDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME),
                    record.getLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTRATION_DATE_COLUMN_NAME),
                    record.getLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LAST_CONNECTION_DATE_COLUMN_NAME),
                    Actors.getByCode(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TYPE_COLUMN_NAME)),
                    record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME),
                    record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_EXTENDED_COLUMN_NAME),
                    getAssetIssuerProfileImagePrivateKey(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME)));
        }
        return actorAssetIssuer;
    }

    private ActorAssetIssuer getActorFromRecordRegistered(List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetAssetIssuerActorProfileImageException {
        AssetIssuerActorRecord actorAssetIssuer = null;
        for (DatabaseTableRecord record : records) {
            actorAssetIssuer = new AssetIssuerActorRecord(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME),
                    DAPConnectionState.getByCode(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME)),
                    record.getDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME),
                    record.getDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME),
                    record.getLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME),
                    record.getLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME),
                    Actors.getByCode(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TYPE_COLUMN_NAME)),
                    record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_DESCRIPTION_COLUMN_NAME),
                    record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_EXTENDED_COLUMN_NAME),
                    getAssetIssuerProfileImagePrivateKey(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME))
            );
        }
        return actorAssetIssuer;
    }

    private void addRecordsTableRegisteredToList(List<ActorAssetIssuer> list, List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetAssetIssuerActorProfileImageException {

        for (DatabaseTableRecord record : records) {

            list.add(new AssetIssuerActorRecord(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME),
                            record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME),
                            DAPConnectionState.getByCode(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME)),
                            record.getDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME),
                            record.getDoubleValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME),
                            record.getLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME),
                            record.getLongValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME),
                            Actors.getByCode(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TYPE_COLUMN_NAME)),
                            record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_DESCRIPTION_COLUMN_NAME),
                            record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_EXTENDED_COLUMN_NAME),
                            getAssetIssuerProfileImagePrivateKey(record.getStringValue(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME)))
            );
        }
    }
}
