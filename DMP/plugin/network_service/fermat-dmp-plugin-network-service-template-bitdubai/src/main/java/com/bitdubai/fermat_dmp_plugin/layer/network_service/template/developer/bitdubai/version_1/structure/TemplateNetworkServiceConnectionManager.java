/*
 * @#TemplateNetworkServiceConnectionManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.database.communication.IncomingMessageDao;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.database.communication.OutgoingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsVPNConnection;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.HashMap;
import java.util.Map;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.TemplateNetworkServiceConnectionManager</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 31/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TemplateNetworkServiceConnectionManager implements NetworkServiceConnectionManager {

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
     * Holds all references to the template network service locals
     */
    private Map<String, TemplateNetworkServiceLocal> templateNetworkServiceLocalsCache;

    /**
     * Holds all references to the template network service remote agents
     */
    private Map<String, TemplateNetworkServiceRemoteAgent> templateNetworkServiceRemoteAgentsCache;

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
    public TemplateNetworkServiceConnectionManager(PlatformComponentProfile platformComponentProfile, ECCKeyPair identity, CommunicationsClientConnection communicationsClientConnection, Database dataBase, ErrorManager errorManager, EventManager eventManager) {
        super();
        this.platformComponentProfile = platformComponentProfile;
        this.identity = identity;
        this.communicationsClientConnection = communicationsClientConnection;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.incomingMessageDao = new IncomingMessageDao(dataBase);
        this.outgoingMessageDao = new OutgoingMessageDao(dataBase);
        this.templateNetworkServiceLocalsCache = new HashMap<>();
        this.templateNetworkServiceRemoteAgentsCache = new HashMap<>();
    }


    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager# connectTo(PlatformComponentProfile)
     */
    public void connectTo(PlatformComponentProfile remotePlatformComponentProfile) {

        try {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
            communicationsClientConnection.requestVpnConnection(platformComponentProfile, remotePlatformComponentProfile);


        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not connect to remote network service "));
        }

    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager# closeConnection(PlatformComponentProfile)
     */
    public void closeConnection(String remoteNetworkServicePublicKey) {

        //Remove the instance and stop his threads
        templateNetworkServiceRemoteAgentsCache.remove(remoteNetworkServicePublicKey).stop();

    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#closeAllConnection()
     */
    public void closeAllConnection() {

        for (String key : templateNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            templateNetworkServiceRemoteAgentsCache.remove(key).stop();
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
            CommunicationsVPNConnection communicationsVPNConnection = communicationsClientConnection.getCommunicationsVPNConnectionStablished(platformComponentProfile, remoteComponentProfile.getIdentityPublicKey());

            //Validate the connection
            if (communicationsVPNConnection != null &&
                    communicationsVPNConnection.isActive()) {

                 /*
                 * Instantiate the local reference
                 */
                TemplateNetworkServiceLocal templateNetworkServiceLocal = new TemplateNetworkServiceLocal(remoteComponentProfile, errorManager, eventManager, outgoingMessageDao);

                /*
                 * Instantiate the remote reference
                 */
                TemplateNetworkServiceRemoteAgent templateNetworkServiceRemoteAgent = new TemplateNetworkServiceRemoteAgent(identity, communicationsVPNConnection, remoteComponentProfile.getIdentityPublicKey(), errorManager, incomingMessageDao, outgoingMessageDao);

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
                templateNetworkServiceLocalsCache.put(remoteComponentProfile.getIdentityPublicKey(), templateNetworkServiceLocal);
                templateNetworkServiceRemoteAgentsCache.put(remoteComponentProfile.getIdentityPublicKey(), templateNetworkServiceRemoteAgent);

            }

        } catch (Exception e) {
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not get connection"));
        }
    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#getNetworkServiceLocalInstance(String)
     */
    public TemplateNetworkServiceLocal getNetworkServiceLocalInstance(String remoteNetworkServicePublicKey) {

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