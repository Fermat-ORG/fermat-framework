package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationPurchaseRecord;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationSaleRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.ClauseMock;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.Test.mocks.PurchaseNegotiationMock;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.Test.mocks.SaleNegotiationMock;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantGetListCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantCancelNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantGetListCustomerBrokerUpdateNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdate;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.event_handler.CustomerBrokerUpdateServiceEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerUpdateNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure.CustomerBrokerUpdateAgent;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure.CustomerBrokerUpdateManagerImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
/**
 * Created by Yordin Alayn on 16.09.15.
 */

public class NegotiationTransactionCustomerBrokerUpdatePluginRoot  extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers{

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,           layer = Layers.PLATFORM_SERVICE,    addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,        layer = Layers.SYSTEM,              addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,        layer = Layers.SYSTEM,              addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,           layer = Layers.PLATFORM_SERVICE,    addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM,     layer = Layers.NEGOTIATION,         plugin = Plugins.NEGOTIATION_PURCHASE)
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM,     layer = Layers.NEGOTIATION,         plugin = Plugins.NEGOTIATION_SALE)
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM,     layer = Layers.NETWORK_SERVICE,     plugin = Plugins.NEGOTIATION_TRANSMISSION)
    private NegotiationTransmissionManager negotiationTransmissionManager;

    /*Represent the dataBase*/
    private Database                                                            dataBase;

    /*Represent DeveloperDatabaseFactory*/
    private CustomerBrokerUpdateNegotiationTransactionDeveloperDatabaseFactory  customerBrokerUpdateNegotiationTransactionDeveloperDatabaseFactory;

    /*Represent CustomerBrokerNewNegotiationTransactionDatabaseDao*/
    private CustomerBrokerUpdateNegotiationTransactionDatabaseDao               customerBrokerUpdateNegotiationTransactionDatabaseDao;

    /*Represent Customer Broker New Manager*/
    private CustomerBrokerUpdateManagerImpl                                     customerBrokerUpdateManagerImpl;

    /*Represent Agent*/
    private CustomerBrokerUpdateAgent                                           customerBrokerUpdateAgent;

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiation                                   customerBrokerPurchaseNegotiation;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiation                                       customerBrokerSaleNegotiation;

    /*Represent Service Event Handler*/
    private CustomerBrokerUpdateServiceEventHandler                             customerBrokerUpdateServiceEventHandler;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    public NegotiationTransactionCustomerBrokerUpdatePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /*IMPLEMENTATION Service.*/
    @Override
    public void start() throws CantStartPluginException {

        try {

            //Initialize database
            initializeDb();

            //Initialize Developer Database Factory
            customerBrokerUpdateNegotiationTransactionDeveloperDatabaseFactory = new CustomerBrokerUpdateNegotiationTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            customerBrokerUpdateNegotiationTransactionDeveloperDatabaseFactory.initializeDatabase();

            //Initialize Dao
            customerBrokerUpdateNegotiationTransactionDatabaseDao = new CustomerBrokerUpdateNegotiationTransactionDatabaseDao(pluginDatabaseSystem,pluginId,dataBase);

            //Initialize manager
            customerBrokerUpdateManagerImpl = new CustomerBrokerUpdateManagerImpl(
                customerBrokerUpdateNegotiationTransactionDatabaseDao,
                customerBrokerPurchaseNegotiationManager,
                customerBrokerSaleNegotiationManager
            );

            //Init event recorder service.
            customerBrokerUpdateServiceEventHandler = new CustomerBrokerUpdateServiceEventHandler(customerBrokerUpdateNegotiationTransactionDatabaseDao,eventManager);
            customerBrokerUpdateServiceEventHandler.start();

            //Init monitor Agent
            customerBrokerUpdateAgent = new CustomerBrokerUpdateAgent(
                pluginDatabaseSystem,
                logManager,
                errorManager,
                eventManager,
                pluginId,
                negotiationTransmissionManager,
                customerBrokerPurchaseNegotiation,
                customerBrokerSaleNegotiation,
                customerBrokerPurchaseNegotiationManager,
                customerBrokerSaleNegotiationManager
            );
            customerBrokerUpdateAgent.start();

            //TEST MOCK
            //CREATE CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION
//            createCustomerBrokerUpdatePurchaseNegotiationTest();

            //CREATE CUSTOMER BROKER UPDATE SALE NEGOTIATION
//            createCustomerBrokerUpdateSaleNegotiationTest();

            //CREATE CUSTOMER BROKER CANCEL PURCHASE NEGTIATION
//            createCustomerBrokerCancelPurchaseNegotiationTest();

            //CREATE CUSTOMER BROKER CANCEL SALE NEGTIATION
//            createCustomerBrokerCancelSaleNegotiationTest();

            //Startes Service
            this.serviceStatus = ServiceStatus.STARTED;
//            System.out.print("-----------------------\n CUSTOMER BROKER UPDATE: SUCCESSFUL START \n-----------------------\n");

        } catch (CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException e){
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_UPDATE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE,FermatException.wrapException(e),"Error Starting Customer Broker Update PluginRoot - Database","Unexpected Exception");
        } catch (CantStartServiceException e){
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_UPDATE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE,FermatException.wrapException(e),"Error Starting Customer Broker Update PluginRoot - EventHandler","Unexpected Exception");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_UPDATE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE,FermatException.wrapException(e),"Error Starting Customer Broker Update PluginRoot","Unexpected Exception");
        }
    }

    @Override
    public void pause() { this.serviceStatus = ServiceStatus.PAUSED; }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public FermatManager getManager() { return customerBrokerUpdateManagerImpl; }
    /*END IMPLEMENTATION Service.*/
    /*IMPLEMENTATION DatabaseManagerForDevelopers*/
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return null;
    }
    /*END IMPLEMENTATION DatabaseManagerForDevelopers*/
    /*IMPLEMENTATION LogManagerForDevelopers*/
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.NegotiationTransactionCustomerBrokerUpdatePluginRoot");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        //I will check the current values and update the LogLevel in those which is different
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            //if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
            if (NegotiationTransactionCustomerBrokerUpdatePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                NegotiationTransactionCustomerBrokerUpdatePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                NegotiationTransactionCustomerBrokerUpdatePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                NegotiationTransactionCustomerBrokerUpdatePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    public static LogLevel getLogLevelByClass(String className) {
        try{
            //sometimes the classname may be passed dynamically with an $moretext. I need to ignore whats after this.
            String[] correctedClass = className.split((Pattern.quote("$")));
            return NegotiationTransactionCustomerBrokerUpdatePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            //If I couldn't get the correct logging level, then I will set it to minimal.
            return DEFAULT_LOG_LEVEL;
        }
    }
    /*END IMPLEMENTATION LogManagerForDevelopers*/

    /*PRIVATE METHOD*/
    private void initializeDb() throws CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException {
        try {

            dataBase = this.pluginDatabaseSystem.openDatabase(this.pluginId, CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            try {
                CustomerBrokerUpdateNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerUpdateNegotiationTransactionDatabaseFactory(pluginDatabaseSystem);
                dataBase = databaseFactory.createDatabase(pluginId, CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException f) {
                errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_UPDATE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,f);
                throw new CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_UPDATE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,z);
                throw new CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_UPDATE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_UPDATE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }
    /*END PRIVATE METHOD*/

    /*TEST METHOD*/

    //TEST CREATE CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION
    private void createCustomerBrokerUpdatePurchaseNegotiationTest() {

        try {

            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - PLUGINROOT - PURCHASE NEGOTIATION TEST: createCustomerBrokerUpdatePurchaseNegotiationTranasction() ****\n");

            CustomerBrokerPurchaseNegotiation negotiationMock = purchaseNegotiationMockTest();
            System.out.print("\n\n**** 1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - PLUGINROOT - PURCHASE NEGOTIATION ****\n" +
                            "\n------------------------------- NEGOTIATION PURCHASE MOCK -------------------------------" +
                            "\n*CustomerPublicKey = " + negotiationMock.getCustomerPublicKey() +
                            "\n*BrokerPublicKey = " + negotiationMock.getBrokerPublicKey()
            );

            //NEGOTIATION XML TEST
            String negotiationMockXML = XMLParser.parseObject(negotiationMock);
            System.out.print("\n\n --- NegotiationMockXML = " + negotiationMockXML);
            CustomerBrokerPurchaseNegotiation purchaseNegotiationXML = new NegotiationPurchaseRecord();
            purchaseNegotiationXML = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationMockXML, purchaseNegotiationXML);
            System.out.print("\n\n --- Negotiation Mock XML Date" +
                            "\n- NegotiationId = " + purchaseNegotiationXML.getNegotiationId() +
                            "\n- CustomerPublicKey = " + purchaseNegotiationXML.getCustomerPublicKey() +
                            "\n- BrokerPublicKey = " + purchaseNegotiationXML.getCustomerPublicKey()
            );

            //CREATE CUSTOMER BROKER UPDATE NEGOTIATION.
            customerBrokerUpdateManagerImpl.createCustomerBrokerUpdatePurchaseNegotiationTranasction(negotiationMock);

            //GET TRANSACTION OF NEGOTIATION
            System.out.print("\n\n\n\n------------------------------- NEGOTIATION TRANSACTION -------------------------------");
            CustomerBrokerUpdate ListNegotiation = customerBrokerUpdateNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerUpdateNegotiationTranasctionFromNegotiationId(negotiationMock.getNegotiationId());
            System.out.print("\n\n --- Negotiation Transaction Date" +
                            "\n- NegotiationId = " + ListNegotiation.getNegotiationId() +
                            "\n- TransactionId = " + ListNegotiation.getTransactionId() +
                            "\n- CustomerPublicKey = " + ListNegotiation.getPublicKeyCustomer() +
                            "\n- BrokerPublicKey = " + ListNegotiation.getPublicKeyBroker() +
                            "\n- NegotiationType = " + ListNegotiation.getNegotiationType().getCode() +
                            "\n- StatusTransaction = " + ListNegotiation.getStatusTransaction().getCode()
            );

            //GET NEGOTIATION OF XML
            System.out.print("\n\n\n\n------------------------------- NEGOTIATION XML -------------------------------");
            if (ListNegotiation.getNegotiationXML() != null) {
                System.out.print("\n- NegotiationXML = " + ListNegotiation.getNegotiationXML());
                CustomerBrokerPurchaseNegotiation purchaseNegotiationTestXML = new NegotiationPurchaseRecord();
                purchaseNegotiationXML = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(ListNegotiation.getNegotiationXML(), purchaseNegotiationTestXML);
                if(purchaseNegotiationXML.getNegotiationId() != null) {
                    System.out.print("\n\n\n --- NegotiationXML Date" +
                                    "\n- NegotiationId = " + purchaseNegotiationXML.getNegotiationId() +
                                    "\n- CustomerPublicKey = " + purchaseNegotiationXML.getCustomerPublicKey() +
                                    "\n- BrokerPublicKey = " + purchaseNegotiationXML.getBrokerPublicKey() +
                                    "\n- Status = " + purchaseNegotiationXML.getStatus().getCode()
                    );
                }else{ System.out.print("\n\n\n --- NegotiationXML Date: purchaseNegotiationXML IS NOT INSTANCE OF NegotiationPurchaseRecord");}
            }else{ System.out.print("\n\n\n --- NegotiationXML Date IS NULL"); }

        } catch (CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException e) {
            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE. PLUGIN ROOT. ERROR CREATE CUSTOMER BROKER UPDATE. ****\n");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e){
            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE. PLUGIN ROOT. ERROR LIST CUSTOMER BROKER UPDATE NOT FOUNT. ****\n");
        }

    }

    //TEST CREATE CUSTOMER BROKER UPDATE SALE NEGOTIATION
    private void createCustomerBrokerUpdateSaleNegotiationTest() {

        try {

            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - PLUGINROOT - SALE NEGOTIATION TEST: createCustomerBrokerUpdatePurchaseNegotiationTranasction() ****\n");

            CustomerBrokerSaleNegotiation negotiationMock = saleNegotiationMockTest();
            System.out.print("\n\n**** 1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - PLUGINROOT - SALE NEGOTIATION ****\n" +
                            "\n------------------------------- NEGOTIATION SALE MOCK -------------------------------" +
                            "\n*CustomerPublicKey = " + negotiationMock.getCustomerPublicKey() +
                            "\n*BrokerPublicKey = " + negotiationMock.getBrokerPublicKey()
            );

            //NEGOTIATION XML TEST
            String negotiationMockXML = XMLParser.parseObject(negotiationMock);
            System.out.print("\n\n --- NegotiationMockXML = " + negotiationMockXML);
            CustomerBrokerSaleNegotiation saleNegotiationXML = new NegotiationSaleRecord();
            saleNegotiationXML = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationMockXML, saleNegotiationXML);
            System.out.print("\n\n --- Negotiation Mock XML Date" +
                            "\n- NegotiationId = " + saleNegotiationXML.getNegotiationId() +
                            "\n- CustomerPublicKey = " + saleNegotiationXML.getCustomerPublicKey() +
                            "\n- BrokerPublicKey = " + saleNegotiationXML.getCustomerPublicKey()
            );

            //CREATE CUSTOMER BROKER UPDATE NEGOTIATION.
            customerBrokerUpdateManagerImpl.createCustomerBrokerUpdateSaleNegotiationTranasction(negotiationMock);

            //GET TRANSACTION OF NEGOTIATION
            System.out.print("\n\n\n\n------------------------------- NEGOTIATION TRANSACTION -------------------------------");
            CustomerBrokerUpdate ListNegotiation = customerBrokerUpdateNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerUpdateNegotiationTranasctionFromNegotiationId(negotiationMock.getNegotiationId());
            System.out.print("\n\n --- Negotiation Transaction Date" +
                            "\n- NegotiationId = " + ListNegotiation.getNegotiationId() +
                            "\n- TransactionId = " + ListNegotiation.getTransactionId() +
                            "\n- CustomerPublicKey = " + ListNegotiation.getPublicKeyCustomer() +
                            "\n- BrokerPublicKey = " + ListNegotiation.getPublicKeyBroker() +
                            "\n- NegotiationType = " + ListNegotiation.getNegotiationType().getCode() +
                            "\n- StatusTransaction = " + ListNegotiation.getStatusTransaction().getCode()
            );

            //GET NEGOTIATION OF XML
            System.out.print("\n\n\n\n------------------------------- NEGOTIATION XML -------------------------------");
            if (ListNegotiation.getNegotiationXML() != null) {
                System.out.print("\n- NegotiationXML = " + ListNegotiation.getNegotiationXML());
                CustomerBrokerSaleNegotiation saleNegotiationTestXML = new NegotiationSaleRecord();
                saleNegotiationXML = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(ListNegotiation.getNegotiationXML(), saleNegotiationTestXML);
                if(saleNegotiationXML.getNegotiationId() != null) {
                    System.out.print("\n\n\n --- NegotiationXML Date" +
                                    "\n- NegotiationId = " + saleNegotiationXML.getNegotiationId() +
                                    "\n- CustomerPublicKey = " + saleNegotiationXML.getCustomerPublicKey() +
                                    "\n- BrokerPublicKey = " + saleNegotiationXML.getBrokerPublicKey() +
                                    "\n- Status = " + saleNegotiationXML.getStatus().getCode()
                    );
                }else{ System.out.print("\n\n\n --- NegotiationXML Date: saleNegotiationXML IS NOT INSTANCE OF NegotiationPurchaseRecord");}
            }else{ System.out.print("\n\n\n --- NegotiationXML Date IS NULL"); }

        } catch (CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException e) {
            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE. PLUGIN ROOT. ERROR CREATE CUSTOMER BROKER UPDATE. ****\n");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e){
            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE. PLUGIN ROOT. ERROR LIST CUSTOMER BROKER UPDATE NOT FOUNT. ****\n");
        }

    }


    //TEST CREATE CUSTOMER BROKER CANCEL PURCHASE NEGOTIATION
    private void createCustomerBrokerCancelPurchaseNegotiationTest() {

        try {

            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - PLUGINROOT - PURCHASE NEGOTIATION TEST: createCustomerBrokerUpdatePurchaseNegotiationTranasction() ****\n");

            CustomerBrokerPurchaseNegotiation negotiationMock = purchaseNegotiationMockTest();
            System.out.print("\n\n**** 1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - PLUGINROOT - PURCHASE NEGOTIATION ****\n" +
                            "\n------------------------------- NEGOTIATION PURCHASE MOCK -------------------------------" +
                            "\n*CustomerPublicKey = " + negotiationMock.getCustomerPublicKey() +
                            "\n*BrokerPublicKey = " + negotiationMock.getBrokerPublicKey()
            );

            //NEGOTIATION XML TEST
            String negotiationMockXML = XMLParser.parseObject(negotiationMock);
            System.out.print("\n\n --- NegotiationMockXML = " + negotiationMockXML);
            CustomerBrokerPurchaseNegotiation purchaseNegotiationXML = new NegotiationPurchaseRecord();
            purchaseNegotiationXML = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationMockXML, purchaseNegotiationXML);
            System.out.print("\n\n --- Negotiation Mock XML Date" +
                            "\n- NegotiationId = " + purchaseNegotiationXML.getNegotiationId() +
                            "\n- CustomerPublicKey = " + purchaseNegotiationXML.getCustomerPublicKey() +
                            "\n- BrokerPublicKey = " + purchaseNegotiationXML.getCustomerPublicKey()
            );

            //CREATE CUSTOMER BROKER UPDATE NEGOTIATION.
            customerBrokerUpdateManagerImpl.cancelNegotiation(negotiationMock);

            //GET TRANSACTION OF NEGOTIATION
            System.out.print("\n\n\n\n------------------------------- NEGOTIATION TRANSACTION -------------------------------");
            CustomerBrokerUpdate ListNegotiation = customerBrokerUpdateNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerUpdateNegotiationTranasctionFromNegotiationId(negotiationMock.getNegotiationId());
            System.out.print("\n\n --- Negotiation Transaction Date" +
                            "\n- NegotiationId = " + ListNegotiation.getNegotiationId() +
                            "\n- TransactionId = " + ListNegotiation.getTransactionId() +
                            "\n- CustomerPublicKey = " + ListNegotiation.getPublicKeyCustomer() +
                            "\n- BrokerPublicKey = " + ListNegotiation.getPublicKeyBroker() +
                            "\n- NegotiationType = " + ListNegotiation.getNegotiationType().getCode() +
                            "\n- StatusTransaction = " + ListNegotiation.getStatusTransaction().getCode()
            );

            //GET NEGOTIATION OF XML
            System.out.print("\n\n\n\n------------------------------- NEGOTIATION XML -------------------------------");
            if (ListNegotiation.getNegotiationXML() != null) {
                System.out.print("\n- NegotiationXML = " + ListNegotiation.getNegotiationXML());
                CustomerBrokerPurchaseNegotiation purchaseNegotiationTestXML = new NegotiationPurchaseRecord();
                purchaseNegotiationXML = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(ListNegotiation.getNegotiationXML(), purchaseNegotiationTestXML);
                if(purchaseNegotiationXML.getNegotiationId() != null) {
                    System.out.print("\n\n\n --- NegotiationXML Date" +
                                    "\n- NegotiationId = " + purchaseNegotiationXML.getNegotiationId() +
                                    "\n- CustomerPublicKey = " + purchaseNegotiationXML.getCustomerPublicKey() +
                                    "\n- BrokerPublicKey = " + purchaseNegotiationXML.getBrokerPublicKey() +
                                    "\n- Status = " + purchaseNegotiationXML.getStatus().getCode()
                    );
                }else{ System.out.print("\n\n\n --- NegotiationXML Date: purchaseNegotiationXML IS NOT INSTANCE OF NegotiationPurchaseRecord");}
            }else{ System.out.print("\n\n\n --- NegotiationXML Date IS NULL"); }

        } catch (CantCancelNegotiationException e) {
            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE. PLUGIN ROOT. ERROR CREATE CUSTOMER BROKER UPDATE. ****\n");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e){
            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE. PLUGIN ROOT. ERROR LIST CUSTOMER BROKER UPDATE NOT FOUNT. ****\n");
        }

    }

    //TEST CREATE CUSTOMER BROKER CANCEL SALE NEGOTIATION
    private void createCustomerBrokerCancelSaleNegotiationTest() {

        try {

            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CANCEL - PLUGINROOT - SALE NEGOTIATION TEST: createCustomerBrokerUpdatePurchaseNegotiationTranasction() ****\n");

            CustomerBrokerSaleNegotiation negotiationMock = saleNegotiationMockTest();
            System.out.print("\n\n**** 1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CANCEL - PLUGINROOT - SALE NEGOTIATION ****\n" +
                            "\n------------------------------- NEGOTIATION SALE MOCK -------------------------------" +
                            "\n*CustomerPublicKey = " + negotiationMock.getCustomerPublicKey() +
                            "\n*BrokerPublicKey = " + negotiationMock.getBrokerPublicKey()
            );

            //NEGOTIATION XML TEST
            String negotiationMockXML = XMLParser.parseObject(negotiationMock);
            System.out.print("\n\n --- NegotiationMockXML = " + negotiationMockXML);
            CustomerBrokerSaleNegotiation saleNegotiationXML = new NegotiationSaleRecord();
            saleNegotiationXML = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationMockXML, saleNegotiationXML);
            System.out.print("\n\n --- Negotiation Mock XML Date" +
                            "\n- NegotiationId = " + saleNegotiationXML.getNegotiationId() +
                            "\n- CustomerPublicKey = " + saleNegotiationXML.getCustomerPublicKey() +
                            "\n- BrokerPublicKey = " + saleNegotiationXML.getCustomerPublicKey()
            );

            //CREATE CUSTOMER BROKER UPDATE NEGOTIATION.
            customerBrokerUpdateManagerImpl.createCustomerBrokerUpdateSaleNegotiationTranasction(negotiationMock);

            //GET TRANSACTION OF NEGOTIATION
            System.out.print("\n\n\n\n------------------------------- NEGOTIATION TRANSACTION -------------------------------");
            CustomerBrokerUpdate ListNegotiation = customerBrokerUpdateNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerUpdateNegotiationTranasctionFromNegotiationId(negotiationMock.getNegotiationId());
            System.out.print("\n\n --- Negotiation Transaction Date" +
                            "\n- NegotiationId = " + ListNegotiation.getNegotiationId() +
                            "\n- TransactionId = " + ListNegotiation.getTransactionId() +
                            "\n- CustomerPublicKey = " + ListNegotiation.getPublicKeyCustomer() +
                            "\n- BrokerPublicKey = " + ListNegotiation.getPublicKeyBroker() +
                            "\n- NegotiationType = " + ListNegotiation.getNegotiationType().getCode() +
                            "\n- StatusTransaction = " + ListNegotiation.getStatusTransaction().getCode()
            );

            //GET NEGOTIATION OF XML
            System.out.print("\n\n\n\n------------------------------- NEGOTIATION XML -------------------------------");
            if (ListNegotiation.getNegotiationXML() != null) {
                System.out.print("\n- NegotiationXML = " + ListNegotiation.getNegotiationXML());
                CustomerBrokerSaleNegotiation saleNegotiationTestXML = new NegotiationSaleRecord();
                saleNegotiationXML = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(ListNegotiation.getNegotiationXML(), saleNegotiationTestXML);
                if(saleNegotiationXML.getNegotiationId() != null) {
                    System.out.print("\n\n\n --- NegotiationXML Date" +
                                    "\n- NegotiationId = " + saleNegotiationXML.getNegotiationId() +
                                    "\n- CustomerPublicKey = " + saleNegotiationXML.getCustomerPublicKey() +
                                    "\n- BrokerPublicKey = " + saleNegotiationXML.getBrokerPublicKey() +
                                    "\n- Status = " + saleNegotiationXML.getStatus().getCode()
                    );
                }else{ System.out.print("\n\n\n --- NegotiationXML Date: saleNegotiationXML IS NOT INSTANCE OF NegotiationPurchaseRecord");}
            }else{ System.out.print("\n\n\n --- NegotiationXML Date IS NULL"); }

        } catch (CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException e) {
            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE. PLUGIN ROOT. ERROR CREATE CUSTOMER BROKER UPDATE. ****\n");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e){
            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE. PLUGIN ROOT. ERROR LIST CUSTOMER BROKER UPDATE NOT FOUNT. ****\n");
        }

    }

    //TEST GET ALL CUSTOMER BROKER UPDATE NEGOTIATION
    private void getAllCustomerBrokerUpdateNegotiationTranasctionTest() {

        try {

            System.out.print("\n**** MOCK CUSTOMER BROKER UPDATE. PURCHASE NEGOTIATION. TEST: getAllCustomerBrokerUpdateNegotiationTranasction() ****\n");
            //LIST CUSTOMER BROKER UPDATE TRANSACTION.
            List<CustomerBrokerUpdate> list = customerBrokerUpdateManagerImpl.getAllCustomerBrokerNewNegotiationTranasction();
            if (!list.isEmpty()) {

                System.out.print("\n\n\n\n------------------------------- LIST NEGOTIATION TRANSACTION -------------------------------");
                for (CustomerBrokerUpdate ListNegotiation : list) {

                    System.out.print("\n\n --- Negotiation Transaction Date" +
                                    "\n- NegotiationId = " + ListNegotiation.getNegotiationId() +
                                    "\n- TransactionId = " + ListNegotiation.getTransactionId() +
                                    "\n- CustomerPublicKey = " + ListNegotiation.getPublicKeyCustomer() +
                                    "\n- BrokerPublicKey = " + ListNegotiation.getPublicKeyBroker() +
                                    "\n- NegotiationType = " + ListNegotiation.getNegotiationType().getCode() +
                                    "\n- StatusTransaction = " + ListNegotiation.getStatusTransaction().getCode()
                    );

                    //GET NEGOTIATION OF XML
                    if (ListNegotiation.getNegotiationXML() != null) {
                        CustomerBrokerPurchaseNegotiation purchaseNegotiationXML = new NegotiationPurchaseRecord();
                        System.out.print("\n- NegotiationXML = " + ListNegotiation.getNegotiationXML());
                        purchaseNegotiationXML = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(ListNegotiation.getNegotiationXML(), purchaseNegotiationXML);
                        if (purchaseNegotiationXML.getNegotiationId() != null) {
                            System.out.print("\n\n\n --- NegotiationXML Date" +
                                            "\n- NegotiationId = " + purchaseNegotiationXML.getNegotiationId() +
                                            "\n- CustomerPublicKey" + purchaseNegotiationXML.getCustomerPublicKey() +
                                            "\n- BrokerPublicKey" + purchaseNegotiationXML.getBrokerPublicKey() +
                                            "\n- Status" + purchaseNegotiationXML.getStatus().getCode()
                            );
                        } else {
                            System.out.print("\n\n\n --- NegotiationXML Date: purchaseNegotiationXML IS NOT INSTANCE OF NegotiationPurchaseRecord");
                        }
                    } else {
                        System.out.print("\n\n\n --- NegotiationXML Date IS NULL");
                    }
                }
                System.out.print("\n\n------------------------------- END LIST NEGOTIATION TRANSACTION -------------------------------");
            } else {
                System.out.print("\n**** MOCK CUSTOMER BROKER UPDATE. PURCHASE NEGOTIATION. ERROR LIST CUSTOMER BROKER UPDATE IS EMPTY . ****\n");
            }

        } catch (CantGetListCustomerBrokerUpdateNegotiationTransactionException e){
            System.out.print("\n**** MOCK CUSTOMER BROKER UPDATE. PURCHASE NEGOTIATION. ERROR GET ALL CUSTOMER BROKER PURCHASE NEGOTIATION NOT FOUNT. ****\n");
        }
    }

    //TEST OTHERS
    private CustomerBrokerPurchaseNegotiation purchaseNegotiationMockTest(){

        Date time = new Date();
        long timestamp = time.getTime();
        UUID negotiationId                              = UUID.fromString("eac97ab3-034e-4e57-93dc-b9f4ccaf1a74");
        String              publicKeyCustomer           = "30C5580D5A807CA38771A7365FC2141A6450556D5233DD4D5D14D4D9CEE7B9715B98951C2F28F820D858898AE0CBCE7B43055AB3C506A804B793E230610E711AEA";
        String              publicKeyBroker             = "041FCC359F748B5074D5554FA4DBCCCC7981D6776E57B5465DB297775FB23DBBF064FCB11EDE1979FC6E02307E4D593A81D2347006109F40B21B969E0E290C3B84";
        long                startDataTime               = 0;
        long                negotiationExpirationDate   = timestamp;
        NegotiationStatus statusNegotiation             = NegotiationStatus.SENT_TO_BROKER;
        Collection<Clause> clauses                      = getClausesTest();
        Boolean             nearExpirationDatetime      = Boolean.FALSE;

        return new PurchaseNegotiationMock(
                negotiationId,
                publicKeyCustomer,
                publicKeyBroker,
                startDataTime,
                negotiationExpirationDate,
                statusNegotiation,
                clauses,
                nearExpirationDatetime
        );
    }

    private CustomerBrokerSaleNegotiation saleNegotiationMockTest(){

        Date time = new Date();
        long timestamp = time.getTime();
        UUID negotiationId                              = UUID.fromString("eac97ab3-034e-4e57-93dc-b9f4ccaf1a74");
        String              publicKeyCustomer           = "30C5580D5A807CA38771A7365FC2141A6450556D5233DD4D5D14D4D9CEE7B9715B98951C2F28F820D858898AE0CBCE7B43055AB3C506A804B793E230610E711AEA";
        String              publicKeyBroker             = "041FCC359F748B5074D5554FA4DBCCCC7981D6776E57B5465DB297775FB23DBBF064FCB11EDE1979FC6E02307E4D593A81D2347006109F40B21B969E0E290C3B84";
        long                startDataTime               = 0;
        long                negotiationExpirationDate   = timestamp;
        NegotiationStatus statusNegotiation             = NegotiationStatus.SENT_TO_BROKER;
        Collection<Clause> clauses                      = getClausesTest();
        Boolean             nearExpirationDatetime      = Boolean.FALSE;

        return new SaleNegotiationMock(
                negotiationId,
                publicKeyCustomer,
                publicKeyBroker,
                startDataTime,
                negotiationExpirationDate,
                statusNegotiation,
                clauses,
                nearExpirationDatetime
        );
    }

    private Collection<Clause> getClausesTest(){
        Collection<Clause> clauses = new ArrayList<>();
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY,
                CurrencyType.BANK_MONEY.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY_QUANTITY,
                "1961"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY,
                CurrencyType.BANK_MONEY.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_DATE_TIME_TO_DELIVER,
                "1000"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_CURRENCY_QUANTITY,
                "2000"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_CURRENCY,
                CurrencyType.CASH_ON_HAND_MONEY.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER,
                "100"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_PAYMENT_METHOD,
                ContractClauseType.CASH_ON_HAND.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_PAYMENT_METHOD,
                ContractClauseType.BANK_TRANSFER.getCode()));
        return clauses;
    }
    /*END TEST*/
}