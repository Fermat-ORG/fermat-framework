package com.bitdubai.fermat_cht_plugin.layer.network_service.developer.chat.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
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
import com.bitdubai.fermat_cht_api.all_definition.agent.CHTTransactionAgent;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantInitializeCHTAgent;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageNewStatusNotificationException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.developer.chat.version_1.ChatPluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.network_service.developer.chat.version_1.database.NetworkServiceChatNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cht_plugin.layer.network_service.developer.chat.version_1.database.NetworkServiceChatNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.network_service.developer.chat.version_1.database.OutgoinChatMetaDataDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo on 05/01/16.
 */
public class ChatMonitorAgent implements CHTTransactionAgent,
        DealsWithLogger,
        DealsWithEvents,
        DealsWithErrors,
        DealsWithPluginDatabaseSystem,
        DealsWithPluginIdentity {
    @Override
    public void start() throws CantStartAgentException {

    }

    @Override
    public void stop() {

    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }

    @Override
    public void setEventManager(EventManager eventManager) {

    }

    @Override
    public void setLogManager(LogManager logManager) {

    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {

    }

    @Override
    public void setPluginId(UUID pluginId) {

    }
//    /*
//   * Represent the sleep time for the read or send (2000 milliseconds)
//   */
//    private static final long SLEEP_TIME = 15000;
//    private static final long RECEIVE_SLEEP_TIME = 15000;
//    private Database database;
//    private MonitorAgent monitorAgent;
//    private Thread agentThread;
//    private LogManager logManager;
//    private EventManager eventManager;
//    private ErrorManager errorManager;
//    private PluginDatabaseSystem pluginDatabaseSystem;
//    private UUID pluginId;
//    private ChatNetworkServiceManager chatNetworkServiceManager;
//
//    public ChatMonitorAgent(PluginDatabaseSystem pluginDatabaseSystem,
//                                    LogManager logManager,
//                                    ErrorManager errorManager,
//                                    EventManager eventManager,
//                                    UUID pluginId,
//                                    ChatNetworkServiceManager chatNetworkServiceManager) throws CantSetObjectException {
//        this.eventManager = eventManager;
//        this.pluginDatabaseSystem = pluginDatabaseSystem;
//        this.errorManager = errorManager;
//        this.pluginId = pluginId;
//        this.logManager=logManager;
//        this.chatNetworkServiceManager=chatNetworkServiceManager;
//    }
//
//
//    @Override
//    public void start() throws CantStartAgentException {
//
//        monitorAgent = new MonitorAgent();
//
//        (this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);
//        (this.monitorAgent).setErrorManager(this.errorManager);
//
//        try {
//            ((MonitorAgent) this.monitorAgent).Initialize();
//        } catch (CantInitializeCHTAgent exception) {
//            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
//        }
//
//        this.agentThread = new Thread(monitorAgent);
//        this.agentThread.start();
//
//    }
//
//    @Override
//    public void stop() {
//
//    }
//
//    @Override
//    public void setErrorManager(ErrorManager errorManager) {
//
//    }
//
//    @Override
//    public void setEventManager(EventManager eventManager) {
//
//    }
//
//    @Override
//    public void setLogManager(LogManager logManager) {
//
//    }
//
//    @Override
//    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
//
//    }
//
//    @Override
//    public void setPluginId(UUID pluginId) {
//
//    }
//
//    /**
//     * Private class which implements runnable and is started by the Agent
//     * Based on MonitorAgent created by Rodrigo Acosta
//     */
//    private class MonitorAgent implements DealsWithPluginDatabaseSystem, DealsWithErrors, Runnable{
//
//            private ErrorManager errorManager;
//            private PluginDatabaseSystem pluginDatabaseSystem;
//
//            private  int iterationNumber = 0;
//            private OutgoinChatMetaDataDao outgoinChatMetaDataDao;
//            private boolean threadWorking;
//
//            @Override
//            public void setErrorManager(ErrorManager errorManager) {
//                this.errorManager = errorManager;
//            }
//
//            @Override
//            public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
//                this.pluginDatabaseSystem = pluginDatabaseSystem;
//            }
//
//            @Override
//            public void run() {
//                threadWorking=true;
//                logManager.log(ChatPluginRoot.getLogLevelByClass(this.getClass().getName()),
//                        "Chat Monitor Agent: running...", null, null);
//                while(threadWorking){
//                    /**
//                     * Increase the iteration counter
//                     */
//                    iterationNumber++;
//                    try {
//                        Thread.sleep(SLEEP_TIME);
//                    } catch (InterruptedException interruptedException) {
//                        return;
//                    }
//
//                    /**
//                     * now I will check if there are pending transactions to raise the event
//                     */
//                    try {
//
//                        logManager.log(ChatPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
//                        doTheMainTask();
//                    } catch (CantSendChatMessageMetadataException | CantUpdateRecordException | CantSendChatMessageNewStatusNotificationException e) {
//                        errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                    }
//
//                }
//
//            }
//            public void Initialize() throws CantInitializeCHTAgent {
//                try {
//
//                    database = this.pluginDatabaseSystem.openDatabase(pluginId,
//                            NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);
//                }
//                catch (DatabaseNotFoundException databaseNotFoundException) {
//
//                    //Logger LOG = Logger.getGlobal();
//                    //LOG.info("Database in Open Contract monitor agent doesn't exists");
//                    NetworkServiceChatNetworkServiceDatabaseFactory networkServiceChatNetworkServiceDatabaseFactory=new NetworkServiceChatNetworkServiceDatabaseFactory(this.pluginDatabaseSystem);
//                    try {
//                        database = networkServiceChatNetworkServiceDatabaseFactory.createDatabase(pluginId,
//                                NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);
//                    } catch (CantCreateDatabaseException cantCreateDatabaseException) {
//                        errorManager.reportUnexpectedPluginException(
//                                Plugins.CHAT_NETWORK_SERVICE,
//                                UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
//                                cantCreateDatabaseException);
//                        throw new CantInitializeCHTAgent(cantCreateDatabaseException,
//                                "Initialize Monitor Agent - trying to create the plugin database",
//                                "Please, check the cause");
//                    }
//                } catch (CantOpenDatabaseException exception) {
//                    errorManager.reportUnexpectedPluginException(
//                            Plugins.CHAT_NETWORK_SERVICE,
//                            UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
//                            exception);
//                    throw new CantInitializeCHTAgent(exception,
//                            "Initialize Monitor Agent - trying to open the plugin database",
//                            "Please, check the cause");
//                }
//        }
//        private void doTheMainTask() throws
//                CantSendChatMessageMetadataException,
//                CantUpdateRecordException,
//                CantSendChatMessageNewStatusNotificationException {
//
//            try{
//                outgoinChatMetaDataDao=new OutgoinChatMetaDataDao(
//                        database);
//                /**
//                 * Check if exist in database new contracts to send
//                 */
//                List<String> contractPendingToSubmitList=openContractBusinessTransactionDao.getPendingToSubmitContractHash();
//                String contractXML;
//                ContractPurchaseRecord purchaseContract=new ContractPurchaseRecord();
//                ContractSaleRecord saleContract=new ContractSaleRecord();
//                ContractType contractType;
//                UUID transactionId;
//                if(!contractPendingToSubmitList.isEmpty()){
//                    for(String hashToSubmit: contractPendingToSubmitList){
//                        System.out.println("OPEN CONTRACT - Hash to submit:\n"+hashToSubmit);
//                        contractXML=openContractBusinessTransactionDao.getContractXML(hashToSubmit);
//                        contractType=openContractBusinessTransactionDao.getContractType(hashToSubmit);
//                        transactionId=openContractBusinessTransactionDao.getTransactionId(hashToSubmit);
//                        switch (contractType){
//                            case PURCHASE:
//                                purchaseContract=(ContractPurchaseRecord) XMLParser.parseXML(
//                                        contractXML,
//                                        purchaseContract);
//                                transactionTransmissionManager.sendContractHashToCryptoBroker(
//                                        transactionId,
//                                        purchaseContract.getPublicKeyCustomer(),
//                                        purchaseContract.getPublicKeyBroker(),
//                                        hashToSubmit,
//                                        purchaseContract.getNegotiationId());
//                                break;
//                            case SALE:
//                                saleContract=(ContractSaleRecord)XMLParser.parseXML(
//                                        contractXML,
//                                        saleContract);
//                                transactionTransmissionManager.sendContractHashToCryptoCustomer(
//                                        transactionId,
//                                        saleContract.getPublicKeyBroker(),
//                                        saleContract.getPublicKeyCustomer(),
//                                        hashToSubmit,
//                                        saleContract.getNegotiationId());
//                                break;
//                        }
//                        //Update the ContractTransactionStatus
//                        openContractBusinessTransactionDao.updateContractTransactionStatus(
//                                hashToSubmit,
//                                ContractTransactionStatus.CHECKING_HASH);
//                    }
//
//                }
//
//                /**
//                 * Check if pending contract to confirm
//                 */
//                List<String> contractPendingToConfirmList=openContractBusinessTransactionDao.getPendingToConfirmContractHash();
//                if(!contractPendingToConfirmList.isEmpty()){
//                    for(String hashToSubmit: contractPendingToSubmitList){
//                        System.out.println("OPEN CONTRACT - Hash to confirm:\n"+hashToSubmit);
//                        transactionId=openContractBusinessTransactionDao.getTransactionId(hashToSubmit);
//                        contractXML=openContractBusinessTransactionDao.getContractXML(hashToSubmit);
//                        contractType=openContractBusinessTransactionDao.getContractType(hashToSubmit);
//                        switch (contractType){
//                            case PURCHASE:
//                                purchaseContract=(ContractPurchaseRecord)XMLParser.parseXML(
//                                        contractXML,
//                                        purchaseContract);
//                                transactionTransmissionManager.sendContractStatusNotificationToCryptoBroker(
//                                        purchaseContract.getPublicKeyCustomer(),
//                                        purchaseContract.getPublicKeyBroker(),
//                                        hashToSubmit,
//                                        transactionId.toString(),
//                                        ContractTransactionStatus.CONTRACT_CONFIRMED);
//                                break;
//                            case SALE:
//                                saleContract=(ContractSaleRecord)XMLParser.parseXML(
//                                        contractXML,
//                                        saleContract);
//                                transactionTransmissionManager.sendContractStatusNotificationToCryptoCustomer(
//                                        purchaseContract.getPublicKeyBroker(),
//                                        purchaseContract.getPublicKeyCustomer(),
//                                        hashToSubmit,
//                                        transactionId.toString(),
//                                        ContractTransactionStatus.CONTRACT_CONFIRMED);
//                                break;
//                        }
//                        //Update the ContractTransactionStatus
//                        openContractBusinessTransactionDao.updateContractTransactionStatus(
//                                hashToSubmit,
//                                ContractTransactionStatus.CONTRACT_CONFIRMED);
//                    }
//
//                }
//
//                /**
//                 * Check if pending events
//                 */
//                List<String> pendingEventsIdList=openContractBusinessTransactionDao.getPendingEvents();
//                for(String eventId : pendingEventsIdList){
//                    checkPendingEvent(eventId);
//                }
//
//
//            } catch (CantGetContractListException e) {
//                throw new CannotSendContractHashException(
//                        e,
//                        "Sending contract hash",
//                        "Cannot get the contract list from database");
//            } catch (UnexpectedResultReturnedFromDatabaseException e) {
//                throw new CannotSendContractHashException(
//                        e,
//                        "Sending contract hash",
//                        "Unexpected result in database");
//            }  catch (CantSendBusinessTransactionHashException e) {
//                throw new CannotSendContractHashException(
//                        e,
//                        "Sending contract hash",
//                        "Error in Transaction Transmission Network Service");
//            }
//            catch (CantSendContractNewStatusNotificationException e) {
//                throw new CantSendContractNewStatusNotificationException(
//                        CantSendContractNewStatusNotificationException.DEFAULT_MESSAGE,
//                        e,
//                        "Sending contract hash",
//                        "Error in Transaction Transmission Network Service");
//            }
//
//        }
//    }
}
