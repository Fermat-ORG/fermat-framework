package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault.CryptoVaultManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
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
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantGetNegotiationTransactionListException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantSendCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Yordin Alayn on 05.07.16.
 */
public class CustomerBrokerCloseAgent2 extends AbstractAgent {

    private Database                                                database;
    private Thread                                                  agentThread;
    private LogManager                                              logManager;
    private EventManager                                            eventManager;
    private NegotiationTransactionCustomerBrokerClosePluginRoot     pluginRoot;
    private PluginDatabaseSystem                                    pluginDatabaseSystem;
    private UUID                                                    pluginId;
    private CustomerBrokerCloseNegotiationTransactionDatabaseDao    dao;
    private NegotiationTransmissionManager                          negotiationTransmissionManager;
    private CustomerBrokerPurchaseNegotiation                       customerBrokerPurchaseNegotiation;
    private CustomerBrokerPurchaseNegotiationManager                customerBrokerPurchaseNegotiationManager;
    private CustomerBrokerSaleNegotiation                           customerBrokerSaleNegotiation;
    private CustomerBrokerSaleNegotiationManager                    customerBrokerSaleNegotiationManager;
    private CryptoAddressBookManager                                cryptoAddressBookManager;
    private CryptoVaultManager                                      cryptoVaultManager;
    private WalletManagerManager                                    walletManagerManager;
    private IntraWalletUserIdentityManager                          intraWalletUserIdentityManager;

    private int                 iterationConfirmSend    = 0;
    private Map<UUID,Integer>   transactionSend         = new HashMap<>();
    
    public CustomerBrokerCloseAgent2(
            long                                                    sleepTime,
            TimeUnit                                                timeUnit,
            long                                                    initDelayTime,
            PluginDatabaseSystem                                    pluginDatabaseSystem,
            LogManager                                              logManager,
            NegotiationTransactionCustomerBrokerClosePluginRoot     pluginRoot,
            EventManager                                            eventManager,
            UUID                                                    pluginId,
            CustomerBrokerCloseNegotiationTransactionDatabaseDao    dao,
            NegotiationTransmissionManager                          negotiationTransmissionManager,
            CustomerBrokerPurchaseNegotiation                       customerBrokerPurchaseNegotiation,
            CustomerBrokerSaleNegotiation                           customerBrokerSaleNegotiation,
            CustomerBrokerPurchaseNegotiationManager                customerBrokerPurchaseNegotiationManager,
            CustomerBrokerSaleNegotiationManager                    customerBrokerSaleNegotiationManager,
            CryptoAddressBookManager                                cryptoAddressBookManager,
            CryptoVaultManager                                      cryptoVaultManager,
            WalletManagerManager                                    walletManagerManager,
            IntraWalletUserIdentityManager                          intraWalletUserIdentityManager
    ){

        super(sleepTime, timeUnit, initDelayTime);

        this.pluginDatabaseSystem                       = pluginDatabaseSystem;
        this.logManager                                 = logManager;
        this.pluginRoot                                 = pluginRoot;
        this.eventManager                               = eventManager;
        this.pluginId                                   = pluginId;
        this.dao                                        = dao;
        this.negotiationTransmissionManager             = negotiationTransmissionManager;
        this.customerBrokerPurchaseNegotiation          = customerBrokerPurchaseNegotiation;
        this.customerBrokerSaleNegotiation              = customerBrokerSaleNegotiation;
        this.customerBrokerPurchaseNegotiationManager   = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerSaleNegotiationManager       = customerBrokerSaleNegotiationManager;
        this.cryptoAddressBookManager                   = cryptoAddressBookManager;
        this.cryptoVaultManager                         = cryptoVaultManager;
        this.walletManagerManager                       = walletManagerManager;
        this.intraWalletUserIdentityManager             = intraWalletUserIdentityManager;
        
    }

    @Override
    protected Runnable agentJob() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                try{

                    doTheMainTask();

                } catch (
                        CantSendCustomerBrokerCloseNegotiationTransactionException | 
                        CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException | 
                        CantUpdateRecordException e) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        };
        return runnable;
    }

    @Override
    protected void onErrorOccur() {
        pluginRoot.reportError(
                UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                new Exception("CustomerBrokerCloseAgent2 Error"));
    }

    private void doTheMainTask() throws
            CantSendCustomerBrokerCloseNegotiationTransactionException,
            CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException,
            CantUpdateRecordException
    {

        try {

            String                              negotiationXML;
            NegotiationType negotiationType;
            UUID                                transactionId;
            List<CustomerBrokerClose> negotiationPendingToSubmitList;
            CustomerBrokerPurchaseNegotiation   purchaseNegotiation = new NegotiationPurchaseRecord();
            CustomerBrokerSaleNegotiation       saleNegotiation     = new NegotiationSaleRecord();
            int                                 timeConfirmSend     = 20;

            //SEND NEGOTIATION PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_SUBMIT)
            negotiationPendingToSubmitList = dao.getPendingToSubmitNegotiation();
            if(!negotiationPendingToSubmitList.isEmpty()){
                for(CustomerBrokerClose negotiationTransaction: negotiationPendingToSubmitList){

                    System.out.print("\n\n**** 5) NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - NEGOTIATION FOR SEND transactionId: " + negotiationTransaction.getTransactionId() + " ****\n");

                    negotiationXML  = negotiationTransaction.getNegotiationXML();
                    negotiationType = negotiationTransaction.getNegotiationType();
                    transactionId   = negotiationTransaction.getTransactionId();

                    switch (negotiationType){
                        case PURCHASE:
                            purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                            System.out.print("\n\n**** 6) NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - PURCHASE NEGOTIATION SEND negotiationId(XML): " + purchaseNegotiation.getNegotiationId() + " ****\n" +
                                    "\n - Status :" + purchaseNegotiation.getStatus().getCode());
                            //SEND NEGOTIATION TO BROKER
                            negotiationTransmissionManager.sendNegotiationToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_CLOSE);

                            break;
                        case SALE:
                            saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML,saleNegotiation);
                            System.out.print("\n\n**** 6) NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - SALE NEGOTIATION SEND negotiationId(XML): " + saleNegotiation.getNegotiationId() + " ****\n" +
                                    "\n - Status :" + saleNegotiation.getStatus().getCode());
                            //SEND NEGOTIATION TO CUSTOMER
                            negotiationTransmissionManager.sendNegotiationToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_CLOSE);

                            break;
                    }

                    //Update the Negotiation Transaction
                    System.out.print("\n\n**** 7) NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - UPDATE STATUS SALE NEGOTIATION STATUS : " + NegotiationTransactionStatus.SENDING_NEGOTIATION.getCode() + " ****\n");
                    dao.updateStatusRegisterCustomerBrokerCloseNegotiationTranasction(
                            transactionId,
                            NegotiationTransactionStatus.SENDING_NEGOTIATION);

                }
            }

            //SEND CONFIRM PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_CONFIRMATION)
            negotiationPendingToSubmitList = dao.getPendingToConfirmtNegotiation();
            if(!negotiationPendingToSubmitList.isEmpty()){
                for(CustomerBrokerClose negotiationTransaction: negotiationPendingToSubmitList){

                    transactionId   = negotiationTransaction.getTransactionId();
                    negotiationType = negotiationTransaction.getNegotiationType();

                    System.out.print("\n\n**** 23) NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - UPDATE CONFIRM STATUS SALE NEGOTIATION STATUS : " + NegotiationTransactionStatus.SENDING_NEGOTIATION.getCode() + " ****\n");

                    switch (negotiationType) {
                        case PURCHASE:
                            System.out.print("\n\n**** 24) NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - PURCHASE NEGOTIATION SEND CONFIRM negotiationId(XML): " + purchaseNegotiation.getNegotiationId() + " ****\n");
                            //SEND CONFIRM NEGOTIATION TO BROKER
                            negotiationTransmissionManager.sendConfirmNegotiationToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_CLOSE);
                            break;
                        case SALE:
                            System.out.print("\n\n**** 24) NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - SALE NEGOTIATION SEND CONFIRM negotiationId(XML): " + purchaseNegotiation.getNegotiationId() + " ****\n");
                            //SEND NEGOTIATION TO CUSTOMER
                            negotiationTransmissionManager.sendConfirmNegotiationToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_CLOSE);
                            break;
                    }

                    System.out.print("\n\n**** 25) NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - UPDATE STATUS SALE NEGOTIATION STATUS : " + NegotiationTransactionStatus.CONFIRM_NEGOTIATION.getCode() + " ****\n");
                    //Update the Negotiation Transaction
                    dao.updateStatusRegisterCustomerBrokerCloseNegotiationTranasction(
                            transactionId,
                            NegotiationTransactionStatus.CONFIRM_NEGOTIATION);

                    //CONFIRM TRANSACTION IS DONE
                    dao.confirmTransaction(transactionId);
                }
            }

            //PROCES PENDING EVENT
            List<UUID> pendingEventsIdList=dao.getPendingEvents();
            for(UUID eventId : pendingEventsIdList){
                checkPendingEvent(eventId);
            }

            //SEND TRNSACTION AGAIN IF NOT IS CONFIRM
            /*iterationConfirmSend++;
            if(timeConfirmSend == iterationConfirmSend){
                CustomerBrokerCloseForwardTransaction forwardTransaction = new CustomerBrokerCloseForwardTransaction(
                        dao,
                        pluginRoot,
                        transactionSend
                );

                forwardTransaction.pendingToConfirmtTransaction();
                transactionSend = forwardTransaction.getTransactionSend();

                iterationConfirmSend = 0;
            }*/

        } catch (CantSendNegotiationToCryptoBrokerException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSendCustomerBrokerCloseNegotiationTransactionException(CantSendCustomerBrokerCloseNegotiationTransactionException.DEFAULT_MESSAGE,e,"Sending Purchase Negotiation","Error in Negotiation Transmission Network Service");
        } catch (CantSendNegotiationToCryptoCustomerException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSendCustomerBrokerCloseNegotiationTransactionException(CantSendCustomerBrokerCloseNegotiationTransactionException.DEFAULT_MESSAGE,e,"Sending Sale Negotiation","Error in Negotiation Transmission Network Service");
        } catch (CantSendConfirmToCryptoBrokerException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException(CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Confirm Purchase Negotiation", "Error in Negotiation Transmission Network Service");
        } catch (CantSendConfirmToCryptoCustomerException e){
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException(CantSendCustomerBrokerCloseConfirmationNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Confirm Sale Negotiation", "Error in Negotiation Transmission Network Service");
        } catch (CantGetNegotiationTransactionListException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSendCustomerBrokerCloseNegotiationTransactionException(CantSendCustomerBrokerCloseNegotiationTransactionException.DEFAULT_MESSAGE, e,"Sending Negotiation","Cannot get the Negotiation list from database");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSendCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e),"Sending Negotiation","UNKNOWN FAILURE.");
        }
        
    }

    //CHECK PENDING EVEN
    private void checkPendingEvent(UUID eventId) throws UnexpectedResultReturnedFromDatabaseException {

        try {
            UUID                                transactionId;
            UUID                                transmissionId;
            NegotiationTransmission negotiationTransmission;
            NegotiationTransaction negotiationTransaction;
            NegotiationType                     negotiationType;
            String                              negotiationXML;
            
            CustomerBrokerClosePurchaseNegotiationTransaction   customerBrokerClosePurchaseNegotiationTransaction;
            CustomerBrokerCloseSaleNegotiationTransaction       customerBrokerCloseSaleNegotiationTransaction;
                    
            CustomerBrokerPurchaseNegotiation   purchaseNegotiation = new NegotiationPurchaseRecord();
            CustomerBrokerSaleNegotiation       saleNegotiation     = new NegotiationSaleRecord();

            String eventTypeCode = dao.getEventType(eventId);

            //EVENT - RECEIVE NEGOTIATION
            if (eventTypeCode.equals(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_CLOSE.getCode())) {
                List<Transaction<NegotiationTransmission>> pendingTransactionList = negotiationTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                for (Transaction<NegotiationTransmission> record : pendingTransactionList) {

                    negotiationTransmission = record.getInformation();
                    negotiationXML          = negotiationTransmission.getNegotiationXML();
                    transmissionId          = negotiationTransmission.getTransmissionId();
                    transactionId           = negotiationTransmission.getTransactionId();
                    negotiationType         = negotiationTransmission.getNegotiationType();

                    System.out.print("\n\n**** 20.1) NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - RECEIVE TRANSACTION  ****\n");
                    if (negotiationXML != null) {

                        negotiationTransaction = dao.getRegisterCustomerBrokerCloseNegotiationTranasction(transactionId);

                        if(negotiationTransmission.getTransmissionType().equals(NegotiationTransmissionType.TRANSMISSION_NEGOTIATION)) {

                            if(negotiationTransaction == null) {
                                switch (negotiationType) {
                                    case PURCHASE:
                                        System.out.print("\n\n**** 20.2) NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - RECEIVE TRANSACTION PURCHASE ****\n");
                                        //CLOSE PURCHASE NEGOTIATION
                                        purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                                        customerBrokerClosePurchaseNegotiationTransaction = new CustomerBrokerClosePurchaseNegotiationTransaction(
                                                customerBrokerPurchaseNegotiationManager,
                                                dao,
                                                cryptoAddressBookManager,
                                                cryptoVaultManager,
                                                walletManagerManager,
                                                pluginRoot,
                                                intraWalletUserIdentityManager
                                        );
                                        customerBrokerClosePurchaseNegotiationTransaction.receivePurchaseNegotiationTranasction(transactionId, purchaseNegotiation);
                                        break;
                                    case SALE:
                                        System.out.print("\n\n**** 20.2) NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - RECEIVE TRANSACTION SALE ****\n");
                                        //CLOSE SALE NEGOTIATION
                                        saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);
                                        customerBrokerCloseSaleNegotiationTransaction = new CustomerBrokerCloseSaleNegotiationTransaction(
                                                customerBrokerSaleNegotiationManager,
                                                dao,
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

                                System.out.print("\n**** 20.2) NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - CREATE NEGOTIATION TRANSACTION REPEAT SEND ****\n");
                                //CONFIRM TRANSACTION
                                dao.updateStatusRegisterCustomerBrokerCloseNegotiationTranasction(
                                        transactionId,
                                        NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM);

                            }

                        } else if(negotiationTransmission.getTransmissionType().equals(NegotiationTransmissionType.TRANSMISSION_CONFIRM)) {

                            if(!negotiationTransaction.getStatusTransaction().getCode().equals(NegotiationTransactionStatus.CONFIRM_NEGOTIATION.getCode())) {

                                switch (negotiationType) {
                                    case PURCHASE:
                                        System.out.print("\n\n**** 27.2) NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - RECEIVE CONFIRM PURCHASE ****\n");
                                        //UPDATE NEGOTIATION IF PAYMENT IS CRYPTO CURRENCY
                                        purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                                        customerBrokerClosePurchaseNegotiationTransaction = new CustomerBrokerClosePurchaseNegotiationTransaction(
                                                customerBrokerPurchaseNegotiationManager,
                                                dao,
                                                cryptoAddressBookManager,
                                                cryptoVaultManager,
                                                walletManagerManager,
                                                pluginRoot,
                                                intraWalletUserIdentityManager
                                        );
                                        customerBrokerClosePurchaseNegotiationTransaction.receivePurchaseConfirm(purchaseNegotiation);
                                        break;
                                    case SALE:
                                        System.out.print("\n\n**** 27.2) NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - RECEIVE CONFIRM SALE ****\n");
                                        //UPDATE NEGOTIATION IF MERCHANDISE IS CRYPTO CURRENCY
                                        saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);
                                        customerBrokerCloseSaleNegotiationTransaction = new CustomerBrokerCloseSaleNegotiationTransaction(
                                                customerBrokerSaleNegotiationManager,
                                                dao,
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
                                dao.updateStatusRegisterCustomerBrokerCloseNegotiationTranasction(transactionId, NegotiationTransactionStatus.CONFIRM_NEGOTIATION);
                            }

                            //CONFIRM TRANSACTION IS DONE
                            dao.confirmTransaction(transactionId);

                        }

                        //NOTIFIED EVENT
                        dao.updateEventTansactionStatus(eventId, EventStatus.NOTIFIED);
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
