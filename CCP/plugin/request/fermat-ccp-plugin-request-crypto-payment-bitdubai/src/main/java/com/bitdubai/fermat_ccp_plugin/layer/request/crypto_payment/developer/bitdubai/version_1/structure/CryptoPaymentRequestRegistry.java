package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantInformApprovalException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantInformRefusalException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantSendRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequestManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentType;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantApproveCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGenerateCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGetCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantListCryptoPaymentRequestsException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantRejectCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantUpdateRequestPaymentStateException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CryptoPaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPayment;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentRegistry;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.IntraActorCryptoTransactionManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.database.CryptoPaymentRequestDao;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantChangeCryptoPaymentRequestStateException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantExecuteUnfinishedActionsException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestRegistryException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure.CryptoPaymentRequestRegistry</code>
 * haves all the methods related with the crypto payment request negotiation.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/10/2015.
 */
public class CryptoPaymentRequestRegistry implements CryptoPaymentRegistry {

    private final CryptoPaymentRequestManager cryptoPaymentRequestManager;
    private final ErrorManager                errorManager               ;
    private final OutgoingIntraActorManager   outgoingIntraActorManager  ;
    private final PluginDatabaseSystem        pluginDatabaseSystem       ;
    private final UUID                        pluginId                   ;
    private Broadcaster                       broadcaster;

    private CryptoPaymentRequestDao cryptoPaymentRequestDao;

    public CryptoPaymentRequestRegistry(final CryptoPaymentRequestManager cryptoPaymentRequestManager,
                                        final ErrorManager                errorManager               ,
                                        final OutgoingIntraActorManager   outgoingIntraActorManager  ,
                                        final PluginDatabaseSystem        pluginDatabaseSystem       ,
                                        final UUID                        pluginId                   ,
                                        final Broadcaster                 broadcaster) {

        this.cryptoPaymentRequestManager = cryptoPaymentRequestManager;
        this.errorManager                = errorManager               ;
        this.outgoingIntraActorManager   = outgoingIntraActorManager  ;
        this.pluginDatabaseSystem        = pluginDatabaseSystem       ;
        this.pluginId                    = pluginId                   ;
        this.broadcaster                 = broadcaster                ;
    }

    public void initialize() throws CantInitializeCryptoPaymentRequestRegistryException {

        try {

            cryptoPaymentRequestDao = new CryptoPaymentRequestDao(pluginDatabaseSystem, pluginId);
            cryptoPaymentRequestDao.initialize();

        } catch (CantInitializeCryptoPaymentRequestDatabaseException e) {

            throw new CantInitializeCryptoPaymentRequestRegistryException(e);
        }
    }

    @Override
    public void generateCryptoPaymentRequest(String                walletPublicKey  ,
                                             String                identityPublicKey,
                                             Actors                identityType     ,
                                             String                actorPublicKey   ,
                                             Actors                actorType        ,
                                             CryptoAddress         cryptoAddress    ,
                                             String                description      ,
                                             long                  amount           ,
                                             BlockchainNetworkType networkType      ,
                                             ReferenceWallet       referenceWallet) throws CantGenerateCryptoPaymentRequestException {


        System.out.println("********** Crypto Payment Request -> generating request. SENT - NOT_SENT_YET.");
        try {
            /**
             * i generate the crypto payment request setting:
             *
             * -requestId:   id for the request.
             * -type:        own (sent by me).
             * -state:       not sent yet (once i sent throw network service i will give the state pending response.
             * -startTime    time when i generate the crypto request.
             */

            UUID requestId = UUID.randomUUID();

            Long startTimeStamp = System.currentTimeMillis();

            CryptoPaymentType  type  = CryptoPaymentType .SENT;
            CryptoPaymentState state = CryptoPaymentState.NOT_SENT_YET;

            // save the record in database

            cryptoPaymentRequestDao.generateCryptoPaymentRequest(
                    requestId,
                    walletPublicKey,
                    identityPublicKey,
                    identityType,
                    actorPublicKey,
                    actorType,
                    cryptoAddress,
                    description,
                    amount,
                    startTimeStamp,
                    type,
                    state,
                    networkType,
                    referenceWallet
            );

            // if i can save it, i send it to the network service.

            System.out.println("********** Crypto Payment Request -> generating request. SENT - NOT_SENT_YET -> WAITING RECEPTION CONFIRMATION.");

            fromNotSentYetToWaitingReceptionConfirmation(
                    requestId,
                    identityPublicKey,
                    identityType,
                    actorPublicKey,
                    actorType,
                    cryptoAddress,
                    description,
                    amount,
                    startTimeStamp,
                    networkType,
                    referenceWallet
            );

            System.out.println("********** Crypto Payment Request -> generating request. SENT - WAITING RECEPTION CONFIRMATION -> OK.");

        } catch(CantGenerateCryptoPaymentRequestException e) {

            reportUnexpectedException(e);
            throw e;
        } catch(CantSendRequestException e) {

            reportUnexpectedException(e);
            throw new CantGenerateCryptoPaymentRequestException(e, "", "Error sending the crypto request by the network service.");
        } catch(CantChangeCryptoPaymentRequestStateException e) {

            reportUnexpectedException(e);
            throw new CantGenerateCryptoPaymentRequestException(e, "", "Can't change the crypto payment request state to PENDING_RESPONSE but already sent throw network service.");
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantGenerateCryptoPaymentRequestException(e, "", "Unhandled Exception.");
        }
    }

    private void fromNotSentYetToWaitingReceptionConfirmation(UUID                  requestId        ,
                                                              String                identityPublicKey,
                                                              Actors                identityType     ,
                                                              String                actorPublicKey   ,
                                                              Actors                actorType        ,
                                                              CryptoAddress         cryptoAddress    ,
                                                              String                description      ,
                                                              long                  amount           ,
                                                              long                  startTimeStamp   ,
                                                              BlockchainNetworkType networkType,
                                                              ReferenceWallet       referenceWallet) throws CantSendRequestException                     ,
                                                                                                              CantChangeCryptoPaymentRequestStateException ,
                                                                                                              CryptoPaymentRequestNotFoundException        {

        // if i can save it, i send it to the network service.

        cryptoPaymentRequestManager.sendCryptoPaymentRequest(
                requestId,
                identityPublicKey,
                identityType,
                actorPublicKey,
                actorType,
                cryptoAddress,
                description,
                amount,
                startTimeStamp,
                networkType,
                referenceWallet
        );

        // change the state to waiting reception confirmation

        cryptoPaymentRequestDao.changeState(
                requestId,
                CryptoPaymentState.WAITING_RECEPTION_CONFIRMATION
        );
    }

    @Override
    public void refuseRequest(UUID requestId) throws CantRejectCryptoPaymentRequestException,
                                                     CryptoPaymentRequestNotFoundException  {

        try {

            //i inform the refusal throw the network service and after that i change the state to refused.

            try {

                cryptoPaymentRequestManager.informRefusal(
                        requestId
                );

            } catch(RequestNotFoundException e) {
                // this should not happen, but if it happens i change the state of the request to error.

                cryptoPaymentRequestDao.changeState(
                        requestId,
                        CryptoPaymentState.ERROR
                );

                throw new CantRejectCryptoPaymentRequestException(e, "requestId: " + requestId, "The network service cannot recognize the crypto payment request id.");
            }

            // if i can inform the refusal i change the state to refused

            cryptoPaymentRequestDao.changeState(
                    requestId,
                    CryptoPaymentState.REFUSED
            );

        } catch(CantInformRefusalException e) {

            reportUnexpectedException(e);
            throw new CantRejectCryptoPaymentRequestException(e, "", "Error refusing the crypto request by the network service.");
        } catch(CantChangeCryptoPaymentRequestStateException e) {

            reportUnexpectedException(e);
            throw new CantRejectCryptoPaymentRequestException(e, "", "Error changing the crypto request state to refused.");
        } catch(CantRejectCryptoPaymentRequestException |
                CryptoPaymentRequestNotFoundException   e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw e;
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantRejectCryptoPaymentRequestException(e, "", "Unhandled Exception.");
        }

    }

    @Override
    public void approveRequest(UUID requestId) throws CantApproveCryptoPaymentRequestException,
                                                      CryptoPaymentRequestNotFoundException   ,
                                                      InsufficientFundsException              {

        try {

            /**
             * i start the process changing the state to in approving process.
             * next to it, i made the sending of crypto throw outgoing intra actor.
             * if its all ok, i change the state to payment process started, else, i return to the initial pending_response state
             * i inform the approval throw network service, and after that i change the state to approved.
             */

            CryptoPayment cryptoPayment = cryptoPaymentRequestDao.getRequestById(requestId);



            cryptoPaymentRequestDao.changeState(requestId, CryptoPaymentState.IN_APPROVING_PROCESS);

            fromInApprovingProcessToPaymentProcessStarted(requestId, cryptoPayment);

            fromPaymentProcessStartedToApproved(requestId);

        } catch(CantInformApprovalException e) {

            reportUnexpectedException(e);
            throw new CantApproveCryptoPaymentRequestException(e, "", "Error aproving the crypto request by the network service.");
        } catch(CantChangeCryptoPaymentRequestStateException e) {

            reportUnexpectedException(e);
            throw new CantApproveCryptoPaymentRequestException(e, "", "Error changing the crypto request state to approved.");
        } catch(CantGetCryptoPaymentRequestException e) {

            reportUnexpectedException(e);
            throw new CantApproveCryptoPaymentRequestException(e, "", "Error trying to get the crypto request state.");
        } catch(CantApproveCryptoPaymentRequestException |
                CryptoPaymentRequestNotFoundException   e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw e;
        } catch(InsufficientFundsException e) {
            // i just throw the exception
            throw e;
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantApproveCryptoPaymentRequestException(e, "", "Unhandled Exception.");
        }

    }

    /**
     * send money throw outgoing intra actor
     * if all ok i set the request in payment process started
     * if not i set in pending response (initial state).
     */
    private void fromInApprovingProcessToPaymentProcessStarted(UUID          requestId    ,
                                                               CryptoPayment cryptoPayment) throws CantChangeCryptoPaymentRequestStateException,
            CryptoPaymentRequestNotFoundException       ,
            CantApproveCryptoPaymentRequestException    ,
            InsufficientFundsException                  {

        try {
            IntraActorCryptoTransactionManager transactionManager = outgoingIntraActorManager.getTransactionManager();
            transactionManager.payCryptoRequest(
                    requestId,
                    cryptoPayment.getWalletPublicKey(),
                    cryptoPayment.getCryptoAddress(),
                    cryptoPayment.getAmount(),
                    cryptoPayment.getDescription(),
                    cryptoPayment.getIdentityPublicKey(),
                    cryptoPayment.getActorPublicKey(),
                    cryptoPayment.getIdentityType(),
                    cryptoPayment.getActorType(),
                    cryptoPayment.getReferenceWallet(),
                    cryptoPayment.getNetworkType()
            );

            cryptoPaymentRequestDao.changeState(
                    requestId,
                    CryptoPaymentState.PAYMENT_PROCESS_STARTED
            );

        } catch(OutgoingIntraActorCantSendFundsExceptions            |
                CantGetOutgoingIntraActorTransactionManagerException e) {
            // if there's an error here we return to the initial state.
            cryptoPaymentRequestDao.changeState(
                    requestId,
                    CryptoPaymentState.PENDING_RESPONSE
            );
            throw new CantApproveCryptoPaymentRequestException(e, "", "There's an error trying to send the crypto.");
        } catch(OutgoingIntraActorInsufficientFundsException e) {
            // this exception is controllable
            // if there's not founds, we return to the initial state.
            cryptoPaymentRequestDao.changeState(
                    requestId,
                    CryptoPaymentState.PENDING_RESPONSE
            );
            throw new InsufficientFundsException(e);
        }
    }

    /**
     * i inform the approval throw the network service and after that i change the state to approved.
     */
    private void fromPaymentProcessStartedToApproved(UUID requestId) throws CantInformApprovalException                 ,
                                                                        CantApproveCryptoPaymentRequestException    ,
                                                                        CantChangeCryptoPaymentRequestStateException,
                                                                        CryptoPaymentRequestNotFoundException {
        try {

            cryptoPaymentRequestManager.informApproval(
                    requestId
            );

        } catch(RequestNotFoundException e) {
            // this should not happen, but if it happens i change the state of the request to error.

            cryptoPaymentRequestDao.changeState(
                    requestId,
                    CryptoPaymentState.ERROR
            );

            throw new CantApproveCryptoPaymentRequestException(e, "requestId: " + requestId, "The network service cannot recognize the crypto payment request id.");
        }

        // if i can inform the refusal i change the state to refused

        cryptoPaymentRequestDao.changeState(
                requestId,
                CryptoPaymentState.APPROVED
        );
    }

    @Override
    public CryptoPayment getRequestById(UUID requestId) throws CantGetCryptoPaymentRequestException,
                                                               CryptoPaymentRequestNotFoundException {

        try {

            return cryptoPaymentRequestDao.getRequestById(requestId);

        } catch(CantGetCryptoPaymentRequestException |
                CryptoPaymentRequestNotFoundException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw e;
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantGetCryptoPaymentRequestException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoPayment> listCryptoPaymentRequests(String  walletPublicKey,
                                                         Integer max            ,
                                                         Integer offset         ) throws CantListCryptoPaymentRequestsException {
        try {

            return cryptoPaymentRequestDao.listCryptoPaymentRequests(
                    walletPublicKey,
                    max,
                    offset
            );

        } catch(CantListCryptoPaymentRequestsException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw e;
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantListCryptoPaymentRequestsException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoPayment> listCryptoPaymentRequestsByState(String             walletPublicKey,
                                                                CryptoPaymentState state          ,
                                                                Integer            max            ,
                                                                Integer            offset         ) throws CantListCryptoPaymentRequestsException {

        try {

            return cryptoPaymentRequestDao.listCryptoPaymentRequestsByState(
                    walletPublicKey,
                    state,
                    max,
                    offset
            );

        } catch(CantListCryptoPaymentRequestsException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw e;
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantListCryptoPaymentRequestsException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoPayment> listCryptoPaymentRequestsByType(String            walletPublicKey,
                                                               CryptoPaymentType type           ,
                                                               BlockchainNetworkType blockchainNetworkType,
                                                               Integer           max            ,
                                                               Integer           offset         ) throws CantListCryptoPaymentRequestsException {

        try {

            return cryptoPaymentRequestDao.listCryptoPaymentRequestsByType(
                    walletPublicKey,
                    type,
                    blockchainNetworkType,
                    max,
                    offset
            );

        } catch(CantListCryptoPaymentRequestsException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw e;
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantListCryptoPaymentRequestsException(e, "", "Unhandled Exception.");
        }

    }

    @Override
    public List<CryptoPayment> listCryptoPaymentRequestsByTypeAndNetwork(String            walletPublicKey,
                                                               CryptoPaymentType type           ,
                                                                BlockchainNetworkType blockchainNetworkType,
                                                               Integer           max            ,
                                                               Integer           offset         ) throws CantListCryptoPaymentRequestsException {

        try {

            return cryptoPaymentRequestDao.listCryptoPaymentRequestsByTypeAndNetwork(
                    walletPublicKey,
                    type,
                    blockchainNetworkType,
                    max,
                    offset
            );

        } catch(CantListCryptoPaymentRequestsException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw e;
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantListCryptoPaymentRequestsException(e, "", "Unhandled Exception.");
        }

    }


    @Override
    public List<CryptoPayment> listCryptoPaymentRequestsByTypeAndState(String             walletPublicKey,
                                                                       CryptoPaymentState state          ,
                                                                       CryptoPaymentType  type           ,
                                                                       Integer            max            ,
                                                                       Integer            offset         ) throws CantListCryptoPaymentRequestsException {

        try {

            return cryptoPaymentRequestDao.listCryptoPaymentRequestsByStateAndType(
                    walletPublicKey,
                    state,
                    type,
                    max,
                    offset
            );

        } catch(CantListCryptoPaymentRequestsException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw e;
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantListCryptoPaymentRequestsException(e, "", "Unhandled Exception.");
        }
    }

    public void executeUnfinishedActions() throws CantExecuteUnfinishedActionsException {

        String  errorString   = ""   ;
        boolean errorHandlingEvents = false;

        try {
            final List<CryptoPayment> cryptoPaymentList = cryptoPaymentRequestDao.listUnfinishedActions();

            for(final CryptoPayment cryptoPayment : cryptoPaymentList) {

                switch (cryptoPayment.getState()) {
                    case NOT_SENT_YET:

                        try {

                            fromNotSentYetToWaitingReceptionConfirmation(
                                    cryptoPayment.getRequestId(),
                                    cryptoPayment.getIdentityPublicKey(),
                                    cryptoPayment.getIdentityType(),
                                    cryptoPayment.getActorPublicKey(),
                                    cryptoPayment.getActorType(),
                                    cryptoPayment.getCryptoAddress(),
                                    cryptoPayment.getDescription(),
                                    cryptoPayment.getAmount(),
                                    cryptoPayment.getStartTimeStamp(),
                                    cryptoPayment.getNetworkType(),
                                    cryptoPayment.getReferenceWallet()
                            );

                        } catch(CantSendRequestException                     |
                                CantChangeCryptoPaymentRequestStateException |
                                CryptoPaymentRequestNotFoundException        e) {

                            errorHandlingEvents = true;
                            errorString += cryptoPayment.getState()+" ERROR for REQUEST ID "+ cryptoPayment.getRequestId() + "\n"+e.getMessage()+"\n";
                        }

                        try {

                            fromInApprovingProcessToPaymentProcessStarted(cryptoPayment.getRequestId(), cryptoPayment);

                        } catch(InsufficientFundsException                   |
                                CantChangeCryptoPaymentRequestStateException |
                                CantApproveCryptoPaymentRequestException     |
                                CryptoPaymentRequestNotFoundException        e) {

                            errorHandlingEvents = true;
                            errorString += cryptoPayment.getState()+" ERROR for REQUEST ID "+ cryptoPayment.getRequestId() + "\n"+e.getMessage()+"\n";
                        }

                        try {

                            fromPaymentProcessStartedToApproved(cryptoPayment.getRequestId());

                        } catch(CantInformApprovalException                  |
                                CantChangeCryptoPaymentRequestStateException |
                                CantApproveCryptoPaymentRequestException     |
                                CryptoPaymentRequestNotFoundException        e) {

                            errorHandlingEvents = true;
                            errorString += cryptoPayment.getState()+" ERROR for REQUEST ID "+ cryptoPayment.getRequestId() + "\n"+e.getMessage()+"\n";
                        }

                    case IN_APPROVING_PROCESS:
                        try {

                            fromInApprovingProcessToPaymentProcessStarted(cryptoPayment.getRequestId(), cryptoPayment);

                        } catch(InsufficientFundsException                   |
                                CantChangeCryptoPaymentRequestStateException |
                                CantApproveCryptoPaymentRequestException     |
                                CryptoPaymentRequestNotFoundException        e) {

                            errorHandlingEvents = true;
                            errorString += cryptoPayment.getState()+" ERROR for REQUEST ID "+ cryptoPayment.getRequestId() + "\n"+e.getMessage()+"\n";
                        }

                        try {

                            fromPaymentProcessStartedToApproved(cryptoPayment.getRequestId());

                        } catch(CantInformApprovalException                  |
                                CantChangeCryptoPaymentRequestStateException |
                                CantApproveCryptoPaymentRequestException     |
                                CryptoPaymentRequestNotFoundException        e) {

                            errorHandlingEvents = true;
                            errorString += cryptoPayment.getState()+" ERROR for REQUEST ID "+ cryptoPayment.getRequestId() + "\n"+e.getMessage()+"\n";
                        }
                        break;
                    case PAYMENT_PROCESS_STARTED:
                        try {

                            fromPaymentProcessStartedToApproved(cryptoPayment.getRequestId());

                        } catch(CantInformApprovalException                  |
                                CantChangeCryptoPaymentRequestStateException |
                                CantApproveCryptoPaymentRequestException     |
                                CryptoPaymentRequestNotFoundException        e) {

                            errorHandlingEvents = true;
                            errorString += cryptoPayment.getState()+" ERROR for REQUEST ID "+ cryptoPayment.getRequestId() + "\n"+e.getMessage()+"\n";
                        }
                        break;

                }
            }

        } catch(CantListCryptoPaymentRequestsException e){

            throw new CantExecuteUnfinishedActionsException(e, "", "Error trying to get unfinished actions.");
        }

        if(errorHandlingEvents)
            throw new CantExecuteUnfinishedActionsException(errorString, "Error trying to execute a pending action.");
    }

    @Override
    public void acceptIncomingRequest(UUID requestId) throws CantUpdateRequestPaymentStateException{
        try
        {
            cryptoPaymentRequestDao.changeState(requestId, CryptoPaymentState.APPROVED);
        }
        catch(CantChangeCryptoPaymentRequestStateException e)
        {
            reportUnexpectedException(e);
            throw new CantUpdateRequestPaymentStateException(e, "", "Error updated record.");
        }
        catch(CryptoPaymentRequestNotFoundException e)
        {
            reportUnexpectedException(e);
            throw new CantUpdateRequestPaymentStateException(e, "", "Cannot find a CryptoPaymentRequest with the given id..");
        }

    }

    @Override
    public void revertOutgoingRequest(UUID requestId) throws CantUpdateRequestPaymentStateException{
        try
        {
            cryptoPaymentRequestDao.changeState(requestId, CryptoPaymentState.ERROR);

            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, "PAYMENTERROR|" + requestId.toString());

        }
        catch(CantChangeCryptoPaymentRequestStateException e)
        {
            reportUnexpectedException(e);
            throw new CantUpdateRequestPaymentStateException(e, "", "Error updated record.");
        }
        catch(CryptoPaymentRequestNotFoundException e)
        {
            reportUnexpectedException(e);
            throw new CantUpdateRequestPaymentStateException(e, "", "Cannot find a CryptoPaymentRequest with the given id..");
        }

    }

    private void reportUnexpectedException(Exception e) {
        this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

}
