package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_bank_money_purchase.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.exceptions.CantCreateCustomerBrokerBankMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.exceptions.CantDeleteCustomerBrokerBankMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.exceptions.CantGetListCustomerBrokerBankMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.exceptions.CantupdateCustomerBrokerBankMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.interfaces.CustomerBrokerBankMoneyPurchase;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.interfaces.CustomerBrokerBankMoneyPurchaseManager;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_bank_money_purchase.developer.bitdubai.version_1.database.CustomerBrokerBankMoneyPurchaseContractDao;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_bank_money_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerBankMoneyPurchaseContractDaoException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Angel on 16.09.15.
 */

/**

 */

public class CustomerBrokerBankMoneyPurchasePluginRoot implements CustomerBrokerBankMoneyPurchaseManager, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, LogManagerForDevelopers, Service, Plugin {

    private ErrorManager errorManager;
    private LogManager logManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private CustomerBrokerBankMoneyPurchaseContractDao customerBrokerBankMoneyPurchaseContractDao;
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
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.CustomerBrokerBankMoneyPurchasePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerBankMoneyPurchaseContract");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerBankMoneyPurchaseContractDao");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerBankMoneyPurchaseContractDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerBankMoneyPurchaseContractDatabaseConstants");
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
            this.customerBrokerBankMoneyPurchaseContractDao = new CustomerBrokerBankMoneyPurchaseContractDao(pluginDatabaseSystem);
            this.customerBrokerBankMoneyPurchaseContractDao.initialize(pluginId);
        } catch (CantInitializeCustomerBrokerBankMoneyPurchaseContractDaoException cantInitializeCustomerBrokerBankMoneyPurchaseContractDaoException) {
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
    public List<CustomerBrokerBankMoneyPurchase> getAllCustomerBrokerBankMoneyPurchaseFromCurrentDeviceUser() throws CantGetListCustomerBrokerBankMoneyPurchaseException {
        return this.customerBrokerBankMoneyPurchaseContractDao.getAllCustomerBrokerBankMoneyPurchaseFromCurrentDeviceUser();
    }

    @Override
    public CustomerBrokerBankMoneyPurchase getCustomerBrokerBankMoneyPurchaseForContractId(UUID ContractId) throws CantGetListCustomerBrokerBankMoneyPurchaseException {
        return this.customerBrokerBankMoneyPurchaseContractDao.getCustomerBrokerBankMoneyPurchaseForContractId(ContractId);
    }

    @Override
    public CustomerBrokerBankMoneyPurchase createCustomerBrokerBankMoneyPurchase(
            String publicKeyCustomer,
            String publicKeyBroker,
            Float merchandiseAmount,
            CurrencyType merchandiseCurrency,
            Float referencePrice,
            ReferenceCurrency referenceCurrency,
            Float paymentAmount,
            CurrencyType paymentCurrency,
            long paymentExpirationDate,
            long merchandiseDeliveryExpirationDate) throws CantCreateCustomerBrokerBankMoneyPurchaseException {

        return this.customerBrokerBankMoneyPurchaseContractDao.createCustomerBrokerBankMoneyPurchase(
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
    public void updateCustomerBrokerBankMoneyPurchase(UUID contractId, ContractStatus status) throws CantupdateCustomerBrokerBankMoneyPurchaseException {
        this.customerBrokerBankMoneyPurchaseContractDao.updateCustomerBrokerBankMoneyPurchase(contractId, status);
    }

    @Override
    public void deleteCustomerBrokerBankMoneyPurchase(UUID contractID) throws CantDeleteCustomerBrokerBankMoneyPurchaseException {
        this.customerBrokerBankMoneyPurchaseContractDao.deleteCustomerBrokerBankMoneyPurchase(contractID);
    }

    @Override
    public DatabaseTableRecord getCustomerBrokerBankMoneySaleContractTable() {
        return this.customerBrokerBankMoneyPurchaseContractDao.getCustomerBrokerBankMoneyPurchaseContractTable();
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}