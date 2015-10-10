package com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
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
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.database.RedeemPointActorDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.database.RedeemPointActorDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantAddPendingRedeemPointException;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantGetRedeemPointActorProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantGetRedeemPointsListException;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantInitializeReddemPointActorDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantUpdateRedeemPointConnectionException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nerio on 06/10/15.
 */
public class RedeemPointActorDao implements Serializable {

    String REDEEM_POINT_PROFILE_IMAGE_FILE_NAME = "RedeemPointActorProfileImage";

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

    public RedeemPointActorDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    /**
     * Represent de Database where i will be working with
     */
    Database database;

    /**
     * This method open or creates the database i'll be working with     *

     * @throws CantInitializeReddemPointActorDatabaseException
     */
    public void initializeDatabase() throws CantInitializeReddemPointActorDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, RedeemPointActorDatabaseConstants.REDEEM_POINT_DATABASE_NAME);
            database.closeDatabase();
        }
        catch (CantOpenDatabaseException cantOpenDatabaseException)
        {
            throw new CantInitializeReddemPointActorDatabaseException(CantInitializeReddemPointActorDatabaseException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        }
        catch (DatabaseNotFoundException e)
        {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            RedeemPointActorDatabaseFactory databaseFactory = new RedeemPointActorDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = databaseFactory.createDatabase(this.pluginId, RedeemPointActorDatabaseConstants.REDEEM_POINT_DATABASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeReddemPointActorDatabaseException(CantInitializeReddemPointActorDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        }
        catch (Exception e)
        {
            throw new CantInitializeReddemPointActorDatabaseException(CantInitializeReddemPointActorDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a unknown problem and i cannot open the database.");
        }
    }


    public void createNewRedeemPoint(String redeemPointLoggedInPublicKey, String redeemPointToAddName, String redeemPointToAddPublicKey, byte[] profileImage, ConnectionState connectionState) throws CantAddPendingRedeemPointException {

        try {
            /**
             * if Redeem Point exist on table
             * change status
             */
            if (redeemPointExists(redeemPointToAddPublicKey)) {

                this.updateRedeemPointConnectionState(redeemPointLoggedInPublicKey, redeemPointToAddPublicKey, connectionState);

            } else {
                /**
                 * Get actual date
                 */
                Date d = new Date();
                long milliseconds = d.getTime();

                DatabaseTable table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME, redeemPointToAddPublicKey);
                record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_NAME_COLUMN_NAME, redeemPointToAddName);
                record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_STATE_COLUMN_NAME, connectionState.getCode());
                record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, redeemPointLoggedInPublicKey);
                record.setLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME, milliseconds);
                record.setLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_MODIFIED_DATE_COLUMN_NAME, milliseconds);

                table.insertRecord(record);
                /**
                 * Persist profile image on a file
                 */
                persistNewRedeemPointProfileImage(redeemPointToAddPublicKey, profileImage);

                database.closeDatabase();
            }
        } catch (CantInsertRecordException e) {
            database.closeDatabase();
            throw new CantAddPendingRedeemPointException("CAN'T INSERT REDEEM POINT", e, "", "Cant create new REDEEM POINT, insert database problems.");

        } catch (CantUpdateRedeemPointConnectionException e) {
            database.closeDatabase();
            throw new CantAddPendingRedeemPointException("CAN'T INSERT REDEEM POINT", FermatException.wrapException(e), "", "Cant update exist REDEEM POINT state, unknown failure.");

        } catch (Exception e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantAddPendingRedeemPointException("CAN'T INSERT REDEEM POINT", FermatException.wrapException(e), "", "Cant create new REDEEM POINT, unknown failure.");
        }
    }


    public void updateRedeemPointConnectionState(String redeemPointLoggedInPublicKey, String redeemPointToAddPublicKey, ConnectionState connectionState) throws CantUpdateRedeemPointConnectionException {

        DatabaseTable table;

        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Redeem Point actor list, table not found.", "Redeem Point Actor", "");
            }

            // 2) Find the Redeem Point , filter by keys.
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME, redeemPointToAddPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, redeemPointLoggedInPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            /**
             * Get actual date
             */
            Date d = new Date();
            long milliseconds = d.getTime();

            // 3) Get Redeem Point record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_STATE_COLUMN_NAME, connectionState.getCode());
                record.setLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_MODIFIED_DATE_COLUMN_NAME, milliseconds);
                table.updateRecord(record);
            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantUpdateRedeemPointConnectionException(e.getMessage(), e, "Redeem Point Actor", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME + " table in memory.");

        } catch (CantUpdateRecordException e) {
            database.closeDatabase();
            throw new CantUpdateRedeemPointConnectionException(e.getMessage(), e, "Redeem Point Actor", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            database.closeDatabase();
            throw new CantUpdateRedeemPointConnectionException(e.getMessage(), FermatException.wrapException(e), "Redeem Point Actor", "Cant get developer identity list, unknown failure.");
        }
    }


    public List<ActorAssetRedeemPoint> getAllARedeemPoints(String redeemPointLoggedInPublicKey, int max, int offset) throws CantGetRedeemPointsListException {

        // Setup method.
        List<ActorAssetRedeemPoint> list = new ArrayList<ActorAssetRedeemPoint>(); // Redeem Point Actor list.
        DatabaseTable table;

        // Get Redeem Points identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Redeem Point identity list, table not found.", "Plugin Identity", "Cant get Redeem Point identity list, table not found.");
            }

            // 2) Find all Redeem Points.
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, redeemPointLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_STATE_COLUMN_NAME, ConnectionState.CONNECTED.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Redeem Points Recorod.
            for (DatabaseTableRecord record : table.getRecords()) {
                // Add records to list.
                list.add(new RedeemPointActorRecord(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_NAME_COLUMN_NAME),
                        record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME),
                        getRedeemPointProfileImagePrivateKey(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME),
                        ConnectionState.valueOf(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_STATE_COLUMN_NAME))));
            }
            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetRedeemPointsListException(e.getMessage(), e, "Redeem Point Actor", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_DATABASE_NAME + " table in memory.");
        } catch (CantGetRedeemPointActorProfileImageException e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantGetRedeemPointsListException(e.getMessage(), e, "Redeem Point Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            database.closeDatabase();
            throw new CantGetRedeemPointsListException(e.getMessage(), FermatException.wrapException(e), "Redeem Point Actor", "Cant get Redeem Point Actor list, unknown failure.");
        }
        // Return the list values.
        return list;
    }

    public List<ActorAssetRedeemPoint> getRedeemPoints(String redeemPointLoggedInPublicKey, ConnectionState connectionState, int max, int offset) throws CantGetRedeemPointsListException {

        // Setup method.
        List<ActorAssetRedeemPoint> list = new ArrayList<ActorAssetRedeemPoint>(); // Redeem Point Actor list.
        DatabaseTable table;

        // Get Redeem Points identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Redeem Point identity list, table not found.", "Plugin Identity", "Cant get Redeem Point identity list, table not found.");
            }
            // 2) Find  Redeem Points by state.
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, redeemPointLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_STATE_COLUMN_NAME, connectionState.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Redeem Points Recorod.
            for (DatabaseTableRecord record : table.getRecords()) {

                // Add records to list.
                list.add(new RedeemPointActorRecord(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_NAME_COLUMN_NAME),
                        record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME),
                        getRedeemPointProfileImagePrivateKey(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME),
                        ConnectionState.valueOf(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_STATE_COLUMN_NAME))));
            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetRedeemPointsListException(e.getMessage(), e, "Redeem Point Actor", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_DATABASE_NAME + " table in memory.");
        } catch (CantGetRedeemPointActorProfileImageException e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantGetRedeemPointsListException(e.getMessage(), e, "Redeem Point Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            database.closeDatabase();
            throw new CantGetRedeemPointsListException(e.getMessage(), FermatException.wrapException(e), "Redeem Points Actor", "Cant get Redeem Point Actor list, unknown failure.");
        }
        // Return the list values.
        return list;
    }

    /**
     * Private Methods
     */
    private void persistNewRedeemPointProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    REDEEM_POINT_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
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

    private byte[] getRedeemPointProfileImagePrivateKey(String publicKey) throws CantGetRedeemPointActorProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    REDEEM_POINT_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetRedeemPointActorProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);

        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetRedeemPointActorProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting Redeem Point Actor private keys file.", null);
        } catch (Exception e) {
            throw new CantGetRedeemPointActorProfileImageException("CAN'T GET IMAGE PROFILE ", FermatException.wrapException(e), "", "");
        }
        return profileImage;
    }

    /**
     * <p>Method that check if Redeem Point public key exists.
     *
     * @param redeemPointToAddPublicKey
     * @return boolean exists
     * @throws CantCreateNewDeveloperException
     */
    private boolean redeemPointExists(String redeemPointToAddPublicKey) throws CantCreateNewDeveloperException {

        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */

        try {
            table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists, table not  found.", "Redeem Point Actor", "Cant check if alias exists, table not found.");
            }
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME, redeemPointToAddPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Redeem Point Actor", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_DATABASE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Redeem Point Actor", "Cant check if alias exists, unknown failure.");
        }
    }
}
