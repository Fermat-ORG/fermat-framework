package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseClausesDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseLogsDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseNegotiationDaoException;

import java.util.UUID;

/**
 * Created by angel on 19/10/15.
 */
public class CustomerBrokerPurchaseNegotiationDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
        Builders
     */

    public CustomerBrokerPurchaseNegotiationDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*
        Public methods
     */

    public void initialize(UUID pluginId) throws CantInitializeCustomerBrokerPurchaseNegotiationDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
        } catch (DatabaseNotFoundException e) {

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomerBrokerPurchaseNegotiationDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeCustomerBrokerPurchaseNegotiationDaoException(CantInitializeCustomerBrokerPurchaseNegotiationDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /*
        Private methods
     */

    private void initializeClause(UUID pluginId) throws CantInitializeCustomerBrokerPurchaseClausesDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
        } catch (DatabaseNotFoundException e) {

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomerBrokerPurchaseClausesDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeCustomerBrokerPurchaseClausesDaoException(CantInitializeCustomerBrokerPurchaseNegotiationDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private void initializeLogs(UUID pluginId) throws CantInitializeCustomerBrokerPurchaseLogsDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSE_STATUS_LOG_TABLE_NAME);
        } catch (DatabaseNotFoundException e) {

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomerBrokerPurchaseLogsDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSE_STATUS_LOG_TABLE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeCustomerBrokerPurchaseLogsDaoException(CantInitializeCustomerBrokerPurchaseNegotiationDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }
}
