package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FeatureForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.ProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantAcceptAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantConfirmAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantDenyAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantGetPendingAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantSendAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingAddressExchangeRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.AddressExchangeRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.database.CommunicationLayerNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.database.CommunicationLayerNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers.CompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers.CompleteRequestListComponentRegisteredNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.exceptions.CantInitializeNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.structure.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.structure.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.event_handlers.NewReceiveMessagesNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantCreateRequestException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantHandleNewMessagesException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressesNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantReceiveAcceptanceException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantReceiveDenialException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantReceiveRequestException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.AcceptMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.DenyMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.NetworkServiceMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressesExecutorAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.DealsWithWsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This plugin manages the exchange of crypto addresses between actors.
 *
 * Created by Leon Acosta (laion.cj91@gmail.com) on 22/09/2015.
 */
public class CryptoAddressesNetworkServicePluginRoot extends AbstractPlugin implements
        CryptoAddressesManager,
        DealsWithWsCommunicationsCloudClientManager,
        DealsWithErrors,
        DealsWithEvents,
        DealsWithPluginDatabaseSystem,
        NetworkService {

    @Override
    public List<AddonVersionReference> getNeededAddonReferences() {
        return new ArrayList<>();
    }

    @Override
    public List<PluginVersionReference> getNeededPluginReferences() {
        return new ArrayList<>();
    }

    @Override
    public List<DevelopersUtilReference> getAvailableDeveloperUtils() {
        return new ArrayList<>();
    }

    @Override
    protected void validateAndAssignReferences() {

    }

    @Override
    public FeatureForDevelopers getFeatureForDevelopers(final DevelopersUtilReference developersUtilReference) throws CantGetFeatureForDevelopersException {
        return null;
    }

    /**
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * DealWithEvents Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;
    private List<FermatEventListener> listenersAdded;
    public final static EventSource EVENT_SOURCE = EventSource.NETWORK_SERVICE_CRYPTO_ADDRESSES;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;


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
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Represent the identity
     */
    private ECCKeyPair identity;

    /**
     * Represent the platformComponentProfile
     */
    private PlatformComponentProfile platformComponentProfile;

    /**
     * Crypto Payment Request Network Service details.
     */
    private final PlatformComponentType platformComponentType;
    private final NetworkServiceType networkServiceType;
    private final String name;
    private final String alias;
    private final String extraData;

    /**
     * Represent the register
     */
    private boolean register;

    /**
     * Represent the registrationProcessNetworkServiceAgent
     */
    private CommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent;

    private CryptoAddressesExecutorAgent cryptoAddressesExecutorAgent;

    private CryptoAddressesNetworkServiceDao cryptoAddressesNetworkServiceDao;

    public CryptoAddressesNetworkServicePluginRoot() {
        super();
        this.listenersAdded = new ArrayList<>();
        this.platformComponentType = PlatformComponentType.NETWORK_SERVICE;
        this.networkServiceType    = NetworkServiceType.CRYPTO_ADDRESSES;
        this.name                  = "Crypto Addresses Network Service";
        this.alias                 = "CryptoAddressesNetworkService";
        this.extraData             = null;
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


        // initialize crypto payment request dao

        try {

            cryptoAddressesNetworkServiceDao = new CryptoAddressesNetworkServiceDao(pluginDatabaseSystem, pluginId);

            cryptoAddressesNetworkServiceDao.initialize();

        } catch(CantInitializeCryptoAddressesNetworkServiceDatabaseException e) {

            CantStartPluginException pluginStartException = new CantStartPluginException(e, "", "Problem initializing crypto addresses dao.");
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
        }

        try {

            /*
             * Create a new key pair for this execution
             */
            identity = new ECCKeyPair();

            /*
             * Initialize the data base
             */
            initializeCommunicationDb();

            /*
             * Verify if the communication cloud client is active
             */
            if (!wsCommunicationsCloudClientManager.isDisable()){

                /*
                 * Initialize the agent and start
                 */
                communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection());
                communicationRegistrationProcessNetworkServiceAgent.start();
            }

            // adding communication network service common events listeners
            addCryptoAddressesListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION, new CompleteComponentRegistrationNotificationEventHandler(this));
            addCryptoAddressesListener(P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION, new CompleteRequestListComponentRegisteredNotificationEventHandler(this));
            addCryptoAddressesListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION, new CompleteComponentConnectionRequestNotificationEventHandler(this));


            addCryptoAddressesListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_SENT_NOTIFICATION, new NewReceiveMessagesNotificationEventHandler(null));

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (CantInitializeNetworkServiceDatabaseException exception) {

            String context =
                    "Plugin ID:     " + pluginId + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                            "Database Name: " + CommunicationLayerNetworkServiceDatabaseConstants.DATA_BASE_NAME;

            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
        }


    }

    private void addCryptoAddressesListener(final FermatEventEnum fermatEventEnum,
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

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeNetworkServiceDatabaseException(cantOpenDatabaseException);

        } catch (DatabaseNotFoundException e) {

            CommunicationLayerNetworkServiceDatabaseFactory communicationLayerNetworkServiceDatabaseFactory = new CommunicationLayerNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                this.dataBase = communicationLayerNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationLayerNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {

                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateDatabaseException);
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

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;

        }

    }

    @Override
    public void pause() {

        // pause connections manager.
        communicationNetworkServiceConnectionManager.pause();

        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {

        // resume connections manager.
        communicationNetworkServiceConnectionManager.resume();

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {

        // remove all listeners from the event manager and from the plugin.
        for (FermatEventListener listener: listenersAdded)
            eventManager.removeListener(listener);

        listenersAdded.clear();

        // close all connections.
        communicationNetworkServiceConnectionManager.closeAllConnection();

        // set to not registered.
        register = Boolean.FALSE;

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_SEND.
     * - Action        : REQUEST.
     * - Type          : SENT.
     */
    @Override
    public void sendAddressExchangeRequest(final String                walletPublicKey            ,
                                           final CryptoCurrency        cryptoCurrency             ,
                                           final Actors                identityTypeRequesting     ,
                                           final Actors                identityTypeResponding     ,
                                           final String                identityPublicKeyRequesting,
                                           final String                identityPublicKeyResponding,
                                           final BlockchainNetworkType blockchainNetworkType      ) throws CantSendAddressExchangeRequestException {

        try {

            UUID newId = UUID.randomUUID();

            ProtocolState state  = ProtocolState.PROCESSING_SEND;
            RequestType   type   = RequestType  .SENT           ;
            RequestAction action = RequestAction.REQUEST        ;

            cryptoAddressesNetworkServiceDao.createAddressExchangeRequest(
                    newId,
                    walletPublicKey,
                    cryptoCurrency,
                    identityTypeRequesting,
                    identityTypeResponding,
                    identityPublicKeyRequesting,
                    identityPublicKeyResponding,
                    state,
                    type,
                    action,
                    blockchainNetworkType
            );

        } catch (CantCreateRequestException e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSendAddressExchangeRequestException(e, null, "Error trying to create the request.");
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSendAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : ACCEPT.
     * - Protocol State: PROCESSING_SEND.
     */
    @Override
    public void acceptAddressExchangeRequest(final UUID          requestId    ,
                                             final CryptoAddress cryptoAddress) throws CantAcceptAddressExchangeRequestException,
                                                                                       PendingRequestNotFoundException          {

        try {

            ProtocolState protocolState = ProtocolState.PROCESSING_SEND;
            cryptoAddressesNetworkServiceDao.acceptAddressExchangeRequest(requestId, cryptoAddress, protocolState);

        } catch (CantAcceptAddressExchangeRequestException | PendingRequestNotFoundException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * we'll return to the actor all the pending requests pending a local action.
     * State : PENDING_ACTION.
     *
     * @param actorType  type of actor asking for pending requests
     *
     * @throws CantListPendingAddressExchangeRequestsException      if something goes wrong.
     */
    @Override
    public List<AddressExchangeRequest> listPendingRequests(Actors actorType) throws CantListPendingAddressExchangeRequestsException {

        try {

            return cryptoAddressesNetworkServiceDao.listPendingRequestsByActorType(actorType);

        } catch (CantListPendingAddressExchangeRequestsException e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingAddressExchangeRequestsException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    @Override
    public AddressExchangeRequest getPendingRequest(UUID requestId) throws CantGetPendingAddressExchangeRequestException,
                                                                           PendingRequestNotFoundException              {

        try {

            return cryptoAddressesNetworkServiceDao.getPendingRequest(requestId);

        } catch (PendingRequestNotFoundException e){
            // when i don't find it i only pass the exception (maybe another plugin confirm the pending request).
            throw e;
        } catch (CantGetPendingAddressExchangeRequestException e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetPendingAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * when i confirm a request i put it in the final state, indicating:
     * State : DONE.
     * Action: NONE.
     *
     * @param requestId id of the address exchange request we want to confirm.
     *
     * @throws CantConfirmAddressExchangeRequestException   if something goes wrong.
     * @throws PendingRequestNotFoundException              if i can't find the record.
     */
    @Override
    public void confirmAddressExchangeRequest(UUID requestId) throws CantConfirmAddressExchangeRequestException,
                                                                     PendingRequestNotFoundException           {

        try {

            cryptoAddressesNetworkServiceDao.confirmAddressExchangeRequest(requestId);

        } catch (CantConfirmAddressExchangeRequestException | PendingRequestNotFoundException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * when i deny a request i indicate the ns agent to do the next action:
     * State : PROCESSING_SEND.
     * Action: DENY.
     *
     * @param requestId id of the address exchange request we want to confirm.
     *
     * @throws CantDenyAddressExchangeRequestException      if something goes wrong.
     * @throws PendingRequestNotFoundException              if i can't find the record.
     */
    @Override
    public void denyAddressExchangeRequest(UUID requestId) throws CantDenyAddressExchangeRequestException,
                                                                  PendingRequestNotFoundException        {

        try {

            ProtocolState protocolState = ProtocolState.PROCESSING_SEND;
            cryptoAddressesNetworkServiceDao.denyAddressExchangeRequest(requestId, protocolState);

        } catch(PendingRequestNotFoundException |
                CantDenyAddressExchangeRequestException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDenyAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
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

        System.out.println("CryptoAddressesNetworkServicePluginRoot - requestRemoteNetworkServicesRegisteredList");

        try {

            /*
             * Request the list of component registers
             */
            wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(platformComponentProfile, discoveryQueryParameters);

        } catch (CantRequestListException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

        }

    }
    /**
     * This method initialize the cryptoPaymentRequestNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the RegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfile for this component
     */
    public void initializeCommunicationNetworkServiceConnectionManager(){
        this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(
                platformComponentProfile,
                identity,
                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(),
                dataBase,
                errorManager,
                eventManager
        );
    }

    public String getIdentityPublicKey(){
        return this.identity.getPublicKey();
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getExtraData() {
        return extraData;
    }

    public PlatformComponentProfile getPlatformComponentProfilePluginRoot() {
        return platformComponentProfile;
    }

    @Override
    public PlatformComponentType getPlatformComponentType() {
        return platformComponentType;
    }

    @Override
    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    public void setPlatformComponentProfile(final PlatformComponentProfile platformComponentProfile) {
        this.platformComponentProfile = platformComponentProfile;
    }

    /**
     * (non-javadoc)
     * @see NetworkService#getNetworkServiceConnectionManager()
     */
    @Override
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return communicationNetworkServiceConnectionManager;
    }

    /**
     * (non-javadoc)
     * @see NetworkService#constructDiscoveryQueryParamsFactory(PlatformComponentType, NetworkServiceType, String, String, Location, Double, String, String, Integer, Integer, PlatformComponentType, NetworkServiceType)
     */
    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(final PlatformComponentType    platformComponentType         ,
                                                                         final NetworkServiceType       networkServiceType            ,
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
                platformComponentType         ,
                networkServiceType            ,
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
     * Get is Register
     * @return boolean
     */
    public boolean isRegister() {
        return register;
    }

    /**
     * Handles the events CompleteComponentRegistrationNotification
     */
    public void handleCompleteComponentRegistrationNotificationEvent(final PlatformComponentProfile platformComponentProfileRegistered) {

        System.out.println(" CryptoAddressesNetworkServicePluginRoot - Starting method handleCompleteComponentRegistrationNotificationEvent");

        /*
         * If the component registered have my profile and my identity public key
         */
        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.NETWORK_SERVICE &&
                platformComponentProfileRegistered.getNetworkServiceType()    == NetworkServiceType   .CRYPTO_PAYMENT_REQUEST    &&
                platformComponentProfileRegistered.getIdentityPublicKey().equals(identity.getPublicKey())                        ) {

            /*
             * Mark as register
             */
            this.register = Boolean.TRUE;
            System.out.println(" Crypto Addresses Network Service is now registered.");

            try {

                cryptoAddressesExecutorAgent = new CryptoAddressesExecutorAgent(
                        communicationNetworkServiceConnectionManager,
                        this,
                        errorManager,
                        eventManager,
                        pluginDatabaseSystem,
                        pluginId,
                        null
                );

                cryptoAddressesExecutorAgent.start();

                addCryptoAddressesListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_SENT_NOTIFICATION, new NewReceiveMessagesNotificationEventHandler(this));

                System.out.println(" CryptoAddresses Executor Agent is now running and listening New Receive Messages Notification Events.");

            } catch(CantStartAgentException e) {

                CantStartPluginException pluginStartException = new CantStartPluginException(e, "", "Problem initializing crypto addresses dao.");
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            }

        }
    }

    /**
     * Handles the events CompleteRequestListComponentRegisteredNotificationEvent
     */
    public void handleCompleteComponentConnectionRequestNotificationEvent(final PlatformComponentProfile remoteComponentProfile) {


        System.out.println(" Crypto Payment Request Network Service - Starting method handleCompleteComponentConnectionRequestNotificationEvent");

        /*
         * Tell the manager to handler the new connection established
         */
        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);

        if (remoteNetworkServicesRegisteredList != null && !remoteNetworkServicesRegisteredList.isEmpty()){

            remoteNetworkServicesRegisteredList.add(remoteComponentProfile);

            System.out.print(" CPR NS - INCOMING CONNECTION ADDED: " + remoteComponentProfile.getAlias());
        }

    }

    @Override
    public void handleFailureComponentRegistrationNotificationEvent(final PlatformComponentProfile networkServiceApplicant,
                                                                    final PlatformComponentProfile remoteParticipant      ) {

        cryptoAddressesExecutorAgent.connectionFailure(remoteParticipant.getIdentityPublicKey());
    }

    @Override
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {

        System.out.println(" Crypto Addresses Network Service - Starting method handleCompleteRequestListComponentRegisteredNotificationEvent");

        System.out.print(" CADD NS - SUCCESSFUL CONNECTION: : " + getName());

        remoteNetworkServicesRegisteredList.addAll(platformComponentProfileRegisteredList);

    }


    /**
     * Handles the events CompleteRequestListComponentRegisteredNotificationEvent
     * @param remoteComponentProfile
     */
    @Override
    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {

    }

    public void handleNewMessages(FermatMessage fermatMessage) throws CantHandleNewMessagesException {

        try {

            Gson gson = new Gson();

            String jsonMessage = fermatMessage.getContent();

            NetworkServiceMessage networkServiceMessage = gson.fromJson(jsonMessage, NetworkServiceMessage.class);

            switch (networkServiceMessage.getMessageType()) {

                case ACCEPT:
                    AcceptMessage acceptMessage = gson.fromJson(jsonMessage, AcceptMessage.class);
                    receiveAcceptance(acceptMessage);
                    break;

                case DENY:
                    DenyMessage denyMessage = gson.fromJson(jsonMessage, DenyMessage.class);
                    receiveDenial(denyMessage);
                    break;

                case REQUEST:
                    // update the request to processing receive state with the given action.
                    RequestMessage requestMessage = gson.fromJson(jsonMessage, RequestMessage.class);
                    receiveRequest(requestMessage);
                    break;
                default:
                    throw new CantHandleNewMessagesException(
                            "message type: " +networkServiceMessage.getMessageType().name(),
                            "Message type not handled."
                    );
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_RECEIVE.
     * - Action        : REQUEST           .
     */
    private void receiveRequest(RequestMessage requestMessage) throws CantReceiveRequestException {

        try {

            ProtocolState protocolState = ProtocolState.PROCESSING_RECEIVE;
            RequestType   type          = RequestType  .RECEIVED          ;
            RequestAction action        = RequestAction.REQUEST           ;

            cryptoAddressesNetworkServiceDao.createAddressExchangeRequest(
                    requestMessage.getRequestId()                  ,
                    null                                           ,
                    requestMessage.getCryptoCurrency()             ,
                    requestMessage.getIdentityTypeRequesting()     ,
                    requestMessage.getIdentityTypeResponding()     ,
                    requestMessage.getIdentityPublicKeyRequesting(),
                    requestMessage.getIdentityPublicKeyResponding(),
                    protocolState                                  ,
                    type                                           ,
                    action                                         ,
                    requestMessage.getBlockchainNetworkType()
            );

        } catch(CantCreateRequestException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw new CantReceiveRequestException(e, "", "Error in Crypto Payment Request NS Dao.");
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantReceiveRequestException(e, "", "Unhandled Exception.");
        }
    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : ACCEPT.
     * - Protocol State: PROCESSING_RECEIVE.
     */
    private void receiveAcceptance(AcceptMessage acceptMessage) throws CantReceiveAcceptanceException {

        try {

            ProtocolState protocolState = ProtocolState.PROCESSING_RECEIVE;

            cryptoAddressesNetworkServiceDao.acceptAddressExchangeRequest(
                    acceptMessage.getRequestId(),
                    acceptMessage.getCryptoAddress(),
                    protocolState
            );

        } catch (CantAcceptAddressExchangeRequestException | PendingRequestNotFoundException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            reportUnexpectedException(e);
            throw new CantReceiveAcceptanceException(e, "", "Error in crypto addresses DAO");
        } catch (Exception e){

            reportUnexpectedException(e);
            throw new CantReceiveAcceptanceException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : DENY.
     * - Protocol State: PROCESSING_RECEIVE.
     */
    private void receiveDenial(DenyMessage denyMessage) throws CantReceiveDenialException {

        try {

            ProtocolState protocolState = ProtocolState.PROCESSING_RECEIVE;

            cryptoAddressesNetworkServiceDao.denyAddressExchangeRequest(
                    denyMessage.getRequestId(),
                    protocolState
            );

        } catch (CantDenyAddressExchangeRequestException | PendingRequestNotFoundException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            reportUnexpectedException(e);
            throw new CantReceiveDenialException(e, "", "Error in crypto addresses DAO");
        } catch (Exception e){

            reportUnexpectedException(e);
            throw new CantReceiveDenialException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    private void reportUnexpectedException(Exception e) {
        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
    }

    @Override
    public void setWsCommunicationsCloudClientConnectionManager(final WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager) {
        this.wsCommunicationsCloudClientManager = wsCommunicationsCloudClientManager;
    }

    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(final ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(final EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(final PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */
    @Override
    public void setId(final UUID pluginId) {
        this.pluginId = pluginId;
    }


}
