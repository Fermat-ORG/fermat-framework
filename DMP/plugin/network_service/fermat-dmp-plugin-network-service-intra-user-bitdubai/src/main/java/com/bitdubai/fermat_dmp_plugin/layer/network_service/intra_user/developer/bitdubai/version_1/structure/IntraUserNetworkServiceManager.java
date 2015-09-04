/*
 * @#IntraUserNetworkServiceManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.exceptions.ErrorCancellingIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.exceptions.ErrorDisconnectingIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.exceptions.ErrorInIntraUserSearchException;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.exceptions.ErrorSearchingSuggestionsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUser;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserNotification;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationLayerManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ConnectionStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ServiceToServiceOnlineConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceManager</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 31/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraUserNetworkServiceManager implements IntraUserManager {

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
     * Holds all references to the intra user network service locals
     */
    private Map<String, IntraUserNetworkServiceLocal> intraUserNetworkServiceLocalsCache;

    /**
     * Holds all references to the intra user network service remote agents
     */
    private Map<String, IntraUserNetworkServiceRemoteAgent> intraUserNetworkServiceRemoteAgentsCache;

    /**
     * Represent the incomingMessageDataAccessObject
     */
    private IncomingMessageDataAccessObject incomingMessageDataAccessObject;

    /**
     * Represent the outgoingMessageDataAccessObject
     */
    private OutgoingMessageDataAccessObject outgoingMessageDataAccessObject;

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
    public IntraUserNetworkServiceManager(ECCKeyPair eccKeyPair, CommunicationLayerManager communicationLayerManager, Database dataBase, ErrorManager errorManager, EventManager eventManager) {
        super();
        this.eccKeyPair = eccKeyPair;
        this.communicationLayerManager = communicationLayerManager;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.incomingMessageDataAccessObject = new IncomingMessageDataAccessObject(dataBase, errorManager);
        this.outgoingMessageDataAccessObject = new OutgoingMessageDataAccessObject(dataBase, errorManager);
        this.intraUserNetworkServiceLocalsCache = new HashMap<>();
        this.intraUserNetworkServiceRemoteAgentsCache = new HashMap<>();
    }


    /**
     * Create a new connection to
     *
     * @param remoteNetworkServicePublicKey the remote Network Service public key
     * @return IntraUserNetworkServiceLocal a new instance
     */
    public void connectTo(String remoteNetworkServicePublicKey) {

        try {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
            communicationLayerManager.requestConnectionTo(NetworkServices.INTRA_USER, remoteNetworkServicePublicKey);


        } catch (CommunicationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not connect to remote network service "));
        }

    }

    /**
     * Close a previous connection
     *
     * @param remoteNetworkServicePublicKey he remote network service public key
     */
    public void closeConnection(String remoteNetworkServicePublicKey) {

        //Remove the instance and stop his threads
        intraUserNetworkServiceRemoteAgentsCache.remove(remoteNetworkServicePublicKey).stop();

    }

    /**
     * Close all previous connections
     */
    public void closeAllConnection() {

        for (String key : intraUserNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            intraUserNetworkServiceRemoteAgentsCache.remove(key).stop();
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
            communicationLayerManager.acceptIncomingNetworkServiceConnectionRequest(communicationChannel, NetworkServices.INTRA_USER, remoteNetworkServicePublicKey);

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
                IntraUserNetworkServiceLocal intraUserNetworkServiceLocal = new IntraUserNetworkServiceLocal(remoteNetworkServicePublicKey, errorManager, eventManager, outgoingMessageDataAccessObject);

                /*
                 * Instantiate the remote reference
                 */
                IntraUserNetworkServiceRemoteAgent intraUserNetworkServiceRemoteAgent = new IntraUserNetworkServiceRemoteAgent(eccKeyPair, remoteNetworkServicePublicKey, serviceToServiceOnlineConnection, errorManager, incomingMessageDataAccessObject, outgoingMessageDataAccessObject);

                /*
                 * Register the observer to the observable agent
                 */
                intraUserNetworkServiceRemoteAgent.addObserver(intraUserNetworkServiceLocal);

                /*
                 * Start the service thread
                 */
                intraUserNetworkServiceRemoteAgent.start();

                /*
                 * Add to the cache
                 */
                intraUserNetworkServiceLocalsCache.put(remoteNetworkServicePublicKey, intraUserNetworkServiceLocal);
                intraUserNetworkServiceRemoteAgentsCache.put(remoteNetworkServicePublicKey, intraUserNetworkServiceRemoteAgent);

            }

        } catch (CommunicationException communicationException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not accept incoming connection"));
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
                IntraUserNetworkServiceLocal intraUserNetworkServiceLocal = new IntraUserNetworkServiceLocal(remoteNetworkServicePublicKey, errorManager, eventManager, outgoingMessageDataAccessObject);

                /*
                 * Instantiate the remote reference
                 */
                IntraUserNetworkServiceRemoteAgent intraUserNetworkServiceRemoteAgent = new IntraUserNetworkServiceRemoteAgent(eccKeyPair, remoteNetworkServicePublicKey, serviceToServiceOnlineConnection, errorManager, incomingMessageDataAccessObject, outgoingMessageDataAccessObject);

                /*
                 * Register the observer to the observable agent
                 */
                intraUserNetworkServiceRemoteAgent.addObserver(intraUserNetworkServiceLocal);

                /*
                 * Start the service thread
                 */
                intraUserNetworkServiceRemoteAgent.start();

                /*
                 * Add to the cache
                 */
                intraUserNetworkServiceLocalsCache.put(remoteNetworkServicePublicKey, intraUserNetworkServiceLocal);
                intraUserNetworkServiceRemoteAgentsCache.put(remoteNetworkServicePublicKey, intraUserNetworkServiceRemoteAgent);

            }

        } catch (CommunicationException communicationException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not get connection"));
        }
    }

    /**
     * Return the IntraUserNetworkServiceLocal that represent the intra user network service remote
     *
     * @param remoteNetworkServicePublicKey the remote network service public key
     * @return IntraUserNetworkServiceLocal the local instance that represent
     */
    public IntraUserNetworkServiceLocal getIntraUserNetworkServiceLocalInstance(String remoteNetworkServicePublicKey) {

        //return the instance
        return intraUserNetworkServiceLocalsCache.get(remoteNetworkServicePublicKey);
    }

    /**
     * Pause the manager
     */
    public void pause() {

        for (String key : intraUserNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            intraUserNetworkServiceRemoteAgentsCache.get(key).pause();
        }

    }

    /**
     * Resume the manager
     */
    public void resume() {

        for (String key : intraUserNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            intraUserNetworkServiceRemoteAgentsCache.get(key).resume();
        }

    }


    /*
     * IntraUserManager Interface method implementation
     */
    @Override
    public List<IntraUser> searchIntraUserByName(String intraUserAlias) throws ErrorInIntraUserSearchException {
        return null;
    }

    @Override
    public List<IntraUser> getIntraUsersSuggestions(int max, int offset) throws ErrorSearchingSuggestionsException {
        return null;
    }

    @Override
    public void askIntraUserForAcceptance(String intraUserLoggedInPublicKey, String intraUserLoggedInName, String intraUserToAddPublicKey, byte[] myProfileImage) {

    }

    @Override
    public void acceptIntraUser(String intraUserLoggedInPublicKey, String intraUserToAddPublicKey) {

    }

    @Override
    public void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey) {

    }

    @Override
    public void disconnectIntraUSer(String intraUserLoggedInPublicKey, String intraUserToDisconnectPublicKey) throws ErrorDisconnectingIntraUserException {

    }

    @Override
    public void cancelIntraUSer(String intraUserLoggedInPublicKey, String intraUserToCancelPublicKey) throws ErrorCancellingIntraUserException {

    }

    @Override
    public List<IntraUserNotification> getNotifications() {
        return new ArrayList<IntraUserNotification>();
    }

    @Override
    public void confirmNotification(String intraUserLogedInPublicKey, String intraUserInvolvedPublicKey) {

    }
}