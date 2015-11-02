package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantDeleteCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantupdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.interfaces.CustomerBrokerSale;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.interfaces.CustomerBrokerSaleManager;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleContractDao;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleContractDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by angel on 02/11/15.
 */
public class CustomerBrokerSalePluginRoot implements CustomerBrokerSaleManager, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, LogManagerForDevelopers, Service, Plugin {

    private ErrorManager errorManager;
    private LogManager logManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private CustomerBrokerSaleContractDao customerBrokerSaleContractDao;
    private UUID pluginId;
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.CustomerBrokerSalePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerSaleInformation");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleContractDao");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleContractDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleContractDatabaseConstants");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

    }

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
        try {
            this.customerBrokerSaleContractDao = new CustomerBrokerSaleContractDao(pluginDatabaseSystem, this.pluginId);
            this.customerBrokerSaleContractDao.initializeDatabase();
        } catch (CantInitializeCustomerBrokerSaleContractDatabaseException cantInitializeCustomerBrokerSaleContractDaoException) {
            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DESIGNER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantInitializeExtraUserRegistryException);
            // throw new CantStartPluginException(cantInitializeExtraUserRegistryException, Plugins.BITDUBAI_ACTOR_DEVELOPER);
        }
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*
    *   Methods of Menager
    * */

        @Override
        public List<CustomerBrokerSale> getAllCustomerBrokerSaleFromCurrentDeviceUser() throws CantGetListCustomerBrokerSaleException {
            return this.customerBrokerSaleContractDao.getAllCustomerBrokerSaleFromCurrentDeviceUser();
        }

        @Override
        public CustomerBrokerSale getCustomerBrokerSaleForContractId(UUID ContractId) throws CantGetListCustomerBrokerSaleException {
            return this.customerBrokerSaleContractDao.getCustomerBrokerSaleForContractId(ContractId);
        }

        @Override
        public DatabaseTableRecord getCustomerBrokerSaleContractTable() {
            return this.customerBrokerSaleContractDao.getCustomerBrokerSaleContractTable();
        }

        @Override
        public CustomerBrokerSale createCustomerBrokerSale(String publicKeyCustomer, String publicKeyBroker, Float merchandiseAmount, CurrencyType merchandiseCurrency, Float referencePrice, ReferenceCurrency referenceCurrency, Float paymentAmount, CurrencyType paymentCurrency, long paymentExpirationDate, long merchandiseDeliveryExpirationDate) throws CantCreateCustomerBrokerSaleException {
            return this.customerBrokerSaleContractDao.createCustomerBrokerSale(
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
        public void updateCustomerBrokerSale(UUID contractId, ContractStatus status) throws CantupdateCustomerBrokerSaleException {
            this.customerBrokerSaleContractDao.updateCustomerBrokerSale(contractId, status);
        }

        @Override
        public void deleteCustomerBrokerSale(UUID contractID) throws CantDeleteCustomerBrokerSaleException {
            this.customerBrokerSaleContractDao.deleteCustomerBrokerSale(contractID);
        }
}
