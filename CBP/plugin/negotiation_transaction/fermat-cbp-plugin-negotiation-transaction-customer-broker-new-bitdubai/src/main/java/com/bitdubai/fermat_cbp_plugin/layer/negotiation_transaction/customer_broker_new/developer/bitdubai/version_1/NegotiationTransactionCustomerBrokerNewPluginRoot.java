package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationPurchaseRecord;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.Test.mocks.PurchaseNegotiationMock;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantGetListCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.event_handler.CustomerBrokerNewServiceEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure.CustomerBrokerNewAgent;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure.CustomerBrokerNewManagerImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Yordin Alayn on 23.11.15.
 */

public class NegotiationTransactionCustomerBrokerNewPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,       layer = Layers.PLATFORM_SERVICE,    addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,    layer = Layers.SYSTEM,              addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,    layer = Layers.SYSTEM,              addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,       layer = Layers.PLATFORM_SERVICE,    addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION,         plugin = Plugins.NEGOTIATION_PURCHASE)
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION,         plugin = Plugins.NEGOTIATION_SALE)
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NETWORK_SERVICE,     plugin = Plugins.NEGOTIATION_TRANSMISSION)
    private NegotiationTransmissionManager negotiationTransmissionManager;

    /*Represent the dataBase*/
    private Database                                                        dataBase;

    /*Represent DeveloperDatabaseFactory*/
    private CustomerBrokerNewNegotiationTransactionDeveloperDatabaseFactory customerBrokerNewNegotiationTransactionDeveloperDatabaseFactory;

    /*Represent CustomerBrokerNewNegotiationTransactionDatabaseDao*/
    private CustomerBrokerNewNegotiationTransactionDatabaseDao              customerBrokerNewNegotiationTransactionDatabaseDao;

    /*Represent Customer Broker New Manager*/
    private CustomerBrokerNewManagerImpl                                    customerBrokerNewManagerImpl;

    /*Represent Agent*/
    private CustomerBrokerNewAgent                                          customerBrokerNewAgent;

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiation                               customerBrokerPurchaseNegotiation;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiation                                   customerBrokerSaleNegotiation;

    /*Represent Service Event Handler*/
    private CustomerBrokerNewServiceEventHandler                            customerBrokerNewServiceEventHandler;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    public NegotiationTransactionCustomerBrokerNewPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }
    
    /*IMPLEMENTATION Service.*/
    @Override
    public void start() throws CantStartPluginException {

        try {

            //Initialize database
            initializeDb();

            //Initialize Developer Database Factory
            customerBrokerNewNegotiationTransactionDeveloperDatabaseFactory = new CustomerBrokerNewNegotiationTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            customerBrokerNewNegotiationTransactionDeveloperDatabaseFactory.initializeDatabase();

            //Initialize Dao
//            customerBrokerNewNegotiationTransactionDatabaseDao = new CustomerBrokerNewNegotiationTransactionDatabaseDao(pluginDatabaseSystem, pluginId);
            customerBrokerNewNegotiationTransactionDatabaseDao = new CustomerBrokerNewNegotiationTransactionDatabaseDao(pluginDatabaseSystem, pluginId, dataBase);

            //Initialize manager
            customerBrokerNewManagerImpl = new CustomerBrokerNewManagerImpl(
                    customerBrokerNewNegotiationTransactionDatabaseDao,
                    customerBrokerPurchaseNegotiationManager,
                    customerBrokerSaleNegotiationManager
            );

            //Init event recorder service.
            customerBrokerNewServiceEventHandler = new CustomerBrokerNewServiceEventHandler(customerBrokerNewNegotiationTransactionDatabaseDao,eventManager);
            customerBrokerNewServiceEventHandler.start();

            //Init monitor Agent
            customerBrokerNewAgent = new CustomerBrokerNewAgent(
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

            //TEST MOCK
            customerBrokerNewPurchaseNegotiationTest();

            customerBrokerNewAgent.start();

            //Startes Service
            this.serviceStatus = ServiceStatus.STARTED;
            System.out.print("-----------------------\n CUSTOMER BROKER NEW: SUCCESSFUL START \n-----------------------\n");

        } catch (CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException e){
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE,FermatException.wrapException(e),"Error Starting Customer Broker New PluginRoot - Database","Unexpected Exception");
        } catch (CantStartServiceException e){
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE,FermatException.wrapException(e),"Error Starting Customer Broker New PluginRoot - EventHandler","Unexpected Exception");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE,FermatException.wrapException(e),"Error Starting Customer Broker New PluginRoot","Unexpected Exception");
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
    public void stop() { this.serviceStatus = ServiceStatus.STOPPED; }

    @Override
    public FermatManager getManager() { return customerBrokerNewManagerImpl; }
    /*END IMPLEMENTATION Service.*/

    /*IMPLEMENTATION DatabaseManagerForDevelopers.*/
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return new CustomerBrokerNewNegotiationTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return new CustomerBrokerNewNegotiationTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try{
            return new CustomerBrokerNewNegotiationTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }
    /*END IMPLEMENTATION DatabaseManagerForDevelopers.*/

    /*IMPLEMENTATION LogManagerForDevelopers*/
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.NegotiationTransactionCustomerBrokerNewPluginRoot");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        //I will check the current values and update the LogLevel in those which is different
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            //if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
            if (NegotiationTransactionCustomerBrokerNewPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                NegotiationTransactionCustomerBrokerNewPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                NegotiationTransactionCustomerBrokerNewPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                NegotiationTransactionCustomerBrokerNewPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    public static LogLevel getLogLevelByClass(String className) {
        try{
            //sometimes the classname may be passed dynamically with an $moretext. I need to ignore whats after this.
            String[] correctedClass = className.split((Pattern.quote("$")));
            return NegotiationTransactionCustomerBrokerNewPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            //If I couldn't get the correct logging level, then I will set it to minimal.
            return DEFAULT_LOG_LEVEL;
        }
    }
    /*END IMPLEMENTATION LogManagerForDevelopers*/

    /*PUBLIC METHOD*/
    private void initializeDb() throws CantInitializeDatabaseException {
        try {

            dataBase = this.pluginDatabaseSystem.openDatabase(this.pluginId, CustomerBrokerNewNegotiationTransactionDatabaseConstants.DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            try {
                CustomerBrokerNewNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerNewNegotiationTransactionDatabaseFactory(pluginDatabaseSystem);
                dataBase = databaseFactory.createDatabase(pluginId, CustomerBrokerNewNegotiationTransactionDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException f) {
                errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,f);
                throw new CantInitializeDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,z);
                throw new CantInitializeDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
            throw new CantInitializeDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
            throw new CantInitializeDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    private void customerBrokerNewPurchaseNegotiationTest() {

        try {
            CustomerBrokerPurchaseNegotiation negotiationMock = new PurchaseNegotiationMock();
            System.out.print("\n\n**** 1) MOCK CUSTOMER BROKER NEW. PURCHASE NEGOTIATION. PLUGINROOT ****\n" +
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

            //CREATE CUSTOMER BROKER NEW NEGOTIATION.
            //customerBrokerNewManagerImpl.createCustomerBrokerNewPurchaseNegotiationTranasction(negotiationMock);

            //LIST CUSTOMER BROKER NEW TRANSACTION.
            List<CustomerBrokerNew> list = customerBrokerNewManagerImpl.getAllCustomerBrokerNewNegotiationTranasction();
            if(!list.isEmpty()) {

                System.out.print("\n\n\n\n------------------------------- LIST NEGOTIATION TRANSACTION -------------------------------");
                for (CustomerBrokerNew ListNegotiation : list) {
                    
                    System.out.print("\n\n --- Negotiation Transaction Date" +
                        "\n- NegotiationId = " + ListNegotiation.getNegotiationId() +
                        "\n- TransactionId = " + ListNegotiation.getTransactionId() +
                        "\n- CustomerPublicKey = " + ListNegotiation.getPublicKeyCustomer() +
                        "\n- BrokerPublicKey = " + ListNegotiation.getPublicKeyBroker() +
                        "\n- NegotiationType = " + ListNegotiation.getNegotiationType().getCode() +
                        "\n- StatusTransaction = " + ListNegotiation.getStatusTransaction().getCode() +
                        "\n- NegotiationXML = " + ListNegotiation.getNegotiationXML()
                    );

                    /*
                    //GET NEGOTIATION OF XML
                    if (ListNegotiation.getNegotiationXML() != null) {
                        CustomerBrokerPurchaseNegotiation purchaseNegotiationXML = new NegotiationPurchaseRecord();
                        purchaseNegotiationXML = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(ListNegotiation.getNegotiationXML(), purchaseNegotiationXML);
                        System.out.print("\n\n\n --- NegotiationXML Date" +
                                        "\n- NegotiationId = " + purchaseNegotiationXML.getNegotiationId() +
                                        "\n- CustomerPublicKey" + purchaseNegotiationXML.getCustomerPublicKey() +
                                        "\n- BrokerPublicKey" + purchaseNegotiationXML.getBrokerPublicKey() +
                                        "\n- Status" + purchaseNegotiationXML.getStatus().getCode()
                        );
                    }else{
                        System.out.print("\n\n\n --- NegotiationXML Date IS NULL");
                    }
                    */
                    //GET NEGOTIATION OF THE TRANSACTION.
                    /*
                    CustomerBrokerPurchaseNegotiation purchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(ListNegotiation.getNegotiationId());
                    System.out.print("\n\n\n --- Negotiation Date" +
                        "\n- NegotiationId = " + purchaseNegotiation.getNegotiationId() +
                        "\n- CustomerPublicKey" + purchaseNegotiation.getCustomerPublicKey() +
                        "\n- BrokerPublicKey" + purchaseNegotiation.getBrokerPublicKey() +
                        "\n- Status" + purchaseNegotiation.getStatus().getCode()
                    );*/
                }
                System.out.print("\n\n------------------------------- END LIST NEGOTIATION TRANSACTION -------------------------------");
            }else{
                System.out.print("\n**** MOCK CUSTOMER BROKER NEW. PURCHASE NEGOTIATION. ERROR LIST CUSTOMER BROKER NEW IS EMPTY . ****\n");
            }

//        } catch (CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException e) {
//            System.out.print("\n**** MOCK CUSTOMER BROKER NEW. PURCHASE NEGOTIATION. ERROR CREATE CUSTOMER BROKER NEW. ****\n");
        } catch (CantGetListCustomerBrokerNewNegotiationTransactionException e){
            System.out.print("\n**** MOCK CUSTOMER BROKER NEW. PURCHASE NEGOTIATION. ERROR LIST CUSTOMER BROKER NEW NOT FOUNT. ****\n");
//        }catch (CantGetListPurchaseNegotiationsException e){
//            System.out.print("\n**** MOCK CUSTOMER BROKER NEW. PURCHASE NEGOTIATION. ERROR GET CUSTOMER BROKER PURCHASE NEGOTIATION NOT FOUNT. ****\n");
        }

    }
    /*END PUBLIC METHOD*/

}