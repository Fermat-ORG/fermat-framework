package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleClausesDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleLogsDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleNegotiationDaoException;

import java.util.UUID;

/**
 * Created by angel on 20/10/15.
 */
public class CustomerBrokerSaleNegotiationDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
        Builders
     */

        public CustomerBrokerSaleNegotiationDao(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

    /*
        Public methods
     */


        public void initialize(UUID pluginId) throws CantInitializeCustomerBrokerSaleNegotiationDaoException {
            try {
                this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
            } catch (DatabaseNotFoundException e) {

            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCustomerBrokerSaleNegotiationDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME, "");
            } catch (Exception exception) {
                throw new CantInitializeCustomerBrokerSaleNegotiationDaoException(CantInitializeCustomerBrokerSaleNegotiationDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
            }
        }

    /*
        Private methods
     */

        private void initializeClause(UUID pluginId) throws CantInitializeCustomerBrokerSaleClausesDaoException {
            try {
                this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
            } catch (DatabaseNotFoundException e) {

            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCustomerBrokerSaleClausesDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_TABLE_NAME, "");
            } catch (Exception exception) {
                throw new CantInitializeCustomerBrokerSaleClausesDaoException(CantInitializeCustomerBrokerSaleNegotiationDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
            }
        }

        private void initializeLogs(UUID pluginId) throws CantInitializeCustomerBrokerSaleLogsDaoException {
            try {
                this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSE_STATUS_LOG_TABLE_NAME);
            } catch (DatabaseNotFoundException e) {

            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCustomerBrokerSaleLogsDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSE_STATUS_LOG_TABLE_NAME, "");
            } catch (Exception exception) {
                throw new CantInitializeCustomerBrokerSaleLogsDaoException(CantInitializeCustomerBrokerSaleNegotiationDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
            }
        }
}
