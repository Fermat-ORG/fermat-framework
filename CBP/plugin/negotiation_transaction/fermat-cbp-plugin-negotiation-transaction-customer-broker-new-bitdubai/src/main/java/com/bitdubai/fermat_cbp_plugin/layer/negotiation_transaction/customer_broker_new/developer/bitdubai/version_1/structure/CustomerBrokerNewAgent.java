package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
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
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.events.NewContractOpened;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.events.NewNegotiationTransactionNewEvent;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantSendConfirmationNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantSendCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.NegotiationPurchaseRecord;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.NegotiationSaleRecord;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.exceptions.CantConfirmNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.exceptions.CantSendNegotiationToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.exceptions.CantSendNegotiationToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.NegotiationTransactionCustomerBrokerNewPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantGetNegotiationTransactionListException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Yordin Alayn on 08.12.15.
 * Based On OpenContractMonitorAgent Create by Manuel Perez.
 */
public class CustomerBrokerNewAgent implements
        CBPTransactionAgent,
        DealsWithLogger,
        DealsWithEvents,
        DealsWithErrors,
        DealsWithPluginDatabaseSystem,
        DealsWithPluginIdentity{

    private Database                            database;

    private Thread                              agentThread;

    private LogManager                          logManager;

    private EventManager                        eventManager;

    private ErrorManager                        errorManager;

    private PluginDatabaseSystem                pluginDatabaseSystem;

    private UUID                                pluginId;

    /*Represent the Network Service*/
    private NegotiationTransmissionManager      negotiationTransmissionManager;

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiation   customerBrokerPurchaseNegotiation;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiation       customerBrokerSaleNegotiation;


    private MonitorAgent monitorAgent;

    public CustomerBrokerNewAgent(
            PluginDatabaseSystem                pluginDatabaseSystem,
            LogManager                          logManager,
            ErrorManager                        errorManager,
            EventManager                        eventManager,
            UUID                                pluginId,
            NegotiationTransmissionManager      negotiationTransmissionManager,
            CustomerBrokerPurchaseNegotiation   customerBrokerPurchaseNegotiation,
            CustomerBrokerSaleNegotiation       customerBrokerSaleNegotiation
    ){
        this.pluginDatabaseSystem               = pluginDatabaseSystem;
        this.logManager                         = logManager;
        this.errorManager                       = errorManager;
        this.eventManager                       = eventManager;
        this.pluginId                           = pluginId;
        this.negotiationTransmissionManager     = negotiationTransmissionManager;
        this.customerBrokerPurchaseNegotiation  = customerBrokerPurchaseNegotiation;
        this.customerBrokerSaleNegotiation      = customerBrokerSaleNegotiation;
    }

    /*IMPLEMENTATION CBPTransactionAgent*/
    @Override
    public void start() throws CantStartAgentException {

        Logger LOG = Logger.getGlobal();
        LOG.info("CUSTMER BROKER NEW AGENT STARTING...");
        monitorAgent = new MonitorAgent();

        ((DealsWithPluginDatabaseSystem) this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        ((DealsWithErrors) this.monitorAgent).setErrorManager(this.errorManager);

        try {
            ((MonitorAgent) this.monitorAgent).startAgent();
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }

        this.agentThread = new Thread(monitorAgent);
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

    /*INNER CLASSES*/
    private class MonitorAgent implements Runnable {

        private volatile boolean agentRunning;
        PluginDatabaseSystem pluginDatabaseSystem;
        private CustomerBrokerNewNegotiationTransactionDatabaseDao customerBrokerNewNegotiationTransactionDatabaseDao;
        public final int SLEEP_TIME = 5000;
        int iterationNumber = 0;
        boolean threadWorking;
        public MonitorAgent() {
            startAgent();
        }
        
        /*INNER CLASS PUBLIC METHOD*/
        @Override
        public void run() {
            
            threadWorking=true;
            logManager.log(NegotiationTransactionCustomerBrokerNewPluginRoot.getLogLevelByClass(this.getClass().getName()),
                    "Customer Broker New Monitor Agent: running...", null, null);
            while(threadWorking){
                //Increase the iteration counter
                iterationNumber++;
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    return;
                }

                //now I will check if there are pending transactions to raise the event
                try {
                    logManager.log(NegotiationTransactionCustomerBrokerNewPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CantSendCustomerBrokerNewNegotiationTransactionException | CantSendConfirmationNegotiationTransactionException | CantUpdateRecordException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.OPEN_CONTRACT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }

            }
        }
        
        public void Initialize() throws CantInitializeCBPAgent {
            try {
                database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            }
            catch (DatabaseNotFoundException databaseNotFoundException) {

                Logger LOG = Logger.getGlobal();
                LOG.info("Database in Open Contract monitor agent doesn't exists");
                CustomerBrokerNewNegotiationTransactionDatabaseFactory customerBrokerNewNegotiationTransactionDatabaseFactory=new CustomerBrokerNewNegotiationTransactionDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = customerBrokerNewNegotiationTransactionDatabaseFactory.createDatabase(pluginId,pluginId.toString());
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
                CantSendCustomerBrokerNewNegotiationTransactionException,
                CantSendConfirmationNegotiationTransactionException,
                CantUpdateRecordException
        {
            try{
                
                //Check if exist in database new contracts to send
                String                  negotiationXML;
                NegotiationType         negotiationType;
                UUID                    transactionId;
                NegotiationTransaction  negotiationTransaction;
                CustomerBrokerPurchaseNegotiation  purchaseNegotiation = new NegotiationPurchaseRecord();
                CustomerBrokerSaleNegotiation      saleNegotiation     = new NegotiationSaleRecord();

                List<String> negotiationPendingToSubmitList    = customerBrokerNewNegotiationTransactionDatabaseDao.getPendingToSubmitNegotiation();
                
                if(!negotiationPendingToSubmitList.isEmpty()){
                    for(String negotiationToSubmit: negotiationPendingToSubmitList){
                        System.out.println("Customer Broker New - Negotiation to submit:\n"+negotiationToSubmit);

                        negotiationXML          = customerBrokerNewNegotiationTransactionDatabaseDao.getNegotiationXML(negotiationToSubmit);
                        negotiationType         = customerBrokerNewNegotiationTransactionDatabaseDao.getContractType(negotiationToSubmit);
                        transactionId           = customerBrokerNewNegotiationTransactionDatabaseDao.getTransactionId(negotiationToSubmit);
                        negotiationTransaction  = customerBrokerNewNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerNewNegotiationTranasction(transactionId);

                        switch (negotiationType){
                            case PURCHASE:
                                //PASAR LA NEGOCIACION, AGREGAR AL NegotiationPurchaseRecord EL ToString
                                purchaseNegotiation = (CustomerBrokerPurchaseNegotiation)XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                                negotiationTransmissionManager.sendNegotiatioToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_NEW);
                                break;
                            case SALE:
                                saleNegotiation = (CustomerBrokerSaleNegotiation)XMLParser.parseXML(negotiationXML,saleNegotiation);
                                negotiationTransmissionManager.sendNegotiatioToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_NEW);
                                break;
                        }
                        
                        //Update the NegotiationTransactionStatus
                        customerBrokerNewNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(transactionId, NegotiationTransactionStatus.SENDING_NEGOTIATION);
                    }

                }
                
                //Check if pending contract to confirm
                List<String> contractPendingToConfirmList   = customerBrokerNewNegotiationTransactionDatabaseDao.getPendingToConfirmtNegotiation();
                
                if(!contractPendingToConfirmList.isEmpty()){
                    for(String negotiationToSubmit: negotiationPendingToSubmitList){
                        
                        System.out.println("Customer Broker New - Negotiation to confirm:\n"+negotiationToSubmit);
                        
                        negotiationXML  = customerBrokerNewNegotiationTransactionDatabaseDao.getNegotiationXML(negotiationToSubmit);
                        negotiationType = customerBrokerNewNegotiationTransactionDatabaseDao.getContractType(negotiationToSubmit);
                        transactionId   = customerBrokerNewNegotiationTransactionDatabaseDao.getTransactionId(negotiationToSubmit);
                        negotiationTransaction  = customerBrokerNewNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerNewNegotiationTranasction(transactionId);

                        switch (negotiationType){
                            case PURCHASE:
                                purchaseNegotiation=(CustomerBrokerPurchaseNegotiation)XMLParser.parseXML(negotiationXML,purchaseNegotiation);
                                negotiationTransmissionManager.confirmNegotiation(negotiationTransaction,NegotiationTransactionType.CUSTOMER_BROKER_NEW);
                                break;
                            case SALE:
                                purchaseNegotiation=(CustomerBrokerPurchaseNegotiation)XMLParser.parseXML(negotiationXML,purchaseNegotiation);
                                negotiationTransmissionManager.confirmNegotiation(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_NEW);
                                break;
                        }
                        //Update the ContractTransactionStatus
                        customerBrokerNewNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(transactionId, NegotiationTransactionStatus.CONFIRM_NEGOTIATION);
                    }

                }
                
                //Check if pending events
                List<String> pendingEventsIdList=customerBrokerNewNegotiationTransactionDatabaseDao.getPendingEvents();
                for(String eventId : pendingEventsIdList){
                    checkPendingEvent(eventId);
                }
                
            } catch (CantGetNegotiationTransactionListException e) {
                throw new CantSendCustomerBrokerNewNegotiationTransactionException(CantSendCustomerBrokerNewNegotiationTransactionException.DEFAULT_MESSAGE,e,"Sending Negotiation","Cannot get the Negotiation list from database");
            } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e) {
                throw new CantUpdateRecordException(CantUpdateRecordException.DEFAULT_MESSAGE,e,"Sending Negotiation","Cannot Update State the Negotiation from database");
            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                throw new CantUpdateRecordException(CantUpdateRecordException.DEFAULT_MESSAGE,e,"Sending Negotiation","Unexpected result in database");
            } catch (CantSendNegotiationToCryptoBrokerException e) {
                throw new CantSendCustomerBrokerNewNegotiationTransactionException(CantSendCustomerBrokerNewNegotiationTransactionException.DEFAULT_MESSAGE,e,"Sending Negotiation","Error in Negotiation Transmission Network Service");
            } catch (CantSendNegotiationToCryptoCustomerException e) {
                throw new CantSendCustomerBrokerNewNegotiationTransactionException(CantSendCustomerBrokerNewNegotiationTransactionException.DEFAULT_MESSAGE,e,"Sending Negotiation","Error in Negotiation Transmission Network Service");
            } catch (CantConfirmNegotiationException e) {
                throw new CantSendConfirmationNegotiationTransactionException(CantSendConfirmationNegotiationTransactionException.DEFAULT_MESSAGE,e,"Sending Negotiation","Error in Negotiation Transmission Network Service");
            }
        }
        
        private void raiseNewContractEvent(){
            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.NEW_NEGOTIATION_TRANSACTION_NEW);
            NewNegotiationTransactionNewEvent newNegotiationTransactionNewEvent = (NewNegotiationTransactionNewEvent) fermatEvent;
            newNegotiationTransactionNewEvent.setSource(EventSource.NEGOTIATION_TRANSACTION_NEW);
            eventManager.raiseEvent(newNegotiationTransactionNewEvent);
        }

        private void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {

            try {
                UUID                            transactionId;
                UUID                            negotiationId;
                UUID                            negotiationIdFromDatabase;
                NegotiationTransmission         negotiationTransmission;
                NegotiationTransaction          negotiationTransaction;
                NegotiationTransactionStatus    negotiationTransactionStatus;

                String eventTypeCode = customerBrokerNewNegotiationTransactionDatabaseDao.getEventType(eventId);

                //EVENT TRANSACTION
                if (eventTypeCode.equals(EventType.INCOMING_NEGOTIATION_TRANSACTION.getCode())) {
                    //evaluar si es transmission o transaction
                    List<Transaction<NegotiationTransmission>> pendingTransactionList = negotiationTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for(Transaction<NegotiationTransmission> record : pendingTransactionList){
                        negotiationTransmission = record.getInformation();
                        transactionId = negotiationTransmission.getTransactionId();
                        negotiationTransaction  = customerBrokerNewNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerNewNegotiationTranasction(transactionId);
                        if(negotiationTransaction.getNegotiationXML() != null){
                            negotiationId = negotiationTransmission.getNegotiationId();
                            negotiationIdFromDatabase = negotiationTransaction.getNegotiationId();
                            if(negotiationId.equals(negotiationIdFromDatabase)){
                                negotiationTransactionStatus = NegotiationTransactionStatus.PENDING_CONFIRMATION;
                            } else{
                                negotiationTransactionStatus = NegotiationTransactionStatus.REJECTED_NEGOTIATION;
                            }
                        }
                    }
                }

                //EVENT CONFIRM NEGOTIATION
                if (eventTypeCode.equals(EventType.INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_NEGOTIATION.getCode())) {

                }

                //EVENT CONFIRM RESPONSE
                if (eventTypeCode.equals(EventType.INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_RESPONSE.getCode())) {

                }

            } catch (CantDeliverPendingTransactionsException e){
                e.printStackTrace();
            } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e){
                e.printStackTrace();
            }
        }
        
        /*END INNER CLASS PRIVATE METHOD*/

    }
    /*END INNER CLASS*/

}
