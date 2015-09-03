package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;

import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.dmp_actor.Actor;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetDataBaseTool;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.UserRegistry;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantCreateExtraUserRegistry;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantGetExtraUserRegistry;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantInitializeExtraUserRegistryException;

import java.util.UUID;

/**
 * Created by ciencias on 3/18/15.
 * Modified by natalia
 */
public class ExtraUserRegistry implements DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, DealsWithPluginIdentity, UserRegistry {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    UUID pluginId;


    /**
     * UserRegistry Interface member variables.
     */


    private Database database;

    private final String NO_IMAGE = "NoImageProvided";

    /**
     * DealsWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public void initialize() throws CantInitializeExtraUserRegistryException {
        //TODO Manuel este metodo tiene que gestionar excepciones genericas
        /**
         * Modified by Manuel Perez on 27/97/2015
         * */
        /**
         * I will try to open the users' database..
         */
        try {

            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, "ExtraUser");
        } catch (DatabaseNotFoundException databaseNotFoundException) {

            ExtraUserDatabaseFactory databaseFactory = new ExtraUserDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);
            databaseFactory.setErrorManager(this.errorManager);

            try {

                this.database = databaseFactory.createDatabase(pluginId);

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                String message = CantInitializeExtraUserRegistryException.DEFAULT_MESSAGE;
                FermatException cause = cantCreateDatabaseException.getCause();
                String context = "DataBase Factory: " + cantCreateDatabaseException.getContext();
                String possibleReason = "The exception occurred when calling  'databaseFactory.createDatabase()': " + cantCreateDatabaseException.getPossibleReason();

                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeExtraUserRegistryException(message, cause, context, possibleReason);
            }catch(Exception exception){

                throw new CantInitializeExtraUserRegistryException(CantInitializeExtraUserRegistryException.DEFAULT_MESSAGE,FermatException.wrapException(exception),null,null);

            }
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            String message = CantInitializeExtraUserRegistryException.DEFAULT_MESSAGE;
            FermatException cause = cantOpenDatabaseException.getCause();
            String context = "Create Database:" + cantOpenDatabaseException.getContext();
            String possibleReason = "The exception occurred while trying to open the database of users 'this.database = this.platformDatabaseSystem.openDatabase (\"ExtraUser\")': " + cantOpenDatabaseException.getPossibleReason();

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            /*
            Modified by Francisco Arce
            */
            throw new CantInitializeExtraUserRegistryException(message, cause, context, possibleReason);
        }catch(Exception exception){

            throw new CantInitializeExtraUserRegistryException(CantInitializeExtraUserRegistryException.DEFAULT_MESSAGE,FermatException.wrapException(exception),null,null);

        }

    }

    /**
     * UserRegistry interface implementation.
     */


    /**
     * <p>Create a new Extra User, insert new table record.
     *
     * @param userName
     * @return Object user
     * @throws CantCreateExtraUserRegistry
     */
    @Override
    public Actor createUser(String userName) throws CantCreateExtraUserRegistry {

        /**
         *  I create new ExtraUser instance
         */

        Actor actor = new ExtraUser();
        //Modified by Manuel Pérez on 26/07/2015
        DatabaseTable extrauserTable=null;
        DatabaseTableRecord extrauserRecord=null;
        try{

            String actorPublicKey = UUID.randomUUID().toString();

            actor.setActorPublicKey(actorPublicKey);
            actor.setName(userName);
            actor.setPhoto(null);
            /**
             * Here I create a new Extra User record .
             */
            long unixTime = System.currentTimeMillis() / 1000L;

            extrauserTable = database.getTable(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME);
            extrauserRecord = extrauserTable.getEmptyRecord();

            extrauserRecord.setStringValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_ID_COLUMN_NAME, actorPublicKey);
            extrauserRecord.setStringValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME_COLUMN_NAME, userName);
            extrauserRecord.setStringValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_PHOTO_FILE_NAME_COLUMN, NO_IMAGE);
            extrauserRecord.setLongValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_TIME_STAMP_COLUMN_NAME, unixTime);
            extrauserTable.insertRecord(extrauserRecord);

        }catch (CantInsertRecordException cantInsertRecord) {
            /**
             * I can not solve this situation.
             */
        /*Francisco Arce
        Exception in the context Fermat Context
        *
        * */
            String message = CantCreateExtraUserRegistry.DEFAULT_MESSAGE;
            FermatException cause = cantInsertRecord.getCause();
            String context = "Extra User Record: " + cantInsertRecord.getContext();
            String possibleReason = "The exception occurred when recording the Extra User extrauserTable.insertRecord(extrauserRecord): " + cantInsertRecord.getPossibleReason();
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantInsertRecord);
            /*
          modified by Francisco Arce
            */
            throw new CantCreateExtraUserRegistry(message, cause, context, possibleReason);
        }catch(Exception exception){

            FermatException e = new CantGetDataBaseTool(CantGetDataBaseTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "PluginID: "+pluginId ,"Check the cause");
            this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);


        }

        return actor;
    }

    @Override
    public Actor createUser(String userName, byte[] photo) throws CantCreateExtraUserRegistry {

        /**
         *  I create new ExtraUser instance
         */

        Actor actor = new ExtraUser();
        //Modified by Manuel Pérez on 26/07/2015
        DatabaseTable extrauserTable=null;
        DatabaseTableRecord extrauserRecord=null;
        try{

            String actorPublicKey = UUID.randomUUID().toString();

            actor.setActorPublicKey(actorPublicKey);
            actor.setName(userName);
            actor.setPhoto(photo);
            /**
             * Here I create a new Extra User record .
             */
            long unixTime = System.currentTimeMillis() / 1000L;

            extrauserTable = database.getTable(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME);
            extrauserRecord = extrauserTable.getEmptyRecord();

            extrauserRecord.setStringValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_ID_COLUMN_NAME, actorPublicKey);
            extrauserRecord.setStringValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME_COLUMN_NAME, userName);
            extrauserRecord.setStringValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_PHOTO_FILE_NAME_COLUMN, actorPublicKey);
            extrauserRecord.setLongValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_TIME_STAMP_COLUMN_NAME, unixTime);
            extrauserTable.insertRecord(extrauserRecord);


            try {
                pluginFileSystem.getBinaryFile(pluginId,DeviceDirectory.LOCAL_USERS.getName(), actorPublicKey, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
            } catch (CantCreateFileException cantPersistFileException) {
                throw new CantCreateExtraUserRegistry("CAN'T SAVE USER PHOTO", cantPersistFileException, "Error persist image file " + actorPublicKey, "");
            } catch (FileNotFoundException e) {

                try {
                    PluginBinaryFile imageFile = pluginFileSystem.createBinaryFile(pluginId, DeviceDirectory.LOCAL_USERS.getName() , actorPublicKey, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
                    imageFile.setContent(photo);
                    try {
                        imageFile.persistToMedia();
                    } catch (CantPersistFileException cantPersistFileException) {
                        throw new CantCreateExtraUserRegistry("CAN'T SAVE USER PHOTO", cantPersistFileException, "Error persist image file " + actorPublicKey, "");

                    }
                } catch (CantCreateFileException cantPersistFileException) {
                    throw new CantCreateExtraUserRegistry("CAN'T SAVE USER PHOTO", cantPersistFileException, "Error persist image file " + actorPublicKey, "");
                }
            }

        }catch (CantInsertRecordException cantInsertRecord) {
            /**
             * I can not solve this situation.
             */
        /*Francisco Arce
        Exception in the context Fermat Context
        *
        * */
            String message = CantCreateExtraUserRegistry.DEFAULT_MESSAGE;
            FermatException cause = cantInsertRecord.getCause();
            String context = "Extra User Record: " + cantInsertRecord.getContext();
            String possibleReason = "The exception occurred when recording the Extra User extrauserTable.insertRecord(extrauserRecord): " + cantInsertRecord.getPossibleReason();
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantInsertRecord);
            /*
          modified by Francisco Arce
            */
            throw new CantCreateExtraUserRegistry(message, cause, context, possibleReason);
        }catch (CantCreateExtraUserRegistry e){
            throw e;
        }catch(Exception exception){
            FermatException e = new CantCreateExtraUserRegistry("UNEXPECTED EXCEPTION", FermatException.wrapException(exception), "PluginID: "+pluginId ,"Check the cause");
            this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);


        }

        return actor;
    }

    /**
     * <p>Return a specific user, looking for registered user id.
     *
     * @param actorPublicKey
     * @return Object user
     * @throws CantGetExtraUserRegistry
     */
    @Override
    public Actor getUser(String actorPublicKey) throws CantGetExtraUserRegistry {
         /**
         * Reads the user data table , in this case only the name , creates an instance and returns
         */
        DatabaseTable table;
        //Modified by Manuel Pérez on 26/07/2015
        Actor actor = new ExtraUser();
        try {
        /**
         *  I will load the information of table into a memory structure, filter by user id .
         */
            table = this.database.getTable(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME);
            table.setStringFilter(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_ID_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            /**
             * Will go through the records getting each extra user.
             */

            actor.setActorPublicKey(actorPublicKey);
            for (DatabaseTableRecord record : table.getRecords()) {
                actor.setName(record.getStringValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME_COLUMN_NAME));
                if(record.getStringValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_PHOTO_FILE_NAME_COLUMN).equals(NO_IMAGE))
                    actor.setPhoto(null);
                else {
                    PluginBinaryFile imageFile;

                    String path = DeviceDirectory.LOCAL_USERS.getName();
                    imageFile = pluginFileSystem.getBinaryFile(pluginId, path, actorPublicKey, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
                    actor.setPhoto(imageFile.getContent());
                }
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            /*Francisco Arce
            Exception in the context Fermat Context
            *
            * */
            String message = CantGetExtraUserRegistry.DEFAULT_MESSAGE;
            FermatException cause = cantLoadTableToMemory.getCause();
            String context = "table Memory: " + cantLoadTableToMemory.getContext();
            String possibleReason = "The exception occurred when calling table.loadToMemory (): " + cantLoadTableToMemory.getPossibleReason();

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantGetExtraUserRegistry(message, cause, context, possibleReason);
        } catch (FileNotFoundException | CantCreateFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetExtraUserRegistry(CantGetExtraUserRegistry.DEFAULT_MESSAGE, e, null, null);
        }catch(Exception exception) {

            throw new CantGetExtraUserRegistry(CantGetExtraUserRegistry.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }


        return actor;
    }

    @Override
    public byte[] getPhoto(String actorPublicKey) throws CantGetExtraUserRegistry {
        DatabaseTable table;
        try {
            table = this.database.getTable(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME);
            table.setStringFilter(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_ID_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            String fileName =  table.getRecords().get(0).getStringValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_PHOTO_FILE_NAME_COLUMN);
            if(fileName.equals(NO_IMAGE))
                return null;

            PluginBinaryFile imageFile;

            String path = DeviceDirectory.LOCAL_USERS.getName();
            imageFile = pluginFileSystem.getBinaryFile(pluginId, path, actorPublicKey, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
            return imageFile.getContent();
        } catch (CantLoadTableToMemoryException | FileNotFoundException | CantCreateFileException e) {
            throw new CantGetExtraUserRegistry("",e,"","");
        } catch (Exception e) {
            throw new CantGetExtraUserRegistry("",FermatException.wrapException(e),"","");
        }
    }

    @Override
    public void setPhoto(String actorPublicKey, byte[] photo) throws CantGetExtraUserRegistry {
        try {
            /**
             *  I will load the information of table into a memory structure, filter by user id .
             */
            DatabaseTable table = this.database.getTable(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME);
            table.setStringFilter(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_ID_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            /**
             * Will go through the records getting each extra user.
             */
            String oldPhotoFileName;
            for (DatabaseTableRecord record : table.getRecords()) {
                oldPhotoFileName = record.getStringValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_PHOTO_FILE_NAME_COLUMN);
                record.setStringValue(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_PHOTO_FILE_NAME_COLUMN, actorPublicKey);
                table.updateRecord(record);
                if (oldPhotoFileName.equals(NO_IMAGE)) {

                    try {
                        pluginFileSystem.getBinaryFile(pluginId, DeviceDirectory.LOCAL_USERS.getName(), actorPublicKey, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
                    } catch (CantCreateFileException cantPersistFileException) {
                        throw new CantGetExtraUserRegistry("CAN'T SAVE USER PHOTO", cantPersistFileException, "Error persist image file " + actorPublicKey, "");
                    } catch (FileNotFoundException e) {
                        try {
                            PluginBinaryFile imageFile = pluginFileSystem.createBinaryFile(pluginId, DeviceDirectory.LOCAL_USERS.getName(), actorPublicKey, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
                            imageFile.setContent(photo);
                            try {
                                imageFile.persistToMedia();
                            } catch (CantPersistFileException cantPersistFileException) {
                                throw new CantGetExtraUserRegistry("CAN'T SAVE USER PHOTO", cantPersistFileException, "Error persist image file " + actorPublicKey, "");
                            }
                        } catch (CantCreateFileException cantPersistFileException) {
                            throw new CantGetExtraUserRegistry("CAN'T SAVE USER PHOTO", cantPersistFileException, "Error persist image file " + actorPublicKey, "");
                        }
                    }
                } else {
                    try {
                        PluginBinaryFile imageFile = this.pluginFileSystem.getBinaryFile(pluginId, DeviceDirectory.LOCAL_USERS.getName(), actorPublicKey, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
                        imageFile.setContent(photo);
                        imageFile.persistToMedia();
                    } catch (CantPersistFileException | CantCreateFileException | FileNotFoundException e) {
                        throw new CantGetExtraUserRegistry("CAN'T SAVE USER PHOTO", e, "Error persist image file " + actorPublicKey, "");
                    }
                }
            }


        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            /*Francisco Arce
            Exception in the context Fermat Context
            *
            * */
            String message = CantGetExtraUserRegistry.DEFAULT_MESSAGE;
            FermatException cause = cantLoadTableToMemory.getCause();
            String context = "table Memory: " + cantLoadTableToMemory.getContext();
            String possibleReason = "The exception occurred when calling table.loadToMemory (): " + cantLoadTableToMemory.getPossibleReason();

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantGetExtraUserRegistry(message, cause, context, possibleReason);
        }catch (CantGetExtraUserRegistry e){
            throw e;
        } catch(Exception exception) {
            throw new CantGetExtraUserRegistry(CantGetExtraUserRegistry.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }
}
