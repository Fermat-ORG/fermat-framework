package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.TranslatorIdentity;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.enums.IntraUserNotificationDescriptor;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserNotification;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentity;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantGetIntraUserProfileImageException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeNetworkIntraUserDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 02/09/15.
 */
public class IntraUserNetworkServiceDao {

    UUID databaseOwnerId;
    String databaseName;


    String INTRA_USERS_PROFILE_IMAGE_FILE_NAME = "intraUserActorProfileImage";
    /**
     * Represent the Plugin Database.
     */

    private PluginDatabaseSystem pluginDatabaseSystem;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    /**
     * Represent de Database where i will be working with
     */
    private Database database;


    /**
     * Constructor with parameters
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */

    public IntraUserNetworkServiceDao(PluginDatabaseSystem pluginDatabaseSystem,PluginFileSystem pluginFileSystem, UUID pluginId,Database database) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.database = database;
        this.pluginFileSystem = pluginFileSystem;
    }




    public void saveRequestCache(UUID id, String intraUserLoggedInPublicKey, String alias, String intraUserToAddPublicKey, IntraUserNotificationDescriptor requestDescriptor, byte[] profileImage) throws CantExecuteDatabaseOperationException {
        try
        {
            long millis = new java.util.Date().getTime();

            database = openDatabase();
            DatabaseTable table = this.database.getTable(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setUUIDValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_ID_COLUMN_NAME, id);
            record.setStringValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_INTRA_USER_PUBLIC_KEY_COLUMN_NAME, intraUserToAddPublicKey);
            record.setStringValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_USER_NAME_COLUMN_NAME, alias);
            record.setStringValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_INTRA_USER_LOGGED_IN_PUBLIC_KEY_COLUMN_NAME,intraUserLoggedInPublicKey);
            record.setLongValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_CREATED_TIME_COLUMN_NAME, millis);
            record.setStringValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_DESCRIPTOR_COLUMN_NAME, requestDescriptor.getCode());

            table.insertRecord(record);

            /**
             * Persist profile image on a file
             */

            persistNewUserProfileImage(intraUserToAddPublicKey, profileImage);

            closeDatabase();

        }  catch (CantInitializeNetworkIntraUserDataBaseException e){
            closeDatabase();
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,e,"", "Error deleting request connection record on database");

        }  catch (Exception exception){
            closeDatabase();
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,exception, "", "Error save record to ask intra user acceptance");
        }
    }

    public void deleteRequestRecord(String intraUserLoggedInPublicKey, String intraUserToAddPublicKey) throws CantExecuteDatabaseOperationException {
        try
        {
            database = openDatabase();
            DatabaseTable table = this.database.getTable(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_NAME);
            table.setStringFilter(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_INTRA_USER_LOGGED_IN_PUBLIC_KEY_COLUMN_NAME, intraUserLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_INTRA_USER_PUBLIC_KEY_COLUMN_NAME, intraUserToAddPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();
            // Get request Record.
            for (DatabaseTableRecord record : table.getRecords ()) {
                //delete
                table.deleteRecord(record);
            }

            closeDatabase();

        }  catch (CantInitializeNetworkIntraUserDataBaseException e){
            closeDatabase();
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,e,"", "Error deleting request connection record on database");

        }  catch (Exception exception){
            closeDatabase();
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,exception, "", "Generic Error deleting request connection record on database");
        }
    }

    public List<IntraUserNotification> getAllRequestCacheRecord() throws CantExecuteDatabaseOperationException {
        try
        {
            List<IntraUserNotification>  intraUserNotificationList = new ArrayList<IntraUserNotification>();
            database = openDatabase();
            DatabaseTable table = this.database.getTable(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_NAME);

            table.loadToMemory();

            // Get request Record.
            for (DatabaseTableRecord record : table.getRecords ()) {

                //create list of IntraUserNotifications
                byte[] profileImage = getIntraUserProfileImagePrivateKey(record.getStringValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_INTRA_USER_PUBLIC_KEY_COLUMN_NAME));

                IntraUserNotification intraUserNotification = new IntraUserNetworkServiceNotification(record.getStringValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_INTRA_USER_LOGGED_IN_PUBLIC_KEY_COLUMN_NAME),
                        record.getStringValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_USER_NAME_COLUMN_NAME),
                        record.getStringValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_INTRA_USER_PUBLIC_KEY_COLUMN_NAME),
                        IntraUserNotificationDescriptor.getByCode(record.getStringValue(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_DESCRIPTOR_COLUMN_NAME)),
                        profileImage);

                intraUserNotificationList.add(intraUserNotification) ;

            }

            closeDatabase();

            return intraUserNotificationList;

        }  catch (CantInitializeNetworkIntraUserDataBaseException e){
            closeDatabase();
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,e,"", "Error listing request records from database");

        }  catch (Exception exception){
            closeDatabase();
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,exception, "", "Generic Error listing request records");
        }
    }

    /**
     * Private Methods
     */



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
            throw  new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", FermatException.wrapException(e),"", "");
        }
    }



    private byte[] getIntraUserProfileImagePrivateKey(String publicKey) throws CantGetIntraUserProfileImageException {
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
            throw new CantGetIntraUserProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);

        }
        catch (FileNotFoundException |CantCreateFileException e) {
            throw new CantGetIntraUserProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting Intra User Actor private keys file.", null);
        }
        catch (Exception e) {
            throw  new CantGetIntraUserProfileImageException("CAN'T GET IMAGE PROFILE ",FermatException.wrapException(e),"", "");
        }

        return profileImage;
    }



    private Database openDatabase() throws CantInitializeNetworkIntraUserDataBaseException {
        try {
            if(database == null)
                database = pluginDatabaseSystem.openDatabase(this.databaseOwnerId, IntraUserNetworkServiceDatabaseConstants.DATA_BASE_NAME);
            else
                database.openDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeNetworkIntraUserDataBaseException(CantInitializeNetworkIntraUserDataBaseException.DEFAULT_MESSAGE,cantOpenDatabaseException, "Trying to open database " + databaseName, "Error in Database plugin");
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            throw new CantInitializeNetworkIntraUserDataBaseException(CantInitializeNetworkIntraUserDataBaseException.DEFAULT_MESSAGE,databaseNotFoundException, "Trying to open database " + databaseName, "Error in Database plugin. Database should already exists.");
        }
        return database;
    }

    private void closeDatabase(){
        if(database != null)
            database.closeDatabase();
    }
}
