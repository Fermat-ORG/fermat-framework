package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by natalia on 12/08/15.
 */
public class IntraWalletUserActorDatabaseFactory {


/**
 * DealsWithPluginDatabaseSystem Interface member variables.
 */
private PluginDatabaseSystem pluginDatabaseSystem;

        /**
         * Constructor with parameters to instantiate class
         * .
         *
         * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
         */
        public IntraWalletUserActorDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        /**
         * Create the database
         *
         * @param ownerId      the owner id
         * @param databaseName the database name
         * @return Database
         * @throws CantCreateDatabaseException
         */
        public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
            Database database;

            /**
             * I will create the database where I am going to store the information of this wallet.
             */
            try {
                database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);


            /**
             * Next, I will add the needed tables.
             */

                DatabaseTableFactory table;
                DatabaseFactory databaseFactory = database.getDatabaseFactory();

                /**
                 * Create Intra User table.
                 */
                table = databaseFactory.newTableFactory(ownerId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME);

                table.addColumn(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
                table.addColumn(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
                table.addColumn(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
                table.addColumn(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_CONTACT_STATE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
                table.addColumn(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_REGISTRATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
                table.addColumn(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_MODIFIED_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);

                table.addIndex(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_FIRST_KEY_COLUMN);


                    //Create the table
                    databaseFactory.createTable(ownerId, table);

                return database;

            } catch (CantCreateTableException cantCreateTableException) {

                    throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");

            } catch (InvalidOwnerIdException invalidOwnerId) {
                /**
                 * This shouldn't happen here because I was the one who gave the owner id to the database file system,
                 * but anyway, if this happens, I can not continue.
                 */
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
            }
            catch (CantCreateDatabaseException cantCreateDatabaseException) {
                /**
                 * I can not handle this situation.
                 */
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
            }
            catch (Exception e) {
                /**
                 * I can not handle this situation.
                 */
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
            }
    }

        }


