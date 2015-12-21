package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
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
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CannotSendContractHashException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.CustomerOnlinePaymentRecord;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.BrokerSubmitOnlineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.IntraActorCryptoTransactionManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

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

    public BrokerSubmitOnlineMerchandiseMonitorAgent(
            PluginDatabaseSystem pluginDatabaseSystem,
            LogManager logManager,
            ErrorManager errorManager,
            EventManager eventManager,
            UUID pluginId,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            OutgoingIntraActorManager outgoingIntraActorManager) throws CantSetObjectException {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.logManager=logManager;
        this.transactionTransmissionManager=transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager=customerBrokerContractPurchaseManager;
        this.outgoingIntraActorManager=outgoingIntraActorManager;
        this.customerBrokerContractSaleManager=customerBrokerContractSaleManager;
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
                } catch (CannotSendContractHashException | CantUpdateRecordException | CantSendContractNewStatusNotificationException e) {
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
                CantSendContractNewStatusNotificationException {

            try{
                brokerSubmitOnlineMerchandiseBusinessTransactionDao =new BrokerSubmitOnlineMerchandiseBusinessTransactionDao(
                        pluginDatabaseSystem,
                        pluginId,
                        database);

                UUID outgoingCryptoTransactionId;
                com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.CustomerOnlinePaymentRecord customerOnlinePaymentRecord;
                String contractHash;

                /**
                 * Check if there is some transaction to crypto de stock
                 */

                /**
                 * Check if there is some crypto to send
                 */

                //TODO: finish this

                /**
                 * Check contract status to send.
                 */


                /**
                 * Check pending notifications - Customer side
                 */


                /**
                 * Check pending transactions
                 */

                /**
                 * Check if pending to submit crypto status
                 */


                /**
                 * Check if on crypto network crypto status
                 */


                /**
                 * Check if on blockchain crypto status
                 */


                /**
                 * Check if pending events
                 */



            } catch (Exception e) {
                //TODO: Remove this exception handler, is only for compilation
                throw new CannotSendContractHashException(
                        e,
                        "Sending contract hash",
                        "Cannot get the contract list from database");
            } /*catch (UnexpectedResultReturnedFromDatabaseException e) {
                throw new CannotSendContractHashException(
                        e,
                        "Sending contract hash",
                        "Unexpected result in database");
            }  /*catch (CantSendBusinessTransactionHashException e) {
                throw new CannotSendContractHashException(
                        e,
                        "Sending contract hash",
                        "Error in Transaction Transmission Network Service");
            }
            catch (CantSendContractNewStatusNotificationException e) {
                throw new CantSendContractNewStatusNotificationException(
                        CantSendContractNewStatusNotificationException.DEFAULT_MESSAGE,
                        e,
                        "Sending contract hash",
                        "Error in Transaction Transmission Network Service");
            } catch (OutgoingIntraActorInsufficientFundsException e) {
                //TODO: I want to get a better handler for this exception
                e.printStackTrace();
            } catch (OutgoingIntraActorCantSendFundsExceptions outgoingIntraActorCantSendFundsExceptions) {
                outgoingIntraActorCantSendFundsExceptions.printStackTrace();
            } catch (OutgoingIntraActorCantGetCryptoStatusException e) {
                e.printStackTrace();
            }*/

        }

        //TODO: raise the proper event
        private void raisePaymentConfirmationEvent(){
            /*FermatEvent fermatEvent = eventManager.getNewEvent(EventType.CUSTOMER_ONLINE_PAYMENT_CONFIRMED);
            CustomerOnlinePaymentConfirmed customerOnlinePaymentConfirmed = (CustomerOnlinePaymentConfirmed) fermatEvent;
            customerOnlinePaymentConfirmed.setSource(EventSource.CUSTOMER_ONLINE_PAYMENT);
            eventManager.raiseEvent(customerOnlinePaymentConfirmed);*/
        }

        //TODO: raise an event only in broker side, notifying the incoming online payment. Create the event.
        //TODO: raise an event only in customer side, notifying the reception of an online payment. Create the event.
        private void checkPendingTransaction(
                CustomerOnlinePaymentRecord customerOnlinePaymentRecord)throws CantUpdateRecordException
        {
            //TODO: implement this.
        }

        private void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {

            //TODO: implement this.

        }

    }

}

