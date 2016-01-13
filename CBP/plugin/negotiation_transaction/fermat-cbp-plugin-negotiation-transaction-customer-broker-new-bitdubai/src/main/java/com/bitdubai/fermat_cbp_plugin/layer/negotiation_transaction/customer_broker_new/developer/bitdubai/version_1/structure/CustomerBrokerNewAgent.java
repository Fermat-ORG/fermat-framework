package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
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
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantNewSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantSendCustomerBrokerNewConfirmationNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantSendCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationPurchaseRecord;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationSaleRecord;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmissionManager;
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

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 08.12.15.
 * Based On OpenContractMonitorAgentTransaction Create by Manuel Perez.
 */
public class CustomerBrokerNewAgent implements
        CBPTransactionAgent,
        DealsWithLogger,
        DealsWithEvents,
        DealsWithErrors,
        DealsWithPluginDatabaseSystem,
        DealsWithPluginIdentity{

    private Database                                    database;

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
    private MonitorAgentTransaction                     monitorAgentTransaction;

    public CustomerBrokerNewAgent(
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

        this.agentThread = new Thread(monitorAgentTransaction);
        this.agentThread.start();
        System.out.print("-----------------------\n CUSTOMER BROKER NEW AGENT: SUCCESSFUL START \n-----------------------\n");

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
    private class MonitorAgentTransaction implements DealsWithPluginDatabaseSystem, DealsWithErrors, Runnable {

        private CustomerBrokerNewSaleNegotiationTransaction     customerBrokerNewSaleNegotiationTransaction;

        private volatile boolean                            agentRunning;

        ErrorManager                                        errorManager;

        PluginDatabaseSystem                                pluginDatabaseSystem;

        CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao;

        public final int                                    SLEEP_TIME = 5000;

        int                                                 iterationNumber = 0;

        boolean                                             threadWorking;

        //public MonitorAgentTransaction() { startAgent(); }

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
            
            threadWorking=true;
            logManager.log(NegotiationTransactionCustomerBrokerNewPluginRoot.getLogLevelByClass(this.getClass().getName()),"Customer Broker New Monitor Agent: running...", null, null);

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

                } catch (CantSendCustomerBrokerNewNegotiationTransactionException | CantSendCustomerBrokerNewConfirmationNegotiationTransactionException | CantUpdateRecordException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }

        }

        /*INNER CLASS PUBLIC METHOD*/
        public void Initialize() throws CantInitializeCBPAgent {

            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerNewNegotiationTransactionDatabaseConstants.DATABASE_NAME);

            }
            catch (DatabaseNotFoundException databaseNotFoundException) {

                try {
                    CustomerBrokerNewNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerNewNegotiationTransactionDatabaseFactory(this.pluginDatabaseSystem);
                    database = databaseFactory.createDatabase(pluginId,CustomerBrokerNewNegotiationTransactionDatabaseConstants.DATABASE_NAME);
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
                CantSendCustomerBrokerNewConfirmationNegotiationTransactionException,
                CantUpdateRecordException
        {
            try{

//                System.out.print("\n\n**** MOCK CUSTOMER BROKER NEW. AGENT ****\n");
                customerBrokerNewNegotiationTransactionDatabaseDao = new CustomerBrokerNewNegotiationTransactionDatabaseDao(pluginDatabaseSystem, pluginId, database);

                String                  negotiationXML;
                NegotiationType         negotiationType;
                UUID                    transactionId;
                NegotiationTransaction  negotiationTransaction;
                List<CustomerBrokerNew> negotiationPendingToSubmitList;
                CustomerBrokerPurchaseNegotiation  purchaseNegotiation = new NegotiationPurchaseRecord();
                CustomerBrokerSaleNegotiation  saleNegotiation = new NegotiationSaleRecord();

//                System.out.print("\n\n**** X) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - PENDING LIST SENDING ****\n");
                //SEND NEGOTIATION PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_SUBMIT)
                negotiationPendingToSubmitList  = customerBrokerNewNegotiationTransactionDatabaseDao.getPendingToSubmitNegotiation();
//                negotiationPendingToSubmitList  = customerBrokerNewNegotiationTransactionDatabaseDao.getAllRegisterCustomerBrokerNewNegotiationTranasction();
                if(!negotiationPendingToSubmitList.isEmpty()){
//                    for(String negotiationToSubmit: negotiationPendingToSubmitList){
                    for (CustomerBrokerNew negotiationToSubmit : negotiationPendingToSubmitList) {
//                        if (negotiationToSubmit.getStatusTransaction().getCode().equals(NegotiationTransactionStatus.PENDING_SUBMIT.getCode())) {

//                        System.out.println("\nCustomer Broker New - Negotiation to submit:\n"+negotiationToSubmit);
                        System.out.print("\n\n**** 5) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - NEGOTIATION FOR SEND transactionId: " + negotiationToSubmit.getTransactionId() + " ****\n");
//                        System.out.println("\nCustomer Broker New - Negotiation to submit:\n"+negotiationToSubmit.getNegotiationId());

//                        negotiationXML          = customerBrokerNewNegotiationTransactionDatabaseDao.getNegotiationXML(negotiationToSubmit);
//                        negotiationType         = customerBrokerNewNegotiationTransactionDatabaseDao.getNegotiationType(negotiationToSubmit);
//                        transactionId           = customerBrokerNewNegotiationTransactionDatabaseDao.getTransactionId(negotiationToSubmit);
//                        negotiationTransaction  = customerBrokerNewNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerNewNegotiationTranasction(transactionId);

                        negotiationXML = negotiationToSubmit.getNegotiationXML();
                        negotiationType = negotiationToSubmit.getNegotiationType();
                        transactionId = negotiationToSubmit.getTransactionId();
                        negotiationTransaction = negotiationToSubmit;

                        switch (negotiationType){
                            case PURCHASE:
                                purchaseNegotiation = (CustomerBrokerPurchaseNegotiation)XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                                System.out.print("\n\n**** 6) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - PURCHASE NEGOTIATION SEND negotiationId(XML): " + purchaseNegotiation.getNegotiationId() + " ****\n");
                                //SEND NEGOTIATION TO BROKER
                                negotiationTransmissionManager.sendNegotiatioToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_NEW);
                                //CHANGE STATUS PURCHASE NEGOTIATION. SEND_TO_BROKER: send negotiation to broker, waiting confirm
                                customerBrokerPurchaseNegotiationManager.sendToBroker(purchaseNegotiation);
                                break;
                        }
                        
                        //Update the Negotiation Transaction
                        customerBrokerNewNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(transactionId, NegotiationTransactionStatus.SENDING_NEGOTIATION);

                    }
                }

                //SEND CONFIRM PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_CONFIRMATION)
                negotiationPendingToSubmitList = customerBrokerNewNegotiationTransactionDatabaseDao.getPendingToConfirmtNegotiation();
                if(!negotiationPendingToSubmitList.isEmpty()){
//                    for(String negotiationToSubmit: negotiationPendingToSubmitList){
                    for (CustomerBrokerNew negotiationToSubmit : negotiationPendingToSubmitList) {

                        System.out.print("\n\n**** 22) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - CONFIRMATION FOR SEND transactionId: " + negotiationToSubmit.getTransactionId() + " ****\n");
                        
//                        negotiationXML          = customerBrokerNewNegotiationTransactionDatabaseDao.getNegotiationXML(negotiationToSubmit);
//                        negotiationType         = customerBrokerNewNegotiationTransactionDatabaseDao.getNegotiationType(negotiationToSubmit);
//                        transactionId           = customerBrokerNewNegotiationTransactionDatabaseDao.getTransactionId(negotiationToSubmit);
//                        negotiationTransaction  = customerBrokerNewNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerNewNegotiationTranasction(transactionId);

                        negotiationXML = negotiationToSubmit.getNegotiationXML();
                        negotiationType = negotiationToSubmit.getNegotiationType();
                        transactionId = negotiationToSubmit.getTransactionId();
                        negotiationTransaction = negotiationToSubmit;

                        switch (negotiationType){
                            case SALE:
                                saleNegotiation = (CustomerBrokerSaleNegotiation)XMLParser.parseXML(negotiationXML,saleNegotiation);
                                System.out.print("\n\n**** 23) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - CONFIRMATION SEND negotiationId(XML): " + negotiationToSubmit.getTransactionId() + " ****\n");
                                negotiationTransmissionManager.sendConfirmNegotiatioToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_NEW);
                                //CHANGE STATUS PURCHASE NEGOTIATION. WAITING_TO_BROKER: send confirm of creation of the negotiation in the broker, Waiting response of part of the broker
                                customerBrokerSaleNegotiationManager.waitForBroker(saleNegotiation);
                                break;
                        }

                        //Update the Negotiation Transaction
                        customerBrokerNewNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(transactionId, NegotiationTransactionStatus.CONFIRM_NEGOTIATION);
                    }

                }

                //PROCES PENDING EVENT
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
//            } catch (CantSendNegotiationToCryptoBrokerException e) {
//                throw new CantSendCustomerBrokerNewNegotiationTransactionException(CantSendCustomerBrokerNewNegotiationTransactionException.DEFAULT_MESSAGE,e,"Sending Sale Negotiation","Error in Negotiation Transmission Network Service");
//            } catch (CantSendConfirmToCryptoCustomerException e) {
//                throw new CantSendCustomerBrokerNewConfirmationNegotiationTransactionException(CantSendCustomerBrokerNewConfirmationNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Confirm Purchase Negotiation", "Error in Negotiation Transmission Network Service");
            } catch (Exception e) {
                throw new CantSendCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e),"Sending Negotiation","UNKNOWN FAILURE.");
            }
        }

        //CHECK PENDING EVEN
        private void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {

            try {

                UUID                            transactionId;
                UUID                            transmissionId;
                NegotiationTransmission         negotiationTransmission;
                NegotiationTransaction          negotiationTransaction;
                NegotiationTransactionStatus    negotiationTransactionStatus;
                NegotiationType                 negotiationType;
                String                          negotiationXML;
                CustomerBrokerSaleNegotiation   saleNegotiation     = new NegotiationSaleRecord();

                String eventTypeCode = customerBrokerNewNegotiationTransactionDatabaseDao.getEventType(eventId);

                //EVENT - RECEIVE NEGOTIATION
                if (eventTypeCode.equals(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_NEW.getCode())) {
                    System.out.print("\n\n**** 18) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - LIST EVENT PENDING ****\n");
                    List<Transaction<NegotiationTransmission>> pendingTransactionList = negotiationTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<NegotiationTransmission> record : pendingTransactionList) {

                        negotiationTransmission = record.getInformation();
                        negotiationXML  = negotiationTransmission.getNegotiationXML();
                        transmissionId  = negotiationTransmission.getTransmissionId();
                        transactionId   = negotiationTransmission.getTransactionId();
                        negotiationType = negotiationTransmission.getNegotiationType();

                        if (negotiationXML != null) {
                            switch (negotiationType) {
                                case SALE:
                                    System.out.print("\n\n**** 19) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - CREATE SALE NEGOTIATION TRANSACTION  ****\n");
                                    //CREATE SALE NEGOTIATION
                                    saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);
                                    customerBrokerNewSaleNegotiationTransaction = new CustomerBrokerNewSaleNegotiationTransaction(
                                            customerBrokerSaleNegotiationManager,
                                            customerBrokerNewNegotiationTransactionDatabaseDao
                                    );
                                    customerBrokerNewSaleNegotiationTransaction.receiveSaleNegotiationTranasction(transactionId, saleNegotiation);
                                    break;
                            }

                            //NOTIFIED EVENT
                            customerBrokerNewNegotiationTransactionDatabaseDao.updateEventTansactionStatus(transactionId, EventStatus.NOTIFIED);
                        }
                    }
                }

                //EVENT - RECEIVE CONFIRM NEGOTIATION
                if (eventTypeCode.equals(EventType.INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_NEW.getCode())) {
                    List<Transaction<NegotiationTransmission>> pendingTransactionList = negotiationTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<NegotiationTransmission> record : pendingTransactionList) {
                        negotiationTransmission = record.getInformation();
                        transactionId = negotiationTransmission.getTransactionId();
                        negotiationTransaction = customerBrokerNewNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerNewNegotiationTranasction(transactionId);
                        if (negotiationTransaction.getStatusTransaction().equals(NegotiationTransactionStatus.PENDING_CONFIRMATION.getCode())) {

                            //CONFIRM TRANSACTION
                            customerBrokerNewNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(transactionId, NegotiationTransactionStatus.CONFIRM_NEGOTIATION);
                            //NOTIFIED EVENT
                            customerBrokerNewNegotiationTransactionDatabaseDao.updateEventTansactionStatus(transactionId, EventStatus.NOTIFIED);
                            //CONFIRM TRANSMISSION
                            negotiationTransmissionManager.confirmReception(transactionId);

                        }
                    }
                }

            } catch (CantDeliverPendingTransactionsException e) {
                e.printStackTrace();
            } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e) {
                e.printStackTrace();
            } catch (CantNewSaleNegotiationTransactionException e) {
                e.printStackTrace();
            } catch (CantConfirmTransactionException e) {
                e.printStackTrace();
            } catch (CantUpdateRecordException e){
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*END INNER CLASS PRIVATE METHOD*/

    }
    /*END INNER CLASS*/

}
