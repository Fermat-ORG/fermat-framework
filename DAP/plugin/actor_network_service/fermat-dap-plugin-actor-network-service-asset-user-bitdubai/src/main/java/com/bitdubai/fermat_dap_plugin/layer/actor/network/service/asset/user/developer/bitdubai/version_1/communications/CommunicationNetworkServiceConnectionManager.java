/*
 * @#TemplateNetworkServiceConnectionManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.communications;



import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.IncomingMessageDao;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.OutgoingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsVPNConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.HashMap;
import java.util.Map;


/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationNetworkServiceConnectionManager implements NetworkServiceConnectionManager {

    /**
     * Represent the communicationsClientConnection
     */
    private CommunicationsClientConnection communicationsClientConnection;

    /**
     * Represent the platformComponentProfile
     */
    private PlatformComponentProfile platformComponentProfile;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * Holds all references to the communication network service locals
     */
    private Map<String, CommunicationNetworkServiceLocal> communicationNetworkServiceLocalsCache;

    /**
     * Holds all references to the communication network service remote agents
     */
    private Map<String,CommunicationNetworkServiceRemoteAgent> communicationNetworkServiceRemoteAgentsCache;

    /**
     * Represent the incomingMessageDao
     */
    private IncomingMessageDao incomingMessageDao;

    /**
     * Represent the outgoingMessageDao
     */
    private OutgoingMessageDao outgoingMessageDao;

    /**
     * Represent the identity
     */
    private ECCKeyPair identity;


    /**
     * Constructor with parameters
     *
     * @param communicationsClientConnection a communicationLayerManager instance
     * @param errorManager              a errorManager instance
     */
    public CommunicationNetworkServiceConnectionManager(PlatformComponentProfile platformComponentProfile, ECCKeyPair identity, CommunicationsClientConnection communicationsClientConnection, Database dataBase, ErrorManager errorManager, EventManager eventManager) {
        super();
        this.platformComponentProfile = platformComponentProfile;
        this.identity = identity;
        this.communicationsClientConnection = communicationsClientConnection;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.incomingMessageDao = new IncomingMessageDao(dataBase);
        this.outgoingMessageDao = new OutgoingMessageDao(dataBase);
        this.communicationNetworkServiceLocalsCache = new HashMap<>();
        this.communicationNetworkServiceRemoteAgentsCache = new HashMap<>();
    }


    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager# connectTo(PlatformComponentProfile)
     */
    @Override
    public void connectTo(PlatformComponentProfile remotePlatformComponentProfile) {

        try {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
            communicationsClientConnection.requestVpnConnection(platformComponentProfile, remotePlatformComponentProfile);


        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not connect to remote network service "));
        }

    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#connectTo(PlatformComponentProfile, PlatformComponentProfile, PlatformComponentProfile)
     */
    @Override
    public void connectTo(PlatformComponentProfile applicantParticipant, PlatformComponentProfile applicantNetworkService, PlatformComponentProfile remoteParticipant) throws CantEstablishConnectionException {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
        communicationsClientConnection.requestDiscoveryVpnConnection(applicantParticipant, applicantNetworkService, remoteParticipant);

    }


    //TODO: COPIAR ESTO A TODOS LOS OTROS NETWORK SERVICES;  XXOO. te quiero robert xD

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#closeConnection(String)
     */
    @Override
    public void closeConnection(String remoteNetworkServicePublicKey) {
        //Remove the instance and stop his threads
        if(communicationNetworkServiceRemoteAgentsCache.containsKey(remoteNetworkServicePublicKey)) {
            communicationNetworkServiceRemoteAgentsCache.remove(remoteNetworkServicePublicKey).stop();
        }

        if(communicationNetworkServiceLocalsCache.containsKey(remoteNetworkServicePublicKey)){
            communicationNetworkServiceLocalsCache.remove(remoteNetworkServicePublicKey);
        }

    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#closeAllConnection()
     */
    @Override
    public void closeAllConnection() {

        for (String key : communicationNetworkServiceRemoteAgentsCache.keySet()) {
            closeConnection(key);
        }

    }

    /**
     * Handles events that indicate a connection to been established between two
     * network services and prepares all objects to work with this new connection
     *
     * @param remoteComponentProfile
     */
    public void handleEstablishedRequestedNetworkServiceConnection(PlatformComponentProfile remoteComponentProfile) {

        try {

            /*
             * Get the active connection
             */
            CommunicationsVPNConnection communicationsVPNConnection = communicationsClientConnection.getCommunicationsVPNConnectionStablished(platformComponentProfile.getNetworkServiceType(), remoteComponentProfile);

            //Validate the connection
            if (communicationsVPNConnection != null &&
                    communicationsVPNConnection.isActive()) {

                 /*
                 * Instantiate the local reference
                 */
                CommunicationNetworkServiceLocal communicationNetworkServiceLocal = new CommunicationNetworkServiceLocal(remoteComponentProfile, errorManager, eventManager, outgoingMessageDao,platformComponentProfile.getNetworkServiceType());

                /*
                 * Instantiate the remote reference
                 */
                CommunicationNetworkServiceRemoteAgent communicationNetworkServiceRemoteAgent = new CommunicationNetworkServiceRemoteAgent(identity, communicationsVPNConnection, errorManager, eventManager, incomingMessageDao, outgoingMessageDao);

                /*
                 * Register the observer to the observable agent
                 */
                communicationNetworkServiceRemoteAgent.addObserver(communicationNetworkServiceLocal);

                /*
                 * Start the service thread
                 */
                communicationNetworkServiceRemoteAgent.start();

                /*
                 * Add to the cache
                 */
                communicationNetworkServiceLocalsCache.put(remoteComponentProfile.getIdentityPublicKey(), communicationNetworkServiceLocal);
                communicationNetworkServiceRemoteAgentsCache.put(remoteComponentProfile.getIdentityPublicKey(), communicationNetworkServiceRemoteAgent);

            }

        } catch (Exception e) {
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not get connection"));
        }
    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#getNetworkServiceLocalInstance(String)
     */
    @Override
    public CommunicationNetworkServiceLocal getNetworkServiceLocalInstance(String remoteNetworkServicePublicKey) {

        //return the instance
        return communicationNetworkServiceLocalsCache.get(remoteNetworkServicePublicKey);
    }

    /**
     * Pause the manager
     */
    public void pause() {

        for (String key : communicationNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            communicationNetworkServiceRemoteAgentsCache.get(key).pause();
        }

    }

    public ECCKeyPair getIdentity() {
        return identity;
    }

    /**
     * Resume the manager
     */
    public void resume() {

        for (String key : communicationNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            communicationNetworkServiceRemoteAgentsCache.get(key).resume();
        }

    }

    /**
     * Get the OutgoingMessageDao
     * @return OutgoingMessageDao
     */
    public  OutgoingMessageDao getOutgoingMessageDao() {
        return outgoingMessageDao;
    }

    /**
     * Get the IncomingMessageDao
     * @return IncomingMessageDao
     */
    public  IncomingMessageDao getIncomingMessageDao() {
        return incomingMessageDao;
    }
}