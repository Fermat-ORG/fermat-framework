package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService.database.IncomingMessageDAO;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService.database.OutgoingMessageDAO;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationLayerManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ConnectionStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ServiceToServiceOnlineConnection;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodrigo on 8/12/15.
 */
public class WalletStoreNetworkServiceManager {
    /**
     * Represent the communicationLayerManager
     */
    private CommunicationLayerManager communicationLayerManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * Holds all references to the template network service locals
     */
    private Map<String, WalletStoreNetworkServiceLocalAgent> templateNetworkServiceLocalsCache;

    /**
     * Holds all references to the template network service remote agents
     */
    private Map<String, WalletStoreNetworkServiceRemoteAgent> templateNetworkServiceRemoteAgentsCache;

    /**
     * Represent the incomingMessageDao
     */
    private IncomingMessageDAO incomingMessageDao;

    /**
     * Represent the outgoingMessageDao
     */
    private OutgoingMessageDAO outgoingMessageDao;

    /**
     * Represent the eccKeyPair
     */
    private ECCKeyPair eccKeyPair;


    /**
     * Constructor with parameters
     *
     * @param communicationLayerManager a communicationLayerManager instance
     * @param errorManager              a errorManager instance
     */
    public WalletStoreNetworkServiceManager(ECCKeyPair eccKeyPair, CommunicationLayerManager communicationLayerManager, Database dataBase, ErrorManager errorManager, EventManager eventManager) {
        super();
        this.eccKeyPair = eccKeyPair;
        this.communicationLayerManager = communicationLayerManager;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.incomingMessageDao = new IncomingMessageDAO(dataBase);
        this.outgoingMessageDao = new OutgoingMessageDAO(dataBase);
        this.templateNetworkServiceLocalsCache = new HashMap<>();
        this.templateNetworkServiceRemoteAgentsCache = new HashMap<>();
    }


    /**
     * Create a new connection to
     *
     * @param remoteNetworkServicePublicKey the remote Network Service public key
     * @return WalletStoreNetworkServiceLocalAgent a new instance
     */
    public void connectTo(String remoteNetworkServicePublicKey) {

        try {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
            communicationLayerManager.requestConnectionTo(NetworkServices.TEMPLATE, remoteNetworkServicePublicKey);


        } catch (CommunicationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not connect to remote network service "));
        }

    }

    /**
     * Close a previous connection
     *
     * @param remoteNetworkServicePublicKey he remote network service public key
     */
    public void closeConnection(String remoteNetworkServicePublicKey) {

        //Remove the instance and stop his threads
        templateNetworkServiceRemoteAgentsCache.remove(remoteNetworkServicePublicKey).stop();

    }

    /**
     * Close all previous connections
     */
    public void closeAllConnection() {

        for (String key : templateNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            templateNetworkServiceRemoteAgentsCache.remove(key).stop();
        }

    }

    /**
     * Method to accept incoming connection request
     *
     * @param communicationChannel          the communication channel
     * @param remoteNetworkServicePublicKey the remote network service public key
     */
    public void acceptIncomingNetworkServiceConnectionRequest(CommunicationChannels communicationChannel, String remoteNetworkServicePublicKey) {

        try {

            /*
             * Accept the new connection
             */
            communicationLayerManager.acceptIncomingNetworkServiceConnectionRequest(communicationChannel, NetworkServices.TEMPLATE, remoteNetworkServicePublicKey);

            /*
             * Get the active connection
             */
            ServiceToServiceOnlineConnection serviceToServiceOnlineConnection = communicationLayerManager.getActiveNetworkServiceConnection(communicationChannel, NetworkServices.TEMPLATE, remoteNetworkServicePublicKey);

            //Validate the connection
            if (serviceToServiceOnlineConnection != null &&
                    serviceToServiceOnlineConnection.getStatus() == ConnectionStatus.CONNECTED) {

                /*
                 * Instantiate the local reference
                 */
                WalletStoreNetworkServiceLocalAgent templateNetworkServiceLocal = new WalletStoreNetworkServiceLocalAgent(remoteNetworkServicePublicKey, errorManager, eventManager, outgoingMessageDao);

                /*
                 * Instantiate the remote reference
                 */
                WalletStoreNetworkServiceRemoteAgent templateNetworkServiceRemoteAgent = new WalletStoreNetworkServiceRemoteAgent(eccKeyPair, remoteNetworkServicePublicKey, serviceToServiceOnlineConnection, errorManager, incomingMessageDao, outgoingMessageDao);

                /*
                 * Register the observer to the observable agent
                 */
                templateNetworkServiceRemoteAgent.addObserver(templateNetworkServiceLocal);

                /*
                 * Start the service thread
                 */
                templateNetworkServiceRemoteAgent.start();

                /*
                 * Add to the cache
                 */
                templateNetworkServiceLocalsCache.put(remoteNetworkServicePublicKey, templateNetworkServiceLocal);
                templateNetworkServiceRemoteAgentsCache.put(remoteNetworkServicePublicKey, templateNetworkServiceRemoteAgent);

            }

        } catch (CommunicationException communicationException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not accept incoming connection"));
        }

    }

    /**
     * Handles events that indicate a connection to been established between two intra user
     * network services and prepares all objects to work with this new connection
     *
     * @param communicationChannel          the communication channel
     * @param remoteNetworkServicePublicKey the remote network service public key
     */
    public void handleEstablishedRequestedNetworkServiceConnection(CommunicationChannels communicationChannel, String remoteNetworkServicePublicKey) {

        try {

            /*
             * Get the active connection
             */
            ServiceToServiceOnlineConnection serviceToServiceOnlineConnection = communicationLayerManager.getActiveNetworkServiceConnection(communicationChannel, NetworkServices.INTRA_USER, remoteNetworkServicePublicKey);

            //Validate the connection
            if (serviceToServiceOnlineConnection != null &&
                    serviceToServiceOnlineConnection.getStatus() == ConnectionStatus.CONNECTED) {

                 /*
                 * Instantiate the local reference
                 */
                WalletStoreNetworkServiceLocalAgent templateNetworkServiceLocal = new WalletStoreNetworkServiceLocalAgent(remoteNetworkServicePublicKey, errorManager, eventManager, outgoingMessageDao);

                /*
                 * Instantiate the remote reference
                 */
                WalletStoreNetworkServiceRemoteAgent templateNetworkServiceRemoteAgent = new WalletStoreNetworkServiceRemoteAgent(eccKeyPair, remoteNetworkServicePublicKey, serviceToServiceOnlineConnection, errorManager, incomingMessageDao, outgoingMessageDao);

                /*
                 * Register the observer to the observable agent
                 */
                templateNetworkServiceRemoteAgent.addObserver(templateNetworkServiceLocal);

                /*
                 * Start the service thread
                 */
                templateNetworkServiceRemoteAgent.start();

                /*
                 * Add to the cache
                 */
                templateNetworkServiceLocalsCache.put(remoteNetworkServicePublicKey, templateNetworkServiceLocal);
                templateNetworkServiceRemoteAgentsCache.put(remoteNetworkServicePublicKey, templateNetworkServiceRemoteAgent);

            }

        } catch (CommunicationException communicationException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not get connection"));
        }
    }

    /**
     * Return the WalletStoreNetworkServiceLocalAgent that represent the intra user network service remote
     *
     * @param remoteNetworkServicePublicKey the remote network service public key
     * @return WalletStoreNetworkServiceLocalAgent the local instance that represent
     */
    public WalletStoreNetworkServiceLocalAgent getIntraUserNetworkServiceLocalInstance(String remoteNetworkServicePublicKey) {

        //return the instance
        return templateNetworkServiceLocalsCache.get(remoteNetworkServicePublicKey);
    }

    /**
     * Pause the manager
     */
    public void pause() {

        for (String key : templateNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            templateNetworkServiceRemoteAgentsCache.get(key).pause();
        }

    }

    /**
     * Resume the manager
     */
    public void resume() {

        for (String key : templateNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            templateNetworkServiceRemoteAgentsCache.get(key).resume();
        }

    }
}
