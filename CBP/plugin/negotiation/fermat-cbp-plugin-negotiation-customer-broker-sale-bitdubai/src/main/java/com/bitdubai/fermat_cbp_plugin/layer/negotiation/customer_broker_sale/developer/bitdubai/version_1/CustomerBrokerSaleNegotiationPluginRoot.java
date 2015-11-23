package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleNegotiationDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleNegotiationDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jorge on 12-10-2015.
 */
public class CustomerBrokerSaleNegotiationPluginRoot implements CustomerBrokerSaleNegotiationManager, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, LogManagerForDevelopers, Service, Plugin {

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
    public FermatManager getManager() {
        return null;
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
            if (CustomerBrokerSaleNegotiationPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CustomerBrokerSaleNegotiationPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CustomerBrokerSaleNegotiationPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CustomerBrokerSaleNegotiationPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    @Override
    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
        try {
            this.customerBrokerSaleNegotiationDao = new CustomerBrokerSaleNegotiationDao(pluginDatabaseSystem, pluginId);
            this.customerBrokerSaleNegotiationDao.initializeDatabase();
        } catch (CantInitializeCustomerBrokerSaleNegotiationDatabaseException cantInitializeExtraUserRegistryException) {
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
    public CustomerBrokerSaleNegotiation createNegotiation(String publicKeyCustomer, String publicKeyBroker) throws CantCreateCustomerBrokerSaleNegotiationException {
        return customerBrokerSaleNegotiationDao.createCustomerBrokerSaleNegotiation(publicKeyCustomer, publicKeyBroker);
    }

    @Override
    public void cancelNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            customerBrokerSaleNegotiationDao.cancelNegotiation(negotiation);
        } catch (CantUpdateCustomerBrokerSaleException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public void closeNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {

            if(verifyStatusClause(customerBrokerSaleNegotiationDao.getClauses(negotiation.getNegotiationId()))){
                customerBrokerSaleNegotiationDao.closeNegotiation(negotiation);
            }else{
                throw new CantUpdateCustomerBrokerSaleException();
            }

        } catch (CantGetListClauseException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantUpdateCustomerBrokerSaleException e){
            throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    private boolean verifyStatusClause(Collection<Clause> clausules) throws CantUpdateCustomerBrokerSaleException {
        Map<ClauseType, String> clausesAgreed = new HashMap<ClauseType, String>();

        for(Clause clause : clausules) {
            if(clause.getStatus() == ClauseStatus.AGREED){
                clausesAgreed.put(clause.getType(), clause.getValue());
            }
        }

        if(
                ( !clausesAgreed.containsKey(ClauseType.BROKER_CURRENCY) ) &&
                ( !clausesAgreed.containsKey(ClauseType.EXCHANGE_RATE) ) &&
                ( !clausesAgreed.containsKey(ClauseType.BROKER_CURRENCY_QUANTITY) ) &&
                ( !clausesAgreed.containsKey(ClauseType.BROKER_PAYMENT_METHOD) )
        ){
            return false;
        }

        if( clausesAgreed.containsValue(CurrencyType.CRYPTO_MONEY.getCode()) ){
            if( !clausesAgreed.containsKey(ClauseType.BROKER_CRYPTO_ADDRESS) ){
                return false;
            }
        }

        if( clausesAgreed.containsValue(CurrencyType.BANK_MONEY.getCode()) ){
            if(
                    ( !clausesAgreed.containsKey(ClauseType.BROKER_BANK) ) &&
                    ( !clausesAgreed.containsKey(ClauseType.BROKER_BANK_ACCOUNT) )
            ){
                return false;
            }
        }

        if( clausesAgreed.containsValue(CurrencyType.CASH_ON_HAND_MONEY.getCode()) ){
            if(
                    ( !clausesAgreed.containsKey(ClauseType.PLACE_TO_MEET) ) &&
                    ( !clausesAgreed.containsKey(ClauseType.DATE_TIME_TO_MEET) )
             ){
                return false;
            }
        }

        if( clausesAgreed.containsValue(CurrencyType.CASH_DELIVERY_MONEY.getCode()) ){
            if(
                    ( !clausesAgreed.containsKey(ClauseType.BROKER_PLACE_TO_DELIVER) ) &&
                    ( !clausesAgreed.containsKey(ClauseType.BROKER_DATE_TIME_TO_DELIVER) )
            ){
                return false;
            }
        }

        return true;
    }

    @Override
    public CustomerBrokerSaleNegotiation sendToCustomer(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        return customerBrokerSaleNegotiationDao.sendToCustomer(negotiation);
    }

    @Override
    public CustomerBrokerSaleNegotiation waitForCustomer(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        return customerBrokerSaleNegotiationDao.waitForCustomer(negotiation);
    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiations() throws CantGetListSaleNegotiationsException {
        try {
            Collection<CustomerBrokerSaleNegotiation> negotiations = new ArrayList<CustomerBrokerSaleNegotiation>();
            negotiations = customerBrokerSaleNegotiationDao.getNegotiations();
            return negotiations;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiations(NegotiationStatus status) throws CantGetListSaleNegotiationsException {
        try {
            Collection<CustomerBrokerSaleNegotiation> negotiations = new ArrayList<CustomerBrokerSaleNegotiation>();
            negotiations = customerBrokerSaleNegotiationDao.getNegotiations(status);
            return negotiations;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiationsByCustomer(ActorIdentity customer) throws CantGetListSaleNegotiationsException {
        try {
            Collection<CustomerBrokerSaleNegotiation> negotiations = new ArrayList<CustomerBrokerSaleNegotiation>();
            negotiations = customerBrokerSaleNegotiationDao.getNegotiationsByCustomer(customer);
            return negotiations;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiationsByBroker(ActorIdentity broker) throws CantGetListSaleNegotiationsException {
        try {
            Collection<CustomerBrokerSaleNegotiation> negotiations = new ArrayList<CustomerBrokerSaleNegotiation>();
            negotiations = customerBrokerSaleNegotiationDao.getNegotiationsByBroker(broker);
            return negotiations;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        }
    }
}
