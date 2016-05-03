package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantGetNotificationException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraActorNetworkServiceDataBaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantAddIntraWalletCacheUserException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantDeleteIntraWalletCacheUserException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantGetIntraUserProfileImageException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantListIntraWalletCacheUserException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 18/12/15.
 */
public class IntraActorNetworkServiceDao {

    private Database database;

    private static final String PROFILE_IMAGE_DIRECTORY_NAME   = DeviceDirectory.LOCAL_USERS.getName() + "/CCP/intraUserNS";
    private static final String PROFILE_IMAGE_FILE_NAME_PREFIX = "profileImage";

    private final PluginFileSystem     pluginFileSystem    ;
    private final UUID                 pluginId            ;

    public IntraActorNetworkServiceDao(Database database,
                                       final PluginFileSystem pluginFileSystem,
                                       final UUID pluginId) {

        this.database = database;
        this.pluginFileSystem     = pluginFileSystem    ;
        this.pluginId             = pluginId            ;
    }



    public void saveIntraUserCache(List<IntraUserInformation> intraUserInformationList) throws CantAddIntraWalletCacheUserException {

        try {

            /**
             * first delete old cache records
             */

            deleteIntraUserCache();

            DatabaseTable table = this.database.getTable(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_TABLE_NAME);
            table.getRecords();

                /**
                 * save intra user info on database
                 */
                Date d = new Date();
                long milliseconds = d.getTime();


            for (IntraUserInformation intraUserInformation : intraUserInformationList) {

                //if record exist I update data
                if(existCacheRecord(intraUserInformation.getPublicKey()))
                {
                    table.addStringFilter(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PUBLIC_KEY_COLUMN_NAME, intraUserInformation.getPublicKey(), DatabaseFilterType.EQUAL);

                    table.loadToMemory();

                    if(table.getRecords().size() > 0) {
                        DatabaseTableRecord record = table.getRecords().get(0);

                        record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PUBLIC_KEY_COLUMN_NAME, intraUserInformation.getPublicKey());
                        record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_ALIAS_COLUMN_NAME, intraUserInformation.getName());

                        record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PHRASE_COLUMN_NAME, intraUserInformation.getPhrase());
                        record.setLongValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_TIMESTAMP_COLUMN_NAME, milliseconds);
                        record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_CITY_COLUMN_NAME, "");
                        record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_COUNTRY_COLUMN_NAME, "");


                        /**
                         * Persist profile image on a file
                         */
                        if (intraUserInformation.getProfileImage() != null && intraUserInformation.getProfileImage().length > 0)
                            persistNewUserProfileImage(intraUserInformation.getPublicKey(), intraUserInformation.getProfileImage());


                        table.updateRecord(record);
                    }

                }
                else
                {
                    DatabaseTableRecord record = table.getEmptyRecord();


                    record.setUUIDValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_ID_COLUMN_NAME, UUID.randomUUID());

                    record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PUBLIC_KEY_COLUMN_NAME, intraUserInformation.getPublicKey());
                    record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_ALIAS_COLUMN_NAME, intraUserInformation.getName());

                    record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PHRASE_COLUMN_NAME, intraUserInformation.getPhrase());
                    record.setLongValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_TIMESTAMP_COLUMN_NAME, milliseconds);
                    record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_CITY_COLUMN_NAME, "");
                    record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_COUNTRY_COLUMN_NAME, "");


                    /**
                     * Persist profile image on a file
                     */
                    if(intraUserInformation.getProfileImage()!=null && intraUserInformation.getProfileImage().length > 0)
                        persistNewUserProfileImage(intraUserInformation.getPublicKey(), intraUserInformation.getProfileImage());


                    table.insertRecord(record);

                }

            }


        } catch (CantInsertRecordException e) {

            throw new CantAddIntraWalletCacheUserException(CantAddIntraWalletCacheUserException.DEFAULT_MESSAGE, e, "", "Cant create new intra user cache record, insert database problems.");

        }
        catch (Exception e) {
            throw new CantAddIntraWalletCacheUserException(CantAddIntraWalletCacheUserException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }



    }

    public List<IntraUserInformation> listIntraUserCache(int max, int offset) throws CantListIntraWalletCacheUserException {

        try {

            DatabaseTable table = this.database.getTable(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_TABLE_NAME);

            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));

            table.loadToMemory();

                List<DatabaseTableRecord> records = table.getRecords();

                List<IntraUserInformation> intraUserInformationList = new ArrayList<>();

                for (DatabaseTableRecord record : records) {
                    intraUserInformationList.add(buildIntraUserRecord(record));
                }
                return intraUserInformationList;

            } catch (CantLoadTableToMemoryException e) {

                throw new CantListIntraWalletCacheUserException(CantListIntraWalletCacheUserException.DEFAULT_MESSAGE,e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
            } catch(InvalidParameterException exception){

                throw new CantListIntraWalletCacheUserException(CantListIntraWalletCacheUserException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Exception invalidParameterException.","");
            }

    }


    private void deleteIntraUserCache() throws CantDeleteIntraWalletCacheUserException {

        try {

            DatabaseTable table = this.database.getTable(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_TABLE_NAME);


            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();


            for (DatabaseTableRecord record : records) {


                table.deleteRecord(record);
            }


        } catch (CantDeleteRecordException e) {

            throw new CantDeleteIntraWalletCacheUserException(CantDeleteIntraWalletCacheUserException.DEFAULT_MESSAGE,e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        } catch(CantLoadTableToMemoryException exception){

            throw new CantDeleteIntraWalletCacheUserException(CantDeleteIntraWalletCacheUserException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Exception invalidParameterException.","");
        }

    }


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
            throw new CantPersistProfileImageException(CantPersistProfileImageException.DEFAULT_MESSAGE,e, "Error persist file.", null);

        } catch (CantCreateFileException e) {

            throw new CantPersistProfileImageException(CantPersistProfileImageException.DEFAULT_MESSAGE,e, "Error creating file.", null);
        } catch (Exception e) {

            throw new CantPersistProfileImageException(CantPersistProfileImageException.DEFAULT_MESSAGE,FermatException.wrapException(e), "", "");
        }
    }


    private byte[] getIntraUserProfileImagePrivateKey(final String publicKey) throws CantGetIntraUserProfileImageException,FileNotFoundException {

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
            throw new CantGetIntraUserProfileImageException(CantGetIntraUserProfileImageException.DEFAULT_MESSAGE,e, "Error loaded file.", null);

        } catch (FileNotFoundException | CantCreateFileException e) {

            throw new FileNotFoundException(e, "", null);
        } catch (Exception e) {

            throw new CantGetIntraUserProfileImageException(CantGetIntraUserProfileImageException.DEFAULT_MESSAGE,FermatException.wrapException(e), "", "");
        }
    }

    private String buildProfileImageFileName(final String publicKey) {
        return PROFILE_IMAGE_FILE_NAME_PREFIX + "_" + publicKey;
    }


    private IntraUserInformation buildIntraUserRecord(DatabaseTableRecord record) throws InvalidParameterException {

        try
        {
            String intraUserAlias              = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_ALIAS_COLUMN_NAME);
            String intraUserPublicKey          = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PUBLIC_KEY_COLUMN_NAME);
            String intraUserPhrase             = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PHRASE_COLUMN_NAME);

            byte[] profileImage = null;

            try {
                profileImage = getIntraUserProfileImagePrivateKey(record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PUBLIC_KEY_COLUMN_NAME));
            } catch(FileNotFoundException e) {
                profileImage = new  byte[0];
            }

            return new IntraUserNetworkService(intraUserPublicKey, profileImage,intraUserAlias, intraUserPhrase);
        }
        catch(Exception e)
        {
            throw  new InvalidParameterException();
        }


    }

    public boolean existCacheRecord(String intraUserPublicKey) throws CantGetNotificationException {


        try {

            DatabaseTable table = this.database.getTable(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_TABLE_NAME);

            table.addStringFilter(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PUBLIC_KEY_COLUMN_NAME, intraUserPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();


            if (!records.isEmpty())
                return true;
            else
                return false;

        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetNotificationException( "",exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        }

    }
}
