package com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.estructure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantInitializeExtraUserRegistryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_pip_api.layer.pip_identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_pip_api.layer.pip_identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.database.IdentityTranslatorDatabaseConstants;
import com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.database.IdentityTranslatorDatabaseFactory;

import java.util.UUID;

/**
 * Created by natalia on 31/07/15.
 */
public class IdentityTranslatorDao{


    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    private Database database;

    UUID pluginId;

    /**
     * Constructor
     *
     */

    public IdentityTranslatorDao(ErrorManager errorManager,PluginDatabaseSystem pluginDatabaseSystem,UUID pluginId){
        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this. pluginId = pluginId;
    }


    // Private instance methods declarations.

    public void initialize() throws CantInitializeExtraUserRegistryException {
           /**
         * I will try to open the translator' database..
         */
        try {

            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, "ExtraUser");
        } catch (DatabaseNotFoundException databaseNotFoundException) {

            IdentityTranslatorDatabaseFactory databaseFactory = new IdentityTranslatorDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);
            databaseFactory.setErrorManager(this.errorManager);

            try {

                this.database = databaseFactory.createDatabase(pluginId);

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                String message = CantInitializeExtraUserRegistryException.DEFAULT_MESSAGE;
                FermatException cause = cantCreateDatabaseException.getCause();
                String context = "DataBase Factory: " + cantCreateDatabaseException.getContext();
                String possibleReason = "The exception occurred when calling  'databaseFactory.createDatabase()': " + cantCreateDatabaseException.getPossibleReason();

                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TRANSLATOR_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeExtraUserRegistryException(message, cause, context, possibleReason);
            }catch(Exception exception){

                throw new CantInitializeExtraUserRegistryException(CantInitializeExtraUserRegistryException.DEFAULT_MESSAGE,FermatException.wrapException(exception),null,null);

            }
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            String message = CantInitializeExtraUserRegistryException.DEFAULT_MESSAGE;
            FermatException cause = cantOpenDatabaseException.getCause();
            String context = "Create Database:" + cantOpenDatabaseException.getContext();
            String possibleReason = "The exception occurred while trying to open the database of users 'this.database = this.platformDatabaseSystem.openDatabase (\"ExtraUser\")': " + cantOpenDatabaseException.getPossibleReason();

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TRANSLATOR_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            /*
            Modified by Francisco Arce
            */
            throw new CantInitializeExtraUserRegistryException(message, cause, context, possibleReason);
        }catch(Exception exception){

            throw new CantInitializeExtraUserRegistryException(CantInitializeExtraUserRegistryException.DEFAULT_MESSAGE,FermatException.wrapException(exception),null,null);

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
        table = this.database.getTable (IdentityTranslatorDatabaseConstants.TRANSLATOR_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists, table not \" + DeveloperIdentityDatabaseConstants.DEVELOPER_TABLE_NAME + \" found.", "Plugin Identity", "Cant check if alias exists, table not \" + DeveloperIdentityDatabaseConstants.DEVELOPER_TABLE_NAME + \" found.");
            }

             table.setStringFilter(IdentityTranslatorDatabaseConstants.TRANSLATOR_DEVELOPER_ALIAS_COLUMN_NAME, alias, DatabaseFilterType.EQUAL);
            table.loadToMemory();

              return table.getRecords ().size () > 0;


        } catch (CantLoadTableToMemoryException em) {
             throw new CantCreateNewDeveloperException (em.getMessage(), em, "Plugin Identity", "Cant load " + IdentityTranslatorDatabaseConstants.TRANSLATOR_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException (e.getMessage(), e, "Plugin Identity", "Cant check if alias exists, unknown failure.");
        }
    }


}
