package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantConfirmRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantGetRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantInformApprovalException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantInformDenialException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantInformReceptionException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantInformRefusalException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantListPendingRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantSendRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequestManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.database.CryptoPaymentRequestNetworkServiceDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantDeleteRequestException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantListRequestsException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;

/**
 * TODO This plugin do.
 *
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public class CryptoPaymentRequestNetworkServicePluginRoot implements
        CryptoPaymentRequestManager,
        DealsWithErrors,
        DealsWithEvents,
        DealsWithPluginDatabaseSystem,
        Plugin,
        Service {

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithEvents Interface member variables
     */
    private EventManager eventManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Plugin Interface member variables.
     */
    private UUID pluginId;

    /**
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;


    private CryptoPaymentRequestNetworkServiceDao cryptoPaymentRequestNetworkServiceDao;

    @Override
    public void sendCryptoPaymentRequest(String                walletPublicKey  ,
                                         String                identityPublicKey,
                                         Actors                identityType     ,
                                         String                actorPublicKey   ,
                                         Actors                actorType        ,
                                         CryptoAddress         cryptoAddress    ,
                                         String                description      ,
                                         long                  amount           ,
                                         BlockchainNetworkType networkType      ) throws CantSendRequestException {

    }

    @Override
    public void informRefusal(UUID requestId) throws RequestNotFoundException   ,
                                                     CantInformRefusalException {

        try {

            // TODO SEND MESSAGE WITH REFUSAL.
            // TODO AFTER THAT, IF SUCCESSFUL, DELETE THE REQUEST.

            cryptoPaymentRequestNetworkServiceDao.deleteRequest(requestId);

        } catch(CantDeleteRequestException |
                RequestNotFoundException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw new CantInformRefusalException(e, "", "Error in Crypto Payment Request NS Dao.");
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantInformRefusalException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void informDenial(UUID requestId) throws RequestNotFoundException  ,
                                                    CantInformDenialException {

        try {

            // TODO SEND MESSAGE WITH DENIAL.
            // TODO AFTER THAT, IF SUCCESSFUL, DELETE THE REQUEST.

            cryptoPaymentRequestNetworkServiceDao.deleteRequest(requestId);

        } catch(CantDeleteRequestException |
                RequestNotFoundException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw new CantInformDenialException(e, "", "Error in Crypto Payment Request NS Dao.");
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantInformDenialException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void informApproval(UUID requestId) throws CantInformApprovalException,
                                                      RequestNotFoundException   {

        try {

            // TODO SEND MESSAGE WITH APPROVAL.
            // TODO AFTER THAT, IF SUCCESSFUL, DELETE THE REQUEST.

            cryptoPaymentRequestNetworkServiceDao.deleteRequest(requestId);

        } catch(CantDeleteRequestException |
                RequestNotFoundException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw new CantInformApprovalException(e, "", "Error in Crypto Payment Request NS Dao.");
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantInformApprovalException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void informReception(UUID requestId) throws CantInformReceptionException, RequestNotFoundException {

        try {

            // TODO SEND MESSAGE WITH RECEPTION CONFIRMATION.
            // TODO AFTER THAT, IF SUCCESSFUL, DELETE THE REQUEST.

            cryptoPaymentRequestNetworkServiceDao.deleteRequest(requestId);

        } catch(CantDeleteRequestException |
                RequestNotFoundException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw new CantInformReceptionException(e, "", "Error in Crypto Payment Request NS Dao.");
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantInformReceptionException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void confirmRequest(UUID requestId) throws CantConfirmRequestException,
                                                      RequestNotFoundException   {

        try {

            cryptoPaymentRequestNetworkServiceDao.deleteRequest(requestId);

        } catch(CantDeleteRequestException |
                RequestNotFoundException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw new CantConfirmRequestException(e, "", "Error in Crypto Payment Request NS Dao.");
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantConfirmRequestException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public CryptoPaymentRequest getRequestById(UUID requestId) throws CantGetRequestException  ,
                                                                      RequestNotFoundException {

        try {

            return cryptoPaymentRequestNetworkServiceDao.getRequestById(requestId);

        } catch(CantGetRequestException |
                RequestNotFoundException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw e;
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantGetRequestException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoPaymentRequest> listPendingRequests() throws CantListPendingRequestsException {

        try {

            return cryptoPaymentRequestNetworkServiceDao.listRequestsByProtocolState(
                    RequestProtocolState.PENDING_ACTION
            );

        } catch(CantListRequestsException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw new CantListPendingRequestsException(e, "", "Error in Crypto Payment Request NS Dao.");
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantListPendingRequestsException(e, "", "Unhandled Exception.");
        }

    }

    /*
         * Service Interface implementation
         */
    @Override
    public void start() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    private void reportUnexpectedException(Exception e) {
        this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    /**
     * DealsWithErrors Interface implementation
     */
    @Override
    public void setErrorManager(final ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithEvents Interface implementation
     */
    @Override
    public void setEventManager(final EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation
     */
    @Override
    public void setPluginDatabaseSystem(final PluginDatabaseSystem pluginDatabaseSystemManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystemManager;
    }

    /**
     * Plugin Interface implementation.
     */
    @Override
    public void setId(final UUID pluginId) {
        this.pluginId = pluginId;
    }

}
