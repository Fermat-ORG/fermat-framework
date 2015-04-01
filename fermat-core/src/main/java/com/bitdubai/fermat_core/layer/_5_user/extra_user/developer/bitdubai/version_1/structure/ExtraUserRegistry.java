package com.bitdubai.fermat_core.layer._5_user.extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._2_os.database_system.Database;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPlatformDatabaseSystem;

import com.bitdubai.fermat_api.layer._2_os.database_system.PlatformDatabaseSystem;

import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantInsertRecord;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantLoadTableToMemory;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer._5_user.User;

import com.bitdubai.fermat_api.layer._5_user.extra_user.UserRegistry;
import com.bitdubai.fermat_api.layer._5_user.extra_user.exceptions.CantCreateExtraUserRegistry;
import com.bitdubai.fermat_api.layer._5_user.extra_user.exceptions.CantGetExtraUserRegistry;
import com.bitdubai.fermat_core.layer._8_crypto.address_book.developer.bitdubai.version_1.exceptions.CantInitializeAddresBookException;



import java.util.UUID;

/**
 * Created by ciencias on 3/18/15.
 */
public class ExtraUserRegistry implements DealsWithPlatformDatabaseSystem,UserRegistry {

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PlatformDatabaseSystem platformDatabaseSystem;


    /**
     * UserRegistry Interface member variables.
     */


    private Database database;







    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */

    @Override
    public void setPlatformDatabaseSystem(PlatformDatabaseSystem platformDatabaseSystem) {
        this.platformDatabaseSystem = platformDatabaseSystem;
    }


    /**
     * UserRegistry interface implementation.
     */

    public void initialize() throws CantInitializeAddresBookException {

        /**
         * I will try to open the wallets' database..
         */
        try {
            this.database = this.platformDatabaseSystem.openDatabase("ExtraUser");
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {

            ExtraUserDatabaseFactory databaseFactory = new ExtraUserDatabaseFactory();
            databaseFactory.setPlatformDatabaseSystem(this.platformDatabaseSystem);

            /**
             * I will create the database where I am going to store the information of this wallet.
             */
            try {

                this.database =  databaseFactory.createDatabase();

            }
            catch (CantCreateDatabaseException cantCreateDatabaseException){

                /**
                 * The database cannot be created. I can not handle this situation.
                 */
                System.err.println("CantCreateDatabaseException: " + cantCreateDatabaseException.getMessage());
                cantCreateDatabaseException.printStackTrace();
                throw new CantInitializeAddresBookException();
            }
        }
        catch (CantOpenDatabaseException cantOpenDatabaseException){

            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            System.err.println("CantOpenDatabaseException: " + cantOpenDatabaseException.getMessage());
            cantOpenDatabaseException.printStackTrace();
            throw new CantInitializeAddresBookException();
        }

    }

    @Override
    public User createUser() throws CantCreateExtraUserRegistry {

        /**
         *  I create new ExtraUser instance
         */

        User user = new ExtraUser();

        UUID userId = UUID.randomUUID();

        user.setId(userId);
        user.setName("");
        /**
         * Here I create a new Extra User record .
         */
        long unixTime = System.currentTimeMillis() / 1000L;

        DatabaseTable extrauserTable = database.getTable(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME);
        DatabaseTableRecord extrauserRecord = extrauserTable.getEmptyRecord();

        extrauserRecord.setUUIDValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_ID_COLUMN_NAME , userId);
        extrauserRecord.setStringValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME_COLUMN_NAME, "");
        extrauserRecord.setlongValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_TIME_STAMP_COLUMN_NAME, unixTime);


        try{
            extrauserTable.insertRecord(extrauserRecord);
        }catch(CantInsertRecord e)
        {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantInsertRecord: " + e.getMessage());
            e.printStackTrace();
            throw new CantCreateExtraUserRegistry();
        }
        
        return user;
    }

    @Override
    public User getUser(UUID userId) throws CantGetExtraUserRegistry {


        /**
         * Reads the user data table , in this case only the name , creates an instance and returns
         */

        DatabaseTable table;

        /**
         *  I will load the information of table into a memory structure, filter by user id .
         */
        table = this.database.getTable(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME);
        table.setUUIDFilter(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_ID_COLUMN_NAME, userId, DatabaseFilterType.EQUAL);
        try {
            table.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantLoadTableToMemory: " + cantLoadTableToMemory.getMessage());
            cantLoadTableToMemory.printStackTrace();
            throw new CantGetExtraUserRegistry();
        }


        /**
         * Will go through the records getting each extra user.
         */

        UUID user_id ;
        User user = new ExtraUser();
        user.setId(userId);
        for (DatabaseTableRecord record : table.getRecords()) {
            user.setName(record.getStringValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME_COLUMN_NAME));

            }


        return user;
    }
}
