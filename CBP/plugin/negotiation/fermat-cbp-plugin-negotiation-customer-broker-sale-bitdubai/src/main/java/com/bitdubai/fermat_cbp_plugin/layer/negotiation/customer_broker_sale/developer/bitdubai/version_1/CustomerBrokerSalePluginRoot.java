package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.exceptions.CantListSaleNegotianionsException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleNegotiationDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleNegotiationDaoException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jorge on 12-10-2015.
 */
public class CustomerBrokerSalePluginRoot implements CustomerBrokerSaleNegotiationManager, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, LogManagerForDevelopers, Service, Plugin {

    private ErrorManager errorManager;
    private LogManager logManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private static Map<String, LogLevel> newLoggingLevel = new HashMap<>();
    private CustomerBrokerSaleNegotiationDao customerBrokerSaleNegotiationDao;
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

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
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.CryptoBrokerIdentityPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerSaleNegotiation");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleNegotiationDao");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleNegotiationDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleNegotiationDatabaseConstants");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            if (CustomerBrokerSalePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CustomerBrokerSalePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CustomerBrokerSalePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CustomerBrokerSalePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    @Override
    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
        try {
            this.customerBrokerSaleNegotiationDao = new CustomerBrokerSaleNegotiationDao(pluginDatabaseSystem);
            this.customerBrokerSaleNegotiationDao.initialize(pluginId);
        } catch (CantInitializeCustomerBrokerSaleNegotiationDaoException cantInitializeExtraUserRegistryException) {
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

    @Override
    public CustomerBrokerSaleNegotiation createNegotiation(String publicKeyCustomer, String publicKeyBroker, Collection<Clause> clauses) throws CantCreateCustomerBrokerSaleNegotiationException {
        return null;
    }

    @Override
    public void cancelNegotiation(CustomerBrokerSaleNegotiation negotiation) {
        
    }

    @Override
    public void closeNegotiation(CustomerBrokerSaleNegotiation negotiation) {

    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiations() throws CantListSaleNegotianionsException {
        try {
            Collection<CustomerBrokerSaleNegotiation> negotiations = new ArrayList<CustomerBrokerSaleNegotiation>();
            negotiations = customerBrokerSaleNegotiationDao.getNegotiations();
            return negotiations;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListSaleNegotianionsException(CantListSaleNegotianionsException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantListSaleNegotianionsException(CantListSaleNegotianionsException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiations(NegotiationStatus status) throws CantListSaleNegotianionsException {
        try {
            Collection<CustomerBrokerSaleNegotiation> negotiations = new ArrayList<CustomerBrokerSaleNegotiation>();
            negotiations = customerBrokerSaleNegotiationDao.getNegotiations(status);
            return negotiations;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListSaleNegotianionsException(CantListSaleNegotianionsException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantListSaleNegotianionsException(CantListSaleNegotianionsException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiationsByCustomer(ActorIdentity customer) throws CantListSaleNegotianionsException {
        try {
            Collection<CustomerBrokerSaleNegotiation> negotiations = new ArrayList<CustomerBrokerSaleNegotiation>();
            negotiations = customerBrokerSaleNegotiationDao.getNegotiationsByCustomer(customer);
            return negotiations;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListSaleNegotianionsException(CantListSaleNegotianionsException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantListSaleNegotianionsException(CantListSaleNegotianionsException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiationsByBroker(ActorIdentity broker) throws CantListSaleNegotianionsException {
        try {
            Collection<CustomerBrokerSaleNegotiation> negotiations = new ArrayList<CustomerBrokerSaleNegotiation>();
            negotiations = customerBrokerSaleNegotiationDao.getNegotiationsByBroker(broker);
            return negotiations;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListSaleNegotianionsException(CantListSaleNegotianionsException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantListSaleNegotianionsException(CantListSaleNegotianionsException.DEFAULT_MESSAGE, e, "", "");
        }
    }
}
