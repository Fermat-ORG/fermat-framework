package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
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
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_cbp_api.all_definition.agent.CBPTransactionAgent;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationPurchaseRecord;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationSaleRecord;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.NegotiationTransactionCustomerBrokerClosePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantGetNegotiationTransactionListException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantSendCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 22.12.15.
 */
public class CustomerBrokerCloseAgent  implements
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

    /*Represent Address Book Manager*/
    private CryptoAddressBookManager                    cryptoAddressBookManager;

    /*Represent Vault Manager*/
    private CryptoVaultManager                          cryptoVaultManager;

    /*Represent Wallet Manager*/
    private WalletManagerManager                        walletManagerManager;

    public CustomerBrokerCloseAgent(
        PluginDatabaseSystem                        pluginDatabaseSystem,
        LogManager                                  logManager,
        ErrorManager                                errorManager,
        EventManager                                eventManager,
        UUID                                        pluginId,
        NegotiationTransmissionManager              negotiationTransmissionManager,
        CustomerBrokerPurchaseNegotiation           customerBrokerPurchaseNegotiation,
        CustomerBrokerSaleNegotiation               customerBrokerSaleNegotiation,
        CustomerBrokerPurchaseNegotiationManager    customerBrokerPurchaseNegotiationManager,
        CustomerBrokerSaleNegotiationManager        customerBrokerSaleNegotiationManager,
        CryptoAddressBookManager                    cryptoAddressBookManager,
        CryptoVaultManager                          cryptoVaultManager,
        WalletManagerManager                        walletManagerManager
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
        this.cryptoAddressBookManager                   = cryptoAddressBookManager;
        this.cryptoVaultManager                         = cryptoVaultManager;
        this.walletManagerManager                       = walletManagerManager;
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
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_CLOSE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }

        this.agentThread = new Thread(monitorAgentTransaction);
        this.agentThread.start();
//        System.out.print("-----------------------\n CUSTOMER BROKER CLOSE AGENT: SUCCESSFUL START \n-----------------------\n");
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

        private CustomerBrokerCloseSaleNegotiationTransaction       customerBrokerCloseSaleNegotiationTransaction;

        private CustomerBrokerClosePurchaseNegotiationTransaction   customerBrokerClosePurchaseNegotiationTransaction;

        private volatile boolean                                    agentRunning;

        ErrorManager                                                errorManager;

        PluginDatabaseSystem                                        pluginDatabaseSystem;

        CustomerBrokerCloseNegotiationTransactionDatabaseDao        customerBrokerCloseNegotiationTransactionDatabaseDao;

        boolean                                                     threadWorking;

        public final int                                            SLEEP_TIME = 5000;

        int                                                         iterationNumber = 0;

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
            logManager.log(NegotiationTransactionCustomerBrokerClosePluginRoot.getLogLevelByClass(this.getClass().getName()),"Customer Broker Close Monitor Agent: running...", null, null);

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

                    logManager.log(NegotiationTransactionCustomerBrokerClosePluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();

                } catch (CantSendCustomerBrokerCloseNegotiationTransactionException | CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException | CantUpdateRecordException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_CLOSE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        }

        /*INNER CLASS PUBLIC METHOD*/
        public void Initialize() throws CantInitializeCBPAgent {

            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerCloseNegotiationTransactionDatabaseConstants.DATABASE_NAME);

            }
            catch (DatabaseNotFoundException databaseNotFoundException) {

                try {
                    CustomerBrokerCloseNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerCloseNegotiationTransactionDatabaseFactory(this.pluginDatabaseSystem);
                    database = databaseFactory.createDatabase(pluginId,CustomerBrokerCloseNegotiationTransactionDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_CLOSE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException,"Customer Broker Close Initialize Monitor Agent - trying to create the plugin database","Please, check the cause");
                }

            } catch (CantOpenDatabaseException exception) {
                errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_CLOSE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,exception);
                throw new CantInitializeCBPAgent(exception,"Customer Broker Close Initialize Monitor Agent - trying to open the plugin database","Please, check the cause");
            }

        }

        public void stopAgent() { agentRunning = false; }

        public void startAgent() { agentRunning = true; }

        public boolean isAgentRunning() { return agentRunning; }
        /*END INNER CLASS PUBLIC METHOD*/

        /*INNER CLASS PRIVATE METHOD*/
        private void doTheMainTask() throws
            CantSendCustomerBrokerCloseNegotiationTransactionException,
            CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException,
            CantUpdateRecordException
        {

            try {

                customerBrokerCloseNegotiationTransactionDatabaseDao = new CustomerBrokerCloseNegotiationTransactionDatabaseDao(pluginDatabaseSystem, pluginId, database);

                String                              negotiationXML;
                NegotiationType                     negotiationType;
                UUID                                transactionId;
                NegotiationTransaction              negotiationTransaction;
                List<String>                        negotiationPendingToSubmitList;
                CustomerBrokerPurchaseNegotiation   purchaseNegotiation = new NegotiationPurchaseRecord();
                CustomerBrokerSaleNegotiation       saleNegotiation     = new NegotiationSaleRecord();

                //SEND NEGOTIATION PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_SUBMIT)
                negotiationPendingToSubmitList = customerBrokerCloseNegotiationTransactionDatabaseDao.getPendingToSubmitNegotiation();
                if(!negotiationPendingToSubmitList.isEmpty()){
                    for(String negotiationToSubmit: negotiationPendingToSubmitList){

                        System.out.println("Customer Broker Close - Negotiation to submit:\n"+negotiationToSubmit);

                        transactionId          = customerBrokerCloseNegotiationTransactionDatabaseDao.getTransactionId(negotiationToSubmit);
                        negotiationTransaction = customerBrokerCloseNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerCloseNegotiationTranasction(transactionId);
                        negotiationXML         = negotiationTransaction.getNegotiationXML();
                        negotiationType        = negotiationTransaction.getNegotiationType();

                        switch (negotiationType){
                            case PURCHASE:
                                purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML,purchaseNegotiation);
                                //SEND NEGOTIATION TO BROKER
                                negotiationTransmissionManager.sendNegotiatioToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_CLOSE);
                                //CHANGE STATUS PURCHASE NEGOTIATION. SEND_TO_BROKER: send negotiation to broker, waiting confirm
                                customerBrokerPurchaseNegotiationManager.sendToBroker(purchaseNegotiation);
                                break;
                            case SALE:
                                saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML,saleNegotiation);
                                //SEND NEGOTIATION TO CUSTOMER
                                negotiationTransmissionManager.sendNegotiatioToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_CLOSE);
                                //CHANGE STATUS PURCHASE NEGOTIATION. SEND_TO_CUSTOMER: send negotiation to customer, waiting confirm
                                customerBrokerSaleNegotiationManager.sendToBroker(saleNegotiation);
                                break;
                        }

                        //Update the Negotiation Transaction
                        customerBrokerCloseNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerCloseNegotiationTranasction(transactionId, NegotiationTransactionStatus.SENDING_NEGOTIATION);

                    }
                }

                //SEND CONFIRM PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_CONFIRMATION)
                negotiationPendingToSubmitList = customerBrokerCloseNegotiationTransactionDatabaseDao.getPendingToConfirmtNegotiation();
                if(!negotiationPendingToSubmitList.isEmpty()){
                    for(String negotiationToSubmit: negotiationPendingToSubmitList) {

                        System.out.println("Customer Broker Close - Negotiation to submit:\n" + negotiationToSubmit);

                        transactionId           = customerBrokerCloseNegotiationTransactionDatabaseDao.getTransactionId(negotiationToSubmit);
                        negotiationTransaction  = customerBrokerCloseNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerCloseNegotiationTranasction(transactionId);
                        negotiationType         = negotiationTransaction.getNegotiationType();

                        switch (negotiationType) {
                            case PURCHASE:
                                //SEND CONFIRM NEGOTIATION TO BROKER
                                negotiationTransmissionManager.sendConfirmNegotiatioToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_CLOSE);
                                break;
                            case SALE:
                                //SEND NEGOTIATION TO CUSTOMER
                                negotiationTransmissionManager.sendConfirmNegotiatioToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_CLOSE);
                                break;
                        }

                        //Update the Negotiation Transaction
                        customerBrokerCloseNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerCloseNegotiationTranasction(transactionId, NegotiationTransactionStatus.CONFIRM_NEGOTIATION);
                    }
                }

                //PROCES PENDING EVENT
                List<String> pendingEventsIdList=customerBrokerCloseNegotiationTransactionDatabaseDao.getPendingEvents();
                for(String eventId : pendingEventsIdList){
                    checkPendingEvent(eventId);
                }

            } catch (CantSendNegotiationToCryptoBrokerException e) {
                throw new CantSendCustomerBrokerCloseNegotiationTransactionException(CantSendCustomerBrokerCloseNegotiationTransactionException.DEFAULT_MESSAGE,e,"Sending Purchase Negotiation","Error in Negotiation Transmission Network Service");
            } catch (CantSendNegotiationToCryptoCustomerException e) {
                throw new CantSendCustomerBrokerCloseNegotiationTransactionException(CantSendCustomerBrokerCloseNegotiationTransactionException.DEFAULT_MESSAGE,e,"Sending Sale Negotiation","Error in Negotiation Transmission Network Service");
            } catch (CantSendConfirmToCryptoBrokerException e) {
                throw new CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException(CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Confirm Purchase Negotiation", "Error in Negotiation Transmission Network Service");
            } catch (CantSendConfirmToCryptoCustomerException e){
                throw new CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException(CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Confirm Sale Negotiation", "Error in Negotiation Transmission Network Service");
            } catch (CantGetNegotiationTransactionListException e) {
                throw new CantSendCustomerBrokerCloseNegotiationTransactionException(CantSendCustomerBrokerCloseNegotiationTransactionException.DEFAULT_MESSAGE, e,"Sending Negotiation","Cannot get the Negotiation list from database");
            } catch (Exception e) {
                throw new CantSendCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e),"Sending Negotiation","UNKNOWN FAILURE.");
            }
        }

        //CHECK PENDING EVEN
        private void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {

            try {
                UUID                                transactionId;
                UUID                                transmissionId;
                NegotiationTransmission             negotiationTransmission;
                NegotiationTransaction              negotiationTransaction;
                NegotiationType                     negotiationType;
                String                              negotiationXML;
                CustomerBrokerPurchaseNegotiation   purchaseNegotiation = new NegotiationPurchaseRecord();
                CustomerBrokerSaleNegotiation       saleNegotiation     = new NegotiationSaleRecord();

                String eventTypeCode = customerBrokerCloseNegotiationTransactionDatabaseDao.getEventType(eventId);

                //EVENT - RECEIVE NEGOTIATION
                if (eventTypeCode.equals(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_CLOSE.getCode())) {
                    List<Transaction<NegotiationTransmission>> pendingTransactionList = negotiationTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<NegotiationTransmission> record : pendingTransactionList) {

                        negotiationTransmission = record.getInformation();
                        negotiationXML          = negotiationTransmission.getNegotiationXML();
                        transactionId           = negotiationTransmission.getTransactionId();
                        negotiationType         = negotiationTransmission.getNegotiationType();

                        if (negotiationXML != null) {

                            switch (negotiationType) {
                                case PURCHASE:
                                    //CLOSE PURCHASE NEGOTIATION
                                    purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                                    customerBrokerClosePurchaseNegotiationTransaction = new CustomerBrokerClosePurchaseNegotiationTransaction(
                                            customerBrokerPurchaseNegotiationManager,
                                            customerBrokerCloseNegotiationTransactionDatabaseDao,
                                            cryptoAddressBookManager,
                                            cryptoVaultManager,
                                            walletManagerManager
                                    );
                                    customerBrokerClosePurchaseNegotiationTransaction.receivePurchaseNegotiationTranasction(transactionId, purchaseNegotiation);
                                    break;
                                case SALE:
                                    //CLOSE SALE NEGOTIATION
                                    saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);
                                    customerBrokerCloseSaleNegotiationTransaction = new CustomerBrokerCloseSaleNegotiationTransaction(
                                            customerBrokerSaleNegotiationManager,
                                            customerBrokerCloseNegotiationTransactionDatabaseDao,
                                            cryptoAddressBookManager,
                                            cryptoVaultManager,
                                            walletManagerManager
                                    );
                                    customerBrokerCloseSaleNegotiationTransaction.receiveSaleNegotiationTranasction(transactionId, saleNegotiation);
                                    break;
                            }

                            //NOTIFIED EVENT
                            customerBrokerCloseNegotiationTransactionDatabaseDao.updateEventTansactionStatus(transactionId, EventStatus.NOTIFIED);

                        }

                    }
                }

                //EVENT - RECEIVE CONFIRM NEGOTIATION
                if (eventTypeCode.equals(EventType.INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_CLOSE.getCode())) {
                    List<Transaction<NegotiationTransmission>> pendingTransactionList = negotiationTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<NegotiationTransmission> record : pendingTransactionList) {

                        negotiationTransmission = record.getInformation();
                        negotiationXML          = negotiationTransmission.getNegotiationXML();
                        transmissionId          = negotiationTransmission.getTransmissionId();
                        transactionId           = negotiationTransmission.getTransactionId();
                        negotiationType         = negotiationTransmission.getNegotiationType();
                        negotiationTransaction  = customerBrokerCloseNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerCloseNegotiationTranasction(transactionId);

                        if (negotiationTransaction.getStatusTransaction().equals(NegotiationTransactionStatus.PENDING_CONFIRMATION.getCode())) {

                            if (negotiationXML != null) {
                                switch (negotiationType) {
                                    case PURCHASE:
                                        //UPDATE NEGOTIATION IF PAYMENT IS CRYPTO CURRENCY
                                        purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                                        customerBrokerClosePurchaseNegotiationTransaction = new CustomerBrokerClosePurchaseNegotiationTransaction(
                                                customerBrokerPurchaseNegotiationManager,
                                                customerBrokerCloseNegotiationTransactionDatabaseDao,
                                                cryptoAddressBookManager,
                                                cryptoVaultManager,
                                                walletManagerManager
                                        );
                                        customerBrokerClosePurchaseNegotiationTransaction.receivePurchaseConfirm(purchaseNegotiation);
                                        break;
                                    case SALE:
                                        //UPDATE NEGOTIATION IF MERCHANDISE IS CRYPTO CURRENCY
                                        saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);
                                        customerBrokerCloseSaleNegotiationTransaction = new CustomerBrokerCloseSaleNegotiationTransaction(
                                                customerBrokerSaleNegotiationManager,
                                                customerBrokerCloseNegotiationTransactionDatabaseDao,
                                                cryptoAddressBookManager,
                                                cryptoVaultManager,
                                                walletManagerManager
                                        );
                                        customerBrokerCloseSaleNegotiationTransaction.receiveSaleConfirm(saleNegotiation);
                                        break;
                                }
                            }

                            //CONFIRM TRANSACTION
                            customerBrokerCloseNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerCloseNegotiationTranasction(transactionId, NegotiationTransactionStatus.CONFIRM_NEGOTIATION);
                            //NOTIFIED EVENT
                            customerBrokerCloseNegotiationTransactionDatabaseDao.updateEventTansactionStatus(transactionId, EventStatus.NOTIFIED);
                            //CONFIRM TRANSMISSION
                            negotiationTransmissionManager.confirmReception(transmissionId);

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}