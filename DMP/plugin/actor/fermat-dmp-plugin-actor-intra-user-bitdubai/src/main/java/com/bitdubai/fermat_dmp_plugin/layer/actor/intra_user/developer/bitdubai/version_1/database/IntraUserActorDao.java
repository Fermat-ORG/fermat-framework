package com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database;



import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.enums.ContactState;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUser;
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
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantAddPendingIntraUserException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantGetIntraUserActorProfileImageException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraUserActorDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateIntraUserConnectionException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.structure.IntraUserActorActor;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDao</code>
 * has all methods related with database access.<p/>
 * <p/>
 * <p/>
 * Created by natalia on 12/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraUserActorDao implements Serializable{

    /**
     * Represent the Plugin Database.
     */


    String INTRA_USERS_PROFILE_IMAGE_FILE_NAME = "intraUserActorProfileImage";
    private PluginDatabaseSystem pluginDatabaseSystem;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    /**
     * Constructor with parameters
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */

    public IntraUserActorDao(PluginDatabaseSystem pluginDatabaseSystem,PluginFileSystem pluginFileSystem, UUID pluginId) {
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

     * @throws CantInitializeIntraUserActorDatabaseException
     */
    public void initializeDatabase() throws CantInitializeIntraUserActorDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, IntraUserActorDatabaseConstants.INTRA_USER_DATABASE_NAME);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeIntraUserActorDatabaseException(CantInitializeIntraUserActorDatabaseException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            IntraUserActorDatabaseFactory databaseFactory = new IntraUserActorDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = databaseFactory.createDatabase(this.pluginId, IntraUserActorDatabaseConstants.INTRA_USER_DATABASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeIntraUserActorDatabaseException(CantInitializeIntraUserActorDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        }
    }

    public void createNewIntraUser (String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage, ContactState contactState) throws CantAddPendingIntraUserException {

        try {

            /**
             * if intra user exist on table
             * change status
             */
            if (intraUserExists(intraUserToAddPublicKey)) {

                this.updateIntraUserConnectionState(intraUserLoggedInPublicKey,intraUserToAddPublicKey,contactState);

            }
            else
            {
                /**
                 * Get actual date
                 */
                Date d = new Date();
                long milliseconds = d.getTime();

                DatabaseTable table = this.database.getTable(IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setStringValue(IntraUserActorDatabaseConstants.INTRA_USER_INTRA_USER_PUBLIC_KEY_COLUMN_NAME, intraUserToAddPublicKey);
                record.setStringValue(IntraUserActorDatabaseConstants.INTRA_USER_NAME_COLUMN_NAME,intraUserToAddName );
                record.setStringValue(IntraUserActorDatabaseConstants.INTRA_USER_CONTACT_STATE_COLUMN_NAME, contactState.getCode());
                record.setStringValue(IntraUserActorDatabaseConstants.INTRA_USER_INTRA_USER_LOGGED_PUBLIC_KEY_COLUMN_NAME, intraUserLoggedInPublicKey);
                record.setLongValue(IntraUserActorDatabaseConstants.INTRA_USER_REGISTRATION_DATE_COLUMN_NAME, milliseconds);
                record.setLongValue(IntraUserActorDatabaseConstants.INTRA_USER_REGISTRATION_DATE_COLUMN_NAME, milliseconds);

                table.insertRecord(record);


                /**
                 * Persist profile image on a file
                 */
                persistNewUserProfileImage(intraUserToAddPublicKey, profileImage);

                database.closeDatabase();
            }



        } catch (CantInsertRecordException e){
            database.closeDatabase();

            throw new CantAddPendingIntraUserException("CAN'T INSERT INTRA USER", e, "", "Cant create new intra user, insert database problems.");

        } catch (CantUpdateIntraUserConnectionException e) {
            database.closeDatabase();

            throw new CantAddPendingIntraUserException ("CAN'T INSERT INTRA USER", FermatException.wrapException(e), "", "Cant update exist intra user state, unknown failure.");

        } catch (Exception e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantAddPendingIntraUserException ("CAN'T INSERT INTRA USER", FermatException.wrapException(e), "", "Cant create new intra user, unknown failure.");
        }


    }

    public void updateIntraUserConnectionState (String intraUserLoggedInPublicKey, String intraUserToAddPublicKey,ContactState contactState) throws CantUpdateIntraUserConnectionException {


          DatabaseTable table;

        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable (IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get intra user actor list, table not found.", "Intra User Actor", "");
            }


            // 2) Find the Intra User , filter by keys.
            table.setStringFilter(IntraUserActorDatabaseConstants.INTRA_USER_INTRA_USER_PUBLIC_KEY_COLUMN_NAME, intraUserToAddPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(IntraUserActorDatabaseConstants.INTRA_USER_INTRA_USER_LOGGED_PUBLIC_KEY_COLUMN_NAME, intraUserLoggedInPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            /**
             * Get actual date
             */
            Date d = new Date();
            long milliseconds = d.getTime();

            // 3) Get Intra user record and update state.
            for (DatabaseTableRecord record : table.getRecords ()) {

                record.setStringValue(IntraUserActorDatabaseConstants.INTRA_USER_CONTACT_STATE_COLUMN_NAME,contactState.getCode());
                record.setLongValue(IntraUserActorDatabaseConstants.INTRA_USER_MODIFIED_DATE_COLUMN_NAME,milliseconds);
                table.updateRecord(record);
            }

            database.closeDatabase();
        }
        catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantUpdateIntraUserConnectionException(e.getMessage(), e, "Intra User Actor", "Cant load " + IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME + " table in memory.");

        }
        catch (CantUpdateRecordException e) {
            database.closeDatabase();
            throw new CantUpdateIntraUserConnectionException(e.getMessage(), e, "Intra User Actor", "Cant load " + IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME + " table in memory.");

        }
        catch (Exception e) {
            database.closeDatabase();
            throw new CantUpdateIntraUserConnectionException (e.getMessage(), FermatException.wrapException(e), "Intra User Actor", "Cant get developer identity list, unknown failure.");
        }



    }


    public List<ActorIntraUser> getAllIntraUsers (String intraUserLoggedInPublicKey) throws CantGetIntraUsersListException {


        // Setup method.
        List<ActorIntraUser> list = new ArrayList<ActorIntraUser>(); // Intra User Actor list.
        DatabaseTable table;

        // Get Intra Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable (IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get intra user identity list, table not found.", "Plugin Identity", "Cant get Intra User identity list, table not found.");
            }


            // 2) Find all Intra Users.
            table.setStringFilter(IntraUserActorDatabaseConstants.INTRA_USER_INTRA_USER_LOGGED_PUBLIC_KEY_COLUMN_NAME, intraUserLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(IntraUserActorDatabaseConstants.INTRA_USER_CONTACT_STATE_COLUMN_NAME, ContactState.CONNECTED.getCode(), DatabaseFilterType.EQUAL);

            table.loadToMemory();


            // 3) Get Intra Users Recorod.
            for (DatabaseTableRecord record : table.getRecords ()) {

                // Add records to list.
                list.add(new IntraUserActorActor(record.getStringValue(IntraUserActorDatabaseConstants.INTRA_USER_NAME_COLUMN_NAME),
                        record.getStringValue (IntraUserActorDatabaseConstants.INTRA_USER_INTRA_USER_PUBLIC_KEY_COLUMN_NAME),
                        getIntraUserProfileImagePrivateKey(record.getStringValue(IntraUserActorDatabaseConstants.INTRA_USER_INTRA_USER_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(IntraUserActorDatabaseConstants.INTRA_USER_REGISTRATION_DATE_COLUMN_NAME),
                        ContactState.valueOf(record.getStringValue(IntraUserActorDatabaseConstants.INTRA_USER_CONTACT_STATE_COLUMN_NAME))));
            }

            database.closeDatabase();
        }
        catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetIntraUsersListException(e.getMessage(), e, "Intra User Actor", "Cant load " + IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME + " table in memory.");
        }
        catch (CantGetIntraUserActorProfileImageException e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantGetIntraUsersListException (e.getMessage(), e, "Intra User Actor", "Can't get profile ImageMiddleware.");

        } catch (Exception e) {
            database.closeDatabase();
            throw new CantGetIntraUsersListException (e.getMessage(), FermatException.wrapException(e), "Intra User Actor", "Cant get Instra User Actor list, unknown failure.");
        }


        // Return the list values.
        return list;
    }


    public List<ActorIntraUser> getIntraUsers (String intraUserLoggedInPublicKey, ContactState contactState) throws CantGetIntraUsersListException {


        // Setup method.
        List<ActorIntraUser> list = new ArrayList<ActorIntraUser>(); // Intra User Actor list.
        DatabaseTable table;

        // Get Intra Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable (IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get intra user identity list, table not found.", "Plugin Identity", "Cant get Intra User identity list, table not found.");
            }


            // 2) Find  Intra Users by state.
            table.setStringFilter(IntraUserActorDatabaseConstants.INTRA_USER_INTRA_USER_LOGGED_PUBLIC_KEY_COLUMN_NAME, intraUserLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(IntraUserActorDatabaseConstants.INTRA_USER_CONTACT_STATE_COLUMN_NAME, contactState.getCode(), DatabaseFilterType.EQUAL);

            table.loadToMemory();


            // 3) Get Intra Users Recorod.
            for (DatabaseTableRecord record : table.getRecords ()) {

                // Add records to list.
                list.add(new IntraUserActorActor(record.getStringValue(IntraUserActorDatabaseConstants.INTRA_USER_NAME_COLUMN_NAME),
                        record.getStringValue (IntraUserActorDatabaseConstants.INTRA_USER_INTRA_USER_PUBLIC_KEY_COLUMN_NAME),
                        getIntraUserProfileImagePrivateKey(record.getStringValue(IntraUserActorDatabaseConstants.INTRA_USER_INTRA_USER_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(IntraUserActorDatabaseConstants.INTRA_USER_REGISTRATION_DATE_COLUMN_NAME),
                        ContactState.valueOf(record.getStringValue(IntraUserActorDatabaseConstants.INTRA_USER_CONTACT_STATE_COLUMN_NAME))));
            }

            database.closeDatabase();
        }
        catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetIntraUsersListException(e.getMessage(), e, "Intra User Actor", "Cant load " + IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME + " table in memory.");
        }
        catch (CantGetIntraUserActorProfileImageException e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantGetIntraUsersListException (e.getMessage(), e, "Intra User Actor", "Can't get profile ImageMiddleware.");

        } catch (Exception e) {
            database.closeDatabase();
            throw new CantGetIntraUsersListException (e.getMessage(), FermatException.wrapException(e), "Intra User Actor", "Cant get Intra User Actor list, unknown failure.");
        }


        // Return the list values.
        return list;
    }

    public List<ActorIntraUser> getYourPendingConnections (String intraUserLoggedInPublicKey, ContactState contactState) throws CantGetIntraUsersListException {


        // Setup method.
        List<ActorIntraUser> list = new ArrayList<ActorIntraUser>(); // Intra User Actor list.
        DatabaseTable table;

        // Get Intra Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable (IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get intra user identity list, table not found.", "Plugin Identity", "Cant get Intra User identity list, table not found.");
            }


            // 2) Find  Intra Users by state.
            table.setStringFilter(IntraUserActorDatabaseConstants.INTRA_USER_INTRA_USER_PUBLIC_KEY_COLUMN_NAME, intraUserLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(IntraUserActorDatabaseConstants.INTRA_USER_CONTACT_STATE_COLUMN_NAME, contactState.getCode(), DatabaseFilterType.EQUAL);

            table.loadToMemory();


            // 3) Get Intra Users Recorod.
            for (DatabaseTableRecord record : table.getRecords ()) {

                // Add records to list.
                list.add(new IntraUserActorActor(record.getStringValue(IntraUserActorDatabaseConstants.INTRA_USER_NAME_COLUMN_NAME),
                        record.getStringValue (IntraUserActorDatabaseConstants.INTRA_USER_INTRA_USER_PUBLIC_KEY_COLUMN_NAME),
                        getIntraUserProfileImagePrivateKey(record.getStringValue(IntraUserActorDatabaseConstants.INTRA_USER_INTRA_USER_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(IntraUserActorDatabaseConstants.INTRA_USER_REGISTRATION_DATE_COLUMN_NAME),
                        ContactState.valueOf(record.getStringValue(IntraUserActorDatabaseConstants.INTRA_USER_CONTACT_STATE_COLUMN_NAME))));
            }

            database.closeDatabase();
        }
        catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetIntraUsersListException(e.getMessage(), e, "Intra User Actor", "Cant load " + IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME + " table in memory.");
        }
        catch (CantGetIntraUserActorProfileImageException e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantGetIntraUsersListException (e.getMessage(), e, "Intra User Actor", "Can't get profile Image.");

        } catch (Exception e) {
            database.closeDatabase();
            throw new CantGetIntraUsersListException (e.getMessage(), FermatException.wrapException(e), "Intra User Actor", "Cant get Intra User Actor list, unknown failure.");
        }


        // Return the list values.
        return list;
    }


    /**
     * Private Methods
     */


    private Database openDatabase() throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, IntraUserActorDatabaseConstants.INTRA_USER_DATABASE_NAME);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw  new CantExecuteDatabaseOperationException("ERROR OPEN DATABASE",exception,"", "Error in database plugin.");

        }
        catch (Exception e) {
            throw  new CantExecuteDatabaseOperationException("ERROR OPEN DATABASE",FermatException.wrapException(e),"", "Error in database plugin.");
        }
    }




    private void  persistNewUserProfileImage(String publicKey,byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    INTRA_USERS_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
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
        catch (Exception e) {
            throw  new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ",FermatException.wrapException(e),"", "");
        }
    }



    public byte[] getIntraUserProfileImagePrivateKey(String publicKey) throws CantGetIntraUserActorProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    INTRA_USERS_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );


            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetIntraUserActorProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);

        }
        catch (FileNotFoundException |CantCreateFileException e) {
            throw new CantGetIntraUserActorProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting Intra User Actor private keys file.", null);
        }
        catch (Exception e) {
            throw  new CantGetIntraUserActorProfileImageException("CAN'T GET IMAGE PROFILE ",FermatException.wrapException(e),"", "");
        }

        return profileImage;
    }


    /**
     * <p>Method that check if intra user public key exists.
     * @param intraUserToAddPublicKey
     * @return boolean exists
     * @throws CantCreateNewDeveloperException
     */
    private boolean intraUserExists (String intraUserToAddPublicKey) throws CantCreateNewDeveloperException {


        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */

        try {
            table = this.database.getTable (IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists, table not  found.", "Intra User Actor", "Cant check if alias exists, table not found.");
            }

            table.setStringFilter(IntraUserActorDatabaseConstants.INTRA_USER_INTRA_USER_PUBLIC_KEY_COLUMN_NAME, intraUserToAddPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords ().size () > 0;


        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException (em.getMessage(), em, "Intra User Actor", "Cant load " + IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException (e.getMessage(), FermatException.wrapException(e), "Intra User Actor", "Cant check if alias exists, unknown failure.");
        }
    }


    /**
     * <p>Method that check if alias exists.
     * @param alias
     * @return boolean exists
     * @throws CantCreateNewDeveloperException
     */
    private boolean aliasExists (String alias) throws CantCreateNewDeveloperException {


        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */

        try {
            table = this.database.getTable (IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists, table not  found.", "Intra User Actor", "Cant check if alias exists, table not found.");
            }

            table.setStringFilter(IntraUserActorDatabaseConstants.INTRA_USER_NAME_COLUMN_NAME, alias, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords ().size () > 0;


        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException (em.getMessage(), em, "Intra User Actor", "Cant load " + IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException (e.getMessage(), FermatException.wrapException(e), "Intra User Actor", "Cant check if alias exists, unknown failure.");
        }
    }

}
