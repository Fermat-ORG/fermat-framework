package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.agent.CBPTransactionAgent;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationSaleRecord;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationPurchaseRecord;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_sale.interfaces.CustomerBrokerSale;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantGetNegotiationTransactionListException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantSendCustomerBrokerUpdateConfirmationNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantSendCustomerBrokerUpdateNegotiationTransactionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 16.12.15.
 */
public class CustomerBrokerUpdateAgent implements
        CBPTransactionAgent,
        DealsWithLogger,
        DealsWithEvents,
        DealsWithErrors,
        DealsWithPluginDatabaseSystem,
        DealsWithPluginIdentity {

    private Database database;

    private Thread                                      agentThread;

    private LogManager                                  logManager;

    private EventManager                                eventManager;

    private ErrorManager                                errorManager;

    private PluginDatabaseSystem                        pluginDatabaseSystem;

    private UUID                                        pluginId;

    /*Represent the Network Service*/
    private NegotiationTransmissionManager              negotiationTransmissionManager;

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiation           customerBrokerPurchaseNegotiation;

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiationManager    customerBrokerPurchaseNegotiationManager;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiation               customerBrokerSaleNegotiation;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiationManager        customerBrokerSaleNegotiationManager;

    /*Represent the Monitor Agent*/
//    private MonitorAgentTransaction                     monitorAgentTransaction;

    public CustomerBrokerUpdateAgent(
        PluginDatabaseSystem                        pluginDatabaseSystem,
        LogManager                                  logManager,
        ErrorManager                                errorManager,
        EventManager                                eventManager,
        UUID                                        pluginId,
        NegotiationTransmissionManager              negotiationTransmissionManager,
        CustomerBrokerPurchaseNegotiation           customerBrokerPurchaseNegotiation,
        CustomerBrokerSaleNegotiation               customerBrokerSaleNegotiation,
        CustomerBrokerPurchaseNegotiationManager    customerBrokerPurchaseNegotiationManager,
        CustomerBrokerSaleNegotiationManager        customerBrokerSaleNegotiationManager
    ){
        this.pluginDatabaseSystem                       = pluginDatabaseSystem;
        this.logManager                                 = logManager;
        this.errorManager                               = errorManager;
        this.eventManager                               = eventManager;
        this.pluginId                                   = pluginId;
        this.negotiationTransmissionManager             = negotiationTransmissionManager;
        this.customerBrokerPurchaseNegotiation          = customerBrokerPurchaseNegotiation;
        this.customerBrokerSaleNegotiation              = customerBrokerSaleNegotiation;
        this.customerBrokerPurchaseNegotiationManager   = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerSaleNegotiationManager       = customerBrokerSaleNegotiationManager;
    }

    /*IMPLEMENTATION CBPTransactionAgent*/
    @Override
    public void start() throws CantStartAgentException {
/*
//        Logger LOG = Logger.getGlobal();
//        LOG.info("CUSTMER BROKER NEW AGENT STARTING...");
        monitorAgentTransaction = new MonitorAgentTransaction();

        ((DealsWithPluginDatabaseSystem) this.monitorAgentTransaction).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        ((DealsWithErrors) this.monitorAgentTransaction).setErrorManager(this.errorManager);

        try {
            ((MonitorAgentTransaction) this.monitorAgentTransaction).Initialize();
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }
*/
//        this.agentThread = new Thread(monitorAgentTransaction);
        this.agentThread.start();


    }

    @Override
    public void stop() { this.agentThread.interrupt(); }

    /*IMPLEMENTATION DealsWithErrors*/
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager=errorManager;
    }

    /*IMPLEMENTATION DealsWithEvents*/
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager=eventManager;
    }

    /*IMPLEMENTATION DealsWithLogger*/
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager=logManager;
    }

    /*IMPLEMENTATION DealsWithPluginDatabaseSystem*/
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) { this.pluginDatabaseSystem=pluginDatabaseSystem; }

    /*IMPLEMENTATION DealsWithPluginIdentity*/
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId=pluginId;
    }

    /*INNER CLASS*/
    private class MonitorAgentTransaction implements DealsWithPluginDatabaseSystem, DealsWithErrors, Runnable {

        private volatile boolean                            agentRunning;

        ErrorManager                                        errorManager;

        PluginDatabaseSystem                                pluginDatabaseSystem;

        CustomerBrokerUpdateNegotiationTransactionDatabaseDao customerBrokerUpdateNegotiationTransactionDatabaseDao;

        public final int                                    SLEEP_TIME = 5000;

        int                                                 iterationNumber = 0;

        boolean                                             threadWorking;

        /*IMPLEMENTATION DealsWithPluginIdentity*/
        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) { this.pluginDatabaseSystem=pluginDatabaseSystem; }

        /*IMPLEMENTATION DealsWithErrors*/
        @Override
        public void setErrorManager(ErrorManager errorManager) {
            this.errorManager=errorManager;
        }

        /*IMPLEMENTATION Runnable*/
        @Override
        public void run() {

        }

        /*INNER CLASS PUBLIC METHOD*/
        public void Initialize() throws CantInitializeCBPAgent {

            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.DATABASE_NAME);

            }
            catch (DatabaseNotFoundException databaseNotFoundException) {

                try {
                    CustomerBrokerUpdateNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerUpdateNegotiationTransactionDatabaseFactory(this.pluginDatabaseSystem);
                    database = databaseFactory.createDatabase(pluginId,CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException,"Customer Broker New Initialize Monitor Agent - trying to create the plugin database","Please, check the cause");
                }

            } catch (CantOpenDatabaseException exception) {
                errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,exception);
                throw new CantInitializeCBPAgent(exception,"Customer Broker New Initialize Monitor Agent - trying to open the plugin database","Please, check the cause");
            }

        }

        public void stopAgent() { agentRunning = false; }

        public void startAgent() { agentRunning = true; }

        public boolean isAgentRunning() { return agentRunning; }
        /*END INNER CLASS PUBLIC METHOD*/

        /*INNER CLASS PRIVATE METHOD*/
        private void doTheMainTask() throws
                CantSendCustomerBrokerUpdateNegotiationTransactionException,
                CantSendCustomerBrokerUpdateConfirmationNegotiationTransactionException,
                CantUpdateRecordException
        {
            try {

                customerBrokerUpdateNegotiationTransactionDatabaseDao = new CustomerBrokerUpdateNegotiationTransactionDatabaseDao(pluginDatabaseSystem, pluginId, database);

                String                              negotiationXML;
                NegotiationType                     negotiationType;
                UUID                                transactionId;
                NegotiationTransaction              negotiationTransaction;
                List<String>                        negotiationPendingToSubmitList;
                CustomerBrokerPurchaseNegotiation   purchaseNegotiation = new NegotiationPurchaseRecord();
                CustomerBrokerSaleNegotiation       saleNegotiation     = new NegotiationSaleRecord();

                //Check if exist negotiation for send (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_SUBMIT)
                negotiationPendingToSubmitList = customerBrokerUpdateNegotiationTransactionDatabaseDao.getPendingToSubmitNegotiation();
                if(!negotiationPendingToSubmitList.isEmpty()) {
                    for (String negotiationToSubmit : negotiationPendingToSubmitList) {

                        negotiationTransaction  = customerBrokerUpdateNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerUpdateNegotiationTranasctionFromNegotiationId(negotiationToSubmit);
                        transactionId           = negotiationTransaction.getTransactionId();
                        negotiationXML          = negotiationTransaction.getNegotiationXML();
                        negotiationType         = NegotiationType.PURCHASE;

                        switch (negotiationType){
                            case PURCHASE:
                                purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML,purchaseNegotiation);
                                //SEND NEGOTIATION TO BROKER
                                negotiationTransmissionManager.sendNegotiatioToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_UPDATE);
                                //CHANGE STATUS PURCHASE NEGOTIATION
                                customerBrokerPurchaseNegotiationManager.waitForBroker(purchaseNegotiation);
                                break;
                            case SALE:
                                saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML,saleNegotiation);
                                //SEND NEGOTIATION TO CUSTOMER
                                negotiationTransmissionManager.sendNegotiatioToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_UPDATE);
                                //CHANGE STATUS SALE NEGOTIATION
                                customerBrokerSaleNegotiationManager.waitForBroker(saleNegotiation);
                                break;
                        }

                        //Update the Negotiation Transaction
                        customerBrokerUpdateNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerUpdateNegotiationTranasction(transactionId, NegotiationTransactionStatus.SENDING_NEGOTIATION);
                    }
                }

                //Check if exist negotiation for confirm (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_CONFIRMATION)
                negotiationPendingToSubmitList = customerBrokerUpdateNegotiationTransactionDatabaseDao.getPendingToConfirmtNegotiation();
                if(!negotiationPendingToSubmitList.isEmpty()) {
                    for (String negotiationToSubmit : negotiationPendingToSubmitList) {

                        negotiationTransaction  = customerBrokerUpdateNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerUpdateNegotiationTranasctionFromNegotiationId(negotiationToSubmit);
                        transactionId           = negotiationTransaction.getTransactionId();
                        negotiationXML          = negotiationTransaction.getNegotiationXML();
                        negotiationType         = NegotiationType.PURCHASE;

                        switch (negotiationType){
                            case PURCHASE:
                                purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML,purchaseNegotiation);
                                //SEND NEGOTIATION TO BROKER
                                negotiationTransmissionManager.sendNegotiatioToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_UPDATE);
                                //CHANGE STATUS PURCHASE NEGOTIATION
//                                customerBrokerPurchaseNegotiationManager.waitForBroker(purchaseNegotiation);
                                break;
                            case SALE:
                                saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML,saleNegotiation);
                                //SEND NEGOTIATION TO CUSTOMER
                                negotiationTransmissionManager.sendNegotiatioToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_UPDATE);
                                //CHANGE STATUS SALE NEGOTIATION
//                                customerBrokerSaleNegotiationManager.waitForBroker(saleNegotiation);
                                break;
                        }

                        //Update the Negotiation Transaction
                        customerBrokerUpdateNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerUpdateNegotiationTranasction(transactionId, NegotiationTransactionStatus.CONFIRM_NEGOTIATION);
                    }
                }

                //Check if pending events
                List<String> pendingEventsIdList=customerBrokerUpdateNegotiationTransactionDatabaseDao.getPendingEvents();
                for(String eventId : pendingEventsIdList){
                    checkPendingEvent(eventId);
                }

            } catch (CantGetNegotiationTransactionListException e) {
                throw new CantSendCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e),"Sending Negotiation","Cannot get the Negotiation list from database");
            } catch (Exception e) {
                throw new CantSendCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e),"Sending Negotiation","UNKNOWN FAILURE.");
            }


        }

        private void raiseNewContractEvent(){
            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.NEW_NEGOTIATION_TRANSACTION_NEW);
            NewNegotiationTransactionNewEvent newNegotiationTransactionNewEvent = (NewNegotiationTransactionNewEvent) fermatEvent;
            newNegotiationTransactionNewEvent.setSource(EventSource.NEGOTIATION_TRANSACTION_UPDATE);
            eventManager.raiseEvent(newNegotiationTransactionNewEvent);
        }

        private void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {

        }
        /*END INNER CLASS PRIVATE METHOD*/
    }
    /*END INNER CLASS*/
}
