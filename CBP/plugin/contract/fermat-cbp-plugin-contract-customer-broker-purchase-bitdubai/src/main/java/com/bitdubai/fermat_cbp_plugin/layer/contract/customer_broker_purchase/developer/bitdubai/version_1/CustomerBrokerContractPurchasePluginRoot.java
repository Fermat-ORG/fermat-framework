package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
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
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantDeleteCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantupdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerContractPurchaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerContractPurchaseDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by angel on 02/11/15.
 */
public class CustomerBrokerContractPurchasePluginRoot implements CustomerBrokerContractPurchaseManager, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, LogManagerForDevelopers, Service, Plugin {

    private ErrorManager errorManager;
    private LogManager logManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private CustomerBrokerContractPurchaseDao CustomerBrokerContractPurchaseDao;
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
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.CustomerBrokerContractPurchasePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerContractPurchaseInformation");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerContractPurchaseDao");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerContractPurchaseDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerContractPurchaseDatabaseConstants");
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
    public FermatManager getManager() {
        return null;
    }

    @Override
    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
        try {
            this.CustomerBrokerContractPurchaseDao = new CustomerBrokerContractPurchaseDao(pluginDatabaseSystem, this.pluginId);
            this.CustomerBrokerContractPurchaseDao.initializeDatabase();
        } catch (CantInitializeCustomerBrokerContractPurchaseDatabaseException cantInitializeCustomerBrokerContractPurchaseDaoException) {
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
        public List<CustomerBrokerContractPurchase> getAllCustomerBrokerContractPurchaseFromCurrentDeviceUser() throws CantGetListCustomerBrokerContractPurchaseException {
            return this.CustomerBrokerContractPurchaseDao.getAllCustomerBrokerContractPurchaseFromCurrentDeviceUser();
        }

        @Override
        public CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseForContractId(UUID ContractId) throws CantGetListCustomerBrokerContractPurchaseException {
            return this.CustomerBrokerContractPurchaseDao.getCustomerBrokerContractPurchaseForContractId(ContractId);
        }

        @Override
        public DatabaseTableRecord getCustomerBrokerPurchaseContractTable() {
            return this.CustomerBrokerContractPurchaseDao.getCustomerBrokerContractPurchaseTable();
        }

        @Override
        public CustomerBrokerContractPurchase createCustomerBrokerContractPurchase(String publicKeyCustomer, String publicKeyBroker, Float merchandiseAmount, CurrencyType merchandiseCurrency, Float referencePrice, ReferenceCurrency referenceCurrency, Float paymentAmount, CurrencyType paymentCurrency, long paymentExpirationDate, long merchandiseDeliveryExpirationDate) throws CantCreateCustomerBrokerContractPurchaseException {
            return this.CustomerBrokerContractPurchaseDao.createCustomerBrokerContractPurchase(
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
        public void updateCustomerBrokerContractPurchase(UUID contractId, ContractStatus status) throws CantupdateCustomerBrokerContractPurchaseException {
            this.CustomerBrokerContractPurchaseDao.updateCustomerBrokerContractPurchase(contractId, status);
        }

        @Override
        public void deleteCustomerBrokerContractPurchase(UUID contractID) throws CantDeleteCustomerBrokerContractPurchaseException {
            this.CustomerBrokerContractPurchaseDao.deleteCustomerBrokerContractPurchase(contractID);
        }
}
