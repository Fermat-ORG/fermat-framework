package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.PhotoType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraActorNetworkServiceDataBaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraActorNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantAddIntraWalletCacheUserException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantDeleteIntraWalletCacheUserException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeNetworkIntraUserDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantListIntraWalletCacheUserException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 18/12/15.
 */
public class IntraActorNetworkServiceDao {

    private Database database;

    public IntraActorNetworkServiceDao(Database database) {

        this.database = database;
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
                DatabaseTableRecord record = table.getEmptyRecord();


                record.setUUIDValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_ID_COLUMN_NAME, UUID.randomUUID());

                record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PUBLIC_KEY_COLUMN_NAME, intraUserInformation.getPublicKey());
                record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_ALIAS_COLUMN_NAME, intraUserInformation.getName());
                if(intraUserInformation.getProfileImage() != null && intraUserInformation.getProfileImage() .length > 0)
                    record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PROFILE_IMAGE_COLUMN_NAME, intraUserInformation.getProfileImage().toString());
                else
                    record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PROFILE_IMAGE_COLUMN_NAME, "");

                record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PHRASE_COLUMN_NAME, intraUserInformation.getPhrase());
                record.setLongValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_TIMESTAMP_COLUMN_NAME, milliseconds);
                record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_CITY_COLUMN_NAME, "");
                record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_COUNTRY_COLUMN_NAME, "");

                table.insertRecord(record);
            }


        } catch (CantInsertRecordException e) {

            throw new CantAddIntraWalletCacheUserException(CantAddIntraWalletCacheUserException.DEFAULT_MESSAGE, e, "", "Cant create new intra user cache record, insert database problems.");

        } catch (CantDeleteIntraWalletCacheUserException e) {

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


    private IntraUserInformation buildIntraUserRecord(DatabaseTableRecord record) throws InvalidParameterException {

        String intraUserAlias              = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_ALIAS_COLUMN_NAME);
        String intraUserProfileImage       = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PROFILE_IMAGE_COLUMN_NAME);
        String intraUserPublicKey          = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PUBLIC_KEY_COLUMN_NAME);
        String intraUserPhrase             = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PHRASE_COLUMN_NAME);
        PhotoType photoType                = PhotoType.getByCode(record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PHOTO_TYPE_COLUMN_NAME));
        byte[] profileImage = null;

        if (intraUserProfileImage.length() > 0)
            profileImage = intraUserProfileImage.getBytes();

        return new IntraUserNetworkService(intraUserPublicKey, profileImage,intraUserAlias, intraUserPhrase,photoType);
    }

}
