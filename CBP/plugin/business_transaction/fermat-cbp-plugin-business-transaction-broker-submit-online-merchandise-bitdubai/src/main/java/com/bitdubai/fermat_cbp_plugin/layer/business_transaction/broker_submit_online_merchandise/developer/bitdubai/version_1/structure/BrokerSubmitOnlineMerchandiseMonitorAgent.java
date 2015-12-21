package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.agent.CBPTransactionAgent;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.PaymentType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.events.BrokerSubmitMerchandiseConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CannotSendContractHashException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.CryptoMoneyDeStockRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.events.CustomerOnlinePaymentConfirmed;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantupdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantupdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions.CantSendBusinessTransactionHashException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions.CantSendContractNewStatusNotificationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.exceptions.CantCreateCryptoMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.interfaces.CryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.BrokerSubmitOnlineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetCryptoStatusException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetSendCryptoTransactionHashException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.IntraActorCryptoTransactionManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/12/15.
 */
public class BrokerSubmitOnlineMerchandiseMonitorAgent implements
        CBPTransactionAgent,
        DealsWithLogger,
        DealsWithEvents,
        DealsWithErrors,
        DealsWithPluginDatabaseSystem,
        DealsWithPluginIdentity {

    Database database;
    MonitorAgent monitorAgent;
    Thread agentThread;
    LogManager logManager;
    EventManager eventManager;
    ErrorManager errorManager;
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;
    TransactionTransmissionManager transactionTransmissionManager;
    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    IntraActorCryptoTransactionManager intraActorCryptoTransactionManager;
    OutgoingIntraActorManager outgoingIntraActorManager;
    CryptoMoneyDestockManager cryptoMoneyDeStockManager;

    public BrokerSubmitOnlineMerchandiseMonitorAgent(
            PluginDatabaseSystem pluginDatabaseSystem,
            LogManager logManager,
            ErrorManager errorManager,
            EventManager eventManager,
            UUID pluginId,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            OutgoingIntraActorManager outgoingIntraActorManager,
            CryptoMoneyDestockManager cryptoMoneyDeStockManager) throws CantSetObjectException {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.logManager=logManager;
        this.transactionTransmissionManager=transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager=customerBrokerContractPurchaseManager;
        this.outgoingIntraActorManager=outgoingIntraActorManager;
        this.customerBrokerContractSaleManager=customerBrokerContractSaleManager;
        this.cryptoMoneyDeStockManager=cryptoMoneyDeStockManager;
        setIntraActorCryptoTransactionManager(outgoingIntraActorManager);
    }

    private void setIntraActorCryptoTransactionManager(
            OutgoingIntraActorManager outgoingIntraActorManager) throws CantSetObjectException {
        if(outgoingIntraActorManager==null){
            throw new CantSetObjectException("outgoingIntraActorManager is null");
        }
        try {
            this.intraActorCryptoTransactionManager=outgoingIntraActorManager.getTransactionManager();
        } catch (CantGetOutgoingIntraActorTransactionManagerException e) {
            throw new CantSetObjectException(
                    e,
                    "Setting the intraActorCryptoTransactionManager",
                    "Cannot get the intraActorCryptoTransactionManager from outgoingIntraActorManager");
        }
    }

    @Override
    public void start() throws CantStartAgentException {

        //Logger LOG = Logger.getGlobal();
        //LOG.info("Customer online payment monitor agent starting");
        monitorAgent = new MonitorAgent();

        ((DealsWithPluginDatabaseSystem) this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        ((DealsWithErrors) this.monitorAgent).setErrorManager(this.errorManager);

        try {
            ((MonitorAgent) this.monitorAgent).Initialize();
        } catch (CantInitializeCBPAgent exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
        }

        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();

    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager=errorManager;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager=eventManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager=logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem=pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId=pluginId;
    }

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
    private class MonitorAgent implements DealsWithPluginDatabaseSystem, DealsWithErrors, Runnable{

        ErrorManager errorManager;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = 5000;
        int iterationNumber = 0;
        BrokerSubmitOnlineMerchandiseBusinessTransactionDao brokerSubmitOnlineMerchandiseBusinessTransactionDao;
        boolean threadWorking;

        @Override
        public void setErrorManager(ErrorManager errorManager) {
            this.errorManager = errorManager;
        }

        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }
        @Override
        public void run() {

            threadWorking=true;
            logManager.log(BrokerSubmitOnlineMerchandisePluginRoot.getLogLevelByClass(this.getClass().getName()),
                    "Broker Submit Online Merchandise Monitor Agent: running...", null, null);
            while(threadWorking){
                /**
                 * Increase the iteration counter
                 */
                iterationNumber++;
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    return;
                }

                /**
                 * now I will check if there are pending transactions to raise the event
                 */
                try {

                    logManager.log(BrokerSubmitOnlineMerchandisePluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CannotSendContractHashException | CantUpdateRecordException | CantSendContractNewStatusNotificationException | CantCreateCryptoMoneyDestockException e) {
                    errorManager.reportUnexpectedPluginException(
                            Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                            UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                            e);
                }

            }

        }
        public void Initialize() throws CantInitializeCBPAgent {
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId,
                        BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME);
            }
            catch (DatabaseNotFoundException databaseNotFoundException) {

                //Logger LOG = Logger.getGlobal();
                //LOG.info("Database in Open Contract monitor agent doesn't exists");
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory brokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory=
                        new BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = brokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory.createDatabase(pluginId,
                            BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    errorManager.reportUnexpectedPluginException(
                            Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                            UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                            cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException,
                            "Initialize Monitor Agent - trying to create the plugin database",
                            "Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                errorManager.reportUnexpectedPluginException(
                        Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        exception);
                throw new CantInitializeCBPAgent(exception,
                        "Initialize Monitor Agent - trying to open the plugin database",
                        "Please, check the cause");
            }
        }

        private void doTheMainTask() throws
                CannotSendContractHashException,
                CantUpdateRecordException,
                CantSendContractNewStatusNotificationException, CantCreateCryptoMoneyDestockException {

            try{
                brokerSubmitOnlineMerchandiseBusinessTransactionDao =new BrokerSubmitOnlineMerchandiseBusinessTransactionDao(
                        pluginDatabaseSystem,
                        pluginId,
                        database);

                UUID outgoingCryptoTransactionId;
                BusinessTransactionRecord businessTransactionRecord;
                String contractHash;

                /**
                 * Check if there is some transaction to crypto de stock
                 * The de stock condition is reading the ContractTransactionStatus in PENDING_ONLINE_DE_STOCK
                 */
                List<BusinessTransactionRecord> pendingToDeStockTransactionList=
                        brokerSubmitOnlineMerchandiseBusinessTransactionDao.getPendingDeStockTransactionList();
                for(BusinessTransactionRecord pendingToDeStockTransaction : pendingToDeStockTransactionList){
                    CryptoMoneyDeStockRecord cryptoMoneyDeStockRecord=new CryptoMoneyDeStockRecord(
                            pendingToDeStockTransaction);
                    //Execute the de stock transaction.
                    cryptoMoneyDeStockManager.createTransactionDestock(
                            cryptoMoneyDeStockRecord.getPublicKeyActor(),
                            cryptoMoneyDeStockRecord.getCryptoCurrency(),
                            cryptoMoneyDeStockRecord.getCbpWalletPublicKey(),
                            cryptoMoneyDeStockRecord.getCryWalletPublicKey(),
                            cryptoMoneyDeStockRecord.getAmount(),
                            cryptoMoneyDeStockRecord.getMemo(),
                            cryptoMoneyDeStockRecord.getPriceReference(),
                            cryptoMoneyDeStockRecord.getOriginTransaction());
                    pendingToDeStockTransaction.setContractTransactionStatus(
                            ContractTransactionStatus.PENDING_SUBMIT_ONLINE_MERCHANDISE);
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(
                            pendingToDeStockTransaction);
                }

                /**
                 * Check if there is some crypto to send
                 */
                List<String> pendingToSubmitCrypto=brokerSubmitOnlineMerchandiseBusinessTransactionDao.
                        getPendingToSubmitCryptoList();
                for(String pendingContractHash : pendingToSubmitCrypto){
                    businessTransactionRecord =brokerSubmitOnlineMerchandiseBusinessTransactionDao.
                            getBussinesTransactionRecord(pendingContractHash);
                    outgoingCryptoTransactionId=intraActorCryptoTransactionManager.sendCrypto(
                            businessTransactionRecord.getCryptoWalletPublicKey(),
                            businessTransactionRecord.getCryptoAddress(),
                            businessTransactionRecord.getCryptoAmount(),
                            "Payment from Crypto Customer contract " + pendingContractHash,
                            businessTransactionRecord.getBrokerPublicKey(),
                            businessTransactionRecord.getCustomerPublicKey(),
                            Actors.CBP_CRYPTO_BROKER,
                            Actors.CBP_CRYPTO_CUSTOMER,
                            ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET
                    );
                    //Updating the business transaction record
                    businessTransactionRecord.setTransactionId(
                            outgoingCryptoTransactionId.toString());
                    businessTransactionRecord.setContractTransactionStatus(
                            ContractTransactionStatus.CRYPTO_MERCHANDISE_SUBMITTED);
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(
                            businessTransactionRecord);
                }

                //TODO: finish this

                /**
                 * Check contract status to send.
                 */
                List<BusinessTransactionRecord> pendingToSubmitNotificationList=
                        brokerSubmitOnlineMerchandiseBusinessTransactionDao.getPendingToSubmitNotificationList();
                for(BusinessTransactionRecord pendingToSubmitNotificationRecord : pendingToSubmitNotificationList){
                    contractHash=pendingToSubmitNotificationRecord.getTransactionHash();
                    transactionTransmissionManager.sendContractStatusNotificationToCryptoBroker(
                            pendingToSubmitNotificationRecord.getCustomerPublicKey(),
                            pendingToSubmitNotificationRecord.getBrokerPublicKey(),
                            contractHash,
                            pendingToSubmitNotificationRecord.getTransactionId(),
                            ContractTransactionStatus.ONLINE_PAYMENT_SUBMITTED
                    );
                    //Updating the business transaction record
                    pendingToSubmitNotificationRecord.setContractTransactionStatus(
                            ContractTransactionStatus.ONLINE_PAYMENT_SUBMITTED);
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(
                            pendingToSubmitNotificationRecord);
                }

                /**
                 * Check pending notifications - Customer side
                 */
                List<BusinessTransactionRecord> pendingToSubmitConfirmationList=
                        brokerSubmitOnlineMerchandiseBusinessTransactionDao.getPendingToSubmitNotificationList();
                for(BusinessTransactionRecord pendingToSubmitConfirmationRecord : pendingToSubmitConfirmationList){
                    contractHash=pendingToSubmitConfirmationRecord.getTransactionHash();
                    transactionTransmissionManager.sendContractStatusNotificationToCryptoBroker(
                            pendingToSubmitConfirmationRecord.getCustomerPublicKey(),
                            pendingToSubmitConfirmationRecord.getBrokerPublicKey(),
                            contractHash,
                            pendingToSubmitConfirmationRecord.getTransactionId(),
                            ContractTransactionStatus.CONFIRM_ONLINE_CONSIGNMENT
                    );
                    //Updating the business transaction record
                    pendingToSubmitConfirmationRecord.setContractTransactionStatus(
                            ContractTransactionStatus.CONFIRM_ONLINE_CONSIGNMENT);
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(
                            pendingToSubmitConfirmationRecord);
                }

                /**
                 * Check pending transactions
                 */
                List<BusinessTransactionRecord> pendingTransactions=
                        brokerSubmitOnlineMerchandiseBusinessTransactionDao.getPendingCryptoTransactionList();
                for(BusinessTransactionRecord onlinePaymentRecord: pendingTransactions){
                    checkPendingTransaction(onlinePaymentRecord);
                }

                /**
                 * Check if pending to submit crypto status
                 */
                List<BusinessTransactionRecord> pendingSubmitContractList=
                        brokerSubmitOnlineMerchandiseBusinessTransactionDao.getPendingToSubmitCryptoStatusList();
                CryptoStatus cryptoStatus;
                for(BusinessTransactionRecord pendingSubmitContractRecord : pendingSubmitContractList){
                    cryptoStatus=outgoingIntraActorManager.getTransactionStatus(
                            pendingSubmitContractRecord.getTransactionHash());
                    pendingSubmitContractRecord.setCryptoStatus(cryptoStatus);
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(
                            pendingSubmitContractRecord);
                }

                /**
                 * Check if on crypto network crypto status
                 */
                List<BusinessTransactionRecord> pendingOnCryptoNetworkContractList=
                        brokerSubmitOnlineMerchandiseBusinessTransactionDao.getOnCryptoNetworkCryptoStatusList();
                for(BusinessTransactionRecord onCryptoNetworkContractRecord : pendingOnCryptoNetworkContractList){
                    cryptoStatus=outgoingIntraActorManager.getTransactionStatus(
                            onCryptoNetworkContractRecord.getTransactionHash());
                    onCryptoNetworkContractRecord.setCryptoStatus(cryptoStatus);
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(
                            onCryptoNetworkContractRecord);
                }

                /**
                 * Check if on blockchain crypto status
                 */
                List<BusinessTransactionRecord> pendingOnBlockchainContractList=
                        brokerSubmitOnlineMerchandiseBusinessTransactionDao.getOnBlockchainkCryptoStatusList();
                for(BusinessTransactionRecord onBlockchainContractRecord : pendingOnBlockchainContractList){
                    cryptoStatus=outgoingIntraActorManager.getTransactionStatus(
                            onBlockchainContractRecord.getTransactionHash());
                    onBlockchainContractRecord.setCryptoStatus(cryptoStatus);
                    onBlockchainContractRecord.setContractTransactionStatus(
                            ContractTransactionStatus.ONLINE_MERCHANDISE_SUBMITTED);
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(
                            onBlockchainContractRecord);
                }

                /**
                 * Check if pending events
                 */
                List<String> pendingEventsIdList=
                        brokerSubmitOnlineMerchandiseBusinessTransactionDao.getPendingEvents();
                for(String eventId : pendingEventsIdList){
                    checkPendingEvent(eventId);
                }



            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                throw new CannotSendContractHashException(
                        e,
                        "Sending contract hash",
                        "Unexpected result in database");
            } catch (CantSendContractNewStatusNotificationException e) {
                throw new CantSendContractNewStatusNotificationException(
                        CantSendContractNewStatusNotificationException.DEFAULT_MESSAGE,
                        e,
                        "Sending contract hash",
                        "Error in Transaction Transmission Network Service");
            } catch (OutgoingIntraActorInsufficientFundsException e) {
                //TODO: I want to get a better handler for this exception
                errorManager.reportUnexpectedPluginException(
                        Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                        e);
            } catch (OutgoingIntraActorCantGetCryptoStatusException e) {
                throw new CantUpdateRecordException(
                        OutgoingIntraActorCantGetCryptoStatusException.DEFAULT_MESSAGE,
                        e,
                        "Getting the crypto status",
                        "Cannot get the crypto status");
            } catch (CantGetContractListException e) {
                throw new CantUpdateRecordException(
                        OutgoingIntraActorCantGetCryptoStatusException.DEFAULT_MESSAGE,
                        e,
                        "Getting the Contract",
                        "Cannot get the contract list");
            }  catch (OutgoingIntraActorCantSendFundsExceptions outgoingIntraActorCantSendFundsExceptions) {
                errorManager.reportUnexpectedPluginException(
                        Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                        outgoingIntraActorCantSendFundsExceptions);
            }

        }

        private void raisePaymentConfirmationEvent(String contractHash){
            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.CUSTOMER_ONLINE_PAYMENT_CONFIRMED);
            BrokerSubmitMerchandiseConfirmed brokerSubmitMerchandiseConfirmed = (BrokerSubmitMerchandiseConfirmed) fermatEvent;
            brokerSubmitMerchandiseConfirmed.setSource(EventSource.BROKER_SUBMIT_ONLINE_MERCHANDISE);
            brokerSubmitMerchandiseConfirmed.setContractHash(contractHash);
            brokerSubmitMerchandiseConfirmed.setMerchandiseType(PaymentType.CRYPTO_MONEY);
            eventManager.raiseEvent(brokerSubmitMerchandiseConfirmed);
        }

        //TODO: raise an event only in broker side, notifying the incoming online payment. Create the event.
        //TODO: raise an event only in customer side, notifying the reception of an online payment. Create the event.
        private void checkPendingTransaction(
                BusinessTransactionRecord businessTransactionRecord)throws CantUpdateRecordException
        {
            UUID transactionUUID=UUID.fromString(businessTransactionRecord.getTransactionId());
            //Get transaction hash from IntraActorCryptoTransactionManager
            try {
                String transactionHash =
                        intraActorCryptoTransactionManager.getSendCryptoTransactionHash(
                                transactionUUID);
                if(transactionHash==null){
                    //If transactionHash is null the crypto amount is not sent yet, I'll check for this later.
                    return;
                }
                CryptoStatus cryptoStatus=outgoingIntraActorManager.getTransactionStatus(transactionHash);
                businessTransactionRecord.setTransactionHash(transactionHash);
                businessTransactionRecord.setCryptoStatus(cryptoStatus);
                brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(businessTransactionRecord);
            } catch (OutgoingIntraActorCantGetSendCryptoTransactionHashException e) {
                //I want to know a better way to handle with this exception, for now I going to print the exception and return this method.
                e.printStackTrace();
                errorManager.reportUnexpectedPluginException(
                        Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                        e);
            } catch (OutgoingIntraActorCantGetCryptoStatusException e) {
                //I want to know a better way to handle with this exception, for now I going to print the exception and return this method.
                e.printStackTrace();
                errorManager.reportUnexpectedPluginException(
                        Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                        e);
            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                throw new CantUpdateRecordException(
                        UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE,
                        e,
                        "Checking the crypto status",
                        "I get an unexpected results in database");
            }
        }

        private void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {

            try{

                String eventTypeCode=brokerSubmitOnlineMerchandiseBusinessTransactionDao.getEventType(eventId);
                String contractHash;
                BusinessTransactionMetadata businessTransactionMetadata;
                ContractTransactionStatus contractTransactionStatus;
                BusinessTransactionRecord businessTransactionRecord;
                if(eventTypeCode.equals(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE.getCode())){
                    //This will happen in customer side
                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList=
                            transactionTransmissionManager.getPendingTransactions(
                                    Specialist.UNKNOWN_SPECIALIST);
                    for(Transaction<BusinessTransactionMetadata> record : pendingTransactionList){
                        businessTransactionMetadata=record.getInformation();
                        contractHash=businessTransactionMetadata.getContractHash();
                        if(brokerSubmitOnlineMerchandiseBusinessTransactionDao.isContractHashInDatabase(contractHash)){
                            contractTransactionStatus=brokerSubmitOnlineMerchandiseBusinessTransactionDao.
                                    getContractTransactionStatus(contractHash);
                            //TODO: analyze what we need to do here.
                        }else{
                            CustomerBrokerContractPurchase customerBrokerContractPurchase=
                                    customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(
                                            contractHash);
                            brokerSubmitOnlineMerchandiseBusinessTransactionDao.persistContractInDatabase(
                                    customerBrokerContractPurchase);
                            customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(
                                    contractHash,
                                    ContractStatus.MERCHANDISE_SUBMIT);
                            raisePaymentConfirmationEvent(contractHash);
                        }
                        transactionTransmissionManager.confirmReception(record.getTransactionID());
                    }
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateEventStatus(eventId,
                            EventStatus.NOTIFIED);
                }
                if(eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())){
                    //This will happen in broker side
                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList=
                            transactionTransmissionManager.getPendingTransactions(
                                    Specialist.UNKNOWN_SPECIALIST);
                    for(Transaction<BusinessTransactionMetadata> record : pendingTransactionList){
                        businessTransactionMetadata=record.getInformation();
                        contractHash=businessTransactionMetadata.getContractHash();
                        if(brokerSubmitOnlineMerchandiseBusinessTransactionDao.isContractHashInDatabase(contractHash)){
                            businessTransactionRecord =
                                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.
                                            getBussinesTransactionRecord(contractHash);
                            contractTransactionStatus= businessTransactionRecord.getContractTransactionStatus();
                            if(contractTransactionStatus.getCode().equals(ContractTransactionStatus.ONLINE_PAYMENT_SUBMITTED.getCode())){
                                businessTransactionRecord.setContractTransactionStatus(ContractTransactionStatus.CONFIRM_ONLINE_PAYMENT);
                                customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(
                                        contractHash,
                                        ContractStatus.PAYMENT_SUBMIT);
                                raisePaymentConfirmationEvent(contractHash);
                            }
                        }
                        transactionTransmissionManager.confirmReception(record.getTransactionID());
                    }
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                }
                //TODO: look a better way to deal with this exceptions
            } catch (CantDeliverPendingTransactionsException e) {
                e.printStackTrace();
            } catch (CantInsertRecordException e) {
                e.printStackTrace();
            } catch (CantupdateCustomerBrokerContractPurchaseException e) {
                e.printStackTrace();
            } catch (CantupdateCustomerBrokerContractSaleException e) {
                e.printStackTrace();
            } catch (CantConfirmTransactionException e) {
                e.printStackTrace();
            } catch (CantUpdateRecordException e) {
                e.printStackTrace();
            } catch (CantGetListCustomerBrokerContractPurchaseException e) {
                e.printStackTrace();
            }

        }

    }

}

