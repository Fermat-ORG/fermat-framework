package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantConfirmRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantInformDenialException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantInformReceptionException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantListPendingRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequestManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentType;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGenerateCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGetCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CryptoPaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPayment;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.database.CryptoPaymentRequestDao;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantChangeCryptoPaymentRequestStateException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantExecuteCryptoPaymentRequestPendingEventActionsException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantHandleCryptoPaymentRequestApprovedEventException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantHandleCryptoPaymentRequestConfirmedReceptionEventException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantHandleCryptoPaymentRequestDeniedEventException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantHandleCryptoPaymentRequestReceivedEventException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantHandleCryptoPaymentRequestRefusedEventException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestEventActionsException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantGetInstalledWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.DefaultWalletNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure.CryptoPaymentRequestEventActions</code>
 * contains all the methods related with the handling of the events raised by the crypto payment request network service.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/10/2015.
 */
public class CryptoPaymentRequestEventActions {

    private final CryptoPaymentRequestManager cryptoPaymentRequestManager;
    private final PluginDatabaseSystem        pluginDatabaseSystem       ;
    private final UUID                        pluginId                   ;
    private final WalletManagerManager        walletManagerManager       ;
    private final   EventManager eventManager;
    private final Broadcaster                 broadcaster;

    private CryptoPaymentRequestDao cryptoPaymentRequestDao;

    public CryptoPaymentRequestEventActions(final CryptoPaymentRequestManager cryptoPaymentRequestManager,
                                            final PluginDatabaseSystem        pluginDatabaseSystem       ,
                                            final UUID                        pluginId                   ,
                                            final WalletManagerManager        walletManagerManager       ,
                                            final   EventManager eventManager,
                                            final Broadcaster broadcaster) {

        this.cryptoPaymentRequestManager = cryptoPaymentRequestManager;
        this.pluginDatabaseSystem        = pluginDatabaseSystem       ;
        this.pluginId                    = pluginId                   ;
        this.walletManagerManager        = walletManagerManager       ;
        this.eventManager                = eventManager               ;
        this.broadcaster                = broadcaster;
    }

    public void initialize() throws CantInitializeCryptoPaymentRequestEventActionsException {

        try {

            cryptoPaymentRequestDao = new CryptoPaymentRequestDao(pluginDatabaseSystem, pluginId);
            cryptoPaymentRequestDao.initialize();

        } catch (CantInitializeCryptoPaymentRequestDatabaseException e) {

            throw new CantInitializeCryptoPaymentRequestEventActionsException(e);
        }
    }

    public void executePendingRequestEventActions() throws CantExecuteCryptoPaymentRequestPendingEventActionsException {

        List<CryptoPaymentRequest> cryptoPaymentRequestList;
        try {

            cryptoPaymentRequestList = cryptoPaymentRequestManager.listPendingRequests();

        } catch(CantListPendingRequestsException e) {

            throw new CantExecuteCryptoPaymentRequestPendingEventActionsException(e, "", "Error in network service.");
        }

        String  errorString   = ""   ;
        boolean errorHandlingEvents = false;

        for (final CryptoPaymentRequest cpr: cryptoPaymentRequestList) {
            try {
                switch (cpr.getAction()) {
                    case INFORM_APPROVAL :
                        this.handleCryptoPaymentRequestApproved(cpr.getRequestId());
                        break;
                    case INFORM_DENIAL   :
                        this.handleCryptoPaymentRequestDenied(cpr.getRequestId());
                        break;
                    case INFORM_REFUSAL  :
                        this.handleCryptoPaymentRequestRefused(cpr.getRequestId());
                        break;
                    case INFORM_RECEPTION:
                        this.handleCryptoPaymentRequestConfirmedReception(cpr.getRequestId());
                        break;
                    case REQUEST:
                        this.handleCryptoPaymentRequestReceived(cpr);
                        break;
                }

            } catch(CantHandleCryptoPaymentRequestApprovedEventException           |
                    CantHandleCryptoPaymentRequestDeniedEventException             |
                    CantHandleCryptoPaymentRequestReceivedEventException           |
                    CantHandleCryptoPaymentRequestRefusedEventException            |
                    CantHandleCryptoPaymentRequestConfirmedReceptionEventException e) {

                errorHandlingEvents = true;
                errorString += cpr.getAction()+" ERROR for REQUEST ID "+ cpr.getRequestId() + "\n"+e.getMessage()+"\n";
            }
        }

        if(errorHandlingEvents)
            throw new CantExecuteCryptoPaymentRequestPendingEventActionsException(errorString, "Error trying to execute a pending action.");

    }

    /**
     * first, i change the state to approved.
     * then i confirm the request, to delete it in the network service.
     */
    public void handleCryptoPaymentRequestApproved(UUID requestId) throws CantHandleCryptoPaymentRequestApprovedEventException {

        try {

            cryptoPaymentRequestDao.changeState(
                    requestId,
                    CryptoPaymentState.APPROVED
            );

            cryptoPaymentRequestManager.confirmRequest(requestId);

        } catch(CantChangeCryptoPaymentRequestStateException |
                CryptoPaymentRequestNotFoundException        e) {

            throw new CantHandleCryptoPaymentRequestApprovedEventException(e, "RequestId: "+requestId, "Error trying to change the state.");
        } catch(CantConfirmRequestException |
                RequestNotFoundException    e) {
            // TODO what to do in case i didn't find the request.
            throw new CantHandleCryptoPaymentRequestApprovedEventException(e, "RequestId: "+requestId, "Error in network service.");
        } catch(Exception e) {

            throw new CantHandleCryptoPaymentRequestApprovedEventException(e, "RequestId: "+requestId, "Unhandled exception.");
        }
    }

    /**
     * first, i change the state to pending_response.
     * then i confirm the request, to delete it in the network service.
     */
    public void handleCryptoPaymentRequestConfirmedReception(UUID requestId) throws CantHandleCryptoPaymentRequestConfirmedReceptionEventException {

        try {

            cryptoPaymentRequestDao.changeState(
                    requestId,
                    CryptoPaymentState.PENDING_RESPONSE
            );

            cryptoPaymentRequestManager.confirmRequest(requestId);

        } catch(CantChangeCryptoPaymentRequestStateException |
                CryptoPaymentRequestNotFoundException        e) {

            throw new CantHandleCryptoPaymentRequestConfirmedReceptionEventException(e, "RequestId: "+requestId, "Error trying to change the state.");
        } catch(CantConfirmRequestException |
                RequestNotFoundException    e) {
            // TODO what to do in case i didn't find the request.
            throw new CantHandleCryptoPaymentRequestConfirmedReceptionEventException(e, "RequestId: "+requestId, "Error in network service.");
        } catch(Exception e) {

            throw new CantHandleCryptoPaymentRequestConfirmedReceptionEventException(e, "RequestId: "+requestId, "Unhandled exception.");
        }
    }

    /**
     * first, i change the state to denied.
     * then i confirm the request, to delete it in the network service.
     */
    public void handleCryptoPaymentRequestDenied(UUID requestId) throws CantHandleCryptoPaymentRequestDeniedEventException {

        try {

            cryptoPaymentRequestDao.changeState(
                    requestId,
                    CryptoPaymentState.DENIED_BY_INCOMPATIBILITY
            );

            cryptoPaymentRequestManager.confirmRequest(requestId);

            CryptoPayment record = cryptoPaymentRequestDao.getRequestById(requestId);

            InstalledWallet installedWallet = walletManagerManager.getDefaultWallet(
                    record.getCryptoAddress().getCryptoCurrency(),
                    record.getIdentityType(),
                    record.getNetworkType()
            );

            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, installedWallet.getWalletPublicKey(), "PAYMENTDENIED_" + requestId.toString());


        } catch(CantChangeCryptoPaymentRequestStateException |
                CryptoPaymentRequestNotFoundException        e) {

            throw new CantHandleCryptoPaymentRequestDeniedEventException(e, "RequestId: "+requestId, "Error trying to change the state.");
        } catch(CantConfirmRequestException |
                RequestNotFoundException    e) {
            // TODO what to do in case i didn't find the request.
            throw new CantHandleCryptoPaymentRequestDeniedEventException(e, "RequestId: "+requestId, "Error in network service.");
        } catch(Exception e) {

            throw new CantHandleCryptoPaymentRequestDeniedEventException(e, "RequestId: "+requestId, "Unhandled exception.");
        }
    }

    /**
     * if the request is already saved i inform the reception
     * if not, i generate it and inform the reception
     *          to generate the crypto request, i need a installed wallet where i can assing it
     *          if i don't find one, i have to inform the denial.
     * else i do nothing
     */
    public void handleCryptoPaymentRequestReceived(final CryptoPaymentRequest cryptoPaymentRequest) throws CantHandleCryptoPaymentRequestReceivedEventException {

        try {

            try {
                // check if exists, if exists, i inform the reception.
                // if not, i handle CryptoPaymentRequestNotFoundException and generate the crypto payment request.
                cryptoPaymentRequestDao.getRequestById(cryptoPaymentRequest.getRequestId());

                cryptoPaymentRequestManager.informReception(cryptoPaymentRequest.getRequestId());

                /*InstalledWallet installedWallet = walletManagerManager.getDefaultWallet(
                        cryptoPaymentRequest.getCryptoAddress().getCryptoCurrency(),
                        cryptoPaymentRequest.getIdentityType(),
                        cryptoPaymentRequest.getNetworkType()
                );*/

                broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, cryptoPaymentRequest.getWalletPublicKey(), "PAYMENTREQUEST_" + cryptoPaymentRequest.getRequestId().toString());

            } catch (CantGetCryptoPaymentRequestException e) {

                throw new CantHandleCryptoPaymentRequestReceivedEventException(e, "RequestId: " + cryptoPaymentRequest.getRequestId(), "Error trying to find the record in crypto payments table.");
            } catch(CantInformReceptionException |
                    RequestNotFoundException     e ) {
                 // TODO what to do in case i didn't find the request.
                throw new CantHandleCryptoPaymentRequestReceivedEventException(e, "RequestId: " + cryptoPaymentRequest.getRequestId(), "Error in network service.");
            }

        } catch(CryptoPaymentRequestNotFoundException e) {

            try {
                // then i go to find the new request and a compatible installed wallet to assign it.

                   /* InstalledWallet installedWallet = walletManagerManager.getDefaultWallet(
                            cryptoPaymentRequest.getCryptoAddress().getCryptoCurrency(),
                            cryptoPaymentRequest.getIdentityType(),
                            cryptoPaymentRequest.getNetworkType()
                    );*/

                CryptoPaymentType type = CryptoPaymentType.RECEIVED;
                CryptoPaymentState state = CryptoPaymentState.PENDING_RESPONSE;

                // save the record in database

                cryptoPaymentRequestDao.generateCryptoPaymentRequest(
                        cryptoPaymentRequest.getRequestId(),
                        cryptoPaymentRequest.getWalletPublicKey(),
                        cryptoPaymentRequest.getIdentityPublicKey(),
                        cryptoPaymentRequest.getIdentityType(),
                        cryptoPaymentRequest.getActorPublicKey(),
                        cryptoPaymentRequest.getActorType(),
                        cryptoPaymentRequest.getCryptoAddress(),
                        cryptoPaymentRequest.getDescription(),
                        cryptoPaymentRequest.getAmount(),
                        cryptoPaymentRequest.getStartTimeStamp(),
                        type,
                        state,
                        cryptoPaymentRequest.getNetworkType(),
                        cryptoPaymentRequest.getReferenceWallet()
                );

                cryptoPaymentRequestManager.informReception(cryptoPaymentRequest.getRequestId());

                broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, cryptoPaymentRequest.getWalletPublicKey(), "PAYMENTREQUEST_" + cryptoPaymentRequest.getRequestId().toString());


               /* } catch(DefaultWalletNotFoundException z) {

                    cryptoPaymentRequestManager.informDenial(cryptoPaymentRequest.getRequestId());

                }

            } catch(CantInformDenialException    |
                    CantInformReceptionException |
                    RequestNotFoundException     z ) {
                // TODO what to do in case i didn't find the request.
                throw new CantHandleCryptoPaymentRequestReceivedEventException(z, "RequestId: " + cryptoPaymentRequest.getRequestId(), "Error in network service.");
            } catch(CantGetInstalledWalletException z ) {

                throw new CantHandleCryptoPaymentRequestReceivedEventException(z, "RequestId: " + cryptoPaymentRequest.getRequestId(), "Error in wallet manager manager.");*/
            } catch (CantGenerateCryptoPaymentRequestException z) {

                throw new CantHandleCryptoPaymentRequestReceivedEventException(z, "RequestId: " + cryptoPaymentRequest.getRequestId(), "Error in the generation of the request.");
            } catch (Exception z) {

                throw new CantHandleCryptoPaymentRequestReceivedEventException(z, "RequestId: " + cryptoPaymentRequest.getRequestId(), "Unhandled Exception.");
            }
        }

    }

    /**
     * first, i change the state to refused.
     * then i confirm the request, to delete it in the network service.
     */
    public void handleCryptoPaymentRequestRefused(UUID requestId) throws CantHandleCryptoPaymentRequestRefusedEventException {

        try {

            cryptoPaymentRequestDao.changeState(
                    requestId,
                    CryptoPaymentState.REFUSED
            );

            cryptoPaymentRequestManager.confirmRequest(requestId);

            CryptoPayment record = cryptoPaymentRequestDao.getRequestById(requestId);

            InstalledWallet installedWallet = walletManagerManager.getDefaultWallet(
                    record.getCryptoAddress().getCryptoCurrency(),
                    record.getIdentityType(),
                    record.getNetworkType()
            );

            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, installedWallet.getWalletPublicKey(), "PAYMENTDENIED_" + requestId.toString());


        } catch(CantChangeCryptoPaymentRequestStateException |
                CryptoPaymentRequestNotFoundException        e) {

            throw new CantHandleCryptoPaymentRequestRefusedEventException(e, "RequestId: "+requestId, "Error trying to change the state.");
        } catch(CantConfirmRequestException |
                RequestNotFoundException    e) {
            // TODO what to do in case i didn't find the request.
            throw new CantHandleCryptoPaymentRequestRefusedEventException(e, "RequestId: "+requestId, "Error in network service.");
        } catch(Exception e) {

            throw new CantHandleCryptoPaymentRequestRefusedEventException(e, "RequestId: "+requestId, "Unhandled exception.");
        }
    }


}
