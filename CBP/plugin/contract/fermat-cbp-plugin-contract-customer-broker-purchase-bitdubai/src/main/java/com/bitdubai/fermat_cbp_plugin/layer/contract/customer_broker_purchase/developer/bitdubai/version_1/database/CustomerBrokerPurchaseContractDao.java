package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseContractDatabaseException;

import java.util.UUID;

/**
 * Created by angel on 02/11/15.
 */
public class CustomerBrokerPurchaseContractDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
        Builders
     */

        public CustomerBrokerPurchaseContractDao(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

    /*
        Public methods
     */

        public void initialize(UUID pluginId) throws CantInitializeCustomerBrokerPurchaseContractDatabaseException {
            try {
                this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_TABLE_NAME);
            } catch (DatabaseNotFoundException e) {

            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCustomerBrokerPurchaseContractDatabaseException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_TABLE_NAME, "");
            } catch (Exception exception) {
                throw new CantInitializeCustomerBrokerPurchaseContractDatabaseException(CantInitializeCustomerBrokerPurchaseContractDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
            }
        }
}
