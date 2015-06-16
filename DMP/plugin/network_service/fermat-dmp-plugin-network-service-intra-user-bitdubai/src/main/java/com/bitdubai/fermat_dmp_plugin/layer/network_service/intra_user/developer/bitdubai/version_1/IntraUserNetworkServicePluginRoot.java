package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.p2p_communication.CantConnectToRemoteServiceException;
import com.bitdubai.fermat_api.layer.p2p_communication.CantSendMessageException;
import com.bitdubai.fermat_api.layer.p2p_communication.CommunicationChannelNotImplemented;
import com.bitdubai.fermat_api.layer.p2p_communication.CommunicationChannels;
import com.bitdubai.fermat_api.layer.p2p_communication.CommunicationLayerManager;
import com.bitdubai.fermat_api.layer.p2p_communication.ConnectionStatus;
import com.bitdubai.fermat_api.layer.p2p_communication.DealsWithCommunicationLayerManager;
import com.bitdubai.fermat_api.layer.p2p_communication.ServiceToServiceOnlineConnection;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.IntraUserManager;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricKeyCreator;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricPrivateKey;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.PrivateKey;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkService;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.IntraUserIncomingNetworkServiceConnectionRequestHandler;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.UserLoggedInEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.UserLoggedOutEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantLogInNetworkIntraUserException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceLocal;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceMessage;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceRemote;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.all_definition.enums.Plugins.BITDUBAI_USER_NETWORK_SERVICE;
import static com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.IntraUserNetworkServicePluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 *
 * Created by loui on 12/02/15.
 * Update by Roberto Requena - (rrequena) on 20/05/15.
 *
 * @version 1.0
 */
public class IntraUserNetworkServicePluginRoot  implements IntraUserManager, Service, NetworkService, DealsWithCommunicationLayerManager, DealsWithPluginDatabaseSystem, DealsWithEvents, DealsWithErrors, Plugin {

    /**
     * DealsWithCommunicationLayerManager Interface member variables.
     */
    private CommunicationLayerManager communicationLayerManager;

    /**
     * Represent the status of the service
     */
    private ServiceStatus serviceStatus;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variable
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;

    /**
     * Hold the listeners references
     */
    private List<EventListener> listenersAdded;

    /**
     * Holds the private and public key
     */
    private KeyPair keyPair;

    /**
     * Represent the intraUserNetworkServiceLocal
     */
    private IntraUserNetworkServiceLocal intraUserNetworkServiceLocal;


    /**
     * Holds the serviceToServiceOnlineConnectionsCache
     */
    private Map<UUID, ServiceToServiceOnlineConnection>  serviceToServiceOnlineConnectionsCache;

    /**
     * Constructor
     */
    public IntraUserNetworkServicePluginRoot() {
        super();
        this.listenersAdded                         = new ArrayList<>();
        this.intraUserNetworkServiceLocal           = new IntraUserNetworkServiceLocal();
        this.serviceToServiceOnlineConnectionsCache = new HashMap<>();
    }

    /**
     * Initialize the pair key to this instance
     *
     *  Private Key + Public Key
     */
    private void initializePairKey() {

        /*
         * Created a new private key with generate big integer random
         */
        PrivateKey privateKey = new AsymmetricPrivateKey(new BigInteger(AsymmectricCryptography.createPrivateKey(),16));

        /*
         * Generate the public key from the private key
         */
        AsymmetricKeyCreator keyCreator = new AsymmetricKeyCreator();
        PublicKey publicKey = keyCreator.createPublicKey(privateKey);

        /*
         * Storage in the key pair
         */
        keyPair = new KeyPair(publicKey, privateKey);

    }

    /**
     * Initialize the event listener and configure
     */
    private void initializeListener(){

        /*
         * Listen and handle incoming network service connection request in event
         */
        EventListener eventListener = eventManager.getNewListener(EventType.INCOMING_NETWORK_SERVICE_CONNECTION_REQUEST);
        eventListener.setEventHandler(new IntraUserIncomingNetworkServiceConnectionRequestHandler(this));
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        /*
         * Listen and handle intra user logged in event
         */
        eventListener = eventManager.getNewListener(EventType.INTRA_USER_LOGGED_IN);
        eventListener.setEventHandler(new UserLoggedInEventHandler(this));
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        /*
         * Listen and handle intra user logged out event
         */
        eventListener = eventManager.getNewListener(EventType.INTRA_USER_LOGGED_OUT);
        eventListener.setEventHandler(new UserLoggedOutEventHandler(this));
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {


        boolean entrar = Boolean.TRUE;

        if (entrar) return;

        /*
         * If all resources are inject
         */
        if (communicationLayerManager != null &&
                pluginDatabaseSystem  != null &&
                    errorManager      != null &&
                        eventManager  != null) {

            /*
             * Generate a new KeyPair
             */
            initializePairKey();

            /*
             * Initialize the listener
             */
            initializeListener();

            /*
             * Register this network service whit the communicationLayerManager
             */
            communicationLayerManager.registerNetworkService(NetworkServices.INTRA_USER, pluginId);

            /*
             * Its all ok, set the new status
            */
            this.serviceStatus = ServiceStatus.STARTED;


        } else {
            errorManager.reportUnexpectedPluginException(BITDUBAI_USER_NETWORK_SERVICE, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("No all required resource are injected"));
            throw new CantStartPluginException(BITDUBAI_USER_NETWORK_SERVICE);
        }



    }


    @Override
    public void pause() {

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.PAUSED;

    }

    @Override
    public void resume() {

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {


        /**
         * I will remove all the event listeners registered with the event manager.
         */
        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();

         /*
         * Unregister whit the communicationLayerManager
         */
        communicationLayerManager.unregisterNetworkService(pluginId);

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    /**
     * (non-Javadoc)
     * @see Service#getStatus()
     */
    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }


    /**
     * (non-Javadoc)
     * @see DealsWithCommunicationLayerManager#setCommunicationLayerManager(CommunicationLayerManager)  No Compila (Luis)
     */
    @Override
    public void setCommunicationLayerManager(CommunicationLayerManager communicationLayerManager) {
        this.communicationLayerManager = communicationLayerManager;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithErrors#setErrorManager(ErrorManager)
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithEvents#setEventManager(EventManager) )
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /* (non-Javadoc)
    * @see DealsWithPluginDatabaseSystem#setPluginDatabaseSystem()
    */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
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
     * (non-Javadoc)
     * @see Plugin#setId(UUID)
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * Method that logged out the user (register on the net)
     *
     * @param userId
     * @throws CantLogInNetworkIntraUserException
     */
    public void logIn (UUID userId) throws CantLogInNetworkIntraUserException{

        //Se puso para solucion de "Unread field, findBug
        pluginDatabaseSystem.toString();

    }

    /**
     * Method that logged out the user (unregister on the net)
     *
     * @param userId
     */
    public void logOut (UUID userId) {

    }

    /**
     * Method to accept incoming connection request
     *
     * @param communicationChannel
     * @param remoteNetworkService
     */
    public void  acceptIncomingNetworkServiceConnectionRequest(CommunicationChannels communicationChannel, UUID remoteNetworkService){

        try {

           ServiceToServiceOnlineConnection serviceToServiceOnlineConnection = communicationLayerManager.acceptIncomingNetworkServiceConnectionRequest(communicationChannel, NetworkServices.INTRA_USER, this.getId(), remoteNetworkService);

           if (serviceToServiceOnlineConnection.getStatus() == ConnectionStatus.CONNECTED &&
                   serviceToServiceOnlineConnection.getUnreadMessagesCount() > 0 ) {

                //Read a message with serialize remote object
                IntraUserNetworkServiceMessage message = (IntraUserNetworkServiceMessage) serviceToServiceOnlineConnection.readNextMessage();
                String jsomObject = message.getTextContent();

                /*
                 * Problema de seguridad si el mensaje viene encriptado
                 * de donde obtener la clave publica del intra user remoto
                 * para desencriptar dicho mensaje
                 *
                 *  Posible solución enviarla en un atributo aparte en el mismo mensaje? es seguro?
                 *
                 */

                //Deserialize the remote object
                IntraUserNetworkServiceRemote intraUserNetworkServiceRemote = new IntraUserNetworkServiceRemote(jsomObject);


                //Cache the Online Connection
                serviceToServiceOnlineConnectionsCache.put(remoteNetworkService, serviceToServiceOnlineConnection);

                //Put to the references cache
                this.intraUserNetworkServiceLocal.addIntraUserNetworkServiceRemoteInstance(remoteNetworkService, intraUserNetworkServiceRemote);

           }

        } catch (CommunicationChannelNotImplemented communicationChannelNotImplemented) {
            errorManager.reportUnexpectedPluginException(BITDUBAI_USER_NETWORK_SERVICE, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not accept incoming connection"));

        }

    }

    /**
     * Create a new connection to
     *
     * @param remoteNetworkService
     */
    public void connectTo(UUID remoteNetworkService){

        try {

            ServiceToServiceOnlineConnection serviceToServiceOnlineConnection = communicationLayerManager.connectTo(NetworkServices.INTRA_USER, remoteNetworkService);

            if (serviceToServiceOnlineConnection.getStatus() == ConnectionStatus.CONNECTED) {

                IntraUserNetworkServiceRemote remote = intraUserNetworkServiceLocal.getRemoteObject();
                String jsomObject = remote.serializeToJsomObject();

                //Send a message with serialize remote object
                sendMessage(remoteNetworkService, jsomObject);

                //Cache the Online Connection
                serviceToServiceOnlineConnectionsCache.put(remoteNetworkService, serviceToServiceOnlineConnection);

            }

        } catch (CantConnectToRemoteServiceException e) {
            errorManager.reportUnexpectedPluginException(BITDUBAI_USER_NETWORK_SERVICE, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not connect to remote network service "+remoteNetworkService));
        }

    }

    /**
     * Send a message
     *
     * @param remoteNetworkService
     * @param msjTextContent
     */
    public void sendMessage(UUID remoteNetworkService, String msjTextContent){

        //Get the Online Connection from remote reference
        ServiceToServiceOnlineConnection serviceToServiceOnlineConnection = serviceToServiceOnlineConnectionsCache.get(remoteNetworkService);

        if (serviceToServiceOnlineConnection.getStatus() == ConnectionStatus.CONNECTED) {

            try {

                //Create a new message to send
                IntraUserNetworkServiceMessage message = new IntraUserNetworkServiceMessage();

                //Encrypt the message text content
                message.setTextContent(AsymmectricCryptography.encryptMessagePublicKey(msjTextContent, keyPair.getPublic().toString()));

                //Sing the encrypt message
                message.setSignature(AsymmectricCryptography.createMessageSignature(message.getTextContent(), keyPair.getPrivate().toString()));

                //Send the message
                serviceToServiceOnlineConnection.sendMessage(message);

            } catch (CantSendMessageException e) {
                errorManager.reportUnexpectedPluginException(BITDUBAI_USER_NETWORK_SERVICE, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not send message to remote network service " + remoteNetworkService));
            }

        }

    }

}
