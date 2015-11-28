package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantDeleteCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantupdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerContractSaleDao;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerContractSaleDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerContractSaleDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angel on 02/11/15.
 */

public class CustomerBrokerContractSalePluginRoot extends AbstractPlugin implements CustomerBrokerContractSaleManager, DatabaseManagerForDevelopers

    {
    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.USER, addon = Addons.DEVICE_USER)
    private DeviceUserManager deviceUserManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    private CustomerBrokerContractSaleDao CustomerBrokerContractSaleDao;

    /*
       Builder
    */

        public CustomerBrokerContractSalePluginRoot() {
            super(new PluginVersionReference(new Version()));
        }

    /*
        Plugin Interface implementation.
    */

        @Override
        public void start() throws CantStartPluginException {
            this.serviceStatus = ServiceStatus.STARTED;
            try {
                this.CustomerBrokerContractSaleDao = new CustomerBrokerContractSaleDao(pluginDatabaseSystem, this.pluginId);
                this.CustomerBrokerContractSaleDao.initializeDatabase();
            } catch (CantInitializeCustomerBrokerContractSaleDatabaseException e) {
                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantStartPluginException();
            }
        }

    /*
        DatabaseManagerForDevelopers Interface implementation.
    */

        @Override
        public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
            CustomerBrokerContractSaleDeveloperDatabaseFactory dbFactory = new CustomerBrokerContractSaleDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            return dbFactory.getDatabaseList(developerObjectFactory);
        }

        @Override
        public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
            CustomerBrokerContractSaleDeveloperDatabaseFactory dbFactory = new CustomerBrokerContractSaleDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            return dbFactory.getDatabaseTableList(developerObjectFactory);
        }

        @Override
        public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
            try {
                CustomerBrokerContractSaleDeveloperDatabaseFactory dbFactory = new CustomerBrokerContractSaleDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
                dbFactory.initializeDatabase();
                return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
            } catch (CantInitializeCustomerBrokerContractSaleDatabaseException e) {
                this.errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
            return new ArrayList<>();
        }

    /*
        CustomerBrokerContractSale Interface implementation.
    */

        @Override
        public List<CustomerBrokerContractSale> getAllCustomerBrokerContractSaleFromCurrentDeviceUser() throws CantGetListCustomerBrokerContractSaleException {
            return this.CustomerBrokerContractSaleDao.getAllCustomerBrokerContractSaleFromCurrentDeviceUser();
        }

        @Override
        public CustomerBrokerContractSale getCustomerBrokerContractSaleForContractId(String ContractId) throws CantGetListCustomerBrokerContractSaleException {
            return this.CustomerBrokerContractSaleDao.getCustomerBrokerContractSaleForcontractID(ContractId);
        }

        @Override
        public DatabaseTableRecord getCustomerBrokerContractSaleTable() {
            return this.CustomerBrokerContractSaleDao.getCustomerBrokerContractSaleTable();
        }

        @Override
        public CustomerBrokerContractSale createCustomerBrokerContractSale(String ContractId, String publicKeyCustomer, String publicKeyBroker, Float merchandiseAmount, CurrencyType merchandiseCurrency, Float referencePrice, ReferenceCurrency referenceCurrency, Float paymentAmount, CurrencyType paymentCurrency, long paymentExpirationDate, long merchandiseDeliveryExpirationDate) throws CantCreateCustomerBrokerContractSaleException {
            return this.CustomerBrokerContractSaleDao.createCustomerBrokerContractSale(
                    ContractId,
                    publicKeyCustomer,
                    publicKeyBroker,
                    merchandiseAmount,
                    merchandiseCurrency,
                    referencePrice,
                    referenceCurrency,
                    paymentAmount,
                    paymentCurrency,
                    paymentExpirationDate,
                    merchandiseDeliveryExpirationDate
            );
        }

        @Override
        public void updateCustomerBrokerContractSale(String contractId, ContractStatus status) throws CantupdateCustomerBrokerContractSaleException {
            this.CustomerBrokerContractSaleDao.updateCustomerBrokerContractSale(contractId, status);
        }

        @Override
        public void deleteCustomerBrokerContractSale(String contractID) throws CantDeleteCustomerBrokerContractSaleException {
            this.CustomerBrokerContractSaleDao.deleteCustomerBrokerContractSale(contractID);
        }
    }
