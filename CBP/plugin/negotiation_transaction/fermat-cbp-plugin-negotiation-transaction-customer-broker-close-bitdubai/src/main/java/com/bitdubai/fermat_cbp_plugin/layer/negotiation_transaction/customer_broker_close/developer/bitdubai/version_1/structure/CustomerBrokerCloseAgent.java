package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
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
import com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault.CryptoVaultManager;
import com.bitdubai.fermat_cbp_api.all_definition.agent.CBPTransactionAgent;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
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
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerClose;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoCustomerException;
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
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 22.12.15.
 */
public class CustomerBrokerCloseAgent implements
        CBPTransactionAgent,
        DealsWithLogger,
        DealsWithEvents,
        DealsWithPluginDatabaseSystem,
        DealsWithPluginIdentity {

    private Database database;

    private Thread agentThread;

    private LogManager logManager;

    private EventManager eventManager;

    private NegotiationTransactionCustomerBrokerClosePluginRoot pluginRoot;

    private PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    /*Represent the Network Service*/
    private NegotiationTransmissionManager negotiationTransmissionManager;

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation;

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    /*Represent the Monitor Agent*/
    private MonitorAgentTransaction monitorAgentTransaction;

    /*Represent Address Book Manager*/
    private CryptoAddressBookManager cryptoAddressBookManager;

    /*Represent Vault Manager*/
    private CryptoVaultManager cryptoVaultManager;

    /*Represent Wallet Manager*/
    private WalletManagerManager walletManagerManager;

    /*Represent the Plugins Version*/
    private PluginVersionReference pluginVersionReference;

    private IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    public CustomerBrokerCloseAgent(
            PluginDatabaseSystem pluginDatabaseSystem,
            LogManager logManager,
            NegotiationTransactionCustomerBrokerClosePluginRoot pluginRoot,
            EventManager eventManager,
            UUID pluginId,
            NegotiationTransmissionManager negotiationTransmissionManager,
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation,
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation,
            CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
            CryptoAddressBookManager cryptoAddressBookManager,
            CryptoVaultManager cryptoVaultManager,
            WalletManagerManager walletManagerManager,
            IntraWalletUserIdentityManager intraWalletUserIdentityManager
    ) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.logManager = logManager;
        this.pluginRoot = pluginRoot;
        this.eventManager = eventManager;
        this.pluginId = pluginId;
        this.negotiationTransmissionManager = negotiationTransmissionManager;
        this.customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiation;
        this.customerBrokerSaleNegotiation = customerBrokerSaleNegotiation;
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.cryptoVaultManager = cryptoVaultManager;
        this.walletManagerManager = walletManagerManager;
        this.pluginVersionReference = pluginVersionReference;
        this.intraWalletUserIdentityManager = intraWalletUserIdentityManager;
    }

    /*IMPLEMENTATION CBPTransactionAgent*/
    @Override
    public void start() throws CantStartAgentException {

//        Logger LOG = Logger.getGlobal();
//        LOG.info("CUSTMER BROKER NEW AGENT STARTING...");
        monitorAgentTransaction = new MonitorAgentTransaction();

        this.monitorAgentTransaction.setPluginDatabaseSystem(this.pluginDatabaseSystem);

        try {
            this.monitorAgentTransaction.Initialize();
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }

        this.agentThread = new Thread(monitorAgentTransaction);
        this.agentThread.start();
//        System.out.print("-----------------------\n CUSTOMER BROKER CLOSE AGENT: SUCCESSFUL START \n-----------------------\n");
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    /*IMPLEMENTATION DealsWithEvents*/
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /*IMPLEMENTATION DealsWithLogger*/
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /*IMPLEMENTATION DealsWithPluginDatabaseSystem*/
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*IMPLEMENTATION DealsWithPluginIdentity*/
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /*INNER CLASSES*/
    private class MonitorAgentTransaction implements DealsWithPluginDatabaseSystem, Runnable {

        private CustomerBrokerCloseSaleNegotiationTransaction customerBrokerCloseSaleNegotiationTransaction;

        private CustomerBrokerClosePurchaseNegotiationTransaction customerBrokerClosePurchaseNegotiationTransaction;

        private volatile boolean agentRunning;

        PluginDatabaseSystem pluginDatabaseSystem;

        CustomerBrokerCloseNegotiationTransactionDatabaseDao customerBrokerCloseNegotiationTransactionDatabaseDao;

        boolean threadWorking;

        public final int SLEEP_TIME = 5000;

        int iterationNumber = 0;

        int iterationConfirmSend = 0;

        Map<UUID, Integer> transactionSend = new HashMap<>();

        /*IMPLEMENTATION DealsWithPluginIdentity*/
        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        /*IMPLEMENTATION Runnable*/
        @Override
        public void run() {

            threadWorking = true;
            logManager.log(NegotiationTransactionCustomerBrokerClosePluginRoot.getLogLevelByClass(this.getClass().getName()), "Customer Broker Close Monitor Agent: running...", null, null);

            while (threadWorking) {
                //Increase the iteration counter
                iterationNumber++;
                iterationConfirmSend++;
                try {

                    Thread.sleep(SLEEP_TIME);

                } catch (InterruptedException interruptedException) {
                    return;
                }

                //now I will check if there are pending transactions to raise the event
                try {

                    logManager.log(NegotiationTransactionCustomerBrokerClosePluginRoot.getLogLevelByClass(this.getClass().getName()), new StringBuilder().append("Iteration number ").append(iterationNumber).toString(), null, null);
                    doTheMainTask();

                } catch (CantSendCustomerBrokerCloseNegotiationTransactionException | CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException | CantUpdateRecordException e) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        }

        /*INNER CLASS PUBLIC METHOD*/
        public void Initialize() throws CantInitializeCBPAgent {

            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerCloseNegotiationTransactionDatabaseConstants.DATABASE_NAME);

            } catch (DatabaseNotFoundException databaseNotFoundException) {

                try {
                    CustomerBrokerCloseNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerCloseNegotiationTransactionDatabaseFactory(this.pluginDatabaseSystem);
                    database = databaseFactory.createDatabase(pluginId, CustomerBrokerCloseNegotiationTransactionDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException, "Customer Broker Close Initialize Monitor Agent - trying to create the plugin database", "Please, check the cause");
                }

            } catch (CantOpenDatabaseException exception) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantInitializeCBPAgent(exception, "Customer Broker Close Initialize Monitor Agent - trying to open the plugin database", "Please, check the cause");
            }

        }

        public void stopAgent() {
            agentRunning = false;
        }

        public void startAgent() {
            agentRunning = true;
        }

        public boolean isAgentRunning() {
            return agentRunning;
        }
        /*END INNER CLASS PUBLIC METHOD*/

        /*INNER CLASS PRIVATE METHOD*/
        private void doTheMainTask() throws
                CantSendCustomerBrokerCloseNegotiationTransactionException,
                CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException,
                CantUpdateRecordException {

            try {

                customerBrokerCloseNegotiationTransactionDatabaseDao = new CustomerBrokerCloseNegotiationTransactionDatabaseDao(pluginDatabaseSystem, pluginId, database);

                String negotiationXML;
                NegotiationType negotiationType;
                UUID transactionId;
                List<CustomerBrokerClose> negotiationPendingToSubmitList;
                CustomerBrokerPurchaseNegotiation purchaseNegotiation = new NegotiationPurchaseRecord();
                CustomerBrokerSaleNegotiation saleNegotiation = new NegotiationSaleRecord();
                int timeConfirmSend = 20;

                //SEND NEGOTIATION PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_SUBMIT)
                negotiationPendingToSubmitList = customerBrokerCloseNegotiationTransactionDatabaseDao.getPendingToSubmitNegotiation();
                if (!negotiationPendingToSubmitList.isEmpty()) {
                    for (CustomerBrokerClose negotiationTransaction : negotiationPendingToSubmitList) {

                        System.out.print(new StringBuilder().append("\n\n**** 5) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - NEGOTIATION FOR SEND transactionId: ").append(negotiationTransaction.getTransactionId()).append(" ****\n").toString());

                        negotiationXML = negotiationTransaction.getNegotiationXML();
                        negotiationType = negotiationTransaction.getNegotiationType();
                        transactionId = negotiationTransaction.getTransactionId();

                        switch (negotiationType) {
                            case PURCHASE:
                                purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                                System.out.print(new StringBuilder().append("\n\n**** 6) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - PURCHASE NEGOTIATION SEND negotiationId(XML): ").append(purchaseNegotiation.getNegotiationId()).append(" ****\n").append("\n - Status :").append(purchaseNegotiation.getStatus().getCode()).toString());
                                //SEND NEGOTIATION TO BROKER
                                negotiationTransmissionManager.sendNegotiationToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_CLOSE);

                                break;
                            case SALE:
                                saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);
                                System.out.print(new StringBuilder().append("\n\n**** 6) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - SALE NEGOTIATION SEND negotiationId(XML): ").append(saleNegotiation.getNegotiationId()).append(" ****\n").append("\n - Status :").append(saleNegotiation.getStatus().getCode()).toString());
                                //SEND NEGOTIATION TO CUSTOMER
                                negotiationTransmissionManager.sendNegotiationToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_CLOSE);

                                break;
                        }

                        //Update the Negotiation Transaction
                        System.out.print(new StringBuilder().append("\n\n**** 7) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - UPDATE STATUS SALE NEGOTIATION STATUS : ").append(NegotiationTransactionStatus.SENDING_NEGOTIATION.getCode()).append(" ****\n").toString());
                        customerBrokerCloseNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerCloseNegotiationTranasction(
                                transactionId,
                                NegotiationTransactionStatus.SENDING_NEGOTIATION);

                    }
                }

                //SEND CONFIRM PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_CONFIRMATION)
                negotiationPendingToSubmitList = customerBrokerCloseNegotiationTransactionDatabaseDao.getPendingToConfirmtNegotiation();
                if (!negotiationPendingToSubmitList.isEmpty()) {
                    for (CustomerBrokerClose negotiationTransaction : negotiationPendingToSubmitList) {

                        transactionId = negotiationTransaction.getTransactionId();
                        negotiationType = negotiationTransaction.getNegotiationType();

                        System.out.print(new StringBuilder().append("\n\n**** 23) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - UPDATE CONFIRM STATUS SALE NEGOTIATION STATUS : ").append(NegotiationTransactionStatus.SENDING_NEGOTIATION.getCode()).append(" ****\n").toString());

                        switch (negotiationType) {
                            case PURCHASE:
                                System.out.print(new StringBuilder().append("\n\n**** 24) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - PURCHASE NEGOTIATION SEND CONFIRM negotiationId(XML): ").append(purchaseNegotiation.getNegotiationId()).append(" ****\n").toString());
                                //SEND CONFIRM NEGOTIATION TO BROKER
                                negotiationTransmissionManager.sendConfirmNegotiationToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_CLOSE);
                                break;
                            case SALE:
                                System.out.print(new StringBuilder().append("\n\n**** 24) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - SALE NEGOTIATION SEND CONFIRM negotiationId(XML): ").append(purchaseNegotiation.getNegotiationId()).append(" ****\n").toString());
                                //SEND NEGOTIATION TO CUSTOMER
                                negotiationTransmissionManager.sendConfirmNegotiationToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_CLOSE);
                                break;
                        }

                        System.out.print(new StringBuilder().append("\n\n**** 25) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - UPDATE STATUS SALE NEGOTIATION STATUS : ").append(NegotiationTransactionStatus.CONFIRM_NEGOTIATION.getCode()).append(" ****\n").toString());
                        //Update the Negotiation Transaction
                        customerBrokerCloseNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerCloseNegotiationTranasction(
                                transactionId,
                                NegotiationTransactionStatus.CONFIRM_NEGOTIATION);

                        //CONFIRM TRANSACTION IS DONE
                        customerBrokerCloseNegotiationTransactionDatabaseDao.confirmTransaction(transactionId);
                    }
                }

                //PROCES PENDING EVENT
                List<UUID> pendingEventsIdList = customerBrokerCloseNegotiationTransactionDatabaseDao.getPendingEvents();
                for (UUID eventId : pendingEventsIdList) {
                    checkPendingEvent(eventId);
                }

                //SEND TRNSACTION AGAIN IF NOT IS CONFIRM
                if (timeConfirmSend == iterationConfirmSend) {
                    CustomerBrokerCloseForwardTransaction forwardTransaction = new CustomerBrokerCloseForwardTransaction(
                            customerBrokerCloseNegotiationTransactionDatabaseDao,
                            pluginRoot,
                            transactionSend
                    );

                    forwardTransaction.pendingToConfirmtTransaction();
                    transactionSend = forwardTransaction.getTransactionSend();

                    iterationConfirmSend = 0;
                }

            } catch (CantSendNegotiationToCryptoBrokerException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerCloseNegotiationTransactionException(CantSendCustomerBrokerCloseNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Purchase Negotiation", "Error in Negotiation Transmission Network Service");
            } catch (CantSendNegotiationToCryptoCustomerException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerCloseNegotiationTransactionException(CantSendCustomerBrokerCloseNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Sale Negotiation", "Error in Negotiation Transmission Network Service");
            } catch (CantSendConfirmToCryptoBrokerException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException(CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Confirm Purchase Negotiation", "Error in Negotiation Transmission Network Service");
            } catch (CantSendConfirmToCryptoCustomerException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException(CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Confirm Sale Negotiation", "Error in Negotiation Transmission Network Service");
            } catch (CantGetNegotiationTransactionListException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerCloseNegotiationTransactionException(CantSendCustomerBrokerCloseNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Negotiation", "Cannot get the Negotiation list from database");
            } catch (Exception e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Sending Negotiation", "UNKNOWN FAILURE.");
            }
        }

        //CHECK PENDING EVEN
        private void checkPendingEvent(UUID eventId) throws UnexpectedResultReturnedFromDatabaseException {

            try {
                UUID transactionId;
                UUID transmissionId;
                NegotiationTransmission negotiationTransmission;
                NegotiationTransaction negotiationTransaction;
                NegotiationType negotiationType;
                String negotiationXML;
                CustomerBrokerPurchaseNegotiation purchaseNegotiation = new NegotiationPurchaseRecord();
                CustomerBrokerSaleNegotiation saleNegotiation = new NegotiationSaleRecord();

                String eventTypeCode = customerBrokerCloseNegotiationTransactionDatabaseDao.getEventType(eventId);

                //EVENT - RECEIVE NEGOTIATION
                if (eventTypeCode.equals(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_CLOSE.getCode())) {
                    List<Transaction<NegotiationTransmission>> pendingTransactionList = negotiationTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<NegotiationTransmission> record : pendingTransactionList) {

                        negotiationTransmission = record.getInformation();
                        negotiationXML = negotiationTransmission.getNegotiationXML();
                        transmissionId = negotiationTransmission.getTransmissionId();
                        transactionId = negotiationTransmission.getTransactionId();
                        negotiationType = negotiationTransmission.getNegotiationType();

                        System.out.print("\n\n**** 20.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - RECEIVE TRANSACTION  ****\n");
                        if (negotiationXML != null) {

                            negotiationTransaction = customerBrokerCloseNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerCloseNegotiationTranasction(transactionId);

                            if (negotiationTransmission.getTransmissionType().equals(NegotiationTransmissionType.TRANSMISSION_NEGOTIATION)) {

                                if (negotiationTransaction == null) {
                                    switch (negotiationType) {
                                        case PURCHASE:
                                            System.out.print("\n\n**** 20.2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - RECEIVE TRANSACTION PURCHASE ****\n");
                                            //CLOSE PURCHASE NEGOTIATION
                                            purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                                            customerBrokerClosePurchaseNegotiationTransaction = new CustomerBrokerClosePurchaseNegotiationTransaction(
                                                    customerBrokerPurchaseNegotiationManager,
                                                    customerBrokerCloseNegotiationTransactionDatabaseDao,
                                                    cryptoAddressBookManager,
                                                    cryptoVaultManager,
                                                    walletManagerManager,
                                                    pluginRoot,
                                                    intraWalletUserIdentityManager
                                            );
                                            customerBrokerClosePurchaseNegotiationTransaction.receivePurchaseNegotiationTranasction(transactionId, purchaseNegotiation);
                                            break;
                                        case SALE:
                                            System.out.print("\n\n**** 20.2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - RECEIVE TRANSACTION SALE ****\n");
                                            //CLOSE SALE NEGOTIATION
                                            saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);
                                            customerBrokerCloseSaleNegotiationTransaction = new CustomerBrokerCloseSaleNegotiationTransaction(
                                                    customerBrokerSaleNegotiationManager,
                                                    customerBrokerCloseNegotiationTransactionDatabaseDao,
                                                    cryptoAddressBookManager,
                                                    cryptoVaultManager,
                                                    walletManagerManager,
                                                    pluginRoot,
                                                    intraWalletUserIdentityManager
                                            );
                                            customerBrokerCloseSaleNegotiationTransaction.receiveSaleNegotiationTranasction(transactionId, saleNegotiation);
                                            break;
                                    }
                                } else {

                                    System.out.print("\n**** 20.2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - CREATE NEGOTIATION TRANSACTION REPEAT SEND ****\n");
                                    //CONFIRM TRANSACTION
                                    customerBrokerCloseNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerCloseNegotiationTranasction(
                                            transactionId,
                                            NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM);

                                }

                            } else if (negotiationTransmission.getTransmissionType().equals(NegotiationTransmissionType.TRANSMISSION_CONFIRM)) {

                                if (!negotiationTransaction.getStatusTransaction().getCode().equals(NegotiationTransactionStatus.CONFIRM_NEGOTIATION.getCode())) {

                                    switch (negotiationType) {
                                        case PURCHASE:
                                            System.out.print("\n\n**** 27.2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - RECEIVE CONFIRM PURCHASE ****\n");
                                            //UPDATE NEGOTIATION IF PAYMENT IS CRYPTO CURRENCY
                                            purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                                            customerBrokerClosePurchaseNegotiationTransaction = new CustomerBrokerClosePurchaseNegotiationTransaction(
                                                    customerBrokerPurchaseNegotiationManager,
                                                    customerBrokerCloseNegotiationTransactionDatabaseDao,
                                                    cryptoAddressBookManager,
                                                    cryptoVaultManager,
                                                    walletManagerManager,
                                                    pluginRoot,
                                                    intraWalletUserIdentityManager
                                            );
                                            customerBrokerClosePurchaseNegotiationTransaction.receivePurchaseConfirm(purchaseNegotiation);
                                            break;
                                        case SALE:
                                            System.out.print("\n\n**** 27.2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - RECEIVE CONFIRM SALE ****\n");
                                            //UPDATE NEGOTIATION IF MERCHANDISE IS CRYPTO CURRENCY
                                            saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);
                                            customerBrokerCloseSaleNegotiationTransaction = new CustomerBrokerCloseSaleNegotiationTransaction(
                                                    customerBrokerSaleNegotiationManager,
                                                    customerBrokerCloseNegotiationTransactionDatabaseDao,
                                                    cryptoAddressBookManager,
                                                    cryptoVaultManager,
                                                    walletManagerManager,
                                                    pluginRoot,
                                                    intraWalletUserIdentityManager
                                            );
                                            customerBrokerCloseSaleNegotiationTransaction.receiveSaleConfirm(saleNegotiation);
                                            break;
                                    }

                                    //CONFIRM TRANSACTION
                                    customerBrokerCloseNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerCloseNegotiationTranasction(transactionId, NegotiationTransactionStatus.CONFIRM_NEGOTIATION);
                                }

                                //CONFIRM TRANSACTION IS DONE
                                customerBrokerCloseNegotiationTransactionDatabaseDao.confirmTransaction(transactionId);

                            }

                            //NOTIFIED EVENT
                            customerBrokerCloseNegotiationTransactionDatabaseDao.updateEventTansactionStatus(eventId, EventStatus.NOTIFIED);
                            //CONFIRM TRANSMISSION
                            negotiationTransmissionManager.confirmReception(transmissionId);

                        }

                    }
                }

            } catch (Exception e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                e.printStackTrace();
            }
        }

    }
}