package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.exceptions.CantDeleteCustomerBrokerPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.exceptions.CantupdateCustomerBrokerPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.interfaces.CustomerBrokerPurchase;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.interfaces.CustomerBrokerPurchaseManager;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseContractDao;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseContractDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by angel on 02/11/15.
 */
public class CustomerBrokerPurchasePluginRoot implements CustomerBrokerPurchaseManager, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, LogManagerForDevelopers, Service, Plugin {

    private ErrorManager errorManager;
    private LogManager logManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private CustomerBrokerPurchaseContractDao customerBrokerPurchaseContractDao;
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
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.CustomerBrokerPurchasePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseContract");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseContractDao");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseContractDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseContractDatabaseConstants");
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
            this.customerBrokerPurchaseContractDao = new CustomerBrokerPurchaseContractDao(pluginDatabaseSystem, this.pluginId);
            this.customerBrokerPurchaseContractDao.initializeDatabase();
        } catch (CantInitializeCustomerBrokerPurchaseContractDatabaseException cantInitializeCustomerBrokerPurchaseContractDaoException) {
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
        public List<CustomerBrokerPurchase> getAllCustomerBrokerPurchaseFromCurrentDeviceUser() throws CantGetListCustomerBrokerPurchaseException {
            return this.customerBrokerPurchaseContractDao.getAllCustomerBrokerPurchaseFromCurrentDeviceUser();
        }

        @Override
        public CustomerBrokerPurchase getCustomerBrokerPurchaseForContractId(UUID ContractId) throws CantGetListCustomerBrokerPurchaseException {
            return this.customerBrokerPurchaseContractDao.getCustomerBrokerPurchaseForContractId(ContractId);
        }

        @Override
        public DatabaseTableRecord getCustomerBrokerSaleContractTable() {
            return this.customerBrokerPurchaseContractDao.getCustomerBrokerPurchaseContractTable();
        }

        @Override
        public CustomerBrokerPurchase createCustomerBrokerPurchase(String publicKeyCustomer, String publicKeyBroker, Float merchandiseAmount, CurrencyType merchandiseCurrency, Float referencePrice, ReferenceCurrency referenceCurrency, Float paymentAmount, CurrencyType paymentCurrency, long paymentExpirationDate, long merchandiseDeliveryExpirationDate) throws CantCreateCustomerBrokerPurchaseException {
            return this.customerBrokerPurchaseContractDao.createCustomerBrokerPurchase(
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
        public void updateCustomerBrokerPurchase(UUID contractId, ContractStatus status) throws CantupdateCustomerBrokerPurchaseException {
            this.customerBrokerPurchaseContractDao.updateCustomerBrokerPurchase(contractId, status);
        }

        @Override
        public void deleteCustomerBrokerPurchase(UUID contractID) throws CantDeleteCustomerBrokerPurchaseException {
            this.customerBrokerPurchaseContractDao.deleteCustomerBrokerPurchase(contractID);
        }
}
