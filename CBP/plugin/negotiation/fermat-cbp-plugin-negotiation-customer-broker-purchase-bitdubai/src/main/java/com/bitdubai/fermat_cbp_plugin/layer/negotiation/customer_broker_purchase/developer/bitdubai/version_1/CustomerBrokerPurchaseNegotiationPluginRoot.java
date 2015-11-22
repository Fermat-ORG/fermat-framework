package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantGetNextClauseTypeException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseNegotiationDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException;
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
public class CustomerBrokerPurchaseNegotiationPluginRoot implements CustomerBrokerPurchaseNegotiationManager, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, LogManagerForDevelopers, Service, Plugin {

    private ErrorManager errorManager;
    private LogManager logManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private static Map<String, LogLevel> newLoggingLevel = new HashMap<>();
    private CustomerBrokerPurchaseNegotiationDao customerBrokerPurchaseNegotiationDao;
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
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.CryptoBrokerIdentityPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseNegotiation");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseNegotiationDao");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseNegotiationDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseNegotiationDatabaseConstants");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            if (CustomerBrokerPurchaseNegotiationPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CustomerBrokerPurchaseNegotiationPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CustomerBrokerPurchaseNegotiationPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CustomerBrokerPurchaseNegotiationPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    @Override
    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
        try {
            this.customerBrokerPurchaseNegotiationDao = new CustomerBrokerPurchaseNegotiationDao(pluginDatabaseSystem, pluginId);
            this.customerBrokerPurchaseNegotiationDao.initializeDatabase();
        } catch (CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException cantInitializeExtraUserRegistryException) {
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
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem){
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public CustomerBrokerPurchaseNegotiation createCustomerBrokerPurchaseNegotiation(String publicKeyCustomer, String publicKeyBroker, Collection<Clause> clauses) throws CantCreateCustomerBrokerPurchaseNegotiationException {
        return customerBrokerPurchaseNegotiationDao.createCustomerBrokerPurchaseNegotiation(publicKeyCustomer, publicKeyBroker, clauses);
    }

    @Override
    public CustomerBrokerPurchaseNegotiation updateCustomerBrokerPurchaseNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
        return customerBrokerPurchaseNegotiationDao.updateCustomerBrokerPurchaseNegotiation(negotiation);
    }

    @Override
    public void cancelNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
        try {
            customerBrokerPurchaseNegotiationDao.cancelNegotiation(negotiation);
        } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public void closeNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
        try {
            if(verifyStatusClause(customerBrokerPurchaseNegotiationDao.getClauses(negotiation.getNegotiationId()))){
                customerBrokerPurchaseNegotiationDao.closeNegotiation(negotiation);
            }else{
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException();
            }
        } catch (CantGetListClauseException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e){
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    private boolean verifyStatusClause(Collection<Clause> clausules) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
        Map<ClauseType, String> clausesAgreed = new HashMap<ClauseType, String>();

        for(Clause clause : clausules) {
            if(clause.getStatus() == ClauseStatus.AGREED){
                clausesAgreed.put(clause.getType(), clause.getValue());
            }
        }

        if(
                ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_CURRENCY) ) &&
                ( !clausesAgreed.containsKey(ClauseType.EXCHANGE_RATE) ) &&
                ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_CURRENCY_QUANTITY) ) &&
                ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_PAYMENT_METHOD) )
        ){
            return false;
        }

        if( clausesAgreed.containsValue(CurrencyType.CRYPTO_MONEY.getCode()) ){
            if( !clausesAgreed.containsKey(ClauseType.CUSTOMER_CRYPTO_ADDRESS) ){
                return false;
            }
        }

        if( clausesAgreed.containsValue(CurrencyType.BANK_MONEY.getCode()) ){
            if(
                    ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_BANK) ) &&
                    ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_BANK_ACCOUNT) )
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
                    ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_PLACE_TO_DELIVER) ) &&
                    ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER) )
            ){
                return false;
            }
        }

        return true;
    }

    @Override
    public CustomerBrokerPurchaseNegotiation sendToBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
        return customerBrokerPurchaseNegotiationDao.sendToBroker(negotiation);
    }

    @Override
    public CustomerBrokerPurchaseNegotiation waitForBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
        return customerBrokerPurchaseNegotiationDao.waitForBroker(negotiation);
    }


    @Override
    public Collection<CustomerBrokerPurchaseNegotiation> getNegotiations() throws CantGetListPurchaseNegotiationsException{
        try {
            Collection<CustomerBrokerPurchaseNegotiation> negotiations = new ArrayList<CustomerBrokerPurchaseNegotiation>();
            negotiations = customerBrokerPurchaseNegotiationDao.getNegotiations();
            return negotiations;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerPurchaseNegotiation> getNegotiations(NegotiationStatus status) throws CantGetListPurchaseNegotiationsException {
        try {
            Collection<CustomerBrokerPurchaseNegotiation> negotiations = new ArrayList<CustomerBrokerPurchaseNegotiation>();
            negotiations = customerBrokerPurchaseNegotiationDao.getNegotiations(status);
            return negotiations;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerPurchaseNegotiation> getNegotiationsByCustomer(ActorIdentity customer) throws CantGetListPurchaseNegotiationsException {
        try {
            Collection<CustomerBrokerPurchaseNegotiation> negotiations = new ArrayList<CustomerBrokerPurchaseNegotiation>();
            negotiations = customerBrokerPurchaseNegotiationDao.getNegotiationsByCustomer(customer);
            return negotiations;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerPurchaseNegotiation> getNegotiationsByBroker(ActorIdentity broker) throws CantGetListPurchaseNegotiationsException {
        try {
            Collection<CustomerBrokerPurchaseNegotiation> negotiations = new ArrayList<CustomerBrokerPurchaseNegotiation>();
            negotiations = customerBrokerPurchaseNegotiationDao.getNegotiationsByBroker(broker);
            return negotiations;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    // Clauses

        public ClauseType getNextClauseType(UUID negotiation) throws CantGetNextClauseTypeException {

            try {
                ClauseType type = this.customerBrokerPurchaseNegotiationDao.getNextClauseType(negotiation);

                switch (type) {
                    case CUSTOMER_CURRENCY:
                        return ClauseType.EXCHANGE_RATE;

                    case EXCHANGE_RATE:
                        return ClauseType.CUSTOMER_CURRENCY_QUANTITY;

                    case CUSTOMER_CURRENCY_QUANTITY:
                        return ClauseType.CUSTOMER_PAYMENT_METHOD;

                    case CUSTOMER_PAYMENT_METHOD:
                        CurrencyType paymentMethod = CurrencyType.getByCode(this.customerBrokerPurchaseNegotiationDao.getPaymentMethod(negotiation));
                        return getNextClauseTypeByCurrencyType(paymentMethod);

                    case CUSTOMER_BANK:
                        return ClauseType.CUSTOMER_BANK_ACCOUNT;

                    case PLACE_TO_MEET:
                        return ClauseType.DATE_TIME_TO_MEET;

                    case CUSTOMER_PLACE_TO_DELIVER:
                        return ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER;

                    default:
                        throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE);
                }
            } catch (InvalidParameterException e) {
                throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE);
            }
        }

        private ClauseType getNextClauseTypeByCurrencyType(CurrencyType paymentMethod) throws CantGetNextClauseTypeException {
            switch (paymentMethod) {
                case CRYPTO_MONEY:
                    return ClauseType.CUSTOMER_CRYPTO_ADDRESS;

                case BANK_MONEY:
                    return ClauseType.CUSTOMER_BANK;

                case CASH_ON_HAND_MONEY:
                    return ClauseType.PLACE_TO_MEET;

                case CASH_DELIVERY_MONEY:
                    return ClauseType.CUSTOMER_PLACE_TO_DELIVER;

                default:
                    throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE);
            }
        }

}
