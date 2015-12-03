package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantupdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerContractPurchaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseContractDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseContractDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angel on 02/11/15.
 */
public class CustomerBrokerContractPurchasePluginRoot extends AbstractPlugin implements CustomerBrokerContractPurchaseManager, DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.USER, addon = Addons.DEVICE_USER)
    private DeviceUserManager deviceUserManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    private CustomerBrokerContractPurchaseDao CustomerBrokerContractPurchaseDao;

    /*
       Builder
    */

        public CustomerBrokerContractPurchasePluginRoot() {
            super(new PluginVersionReference(new Version()));
        }

    /*
        Plugin Interface implementation.
    */

        @Override
        public void start() throws CantStartPluginException {
            this.serviceStatus = ServiceStatus.STARTED;
            try {
                this.CustomerBrokerContractPurchaseDao = new CustomerBrokerContractPurchaseDao(pluginDatabaseSystem, this.pluginId);
                this.CustomerBrokerContractPurchaseDao.initializeDatabase();
            } catch (CantInitializeCustomerBrokerPurchaseContractDatabaseException e) {
                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantStartPluginException();
            }
        }

    /*
        DatabaseManagerForDevelopers Interface implementation.
    */

        @Override
        public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
            CustomerBrokerPurchaseContractDeveloperDatabaseFactory dbFactory = new CustomerBrokerPurchaseContractDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            return dbFactory.getDatabaseList(developerObjectFactory);
        }

        @Override
        public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
            CustomerBrokerPurchaseContractDeveloperDatabaseFactory dbFactory = new CustomerBrokerPurchaseContractDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            return dbFactory.getDatabaseTableList(developerObjectFactory);
        }

        @Override
        public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
            try {
                CustomerBrokerPurchaseContractDeveloperDatabaseFactory dbFactory = new CustomerBrokerPurchaseContractDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
                dbFactory.initializeDatabase();
                return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
            } catch (CantInitializeCustomerBrokerPurchaseContractDatabaseException e) {
                this.errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
            return new ArrayList<>();
        }

    /*
        CustomerBrokerContractPurchase Interface implementation.
    */

        @Override
        public List<CustomerBrokerContractPurchase> getAllCustomerBrokerContractPurchaseFromCurrentDeviceUser() throws CantGetListCustomerBrokerContractPurchaseException {
            return this.CustomerBrokerContractPurchaseDao.getAllCustomerBrokerPurchaseContractFromCurrentDeviceUser();
        }

        @Override
        public CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseForContractId(String ContractId) throws CantGetListCustomerBrokerContractPurchaseException {
            return this.CustomerBrokerContractPurchaseDao.getCustomerBrokerPurchaseContractForcontractID(ContractId);
        }

        @Override
        public CustomerBrokerContractPurchase createCustomerBrokerContractPurchase(CustomerBrokerContractPurchase contract) throws CantCreateCustomerBrokerContractPurchaseException {
            return this.CustomerBrokerContractPurchaseDao.createCustomerBrokerPurchaseContract(contract);
        }

        @Override
        public void updateStatusCustomerBrokerPurchaseContractStatus(String contractId, ContractStatus status) throws CantupdateCustomerBrokerContractPurchaseException {
            this.CustomerBrokerContractPurchaseDao.updateStatusCustomerBrokerPurchaseContract(contractId, status);
        }

        @Override
        public void updateStatusCustomerBrokerPurchaseContractClauseStatus(String contractId, ContractClause clause) throws CantupdateCustomerBrokerContractPurchaseException {
            this.CustomerBrokerContractPurchaseDao.updateStatusCustomerBrokerPurchaseContractClauseStatus(contractId, clause);
        }
}