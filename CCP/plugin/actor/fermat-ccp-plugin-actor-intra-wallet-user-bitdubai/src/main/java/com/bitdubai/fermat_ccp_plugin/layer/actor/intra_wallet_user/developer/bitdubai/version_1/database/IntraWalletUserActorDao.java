package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
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
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.IntraWalletUser;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantAddPendingIntraWalletUserException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantGetIntraWalletUserActorProfileImageException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantGetIntraWalletUsersListException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraWalletUserActorDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantUpdateConnectionException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.structure.IntraWalletUserActor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserActorDao</code>
 * has all methods related with database access.<p/>
 * <p/>
 * <p/>
 * Created by natalia on 12/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraWalletUserActorDao {

    private static final String PROFILE_IMAGE_DIRECTORY_NAME = DeviceDirectory.LOCAL_USERS.getName() + "/CCP/intraWalletUser";
    private static final String PROFILE_IMAGE_FILE_NAME_PREFIX = "profileImage";

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final PluginFileSystem pluginFileSystem;
    private final UUID pluginId;

    public IntraWalletUserActorDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                   final PluginFileSystem pluginFileSystem,
                                   final UUID pluginId) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    private Database database;

    public void initializeDatabase() throws CantInitializeIntraWalletUserActorDatabaseException {

        try {

            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            throw new CantInitializeIntraWalletUserActorDatabaseException(cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException e) {

            IntraWalletUserActorDatabaseFactory databaseFactory = new IntraWalletUserActorDatabaseFactory(pluginDatabaseSystem);

            try {

                database = databaseFactory.createDatabase(this.pluginId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME);

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {

                throw new CantInitializeIntraWalletUserActorDatabaseException(cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        } catch (Exception e) {
            throw new CantInitializeIntraWalletUserActorDatabaseException(e, "", "Exception not handled by the plugin, there is a unknown problem and i cannot open the database.");
        }
    }

    public void createNewIntraWalletUser(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage, ConnectionState contactState) throws CantAddPendingIntraWalletUserException {

        try {

            /**
             * if intra user exist on table
             * change status
             */
            if (intraUserExists(intraUserToAddPublicKey)) {

                this.updateConnectionState(intraUserLoggedInPublicKey, intraUserToAddPublicKey, contactState);

            } else {
                /**
                 * Get actual date
                 */
                Date d = new Date();
                long milliseconds = d.getTime();

                DatabaseTable table = this.database.getTable(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setStringValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_PUBLIC_KEY_COLUMN_NAME, intraUserToAddPublicKey);
                record.setStringValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_NAME_COLUMN_NAME, intraUserToAddName);
                record.setStringValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_CONTACT_STATE_COLUMN_NAME, contactState.getCode());
                record.setStringValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, intraUserLoggedInPublicKey);
                record.setLongValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_REGISTRATION_DATE_COLUMN_NAME, milliseconds);
                record.setLongValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_MODIFIED_DATE_COLUMN_NAME, milliseconds);

                table.insertRecord(record);


                /**
                 * Persist profile image on a file
                 */
                if(profileImage!=null) persistNewUserProfileImage(intraUserToAddPublicKey, profileImage);

            }


        } catch (CantInsertRecordException e) {

            throw new CantAddPendingIntraWalletUserException("CAN'T INSERT INTRA USER", e, "", "Cant create new intra user, insert database problems.");

        } catch (CantUpdateConnectionException e) {

            throw new CantAddPendingIntraWalletUserException("CAN'T INSERT INTRA USER", FermatException.wrapException(e), "", "Cant update exist intra user state, unknown failure.");

        } catch (Exception e) {
            // Failure unknown.
            throw new CantAddPendingIntraWalletUserException("CAN'T INSERT INTRA USER", FermatException.wrapException(e), "", "Cant create new intra user, unknown failure.");
        }


    }

    public void updateConnectionState(final String intraUserLoggedInPublicKey,
                                      final String intraUserToAddPublicKey,
                                      final ConnectionState contactState) throws CantUpdateConnectionException {

        try {

            /**
             * 1) Get the table.
             */
            final DatabaseTable table = this.database.getTable(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME);

            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant get intra user actor list, table not found.", "Intra User Actor", "");

            // 2) Find the Intra User , filter by keys.
            table.setStringFilter(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_PUBLIC_KEY_COLUMN_NAME, intraUserToAddPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, intraUserLoggedInPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            /**
             * Get actual date
             */
            Date d = new Date();
            long milliseconds = d.getTime();

            // 3) Get Intra user record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {

                record.setStringValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_CONTACT_STATE_COLUMN_NAME, contactState.getCode());
                record.setLongValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_MODIFIED_DATE_COLUMN_NAME, milliseconds);
                table.updateRecord(record);
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantUpdateConnectionException(e.getMessage(), e, "Intra User Actor", "Cant load " + IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME + " table in memory.");

        } catch (CantUpdateRecordException e) {

            throw new CantUpdateConnectionException(e.getMessage(), e, "Intra User Actor", "Cant load " + IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME + " table in memory.");

        } catch (Exception e) {

            throw new CantUpdateConnectionException(e.getMessage(), FermatException.wrapException(e), "Intra User Actor", "Cant get developer identity list, unknown failure.");
        }
    }


    public List<IntraWalletUser> getAllConnectedIntraWalletUsers(String intraUserLoggedInPublicKey, int max, int offset) throws CantGetIntraWalletUsersListException {

        // Get Intra Users identities list.
        try {

            final ConnectionState connectedState = ConnectionState.CONNECTED;

            /**
             * 1) Get the table.
             */
            final DatabaseTable table = this.database.getTable(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME);

            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant get intra user identity list, table not found.", "Plugin Identity", "Cant get Intra User identity list, table not found.");

            // 2) Find all Intra Users.
            table.setStringFilter(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, intraUserLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_CONTACT_STATE_COLUMN_NAME, connectedState.getCode(), DatabaseFilterType.EQUAL);

            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));

            table.loadToMemory();

            List<IntraWalletUser> list = new ArrayList<>(); // Intra User Actor list.

            // 3) Get Intra Users Recorod.
            for (DatabaseTableRecord record : table.getRecords()) {

                // Add records to list.
                list.add(new IntraWalletUserActor(record.getStringValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_NAME_COLUMN_NAME),
                        record.getStringValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_PUBLIC_KEY_COLUMN_NAME),
                        null,//getIntraUserProfileImagePrivateKey(record.getStringValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_REGISTRATION_DATE_COLUMN_NAME),
                        ConnectionState.getByCode(record.getStringValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_CONTACT_STATE_COLUMN_NAME))));
            }

            // Return the list values.
            return list;

        } catch (CantLoadTableToMemoryException e) {

            throw new CantGetIntraWalletUsersListException(e.getMessage(), e, "Intra User Actor", "Cant load " + IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME + " table in memory.");
        //} catch (CantGetIntraWalletUserActorProfileImageException e) {
            // Failure unknown.
       //     throw new CantGetIntraWalletUsersListException(e.getMessage(), e, "Intra User Actor", "Can't get profile ImageMiddleware.");

        } catch (Exception e) {

            throw new CantGetIntraWalletUsersListException(e.getMessage(), FermatException.wrapException(e), "Intra User Actor", "Cant get Instra User Actor list, unknown failure.");
        }


    }


    public List<IntraWalletUser> getAllConnectedIntraWalletUsers(final String intraActorSelectedPublicKey,
                                                                 final ConnectionState contactState,
                                                                 final int max,
                                                                 final int offset) throws CantGetIntraWalletUsersListException {


        // Setup method.
        List<IntraWalletUser> list = new ArrayList<>(); // Intra User Actor list.
        DatabaseTable table;

        // Get Intra Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME);

            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant get intra user identity list, table not found.", "Plugin Identity", "Cant get Intra User identity list, table not found.");


            // 2) Find  Intra Users by state.
            table.setStringFilter(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, intraActorSelectedPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_CONTACT_STATE_COLUMN_NAME, contactState.getCode(), DatabaseFilterType.EQUAL);

            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));

            table.loadToMemory();

            // 3) Get Intra Users Recorod.
            for (DatabaseTableRecord record : table.getRecords()) {

                // Add records to list.
                list.add(new IntraWalletUserActor(record.getStringValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_NAME_COLUMN_NAME),
                        record.getStringValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_PUBLIC_KEY_COLUMN_NAME),
                        null,//getIntraUserProfileImagePrivateKey(record.getStringValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_REGISTRATION_DATE_COLUMN_NAME),
                        ConnectionState.getByCode(record.getStringValue(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_CONTACT_STATE_COLUMN_NAME))));
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantGetIntraWalletUsersListException(e.getMessage(), e, "Intra User Actor", "Cant load " + IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME + " table in memory.");
       //} catch (CantGetIntraWalletUserActorProfileImageException e) {
            // Failure unknown.
         //   throw new CantGetIntraWalletUsersListException(e.getMessage(), e, "Intra User Actor", "Can't get profile ImageMiddleware.");

        } catch (Exception e) {

            throw new CantGetIntraWalletUsersListException(e.getMessage(), FermatException.wrapException(e), "Intra User Actor", "Cant get Intra User Actor list, unknown failure.");
        }


        // Return the list values.
        return list;
    }

    /**
     * Private Methods
     */


    private void persistNewUserProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {

        try {

            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    PROFILE_IMAGE_DIRECTORY_NAME,
                    buildProfileImageFileName(publicKey),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(profileImage);
            file.persistToMedia();

        } catch (CantPersistFileException e) {
            throw new CantPersistProfileImageException(e, "Error persist file.", null);

        } catch (CantCreateFileException e) {

            throw new CantPersistProfileImageException(e, "Error creating file.", null);
        } catch (Exception e) {

            throw new CantPersistProfileImageException(FermatException.wrapException(e), "", "");
        }
    }


    private byte[] getIntraUserProfileImagePrivateKey(final String publicKey) throws CantGetIntraWalletUserActorProfileImageException {

        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    PROFILE_IMAGE_DIRECTORY_NAME,
                    buildProfileImageFileName(publicKey),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            return file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetIntraWalletUserActorProfileImageException(e, "Error loaded file.", null);

        } catch (FileNotFoundException | CantCreateFileException e) {

            throw new CantGetIntraWalletUserActorProfileImageException(e, "Error getting Intra User Actor private keys file.", null);
        } catch (Exception e) {

            throw new CantGetIntraWalletUserActorProfileImageException(FermatException.wrapException(e), "", "");
        }
    }

    private String buildProfileImageFileName(final String publicKey) {
        return PROFILE_IMAGE_FILE_NAME_PREFIX + "_" + publicKey;
    }

    /**
     * <p>Method that check if intra user public key exists.
     *
     * @param intraUserToAddPublicKey
     * @return boolean exists
     * @throws CantCreateNewDeveloperException
     */
    private boolean intraUserExists(final String intraUserToAddPublicKey) throws CantCreateNewDeveloperException {

        try {

            final DatabaseTable table = this.database.getTable(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME);

            if (intraUserToAddPublicKey == null) {
                throw new CantGetUserDeveloperIdentitiesException("intraUserToAddPublicKey null", "intraUserToAddPublicKey must not be null.");
            }

            table.setStringFilter(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_PUBLIC_KEY_COLUMN_NAME, intraUserToAddPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Intra User Actor", "Cant load " + IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME + " table in memory.");

        } catch (Exception e) {

            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Intra User Actor", "Cant check if alias exists, unknown failure.");
        }
    }

}
