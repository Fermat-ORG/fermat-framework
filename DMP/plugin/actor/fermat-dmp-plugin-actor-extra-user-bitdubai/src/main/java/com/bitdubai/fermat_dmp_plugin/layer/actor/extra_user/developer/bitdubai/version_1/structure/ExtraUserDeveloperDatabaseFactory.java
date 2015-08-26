package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetDataBaseTool;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantInitializeExtraUserRegistryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDeveloperDatabaseFactory</code> have
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class ExtraUserDeveloperDatabaseFactory implements DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    UUID pluginId;

    Database database;

    /**
     * Constructor
     *
     * @param errorManager
     * @param pluginDatabaseSystem
     */
    public ExtraUserDeveloperDatabaseFactory(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }




    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeExtraUserRegistryException
     */
    public void initializeDatabase() throws CantInitializeExtraUserRegistryException {
        //TODO Manuel, este metodo esta pendiente por la implementacion de las excepciones genericas
        /**
         * I will try to open the users' database..
         */
        try {

            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, "ExtraUser");
            this.database.closeDatabase();
            /**
             * Modified by Manuel Perez on 27/07/2015
             * */
        } catch (DatabaseNotFoundException databaseNotFoundException) {

            ExtraUserDatabaseFactory databaseFactory = new ExtraUserDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);
            databaseFactory.setErrorManager(this.errorManager);

            /**
             * I will create the database where I am going to store the information of this wallet.
             */

            try {

                this.database = databaseFactory.createDatabase(pluginId);

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);

                /**
                 * The database cannot be created. I can not handle this situation.
                 */
                throw new CantInitializeExtraUserRegistryException(CantInitializeExtraUserRegistryException.DEFAULT_MESSAGE, cantCreateDatabaseException);
            } catch(Exception exception){

                throw new CantInitializeExtraUserRegistryException(CantInitializeExtraUserRegistryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);

            }
        } catch(CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);

            /**
             * The database exists but cannot be open. I can not handle this situation.
             */

            throw new CantInitializeExtraUserRegistryException(CantInitializeExtraUserRegistryException.DEFAULT_MESSAGE, cantOpenDatabaseException);
        }catch(Exception exception){

            throw new CantInitializeExtraUserRegistryException(CantInitializeExtraUserRegistryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);

        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {

        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<>();
        /**
         * Modified by Manuel Perez on 26/07/2015
         * I wrapped this method line inside a try-catch struture, I need to catch any
         * generic java exception
         */
        try{

            databases.add(developerObjectFactory.getNewDeveloperDatabase("Extra User", "ExtraUser"));

        }catch(Exception exception){

            FermatException e = new CantGetDataBaseTool(CantGetDataBaseTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "getDatabaseList: "+developerObjectFactory ,"Check the cause");
            this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);
            return databases;

        }

        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * extraUserTable columns
         */
        List<String> extraUserTableColumns = new ArrayList<>();
        extraUserTableColumns.add(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_ID_COLUMN_NAME);
        extraUserTableColumns.add(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME_COLUMN_NAME);
        extraUserTableColumns.add(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_TIME_STAMP_COLUMN_NAME);
        extraUserTableColumns.add(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_PHOTO_FILE_NAME_COLUMN);


        /**
         * Modified by Manuel Perez on 26/07/2015
         * I wrapped this method line inside a try-catch struture, I need to catch any
         * generic java exception
         */
        try{

            /**
             * extraUser table
             */
            DeveloperDatabaseTable extraUserTable = developerObjectFactory.getNewDeveloperDatabaseTable(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME, extraUserTableColumns);
            tables.add(extraUserTable);

        }catch(Exception exception){

            FermatException e = new CantGetDataBaseTool(CantGetDataBaseTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "getDatabaseTableList: "+developerObjectFactory ,"Check the cause");
            this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);
            return tables;

        }

        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<>();

        /**
         * I load the passed table name from the SQLite database.
         */
        //Modified by Manuel Pérez 26/07/2015
        DatabaseTable selectedTable=null;
        try{

            selectedTable = database.getTable(developerDatabaseTable.getName());

            selectedTable.loadToMemory();
            database.closeDatabase();
            List<DatabaseTableRecord> records = selectedTable.getRecords();
            for (DatabaseTableRecord row : records) {
                /**
                 * for each row in the table list
                 */
                List<String> developerRow = new ArrayList<String>();
                for (DatabaseRecord field : row.getValues()) {
                    /**
                     * I get each row and save them into a List<String>
                     */
                    developerRow.add(field.getValue());
                }
                /**
                 * I create the Developer Database record
                 */
                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadTableToMemory);
            database.closeDatabase();
            /**
             * if there was an error, I will returned an empty list.
             */
            return returnedRecords;
        } catch (Exception exception) {
            database.closeDatabase();
            FermatException e = new CantGetDataBaseTool(CantGetDataBaseTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "getDatabaseTableContent: "+developerObjectFactory ,"Check the cause");
            this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);

        }

        /**
         * return the list of DeveloperRecords for the passed table.
         */
        return returnedRecords;
    }

    /**
     * DealsWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPlatformDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
