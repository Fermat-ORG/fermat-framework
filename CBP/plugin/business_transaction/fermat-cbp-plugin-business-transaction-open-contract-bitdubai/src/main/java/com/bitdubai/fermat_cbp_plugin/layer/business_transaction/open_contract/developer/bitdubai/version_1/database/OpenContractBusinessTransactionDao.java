package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.exceptions.CantInitializeOpenContractBusinessTransactionDatabaseException;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 28/11/15.
 */
public class OpenContractBusinessTransactionDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId            ;

    private Database database;

    public OpenContractBusinessTransactionDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                                  final UUID                 pluginId           ,
                                                  final Database database) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.database = database;
    }

    public void initialize() throws CantInitializeOpenContractBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    OpenContractBusinessTransactionDatabaseConstants.DATABASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                OpenContractBusinessTransactionDatabaseFactory databaseFactory = new OpenContractBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        OpenContractBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {

                throw new CantInitializeOpenContractBusinessTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {

                throw new CantInitializeOpenContractBusinessTransactionDatabaseException(CantInitializeOpenContractBusinessTransactionDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {

            throw new CantInitializeOpenContractBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {

            throw new CantInitializeOpenContractBusinessTransactionDatabaseException(CantInitializeOpenContractBusinessTransactionDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /**
     * Returns the Database
     *
     * @return Database
     */
    Database getDataBase() {
        return database;
    }

    /**
     * Returns the Open Contract DatabaseTable
     *
     * @return DatabaseTable
     */
    DatabaseTable getDatabaseContractTable() {
        return getDataBase().getTable(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TABLE_NAME);
    }

}
