package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
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
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.database.CommunicationLayerNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.database.CommunicationLayerNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.event_handlers.CompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.event_handlers.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.event_handlers.CompleteRequestListComponentRegisteredNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.exceptions.CantInitializeNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.structure.CryptoPaymentRequestNetworkServiceConnectionManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.database.CryptoPaymentRequestNetworkServiceDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantDeleteRequestException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantListRequestsException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.EventType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.DealsWithWsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
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
        DealsWithWsCommunicationsCloudClientManager,
        DealsWithErrors,
        DealsWithEvents,
        DealsWithPluginDatabaseSystem,
        NetworkService,
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
    private List<FermatEventListener> listenersAdded = new ArrayList<>();

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

    /**
     * Represent the wsCommunicationsCloudClientManager
     */
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    /**
     *  Represent the remoteNetworkServicesRegisteredList
     */
    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    /**
     * Represent the cryptoPaymentRequestNetworkServiceConnectionManager
     */
    private CryptoPaymentRequestNetworkServiceConnectionManager cryptoPaymentRequestNetworkServiceConnectionManager;

    /**
     * Represent the platformComponentProfile
     */
    private PlatformComponentProfile platformComponentProfile;

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Represent the identity
     */
    private ECCKeyPair identity;

    /**
     * Represent the register
     */
    private boolean register;


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

    /**
     * Service Interface implementation
     */
    @Override
    public void start() throws CantStartPluginException {

        /*
         * Validate required resources
         */
        validateInjectedResources();

        // adding communication network service common events listeners
        addCryptoPaymentRequestListener(EventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION           , new CompleteComponentRegistrationNotificationEventHandler(this));
        addCryptoPaymentRequestListener(EventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION, new CompleteRequestListComponentRegisteredNotificationEventHandler(this));
        addCryptoPaymentRequestListener(EventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION     , new CompleteComponentConnectionRequestNotificationEventHandler(this));

        this.serviceStatus = ServiceStatus.STARTED;
    }

    private void addCryptoPaymentRequestListener(final FermatEventEnum fermatEventEnum,
                                                 final FermatEventHandler fermatEventHandler) {

        FermatEventListener fermatEventListener = eventManager.getNewListener(fermatEventEnum);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);
    }

    /**
     * This method initialize the database
     *
     * @throws CantInitializeNetworkServiceDatabaseException
     */
    private void initializeCommunicationDb() throws CantInitializeNetworkServiceDatabaseException {

        try {

            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationLayerNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeNetworkServiceDatabaseException(cantOpenDatabaseException);

        } catch (DatabaseNotFoundException e) {

            CommunicationLayerNetworkServiceDatabaseFactory communicationLayerNetworkServiceDatabaseFactory = new CommunicationLayerNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                this.dataBase = communicationLayerNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationLayerNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {

                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeNetworkServiceDatabaseException(cantCreateDatabaseException);

            }
        }

    }

    /**
     * This method validate is all required resource are injected into
     * the plugin root by the platform
     *
     * @throws CantStartPluginException
     */
    private void validateInjectedResources() throws CantStartPluginException {

         /*
         * If all resources are inject
         */
        if (wsCommunicationsCloudClientManager == null ||
            pluginDatabaseSystem               == null ||
            errorManager                       == null ||
            eventManager                       == null ) {


            String context =
                    "Plugin ID:                          " + pluginId                           + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                    "wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                    "pluginDatabaseSystem:               " + pluginDatabaseSystem               + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                    "errorManager:                       " + errorManager                       + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                    "eventManager:                       " + eventManager;

            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;

        }

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
     * (non-Javadoc)
     * @see NetworkService#getId()
     */
    @Override
    public UUID getId() {
        return this.pluginId;
    }

    /**
     * (non-javadoc)
     * @see NetworkService#getRemoteNetworkServicesRegisteredList()
     */
    @Override
    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
        return remoteNetworkServicesRegisteredList;
    }

    /**
     * (non-javadoc)
     * @see NetworkService#requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters)
     */
    @Override
    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters){

        System.out.println("CryptoPaymentNetworkServicePluginRoot - requestRemoteNetworkServicesRegisteredList");

        /*
         * Request the list of component registers
         */
        wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(discoveryQueryParameters);

    }

    /**
     * (non-javadoc)
     * @see NetworkService#getNetworkServiceConnectionManager()
     */
    @Override
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return cryptoPaymentRequestNetworkServiceConnectionManager;
    }

    /**
     * (non-javadoc)
     * @see NetworkService#constructDiscoveryQueryParamsFactory(PlatformComponentProfile, String, String, Location, Double, String, String, Integer, Integer, PlatformComponentType, NetworkServiceType)
     */
    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(final PlatformComponentProfile applicant                     ,
                                                                         final String                   alias                         ,
                                                                         final String                   identityPublicKey             ,
                                                                         final Location                 location                      ,
                                                                         final Double                   distance                      ,
                                                                         final String                   name                          ,
                                                                         final String                   extraData                     ,
                                                                         final Integer                  firstRecord                   ,
                                                                         final Integer                  numRegister                   ,
                                                                         final PlatformComponentType    fromOtherPlatformComponentType,
                                                                         final NetworkServiceType       fromOtherNetworkServiceType   ) {

        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructDiscoveryQueryParamsFactory(
                applicant                     ,
                alias                         ,
                identityPublicKey             ,
                location                      ,
                distance                      ,
                name                          ,
                extraData                     ,
                firstRecord                   ,
                numRegister                   ,
                fromOtherPlatformComponentType,
                fromOtherNetworkServiceType
        );
    }

    /**
     * This method initialize the cryptoPaymentRequestNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the RegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfile for this component
     */
    public void initializeCryptoPaymentRequestNetworkServiceConnectionManager(){
        this.cryptoPaymentRequestNetworkServiceConnectionManager = new CryptoPaymentRequestNetworkServiceConnectionManager(
                platformComponentProfile,
                identity,
                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(),
                dataBase,
                errorManager,
                eventManager
        );
    }

    /**
     * Get the IdentityPublicKey
     *
     * @return String
     */
    public String getIdentityPublicKey(){
        return this.identity.getPublicKey();
    }

    /**
     * Get the PlatformComponentProfile
     *
     * @return PlatformComponentProfile
     */
    public PlatformComponentProfile getPlatformComponentProfile() {
        return platformComponentProfile;
    }

    /**
     * Set the PlatformComponentProfile
     *
     * @param platformComponentProfile
     */
    public void setPlatformComponentProfile(final PlatformComponentProfile platformComponentProfile) {
        this.platformComponentProfile = platformComponentProfile;
    }

    /**
     * Get is Register
     * @return boolean
     */
    public boolean isRegister() {
        return register;
    }

    /**
     * Handles the events CompleteComponentRegistrationNotification
     */
    public void handleCompleteComponentRegistrationNotificationEvent(final PlatformComponentProfile platformComponentProfileRegistered){

    }

    /**
     * Handles the events CompleteRequestListComponentRegisteredNotificationEvent
     */
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(final List<PlatformComponentProfile> platformComponentProfileRegisteredList){

        System.out.println(" CryptoPaymentRequestNetworkServiceConnectionManager - Starting method handleCompleteRequestListComponentRegisteredNotificationEvent");

    }

    /**
     * Handles the events CompleteRequestListComponentRegisteredNotificationEvent
     */
    public void handleCompleteComponentConnectionRequestNotificationEvent(final PlatformComponentProfile remoteComponentProfile){

    }

    private void reportUnexpectedException(final Exception e) {
        this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
    }

    /**
     * DealsWithWsCommunicationCloudClient Interface implementation
     */
    @Override
    public void setWsCommunicationsCloudClientConnectionManager(final WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager) {
        this.wsCommunicationsCloudClientManager = wsCommunicationsCloudClientManager;
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
