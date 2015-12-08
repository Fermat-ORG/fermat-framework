package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.exceptions.CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class CustomerOnlinePaymentBusinessTransactionDao {
    
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

        public CustomerOnlinePaymentBusinessTransactionDao(
                final PluginDatabaseSystem pluginDatabaseSystem,
                final UUID pluginId,
                final Database database) {

            this.pluginDatabaseSystem = pluginDatabaseSystem;
            this.pluginId             = pluginId            ;
            this.database             = database            ;
        }

        public void initialize() throws CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException {
            try {

                database = this.pluginDatabaseSystem.openDatabase(
                        this.pluginId,
                        CustomerOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (DatabaseNotFoundException e) {

                try {

                    CustomerOnlinePaymentBusinessTransactionDatabaseFactory databaseFactory =
                            new CustomerOnlinePaymentBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                    database = databaseFactory.createDatabase(
                            pluginId,
                            CustomerOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME
                    );

                } catch (CantCreateDatabaseException f) {

                    throw new CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException(
                            CantCreateDatabaseException.DEFAULT_MESSAGE,
                            f,
                            "",
                            "There is a problem and i cannot create the database.");
                } catch (Exception z) {

                    throw new CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException(
                            CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                            z,
                            "",
                            "Generic Exception.");
                }

            } catch (CantOpenDatabaseException e) {

                throw new CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException(
                        CantOpenDatabaseException.DEFAULT_MESSAGE,
                        e,
                        "",
                        "Exception not handled by the plugin, there is a problem and i cannot open the database.");
            } catch (Exception e) {

                throw new CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException(
                        CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        e,
                        "",
                        "Generic Exception.");
            }
        }

        /**
         * Returns the Database
         *
         * @return Database
         */
        private Database getDataBase() {
            return database;
        }

        /**
         * Returns the Open Contract DatabaseTable
         *
         * @return DatabaseTable
         */
        private DatabaseTable getDatabaseContractTable() {
            return getDataBase().getTable(
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TABLE_NAME);
        }

        /**
         * Returns the Open Contract Events DatabaseTable
         *
         * @return DatabaseTable
         */
        private DatabaseTable getDatabaseEventsTable() {
            return getDataBase().getTable(
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME);
        }
}
