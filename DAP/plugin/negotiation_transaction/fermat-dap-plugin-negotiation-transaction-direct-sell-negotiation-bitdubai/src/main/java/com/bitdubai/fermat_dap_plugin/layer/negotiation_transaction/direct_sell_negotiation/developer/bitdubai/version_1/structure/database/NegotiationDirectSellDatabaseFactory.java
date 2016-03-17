package com.bitdubai.fermat_dap_plugin.layer.negotiation_transaction.direct_sell_negotiation.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15. - Jose Briceño (josebricenor@gmail.com) 16/03/16.
 */
public class NegotiationDirectSellDatabaseFactory implements DealsWithPluginDatabaseSystem {

    //VARIABLE DECLARATION
    private PluginDatabaseSystem pluginDatabaseSystem;

    //CONSTRUCTORS
    public NegotiationDirectSellDatabaseFactory() {
    }

    public NegotiationDirectSellDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) throws CantSetObjectException {
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
    }


    //PUBLIC METHODS
    public Database createDatabase(UUID ownerId) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, NegotiationDirectSellDatabaseConstants.DIRECT_SELL_NEGOTIATION_DATABASE);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Events recorded database table.
             */
            DatabaseTableFactory eventsRecorderTable = databaseFactory.newTableFactory(ownerId, NegotiationDirectSellDatabaseConstants.DIRECT_SELL_NEGOTIATION_EVENTS_RECORDED_TABLE_NAME);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, eventsRecorderTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

        } catch (InvalidOwnerIdException e) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, e, "UUID : " + ownerId, "Invalid Owner. Cannot create the table...");
        }
        return database;
    }

    public boolean isDatabaseCreated(UUID ownerId) {
        try {
            this.pluginDatabaseSystem.openDatabase(ownerId, NegotiationDirectSellDatabaseConstants.DIRECT_SELL_NEGOTIATION_DATABASE);
        } catch (CantOpenDatabaseException e) {
            return false;
        } catch (DatabaseNotFoundException e) {
            return false;
        }
        return true;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    //INNER CLASSES
}
