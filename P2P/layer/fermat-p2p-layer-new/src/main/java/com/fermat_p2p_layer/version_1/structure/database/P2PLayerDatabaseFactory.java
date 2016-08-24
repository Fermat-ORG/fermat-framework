package com.fermat_p2p_layer.version_1.structure.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.*;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/08/16.
 */
public class P2PLayerDatabaseFactory {

    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public P2PLayerDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     *
     * @return Database
     *
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
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
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create P2P Layer Messages table.
             */
            table = databaseFactory.newTableFactory(ownerId, P2P_LAYER_MESSAGES_TABLE_NAME);

            table.addColumn(P2P_LAYER_PACKAGE_ID_COLUMN_NAME                 , DatabaseDataType.STRING       ,  36, Boolean.TRUE );
            table.addColumn(P2P_LAYER_CONTENT_COLUMN_NAME                    , DatabaseDataType.STRING       , 255, Boolean.FALSE);
            table.addColumn(P2P_LAYER_NETWORK_SERVICE_TYPE_COLUMN_NAME       , DatabaseDataType.STRING       ,  10, Boolean.FALSE);
            table.addColumn(P2P_LAYER_SENDER_PUBLIC_KEY_COLUMN_NAME          , DatabaseDataType.STRING       , 130, Boolean.FALSE);
            table.addColumn(P2P_LAYER_RECEIVER_PUBLIC_KEY_COLUMN_NAME        , DatabaseDataType.STRING       , 130, Boolean.FALSE);
            table.addColumn(P2P_LAYER_SHIPPING_TIMESTAMP_COLUMN_NAME         , DatabaseDataType.LONG_INTEGER , 100, Boolean.FALSE);
            table.addColumn(P2P_LAYER_DELIVERY_TIMESTAMP_COLUMN_NAME         , DatabaseDataType.LONG_INTEGER , 100, Boolean.FALSE);
            table.addColumn(P2P_LAYER_IS_BETWEEN_ACTORS_COLUMN_NAME          , DatabaseDataType.STRING       ,  10, Boolean.FALSE);
            table.addColumn(P2P_LAYER_FERMAT_MESSAGE_STATUS_COLUMN_NAME      , DatabaseDataType.STRING       , 100, Boolean.FALSE);
            table.addColumn(P2P_LAYER_SIGNATURE_COLUMN_NAME                  , DatabaseDataType.STRING       , 100, Boolean.FALSE);
            table.addColumn(P2P_LAYER_FAIL_COUNT_COLUMN_NAME                 , DatabaseDataType.INTEGER      ,  10, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }


                /**
                 * Create P2P Layer events table.
                 */
                table = databaseFactory.newTableFactory(ownerId, P2P_LAYER_EVENT_TABLE_NAME);

                table.addColumn(P2P_LAYER_EVENT_ID_COLUMN_NAME                 , DatabaseDataType.STRING       ,  36, Boolean.TRUE );
                table.addColumn(P2P_LAYER_EVENT_NS_OWNER_COLUMN_NAME                    , DatabaseDataType.STRING       , 10, Boolean.FALSE);

                try {
                    //Create the table
                    databaseFactory.createTable(ownerId, table);
                } catch (CantCreateTableException cantCreateTableException) {
                    throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
                }

        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }

}


